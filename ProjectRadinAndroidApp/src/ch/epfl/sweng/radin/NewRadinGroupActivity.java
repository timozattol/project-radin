package ch.epfl.sweng.radin;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

/**
 * Activity allows the user to create a new list of expenses.
 * The user must provide a name for the list and the names of the people that he wants share this list with. 
 *
 */
public class NewRadinGroupActivity extends Activity {
	private final int mClientID = 0;
	private String[] mPeopleInRadinGroup;
	private EditText mNameEdit;
	private boolean[] checkedItems;
	private String[] mFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_radingroup);
        
        retrieveData();
        createDialog(mFriends);
		
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
	 * Shows AlertDialog
	 *
	 */
	public void showDialog(View view) {
		createDialog(mFriends).show();
	}
	
	
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
	private AlertDialog createDialog(final String[] friendNames) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.multi_friend);
		
		final ListView listView = new ListView(this);
		StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.select_dialog_multichoice, friendNames);
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

				checkedItems = new boolean[friendNames.length];
				for (int i = 0; i < checkedIds.length; i++) {
					checkedItems[(int) checkedIds[i]] = true;
				}
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
    	if (listName.equals("") || (listName == null)) {
    		Toast.makeText(getBaseContext(), "You must provide a name for the list", Toast.LENGTH_SHORT).show(); // should be in values/string
        //} else if (friendsSelected.isEmpty) {
    	//	Toast.makeText(getBaseContext(), "Please select at list one person to create a groupe", Toast.LENGTH_SHORT).show();
        } else {
        	//TODO USE STORAGEMANAGER + LISTENER TO SEND TO SERV
        	Toast.makeText(getBaseContext(), "Radin Group created", Toast.LENGTH_LONG).show();
        	this.finish();
        }
    }
	
	/**
	 * TODO make it generic and put it outside the class (shared with RadinGroupAddExpenseActivity)
	 * 
	 * A stable ids adapter, needed to use getCheckedItemIds() on the listView using the adapter
	 * Adapted from: http://www.vogella.com/tutorials/AndroidListView/article.html
	 */
	public class StableArrayAdapter extends ArrayAdapter<String> {
		private HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				String[] objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.length; ++i) {
				mIdMap.put(objects[i], i);
			}
		}
		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}
		@Override
		public boolean hasStableIds() {
			return true;
		}
	}
}