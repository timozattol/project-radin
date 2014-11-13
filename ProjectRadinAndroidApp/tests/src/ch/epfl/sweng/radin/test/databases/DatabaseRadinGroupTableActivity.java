package ch.epfl.sweng.radin.test.databases;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.databases.Database;
import ch.epfl.sweng.radin.databases.RadinGroupTableHelper;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.test.R;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
	private static final DateTime TODAY = DateTime.now();
	private static final DateTime TOMORROW = TODAY.plusDays(1);
	private static final DateTime AFTER_TOMORROW = TOMORROW.plusDays(1);
	private Database db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_database_radin_group_table);
		Button submitRadinGroupToDatabaseBtn = (Button) findViewById(R.id.submitRadinGroupToDB);
		submitRadinGroupToDatabaseBtn
		.setOnClickListener(submitRadinGroupOnClickListener);
		db = new Database(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_database_radin_group_table, menu);
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
		String creationDate = returnText((TextView) findViewById(R.id.RGCreationDate));
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
	
	private DateTime getTimeFrom(String strTime) {
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

	private void insertFieldsInDB() {
		List<RadinGroupModel> radinGroups = new LinkedList<>();
		radinGroups.add(getRadinGroupFromFields());
		db.insertRadinGroups(radinGroups);
	}
	
	private OnClickListener submitRadinGroupOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			insertFieldsInDB();
		}
	};
}
