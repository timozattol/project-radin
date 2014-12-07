/**
 * @author topali2
 */
package ch.epfl.sweng.radin.storage.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import ch.epfl.sweng.radin.storage.Currency;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.TransactionType;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;

/**
 * @author topali2
 *
 */
@SuppressLint("UseSparseArrays") 
public class TransactionWithParticipantsJSONParser implements JSONParser<TransactionWithParticipantsModel> {

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.JSONParser#getModelsFromJson(org.json.JSONObject)
	 */
	@Override
	public List<TransactionWithParticipantsModel> getModelsFromJson(JSONObject json) throws JSONException {
	
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd HH/mm");
		
		List<TransactionWithParticipantsModel> transactionWPModels = new ArrayList<TransactionWithParticipantsModel>();
		JSONArray transactionWPJson = json.getJSONArray("transactionWithParticipants");
		
		for (int i = 0; i < transactionWPJson.length(); i++) {
			JSONObject transactionWP = transactionWPJson.getJSONObject(i);
			JSONObject transactionJson = transactionWP.getJSONObject("transaction");
			
			TransactionModel transaction = new TransactionModel(
					transactionJson.getInt("T_ID"), 
					transactionJson.getInt("T_parentRadinGroupID"), 
					transactionJson.getInt("T_debitorID"), 
					transactionJson.getInt("T_creatorID"), 
					transactionJson.getDouble("T_amount"), 
					Currency.valueOf(transactionJson.getString("T_currency")), 
					dtf.parseDateTime(transactionJson.getString("T_dateTime")), 
					transactionJson.getString("T_purpose"), 
					TransactionType.valueOf(transactionJson.getString("T_type"))
			);
			
			JSONArray usersWithCoeffsJson = transactionWP.getJSONArray("coefficients");
			Map<Integer, Integer> usersWithCoeffs = new HashMap<Integer, Integer>();
			
			for (int j = 0; j < usersWithCoeffsJson.length(); j++) {
				JSONObject userWithCoeff = usersWithCoeffsJson.getJSONObject(i);
				usersWithCoeffs.put(userWithCoeff.getInt("id"), userWithCoeff.getInt("coefficient"));
			}
			
			transactionWPModels.add(new TransactionWithParticipantsModel(transaction, usersWithCoeffs));
		}
		
		return transactionWPModels;
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
