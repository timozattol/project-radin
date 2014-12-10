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
import android.view.Window;
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
public class RadinGroupViewActivity extends DashBoardActivity {
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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_radingroup_view);


		Bundle extras = getIntent().getExtras();
		mCurrentRadinGroupModel = ActionBar.getRadinGroupModelFromBundle(extras);
		String radinGroupTitle = mCurrentRadinGroupModel.getRadinGroupName();
		setHeader(getString(R.string.rg_name_hint), true, true);

		
		setTitle(radinGroupTitle);

		LinearLayout thisLayout = (LinearLayout) findViewById(R.id.radinGroupViewLayout);
		ActionBar.addActionBar(this, thisLayout, mCurrentRadinGroupModel);
		
		mTransactionsModelAdapter = new TransactionArrayAdapter(
                this, 
                R.layout.transaction_list_row, 
                mTransactionModelList);
		
		mTransactionListView = (ListView) findViewById(R.id.transactionListView);
		mTransactionListView.setAdapter(mTransactionsModelAdapter);

		refreshUsersInGroupAndThenTransaction();
		//setAutoTransactionRefresh();
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
                            displayErrorToast("Error while retrieving transactions");
                        } else {
                            refreshViewWithData(items);
                        }
                    }
                });
	}
	
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

	private void fillWithTestData() {
	    //TODO do again with participants
//	    final int hundredFrancs = 100;
//	    final int twoHundredFrancs = 100;
//	    final int fiveSecs = 5000;
//	    final int halfASec = 500;
//        

//	    //TEST
//        final List<TransactionWithParticipantsModel> models = new ArrayList<TransactionWithParticipantsModel>();
//        models.add(new TransactionWithParticipantsModel(0, 0, 0, 0, hundredFrancs, Currency.CHF, 
//                DateTime.now(), "Buy stuff", TransactionType.PAYMENT));
//        models.add(new TransactionModel(0, 0, 0, 0, twoHundredFrancs, Currency.CHF, 
//                DateTime.now(), "Buy more stuff", TransactionType.PAYMENT));
//        refreshViewWithData(models);
//        
//        new CountDownTimer(fiveSecs, halfASec) {
//            private int i = 0;
//            
//            @Override
//            public void onTick(long millisUntilFinished) {
//                i++;
//                models.add(new TransactionModel(0, 0, 0, 0, i*hundredFrancs, Currency.CHF, 
//                        DateTime.now().minusDays(i), "Buy buy buy", TransactionType.PAYMENT));
//                refreshViewWithData(models);
//            }
//            
//            @Override
//            public void onFinish() {
//                
//            }
//        }.start();
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
