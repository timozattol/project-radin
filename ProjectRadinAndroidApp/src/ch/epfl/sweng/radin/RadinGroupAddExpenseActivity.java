package ch.epfl.sweng.radin;

import java.util.ArrayList;

import ch.epfl.sweng.radin.storage.RadinGroupModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity allows the user to add an expense to a radinGroup by providing the creditor, 
 * debitors and the amount payed.
 * 
 * TODO class too big, split, refactor (both dialog creations are almost the same)
 */
public class RadinGroupAddExpenseActivity extends Activity {
	private RadinGroupModel mCurrentRadinGroupModel;
	//private static final int CLIENT_ID = 1234; //will be propagated from LoginActivity?
	private static final int DEFAULT_CREDITOR_SELECTION = 0;
	private ArrayList<String> mSelectedDebtors = new ArrayList<String>();
	private int mSelectedIndex = DEFAULT_CREDITOR_SELECTION;
	private String mSelectedCreditor;
	private double mAmount;
	private String[] mFriends;
	private String[] mFriendsAndClient;
	private boolean[] checkedItems;
	private String  mPurpose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_radingroup_add_expense);
		mSelectedCreditor = this.getResources().getString(R.string.creditor_selected);

		Bundle extras = getIntent().getExtras();
		mCurrentRadinGroupModel = ActionBar.getRadinGroupModelFromBundle(extras);

		String radinGroupTitle = mCurrentRadinGroupModel.getRadinGroupName();
		
		TextView addExpenseText = (TextView) findViewById(R.id.title_add_expense);
		addExpenseText.setText(addExpenseText.getText().toString() + radinGroupTitle);
		
		LinearLayout thisLayout = (LinearLayout) findViewById(R.id.addExpenseRadinGroupLayout);
		ActionBar.addActionBar(this, thisLayout, mCurrentRadinGroupModel);
		
		setDialogData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.radingroup_add_expense, menu);
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
	 * Shows an AlertDialog with a list of potential debtors to select.
	 * Shows a toast if data not ready
	 */
	public void showDebDialog(View view) {
		if (mFriends != null) {
			debtorDialog(serverGetFriendsInGroup()).show();
		} else {
			Toast.makeText(this, R.string.not_ready, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Shows an AlertDialog with a list of potential creditors to select and
	 * adds the user to this list of people.
	 * Shows a toast if data not ready
	 */
	public void showCredDialog(View view) {
		if (mFriendsAndClient != null) {
			creditorDialog(mFriendsAndClient).show();
		} else {
			Toast.makeText(this, R.string.not_ready, Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Retrieve friends and sets the arrays for the dialogs
	 */
	private void setDialogData() {
// =========================== Not yet possible=========================
//		StorageManagerListener listener = new StorageManagerListener();
//		UserStorageManager usrStManager = new UserStorageManager();
//		usrStManager.getFriendsById(CLIENT_ID, listener);
// =====================================================================
		mFriends = serverGetFriendsInGroup();
		
		mFriendsAndClient = new String[mFriends.length + 1];
		mFriendsAndClient[0] = this.getResources().getString(R.string.creditor_selected);
		for (int i = 1; i < mFriendsAndClient.length; i++) {
			mFriendsAndClient[i] = mFriends[i-1];
		}
		//initially false (default value)
		checkedItems = new boolean[mFriends.length];
	}
	
	/**
	 * This will be a method that retrieve Client's friends in the radinGeoupe from server
	 * (Currently not implemented)
	 * @return an array of the client's friends's names
	 */
	public String[] serverGetFriendsInGroup() {
		String[] names = {"julie", "francois", "xavier", "Igor", "JT",
			"Thierry", "Ismail", "Tanja", "Hibou", "Cailloux", "Poux" };
		//TODO add proper methods
		return names;
	}

	/**
	 * Create a Dialog that shows the client's friends' names that can be
	 * selected to add to the expense.
	 * Based on http://developer.android.com/guide/topics/ui/dialogs.html
	 * 
	 * @param names The names that can be selected
	 * @return an AlertDialog that is ready to be shown
	 */
	private AlertDialog debtorDialog(final String[] names) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.debtorsList);
		
		final ListView listView = new ListView(this);
		StableArrayAdapter<String> adapter = 
				new StableArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, names);
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
				//remember choices
				long[] checkedIds = listView.getCheckedItemIds();
				checkedItems = new boolean[names.length];
				for (int i = 0; i < checkedIds.length; i++) {
					checkedItems[(int) checkedIds[i]] = true;
				}				
				//add to list & set textView
				String strNames = "";
				for (int i = 0; i < checkedIds.length; i++) {
					strNames = strNames + " " + names[(int) checkedIds[i]];
					mSelectedDebtors.add(names[(int) checkedIds[i]]);
				}
				TextView debitorSelected = (TextView) findViewById(R.id.debtors_selected);
				debitorSelected.setText(strNames);
			}
		});
		//Set CANCEL button
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				//nothing to do, changes are discarded
			}
		});
		return builder.create();
	}

	/**
	 * Create a Dialog that shows the client's friends' names that can be
	 * selected to be the creditor's name.
	 * 
	 * @param names The names that can be selected
	 * @return an AlertDialog that is ready to be shown
	 */
	private AlertDialog creditorDialog(final String[] names) {
		mSelectedIndex = DEFAULT_CREDITOR_SELECTION;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.creditorList);
		builder.setSingleChoiceItems(names, DEFAULT_CREDITOR_SELECTION, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mSelectedIndex = which;
			}
		});

		// Set OK button
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				//set textView
				TextView creditorSelected = (TextView) findViewById(R.id.creditor_selected);
				mSelectedCreditor = names[mSelectedIndex];
				creditorSelected.setText(mSelectedCreditor);
			}
		});
		// Set CANCEL button
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					//nothing to do, changes are discarded
				}
			});
		return builder.create();
	}

	/**
	 * Checks users'choices, and send them to server if valid through storageManager, show a Toast otherwise.
	 *
	 */
	public void sendExpense(View view) {
		mPurpose = ((EditText) findViewById(R.id.purpose_title)).getText().toString();
		String tmpAmount = ((EditText) findViewById(R.id.amount_Field)).getText().toString();
		if (tmpAmount.equals("")) {
			mAmount = 0;
		} else {
			mAmount = Double.parseDouble(tmpAmount);
		}

		if (mPurpose == null || mPurpose.isEmpty()) {
			Toast.makeText(this, R.string.invalid_purpose, Toast.LENGTH_SHORT).show();
		} else if (mSelectedDebtors.isEmpty()) {
			Toast.makeText(this, R.string.invalid_debtors, Toast.LENGTH_SHORT).show();
		} else if (mAmount == 0) {
			Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show();
		} else {
			//Fields OK (creditor is always assigned)
			// Create and send Model
			this.finish();
			Toast.makeText(this, R.string.expense_added, Toast.LENGTH_SHORT).show();
		}
	}
}

