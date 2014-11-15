package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.databases.Database;
import ch.epfl.sweng.radin.databases.RadinGroupTableHelper;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Simonchelbc
 * 
 */
public class DatabaseRadinGroupTableActivity extends Activity {
	private static final String TAG = "DatabaseRadinGroupTableActivity";
	private static final DateTime TODAY = DateTime.now();
	private static final DateTime TOMORROW = TODAY.plusDays(1);
	private static final DateTime AFTER_TOMORROW = TOMORROW.plusDays(1);
	private Database db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database_radin_group_table);
		Button submitRadinGroupToDatabaseBtn = (Button) findViewById(R.id.submitRadinGroupToDB);
		submitRadinGroupToDatabaseBtn
		.setOnClickListener(submitRadinGroupOnClickListener);
		db = new Database(this);
		Log.v(TAG, "created");
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

	private RadinGroupModel getRadinGroupFromFields() {
		
		int rid = Integer.parseInt(returnText((TextView) findViewById(R.id.RID)));
		String name = returnText((TextView) findViewById(R.id.RGName));
		String creationDate = returnText((TextView) findViewById(R.id.RGCreatedAt));
		String avatar = returnText((TextView) findViewById(R.id.RGAvatar));
		String description = returnText((TextView) findViewById(R.id.RGDescription));
//		values.put(RadinGroupTableHelper.Column.RG_GROUP.getSqlName(), // TODO #rgGroup
//				returnText((TextView) findViewById(R.id.RGGroup)));
		returnText((TextView) findViewById(R.id.RGMasterID));
		returnText((TextView) findViewById(R.id.RGEndedAt));
		returnText((TextView) findViewById(R.id.RGDeletedAt));
		
		RadinGroupModel rg = new RadinGroupModel(rid, getTimeFrom(creationDate), name, description, avatar);
		return rg;
	}
	
	DateTime getTimeFrom(String strTime) {
		DateTime time = null;
		switch (strTime) {
			case "today":
				time = TODAY;
				break;
			case "tomorrow":
				time = TOMORROW;
				break;
			case "after-tomorrow":
				time = AFTER_TOMORROW;
				break;
			default:
				break;
		}
		return time;
	}
	
	private String returnText(TextView textView) {
		return textView.getText().toString();
	}
	
	public List<Map<String, String>> getEverythingFromRadinGroupTable() {
		Cursor cursor = db.query(RadinGroupTableHelper.TABLE_RADIN_GROUP, null,
				null, null, null, null, null);
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		cursor.moveToFirst();
		do {
			rows.add(rowPointedBy(cursor));
		} while (cursor.moveToNext());
		cursor.close();
		return rows;
	}

	/**
	 * @param cursor
	 *            that is currently pointing to a row
	 * @return
	 */
	private Map<String, String> rowPointedBy(Cursor cursor) {
		Map<String, String> columnNameToValue = new HashMap<String, String>();
		for (String column : cursor.getColumnNames()) {
			columnNameToValue.put(column,
					cursor.getString(cursor.getColumnIndex(column)));
		}
		return columnNameToValue;
	}
	
	private OnClickListener submitRadinGroupOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			List<RadinGroupModel> radinGroups = new LinkedList<>();
			radinGroups.add(getRadinGroupFromFields());
			db.insert(radinGroups);
		}
	};
}
