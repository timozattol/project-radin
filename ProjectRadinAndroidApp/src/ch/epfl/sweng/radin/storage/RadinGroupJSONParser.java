/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
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
    public List<RadinGroupModel> getModelsFromJson(List<JSONObject> jsonList) {
        List<RadinGroupModel> radinGroupModels = new ArrayList<RadinGroupModel>();
        radinGroupModels.add(new RadinGroupModel(0, DateTime.now(), "Group 1", 
                "A cool group", "img/avatar1.png"));
        return radinGroupModels;
    }

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.JSONParser#getJsonFromModels(java.util.List)
     */
    @Override
    public List<JSONObject> getJsonFromModels(List<RadinGroupModel> modelList) {
        JSONObject json = new JSONObject();
        try {
            json.put("RG_ID", "0");
            json.put("RG_name", "Group 1");
            json.put("RG_creationDate", "2014/01/01/00/00");
            json.put("RG_description", "A cool group");
            json.put("RG_avatar", "img/avatar1.png");
            json.put("RG_deletedAt", "2014/01/01/00/00");
            
        } catch (JSONException e) {
            
        }
        
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        jsonList.add(json);
        return jsonList;
    }

}
