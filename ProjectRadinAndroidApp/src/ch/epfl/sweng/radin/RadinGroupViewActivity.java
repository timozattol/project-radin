package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.Currency;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.TransactionType;
import ch.epfl.sweng.radin.storage.managers.TransactionStorageManager;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Fabien Zellweger
 * This Activity give a view of the selected radin group
 *
 */
public class RadinGroupViewActivity extends Activity {
	private RadinGroupModel mCurrentRadinGroupModel;
	private List<TransactionModel> mTransactionModelList = new ArrayList<TransactionModel>();
	private TransactionArrayAdapter mTransactionsModelAdapter;
	private ListView mTransactionListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radingroup_view);


		Bundle extras = getIntent().getExtras();
		mCurrentRadinGroupModel = ActionBar.getRadinGroupModelFromBundle(extras);
		String radinGroupTitle = mCurrentRadinGroupModel.getRadinGroupName();
		
		setTitle(radinGroupTitle);

		RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.radinGroupViewLayout);
		ActionBar.addActionBar(this, thisLayout, mCurrentRadinGroupModel);
		
		mTransactionsModelAdapter = new TransactionArrayAdapter(
                this, 
                R.layout.transaction_list_row, 
                mTransactionModelList);
		
		mTransactionListView = (ListView) findViewById(R.id.transactionListView);
		mTransactionListView.setAdapter(mTransactionsModelAdapter);
		
		
		//TEST
		final List<TransactionModel> models = new ArrayList<TransactionModel>();
		models.add(new TransactionModel(0, 0, 0, 0, 100, Currency.CHF, 
		        DateTime.now(), "Buy stuff", TransactionType.PAYMENT));
		models.add(new TransactionModel(0, 0, 0, 0, 200, Currency.CHF, 
                DateTime.now(), "Buy more stuff man lilkekkekekeke so muuuchh ahahahaha", TransactionType.PAYMENT));
		refreshViewWithData(models);
		
		new CountDownTimer(5000, 100) {
		    private int i = 0;
            
            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                models.add(new TransactionModel(0, 0, 0, 0, i*100, Currency.CHF, 
                        DateTime.now(), "Awesome description", TransactionType.PAYMENT));
                refreshViewWithData(models);
            }
            
            @Override
            public void onFinish() {
                
            }
        }.start();
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
	
	private void refreshViewWithData(List<TransactionModel> transactions) {
	    mTransactionsModelAdapter.setTransactionModels(transactions);
	}
	
	private void refreshList() {
	    TransactionStorageManager transactionStorageManager = 
	            TransactionStorageManager.getStorageManager();
	    
	    transactionStorageManager.getAllByRadinGroupId(
	            mCurrentRadinGroupModel.getRadinGroupID(), new RadinListener<TransactionModel>() {
                    
                    @Override
                    public void callback(List<TransactionModel> items,
                            StorageManagerRequestStatus status) {
                        if (status == StorageManagerRequestStatus.FAILURE) {
                            displayErrorToast("Error while retrieving transactions, please try again");
                        } else {
                            refreshViewWithData(items);
                        }
                    }
                });
	}
	
	/**
	 * An adapter for the list of transactions
	 * @author timozattol
	 *
	 */
	private class TransactionArrayAdapter extends ArrayAdapter<TransactionModel> {
	    private List<TransactionModel> transactionModels;
	    private final Context context;
	    
        /**
         * @param context the context
         * @param resource the view resource representing a row in the ListView
         * @param objects the list of TransactionModel
         */
        public TransactionArrayAdapter(Context context, int resource,
                List<TransactionModel> objects) {
            super(context, resource, objects);
            transactionModels = objects;
            this.context = context;
        }
	    
        /* (non-Javadoc)
         * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View rowView = inflater.inflate(R.layout.transaction_list_row, parent, false);
            TextView textViewAmount = (TextView) rowView.findViewById(R.id.transaction_amount);
            TextView textViewPurpose = (TextView) rowView.findViewById(R.id.transaction_purpose);
            TextView textViewCreditor = (TextView) rowView.findViewById(R.id.transaction_creditor);
            TextView textViewUsersConcerned = 
                    (TextView) rowView.findViewById(R.id.transaction_users_concerned);

            TransactionModel transaction = transactionModels.get(position);
            textViewPurpose.setText(transaction.getPurpose());
            textViewAmount.setText(transaction.getAmount() + " " + transaction.getCurrency());
            
            //TODO get username for id
            textViewCreditor.setText("Paid by: " + transaction.getCreditorID());
            
            //TODO get real user concerned
            textViewUsersConcerned.setText("For: Roger, Michelle and Bob");
            return rowView;
        }
        
        public void setTransactionModels(List<TransactionModel> newData) {
            transactionModels.clear();
            transactionModels.addAll(newData);
            notifyDataSetChanged();
        }
	}
}
