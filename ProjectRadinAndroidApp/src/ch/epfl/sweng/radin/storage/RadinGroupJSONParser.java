/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author timozattol
 * A JSONParser for RadinGroupModel. A "fake" one, which always returns the same thing.
 * It's very ugly but it's just to have something that "works" for now.
 */
public class RadinGroupJSONParser implements JSONParser<RadinGroupModel> {

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.JSONParser#getModelsFromJson(java.util.List)
	 */
	@Override
	public List<RadinGroupModel> getModelsFromJson(JSONObject jsonObject) throws JSONException {
		List<RadinGroupModel> radinGroupModels = new ArrayList<RadinGroupModel>();
		JSONArray jsonArray = jsonObject.getJSONArray("user");
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/mm/dd HH/mm");


		for (int i = 0; i < jsonArray.length(); i++) {

			JSONObject jsonData = jsonArray.getJSONObject(i);
			RadinGroupModel radinGroupModel;
			
			if (jsonData.has("RG_MASTER_RID")) {

				radinGroupModel = new RadinGroupModel(
						jsonData.getInt("RG_ID"),
						dtf.parseDateTime(jsonData.getString("RG_CREATION_DATE")),
						jsonData.getString("RG_NAME"),
						jsonData.getString("RG_DESCRIPTION"),
						jsonData.getString("RG_AVATAR"),
						jsonData.getInt("RG_MASTER_RID"));

				if (jsonData.has("RG_END_DATE")) {

					radinGroupModel.setRadinGroupEndDateTime(dtf.parseDateTime(jsonData.getString("RG_END_DATE")));
					radinGroupModel.setRadinGroupDeletionDateTime(
							dtf.parseDateTime(jsonData.getString("RG_DELETED_AT")));
				}

			} else {

				radinGroupModel = new RadinGroupModel(
						jsonData.getInt("RG_ID"),
						new DateTime(jsonData.getString("RG_CREATION_DATE")),
						jsonData.getString("RG_NAME"),
						jsonData.getString("RG_DESCRIPTION"),
						jsonData.getString("RG_AVATAR"));

				if (jsonData.has("RG_END_DATE")) {

					radinGroupModel.setRadinGroupEndDateTime(dtf.parseDateTime(jsonData.getString("RG_END_DATE")));
					radinGroupModel.setRadinGroupDeletionDateTime(
							dtf.parseDateTime(jsonData.getString("RG_DELETED_AT")));
				}
			}
			
			radinGroupModels.add(radinGroupModel);
		}

		//        radinGroupModels.add(new RadinGroupModel(0, DateTime.now(), "Group 1", 
		//                "A cool group", "img/avatar1.png"));
		return radinGroupModels;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.JSONParser#getJsonFromModels(java.util.List)
	 */
	@Override
	public JSONObject getJsonFromModels(List<RadinGroupModel> modelList) throws JSONException {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/mm/dd HH/mm");

		for (RadinGroupModel radinGroupModel : modelList) {

			JSONObject jsonData = new JSONObject();

			if (radinGroupModel.hasMasterID()) {
				jsonData.put("RG_MASTER_RID", true);
			}

			if (radinGroupModel.getRadinGroupEndDateTime() != null) {
				jsonData.put("RG_END_DATE", radinGroupModel.getRadinGroupEndDateTime().toString(dtf));
			}

			if (radinGroupModel.getRadinGroupDeletionDateTime() != null) {
				jsonData.put("RG_DELETED_AT", radinGroupModel.getRadinGroupDeletionDateTime().toString(dtf));
			}

			jsonData.put("_RID", radinGroupModel.getRadinGroupID());
			jsonData.put("RG_NAME", radinGroupModel.getRadinGroupName());
			jsonData.put("RG_CREATION_DATE", radinGroupModel.getGroupCreationDateTime().toString(dtf));
			jsonData.put("RG_DESCRIPTION", radinGroupModel.getGroupDescription());
			jsonData.put("RG_GROUP", "group radin"); // TODO what is group field?
			jsonData.put("RG_AVATAR", radinGroupModel.getAvatar());

			jsonArray.put(jsonData);
		}

		json.put("radinGroup", jsonArray);

		return json;

		//	try {
		//		json.put("_RID", "0");
		//		json.put("RG_NAME", "Group 1");
		//		json.put("RG_CREATION_DATE", "2014/01/01/00/00");
		//		json.put("RG_DESCRIPTION", "A cool group");
		//		json.put("RG_GROUP", "group radin"); // TODO what is group field?
		//		json.put("RG_MASTER_RID", "10");
		//		json.put("RG_AVATAR", "img/avatar1.png");
		//		json.put("RG_END_DATE", "2014/01/01/01/01");
		//		json.put("RG_DELETED_AT", "2014/01/01/01/02");
		//
		//	} catch (JSONException e) {
		//
		//	}


	}

}
