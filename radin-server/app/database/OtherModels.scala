package database

import database.Tables._
import play.api.libs.json.JsObject
import play.api.db.slick._
import scala.slick._
import scala.slick.driver.SQLiteDriver.simple._
import play.api.libs.json.Json
import play.api.libs.json.JsValue

object OtherModels {
  
  case class TransactionsWithCoeffs(all: Seq[TransactionWithParticipants])
  
  case class TransactionWithParticipants(transaction: Transaction, coefficients: Seq[Coefficient])
  
  case class Coefficient(tid: Int, id: Int, coefficient: Int)
  
  case class UserWithoutPassword(U_firstName: String, U_lastName: String, U_username: String, U_email: String,
      U_address: String, U_iban: String, U_bicSwift: String, U_picture: String, U_ID: Option[Int] = None) {
    def this(user: User) = this(user.U_firstName, user.U_lastName,
        user.U_username, user.U_email, user.U_address, user.U_iban, user.U_bicSwift, user.U_picture, user.U_ID)
  }
}