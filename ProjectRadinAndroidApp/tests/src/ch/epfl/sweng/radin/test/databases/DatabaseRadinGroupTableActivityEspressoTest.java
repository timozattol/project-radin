package ch.epfl.sweng.radin.test.databases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.joda.time.DateTime;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.scrollTo;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isEnabled;

import com.google.android.apps.common.testing.ui.espresso.ViewInteraction;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.Visibility;

import ch.epfl.sweng.radin.DatabaseRadinGroupTableActivity;
import ch.epfl.sweng.radin.R;
import ch.epfl.sweng.radin.databases.Database;
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
	private static DatabaseRadinGroupTableActivity mActivity;
	private RadinGroupModel defaultRadinGroup;
	
	public DatabaseRadinGroupTableActivityEspressoTest() {
		super(DatabaseRadinGroupTableActivity.class);

	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Log.d(TAG, "SETUP");
		mActivity = getActivity();
		Database.initialize(mActivity);
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
		List<Map<String, String>> rowsColumnToValue = Database
				.getEverythingFromRadinGroupTable();
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
	
	private void typeTextInField(int rId, String text) {
		onView(withId(rId)).perform(scrollTo(), typeText(text));
	}

	public void testDBAfterInsert() throws Exception {
		Log.v(TAG, "Beginning testDBAterInsert");
		final String nextRID = String.valueOf(Database.getRadinGroupTableSize());
		typeTextInField(R.id.RID, nextRID);
		typeTextInField(R.id.RGCreatedAt, "today");
		typeTextInField(R.id.RGDeletedAt, "after-tomorrow");
		typeTextInField(R.id.RGEndedAt, "tomorrow");
		typeTextInField(R.id.RGName, "test group");
		typeTextInField(R.id.RGAvatar, "img/avatar2.png");
		typeTextInField(R.id.RGMasterID, "10");
		typeTextInField(R.id.RGDescription, "A cool group");
		closeSoftKeyboard();

		Log.v(TAG, "performing click on submitRadinGroupToDB");
		ViewInteraction submitButton = onView(withId(R.id.submitRadinGroupToDB)); //buttons sends all to DB
		submitButton.perform(scrollTo(), click()); 

		Log.v(TAG, "after");
		List<Map<String, String>> rows = Database
				.getEverythingFromRadinGroupTable();
		for (Map<String, String> map : rows) {
			for (String key : map.keySet()) {
				Log.v(TAG, key + " -> " + map.get(key));
			}
		}
		Map<String, String> uniqueRow = rows.get(rows.size() - 1);
		assertEquals(nextRID, uniqueRow.get(RadinGroupTableHelper.Column.RID.toString()));
		assertEquals("img/avatar1.png", uniqueRow.get(RadinGroupTableHelper.Column.RG_AVATAR.toString()));
	} 
}
