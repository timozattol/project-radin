package ch.epfl.sweng.radin.storage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author timozattol
 * A JSONParser for UserModel. A "fake" one, which always returns the same thing.
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
					jsonData.getString("firstName"),
					jsonData.getString("lastName"),
					jsonData.getString("email"),
					jsonData.getString("address"),
					jsonData.getString("iban"),
					jsonData.getString("picture"),
					jsonData.getInt("id")));


		}
		//        userModels.add(new UserModel("Alexandra", "Din", "alexandra.din@radin.ch", 
		//                "69 Radin Street, Zurich (CH)", "CH928375340", "img/avatar00001.jpg", 0));
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

			jsonData.put("firstName", userModel.getFirstName());
			jsonData.put("lastName", userModel.getLastName());
			jsonData.put("email", userModel.getEmail());
			jsonData.put("address", userModel.getAddress());
			jsonData.put("iban", userModel.getIban());
			jsonData.put("picture", userModel.getPicture());
			jsonData.put("id", userModel.getId());

			jsonArray.put(jsonData);
		}

		json.put("user", jsonArray);

		return json;

		//        try {
		//        	
		//            json.put("U_ID", 0);
		//            json.put("U_firstName", "Alexandra");
		//            json.put("U_lastName", "Din");
		//            json.put("U_email", "alexandra.din@radin.ch");
		//            json.put("U_address", "69 Radin Street, Zurich (CH)");
		//            json.put("U_iban", "CH928375340");
		//            json.put("U_picture", "img/avatar00001.jpg");            
		//        } catch (JSONException e) {
		//            
		//        }
		//        List<JSONObject> jsonList = new ArrayList<JSONObject>();
		//        jsonList.add(json);
		//        return jsonList;
	}

}
