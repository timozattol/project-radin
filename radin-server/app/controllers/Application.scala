package controllers

import securesocial.core._
import service.DemoUser
import play.api.mvc.{ Action, RequestHeader }
import play.api.libs.json._

class Application(override implicit val env: RuntimeEnvironment[DemoUser]) extends securesocial.core.SecureSocial[DemoUser] {
  def index = SecuredAction { implicit request =>
    Ok(views.html.index(request.user.main))
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

  // An Authorization implementation that only authorizes uses that logged in using twitter
  case class WithProvider(provider: String) extends Authorization[DemoUser] {
    def isAuthorized(user: DemoUser, request: RequestHeader) = {
      user.main.providerId == provider
    }

  }

}