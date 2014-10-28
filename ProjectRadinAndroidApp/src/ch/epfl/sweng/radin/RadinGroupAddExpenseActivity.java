package ch.epfl.sweng.radin;

import java.util.ArrayList;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity allows the user to add an expense to a radinGroup by providing the creditor, 
 * debitors and the amount payed.
 *
 */
public class RadinGroupAddExpenseActivity extends Activity {
	private static final String CLIENT_NAME = "0000"; //TODO is there a way to indicate in every activity who's the user?
	private static final int DEFAULT_CREDITOR_SELECTION = 0;
	private ArrayList<Integer> mSelectedItems;
	private ArrayList<String> mSelectedDebtors = new ArrayList<String>();
	private int mSelectedIndex = DEFAULT_CREDITOR_SELECTION;
	private String mSelectedCreditor;
	private double mAmount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_radingroup_add_expense);
		mSelectedCreditor = this.getResources().getString(R.string.creditor_selected);

		Bundle extras = getIntent().getExtras();

		String radinGroupTitle = extras.getString("key");
		
		TextView addExpenseText = (TextView) findViewById(R.id.title_add_expense);
		addExpenseText.setText(addExpenseText.getText().toString() + radinGroupTitle);
		
		RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.addExpenseRadinGroupLayout);
		ActionBar.addActionBar(this, thisLayout, radinGroupTitle);
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
	 * Shows an AlertDialog with a list of potential debitors to select
	 * by retrieving the people in the radinGroup from the database.
	 * (currently not implemented)
	 *
	 */
	public void showDebDialog(View view) {
		debtorDialog(serverGetFriendsInGroup()).show();
	}

	/**
	 * Shows an AlertDialog with a list of potential creditors to select
	 * by retrieving the people in the radinGroup from the database.
	 * (currently not implemented)
	 * Adds the user to this list of people.
	 * @param view
	 */
	public void showCredDialog(View view) {
		String[] friends = serverGetFriendsInGroup();
		String[] clientAndFriends = new String[friends.length + 1];

		clientAndFriends[0] = this.getResources().getString(R.string.creditor_selected);
		for (int i = 1; i < clientAndFriends.length; i++) {
			clientAndFriends[i] = friends[i-1];
		}
		creditorDialog(clientAndFriends).show();
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
	 * Create a Dialog that shows the client's friends' names that can be
	 * selected to add to the expense.
	 * Based on http://developer.android.com/guide/topics/ui/dialogs.html
	 * 
	 * @param names The names that can be selected
	 * @return an AlertDialog that is ready to be shown
	 */
	private AlertDialog debtorDialog(final String[] names) {
		mSelectedItems = new ArrayList<Integer>();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.debtorsList);
		builder.setMultiChoiceItems(names, null, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) {
					// If the user checked the item, add it to the selected items
					mSelectedItems.add(which);
				} else if (mSelectedItems.contains(which)) {
					// Else, if the item is already in the array, remove it
					mSelectedItems.remove(Integer.valueOf(which));
				}
			}
		});

		// Set the action buttons
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				mSelectedDebtors.clear();
				for (int index : mSelectedItems) {
					mSelectedDebtors.add(names[index]);
				}
				TextView debitorsSelected = (TextView) findViewById(R.id.debtors_selected);

				if (!mSelectedDebtors.isEmpty()) {
					debitorsSelected.setText(mSelectedDebtors.toString());
				} else {
					debitorsSelected.setText(R.string.debtors_selected);
				}
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				//nothing to do
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

		// Set the action buttons
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				TextView creditorSelected = (TextView) findViewById(R.id.creditor_selected);

				mSelectedCreditor = names[mSelectedIndex];
				creditorSelected.setText(mSelectedCreditor);
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				//nothing to do
			}
		});
		return builder.create();
	}

	/**
	 * Checks all inputs filled in by the user, and send them to server if valid, show a Toast otherwise.
	 *
	 */
	public void sendExpense(View view) {
		EditText amountField = (EditText) findViewById(R.id.amount_Field);
		String amountText = amountField.getText().toString();
		if (amountText == null || amountText.isEmpty()) {
			Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show();
		} else {
			mAmount = Double.parseDouble(amountField.getText().toString());
			if (!mSelectedDebtors.isEmpty()) {
				if (mSelectedCreditor.equals(this.getResources().getString(R.string.creditor_selected))) {
					//TODO Server the creditor is CLIENT_NAME
					//server amount = amount
					//server debitors = mSelectedDebtors
					this.finish();
					Toast.makeText(this, R.string.expense_added, Toast.LENGTH_SHORT).show();
				} else {
					//server: the creditor is mSelectedCreditor
					//server amount = amount
					//server debitors = mSelectedDebtors
					this.finish();
					Toast.makeText(this, R.string.expense_added, Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, R.string.invalid_debtors, Toast.LENGTH_SHORT).show();
			}

		}
	}

}

