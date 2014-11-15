package ch.epfl.sweng.radin.test.databases;

import java.util.List;
import java.util.Map;

import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.Visibility;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.TextView;
import ch.epfl.sweng.radin.DatabaseRadinGroupTableActivity;
import ch.epfl.sweng.radin.R;

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
	private static final String TAG = "DatabaseRadinGroupTableActivityEspressoTest";
	private static final int SLEEP_TIME = 100;
	private DatabaseRadinGroupTableActivity mRadinGroupActivity;

	public DatabaseRadinGroupTableActivityEspressoTest() {
		super(DatabaseRadinGroupTableActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mRadinGroupActivity = getActivity();
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
		assertTrue(withEffectiveVisibility(Visibility.VISIBLE).matches(withId(R.id.submitRadinGroupToDB)));
		onView(allOf(withId(R.id.submitRadinGroupToDB))).perform(scrollTo(), click());
		
		List<Map<String, String>> rows = mRadinGroupActivity
				.getEverythingFromRadinGroupTable();
		assertEquals(rows.size(), 1);
		Map<String, String> uniqueRow = rows.get(0);
		uniqueRow.get(0);

	}

}
