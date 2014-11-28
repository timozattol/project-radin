package ch.epfl.sweng.radin;

import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.managers.TransactionStorageManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Fabien Zellweger
 *
 */
public class RadinGroupStatsActivity extends Activity {
	private RadinGroupModel mCurrentRadinGroupModel;
	private final static int DAYAYEAR = 366;
	private final static int DAYAMONTH = 31;
	private final static int MONTHAYEAR = 12;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radingroup_stats);
		
		Bundle extras = getIntent().getExtras();
		mCurrentRadinGroupModel = ActionBar.getRadinGroupModelFromBundle(extras);
		
		RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.statRadinGroupLayout);
		ActionBar.addActionBar(this, thisLayout, mCurrentRadinGroupModel);
		
		TransactionStorageManager transactionStorageManager = TransactionStorageManager.getStorageManager();
		
		transactionStorageManager.getAllTransactionFromGroup(
				mCurrentRadinGroupModel.getRadinGroupID(),	new RadinListener<TransactionModel>() {
					@Override
					public void callback(List<TransactionModel> items, StorageManagerRequestStatus status) {
						if (status == StorageManagerRequestStatus.SUCCESS) {
							displayItems(items);
						} else {
							displayErrorToast("There was an error, please try again");
						}
					}
				});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.radingroup_balance, menu);
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
	
	private void displayItems(List<TransactionModel> items) {
		double totalAmount = 0.0;
		HashMap<Integer, Double> sortedByWeek = null;
		HashMap<Integer, Double> sortedByMonth = null;
		HashMap<Integer, Double> sortedByYear = null;
		
		for (int i = 0; i < items.size(); i++) {
			int weekKey = items.get(i).getDateTime().year().get() * DAYAYEAR
					+ items.get(i).getDateTime().monthOfYear().get() * DAYAMONTH
					+ items.get(i).getDateTime().dayOfYear().get();
			int monthKey = items.get(i).getDateTime().year().get() * MONTHAYEAR
					+ items.get(i).getDateTime().monthOfYear().get();
			int yearKey = items.get(i).getDateTime().year().get();
			totalAmount += items.get(i).getAmount();
			
			sortedByWeek.put(weekKey, sortedByWeek.containsKey(weekKey) ? sortedByWeek.get(weekKey) 
					+ items.get(i).getAmount() : items.get(i).getAmount());
			sortedByMonth.put(monthKey, sortedByMonth.containsKey(monthKey) ? sortedByMonth.get(monthKey)
					+ items.get(i).getAmount() : items.get(i).getAmount());
			sortedByYear.put(yearKey, sortedByYear.containsKey(yearKey) ? sortedByYear.get(yearKey)
					+ items.get(i).getAmount() : items.get(i).getAmount());			
		}
		
		TextView totalAmountView = (TextView) findViewById(R.id.totalAmount);
		totalAmountView.setText("The total amount of groups " + mCurrentRadinGroupModel.getRadinGroupName() 
				+ " is: " + totalAmount);
		
		//TODO display the graph with the button
		
	}
	private void displayErrorToast(String message) {
	    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
