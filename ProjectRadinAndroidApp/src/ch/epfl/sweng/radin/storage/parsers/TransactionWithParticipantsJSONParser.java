/**
 * @author topali2
 */
package ch.epfl.sweng.radin.storage.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;

/**
 * @author topali2
 *
 */
public class TransactionWithParticipantsJSONParser implements JSONParser<TransactionWithParticipantsModel> {

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.JSONParser#getModelsFromJson(org.json.JSONObject)
	 */
	@Override
	public List<TransactionWithParticipantsModel> getModelsFromJson(JSONObject json) throws JSONException {

		TransactionJSONParser transParser = new TransactionJSONParser();
		
		JSONObject transWithPartObject = json.getJSONObject("transactionWithParticipants");
		JSONArray transArray = transWithPartObject.getJSONArray("transaction");
		
		List<TransactionWithParticipantsModel> transWithPartList = new ArrayList<TransactionWithParticipantsModel>();
		List<TransactionModel> transactionList = transParser.getModelsFromJson(transWithPartObject);
		List<Map<Integer, Integer>> mapList = new ArrayList<Map<Integer, Integer>>();
		
		for (int i = 0; i < transArray.length(); i++) {
			
			mapList.add(new LinkedHashMap<Integer, Integer>());
			JSONArray userIds = transArray.getJSONObject(i).getJSONArray("T_user_coefficients");
			
			for (int j = 0; j < userIds.length(); j++) {
				JSONObject userData = userIds.getJSONObject(j);
				mapList.get(i).put(userData.getInt("id"), userData.getInt("coefficient"));
				
			}
			
		}
				
		for (int i = 0; i < transactionList.size(); i++) {
			
			transWithPartList.add(new TransactionWithParticipantsModel(transactionList.get(i), mapList.get(i)));
			
		}
		
		return transWithPartList;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.JSONParser#getJsonFromModels(java.util.List)
	 */
	@Override
	public JSONObject getJsonFromModels(List<TransactionWithParticipantsModel> transactionList) throws JSONException {
		
		JSONArray transactionWPJson = new JSONArray();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd HH/mm");
		
		for (TransactionWithParticipantsModel transactionWPModel : transactionList) {
			JSONObject transactionWithParticipants = new JSONObject();
			
			JSONObject transaction = new JSONObject();
			
        	transaction.put("T_ID", transactionWPModel.getTransactionID());
        	transaction.put("T_parentRadinGroupID", transactionWPModel.getParentRadinGroupID());
        	transaction.put("T_debitorID", transactionWPModel.getCreditorID());
        	transaction.put("T_creatorID", transactionWPModel.getCreatorID());
        	transaction.put("T_amount", transactionWPModel.getAmount());
        	transaction.put("T_currency", transactionWPModel.getCurrency().toString());
        	transaction.put("T_dateTime", transactionWPModel.getDateTime().toString(dtf));
        	transaction.put("T_purpose", transactionWPModel.getPurpose());
        	transaction.put("T_type", transactionWPModel.getType().toString());
        	
        	transactionWithParticipants.put("transaction", transaction);
        	
        	Map<Integer, Integer> usersWithCoeffs = transactionWPModel.getUsersWithCoefficients();
        	JSONArray coefficients = new JSONArray();
        	
        	for (Map.Entry<Integer, Integer> userWithCoeff : usersWithCoeffs.entrySet()) {
        		JSONObject userWithCoeffJson = new JSONObject();
        		
        		userWithCoeffJson.put("id", userWithCoeff.getKey());
        		userWithCoeffJson.put("coefficient", userWithCoeff.getValue());
        		
        		coefficients.put(userWithCoeffJson);
        	}
        	
        	transactionWithParticipants.put("coefficients", coefficients);
        	
        	transactionWPJson.put(transactionWithParticipants);
		}
		JSONObject transactionWithParticipantsJson = new JSONObject();
		transactionWithParticipantsJson.put("transactionWithParticipants", transactionWPJson);

		return transactionWithParticipantsJson;
	}

}
