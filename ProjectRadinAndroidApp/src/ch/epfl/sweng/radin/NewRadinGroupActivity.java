package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.RadinGroupStorageManager;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Activity allows the user to create a new list of expenses.
 * The user must provide a name for the list and the names of the people that he wants share this list with. 
 *
 */
public class NewRadinGroupActivity extends Activity {
	private final int mClientID = 1; //will be propagated from LoginActivity?
	private EditText mNameEdit;
	private boolean[] checkedItems;
	private ArrayList<UserModel> mFriendsModel;
	private  HashMap<String, UserModel> mNamesAndModel;
	private String[] mFriends;
	private ArrayList<UserModel> mParticipants;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_radingroup);
        
        retrieveData();
		
        mNameEdit = (EditText) findViewById(R.id.edit_name);
    }

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.new_radingroup, menu);
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
	
	/**
	 * Shows AlertDialog containing friends to select
	 *
	 */
	public void showDialog(View view) {
		if (mFriends != null) {
			createDialog().show();
		} else {
			Toast.makeText(getBaseContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Retrieves the data () from StorageManager
	 */
	private void retrieveData() {
		UserStorageManager usrStorageManager = UserStorageManager
				.getStorageManager();
		usrStorageManager.getAllFriendsById(mClientID, new RadinListener<UserModel>() {

			@Override
			public void callback(List<UserModel> items, StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					affectDataRetrieved(items);
				} else {
					Toast.makeText(getApplicationContext(),
							R.string.server_error, Toast.LENGTH_SHORT).show();
				}
			}
			
		});
	}
	
	private void affectDataRetrieved(List<UserModel> items) {
		mFriendsModel = new ArrayList<UserModel>(items);
		mNamesAndModel = new HashMap<String, UserModel>();
		mFriends = new String[mFriendsModel.size()];
		
		for (int i = 0; i < mFriendsModel.size(); i++) {
			UserModel currentUser = mFriendsModel.get(i);
			String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();
			mNamesAndModel.put(fullName, currentUser);
			mFriends[i] = fullName;
		}
		//initially false (default value)
		checkedItems = new boolean[mFriends.length];
	}
	
//	/**
//	 * This will be a method that retrieve Client's friends in the radinGeoupe from server
//	 * (Currently not implemented)
//	 * @return an array of the client's friends's names
//	 */
//	public String[] serverGetFriendsInGroup() {
//		String[] names = { "julie", "francois", "xavier", "Igor", "JT",
//				"Thierry", "Ismail", "Tanja", "Hibou", "Cailloux", "Poux" };
//		//TODO add proper methods
//		return names;
//	}
	
	/**
	 * Create a dialog that display friends to check to add to the new RadiGroup
	 * !!!similar to other dialog from RadinGRoupAddExpense!!!
	 * @param friendNames Client's Friends
	 * @return AlertDialog ready to be shown
	 */
	private AlertDialog createDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.multi_friend);

		final ListView listView = new ListView(this);
		StableArrayAdapter<String> adapter = new StableArrayAdapter<String>(this, 
																			android.R.layout.select_dialog_multichoice,
																			mFriends);
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		for (int i = 0; i < checkedItems.length; i++) {
			listView.setItemChecked(i, checkedItems[i]);
		}
		builder.setView(listView);

		// Set OK button
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				long[] checkedIds = listView.getCheckedItemIds();
				mParticipants = new ArrayList<UserModel>();

				checkedItems = new boolean[mFriends.length];
				for (int i = 0; i < checkedIds.length; i++) {
					checkedItems[(int) checkedIds[i]] = true;
					mParticipants.add(mNamesAndModel
							.get(mFriends[(int) checkedIds[i]]));
					// Log.i("participant" + i, mFriends[(int)
					// checkedIds[i]]);
				}
				// TODO show participants in a TextView
			}
		});
		// Set CANCEL button
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// nothing to do
			}
		});
		return builder.create();
	}
	
	public void createRadinGroup(View view) {
		String rdGrpName = mNameEdit.getText().toString();
    	if ((rdGrpName == null) || rdGrpName.equals("")) {
    		Toast.makeText(getBaseContext(), R.string.invalid_name, Toast.LENGTH_SHORT).show();
        } else if (mParticipants == null || mParticipants.isEmpty()) {
    		Toast.makeText(getBaseContext(), R.string.invalid_participants, Toast.LENGTH_SHORT).show();
        } else {
        	//valid data
        	RadinGroupModel rdinGrpModel = new RadinGroupModel(-1, 
        													   DateTime.now(), 
        													   rdGrpName, 
        													   "", 
        													   "");
        	RadinGroupStorageManager rdGrpStorageManager = RadinGroupStorageManager.getStorageManager();
        	ArrayList<RadinGroupModel> rdGroupToCreate = new ArrayList<RadinGroupModel>();
        	rdGroupToCreate.add(rdinGrpModel);
        	rdGrpStorageManager.create(rdGroupToCreate, new RadinListener<RadinGroupModel>() {
				@Override
				public void callback(List<RadinGroupModel> items, StorageManagerRequestStatus status) {
					if (status == StorageManagerRequestStatus.SUCCESS) {
						((Activity) getApplicationContext()).finish();
						Toast.makeText(getBaseContext(), R.string.rd_group_created, Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getBaseContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
					}
				}
        		
        	});
        	// WaitingDialog until callback is called	
        }
    }
}