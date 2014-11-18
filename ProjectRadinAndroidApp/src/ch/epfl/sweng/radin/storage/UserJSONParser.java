package ch.epfl.sweng.radin.storage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author topali2
 * A JSONParser for UserModel.
 * It's very ugly but it's just to have something that "works" for now.
 */
public class UserJSONParser implements JSONParser<UserModel> {

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.JSONParser#getModelsFromJson(java.util.List)
	 */
	@Override
	public List<UserModel> getModelsFromJson(JSONObject jsonObject) throws JSONException {

		List<UserModel> userModels = new ArrayList<UserModel>();
		JSONArray jsonArray = jsonObject.getJSONArray("user");

		for (int i = 0; i < jsonArray.length(); i++) {

			JSONObject jsonData = jsonArray.getJSONObject(i);
			userModels.add(new UserModel(
					jsonData.getString("U_firstName"),
					jsonData.getString("U_lastName"),
					jsonData.getString("U_email"),
					jsonData.getString("U_address"),
					jsonData.getString("U_iban"),
					jsonData.getString("U_bicSwift"),
					jsonData.getString("U_picture"),
					jsonData.getInt("U_ID")));

		}

		return userModels;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.JSONParser#getJsonFromModels(java.util.List)
	 */
	@Override
	public JSONObject getJsonFromModels(List<UserModel> modelList) throws JSONException {

		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		for (UserModel userModel : modelList) {
			JSONObject jsonData = new JSONObject();

			jsonData.put("U_firstName", userModel.getFirstName());
			jsonData.put("U_lastName", userModel.getLastName());
			jsonData.put("U_email", userModel.getEmail());
			jsonData.put("U_address", userModel.getAddress());
			jsonData.put("U_iban", userModel.getIban());
			jsonData.put("U_bicSwift", userModel.getBicSwift());
			jsonData.put("U_picture", userModel.getPicture());
			jsonData.put("U_ID", userModel.getId());

			jsonArray.put(jsonData);
		}

		json.put("user", jsonArray);

		return json;

	}

}
