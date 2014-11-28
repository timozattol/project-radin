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
import database._

class Application(override implicit val env: RuntimeEnvironment[DemoUser]) extends securesocial.core.SecureSocial[DemoUser] {

  def firstAction = DBAction { implicit rs =>
    users.ddl.create
    radinGroups.ddl.create
    Ok("done")
  }
  
  def getTransactionsForGroup(rgid: String) = TODO
  
  def getTransactionsWithCoeffsForGroup(rgid: String) = TODO

  lazy val users = TableQuery[Users]
  implicit val userFormat = Json.format[User]

  def newUser = DBAction(BodyParsers.parse.json) { implicit rs =>
    val nuser = rs.request.body.validate[User]
    nuser.fold(
      errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
      },
      user => {
    	val newuser = User(user.Uname, user.Upassword, user.Uiban, user.Ulanguage, user.Uaddress, user.Uoptions, user.Uavatar, user.UdeletedAt)
        users.insert(newuser)
        
        val lastid = users.map(_.uid).max.run
        lazy val userlist = users.list
        lazy val lastuser = userlist.filter(_.U_ID == lastid)
        
        Ok(toJson(lastid))
      })
  }
  
  def userList = DBAction { implicit rs =>
    Ok(toJson(users.list))
  }
  //return list of all users

  def insertUser = DBAction { implicit rs =>
    users.insert(User("username", "myPassword", "no iban", "ch-fr", "EPFL", 0, "", ""))
    Ok("Done!")
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

  lazy val radinGroups = TableQuery[RadinGroups]
  implicit val radinGroupFormat = Json.format[RadinGroup]

  def jsonFindAll = DBAction { implicit rs =>
    Ok(toJson(radinGroups.list))
  }

  def jsonInsert = DBAction(parse.json) { implicit rs =>
    rs.request.body.validate[RadinGroup].map { rg =>
      radinGroups.insert(rg)
      Ok(toJson(rg))
    }.getOrElse(BadRequest("invalid json"))
  }
  
}

// An Authorization implementation that only authorizes uses that logged in using twitter
case class WithProvider(provider: String) extends Authorization[DemoUser] {
  def isAuthorized(user: DemoUser, request: RequestHeader) = {
    user.main.providerId == provider
  }

}
