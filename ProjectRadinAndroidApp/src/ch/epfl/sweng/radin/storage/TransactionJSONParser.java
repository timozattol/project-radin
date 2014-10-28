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
 * A JSONParser for TransactionModel. A "fake" one, which always returns the same thing.
 * It's very ugly but it's just to have something that "works" for now.
 */
public class TransactionJSONParser implements JSONParser<TransactionModel> {

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.JSONParser#getModelsFromJson(java.util.List)
     */
    @Override
    public List<TransactionModel> getModelsFromJson(List<JSONObject> jsonList) {
        List<TransactionModel> transactionModels = new ArrayList<TransactionModel>();
        transactionModels.add(new TransactionModel(0, 0, 0, 0, 1, Currency.CHF, 
                DateTime.now(), "Buy more jewelleries", null, TransactionType.PAYMENT));
        return transactionModels;
    }

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.JSONParser#getJsonFromModels(java.util.List)
     */
    @Override
    public List<JSONObject> getJsonFromModels(List<TransactionModel> modelList) {
        JSONObject json = new JSONObject();
        try {
            json.put("T_ID", 0);
            json.put("T_parentRadinGroupID", 0);
            json.put("T_debitorID", 0);
            json.put("T_creatorID", 0);
            json.put("T_amount", 1);
            json.put("T_currency", "CHF");
            json.put("T_dateTime", "2014/01/01/00/00");
            json.put("T_purpose", "Buy more jewelleries");
            json.put("T_type", "payment");
            
        } catch (JSONException e) {
            
        }
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        jsonList.add(json);
        return jsonList;
    }

}
