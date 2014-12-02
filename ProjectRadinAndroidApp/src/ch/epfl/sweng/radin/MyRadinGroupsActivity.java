package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.managers.RadinGroupStorageManager;
import android.app.Activity;
import android.content.Intent;
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

/**
 * 
 * @author Fabien Zellweger
 * This activity get all the radin group from the connected user and list them
 * to give access.
 *
 */
public class MyRadinGroupsActivity extends Activity {
	private final static int TEXT_SIZE = 30;
	private List<RadinGroupModel> mListRadinGroupsModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_radingroups);


		Button addBtn = (Button) findViewById(R.id.addBtn);
		addBtn.setOnClickListener(myRadinGroupsClickListener);

		//TODO This is a fake userId used to test the app, need to remplace this when we got one.
		int userId = 0;

		RadinGroupStorageManager radinGroupStorageManager =  RadinGroupStorageManager.getStorageManager();
		
		radinGroupStorageManager.getAllByUserId(userId, new RadinListener<RadinGroupModel>() {

			@Override
			public void callback(List<RadinGroupModel> items, StorageManagerRequestStatus status) {

			    if (status == StorageManagerRequestStatus.SUCCESS) {      
			        displayList(items);
			        mListRadinGroupsModel = new ArrayList<RadinGroupModel>(items);
			    } else {
			        displayErrorToast("There was an error, please try again");
			    }
			}
		});
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
	
	private void displayErrorToast(String message) {
	    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Call by the callback when he reach the radingroups belong to this user.
	 * Then diplay them with a OnclickListener
	 * @param myRadinGroupsList
	 */
	private void displayList(List<RadinGroupModel> myRadinGroupsList) {
		LinearLayout myRadinGroupsLinearLayout = (LinearLayout) findViewById(R.id.myRadinGroupsLinearLayout);
		myRadinGroupsLinearLayout.setGravity(Gravity.LEFT);
		ProgressBar myRadinGroupProgressBar = (ProgressBar) findViewById(R.id.myRadinGroupsProgressBar);
		myRadinGroupsLinearLayout.removeView(myRadinGroupProgressBar);
		
		for (int i = 0; i < myRadinGroupsList.size(); i++) {
			TextView radinGroupsTextView = new TextView(this);
			String radinGroupsName = ((RadinGroupModel) myRadinGroupsList.get(i)).getRadinGroupName();
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
