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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This Activity allows the user to create a new list of expenses.
 * The user must provide a name for the list and the names of the people 
 * that he wants share this list with. 
 *
 */
public class NewRadinGroupActivity extends Activity {
	public static final int TIMES_TO_TRY = 3; 
	
	private SharedPreferences mPrefs;
	private int mClientId;
	private UserModel mClientModel;
	private EditText mNameEdit;
	private boolean[] checkedItems;
	private ArrayList<UserModel> mFriendsModel;
	private  HashMap<String, UserModel> mNamesAndModel;
	private String[] mFriends;
	private ArrayList<UserModel> mParticipants = new ArrayList<UserModel>();
	private int mRadinGroupId;
	private Activity mCurrentActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_radingroup);
        mNameEdit = (EditText) findViewById(R.id.edit_name);
        
        mPrefs = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
		mClientId = Integer.parseInt(mPrefs.getString(getString(R.string.username), "-1"));
		if (isClientIdValid()) {
	        retrieveData();
		}
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
	 * Retrieves the data (user's friends) from StorageManager
	 */
	private void retrieveData() {
		UserStorageManager usrStorageManager = UserStorageManager.getStorageManager();
		usrStorageManager.getFriendsOfUserWithId(mClientId, new RadinListener<UserModel>() {
			@Override
			public void callback(List<UserModel> items, StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					affectDataRetrieved(items);
				} else {
					Toast.makeText(getApplicationContext(),	R.string.server_error3, Toast.LENGTH_SHORT).show();
					mCurrentActivity.finish();
				}
			}
			
		});
	}
	
	/**
	 * Sets Activity's friends lists.
	 * @param items users's Friends 
	 */
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
	
	/**
	 * Create a dialog that display friends to check to add to the new RadiGroup
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
					mParticipants.add(mNamesAndModel.get(mFriends[(int) checkedIds[i]]));
				}
				String participants = getResources().getString(R.string.participants);
				for (UserModel usr : mParticipants) {
					participants += " " + usr.getFirstName();
				}
				TextView participantsTextView = (TextView) findViewById(R.id.participants_in_radin_group);
				participantsTextView.setText(participants);
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
	
	/**
	 * Called by create button, checks validity of user's input, starts the server interaction if valid.
	 */
	public void createRadinGroup(View view) {
		if (mClientModel != null) {
			mParticipants.add(mClientModel);
		} else {
			mCurrentActivity.finish();
			Toast.makeText(getBaseContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
		}
		String rdGrpName = mNameEdit.getText().toString();
		Button createButton = (Button) findViewById(R.id.create);
		createButton.setClickable(false);
    	if ((rdGrpName == null) || rdGrpName.equals("")) {
    		Toast.makeText(getBaseContext(), R.string.invalid_name, Toast.LENGTH_SHORT).show();
    		createButton.setClickable(true);
        } else if (mParticipants == null || mParticipants.isEmpty()) {
    		Toast.makeText(getBaseContext(), R.string.invalid_participants, Toast.LENGTH_SHORT).show();
    		createButton.setClickable(true);
        } else {
        	//valid data
        	sendRadinGroup(rdGrpName);
        }
    }
	
	/**
	 * Posts the radinGroup to the database, if action is successful, calls sendParticipants. 
	 * @param name the new radinGroups's name
	 */
	private void sendRadinGroup(String name) {
		RadinGroupModel rdinGrpModel = new RadinGroupModel(1, DateTime.now(), name, "", "");
    	RadinGroupStorageManager rdGrpStorageManager = RadinGroupStorageManager.getStorageManager();
    	ArrayList<RadinGroupModel> rdGroupToCreate = new ArrayList<RadinGroupModel>();
    	rdGroupToCreate.add(rdinGrpModel);
    	rdGrpStorageManager.create(rdGroupToCreate, new RadinListener<RadinGroupModel>() {
			@Override
			public void callback(List<RadinGroupModel> items, StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					mRadinGroupId = items.get(0).getRadinGroupID();
					sendParticipants(mParticipants.size() * TIMES_TO_TRY);
				} else {
					Toast.makeText(getBaseContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
				}
			}
    	});
	}
	
	/**
	 * Posts participants one by one to the database. This method may be called more than once for every user
	 * depending on TIMES_TO_TRY (max calls is TIMES_TO_TRY*number of users to send)
	 * @param iterationNumber
	 */
	private void sendParticipants(final int iterationNumber) {
		if (iterationNumber > 0) {
			UserStorageManager usrStorageManager = UserStorageManager.getStorageManager();
			ArrayList<UserModel> usrList = new ArrayList<UserModel>();
			usrList.add(mParticipants.get(0));
			usrStorageManager.postMemberToRadinGroup(mRadinGroupId, usrList, new RadinListener<UserModel>() {
				@Override
				public void callback(List<UserModel> items, StorageManagerRequestStatus status) {
					if (status == StorageManagerRequestStatus.SUCCESS) {
						mParticipants.remove(0);
						if (!mParticipants.isEmpty()) {
							sendParticipants(iterationNumber - 1);
						} else {
							mCurrentActivity.finish();
							Toast.makeText(getBaseContext(), R.string.rd_group_created, Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getBaseContext(), 
									   R.string.server_error_participants, 
									   Toast.LENGTH_SHORT).show();
						mCurrentActivity.finish();
					}
				}
			});
		} else {
			mCurrentActivity.finish();
			Toast.makeText(getBaseContext(), R.string.server_error_participants, Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Checks whether the id well set in the shared preferences
	 * Disables create button if not.
	 */
	private boolean isClientIdValid() {
		if (mClientId == -1) {
			Toast.makeText(getApplicationContext(), R.string.bad_id, Toast.LENGTH_SHORT).show();
			((Button) findViewById(R.id.create)).setClickable(false);
			return false;
		} else {
			UserStorageManager.getStorageManager().getById(mClientId, new RadinListener<UserModel>() {
				@Override
				public void callback(List<UserModel> items, StorageManagerRequestStatus status) {
					mClientModel = items.get(0);			
				}
			});
			return true;
		}
	}
}