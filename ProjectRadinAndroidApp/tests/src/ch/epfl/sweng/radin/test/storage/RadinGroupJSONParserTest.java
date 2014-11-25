package ch.epfl.sweng.radin.test.storage;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;

import android.test.AndroidTestCase;
import ch.epfl.sweng.radin.storage.parsers.RadinGroupJSONParser;
import ch.epfl.sweng.radin.storage.RadinGroupModel;

/**
 * @author topali2
 *
 */
public class RadinGroupJSONParserTest extends AndroidTestCase{
    
	private static final int ID = 1;
	private static final String NAME = "coloc";
	private static final String CREATIONDATE = "2014/01/01 11/25";
	private static final String DESCRIPTION = "bouffe";
	private static final String AVATAR = "img/haha.png";
	private static final int MASTERID = 3;

	private static final int ID2 = 2;
	private static final String NAME2 = "paleo";
	private static final String CREATIONDATE2 = "2014/07/21 11/25";
	private static final String DESCRIPTION2 = "groupe paleo";
	private static final String AVATAR2 = "img/sds.jpeg";
	private static final int MASTERID2 = 4;

	private static JSONObject json2;
	private static JSONObject json;
	private static String jsonString;
	private static String jsonString2;
	private static List<RadinGroupModel> modelList; 
	private static List<RadinGroupModel> modelList2; 
	private static DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy/MM/dd HH/mm");


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		
		jsonString = "{"
				+ "     \"radinGroup\": ["
				+ "    {" 
				+ "                \"RG_ID\": "+ID+","
				+ "                \"RG_name\": \""+NAME+"\","
				+ "                \"RG_creationDate\": \""+CREATIONDATE+"\","
				+ "                \"RG_description\" : \""+DESCRIPTION+"\"," 
				+ "                \"RG_avatar\" : \""+AVATAR+"\"" 
				+ "    }," 
				+ "    {" 
				+ "                \"RG_ID\": "+ID2+","
				+ "                \"RG_name\": \""+NAME2+"\","
				+ "                \"RG_creationDate\": \""+CREATIONDATE2+"\","
				+ "                \"RG_description\" : \""+DESCRIPTION2+"\"," 
				+ "                \"RG_avatar\" : \""+AVATAR2+"\""
				
				+ "    }" 
				+ "  ]" 
				+ "}"; 
		
		jsonString2 = "{"
				+ "     \"radinGroup\": ["
				+ "    {" 
				+ "                \"RG_ID\": "+ID+","
				+ "                \"RG_name\": \""+NAME+"\","
				+ "                \"RG_creationDate\": \""+CREATIONDATE+"\","
				+ "                \"RG_description\" : \""+DESCRIPTION+"\"," 
				+ "                \"RG_avatar\" : \""+AVATAR+"\"," 
				+ "                \"RG_masterID\" : "+MASTERID+""
				+ "    }," 
				+ "    {" 
				+ "                \"RG_ID\": "+ID2+","
				+ "                \"RG_name\": \""+NAME2+"\","
				+ "                \"RG_creationDate\": \""+CREATIONDATE2+"\","
				+ "                \"RG_description\" : \""+DESCRIPTION2+"\"," 
				+ "                \"RG_avatar\" : \""+AVATAR2+"\","
				+ "                \"RG_masterID\" : "+MASTERID2+""
				+ "    }" 
				+ "  ]" 
				+ "}"; 

		json = new JSONObject(jsonString);
		modelList = new ArrayList<RadinGroupModel>();
		modelList.add(new RadinGroupModel(ID, dtf.parseDateTime(CREATIONDATE), NAME, DESCRIPTION, AVATAR));
		modelList.add(new RadinGroupModel(ID2, dtf.parseDateTime(CREATIONDATE2), NAME2, DESCRIPTION2, AVATAR2));
		
		json2 = new JSONObject(jsonString2);
		modelList2 = new ArrayList<RadinGroupModel>();
		modelList2.add(new RadinGroupModel(ID, dtf.parseDateTime(CREATIONDATE), NAME, DESCRIPTION, AVATAR, MASTERID));
		modelList2.add(new RadinGroupModel(
				ID2, dtf.parseDateTime(CREATIONDATE2), NAME2, DESCRIPTION2, AVATAR2, MASTERID2));

	}

	public void testJsonToModelList() throws JSONException {

		RadinGroupJSONParser radinGroup = new RadinGroupJSONParser();
		List<RadinGroupModel> modelListTest = radinGroup.getModelsFromJson(json);
		assertEquals(modelList, modelListTest);
		
		
		RadinGroupJSONParser radinGroup2 = new RadinGroupJSONParser();
		List<RadinGroupModel> modelListTest2 = radinGroup2.getModelsFromJson(json2);
		assertEquals(modelList2, modelListTest2);

	}

	public void testModeListToJson() throws JSONException {

		RadinGroupJSONParser radinGroup = new RadinGroupJSONParser();
		JSONObject jsonTest = radinGroup.getJsonFromModels(modelList);
		JSONAssert.assertEquals(json, jsonTest, true);
		
		
		RadinGroupJSONParser radinGroup2 = new RadinGroupJSONParser();
		JSONObject jsonTest2 = radinGroup2.getJsonFromModels(modelList2);
		JSONAssert.assertEquals(json2, jsonTest2, true);


	}
}
