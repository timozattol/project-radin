package ch.epfl.sweng.radin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;
import ch.epfl.sweng.radin.storage.managers.TransactionWithParticipantsStorageManager;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Fabien Zellweger
 *
 */
@SuppressLint("UseSparseArrays") public class RadinGroupBalanceActivity extends DashBoardActivity {
	private RadinGroupModel mCurrentRadinGroupModel;
	private List<UserModel> mParticipants;
	private List<TransactionWithParticipantsModel> mTransactions;
	private final static int TIME_OUT = 5000;
	private final static int TEXT_SIZE = 30;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_radingroup_balance);

		Bundle extras = getIntent().getExtras();
		mCurrentRadinGroupModel = ActionBar.getRadinGroupModelFromBundle(extras);

		LinearLayout thisLayout = (LinearLayout) findViewById(R.id.balanceRadinGroupLayout);
		ActionBar.addActionBar(this, thisLayout, mCurrentRadinGroupModel);
		setHeader(getString(R.string.balance), true, true);


		fetchUsersThenTransactions();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.radingroup_configuration, menu);
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
	
	private void fetchUsersThenTransactions() {
	    UserStorageManager userStorageManager = UserStorageManager.getStorageManager();
        userStorageManager.getAllForGroupId(mCurrentRadinGroupModel.getRadinGroupID(),
                new RadinListener<UserModel>() {

            @Override
            public void callback(List<UserModel> items, StorageManagerRequestStatus status) {
                if (status == StorageManagerRequestStatus.SUCCESS) {
                    mParticipants = items;
                    
                    // After users are fetched, fetch transactions
                    fetchTransactions();
                } else {
                    displayErrorToast(getString(R.string.retrinving_user_group_error));
                }
            }
        });
	}
	
	private void fetchTransactions() {
	    TransactionWithParticipantsStorageManager storageManager = 
                TransactionWithParticipantsStorageManager.getStorageManager();
        storageManager.getAllForGroupId(mCurrentRadinGroupModel.getRadinGroupID(), 
                new RadinListener<TransactionWithParticipantsModel>() {

            @Override
            public void callback(List<TransactionWithParticipantsModel> items, StorageManagerRequestStatus status) {
                if (status == StorageManagerRequestStatus.SUCCESS) {
                    mTransactions = items;
                    
                    // Draw balances when transactions were successfully fetched.
                    drawBalances(calculateBalances());
                } else {
                    displayErrorToast(getString(R.string.retrinving_transaction_group_error));
                }
            }
        });
	}

	private HashMap<Integer, Double> calculateBalances() {		
		HashMap<Integer, Double> userBalances = new HashMap<Integer, Double>();		

		for (UserModel participant : mParticipants) {
			userBalances.put(participant.getId(), 0.0);
		}

		for (TransactionWithParticipantsModel transaction : mTransactions) {
			double transactionAmount = transaction.getAmount();
			Map<Integer, Integer> usersAndCoefficients = transaction.getUsersWithCoefficients();
			int sumCoefficients = 0;

			for (Integer coefficient : usersAndCoefficients.values()) {
				sumCoefficients += coefficient;
			}

			for (Integer participant : userBalances.keySet()) {
				Double oldBalance = userBalances.get(participant);
				Double newBalance = transactionAmount * (usersAndCoefficients.get(participant) / sumCoefficients);

				if (transaction.getCreditorID() == participant) {
					newBalance -= transactionAmount;
				}

				userBalances.put(participant, oldBalance + newBalance);
			}
		}
		return userBalances;
	}

	private void drawBalances(HashMap<Integer, Double> userBalances) {
		LinearLayout radinGroupBalanceLinearLayout = (LinearLayout) findViewById(R.id.radinGroupBalanceLinearLayout);
		radinGroupBalanceLinearLayout.setGravity(Gravity.LEFT);

		ProgressBar progressBar = (ProgressBar) findViewById(R.id.radinGroupBalanceProgressBar);
		radinGroupBalanceLinearLayout.removeView(progressBar);

		int i = 0;
		for (UserModel participant : mParticipants) {
			TextView userBalanceTextView = new TextView(this);
			String userName = participant.getFirstName();
			userBalanceTextView.setText(userName + getString(R.string.owes) + userBalances.get(participant.getId()));
			userBalanceTextView.setTextSize(TEXT_SIZE);
			userBalanceTextView.setTag(i);
			i++;

			radinGroupBalanceLinearLayout.addView(userBalanceTextView);
		}
	}


	private void displayErrorToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
