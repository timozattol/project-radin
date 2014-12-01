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
      
    } finally {
      users.insert(User("name", "lastname", "username", "password", "email", "address", "iban", "bicSwift", "", ""))
      users.insert(User("name2", "lastname2", "username2", "password2", "email2", "address2", "iban2", "bicSwift2", "", ""))
      radinGroups.insert(RadinGroup("radinGroup", "2014/11/28 10/11", "description bidon", 0, "", ""))
      userRelationships.insert((UserRelationship(10, 7, 0)))
    }

    Ok("done")
  }

  lazy val userRelationships = TableQuery[UserRelationships]
  lazy val users = TableQuery[Users]
  
  def getTransactionsForGroup(rgid: String) = TODO

  def getTransactionsWithCoeffsForGroup(rgid: String) = TODO
  implicit val userFormat = Json.format[User]

  def newUser = DBAction(BodyParsers.parse.json) { implicit rs =>
    val nuser = rs.request.body.validate[User]
    nuser.fold(
      errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
      },
      user => {
        val newuser = User(user.U_firstName, user.U_lastName, user.U_username, user.U_password, user.U_email, user.U_address, user.U_iban, user.U_bicSwift, user.U_avatar, user.U_deletedAt)
        users.insert(newuser)

        val lastid = users.map(_.U_ID).max.run
        lazy val userlist = users.list
        lazy val lastuser = userlist.filter(_.U_ID == lastid)

        Ok(toJson(lastid))
      })
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

  lazy val radinGroups = TableQuery[RadinGroups]
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

  implicit val userRelationShipFormat = Json.format[UserRelationship]
  def newUserRelationship = DBAction(BodyParsers.parse.json) { implicit rs => 
  	  rs.request.body.validate[UserRelationship].map { rg => 
  	    userRelationships.insert(rg)
  	    Ok(toJson(rg))
  	}.getOrElse(BadRequest("invalid json"))
  }
  
  //get all friends of a user with UID sID, and returns the result in JSON
  def getFriendsOfUserWithID(sID: Int) = DBAction { implicit rs => 
    val friendsOfSID = userRelationships.filter { _.uidSource === sID }
    val jsonValue: Seq[(String, JsValue)] = List(("userRelationship", toJson(friendsOfSID.list)))
    Ok(JsObject(jsonValue))
  }
}


// An Authorization implementation that only authorizes uses that logged in using twitter
case class WithProvider(provider: String) extends Authorization[DemoUser] {
  def isAuthorized(user: DemoUser, request: RequestHeader) = {
    user.main.providerId == provider
  }

}
