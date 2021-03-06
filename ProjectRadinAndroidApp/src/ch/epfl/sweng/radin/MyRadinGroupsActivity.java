package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.managers.RadinGroupStorageManager;

/**
 * 
 * @author Fabien Zellweger
 * This activity lists all the RadinGroups to which the user is subscribed.
 *
 */
public class MyRadinGroupsActivity extends Activity {
	private final static int TEXT_SIZE = 30;
	private List<RadinGroupModel> mListRadinGroupsModel;
	private int mUserId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_radingroups);

		Button addBtn = (Button) findViewById(R.id.addBtn);
		addBtn.setOnClickListener(myRadinGroupsClickListener);

		SharedPreferences mPrefs = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
		mUserId = Integer.parseInt(mPrefs.getString(getString(R.string.username), ""));

		retrieveRadinGroups();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		retrieveRadinGroups();
	}

	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_radingroups, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
			case R.id.action_home:
				Intent intent = new Intent(this, HomeActivity.class);
				startActivity(intent);
				return true;
			case R.id.action_settings:

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void retrieveRadinGroups() {
		RadinGroupStorageManager radinGroupStorageManager =  RadinGroupStorageManager.getStorageManager();
		radinGroupStorageManager.getAllByUserId(mUserId, new RadinListener<RadinGroupModel>() {
			@Override
			public void callback(List<RadinGroupModel> items, StorageManagerRequestStatus status) {
				
				if (status == StorageManagerRequestStatus.SUCCESS) {
					
					mListRadinGroupsModel = new ArrayList<RadinGroupModel>(items);
					displayList();
				} else {
					displayErrorToast(getString(R.string.retrieving_radin_group_error));
				}
			}
		});
	}
	
	private void displayErrorToast(String message) {
	    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Call by the callback when he reach the radingroups belong to this user.
	 * Then diplay them with a OnclickListener
	 * @param mListRadinGroupsModel
	 */
	private void displayList() {
		LinearLayout myRadinGroupsLinearLayout = (LinearLayout) findViewById(R.id.myRadinGroupsLinearLayout);
		myRadinGroupsLinearLayout.removeAllViews();
		myRadinGroupsLinearLayout.setGravity(Gravity.LEFT);
		ProgressBar myRadinGroupProgressBar = (ProgressBar) findViewById(R.id.myRadinGroupsProgressBar);
		myRadinGroupsLinearLayout.removeView(myRadinGroupProgressBar);
		
		for (int i = 0; i < mListRadinGroupsModel.size(); i++) {
			TextView radinGroupsTextView = new TextView(this);
			String radinGroupsName = ((RadinGroupModel) mListRadinGroupsModel.get(i)).getRadinGroupName();
			radinGroupsTextView.setText(radinGroupsName);
			radinGroupsTextView.setTextSize(TEXT_SIZE);
			radinGroupsTextView.setTag(i);
			radinGroupsTextView.setOnClickListener(myRadinGroupsClickListener);

			myRadinGroupsLinearLayout.addView(radinGroupsTextView);
		}

	}

	/**
	 * Choose the behaviors between the addRadinGroup button or the listed radinGroups
	 */
	private OnClickListener myRadinGroupsClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			Intent displayActivityIntent;
			switch(id) {
				case R.id.addBtn:
					displayActivityIntent = new Intent(v.getContext(), NewRadinGroupActivity.class);
					startActivity(displayActivityIntent);
					break;
				default:
					displayActivityIntent = new Intent(v.getContext(), RadinGroupViewActivity.class);
					Bundle bundle = ActionBar.makeModelToBundle(mListRadinGroupsModel.get((Integer) v.getTag()));
					displayActivityIntent.putExtras(bundle);
					startActivity(displayActivityIntent);
			}
		}
	};
}
