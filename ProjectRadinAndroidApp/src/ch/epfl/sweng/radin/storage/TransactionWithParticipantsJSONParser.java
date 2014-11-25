/**
 * @author topali2
 */
package ch.epfl.sweng.radin.storage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.radin.storage.parsers.JSONParser;
import ch.epfl.sweng.radin.storage.parsers.TransactionJSONParser;

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

		TransactionJSONParser transJsonParser = new TransactionJSONParser();

		List<TransactionModel> transactionListModel = new ArrayList<TransactionModel>();
		List<List<Integer>> userIDList = new ArrayList<List<Integer>>();
		List<List<Integer>> userCoefficientsList = new ArrayList<List<Integer>>();

		for (TransactionWithParticipantsModel transactionWithParticipantsModel : transactionList) {
			
			transactionListModel.add(transactionWithParticipantsModel);
			userIDList.add(new ArrayList<Integer>(
					transactionWithParticipantsModel.getUsersWithCoefficients().keySet()));
			userCoefficientsList.add(new ArrayList<Integer>(
					transactionWithParticipantsModel.getUsersWithCoefficients().values()));
		}

		JSONObject transactionWithParticipantsObject = new JSONObject();
		JSONObject transObject = transJsonParser.getJsonFromModels(transactionListModel);
		JSONArray transArray = transObject.getJSONArray("transaction");
		
		for (int i = 0; i < transArray.length(); i++) {
			
			JSONArray userArray = new JSONArray();
			
			for (int j = 0; j < userCoefficientsList.size(); j++) {
			
				JSONObject userIdCoeff = new JSONObject();
				userIdCoeff.put("id", userIDList.get(i).get(j));
				userIdCoeff.put("coefficient", userCoefficientsList.get(i).get(j));
				userArray.put(userIdCoeff);
				
			}
			
			transObject.getJSONArray("transaction").getJSONObject(i).put("T_user_coefficients", userArray);
		}
		
		
		transactionWithParticipantsObject.put("transactionWithParticipants", transObject);
		
		return transactionWithParticipantsObject;
	}

}
