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
	private static final DateTime TODAY = DateTime.now();
	private static final DateTime TOMORROW = TODAY.plusDays(1);
	private static final DateTime AFTER_TOMORROW = TOMORROW.plusDays(1);
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
	private static final String TAG = "DatabaseRadinGroupTableActivityEspressoTest";
	// private DatabaseRadinGroupTableActivity mRadinGroupActivity;
	
	public DatabaseRadinGroupTableActivityEspressoTest() {
		super(DatabaseRadinGroupTableActivity.class);

	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Log.d(TAG, "SETUP");
		getActivity();
		Log.d(TAG, "SETUP_DONE");
	}

	public void testRadinGroupActivityNotNull() {
		assertNotNull(getActivity());
	}
	
	public void testPuttingRadinGroupModelIntoDB() {
		RadinGroupModel defaultRadinGroup = new RadinGroupModel(0, TODAY,
				DEFAULT_GROUP_NAME, DEFAULT_DESCRIPTION, DEFAULT_AVATAR,
				DEFAULT_MASTER_ID);
		List<RadinGroupModel> radinGroups = new ArrayList<RadinGroupModel>();
		radinGroups.add(defaultRadinGroup);
		getActivity().sendRadinGroupModelToDB(radinGroups);
		List<Map<String, String>> rowsColumnToValue = getActivity()
				.getEverythingFromRadinGroupTable();
		assertEquals(1, rowsColumnToValue.size());
		Map<String, String> defaultRadinGroupAsMap = rowsColumnToValue.get(0);
		assertEquals(0,
				defaultRadinGroupAsMap.get(RadinGroupTableHelper.Column.RID
						.getSqlName()));
	}
}
