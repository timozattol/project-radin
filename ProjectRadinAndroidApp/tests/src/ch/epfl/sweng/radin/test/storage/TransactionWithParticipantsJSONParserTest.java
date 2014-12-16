/**
 * @author topali2
 */
package ch.epfl.sweng.radin.test.storage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;

import android.test.AndroidTestCase;
import ch.epfl.sweng.radin.storage.Currency;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.TransactionType;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;
import ch.epfl.sweng.radin.storage.parsers.TransactionWithParticipantsJSONParser;

/**
 * @author topali2
 *
 */
public class TransactionWithParticipantsJSONParserTest extends AndroidTestCase{

	private static final int ID = 1;
	private static final int PARENTRADINGROUPID = 3;
	private static final int DEBITORID = 3;
	private static final int CREATORID = 4;
	private static final double AMOUNT = 1526.32;
	private static final String CURRENCY = "CHF";
	private static final String DATETIME = "2014/07/21 11/25";
	private static final String PURPOSE = "gift";
	private static final String TYPE = "PAYMENT";

	private static final int ID2 = 2;
	private static final int PARENTRADINGROUPID2 = 2;
	private static final int DEBITORID2 = 4;
	private static final int CREATORID2 = 5;
	private static final double AMOUNT2 = 156.0;
	private static final String CURRENCY2 = "CHF";
	private static final String DATETIME2 = "2014/09/21 11/25";
	private static final String PURPOSE2 = "due";
	private static final String TYPE2 = "PAYMENT";
	
	private static final Integer USERID = 1;
	private static final Integer USERID2 = 5;
	private static final Integer USERID3 = 2;
	private static final Integer USERID4 = 3;
	
	private static final Integer COEFFICIENT = 3;
	private static final Integer COEFFICIENT2 = 4;
	private static final Integer COEFFICIENT3 = 2;
	private static final Integer COEFFICIENT4 = 1;
	


	private static JSONObject json;
	private static String jsonString;
	private static List<TransactionWithParticipantsModel> modelList; 
	private static DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd HH/mm");
	private static Map<Integer, Integer> map1;
	private static Map<Integer, Integer> map2;


	@Override
	protected void setUp() throws Exception {
		super.setUp();


		jsonString = "{"
				+ "		\"transactionWithParticipants\":["
				+ "     			{\"transaction\":"
				+ "             			    {\"T_ID\": "+ID+","
				+ "             			    \"T_parentRadinGroupID\": "+PARENTRADINGROUPID+","
				+ "      			            \"T_debitorID\": "+DEBITORID+","
				+ "                		      	\"T_creatorID\" : "+CREATORID+"," 
				+ "      			         	\"T_amount\" : "+AMOUNT+","
				+ "             			    \"T_currency\" : \""+CURRENCY+"\"," 
				+ "            			        \"T_dateTime\" : \""+DATETIME+"\"," 
				+ "            			        \"T_purpose\" : \""+PURPOSE+"\"," 
				+ "            			        \"T_type\" : \""+TYPE+"\"" 
				+ "								},"
				+ "					\"coefficients\":["
				+ "                             {"          
				+ "            								   \"id\": "+USERID+","
				+ "            								   \"coefficient\": "+COEFFICIENT+""
				+ "    							}," 
				+ "    							{"             
				+ "   								           \"id\": "+USERID2+","
				+ "           								   \"coefficient\" : "+COEFFICIENT2+""
				+ "   							}]" 
				+ "    				}," 
				+ "   				{\"transaction\":"
				+ "                				{\"T_ID\": "+ID2+","
				+ "               				\"T_parentRadinGroupID\": "+PARENTRADINGROUPID2+","
				+ "               				\"T_debitorID\": "+DEBITORID2+","
				+ "              				\"T_creatorID\" : "+CREATORID2+"," 
				+ "             				\"T_amount\" : "+AMOUNT2+","
				+ "             				\"T_currency\" : \""+CURRENCY2+"\"," 
				+ "                				\"T_dateTime\" : \""+DATETIME2+"\"," 
				+ "               				\"T_purpose\" : \""+PURPOSE2+"\"," 
				+ "                				\"T_type\" : \""+TYPE2+"\""
				+ "								},"
				+ "					\"coefficients\": ["
				+ "                             {"          
				+ "            								   \"id\": "+USERID3+","
				+ "            								   \"coefficient\": "+COEFFICIENT3+""
				+ "    							}," 
				+ "    							{"             
				+ "   								           \"id\": "+USERID4+","
				+ "           								   \"coefficient\" : "+COEFFICIENT4+""
				+ "   							}]" 
				+ "					}"
				+ "    			]" 
				+ "}"; 

		json = new JSONObject(jsonString);
		
		map1 = new LinkedHashMap<Integer, Integer>();
		map2 = new LinkedHashMap<Integer, Integer>();
		
		map1.put(USERID, COEFFICIENT);
		map1.put(USERID2, COEFFICIENT2);
		map2.put(USERID3, COEFFICIENT3);
		map2.put(USERID4, COEFFICIENT4);
		
		modelList = new ArrayList<TransactionWithParticipantsModel>();
		modelList.add(new TransactionWithParticipantsModel(new TransactionModel(
				ID,
				PARENTRADINGROUPID,
				DEBITORID,
				CREATORID,
				AMOUNT,
				Currency.valueOf(CURRENCY),
				dtf.parseDateTime(DATETIME),
				PURPOSE,
				TransactionType.valueOf(TYPE)), map1));
		
		modelList.add(new TransactionWithParticipantsModel(new TransactionModel(
				ID2,
				PARENTRADINGROUPID2,
				DEBITORID2,
				CREATORID2,
				AMOUNT2,
				Currency.valueOf(CURRENCY2),
				dtf.parseDateTime(DATETIME2),
				PURPOSE2,
				TransactionType.valueOf(TYPE2)), map2));


	}

	public void testJsonToModelList() throws JSONException {

		TransactionWithParticipantsJSONParser transaction = new TransactionWithParticipantsJSONParser();
		List<TransactionWithParticipantsModel> modelListTest = transaction.getModelsFromJson(json);
		assertEquals(modelList, modelListTest);

	}

	public void testModeListToJson() throws JSONException {

		TransactionWithParticipantsJSONParser transaction = new TransactionWithParticipantsJSONParser();
		JSONObject jsonTest = transaction.getJsonFromModels(modelList);
		JSONAssert.assertEquals(json, jsonTest, true);

	}
}
