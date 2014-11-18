package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.databases.Database;
import ch.epfl.sweng.radin.databases.RadinGroupTableHelper;
import ch.epfl.sweng.radin.storage.Model;
import ch.epfl.sweng.radin.storage.RadinGroupModel;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * @author Simonchelbc
 * Demonstration activity using the database
 */
public class DatabaseRadinGroupTableActivity extends Activity {
	private static final String TAG = "DatabaseRadinGroupTableActivity";
	
	private static final DateTime TODAY = DateTime.now();
	private static final DateTime TOMORROW = TODAY.plusDays(1);
	private static final DateTime AFTER_TOMORROW = TOMORROW.plusDays(1);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "created");
		setContentView(R.layout.activity_database_radin_group_table);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.database_radin_group_table, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private DateTime getTimeFrom(String strTime) {
		DateTime time = null;
		if (strTime != null) {
			if (strTime.equals("today")) {
				time = TODAY;
			} else if (strTime.equals("tomorrow")) {
				time = TOMORROW;
			} else if (strTime.equals("after-tomorrow")) {
				time = AFTER_TOMORROW;
			}
		}
		return time;
	}

	private String returnText(TextView textView) {
		return textView.getText().toString();
	}

	/**
	 * @return {@code rowsColumnToValue} that associates to each row a map from column name to value 
	 */
	public List<Map<String, String>> getEverythingFromRadinGroupTable() {
		Database.initialize(this); //TODO Only do something if database initialized
		Cursor cursor = Database.query(RadinGroupTableHelper.TABLE_RADIN_GROUP, null,
				null, null, null, null, null);
		List<Map<String, String>> rowsColumnToValue = new ArrayList<Map<String, String>>();
		cursor.moveToFirst();
		do {
			rowsColumnToValue.add(rowPointedBy(cursor));
		} while (cursor.moveToNext());
		cursor.close();
		return rowsColumnToValue;
	}

	/**
	 * @param cursor that is currently pointing to a row
	 * @return a map associated with the row pointed by cursor, it's a mapping from column name to its value
	 */
	private Map<String, String> rowPointedBy(Cursor cursor) {
		Map<String, String> columnNameToValue = new HashMap<String, String>();
		for (String column : cursor.getColumnNames()) {
			columnNameToValue.put(column,
					cursor.getString(cursor.getColumnIndex(column)));
		}
		return columnNameToValue;
	}
	
	/**
	 * Used since using the button doesn't work but is meant to be 
	 * the code used in submitRadinGroupOnClickListener
	 * @return 
	 */
	public Map<Long, Model> sendRadinGroupModelToDB(List<RadinGroupModel> radinGroups) {
		if (radinGroups.isEmpty()) {
			throw new IllegalArgumentException("radin groups can't be empty lists");
		}
		Database.initialize(this.getApplicationContext());
		return Database.insert(radinGroups);
	}
	private RadinGroupModel getRadinGroupFromFields() { //TODO to finish
		
		int rid = Integer
				.parseInt(returnText((TextView) findViewById(R.id.RID)));
		String name = returnText((TextView) findViewById(R.id.RGName));
		String creationDate = returnText((TextView) findViewById(R.id.RGCreatedAt));
		String avatar = returnText((TextView) findViewById(R.id.RGAvatar));
		String description = returnText((TextView) findViewById(R.id.RGDescription));
		// values.put(RadinGroupTableHelper.Column.RG_GROUP.getSqlName(), //
		// TODO #rgGroup
		// returnText((TextView) findViewById(R.id.RGGroup)));
		returnText((TextView) findViewById(R.id.RGMasterID));
		returnText((TextView) findViewById(R.id.RGEndedAt));
		returnText((TextView) findViewById(R.id.RGDeletedAt));
		
		RadinGroupModel rg = new RadinGroupModel(rid,
				getTimeFrom(creationDate), name, description, avatar);
		return rg;
	}
	/**
	 * @param v
	 * Called when the user touches the button
	 */
	public void submitRadinGroupOnClickListener(View v) {
		List<RadinGroupModel> radinGroups = new LinkedList<RadinGroupModel>();
		radinGroups.add(getRadinGroupFromFields());
		Database.initialize(this);
		Database.insert(radinGroups);
	}
}
