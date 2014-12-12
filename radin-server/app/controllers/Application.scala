package controllers

import securesocial.core._
import service.DemoUser
import play.api.mvc.{ Action, RequestHeader }
import play.api._
import scala.slick._
import scala.slick.driver.SQLiteDriver.simple._
import play.api.db.slick._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.api.mvc.BodyParsers._
import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.mvc.BodyParsers
import play.api.db.slick.DBAction
import database.Tables._
import database.OtherModels._

class Application(override implicit val env: RuntimeEnvironment[DemoUser]) extends securesocial.core.SecureSocial[DemoUser] {

  def firstAction = DBAction { implicit rs =>
    try {
      users.ddl.create
      radinGroups.ddl.create
      userRelationships.ddl.create
      transactions.ddl.create
      memberInRadins.ddl.create
      userConcernedByTransactions.ddl.create
    } finally {
      users.insert(User("name", "lastname", "username", "password", "email", "address", "iban", "bicSwift", ""))
      users.insert(User("second", "beforeLast", "uname", "mdp", "courriel", "chez moi", "#1", "mybic", ""))
      users.insert(User("Joel", "Kaufman", "jojo", "1234", "jojo@epfl.ch", "Monadresse", "iban", "#2", ""))
      users.insert(User("Koko", "loco", "koko", "234", "koko@epfl.ch", "kokoAdd", "iban", "#3", ""))
      radinGroups.insert(RadinGroup("radinGroup", "2014/11/28 10/11", "description bidon", ""))
      transactions.insert(Transaction(1, 1, 1, 100, "CHF", "2014/01/01 00/00", "Buy more jewelleries", "PAYMENT"))
      transactions.insert(Transaction(1, 1, 2, 50, "CHF", "2013/02/01 00/00", "Whatever", "PAYMENT"))
      transactions.insert(Transaction(1, 2, 1, 25, "CHF", "2014/02/01 00/00", "Cool expense", "PAYMENT"))
      transactions.insert(Transaction(1, 2, 2, 150, "CHF", "2014/01/02 00/00", "Cooler expense", "PAYMENT"))
      userRelationships.insert(UserRelationship(1, 2, 10))
      userRelationships.insert(UserRelationship(3, 2, 11))
      userRelationships.insert(UserRelationship(1, 3, 12))
      memberInRadins ++= Seq((1, 1, "", 0, ""), (2, 1, "", 0, ""))
      userConcernedByTransactions ++= Seq((1, 1, 1), (1, 2, 2), (1, 3, 1), (2, 1, 1), (2, 2, 2), (2, 4, 1))
    }

    Ok("done")
  }

  implicit val transactionFormat = Json.format[Transaction]
  
 

  def getTransactionsForGroup(rgid: Int) = DBAction { implicit rs =>
    val transactionList = toJson(transactions.list.filter(_.T_parentRadinGroupID == rgid))
    val jsonValue: Seq[(String, JsValue)] = List(("transaction", transactionList))
    val jsonResponse: JsObject = JsObject(jsonValue)
    Ok(jsonResponse)
  }
  
  def newTransaction = DBAction(parse.json) { implicit rs =>
    rs.request.body.\("transaction")(0).validate[Transaction].map { t =>
      transactions.insert(Transaction(t.T_parentRadinGroupID, t.T_creatorID, t.T_debitorID, t.T_amount, t.T_currency, t.T_dateTime, t.T_purpose, t.T_type))
    }
    val lastid = transactions.map(_.tid).max.run
    Logger.info("New Transaction ID : " + lastid)
    val lastT = transactions.list.filter(_.T_ID  == lastid)
    val jsonValue: Seq[(String, JsValue)] = List(("transaction", toJson(lastT)))
    val jsonResponse: JsObject = JsObject(jsonValue)
    Logger.info("New Transaction response : " + jsonResponse.toString)
    Ok(jsonResponse)
  }
  
  
  def newTransactionWithCoeffs = DBAction(parse.json) { implicit rs => 
    rs.request.body.\("transactionWithParticipants") match {
      case JsArray(transactionsWithParticipants) => transactionsWithParticipants foreach {t => 
        t.\("transaction").validate[Transaction].asOpt.map{
          transactions.insert(_)
        }
        val lastid = transactions.map(_.tid).max.run
        t.\("coefficients") match {
          case JsArray(coefficients) => coefficients foreach { coeff =>
            val id = coeff.\("id").as[Int]
            val coefficient = coeff.\("coefficient").as[Int]
            userConcernedByTransactions += ((lastid.get, id, coefficient))
            }
          case _ => BadRequest("wrong coeffs")
        }
      }
      case _ => BadRequest("transactionWithParticipants should be a JSON array")
    }
    Ok(rs.request.body)
  }

  implicit val CoefficientWrites = new Writes[Coefficient] {
    def writes(coeff: Coefficient) = Json.obj(
      "id" -> coeff.id,
      "coefficient" -> coeff.coefficient)
  }

  implicit val TransactionWithParticipantsWrites = new Writes[TransactionWithParticipants] {
    def writes(twp: TransactionWithParticipants) = Json.obj(
      "transaction" -> toJson(twp.transaction),
      "coefficients" -> twp.coefficients)
  }

  implicit val TransactionWithParticipantsListWrites = new Writes[TransactionsWithCoeffs] {
    def writes(list: TransactionsWithCoeffs) = Json.obj(
      "transactionWithParticipants" -> list.all)
  }

  def query2twp(transactions: List[(Transactions#TableElementType, List[database.Tables.UserConcernedByTransaction#TableElementType])]): TransactionsWithCoeffs = {
    val all = transactions.map { elem => TransactionWithParticipants(elem._1.asInstanceOf[Transaction], elem._2.map(x => Coefficient(x._1, x._2, x._3))) }
    return TransactionsWithCoeffs(all)
  }

  def getTransactionsWithCoeffsForGroup(rgid: Int) = DBAction { implicit rs =>
    val x = (for {
      transaction <- transactions.list.filter { _.T_parentRadinGroupID == rgid }
      coefficients <- userConcernedByTransactions.list.filter { _._1 == transaction.T_ID.get }
    } yield (transaction, coefficients)).groupBy(_._1).mapValues(x => x.map { elem => elem._2 }).toList
    val transactionsWithCoeffsForGroup = query2twp(x)
    val jsonValue = toJson(transactionsWithCoeffsForGroup)
    Ok(jsonValue)
  }

  def getAllTransactions = DBAction { implicit rs =>
    Ok(toJson(transactions.list))
  }

  implicit val userFormat = Json.format[User]

  def contentOfJsArray(json: JsValue): Option[Seq[JsValue]] = json match {
    case JsArray(arr) => Some(arr)
    case _ => None
  }

  
  
  /**
   * @author simonchelbc
   * @param JsonArray containing JsObject in format of database.Tables.User
   * @return a  webpage listing all users that have changed, to see if changes have been taken into account
   * What it computes: modifies the entries in Users table with same ID as the one sent in the request in JSON
   *  with what each of the value in the request contains
 * 
 */
def modifyUsers = DBAction(parse.json) { implicit rs => 
    def updateUsers: (String, Seq[JsValue]) = {
	    var errorsLog: String = ""
			var modifiedUsers: Seq[JsValue] = Seq()
      rs.body.\("user") match {
        case JsArray(jsonValues) => jsonValues foreach { jsonValue => 
           jsonValue.validate[User].asOpt.foreach { userNewState =>  
              val userToUpdate = users.filter { _.U_ID === userNewState.U_ID }
              val listOfOneUser = userToUpdate.list
              if (!listOfOneUser.isEmpty) {
                userToUpdate.update(userNewState) 
                modifiedUsers = modifiedUsers :+ jsonValue
              } else errorsLog += "the following user doesn't exist in the database: " + Json.prettyPrint(JsObject(Seq(("user", jsonValue)))) + "\n"
         }  
        }
        (errorsLog, modifiedUsers)
        case _ => (errorsLog + "Request body doesn't contain a JSON array of users \n", modifiedUsers)
      }
    }
    val (errorsLog, modifiedUsers) = updateUsers
    val modifedUsersAsJsObject = JsObject(Seq(("user", JsArray(modifiedUsers))))
    if (errorsLog.isEmpty()) {
      Ok(modifedUsersAsJsObject)
    } else BadRequest(errorsLog + "the following has been modified \n " + Json.prettyPrint(modifedUsersAsJsObject))
  }


  def newUser = DBAction(parse.json) { implicit rs =>
    Logger.info("New user request : " + rs.request.toString + " " + rs.request.body.toString)
    rs.request.body.\("user")(0).validate[User].map { user =>
      val newuser = User(user.U_firstName, user.U_lastName, user.U_username, user.U_password, user.U_email, user.U_address, user.U_iban, user.U_bicSwift, user.U_picture)
      Logger.info("New user sent : " + user.toString())
      if (users.list.filter(_.U_username == user.U_username).isEmpty) {
        users.insert(newuser)
      }
    }
    val lastid = users.map(_.U_ID).max.run
    if ((toJson(users.list.filter(_.U_ID == lastid))).\\("U_username").head.equals(rs.request.body.\("user")(0).\("U_username"))) {
      Logger.info("New user ID : " + lastid)
      val lastuser = toJson(users.list.filter(_.U_ID == lastid))
      val jsonValue: Seq[(String, JsValue)] = List(("user", lastuser))
      val jsonResponse: JsObject = JsObject(jsonValue)
      Logger.info("New user response : " + jsonResponse.toString)
      Ok(jsonResponse)
    } else {
      Logger.info("New user : username already exists " + rs.request.body.\("user")(0).\\("U_username").head)
      BadRequest("username already exists")
    }
  }

  def login(username: String) = DBAction(parse.json) { implicit rs =>
    Logger.info("Login request : " + rs.request.toString + " " + rs.request.body.toString + " " + rs.request.rawQueryString)
    val user = toJson(users.list.filter(_.U_username == username))
    val password = rs.request.body.\("password")
    val userPass = user.\\("U_password").head
    if (user.\\("U_password").length == 1 && (userPass).equals(password)) {
      Logger.info("Logged in !")
      val jsonValue: Seq[(String, JsValue)] = List(("user", toJson(user)))
      Ok(JsObject(jsonValue))
    } else {
      Logger.info("KO     " + password + "    " + user.\\("U_password").head.as[String])
      BadRequest("KO")
    }
  }

  def userListResult(implicit session: Session) = Ok(JsObject(List(("user", toJson(users.list)))))

  def userList = DBAction { implicit rs =>
    Ok(JsObject(List(("user", toJson(users.list)))))
    userListResult
  }
  //return list of all users

  def addUsertoRadinGroup(rgid: Int) = DBAction(parse.json) { implicit rs =>
    var addedUser: User = null
    rs.request.body.\("user")(0).validate[User].map { user =>
      addedUser = user
      memberInRadins += ((user.U_ID.get, rgid, "", 0, ""))
    }
    Logger.info("User " + addedUser.U_ID + " has been added to RadinGroup " + rgid)
    Ok(rs.request.body)
  }
  
  def removeUserFromRadinGroup(uid: Int, rgid: Int) = DBAction { implicit rs =>
    val query1 = memberInRadins.filter(_.MRuid === uid).filter(_.MRrid === rgid)
    val delete = (query1).delete
    Ok
  }

  def getUserById(uid: Int) = DBAction { implicit rs =>
    Ok(JsObject(List(("user", toJson(users.filter { _.U_ID === uid }.list)))))
  }

  // a sample action using an authorization implementation
  def onlyFacebook = SecuredAction(WithProvider("facebook")) { implicit request =>
    Ok("You can see this because you logged in using Facebook")
  }

  def onlyGoogle = SecuredAction(WithProvider("google")) { implicit request =>
    Ok("You can see this because you logged in using Google")
  }

  def linkResult = SecuredAction { implicit request =>
    Ok(views.html.linkResult(request.user))
  }

  /**
   * Sample use of SecureSocial.currentUser. Access the /current-user to test it
   */
  def currentUser = Action.async { implicit request =>
    import play.api.libs.concurrent.Execution.Implicits._
    SecureSocial.currentUser[DemoUser].map { maybeUser =>
      val userId = maybeUser.map(_.main.userId).getOrElse("unknown")
      Ok(s"Your id is $userId")
    }
  }

  //  lazy val radinGroups = TableQuery[RadinGroups]
  implicit val radinGroupFormat = Json.format[RadinGroup]

  def jsonFindAll = DBAction { implicit rs =>
    val jsonValue: Seq[(String, JsValue)] = List(("radinGroup", toJson(radinGroups.list)))
    val jsonResponse: JsObject = JsObject(jsonValue)
    Ok(jsonResponse)
  }

  def newRadinGroup = DBAction(parse.json) { implicit rs =>
    Logger.info("New RadinGroup request : " + rs.request.toString + " " + rs.request.body.toString)
    rs.request.body.\("radinGroup")(0).validate[RadinGroup].map { rg =>
      val newRG = RadinGroup(rg.RG_name, rg.RG_creationDate, rg.RG_description, rg.RG_avatar)
      Logger.info("New RadinGroup sent : " + newRG.toString())
      radinGroups.insert(newRG)
    }
    val lastid = radinGroups.map(_.rid).max.run
    Logger.info("New RadinGroup ID : " + lastid)
    val lastRG = radinGroups.list.filter(_.RG_ID == lastid)
    val jsonValue: Seq[(String, JsValue)] = List(("radinGroup", toJson(lastRG)))
    val jsonResponse: JsObject = JsObject(jsonValue)
    Logger.info("New RadinGroup response : " + jsonResponse.toString)
    Ok(jsonResponse)
  }

  def getRadinGroupsForUser(uid: Int) = DBAction { implicit rs =>
    val radinGroupsForUser = for {
      memberInRadin <- memberInRadins.list.filter(_._1  == uid)
      radinGroupForUser <- radinGroups.list.filter(_.RG_ID.get  == memberInRadin._2)
    } yield radinGroupForUser
    val jsonValue = JsObject(List(("radinGroup", toJson(radinGroupsForUser))))
    Ok(jsonValue)
  }

  def getUsersInRG(rgid: Int) = DBAction { implicit rs =>
    val x = memberInRadins.list.filter(_._2 == rgid)
    val y = for {
      a <- x
      b <- users.list.filter(_.U_ID.get == a._1)
    } yield (b)
    Logger.info("Get users in RG json of users : " + y.toString)
    val jsonFirst = toJson(y).as[JsArray]
    Logger.info("Get users in RG jsonArray : " + jsonFirst.toString)
    val jsonValue = List(("user", jsonFirst))
    val jsonResponse = JsObject(jsonValue)
    Ok(jsonResponse)
  }

  implicit val userRelationShipFormat = Json.format[UserRelationship]

  def newUserRelationship = DBAction(BodyParsers.parse.json) { implicit rs =>
    rs.request.body.validate[UserRelationship].map { rg =>
      userRelationships.insert(rg)
      Ok(toJson(rg))
    }.getOrElse(BadRequest("invalid json"))
  }
  
  def newUserRelationshipForUserFromUsername(uid: Int, username: String) = DBAction { implicit rs =>
    val newFriendID = users.list.filter(_.U_username == username).head.U_ID
    if (newFriendID.isDefined) {
       userRelationships.insert(UserRelationship(uid, newFriendID.get, 1))
       Ok(JsObject(List(("user", toJson(users.list.filter(_.U_ID.get == uid))))))
    } else {
      BadRequest("username doesn't exists")
    }
    
  }

  /**
   * @author simonchelbc
   * @param sID, U_ID of a user contained in Users table
   * @return friends of User with U_ID equal to sID
   */
  def getFriendsOfUserWithID(sID: Int) = DBAction { implicit rs =>
    val friendsOfSID = for {
      relation <- userRelationships.filter { _.uidSource === sID }
      user <- users.filter { _.U_ID === relation.uidTarget }
    } yield user
    val jsonValue: Seq[(String, JsValue)] = List(("user", toJson(friendsOfSID.list)))
    Ok(JsObject(jsonValue))
  }

  def getUserRelationships = DBAction { implicit rs =>
    val jsonValue: Seq[(String, JsValue)] = List(("userRelationship", toJson(userRelationships.list)))
    Ok(JsObject(jsonValue))
  }
}

// An Authorization implementation that only authorizes uses that logged in using twitter
case class WithProvider(provider: String) extends Authorization[DemoUser] {
  def isAuthorized(user: DemoUser, request: RequestHeader) = {
    user.main.providerId == provider
  }

}
