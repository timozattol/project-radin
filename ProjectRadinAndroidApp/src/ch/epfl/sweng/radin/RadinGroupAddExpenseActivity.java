package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.R.string;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.Currency;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.TransactionType;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.TransactionWithParticipantsStorageManager;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

/**
 * This activity allows the user to add an expense to a radinGroup by providing the creditor, 
 * people who have to pay and the amount payed.
 * 
 *TODO Add reimbursement feature (if reimbursement selected amount turns to be opposite amount) 
 */
public class RadinGroupAddExpenseActivity extends Activity {
	private static final int DEFAULT_CREDITOR_SELECTION = 0;

	private RadinGroupModel mCurrentRadinGroupModel;
	private SharedPreferences mPrefs;
	private int mClientId;
	private int mSelectedIndex = DEFAULT_CREDITOR_SELECTION;
	private double mAmount;
	private int mSelectedCreditorId;
	private String  mPurpose;
	private HashMap<String, UserModel> mNamesAndModel;
	private String[] mGroupParticipantsNames;
	private Activity mCurrentActivity = this;
	private HashMap<String, Integer> mSharedMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> mPeopleWhoHaveToPay = new HashMap<String, Integer>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_radingroup_add_expense);

		Bundle extras = getIntent().getExtras();
		mCurrentRadinGroupModel = ActionBar.getRadinGroupModelFromBundle(extras);
		
		String radinGroupTitle = mCurrentRadinGroupModel.getRadinGroupName();
		
		TextView addExpenseText = (TextView) findViewById(R.id.title_add_expense);
		addExpenseText.setText(addExpenseText.getText().toString() + radinGroupTitle);
		
		LinearLayout thisLayout = (LinearLayout) findViewById(R.id.addExpenseRadinGroupLayout);
		ActionBar.addActionBar(this, thisLayout, mCurrentRadinGroupModel);
		
		mPrefs = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
		mClientId = Integer.parseInt(mPrefs.getString(getString(R.string.username), "-1"));
		mSelectedCreditorId = mClientId; //Default creditor = client
		checkClientId();
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
	 * Retrieve friends and sets the arrays for the dialogs
	 */
	private void retrieveParticipants() {
		UserStorageManager usrStorageManager = UserStorageManager.getStorageManager();
		usrStorageManager.getAllForGroupId(mCurrentRadinGroupModel.getRadinGroupID(), new RadinListener<UserModel>() {
			@Override
			public void callback(List<UserModel> items, StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					useDataRetrieved(items);
				} else {
					Toast.makeText(getApplicationContext(),	R.string.server_error, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	/**
	 * Instantiates participants' lists from server's retrieved data 
	 * @param items UserModels retrieved from server
	 */
	private void useDataRetrieved(List<UserModel> items) {
		mNamesAndModel = new HashMap<String, UserModel>();
		mGroupParticipantsNames = new String[items.size()];
		for (int i = 0; i < items.size(); i++) {
			UserModel currentUser = items.get(i);
			String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();
			if (currentUser.getId() != mClientId) {
				mGroupParticipantsNames[i] = fullName;
				mNamesAndModel.put(fullName, currentUser);
			} else {
				String clientName = getResources().getString(string.you);
				mGroupParticipantsNames[i] = clientName;
				mNamesAndModel.put(clientName, currentUser);
			}
		}
	}

	/**
	 * Shows an AlertDialog with a list of potential debtors to select.
	 * Shows a toast if data not ready
	 */
	public void showPeopleWhoHaveToPayDialog(View view) {
		if (mGroupParticipantsNames != null) {
			AlertDialog dialog = peopleWhoHaveToPayDialog();
			dialog.show();
			
			//EditText don't open the keyboard automatically in alertDialog
			//stackoverflow.com/questions/9102074/android-edittext-in-dialog-doesnt-pull-up-soft-keyboard
			dialog.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
					| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
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
		if (mGroupParticipantsNames != null) {
			creditorDialog().show();
			
		} else {
			Toast.makeText(this, R.string.not_ready, Toast.LENGTH_SHORT).show();
		}
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
		builder.setSingleChoiceItems(mGroupParticipantsNames,
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
				TextView creditorSelected = (TextView) findViewById(R.id.creditor_selected);
				mSelectedCreditorId = mNamesAndModel.get(mGroupParticipantsNames[mSelectedIndex]).getId();
				creditorSelected.setText(mGroupParticipantsNames[mSelectedIndex]);
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
	 * Create a Dialog that shows the client's friends' names that can be
	 * selected to add to the expense.
	 * 
	 * @param names The names that can be selected
	 * @return an AlertDialog that is ready to be shown
	 */
	private AlertDialog peopleWhoHaveToPayDialog() {
		mSharedMap.clear();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.debtorsList);
		
		final ListView listView = new ListView(this);
		
		PeopleAndWeightArrayAdapter adapter = new PeopleAndWeightArrayAdapter(
				this, R.layout.transactions_coeff_layout,
				mGroupParticipantsNames);
		
		listView.setAdapter(adapter);
		builder.setView(listView);
		
		// Set OK button
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				mPeopleWhoHaveToPay = new HashMap<String, Integer>(mSharedMap);
				((TextView) findViewById(R.id.debtors_selected)).setText(mSharedMap.keySet().toString());
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
	 * Checks users'choices, and send them to server if valid through storageManager, show a Toast otherwise.
	 *
	 */
	public void sendExpense(View view) {
		Button sendButton = (Button) findViewById(R.id.add_expense_button);
		sendButton.setClickable(false);
		mPurpose = ((EditText) findViewById(R.id.purpose_title)).getText().toString();
		String tmpAmount = ((EditText) findViewById(R.id.amount_Field)).getText().toString();
		if (tmpAmount.equals("")) {
			mAmount = 0;
		} else {
			mAmount = Double.parseDouble(tmpAmount);
		}
		if (mPurpose == null || mPurpose.isEmpty()) {
			Toast.makeText(this, R.string.invalid_purpose, Toast.LENGTH_SHORT).show();
			sendButton.setClickable(true);
		} else if (mPeopleWhoHaveToPay.isEmpty()) {
			Toast.makeText(this, R.string.invalid_debtors, Toast.LENGTH_SHORT).show();
			sendButton.setClickable(true);
		} else if (mAmount <= 0) {
			Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show();
			sendButton.setClickable(true);
		} else {
			//Data OK
			TransactionModel newTransaction = new TransactionModel(1,
																   mCurrentRadinGroupModel.getRadinGroupID(),
																   mSelectedCreditorId,
																   mClientId,
																   mAmount,
																   Currency.CHF,
																   DateTime.now(),
																   mPurpose,
																   TransactionType.PAYMENT);
			TransactionWithParticipantsModel transactionToSend = new TransactionWithParticipantsModel(
					newTransaction, setAndgetParticipantsWithCoeff());
			TransactionWithParticipantsStorageManager trWPartStorageManager = TransactionWithParticipantsStorageManager
					.getStorageManager();
			ArrayList<TransactionWithParticipantsModel> myTransList = new ArrayList<TransactionWithParticipantsModel>();
			myTransList.add(transactionToSend);
			trWPartStorageManager.create(myTransList, new RadinListener<TransactionWithParticipantsModel>() {
				@Override
				public void callback(
						List<TransactionWithParticipantsModel> items,
						StorageManagerRequestStatus status) {
					if (status == StorageManagerRequestStatus.SUCCESS) {
						mCurrentActivity.finish();
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
	 * @return participants with coeff contained in a map
	 */
	private  HashMap<Integer, Integer> setAndgetParticipantsWithCoeff() {
		HashMap<Integer, Integer> participantsWithCoeff = new HashMap<Integer, Integer>();
		for (Entry<String, Integer> entry : mPeopleWhoHaveToPay.entrySet()) {
		    String name = entry.getKey();
		    int weighting = entry.getValue();
		    
		    int usrId = mNamesAndModel.get(name).getId();
		    participantsWithCoeff.put(usrId, weighting);
		}
		return participantsWithCoeff;
	}	
	
	/**
	 * Checks whether the id well set in the shared preferences
	 * Disables create button if not.
	 */
	private void checkClientId() {
		if (mClientId == -1) {
			Toast.makeText(getApplicationContext(), R.string.bad_id, Toast.LENGTH_SHORT).show();
			((Button) findViewById(R.id.add_expense_button)).setClickable(false);
		}
	}
	
	/**
	 * An adapter for creditor dialog 
	 * @author Jokau (adapted from timozattol's implementation)
	 *
	 */
	private class PeopleAndWeightArrayAdapter extends ArrayAdapter<String> {
	    private String[] mParticipants;
	    private final Context mContext;
	    
        /**
         * @param context the context
         * @param resource the view resource representing a row in the ListView
         * @param objects the array of participant's names
         */
	    public PeopleAndWeightArrayAdapter(Context context, int resource, String[] objects) {
	    	super(context, resource, objects);
	    	mParticipants = objects;
	    	this.mContext = context;
	    }
	    
        /* (non-Javadoc)
         * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            final View rowView = inflater.inflate(R.layout.transactions_coeff_layout, parent, false);
            TextView textViewName = (TextView) rowView.findViewById(R.id.transaction_participant);
            CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.check_box);
            textViewName.setText(mParticipants[position]+"");   
            
            checkbox.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					EditText weight = (EditText) rowView.findViewById(R.id.transaction_weight);
					String weightStr = weight.getText().toString();
					String name = ((TextView) rowView.findViewById(R.id.transaction_participant)).getText().toString();
					if (((CheckBox) v).isChecked()) {
						if (weightStr.equals("")) {
							weightStr = "1";
						}
						weight.setOnFocusChangeListener(new OnFocusChangeListener() {
							@Override
							public void onFocusChange(View v, boolean hasFocus) {
								Toast.makeText(getApplicationContext(),
										R.string.edit_text_warning,
										Toast.LENGTH_SHORT).show();
							}
						});
						mSharedMap.put(name, Integer.parseInt(weightStr));
					} else {
						mSharedMap.remove(name);
						weight.setOnFocusChangeListener(new OnFocusChangeListener() {
							@Override
							public void onFocusChange(View v, boolean hasFocus) {
								//do nothing
							}
						});
					}
				}
			});
            return rowView;
        }
	}
}