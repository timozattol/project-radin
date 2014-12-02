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
import play.api.mvc._
import play.api.Play.current
import play.api.mvc.BodyParsers._
import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.db.slick.DBAction
import play.api.mvc.BodyParsers
import play.api.db.slick.DBAction
import play.api.db.slick.DBAction
import database.Tables._

class Application(override implicit val env: RuntimeEnvironment[DemoUser]) extends securesocial.core.SecureSocial[DemoUser] {

  def firstAction = DBAction { implicit rs =>
    try {
      users.ddl.create
      radinGroups.ddl.create
      userRelationships.ddl.create
      transactions.ddl.create
      memberInRadins.ddl.create
    } finally {
      users.insert(User("name", "lastname", "username", "password", "email", "address", "iban", "bicSwift", "", ""))
      users.insert(User("second", "beforeLast", "uname", "mdp", "courriel", "chez moi", "#1", "mybic", "", ""))
      users.insert(User("Joel", "Kaufman", "jojo", "1234", "jojo@epfl.ch", "Monadresse", "iban", "#2", "", ""))
      users.insert(User("Koko", "loco", "koko", "234", "koko@epfl.ch", "kokoAdd", "iban", "#3", "", ""))
      radinGroups.insert(RadinGroup("radinGroup", "2014/11/28 10/11", "description bidon", 0, "", ""))
      transactions.insert(Transaction(1, 1, 1, 100, "CHF", "2014/01/01 00/00", "Buy more jewelleries", "PAYMENT"))
      transactions.insert(Transaction(1, 1, 2, 50, "CHF", "2013/02/01 00/00", "Whatever", "PAYMENT"))
      transactions.insert(Transaction(1, 2, 1, 25, "CHF", "2014/02/01 00/00", "Cool expense", "PAYMENT"))
      transactions.insert(Transaction(1, 2, 2, 150, "CHF", "2014/01/02 00/00", "Cooler expense", "PAYMENT"))
      userRelationships.insert(UserRelationship(1, 2, 10))
      userRelationships.insert(UserRelationship(3, 2 , 11))
      userRelationships.insert(UserRelationship(1, 3 , 12))
      memberInRadins ++= Seq((1, 1, "", 0, ""), (2, 1, "", 0, ""))
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

  def getTransactionsWithCoeffsForGroup(rgid: Int) = TODO

  def getAllTransactions = DBAction { implicit rs =>
    Ok(toJson(transactions.list))
  }

  def newTransaction = DBAction(parse.json) { implicit rs =>
    rs.request.body.validate[Transaction].map { ta =>
      transactions.insert(ta)
      Ok("Ok")
    }.getOrElse(BadRequest("invalid json"))
  }

  //  lazy val users = TableQuery[Users]
  implicit val userFormat = Json.format[User]

  def newUser = DBAction(parse.json) { implicit rs =>
    rs.request.body.\("user")(0).validate[User].map { user =>
      users.insert(user)
    }
    val lastid = users.map(_.U_ID).max.run
    val lastuser = users.list.filter(_.U_ID == lastid)
    val jsonValue: Seq[(String, JsValue)] = List(("user", toJson(lastuser)))
    val jsonResponse: JsObject = JsObject(jsonValue)
    Ok(jsonResponse)
  }

  def login(username: String) = DBAction { implicit rs =>
    val user = toJson(users.list.filter(_.U_username == username))
    val password = rs.request.body.asText.get
    if (user.\\("U_password").length == 1 && user.\\("U_password").head.as[String].equals(password)) {
      Ok("OK")
    } else {
      Ok("KO     " + password + "    " + user.\\("U_password").head.as[String])
    }
  }

  def userList = DBAction { implicit rs =>
    Ok(toJson(users.list))
  }
  //return list of all users

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

  def jsonInsert = DBAction(parse.json) { implicit rs =>
    rs.request.body.validate[RadinGroup].map { rg =>
      radinGroups.insert(rg)
      Ok(toJson(rg))
    }.getOrElse(BadRequest("invalid json"))
  }

  def getRadinGroupsForUser(uid: Int) = TODO

  def getUsersInRG(rgid: Int) = DBAction { implicit rs =>
    val x = memberInRadins.list.filter(_._2 == rgid)
    val y = for {
      a <- x
    } yield toJson(users.list.filter(_.U_ID.get == a._1))
    val jsonValue = List(("user", toJson(y)))
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
  
  //get all friends of a user with UID sID, and returns those friends as a list of Users 
  def getFriendsOfUserWithID(sID: Int) = DBAction { implicit rs => 
    val friendsOfSID = for {
      relation <- userRelationships.filter { _.uidSource === sID }
      user <- users.filter { _.U_ID === relation.uidTarget }
      } yield user
    val jsonValue: Seq[(String, JsValue)] = List(("userRelationship", toJson(friendsOfSID.list)))
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
