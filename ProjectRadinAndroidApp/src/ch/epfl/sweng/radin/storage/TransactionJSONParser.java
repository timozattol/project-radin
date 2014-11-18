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
 * @author topali2
 * A JSONParser for TransactionModel.
 */
public class TransactionJSONParser implements JSONParser<TransactionModel> {

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.JSONParser#getModelsFromJson(java.util.List)
     */
    @Override
    public List<TransactionModel> getModelsFromJson(JSONObject jsonObject) throws JSONException {
        List<TransactionModel> transactionModels = new ArrayList<TransactionModel>();
        
		JSONArray jsonArray = jsonObject.getJSONArray("transaction");
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd HH/mm");
        
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
        return transactionModels;
    }

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.JSONParser#getJsonFromModels(java.util.List)
     */
    @Override
    public JSONObject getJsonFromModels(List<TransactionModel> modelList) throws JSONException {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd HH/mm");

        for (TransactionModel transactionModel : modelList) {
			
        	JSONObject jsonObject = new JSONObject();
			
        	jsonObject.put("T_ID", transactionModel.getTransactionID());
        	jsonObject.put("T_parentRadinGroupID", transactionModel.getParentRadinGroupID());
        	jsonObject.put("T_debitorID", transactionModel.getCreditorID());
        	jsonObject.put("T_creatorID", transactionModel.getCreatorID());
        	jsonObject.put("T_amount", transactionModel.getAmount());
        	jsonObject.put("T_currency", transactionModel.getCurrency().toString());
        	jsonObject.put("T_dateTime", transactionModel.getDateTime().toString(dtf));
        	jsonObject.put("T_purpose", transactionModel.getPurpose());
        	jsonObject.put("T_type", transactionModel.getType().toString());
        	
        	jsonArray.put(jsonObject);        	
        	
		}
        
        json.put("transaction", jsonArray);

        return json;
    }

}
