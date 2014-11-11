package ch.epfl.sweng.radin.storage;

import java.util.ArrayList;
import java.util.List;

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
    public List<UserModel> getModelsFromJson(List<JSONObject> jsonList) {
        List<UserModel> userModels = new ArrayList<UserModel>();
        userModels.add(new UserModel("Alexandra", "Din", "alexandra.din@radin.ch", 
                "69 Radin Street, Zurich (CH)", "CH928375340", "img/avatar00001.jpg", 0));
        return userModels;
    }

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.JSONParser#getJsonFromModels(java.util.List)
     */
    @Override
    public List<JSONObject> getJsonFromModels(List<UserModel> modelList) {
        JSONObject json = new JSONObject();
        try {
            json.put("U_ID", 0);
            json.put("U_firstName", "Alexandra");
            json.put("U_lastName", "Din");
            json.put("U_email", "alexandra.din@radin.ch");
            json.put("U_address", "69 Radin Street, Zurich (CH)");
            json.put("U_iban", "CH928375340");
            json.put("U_picture", "img/avatar00001.jpg");            
        } catch (JSONException e) {
            
        }
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        jsonList.add(json);
        return jsonList;
    }

}
