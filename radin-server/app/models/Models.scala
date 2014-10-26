package models

class Models {

  case class RadinGroup(RID: Int, RGname: String, RGstartDate: String,
    RGdescription: String, RGgroup: String, RGmasterRID: Int,
    RGavatar: String, RGendDate: String, RGdeletedAt: String);

}