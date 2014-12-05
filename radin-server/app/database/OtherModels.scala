package database

import database.Tables.Transaction

class OtherModels {
  
  case class TransactionWithParticipants(transaction: Transaction, participants: String)

}