package ch.epfl.sweng.radin;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Activity allows the user to create a new list of expenses.
 * The user must provide a name for the list and the names of the people that he wants share this list with. 
 *
 */
public class NewRadinGroupActivity extends DashBoardActivity {
	private final int mClientID = 1234; //will be propagated from LoginActivity?
	private EditText mNameEdit;
	private boolean[] checkedItems;
	private String[] mFriends;
	private ArrayList<String> mParticipants;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_radingroup);
        setHeader(getString(R.string.new_radingroup), true, true);
        
        retrieveData();
        createDialog();
		
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
		createDialog().show();
	}
	
	/**
	 * Retrieves the data () from StorageManager
	 */
	public void retrieveData() {
		mFriends = serverGetFriendsInGroup();
		//initially false (default value)
		checkedItems = new boolean[mFriends.length];
	}
	
	/**
	 * This will be a method that retrieve Client's friends in the radinGeoupe from server
	 * (Currently not implemented)
	 * @return an array of the client's friends's names
	 */
	public String[] serverGetFriendsInGroup() {
		String[] names = { "julie", "francois", "xavier", "Igor", "JT",
				"Thierry", "Ismail", "Tanja", "Hibou", "Cailloux", "Poux" };
		//TODO add proper methods
		return names;
	}
	
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
		StableArrayAdapter<String> adapter =
				new StableArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, mFriends);
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
				mParticipants = new ArrayList<String>();
		
				checkedItems = new boolean[mFriends.length];
				for (int i = 0; i < checkedIds.length; i++) {
					checkedItems[(int) checkedIds[i]] = true;
					mParticipants.add(mFriends[(int) checkedIds[i]]);
					//Log.i("participant" + i, mFriends[(int) checkedIds[i]]);
				}
				//TODO show participants in a TextView
			}
		});
		//Set CANCEL button
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				//nothing to do
			}
		});
		return builder.create();
	}
	
	public void createRadinGroup(View view) {
		String listName = mNameEdit.getText().toString();
    	if ((listName == null) || listName.equals("")) {
    		Toast.makeText(getBaseContext(), R.string.invalid_name, Toast.LENGTH_SHORT).show();
        } else if (mParticipants == null || mParticipants.isEmpty()) {
    		Toast.makeText(getBaseContext(), R.string.invalid_participants, Toast.LENGTH_SHORT).show();
        } else {
        	//valid data
        	//TODO USE STORAGEMANAGER + LISTENER TO SEND TO SERV
        	Toast.makeText(getBaseContext(), "Radin Group created", Toast.LENGTH_LONG).show();
        	this.finish();
        }
    }
}