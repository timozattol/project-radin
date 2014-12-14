package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.TransactionType;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.TransactionWithParticipantsStorageManager;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Fabien Zellweger
 * This Activity give a view of the selected radin group
 * The List of transactions must be updated via refreshWithData,
 * so that the ArrayAdapter updates the view.
 *
 */
@SuppressLint("UseSparseArrays")
public class RadinGroupViewActivity extends Activity {
    private final static int SIXTY_SECS = 60000;
    private final static int TEN_SECS = 10000;

	private RadinGroupModel mCurrentRadinGroupModel;
	private List<TransactionWithParticipantsModel> mTransactionModelList = 
	        new ArrayList<TransactionWithParticipantsModel>();
	private TransactionArrayAdapter mTransactionsModelAdapter;
	private ListView mTransactionListView;
	private CountDownTimer mAutoRefreshTimer;
    
	private Map<Integer, UserModel> mIdToUserModelMapping = new HashMap<Integer, UserModel>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radingroup_view);

		Bundle extras = getIntent().getExtras();
		mCurrentRadinGroupModel = ActionBar.getRadinGroupModelFromBundle(extras);

		setTitle(mCurrentRadinGroupModel.getRadinGroupName());

		LinearLayout thisLayout = (LinearLayout) findViewById(R.id.radinGroupViewLayout);
		ActionBar.addActionBar(this, thisLayout, mCurrentRadinGroupModel);

		// Creates an Array adapter for the transaction, to update the view
		// every time the refreshViewWithData method is called.
		mTransactionsModelAdapter = new TransactionArrayAdapter(
                this, 
                R.layout.transaction_list_row, 
                mTransactionModelList);

		mTransactionListView = (ListView) findViewById(R.id.transactionListView);
		mTransactionListView.setAdapter(mTransactionsModelAdapter);

		refreshUsersInGroupAndThenTransaction();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		refreshUsersInGroupAndThenTransaction();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.radingroup_view, menu);
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
	 * Refresh the view with the new @param transactions, after sorting them.
	 */
	private void refreshViewWithData(List<TransactionWithParticipantsModel> transactions) {
	    sortByDateTime(transactions);
	    mTransactionsModelAdapter.setTransactionModels(transactions);
	}
	
	/**
	 * Asynchronously gets the data from the server, 
	 * and refresh the view with data when available.
	 */
	private void refreshTransactionList() {
	    TransactionWithParticipantsStorageManager transactionStorageManager = 
	            TransactionWithParticipantsStorageManager.getStorageManager();
	    
	    transactionStorageManager.getAllForGroupId(
	            mCurrentRadinGroupModel.getRadinGroupID(), new RadinListener<TransactionWithParticipantsModel>() {
                    
                    @Override
                    public void callback(List<TransactionWithParticipantsModel> items,
                            StorageManagerRequestStatus status) {
                        if (status == StorageManagerRequestStatus.FAILURE) {
                            displayErrorToast(getString(R.string.retrinving_transaction_group_error));
                        } else {
                            refreshViewWithData(items);
                        }
                    }
                });
	}
	
	/**
	 * Sorts the given list @param transactions by chronological order.
	 */
	private void sortByDateTime(List<TransactionWithParticipantsModel> transactions) {
	    Collections.sort(transactions, new Comparator<TransactionModel>() {

            @Override
            public int compare(TransactionModel lhs, TransactionModel rhs) {
                if (lhs.getDateTime().isBefore(rhs.getDateTime())) {
                    return -1;
                } else {
                    return +1;
                }
            }

        });
	}

	/**
	 * Asynchronously gets all the users that are in the RadinGroup.
	 * Refresh the view when done.
	 */
	private void refreshUsersInGroupAndThenTransaction() {
	    UserStorageManager userStorageManager = UserStorageManager.getStorageManager();
	    
	    userStorageManager.getAllForGroupId(mCurrentRadinGroupModel.getRadinGroupID(), 
	            new RadinListener<UserModel>() {
                    
                    @Override
                    public void callback(List<UserModel> items,
                            StorageManagerRequestStatus status) {
                        if (status == StorageManagerRequestStatus.SUCCESS) {
                            mIdToUserModelMapping.clear();
                            
                            for (UserModel userModel : items) {
                                mIdToUserModelMapping.put(userModel.getId(), userModel);
                            }
                            
                            // Refresh the list of transactions when the list of users is 
                            // successfully retrieved
                            refreshTransactionList();
                        } else {
                            displayErrorToast("Error while retrieving users");
                        }
                    }
                });
	}

	/**
	 * Sets a timer to refresh the list every 10 seconds, forever.
	 */
	private void setAutoTransactionRefresh() {
	    if (mAutoRefreshTimer == null) {
	        mAutoRefreshTimer = new CountDownTimer(SIXTY_SECS, TEN_SECS) {
                
                @Override
                public void onTick(long millisUntilFinished) {
                    refreshTransactionList();
                }
                
                @Override
                public void onFinish() {
                    // Restarts itself for infinite timer.
                    start();
                }
            };
	    }
	    mAutoRefreshTimer.start();
	}
	
	/**
	 * An adapter for the list of transactions
	 * @author timozattol
	 *
	 */
	private class TransactionArrayAdapter extends ArrayAdapter<TransactionWithParticipantsModel> {
	    private List<TransactionWithParticipantsModel> mTransactionModels;
	    private final Context mContext;
	    
        /**
         * @param context the context
         * @param resource the view resource representing a row in the ListView
         * @param objects the list of TransactionModel
         */
        public TransactionArrayAdapter(Context context, int resource,
                List<TransactionWithParticipantsModel> objects) {
            super(context, resource, objects);
            mTransactionModels = objects;
            this.mContext = context;
        }
	    
        /* (non-Javadoc)
         * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View rowView = inflater.inflate(R.layout.transaction_list_row, parent, false);
            TextView textViewAmount = (TextView) rowView.findViewById(R.id.transaction_amount);
            TextView textViewPurpose = (TextView) rowView.findViewById(R.id.transaction_purpose);
            TextView textViewCreditor = (TextView) rowView.findViewById(R.id.transaction_creditor);
            TextView textViewUsersConcerned = 
                    (TextView) rowView.findViewById(R.id.transaction_users_concerned);
            TextView textViewDateTime = 
                    (TextView) rowView.findViewById(R.id.transaction_datetime);
            

            TransactionWithParticipantsModel transaction = mTransactionModels.get(position);
            textViewPurpose.setText(transaction.getPurpose());
            textViewAmount.setText(transaction.getAmount() + " " + transaction.getCurrency());
            
            UserModel creditor = mIdToUserModelMapping.get(transaction.getCreditorID());

            String creditorFirstName = creditor.getFirstName();
            creditorFirstName = Character.toUpperCase(creditorFirstName.charAt(0))
                    + creditorFirstName.substring(1);

            if (transaction.getType() == TransactionType.PAYMENT) {
                textViewCreditor.setText("Paid by: " + creditorFirstName);
            } else if (transaction.getType() == TransactionType.REIMBURSEMENT) {
                textViewCreditor.setText("Reimbursed by: " + creditorFirstName);
            }
            
            Map<Integer, Integer> usersWithCoeffs = transaction.getUsersWithCoefficients();
            
            textViewUsersConcerned.setText(constructStringFromUsersWithCoeffs(usersWithCoeffs));

            textViewDateTime.setText(transaction.getDateTime().toString(
                    DateTimeFormat.forPattern("d/M/Y")));

            return rowView;
        }
        
        public void setTransactionModels(List<TransactionWithParticipantsModel> newData) {
            mTransactionModels.clear();
            mTransactionModels.addAll(newData);
            notifyDataSetChanged();
        }
        
        private String constructStringFromUsersWithCoeffs(Map<Integer, Integer> usersWithCoeffs) {
            String result = "For: ";
            
            for (int userId : usersWithCoeffs.keySet()) {
                UserModel user = mIdToUserModelMapping.get(userId);
                int coeff = usersWithCoeffs.get(userId);

                String firstName;
                
                if (user == null) {
                    firstName = "Anonymous";
                } else {
                    firstName = user.getFirstName();
                    firstName = Character.toUpperCase(firstName.charAt(0)) + firstName.substring(1);
                }

                if (coeff == 1) {
                    result+= firstName;
                } else if (coeff > 1) {
                    result+= firstName + " (" + coeff + "x)";
                } else {
                    // We're not supposed to have some coefficient < 1
                }

                result += ", ";
            }
            
            // Remove last coma and space
            result = result.substring(0, result.length()-2);

            return result;
        }
	}
}
