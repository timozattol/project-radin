/**
 * 
 */
package ch.epfl.sweng.radin.storage.parsers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.radin.storage.RadinGroupModel;

/**
 * @author topali2
 * A JSONParser for RadinGroupModel.
 */
public class RadinGroupJSONParser implements JSONParser<RadinGroupModel> {

	private DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd HH/mm");
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.JSONParser#getModelsFromJson(java.util.List)
	 */
	@Override
	public List<RadinGroupModel> getModelsFromJson(JSONObject jsonObject) throws JSONException {
		List<RadinGroupModel> radinGroupModels = new ArrayList<RadinGroupModel>();
		JSONArray jsonArray = jsonObject.getJSONArray("radinGroup");
		


		for (int i = 0; i < jsonArray.length(); i++) {

			JSONObject jsonData = jsonArray.getJSONObject(i);
			RadinGroupModel radinGroupModel;
			
			if (jsonData.has("RG_masterID")) {

				radinGroupModel = new RadinGroupModel(
						jsonData.getInt("RG_ID"),
						dtf.parseDateTime(jsonData.getString("RG_creationDate")),
						jsonData.getString("RG_name"),
						jsonData.getString("RG_description"),
						jsonData.getString("RG_avatar"),
						jsonData.getInt("RG_masterID"));

			} else {

				radinGroupModel = new RadinGroupModel(
						jsonData.getInt("RG_ID"),
						dtf.parseDateTime(jsonData.getString("RG_creationDate")),
						jsonData.getString("RG_name"),
						jsonData.getString("RG_description"),
						jsonData.getString("RG_avatar"));

			}
			
			radinGroupModels.add(radinGroupModel);
		}

		return radinGroupModels;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.JSONParser#getJsonFromModels(java.util.List)
	 */
	@Override
	public JSONObject getJsonFromModels(List<RadinGroupModel> modelList) throws JSONException {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		for (RadinGroupModel radinGroupModel : modelList) {

			JSONObject jsonData = new JSONObject();

			if (radinGroupModel.hasMasterID()) {
				jsonData.put("RG_masterID", radinGroupModel.getMasterID());
			}

			jsonData.put("RG_ID", radinGroupModel.getRadinGroupID());
			jsonData.put("RG_name", radinGroupModel.getRadinGroupName());
			jsonData.put("RG_creationDate", radinGroupModel.getGroupCreationDateTime().toString(dtf));
			jsonData.put("RG_description", radinGroupModel.getGroupDescription());
			jsonData.put("RG_avatar", radinGroupModel.getAvatar());

			jsonArray.put(jsonData);
		}

		json.put("radinGroup", jsonArray);

		return json;
	}

}
