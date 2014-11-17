/**
 * 
 */
package ch.epfl.sweng.radin.test.storage;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;

import android.test.AndroidTestCase;
import ch.epfl.sweng.radin.storage.Currency;
import ch.epfl.sweng.radin.storage.TransactionJSONParser;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.TransactionType;

/**
 * @author topali2
 *
 */
public class TransactionJSONParserTest extends AndroidTestCase{
	private static final int ID = 1;
	private static final int PARENTRADINGROUPID = 2;
	private static final int DEBITORID = 3;
	private static final int CREATORID = 4;
	private static final double AMOUNT = 1526.32;
	private static final String CURRENCY = "CHF";
	private static final String DATETIME = "2014/07/21 11/25";
	private static final String PURPOSE = "gift";
	private static final String TYPE = "PAYMENT";

	private static final int ID2 = 1;
	private static final int PARENTRADINGROUPID2 = 2;
	private static final int DEBITORID2 = 3;
	private static final int CREATORID2 = 4;
	private static final double AMOUNT2 = 1526.32;
	private static final String CURRENCY2 = "CHF";
	private static final String DATETIME2 = "2014/07/21 11/25";
	private static final String PURPOSE2 = "gift";
	private static final String TYPE2 = "PAYMENT";

	private static JSONObject json;
	private static String jsonString;
	private static List<TransactionModel> modelList; 
	private static DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd HH/mm");


	@Override
	protected void setUp() throws Exception {
		super.setUp();


		jsonString = "{"
				+ "     \"transaction\": ["
				+ "    {" 
				+ "                \"T_ID\": "+ID+","
				+ "                \"T_parentRadinGroupID\": "+PARENTRADINGROUPID+","
				+ "                \"T_debitorID\": "+DEBITORID+","
				+ "                \"T_creatorID\" : "+CREATORID+"," 
				+ "                \"T_amount\" : "+AMOUNT+","
				+ "                \"T_currency\" : \""+CURRENCY+"\"," 
				+ "                \"T_dateTime\" : \""+DATETIME+"\"," 
				+ "                \"T_purpose\" : \""+PURPOSE+"\"," 
				+ "                \"T_type\" : \""+TYPE+"\"" 
				+ "    }," 
				+ "    {" 
				+ "                \"T_ID\": "+ID2+","
				+ "                \"T_parentRadinGroupID\": "+PARENTRADINGROUPID2+","
				+ "                \"T_debitorID\": "+DEBITORID2+","
				+ "                \"T_creatorID\" : "+CREATORID2+"," 
				+ "                \"T_amount\" : "+AMOUNT2+","
				+ "                \"T_currency\" : \""+CURRENCY2+"\"," 
				+ "                \"T_dateTime\" : \""+DATETIME2+"\"," 
				+ "                \"T_purpose\" : \""+PURPOSE2+"\"," 
				+ "                \"T_type\" : \""+TYPE2+"\"" 
				+ "    }" 
				+ "  ]" 
				+ "}"; 


		json = new JSONObject(jsonString);
		modelList = new ArrayList<TransactionModel>();
		modelList.add(new TransactionModel(
				ID,
				PARENTRADINGROUPID,
				DEBITORID,
				CREATORID,
				AMOUNT,
				Currency.valueOf(CURRENCY),
				dtf.parseDateTime(DATETIME),
				PURPOSE,
				TransactionType.valueOf(TYPE)));
		modelList.add(new TransactionModel(
				ID2,
				PARENTRADINGROUPID2,
				DEBITORID2,
				CREATORID2,
				AMOUNT2,
				Currency.valueOf(CURRENCY2),
				dtf.parseDateTime(DATETIME2),
				PURPOSE2,
				TransactionType.valueOf(TYPE2)));


	}

	public void testJsonToModelList() throws JSONException {

		TransactionJSONParser transaction = new TransactionJSONParser();
		List<TransactionModel> modelListTest = transaction.getModelsFromJson(json);
		assertEquals(modelList, modelListTest);

	}

	public void testModeListToJson() throws JSONException {

		TransactionJSONParser transaction = new TransactionJSONParser();
		JSONObject jsonTest = transaction.getJsonFromModels(modelList);
		JSONAssert.assertEquals(json, jsonTest, true);

	}
}
