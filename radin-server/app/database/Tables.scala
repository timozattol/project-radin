package database

//import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ ProvenShape, ForeignKeyQuery }
import scala.slick.driver.SQLiteDriver.simple._

import play.api.mvc.BodyParsers._
import play.api.libs.json.Json
import play.api.libs.json.Json._

class Tables {

  lazy val radinGroups = TableQuery[RadinGroups]

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
  lazy val transactions = TableQuery[Transaction]

  lazy val users = TableQuery[Users]

  class UserRelationships(tag: Tag) extends Table[(Int, Int, Int)](tag, "USER_RELATIONSHIP") {

    def uidSource: Column[Int] = column[Int]("UID_SOURCE", O.NotNull)
    def uidTarget: Column[Int] = column[Int]("UID_TARGET", O.NotNull)
    def URrelation: Column[Int] = column[Int]("UR_RELATION", O.NotNull)

    def sourceUser = foreignKey("SOURCE_USER", uidSource, users)(_.U_ID)
    def targetUser = foreignKey("TARGET_USER", uidTarget, users)(_.U_ID)
    def URpk = primaryKey("URpk", (uidSource, uidTarget))

    def * = (uidSource, uidTarget, URrelation)
  }
  lazy val userRelationships = TableQuery[UserRelationship]

  class MemberInRadin(tag: Tag) extends Table[(Int, Int, String, Int, String)](tag, "MEMBER_IN_RADIN") {

    def MRuid: Column[Int] = column[Int]("UID", O.NotNull)
    def MRrid: Column[Int] = column[Int]("RID", O.NotNull)
    def MRcreationDate: Column[String] = column[String]("MR_CREATION_DATE", O.NotNull)
    def MRpermission: Column[Int] = column[Int]("MR_PERMISSION", O.NotNull)
    def MRdeletedAt: Column[String] = column[String]("MR_DELETED_AT")

    def memberUser = foreignKey("MEMBER_USER", MRuid, users)(_.U_ID)
    def memberRadinGroup = foreignKey("MEMBER_RG", MRrid, radinGroups)(_.rid)
    def MRpk = primaryKey("MRpk", (MRuid, MRrid))

    def * = (MRuid, MRrid, MRcreationDate, MRpermission, MRdeletedAt)
  }
  lazy val memberInRadins = TableQuery[MemberInRadin]

  class GroupPreset(tag: Tag) extends Table[(Int, Int, String, String)](tag, "GROUP_PRESET") {

    def GPgid: Column[Int] = column[Int]("GID", O.PrimaryKey, O.NotNull)
    def GPrid: Column[Int] = column[Int]("RID", O.NotNull)
    def GPname: Column[String] = column[String]("GP_NAME", O.NotNull)
    def GPdeletedAt: Column[String] = column[String]("GP_DELETED_AT")

    def presetRadinGroup = foreignKey("PRESET_RG", GPrid, radinGroups)(_.rid)

    def * = (GPgid, GPrid, GPname, GPdeletedAt)
  }
  lazy val groupPresets = TableQuery[GroupPreset]

  class MemberInGroup(tag: Tag) extends Table[(Int, Int, String)](tag, "MEMBER_IN_GROUP") {

    def MGgid: Column[Int] = column[Int]("GID", O.NotNull)
    def MGuid: Column[Int] = column[Int]("UID", O.NotNull)
    def MGdeletedAt: Column[String] = column[String]("MB_DELETED_AT")

    def groupPreset = foreignKey("GROUP_GP", MGgid, groupPresets)(_.GPgid)
    def groupUser = foreignKey("GROUP_U", MGuid, users)(_.U_ID)
    def MGpk = primaryKey("MGpk", (MGgid, MGuid))

    def * = (MGgid, MGuid, MGdeletedAt)
  }
  lazy val memberInGroups = TableQuery[MemberInGroup]

  class Invitation(tag: Tag) extends Table[(Int, Int, String, String, Int, String)](tag, "INVITATION") {

    def Iuid: Column[Int] = column[Int]("UID", O.NotNull)
    def Irid: Column[Int] = column[Int]("RID", O.NotNull)
    def IstartDate: Column[String] = column[String]("I_START_DATE", O.NotNull)
    def IacceptedDate: Column[String] = column[String]("I_ACCEPTED_DATE")
    def Itargetuid: Column[Int] = column[Int]("I_TARGET_UID", O.NotNull)
    def IdeletedAt: Column[String] = column[String]("I_DELETED_AT")

    def invitationUser = foreignKey("INVITATION_U", Iuid, users)(_.U_ID)
    def invitationRadinGroup = foreignKey("INVITATION_RG", Irid, radinGroups)(_.rid)
    def invitationTarget = foreignKey("INTIVATION_TU", Itargetuid, users)(_.U_ID)
    def Ipk = primaryKey("Ipk", (Iuid, Irid))

    def * = (Iuid, Irid, IstartDate, IacceptedDate, Itargetuid, IdeletedAt)
  }
  lazy val invitations = TableQuery[Invitation]

  class UserConcernedByTransaction(tag: Tag) extends Table[(Int, Int, Int)](tag, "USER_CONCERNED_BY_TRANSACTION") {

    def UCtid: Column[Int] = column[Int]("TID", O.NotNull)
    def UCuid: Column[Int] = column[Int]("UID", O.NotNull)
    def UCcoefficient: Column[Int] = column[Int]("UC_COEFFICIENT", O.NotNull)

    def concernsTransaction = foreignKey("CONCERNED_TID", UCtid, transactions)(_.tid)
    def concernsUser = foreignKey("CONCERNED_UID", UCuid, users)(_.U_ID)
    def UCpk = primaryKey("UCpk", (UCtid, UCuid))

    def * = (UCtid, UCuid, UCcoefficient)
  }
  lazy val userConcernedByTransactions = TableQuery[UserConcernedByTransaction]

}

case class RadinGroup(RG_name: String, RG_creationDate: String, RG_description: String, RG_masterID: Int, RG_avatar: String, RG_deletedAt: String, RG_ID: Option[Int] = None)

class RadinGroups(tag: Tag) extends Table[RadinGroup](tag, "RADINGROUP") {
  def rid: Column[Int] = column[Int]("RID", O.PrimaryKey, O.AutoInc)
  def RGname: Column[String] = column[String]("RG_NAME", O.NotNull)
  def RGstartDate: Column[String] = column[String]("RG_STARTDATE", O.NotNull)
  def RGdescription: Column[String] = column[String]("RG_DESCRIPTION")
  def RGmasterRID: Column[Int] = column[Int]("RG_MASTERRID")
  def RGavatar: Column[String] = column[String]("RG_AVATAR")
  def RGdeletedAt: Column[String] = column[String]("RG_DELETEDAT")

  def * = (RGname, RGstartDate, RGdescription, RGmasterRID, RGavatar, RGdeletedAt, rid.?) <> (RadinGroup.tupled, RadinGroup.unapply)
}

case class User(U_firstName: String, U_lastName: String, U_username: String, U_password: String, U_email: String, U_address: String, U_iban: String, U_bicSwift: String, U_avatar: String, U_deletedAt: String, U_ID: Option[Int] = None)

class Users(tag: Tag) extends Table[User](tag, "USER") {

  def U_firstName: Column[String] = column[String]("U_NAME", O.NotNull)
  def U_lastName: Column[String] = column[String]("U_LAST", O.NotNull)
  def U_username: Column[String] = column[String]("U_USERNAME", O.NotNull)
  def U_password: Column[String] = column[String]("U_PASSWORD", O.NotNull)
  def U_email: Column[String] = column[String]("U_IBAN")
  def U_address: Column[String] = column[String]("U_LANGUAGE", O.NotNull)
  def U_iban: Column[String] = column[String]("U_ADDRESS")
  def U_bicSwift: Column[String] = column[String]("U_OPTIONS", O.NotNull)
  def U_avatar: Column[String] = column[String]("U_AVATAR")
  def U_deletedAt: Column[String] = column[String]("U_DELETED_AT")
  def U_ID: Column[Int] = column[Int]("UID", O.PrimaryKey, O.AutoInc, O.NotNull)

  def * = (U_firstName, U_lastName, U_username, U_password, U_email, U_address, U_iban, U_bicSwift, U_avatar, U_deletedAt, U_ID.?) <> (User.tupled, User.unapply)
}