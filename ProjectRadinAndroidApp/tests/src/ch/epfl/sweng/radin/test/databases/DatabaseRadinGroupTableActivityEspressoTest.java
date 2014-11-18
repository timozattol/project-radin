package ch.epfl.sweng.radin.test.databases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.DatabaseRadinGroupTableActivity;
import ch.epfl.sweng.radin.databases.RadinGroupTableHelper;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

/**
 * @author Simonchelbc
 *
 */
public class DatabaseRadinGroupTableActivityEspressoTest extends
		ActivityInstrumentationTestCase2<DatabaseRadinGroupTableActivity> {
	private static final DateTime TODAY_CLOCK = DateTime.now();
	private static final String NOW = TODAY_CLOCK.toString();
	private static final DateTime TOMORROW_CLOCK = TODAY_CLOCK.plusDays(1);
	private static final DateTime AFTER_TOMORROW_CLOCK = TOMORROW_CLOCK.plusDays(1);
	private static final int DEFAULT_RADIN_GROUP_ID = 0;
	private static final int DEFAULT_MASTER_ID = 10;
	private static final String DEFAULT_AVATAR = "<path-to-default-avatar>"; // TODO
	// change
	// when
	// we
	// have
	// avatars
	private static final String DEFAULT_GROUP_NAME = "SuperRadins";
	private static final String DEFAULT_DESCRIPTION = "Throw your debts away";
	private static final String NEW_GROUP_NAME = "VeryRadins";
	private static final String NEW_DESCRIPTION = "Throw the debters away";
	
	private static final String TAG = "DatabaseRadinGroupTableActivityEspressoTest";
	// private DatabaseRadinGroupTableActivity mRadinGroupActivity;
	private RadinGroupModel defaultRadinGroup;
	
	public DatabaseRadinGroupTableActivityEspressoTest() {
		super(DatabaseRadinGroupTableActivity.class);

	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Log.d(TAG, "SETUP");
		getActivity();
		defaultRadinGroup = new RadinGroupModel(DEFAULT_RADIN_GROUP_ID, TODAY_CLOCK,
				DEFAULT_GROUP_NAME, DEFAULT_DESCRIPTION, DEFAULT_AVATAR,
				DEFAULT_MASTER_ID);
		Log.d(TAG, "SETUP_DONE");
	}

	public void testRadinGroupActivityNotNull() {
		assertNotNull(getActivity());
	}
	
	public void testInsertOneRadinGroupInDB() {
		List<RadinGroupModel> radinGroups = new ArrayList<RadinGroupModel>();
		radinGroups.add(defaultRadinGroup);
		getActivity().sendRadinGroupModelToDB(radinGroups);
		List<Map<String, String>> rowsColumnToValue = getActivity()
				.getEverythingFromRadinGroupTable();
		assertEquals(1, rowsColumnToValue.size());
		Map<String, String> defaultRadinGroupAsMap = rowsColumnToValue.get(0);
		assertEquals(DEFAULT_RADIN_GROUP_ID,
				Integer.parseInt(defaultRadinGroupAsMap.get(RadinGroupTableHelper.Column.RID
						.toString())));
		assertEquals(DEFAULT_AVATAR, 
				defaultRadinGroupAsMap.get(RadinGroupTableHelper
						.Column.RG_AVATAR.toString()));
		assertEquals(DEFAULT_DESCRIPTION, 
				defaultRadinGroupAsMap.get(RadinGroupTableHelper
						.Column.RG_DESCRIPTION.toString()));
		assertEquals(DEFAULT_GROUP_NAME, 
				defaultRadinGroupAsMap.get(RadinGroupTableHelper
						.Column.RG_NAME.toString()));
		assertEquals(DEFAULT_MASTER_ID, 
				Integer.parseInt(defaultRadinGroupAsMap.
						get(RadinGroupTableHelper.Column
								.RG_MASTER_RID.toString())));
		assertNull(defaultRadinGroupAsMap.get(
				RadinGroupTableHelper.Column.RG_END_DATE.toString()));
		assertNull(defaultRadinGroupAsMap.get(
				RadinGroupTableHelper.Column.RG_DELETED_AT.toString()));
		
		
//		assertEquals(TODAY_CLOCK.toString(), defaultRadinGroupAsMap.get(RadinGroupTableHelper.
//				Column.RG_CREATION_DATE.toString())); //hard to test on time because 
		//toString captures current time
	}
	
	public void testInsertEmptyListOfRadinGroups() throws Exception {
		try {
			List<RadinGroupModel> radinGroups = new ArrayList<RadinGroupModel>();		
			getActivity().sendRadinGroupModelToDB(radinGroups);
			fail("radinGroups cannot be Empty");
		} catch (IllegalArgumentException e) {
			//good
		}
	}
}
