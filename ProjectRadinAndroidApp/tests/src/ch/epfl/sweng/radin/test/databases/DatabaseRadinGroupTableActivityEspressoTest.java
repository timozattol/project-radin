package ch.epfl.sweng.radin.test.databases;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.databases.Database;
import ch.epfl.sweng.radin.databases.RadinGroupTableHelper;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.test.R;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
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

	public DatabaseRadinGroupTableActivityEspressoTest(
			Class<DatabaseRadinGroupTableActivity> activityClass) {
		super(activityClass);
		getActivity();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

	}

	private void typeTextInField(int rId, String text) {
		onView(withId(rId)).perform(typeText(text));
	}

	public void testGoodContentValueWithFields() throws Exception {
		typeTextInField(R.id.RGAvatar, "0");
		typeTextInField(R.id.RGCreationDate, "today");
		typeTextInField(R.id.RGDescription, "A cool group");
		typeTextInField(R.id.RGGroup, "group radin");
		typeTextInField(R.id.RGMasterID, "10");
		typeTextInField(R.id.RGAvatar, "img/avatar1.png");
		typeTextInField(R.id.RGEndedAt, "tomorrow");
		typeTextInField(R.id.RGDeletedAt, "after-tomorrow");
		onView(withId(R.id.submitRadinGroupToDB)).perform(click());
	}

	private List<RadinGroupModel> getAllModels(SQLiteDatabase db) {
		List<RadinGroupModel> rgs = new ArrayList<>();
		Cursor cursor = db.query(RadinGroupTableHelper.TABLE_RADIN_GROUP,
				RadinGroupTableHelper.names(), null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			rgs.add(cursorToRadinGroupModel(cursor));
		}
		cursor.close();
		return rgs;
	}

	private RadinGroupModel cursorToRadinGroupModel(Cursor cursor) {
//		String[] columns = cursor.getColumnNames();
//		for(String col: columns) {
//			
//		}
		int rid = cursor.getColumnIndex(RadinGroupTableHelper.Column.RID
				.getSqlName());

		int RGavatar = cursor
				.getColumnIndex(RadinGroupTableHelper.Column.RG_AVATAR
						.getSqlName());

		int rgCreation = cursor
				.getColumnIndex(RadinGroupTableHelper.Column.RG_CREATION_DATE
						.getSqlName());

		int rgDeleted = cursor
				.getColumnIndex(RadinGroupTableHelper.Column.RG_DELETED_AT
						.getSqlName());

		int rgEndDate = cursor
				.getColumnIndex(RadinGroupTableHelper.Column.RG_END_DATE
						.getSqlName());

		int rgDescr = cursor
				.getColumnIndex(RadinGroupTableHelper.Column.RG_DESCRIPTION
						.getSqlName());

//		int rgGroup = cursor
//				.getColumnIndex(RadinGroupTableHelper.Column.RG_GROUP
//						.getSqlName()); //TODO #rgGroup

		int rgMasterId = cursor
				.getColumnIndex(RadinGroupTableHelper.Column.RG_MASTER_RID
						.getSqlName());

		int rgName = cursor.getColumnIndex(RadinGroupTableHelper.Column.RG_NAME
				.getSqlName());
		

		cursor.getString(rgCreation);
		cursor.getString(rgDeleted);
		cursor.getString(rgEndDate);
		RadinGroupModel rgModel = new RadinGroupModel(rid, null, null, null,
				null, 0);
		return rgModel;
	}

	
}
