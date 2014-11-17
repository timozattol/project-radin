package ch.epfl.sweng.radin.test.databases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;

import com.google.android.apps.common.testing.ui.espresso.ViewInteraction;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.Visibility;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.TextView;
import ch.epfl.sweng.radin.DatabaseRadinGroupTableActivity;
import ch.epfl.sweng.radin.R;
import ch.epfl.sweng.radin.databases.RadinGroupTableHelper;
import ch.epfl.sweng.radin.storage.RadinGroupModel;

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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * @author Simonchelbc <{@link ActivityInstrumentationTestCase2} running tests
 *         over {@link DatabaseRadinGroupTableActivity} to verify implementation
 *         of {@link DatabaseOpenHelper} with {@link RadinGroupTableHelper}
 *         table
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
	private static final int SLEEP_TIME = 100;

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

	private void typeTextInField(int rId, String text) {
		onView(withId(rId)).perform(scrollTo(), typeText(text));
	}

	public void testDBAfterInsert() throws Exception {
		typeTextInField(R.id.RID, "0");
		typeTextInField(R.id.RGCreatedAt, "today");
		typeTextInField(R.id.RGDeletedAt, "after-tomorrow");
		typeTextInField(R.id.RGEndedAt, "tomorrow");
		typeTextInField(R.id.RGName, "test group");
		typeTextInField(R.id.RGAvatar, "img/avatar1.png");
		typeTextInField(R.id.RGMasterID, "10");
		typeTextInField(R.id.RGDescription, "A cool group");
		closeSoftKeyboard();

		Log.v(TAG, "performing click on submitRadinGroupToDB");
		onView(withId(R.id.submitRadinGroupToDB)).perform(scrollTo());
		assertTrue(withEffectiveVisibility(Visibility.VISIBLE).matches(
				withId(R.id.submitRadinGroupToDB)));
		ViewInteraction submitButton = onView(withId(R.id.submitRadinGroupToDB));
		submitButton.perform(scrollTo(), click());

		List<Map<String, String>> rows = getActivity()
				.getEverythingFromRadinGroupTable();
		assertEquals(rows.size(), 1);
		Map<String, String> uniqueRow = rows.get(0);
		uniqueRow.get(0);
	}

}
