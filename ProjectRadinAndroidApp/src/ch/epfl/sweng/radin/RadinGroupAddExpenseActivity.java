package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.Currency;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.TransactionType;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.RadinGroupStorageManager;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity allows the user to add an expense to a radinGroup by providing the creditor, 
 * people who have to pay and the amount payed.
 * 
 *TODO Add reimbursement feature (if reimbursement selected amount turns to be opposite amount) 
 */
public class RadinGroupAddExpenseActivity extends Activity {
	private RadinGroupModel mCurrentRadinGroupModel;
	private static final int DEFAULT_CREDITOR_SELECTION = 0;
	private int mSelectedIndex = DEFAULT_CREDITOR_SELECTION;

	
	private UserModel mClientModel; // should be received from previous activity
	private double mAmount;
	private boolean[] checkedItems;
	private String  mPurpose;
	private UserModel mSelectedCreditor;
	private ArrayList<UserModel> mPeopleWhoHaveToPay = new ArrayList<UserModel>();
	private ArrayList<UserModel> mParticipants;
	private HashMap<String, UserModel> mNamesAndModel;
	private String[] mParticipantsNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_radingroup_add_expense);

		Bundle extras = getIntent().getExtras();
		mCurrentRadinGroupModel = ActionBar.getRadinGroupModelFromBundle(extras);
		String radinGroupTitle = mCurrentRadinGroupModel.getRadinGroupName();
		
		TextView addExpenseText = (TextView) findViewById(R.id.title_add_expense);
		addExpenseText.setText(addExpenseText.getText().toString() + radinGroupTitle);
		
		RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.addExpenseRadinGroupLayout);
		ActionBar.addActionBar(this, thisLayout, mCurrentRadinGroupModel);
		
		retrieveParticipants();
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
		if (mParticipantsNames != null) {
			peopleWhoHaveToPayDialog().show();
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
		if (mParticipantsNames != null) {
			creditorDialog().show();
		} else {
			Toast.makeText(this, R.string.not_ready, Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Retrieve friends and sets the arrays for the dialogs
	 */
	private void retrieveParticipants() {
		RadinGroupStorageManager radinGroupStorageManager = RadinGroupStorageManager.getStorageManager();
		radinGroupStorageManager.getParticipantsByGroupId(mCurrentRadinGroupModel.getRadinGroupID(),
				new RadinListener<UserModel>() {
			@Override
			public void callback(List<UserModel> items, StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					useDataRetrieved(items);
				} else {
					Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	/**
	 * Instantiates participants' lists from server's retrieved data 
	 * @param items UserModels retrieved from server
	 */
	private void useDataRetrieved(List<UserModel> items) {
		mParticipants = new ArrayList<UserModel>(items);
		mNamesAndModel = new HashMap<String, UserModel>();
		mParticipantsNames = new String[mParticipants.size()];
		
		for (int i = 0; i < mParticipants.size(); i++) {
			UserModel currentUser = mParticipants.get(i);
			String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();
			
			mNamesAndModel.put(fullName, currentUser);
			mParticipantsNames[i] = fullName;
		}
		//initially false (default value)
		checkedItems = new boolean[mParticipants.size()];
	}

	/**
	 * Create a Dialog that shows the client's friends' names that can be
	 * selected to add to the expense.
	 * Based on http://developer.android.com/guide/topics/ui/dialogs.html
	 * 
	 * @param names The names that can be selected
	 * @return an AlertDialog that is ready to be shown
	 */
	private AlertDialog peopleWhoHaveToPayDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.debtorsList);
		
		final ListView listView = new ListView(this);
		StableArrayAdapter<String> adapter = 
				new StableArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, mParticipantsNames);
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
				checkedItems = new boolean[mParticipantsNames.length];
				for (int i = 0; i < checkedIds.length; i++) {
					checkedItems[(int) checkedIds[i]] = true;
				}				
				//add to list & set textView
				String strNames = "";
				for (int i = 0; i < checkedIds.length; i++) {
					strNames = strNames + " " + mParticipantsNames[(int) checkedIds[i]];
					mPeopleWhoHaveToPay.add(mNamesAndModel.get(mParticipantsNames[(int) checkedIds[i]]));
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
	private AlertDialog creditorDialog() {
		mSelectedIndex = DEFAULT_CREDITOR_SELECTION;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.creditorList);
		builder.setSingleChoiceItems(mParticipantsNames,
				DEFAULT_CREDITOR_SELECTION,
				new DialogInterface.OnClickListener() {
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
				mSelectedCreditor = mNamesAndModel.get(mParticipantsNames[mSelectedIndex]);
				creditorSelected.setText(mParticipantsNames[mSelectedIndex]);
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
		} else if (mPeopleWhoHaveToPay.isEmpty()) {
			Toast.makeText(this, R.string.invalid_debtors, Toast.LENGTH_SHORT).show();
		} else if (mAmount == 0) {
			Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show();
		} else {
			//Data OK
			if (mSelectedCreditor == null) {
				mSelectedCreditor = mClientModel;
			}
			TransactionModel newTransaction = new TransactionModel(-1,
																   mCurrentRadinGroupModel.getRadinGroupID(),
																   mSelectedCreditor.getId(),
																   mClientModel.getId(),
																   mAmount,
																   Currency.CHF,
																   dateTime,
																   mPurpose,
																   TransactionType.PAYMENT);
			TransactionWithParticipantsModel transactionToSend = 
					new TransactionWithParticipantsModel(newTransaction, setAndgetParticipantsWithCoeff());
			RadinGroupStorageManager radinGroupStorageManager =  RadinGroupStorageManager.getStorageManager();
			radinGroupStorageManager.create((new ArrayList<TransactionWithParticipantsModel>()).add(transactionToSend),
											 new RadinListener<TransactionModel>() {
				@Override
				void callback(List<TransactionModel> items, StorageManagerRequestStatus status) {
					if (status == StorageManagerRequestStatus.SUCCESS) {
						((Activity) getApplicationContext()).finish();
						Toast.makeText(getApplicationContext(), R.string.expense_added, Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
					}
				}
			});
			// WaitingDialog until callback is called	
		}
	}
	
	/**
	 * Add coefficient to transaction's participants. 
	 * For the moment only coeff 1 and 0 availabale (in transaction and not in transaction)
	 * @return participants with coeff contained in a map
	 */
	private  HashMap<UserModel, Integer> setAndgetParticipantsWithCoeff() {
		HashMap<UserModel, Integer> participantsWithCoeff = new HashMap<UserModel, Integer>();
		for (UserModel usr : mParticipants) {
			if (mPeopleWhoHaveToPay.contains(usr)) {
				participantsWithCoeff.put(usr, 1);
			} else {
				participantsWithCoeff.put(usr, 0);
			}
		}
		return participantsWithCoeff;
	}	
}

