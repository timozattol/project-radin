package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;


/**
 * @author Fabien Zellweger
 * This activity shows you your contacts
 */

public class ContactsActivity extends Activity {
	private List<UserModel> mContactUserModelList = new ArrayList<UserModel>();
	private ListView mListFriend;
	private UserArrayAdapter mUserModelAdapter;
	private int mUserId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
		mUserId = Integer.parseInt(prefs.getString(getString(R.string.username), ""));
		
		mUserModelAdapter = new UserArrayAdapter(this, R.layout.user_list_row, mContactUserModelList);
		
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
		UserStorageManager userStorageManager = UserStorageManager.getStorageManager();
		userStorageManager.getFriendsOfUserWithId(mUserId, new RadinListener<UserModel>(){

			@Override
			public void callback(List<UserModel> items,
					StorageManagerRequestStatus status) {				

				if (status == StorageManagerRequestStatus.FAILURE) {
                    displayErrorToast(getString(R.string.retrieving_friend_error));
				} else {
					mUserModelAdapter.setUserModels(items);
					mContactUserModelList.clear();
					mContactUserModelList.addAll(items);
				}				
			}			
		});
//		//Hard coded test
//		
//		List<UserModel> testUserModel = new ArrayList<UserModel>();
//		testUserModel.add(new UserModel("Bob", "Marley", "Boby", "BobM@star.com",
//			"allee des star 12", "CH00 0000 0000 0000 1111 A", "0", "xD", 1));
//		testUserModel.add(new UserModel("Eddard", "Stark", "Ned",
//			"Ned.Stark@Winterfell.nord", "Winterfell Castle 1",
//			"CH13 2362 5450 0760 1284 C", "0", "*-*", 2));
//		testUserModel.add(new UserModel("Albert", "Einstein", "E=MC2",
//			"AlEin@E=MC2.com", "Kramgasse 49 du centre historique de Berne",
//			"CH67 8462 2257 0059 1793 F", "0", ":p", 3));
//		testUserModel.add(new UserModel("Vash", "The Stampede", "The Stampede",
//			"IHaveABigGun@space.univers", "planet Gunsmoke", "GU36 7238 4537 1274 2174 Z", "0", "*_*", 4));
//
//		mUserModelAdapter.setUserModels(testUserModel);	
//
//		mContactUserModelList.clear();
//		mContactUserModelList.addAll(testUserModel);
	}
	
	private OnClickListener contactButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
    		Intent displayActivityIntent = new Intent(v.getContext(), ProfileActivity.class);
    		int searchId = mContactUserModelList.get((Integer) v.getTag()).getId();
    		displayActivityIntent.putExtra("userId", searchId);
    		startActivity(displayActivityIntent);
		}		
	};
	
	private void displayErrorToast(String message) {
	    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	public void dialogAddFriend(View v) {
		final EditText editText = new EditText(this);
		editText.setId(R.id.editTextAddfriend);
		editText.setMaxLines(1);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.contactDialogAlert);
		builder.setView(editText);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				addNewFriend(editText.getText().toString());				
			}			
		});		
		builder.create().show();
	}

	public void addNewFriend(String friendUserName) {
		UserStorageManager userStorageManager = UserStorageManager.getStorageManager();
		userStorageManager.addNewFriend(mUserId, friendUserName, new RadinListener<UserModel>(){

			@Override
			public void callback(List<UserModel> items,
			   	StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					refreshList();
			    } else {
			        displayErrorToast(getString(R.string.add_friend_error));				        
			    }
				
			}
			
		});
	}
	
	
    /**
     * @param context the context
     * @param resource the view resource representing a row in the ListView
     * @param objects the list of TransactionModel
     */
	private class UserArrayAdapter extends ArrayAdapter<UserModel> {
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
			rowView.setTag(position);
			rowView.setOnClickListener(contactButtonListener);

			return rowView;

		}

		public void setUserModels(List<UserModel> newData) {
			mUserModelList.clear();
			mUserModelList.addAll(newData);
			notifyDataSetChanged();
		}
	}
}
