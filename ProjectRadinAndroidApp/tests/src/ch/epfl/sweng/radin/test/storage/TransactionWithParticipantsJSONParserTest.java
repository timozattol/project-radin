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
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsJSONParser;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;
import ch.epfl.sweng.radin.storage.UserModel;

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
	
	private static final String FIRSTNAME = "martin";
	private static final String LASTNAME = "blabla";
	private static final String EMAIL = "martin.blabla@epfl.ch";
	private static final String ADDRESS = "nullepart";
	private static final String IBAN = "CH243545 6464 6564";
	private static final String PICTURE = "img/hoho.png";
	private static final int USERID = 1;
	private static final Integer COEFFICIENT = 1;

	private static final String FIRSTNAME2 = "aloha";
	private static final String LASTNAME2 = "klu";
	private static final String EMAIL2 = "aloha.klu@agepoly.ch";
	private static final String ADDRESS2 = "pas chez lui";
	private static final String IBAN2 = "CH34 0026 5987 6399 1140 A";
	private static final String PICTURE2 = "img/houhou.gif";
	private static final int USERID2 = 5;
	private static final Integer COEFFICIENT2 = 2;
	
	private static final String FIRSTNAME3 = "Julie";
	private static final String LASTNAME3 = "Djeffal";
	private static final String EMAIL3 = "julie.djeffal@epfl.ch";
	private static final String ADDRESS3 = "quelquepart";
	private static final String IBAN3 = "CH246465 6464 6564";
	private static final String PICTURE3 = "img/haha.png";
	private static final int USERID3 = 2;
	private static final Integer COEFFICIENT3 = 2;

	private static final String FIRSTNAME4 = "Cedric";
	private static final String LASTNAME4 = "Cook";
	private static final String EMAIL4 = "cedrik.cook@agepoly.ch";
	private static final String ADDRESS4 = "chez lui";
	private static final String IBAN4 = "CH34 0026 5265 6399 1140 A";
	private static final String PICTURE4 = "img/hihi.gif";
	private static final int USERID4 = 3;
	private static final Integer COEFFICIENT4 = 3;

	private static JSONObject json;
	private static String jsonString;
	private static List<TransactionWithParticipantsModel> modelList; 
	private static DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd HH/mm");
	private static Map<UserModel, Integer> map1;
	private static Map<UserModel, Integer> map2;


	@Override
	protected void setUp() throws Exception {
		super.setUp();


		jsonString = "{"
				+ "		\"transactionWithParticipants\": ["
				+ "		{"
				+ "     			\"transaction\": ["
				+ "    			    {" 
				+ "             			    \"T_ID\": "+ID+","
				+ "             			    \"T_parentRadinGroupID\": "+PARENTRADINGROUPID+","
				+ "      			            \"T_debitorID\": "+DEBITORID+","
				+ "                		      	\"T_creatorID\" : "+CREATORID+"," 
				+ "      			         	\"T_amount\" : "+AMOUNT+","
				+ "             			    \"T_currency\" : \""+CURRENCY+"\"," 
				+ "            			        \"T_dateTime\" : \""+DATETIME+"\"," 
				+ "            			        \"T_purpose\" : \""+PURPOSE+"\"," 
				+ "            			        \"T_type\" : \""+TYPE+"\"," 
				+ "								\"user\": ["
				+ "    							{" 
				+ "             							   \"U_firstName\": \""+FIRSTNAME+"\","
				+ "        								       \"U_lastName\": \""+LASTNAME+"\","
				+ "         							       \"U_email\": \""+EMAIL+"\","
				+ "             							   \"U_address\" : \""+ADDRESS+"\"," 
				+ "           								   \"U_iban\" : \""+IBAN+"\"," 
				+ "           								   \"U_picture\" : \""+PICTURE+"\","            
				+ "            								   \"U_ID\": "+USERID+"," 
				+ "           								   \"U_coefficient\" : "+COEFFICIENT+"" 
				+ "    							}," 
				+ "    							{" 
				+ "   								           \"U_firstName\": \""+FIRSTNAME2+"\","
				+ "    								           \"U_lastName\": \""+LASTNAME2+"\","
				+ "   								           \"U_email\": \""+EMAIL2+"\","
				+ "     								       \"U_address\" : \""+ADDRESS2+"\"," 
				+ "      								       \"U_iban\" : \""+IBAN2+"\"," 
				+ "      								       \"U_picture\" : \""+PICTURE2+"\","            
				+ "   								           \"U_ID\": "+USERID2+","
				+ "           								   \"U_coefficient\" : "+COEFFICIENT2+""
				+ "   							}" 
				+ "							 ]"
				+ "    				}," 
				+ "   				{" 
				+ "                				\"T_ID\": "+ID2+","
				+ "               				\"T_parentRadinGroupID\": "+PARENTRADINGROUPID2+","
				+ "               				\"T_debitorID\": "+DEBITORID2+","
				+ "              				\"T_creatorID\" : "+CREATORID2+"," 
				+ "             				\"T_amount\" : "+AMOUNT2+","
				+ "             				\"T_currency\" : \""+CURRENCY2+"\"," 
				+ "                				\"T_dateTime\" : \""+DATETIME2+"\"," 
				+ "               				\"T_purpose\" : \""+PURPOSE2+"\"," 
				+ "                				\"T_type\" : \""+TYPE2+"\","
				+ "								\"user\": ["
				+ "    							{" 
				+ "             							   \"U_firstName\": \""+FIRSTNAME3+"\","
				+ "        								       \"U_lastName\": \""+LASTNAME3+"\","
				+ "         							       \"U_email\": \""+EMAIL3+"\","
				+ "             							   \"U_address\" : \""+ADDRESS3+"\"," 
				+ "           								   \"U_iban\" : \""+IBAN3+"\"," 
				+ "           								   \"U_picture\" : \""+PICTURE3+"\","            
				+ "            								   \"U_ID\": "+USERID3+"," 
				+ "           								   \"U_coefficient\" : "+COEFFICIENT3+"" 
				+ "    							}," 
				+ "    							{" 
				+ "   								           \"U_firstName\": \""+FIRSTNAME4+"\","
				+ "    								           \"U_lastName\": \""+LASTNAME4+"\","
				+ "   								           \"U_email\": \""+EMAIL4+"\","
				+ "     								       \"U_address\" : \""+ADDRESS4+"\"," 
				+ "      								       \"U_iban\" : \""+IBAN4+"\"," 
				+ "      								       \"U_picture\" : \""+PICTURE4+"\","            
				+ "   								           \"U_ID\": "+USERID4+","
				+ "           								   \"U_coefficient\" : "+COEFFICIENT4+""
				+ "   							}" 
				+ "							 ]"
				+ "    					}" 
				+ "  				]" 
				+ "				}"	
				+ "			]"
				+ "}"; 


		json = new JSONObject(jsonString);
		
		map1 = new LinkedHashMap<UserModel, Integer>();
		map2 = new LinkedHashMap<UserModel, Integer>();
		
		map1.put(new UserModel(FIRSTNAME, LASTNAME, EMAIL, ADDRESS, IBAN, PICTURE, USERID), COEFFICIENT);
		map1.put(new UserModel(FIRSTNAME2, LASTNAME2, EMAIL2, ADDRESS2, IBAN2, PICTURE2, USERID2), COEFFICIENT2);
		map2.put(new UserModel(FIRSTNAME3, LASTNAME3, EMAIL3, ADDRESS3, IBAN3, PICTURE3, USERID3), COEFFICIENT3);
		map2.put(new UserModel(FIRSTNAME4, LASTNAME4, EMAIL4, ADDRESS4, IBAN4, PICTURE4, USERID4), COEFFICIENT4);
		
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
