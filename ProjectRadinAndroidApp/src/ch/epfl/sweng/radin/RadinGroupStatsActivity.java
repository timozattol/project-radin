package ch.epfl.sweng.radin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.managers.TransactionStorageManager;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

/**
 * @author Fabien Zellweger
 * Displays statistics of the expenses over time.
 */
public class RadinGroupStatsActivity extends Activity {
	private RadinGroupModel mCurrentRadinGroupModel;
	private GraphView mYearGraphView;
	private GraphView mMonthGraphView;
	private GraphView mDayGraphView;
	private static final int GRAPH_DEFAULT_ID = 0;
	private static final int GRAPH_DAY_ID = 1;
	private static final int GRAPH_MONTH_ID = 2;
	private static final int GRAPH_YEAR_ID = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radingroup_stats);
		Bundle extras = getIntent().getExtras();
		mCurrentRadinGroupModel = ActionBar.getRadinGroupModelFromBundle(extras);
	
		
		LinearLayout thisLayout = (LinearLayout) findViewById(R.id.statRadinGroupActionBarLayout);
		ActionBar.addActionBar(this, thisLayout, mCurrentRadinGroupModel);
		
		TransactionStorageManager transactionStorageManager = TransactionStorageManager.getStorageManager();
		int rGMId = mCurrentRadinGroupModel.getRadinGroupID();
		transactionStorageManager.getAllForGroupId(rGMId, new RadinListener<TransactionModel>() {
			@Override
			public void callback(List<TransactionModel> items, StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					displayItems(items);
				} else {
					displayErrorToast(getString(R.string.retrieving_transaction_group_error));
				}
			}
		});
		
		Spinner graphSpinner = (Spinner) findViewById(R.id.statsSelectGraphSpinner);
		graphSpinner.setOnItemSelectedListener(spinnerSelectionListener);
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
		Collections.sort(items, new Comparator<TransactionModel>(){
			@Override
			public int compare(TransactionModel firstItem, TransactionModel secondItem) {
				if (firstItem.getDateTime().isAfter(secondItem.getDateTime())) {
					return 1;
				} else if (firstItem.getDateTime().isBefore(secondItem.getDateTime())) {
					return -1;
				}
				return 0;
			}			
		});
		
		TreeMap<String, Double> sortedByYear = new TreeMap<String, Double>();
		TreeMap<String, Double> sortedByMonth = new TreeMap<String, Double>();
		TreeMap<String, Double> sortedByDay = new TreeMap<String, Double>();		
		
		String previousYearDate = "0";
		String previousMonthDate = "0";
		String previousDayDate = "0";	
		
		for (int i = 0; i < items.size(); i++) {
			totalAmount += items.get(i).getAmount();
			
			String currentYearDate = String.valueOf(items.get(i).getDateTime().getYear());
			String currentMonthDate = currentYearDate + " / " 
							+ String.valueOf(items.get(i).getDateTime().getMonthOfYear());
			String currentDayDate = currentMonthDate + " / "
							+ String.valueOf(items.get(i).getDateTime().getDayOfMonth());			
			
			sortedByYear.put(currentYearDate, currentYearDate.equals(previousYearDate) 
							? sortedByYear.get(currentYearDate) + items.get(i).getAmount() 
							: items.get(i).getAmount());
			previousYearDate = currentYearDate;
			
			sortedByMonth.put(currentMonthDate, currentMonthDate.equals(previousMonthDate) 
							? sortedByMonth.get(currentMonthDate) + items.get(i).getAmount() 
							: items.get(i).getAmount());
			previousMonthDate = currentMonthDate;
			
			sortedByDay.put(currentDayDate, currentDayDate.equals(previousDayDate) 
							? sortedByDay.get(currentDayDate) + items.get(i).getAmount() 
							: items.get(i).getAmount());
			previousDayDate = currentDayDate;
		}
		
		TextView totalAmountView = (TextView) findViewById(R.id.totalAmountValue);
		totalAmountView.setText(mCurrentRadinGroupModel.getRadinGroupName() 
							+ ": " + totalAmount);
		
		//Create the year graph
		String[] yearKeys = sortedByYear.navigableKeySet().toArray(new String[0]);
		GraphViewData[] yearGraphData = new GraphViewData[sortedByYear.size()];
		for (int i = 0; i < sortedByYear.size(); i++) {
			yearGraphData[i] = new GraphViewData(i, sortedByYear.get(yearKeys[i]));
		}		
		GraphViewSeries yearGraph = new GraphViewSeries(yearGraphData);
		mYearGraphView = new BarGraphView(this, getString(R.string.spending_year));
		mYearGraphView.addSeries(yearGraph);
		mYearGraphView.getGraphViewStyle().setGridColor(this.getResources().getColor(R.color.header));
		mYearGraphView.setHorizontalLabels(yearKeys);
		mYearGraphView.setManualYAxisBounds(totalAmount, 0.0);
		
		
		//Create the month graph
		String[] monthKeys = sortedByMonth.navigableKeySet().toArray(new String[0]);
		GraphViewData[] monthGraphData = new GraphViewData[sortedByMonth.size()];
		for (int i = 0; i < sortedByMonth.size(); i++) {
			monthGraphData[i] = new GraphViewData(i, sortedByMonth.get(monthKeys[i]));
		}
		GraphViewSeries monthGraph = new GraphViewSeries(monthGraphData);
		mMonthGraphView = new BarGraphView(this, getString(R.string.spending_month));
		mMonthGraphView.addSeries(monthGraph);
		mMonthGraphView.setHorizontalLabels(monthKeys);
		mMonthGraphView.setManualYAxisBounds(totalAmount/2, 0);
		
		//Create the day graph
		String[] dayKeys = sortedByDay.keySet().toArray(new String[0]);
		GraphViewData[] dayGraphData = new GraphViewData[sortedByDay.size()];
		for (int i = 0; i < sortedByDay.size(); i++) {
			dayGraphData[i] = new GraphViewData(i, sortedByDay.get(dayKeys[i]));
		}
		GraphViewSeries dayGraph = new GraphViewSeries(dayGraphData);
		mDayGraphView = new BarGraphView(this, getString(R.string.spending_day));
		mDayGraphView.addSeries(dayGraph);
		mDayGraphView.setHorizontalLabels(dayKeys);
		mDayGraphView.setManualYAxisBounds(totalAmount/2, 0);
		
		//Place the graphs on the right positions and set them invisible
		RelativeLayout statRelativeLayout = (RelativeLayout) findViewById(R.id.statRadinGroupLayout);
		RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.BELOW, R.id.statsSelectGraphSpinner);
		statRelativeLayout.addView(mDayGraphView, layoutParams);
		statRelativeLayout.addView(mMonthGraphView, layoutParams);
		statRelativeLayout.addView(mYearGraphView, layoutParams);
		mDayGraphView.setVisibility(View.INVISIBLE);
		mMonthGraphView.setVisibility(View.INVISIBLE);
		mYearGraphView.setVisibility(View.INVISIBLE);
	}
	private void displayErrorToast(String message) {
	    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	private OnItemSelectedListener spinnerSelectionListener = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
						long id) {

			switch (position) {
				case GRAPH_DEFAULT_ID :
					break;
				case GRAPH_DAY_ID :
					mDayGraphView.setVisibility(View.VISIBLE);
					mMonthGraphView.setVisibility(View.INVISIBLE);
					mYearGraphView.setVisibility(View.INVISIBLE);
					break;
				case GRAPH_MONTH_ID :
					mDayGraphView.setVisibility(View.INVISIBLE);
					mMonthGraphView.setVisibility(View.VISIBLE);
					mYearGraphView.setVisibility(View.INVISIBLE);
					break;
				case GRAPH_YEAR_ID :
					mDayGraphView.setVisibility(View.INVISIBLE);
					mMonthGraphView.setVisibility(View.INVISIBLE);
					mYearGraphView.setVisibility(View.VISIBLE);
					break;
				default:
					displayErrorToast(getString(R.string.invalid_spinner_elem));
					break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
		
	};
}
