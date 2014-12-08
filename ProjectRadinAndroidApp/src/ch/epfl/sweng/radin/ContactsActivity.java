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
	private List<UserModel> mUserModelList = new ArrayList<UserModel>();
	private ListView mListFriend;
	private UserArrayAdapter mUserModelAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);


		//TODO user the real userId
		int currentId = 0;
		
		mUserModelAdapter = new UserArrayAdapter(this, R.layout.user_list_row, mUserModelList);
		
		mListFriend = (ListView) findViewById(R.id.fullContactListView);
		mListFriend.setAdapter(mUserModelAdapter);
		
		refreshList();

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
	
	private void refreshList() {
		//TODO: Uncomment this when the getAllFriend() is implemented
//		UserStorageManager userStorageManager = UserStorageManager.getStorageManager();
//		userStorageManager.getAllFriends(new RadinListener<UserModel>(){
//
//			@Override
//			public void callback(List<UserModel> items,
//					StorageManagerRequestStatus status) {
//				
//
//				if (status == StorageManagerRequestStatus.FAILURE) {
//                    displayErrorToast("Error while retrieving friends from server");
//				} else {
//					mUserModelAdapter.setUserModels(items);
//				}
//				
//				
//			}
//			
//		});
		//Hard coded test
		List<UserModel> TestUserModel = new ArrayList<UserModel>();
		TestUserModel.add(new UserModel("Bob", "Marley", "Boby", "BobM@star.com", "allee des star 12", "CH00 0000 0000 0000 1111 A", "0", "xD", 1));
		TestUserModel.add(new UserModel("Eddard", "Stark", "Ned", "Ned.Stark@Winterfell.nord", "Winterfell Castle 1", "CH13 2362 5450 0760 1284 C", "0", "*-*", 2));
		TestUserModel.add(new UserModel("Albert", "Einstein", "E=MC2", "AlEin@E=MC2.com", "Kramgasse 49 du centre historique de Berne", "CH67 8462 2257 0059 1793 F", "0", ":p", 3));
		mUserModelAdapter.setUserModels(TestUserModel);
		
		
	}
	
	private void displayErrorToast(String message) {
	    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
    /**
     * @param context the context
     * @param resource the view resource representing a row in the ListView
     * @param objects the list of TransactionModel
     */
	private class UserArrayAdapter extends ArrayAdapter<UserModel>{
		private List<UserModel> mUserModelList;
		private Context mContext;

		public UserArrayAdapter(Context context, int resource,
					 List<UserModel> objects) {
			super(context, resource, objects);
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
			TextView textUserName = (TextView) rowView.findViewById(R.id.friendsUserName);
			TextView textFullName = (TextView) rowView.findViewById(R.id.friendsFullName);
			TextView textAdress = (TextView) rowView.findViewById(R.id.friendsAdress);
			TextView textIban = (TextView) rowView.findViewById(R.id.friendsIban);

			UserModel user = mUserModelList.get(position);
			textUserName.setText(user.getUsername());
			textFullName.setText(user.getFirstName() + " " + user.getLastName());
			textAdress.setText(user.getAddress());
			textIban.setText(user.getIban());

			return rowView;

		}

		public void setUserModels(List<UserModel> newData) {
			mUserModelList.clear();
			mUserModelList.addAll(newData);
			notifyDataSetChanged();
		}

	}



}
