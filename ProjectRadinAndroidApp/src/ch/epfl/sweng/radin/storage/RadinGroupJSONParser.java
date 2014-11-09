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
            json.put("_RID", "0");
            json.put("RG_NAME", "Group 1");
            json.put("RG_CREATION_DATE", "2014/01/01/00/00");
            json.put("RG_DESCRIPTION", "A cool group");
            json.put("RG_AVATAR", "img/avatar1.png");
            json.put("RG_END_DATE", "2014/01/01/01/01");
            json.put("RG_DELETED_AT", "2014/01/01/01/02");
            
        } catch (JSONException e) {
            
        }
        
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        jsonList.add(json);
        return jsonList;
    }

}
