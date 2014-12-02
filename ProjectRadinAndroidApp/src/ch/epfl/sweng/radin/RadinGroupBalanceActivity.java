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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Fabien Zellweger
 *
 */
@SuppressLint("UseSparseArrays") public class RadinGroupBalanceActivity extends Activity {
	private RadinGroupModel mCurrentRadinGroupModel;
	private List<UserModel> mParticipants;
	private List<TransactionWithParticipantsModel> mTransactions;
	private final static int TIME_OUT = 5000;
	private final static int TEXT_SIZE = 30;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radingroup_balance);

		Bundle extras = getIntent().getExtras();
		mCurrentRadinGroupModel = ActionBar.getRadinGroupModelFromBundle(extras);

		RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.balanceRadinGroupLayout);
		ActionBar.addActionBar(this, thisLayout, mCurrentRadinGroupModel);

		UserStorageManager userStorageManager = UserStorageManager.getStorageManager();
		userStorageManager.getAllForGroupId(mCurrentRadinGroupModel.getRadinGroupID(),
				new RadinListener<UserModel>() {

			@Override
			public void callback(List<UserModel> items, StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					mParticipants = items;
					mParticipants.notify();
				} else {
					displayErrorToast("Failed to get users for this group");
				}
			}
		});		

		TransactionWithParticipantsStorageManager storageManager = 
				TransactionWithParticipantsStorageManager.getStorageManager();
		storageManager.getAllForGroupId(mCurrentRadinGroupModel.getRadinGroupID(), 
				new RadinListener<TransactionWithParticipantsModel>() {

			@Override
			public void callback(List<TransactionWithParticipantsModel> items, StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					mTransactions = items;
					mTransactions.notify();
				} else {
					displayErrorToast("Failed to get Transactions for this group");
				}
			}
		});

		drawBalances(calculateBalances());
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

	private HashMap<Integer, Double> calculateBalances() {		
		HashMap<Integer, Double> userBalances = new HashMap<Integer, Double>();		

		if (mParticipants == null) {
			try {
				mParticipants.wait(TIME_OUT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (UserModel participant : mParticipants) {
			userBalances.put(participant.getId(), 0.0);
		}

		if (mTransactions == null) {
			try {
				mTransactions.wait(TIME_OUT);
			} catch (InterruptedException e) {
				// TODO fix
				e.printStackTrace();
			}
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
			userBalanceTextView.setText(userName + " owes: " + userBalances.get(participant.getId()));
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
