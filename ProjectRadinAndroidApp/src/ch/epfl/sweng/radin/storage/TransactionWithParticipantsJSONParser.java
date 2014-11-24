/**
 * @author topali2
 */
package ch.epfl.sweng.radin.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
		UserJSONParser userParser = new UserJSONParser();
		
		JSONArray transWithPartObject = json.getJSONArray("transactionWithParticipants");
		JSONObject transObject = transWithPartObject.getJSONObject(0); 
		JSONArray transArray = transObject.getJSONArray("transaction");
		
		List<TransactionModel> transactionlist = transParser.getModelsFromJson(transObject);
		List<List<UserModel>> usersList = new ArrayList<List<UserModel>>();
		List<List<Integer>> usersID = new ArrayList<List<Integer>>();
		List<Map<UserModel, Integer>> mapList = new ArrayList<Map<UserModel, Integer>>();
		List<TransactionWithParticipantsModel> transWithPartList = new ArrayList<TransactionWithParticipantsModel>();
		
		for (int i = 0; i < transArray.length(); i++) {
			
			JSONObject jsonData = transArray.getJSONObject(i);
			JSONArray usersArray = jsonData.getJSONArray("user");
			
			usersID.add(new ArrayList<Integer>());

			for (int j = 0; j < usersArray.length(); j++) {
				
				usersID.get(i).add((Integer) usersArray.getJSONObject(j).get("U_coefficient"));
			}
			
			usersList.add(userParser.getModelsFromJson(jsonData));
			
		}
		
		
		for (int i = 0; i < usersList.size(); i++) {
			
			mapList.add(new HashMap<UserModel, Integer>());
			
			for (int j = 0; j < usersList.get(i).size(); j++) {
				mapList.get(i).put(usersList.get(i).get(j), usersID.get(i).get(j));
			}
		}
		
		for (int i = 0; i < transactionlist.size(); i++) {
			
			transWithPartList.add(new TransactionWithParticipantsModel(transactionlist.get(i), mapList.get(i)));
			
		}
		
		return transWithPartList;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.JSONParser#getJsonFromModels(java.util.List)
	 */
	@Override
	public JSONObject getJsonFromModels(List<TransactionWithParticipantsModel> transactionList) throws JSONException {

		TransactionJSONParser transJsonParser = new TransactionJSONParser();
		UserJSONParser userJsonParser = new UserJSONParser();

		List<TransactionModel> transModelList = new ArrayList<TransactionModel>();
		List<List<UserModel>> userModelList = new ArrayList<List<UserModel>>();
		List<List<Integer>> userIDList = new ArrayList<List<Integer>>();

		for (TransactionWithParticipantsModel transactionWithParticipantsModel : transactionList) {
			
			transModelList.add(transactionWithParticipantsModel.getTransaction());
			userModelList.add(new ArrayList<UserModel>(transactionWithParticipantsModel.getMap().keySet()));
			userIDList.add(new ArrayList<Integer>(transactionWithParticipantsModel.getMap().values()));
		}

		JSONObject transactionWithParticipantsObject = new JSONObject();
		JSONArray transactionWithParticipantsArray = new JSONArray();
		JSONObject transObject = transJsonParser.getJsonFromModels(transModelList);
		JSONArray transArray = transObject.getJSONArray("transaction");
		
		for (int i = 0; i < transArray.length(); i++) {
			
			JSONArray userArray = userJsonParser.getJsonFromModels(userModelList.get(i)).getJSONArray("user");
			
			for (int j = 0; j < userArray.length(); j++) {
			
				userArray.getJSONObject(j).put("U_coefficient", userIDList.get(i).get(j));
				
			}
			
			transObject.getJSONArray("transaction").getJSONObject(i).put("user", userArray);
		}
		
		transactionWithParticipantsArray.put(transObject);
		transactionWithParticipantsObject.put("transactionWithParticipants", transactionWithParticipantsArray);
		
		return transactionWithParticipantsObject;
	}

}
