package DB

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ ProvenShape, ForeignKeyQuery }

class Tables {

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
  lazy val radinGroups = TableQuery[RadinGroup]

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

  class User(tag: Tag) extends Table[(Int, String, String, String, String, String, Int, String, String)](tag, "USER") {

    def uid: Column[Int] = column[Int]("UID", O.PrimaryKey, O.AutoInc, O.NotNull)
    def Uname: Column[String] = column[String]("U_NAME", O.NotNull)
    def Upassword: Column[String] = column[String]("U_PASSWORD", O.NotNull)
    def Uiban: Column[String] = column[String]("U_IBAN")
    def Ulanguage: Column[String] = column[String]("U_LANGUAGE", O.NotNull)
    def Uaddress: Column[String] = column[String]("U_ADDRESS")
    def Uoptions: Column[Int] = column[Int]("U_OPTIONS", O.NotNull)
    def Uavatar: Column[String] = column[String]("U_AVATAR")
    def UdeletedAt: Column[String] = column[String]("U_DELETED_AT")

    def * = (uid, Uname, Upassword, Uiban, Ulanguage, Uaddress, Uoptions, Uavatar, UdeletedAt)
  }
  lazy val users = TableQuery[User]

  class UserRelationship(tag: Tag) extends Table[(Int, Int, Int)](tag, "USER_RELATIONSHIP") {

    def uidSource: Column[Int] = column[Int]("UID_SOURCE", O.NotNull)
    def uidTarget: Column[Int] = column[Int]("UID_TARGET", O.NotNull)
    def URrelation: Column[Int] = column[Int]("UR_RELATION", O.NotNull)

    def sourceUser = foreignKey("SOURCE_USER", uidSource, users)(_.uid)
    def targetUser = foreignKey("TARGET_USER", uidTarget, users)(_.uid)
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
      
      def memberUser = foreignKey("MEMBER_USER", MRuid, users)(_.uid)
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
    def groupUser = foreignKey("GROUP_U", MGuid, users)(_.uid)
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
    
    def invitationUser = foreignKey("INVITATION_U", Iuid, users)(_.uid)
    def invitationRadinGroup = foreignKey("INVITATION_RG", Irid, radinGroups)(_.rid)
    def invitationTarget = foreignKey("INTIVATION_TU", Itargetuid, users)(_.uid)
    def Ipk = primaryKey("Ipk", (Iuid, Irid))
    
    def * = (Iuid, Irid, IstartDate, IacceptedDate, Itargetuid, IdeletedAt)
  }
  lazy val invitations = TableQuery[Invitation]
  
  class UserConcernedByTransaction(tag: Tag) extends Table[(Int, Int, Int)](tag, "USER_CONCERNED_BY_TRANSACTION") {
    
    def UCtid: Column[Int] = column[Int]("TID", O.NotNull)
    def UCuid: Column[Int] = column[Int]("UID", O.NotNull)
    def UCcoefficient: Column[Int] = column[Int]("UC_COEFFICIENT", O.NotNull)
    
    def concernsTransaction = foreignKey("CONCERNED_TID", UCtid, transactions)(_.tid)
    def concernsUser = foreignKey("CONCERNED_UID", UCuid, users)(_.uid)
    def UCpk = primaryKey("UCpk", (UCtid, UCuid))
    
    def * = (UCtid, UCuid, UCcoefficient)
  }
  lazy val userConcernedByTransactions = TableQuery[UserConcernedByTransaction]
  
}