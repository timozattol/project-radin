package ch.epfl.sweng.radin.test.databases;

import ch.epfl.sweng.radin.databases.DatabaseOpenHelper;
import ch.epfl.sweng.radin.databases.RadinGroupTableHelper;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_database_radin_group_table);
		Button submitRadinGroupToDatabaseBtn = (Button) findViewById(R.id.submitRadinGroupToDB);
		submitRadinGroupToDatabaseBtn
		.setOnClickListener(submitRadinGroupOnClickListener);
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

	private ContentValues getContentValuesFromFields() {
		ContentValues values = new ContentValues();
		values.put(RadinGroupTableHelper.Column.RID.getSqlName(),
				returnText((TextView) findViewById(R.id.RID)));
		values.put(RadinGroupTableHelper.Column.RG_AVATAR.getSqlName(),
				returnText((TextView) findViewById(R.id.RGAvatar)));
		values.put(RadinGroupTableHelper.Column.RG_CREATION_DATE.getSqlName(), 
				returnText((TextView) findViewById(R.id.RGCreationDate)));
		values.put(RadinGroupTableHelper.Column.RG_DELETED_AT.getSqlName(), 
				returnText((TextView) findViewById(R.id.RGDeletedAt)));
		values.put(RadinGroupTableHelper.Column.RG_DESCRIPTION.getSqlName(), 
				returnText((TextView) findViewById(R.id.RGDescription)));
		values.put(RadinGroupTableHelper.Column.RG_END_DATE.getSqlName(), 
				returnText((TextView) findViewById(R.id.RGEndedAt)));
		values.put(RadinGroupTableHelper.Column.RG_GROUP.getSqlName(),
				returnText((TextView) findViewById(R.id.RGGroup)));
		values.put(RadinGroupTableHelper.Column.RG_MASTER_RID.getSqlName(), 
				returnText((TextView) findViewById(R.id.RGMasterID)));
		values.put(RadinGroupTableHelper.Column.RG_NAME.getSqlName(), 
				returnText((TextView) findViewById(R.id.RGName)));
		
		return values;
	}
	
	private String returnText(TextView textView) {
		return textView.getText().toString();
	}

	private void insertFieldsInDB() {
		DatabaseOpenHelper dbOpenHelper = new DatabaseOpenHelper(this,
				null);
		SQLiteDatabase writableDB = dbOpenHelper.getWritableDatabase();
		writableDB.insert(RadinGroupTableHelper.TABLE_RADIN_GROUP,
				null, getContentValuesFromFields()); // TODO checkout info
		// about nullHack
	}
	
	private OnClickListener submitRadinGroupOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			insertFieldsInDB();
		}
	};
}
