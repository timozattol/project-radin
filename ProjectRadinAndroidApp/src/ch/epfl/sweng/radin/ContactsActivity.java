package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.TransactionType;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Fabien Zellweger
 * This activity show you you're contacts
 */
public class ContactsActivity extends Activity {
	private ListView mListFriend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);


		//TODO user the real userId
		int currentId = 0;


		ListView mListFriend = (ListView) findViewById(R.id.fullContactListView);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contacts, menu);
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

	private class UserArrayAdapter extends ArrayAdapter<UserModel>{
		private List<UserModel> mUserModelList;
		private Context mContext;

		public UserArrayAdapter(Context context, int resource,
				int textViewResourceId, List<UserModel> objects) {
			super(context, resource, textViewResourceId, objects);
			mUserModelList = objects;
			mContext = context;				
		}

		/* (non-Javadoc)
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			View rowView = inflater.inflate(R.layout.user_list_row, parent, false);
			//TODO put the right avatar of our user
			TextView textFirstName = (TextView) findViewById(R.id.friendsFirstName);
			TextView textLastName = (TextView) findViewById(R.id.friendsLastName);
			TextView textAdress = (TextView) findViewById(R.id.friendsAdress);
			TextView textIban = (TextView) findViewById(R.id.friendsIban);

			UserModel user = mUserModelList.get(position);
			textFirstName.setText(user.getFirstName());
			textLastName.setText(user.getLastName());
			textAdress.setText(user.getAddress());
			textIban.setText(user.getIban());


			return null;

		}


		/* (non-Javadoc)
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		//    @Override
		//    public View getView(int position, View convertView, ViewGroup parent) {
		//        LayoutInflater inflater = LayoutInflater.from(mContext);
		//        View rowView = inflater.inflate(R.layout.transaction_list_row, parent, false);
		//        TextView textViewAmount = (TextView) rowView.findViewById(R.id.transaction_amount);
		//        TextView textViewPurpose = (TextView) rowView.findViewById(R.id.transaction_purpose);
		//        TextView textViewCreditor = (TextView) rowView.findViewById(R.id.transaction_creditor);
		//        TextView textViewUsersConcerned = 
		//                (TextView) rowView.findViewById(R.id.transaction_users_concerned);
		//        TextView textViewDateTime = 
		//                (TextView) rowView.findViewById(R.id.transaction_datetime);
		//        
		//
		//        TransactionModel transaction = mTransactionModels.get(position);
		//        textViewPurpose.setText(transaction.getPurpose());
		//        textViewAmount.setText(transaction.getAmount() + " " + transaction.getCurrency());
		//        
		//        //TODO get username for id
		//        if (transaction.getType() == TransactionType.PAYMENT) {
		//            textViewCreditor.setText("Paid by: " + transaction.getCreditorID());
		//        } else if (transaction.getType() == TransactionType.REIMBURSEMENT) {
		//            textViewCreditor.setText("Reimbursed by: " + transaction.getCreditorID());
		//        }
		//        
		//        
		//        //TODO get real user for transaction
		//        textViewUsersConcerned.setText("For: Roger, Michelle and Bob");
		//
		//        textViewDateTime.setText(transaction.getDateTime().toString(
		//                DateTimeFormat.forPattern("d/M/Y")));
		//
		//        return rowView;
		//    }

	}



}
