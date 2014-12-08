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

}