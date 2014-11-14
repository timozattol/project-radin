/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
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
    public List<TransactionModel> getModelsFromJson(JSONObject jsonObject) throws JSONException {
        List<TransactionModel> transactionModels = new ArrayList<TransactionModel>();
        
		JSONArray jsonArray = jsonObject.getJSONArray("transaction");
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/mm/dd HH/mm");
        
		for (int i = 0; i < jsonArray.length(); i++) {
			
			JSONObject jsonData = jsonArray.getJSONObject(i);
			
			transactionModels.add(new TransactionModel(
					jsonData.getInt("T_ID"),
					jsonData.getInt("T_parentRadinGroupID"),
					jsonData.getInt("T_debitorID"),
					jsonData.getInt("T_creatorID"),
					jsonData.getDouble("T_amount"),
					Currency.valueOf(jsonData.getString("T_currency")),
					dtf.parseDateTime(jsonData.getString("T_dateTime")),
					jsonData.getString("T_purpose"),
					TransactionType.valueOf(jsonData.getString("T_type"))));
			
			
		}
//        transactionModels.add(new TransactionModel(0, 0, 0, 0, 1, Currency.CHF, 
//                DateTime.now(), "Buy more jewelleries", null, TransactionType.PAYMENT));
        return transactionModels;
    }

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.JSONParser#getJsonFromModels(java.util.List)
     */
    @Override
    public JSONObject getJsonFromModels(List<TransactionModel> modelList) throws JSONException {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/mm/dd HH/mm");

        for (TransactionModel transactionModel : modelList) {
			
        	JSONObject jsonObject = new JSONObject();
			
        	jsonObject.put("T_ID", transactionModel.getTransactionID());
        	jsonObject.put("T_parentRadinGroupID", transactionModel.getParentRadinGroupID());
        	jsonObject.put("T_debitorID", transactionModel.getDebitorID());
        	jsonObject.put("T_creatorID", transactionModel.getCreatorID());
        	jsonObject.put("T_amount", transactionModel.getAmount());
        	jsonObject.put("T_currency", transactionModel.getCurrency().toString());
        	jsonObject.put("T_dateTime", transactionModel.getDateTime().toString(dtf));
        	jsonObject.put("T_purpose", transactionModel.getPurpose());
        	jsonObject.put("T_type", transactionModel.getType().toString());
        	
        	jsonArray.put(jsonObject);        	
        	
		}
        
//        try {
//            json.put("T_ID", 0);
//            json.put("T_parentRadinGroupID", 0);
//            json.put("T_debitorID", 0);
//            json.put("T_creatorID", 0);
//            json.put("T_amount", 1);
//            json.put("T_currency", "CHF");
//            json.put("T_dateTime", "2014/01/01/00/00");
//            json.put("T_purpose", "Buy more jewelleries");
//            json.put("T_type", "payment");
//            
//        } catch (JSONException e) {
//            
//        }
        
        json.put("transaction", jsonArray);

        return json;
    }

}
