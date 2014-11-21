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

  lazy val users = TableQuery[Users]
  implicit val userFormat = Json.format[User]

  def newUser = DBAction(BodyParsers.parse.json) { implicit rs =>
    val nuser = rs.request.body.validate[User]
    nuser.fold(
      errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(errors)))
      },
      user => {
        users.insert(user)
        
        val lastid = users.map(_.uid).max.run
        val userlist = users.list
        val lastuser = userlist.filter(_.U_ID == lastid)
        
        Ok(toJson(lastuser))
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

  // example Json value
  val json: JsValue = Json.parse("""
      {"radinGroup":[
   {"RG_ID": "0",
    "RG_name": "Scrum expenses",
    "RG_creationDate": "2014/01/01 00/00",
    "RG_description": "A cool group",
    "RG_avatar": "img/avatar1.png",
    "RG_deletedAt": "2014/01/01 00/00"
    },
    {"RG_ID": "0",
    "RG_name": "Coloc",
    "RG_creationDate": "2014/01/01 00/00",
    "RG_description": "The coolest group",
    "RG_avatar": "img/avatar1.png",
    "RG_deletedAt": "2014/01/01 00/00"
    },
    {"RG_ID": "0",
    "RG_name": "Sweng-Swag",
    "RG_creationDate": "2014/01/01 00/00",
    "RG_description": "A cooler group",
    "RG_avatar": "img/avatar1.png",
    "RG_deletedAt": "2014/01/01 00/00"
    }]
   }
      """)

  val authJson: JsValue = Json.parse("""
       {"radinGroup":[
   {"RG_ID": "1",
    "RG_name": "Auth Group 1",
    "RG_creationDate": "2014/01/01 00/00",
    "RG_description": "A cooler group",
    "RG_avatar": "img/avatar1.png",
    "RG_deletedAt": "2014/01/01 00/00"
    }]
   }
       """)

  def myGroups = Action {
    Ok(json)
  }

  def authGroups = SecuredAction { implicit request =>
    Ok(authJson)
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
