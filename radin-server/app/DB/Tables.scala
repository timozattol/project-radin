package DB

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ ProvenShape, ForeignKeyQuery }

class RadinGroup(tag: Tag) extends Table[(Int, String, String, String, String, Int, String, String, String)](tag, "RADINGROUP") {
  def rid: Column[Int] = column[Int]("RID", O.PrimaryKey, O.AutoInc, O.NotNull)
  def RGname: Column[String] = column[String]("RG_NAME", O.NotNull)
  def RGstartDate: Column[String] = column[String]("RG_STARTDATE", O.NotNull)
  def RGdescription: Column[String] = column[String]("RG_DESCRIPTION")
  def RGgroup: Column[String] = column[String]("RG_GROUP")
  def RGmasterRID: Column[Int] = column[Int]("RG_MASTERRID")
  def RGavatar: Column[String] = column[String]("RG_AVATAR")
  def RGendDate: Column[String] = column[String]("RG_ENDDATE")
  def RGdeletedAt: Column[String] = column[String]("RG_DELETEDAT")

  def * = (rid, RGname, RGstartDate, RGdescription, RGgroup, RGmasterRID, RGavatar, RGendDate, RGdeletedAt)
}

class Transaction(tag: Tag) extends Table[(Int, Int, Int, Int, Int, String, Int, String, Int, String, Int, String)](tag, "TRANSACTION") {

  def tid: Column[Int] = column[Int]("TID", O.PrimaryKey, O.AutoInc, O.NotNull)
  def rid: Column[Int] = column[Int]("RID", O.NotNull)
  def TaddedBy: Column[Int] = column[Int]("T_ADDEDBY", O.NotNull)
  def Tdebitor: Column[Int] = column[Int]("T_DEBITOR", O.NotNull)
  def Tamount: Column[Int] = column[Int]("T_AMOUNT", O.NotNull)
  def Tpurpose: Column[String] = column[String]("T_PURPOSE", O.NotNull)
  def Tcurrency: Column[Int] = column[Int]("T_CURRENCY", O.NotNull)
  def Tdate: Column[String] = column[String]("T_DATE", O.NotNull)
  def Ttype: Column[Int] = column[Int]("T_TRANSACTIONTYPE", O.NotNull)
  def Tjustificative: Column[String] = column[String]("T_JUSTIFICATIVE")
  def TmasterTID: Column[Int] = column[Int]("T_MASTERTID")
  def TdeletedAt: Column[String] = column[String]("T_DELETEDAT")
  
  def * = (tid, rid, TaddedBy, Tdebitor, Tamount, Tpurpose, Tcurrency, Tdate, Ttype, Tjustificative, TmasterTID, TdeletedAt)
}