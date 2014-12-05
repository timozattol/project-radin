package ch.epfl.sweng.radin;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;

/**
 * @author topali2
 */
public class ProfileActivity extends Activity {

	private UserModel profileUser;
	private SharedPreferences prefs;
	private int userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		//This is a fake userId used to test the app
//		userId = prefs.getString(getString(R.string.username));
//		
//		prefs = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
		
		UserStorageManager userStorageManager = UserStorageManager.getStorageManager();
		userStorageManager.getById(userId, new RadinListener<UserModel>() {
			
			@Override
			public void callback(List<UserModel> items, StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					if (items.size() == 1) {						
						profileUser = items.get(0);
						displayUser();
					} else {
						displayErrorToast("Error wrong user informations");
					}
					
				} else {
					displayErrorToast("Connection Error getting userProfile informations");
				}
				
			}

		});
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	/**
	 * 
	 */
	public void displayUser() {
		
		ImageView profilePicture = (ImageView) findViewById(R.id.profilePic);
		//TODO add profile picture
		
		TextView firstName = (TextView) findViewById(R.id.profileFirstName);
		firstName.setText(profileUser.getFirstName());
		
		TextView lastName = (TextView) findViewById(R.id.profileLastName);
		lastName.setText(profileUser.getLastName());
		
		TextView email = (TextView) findViewById(R.id.profileEmail);
		email.setText(profileUser.getEmail());
		
		TextView address = (TextView) findViewById(R.id.profileAddress);
		address.setText(profileUser.getAddress());
		
		TextView iBan = (TextView) findViewById(R.id.profileIban);
		iBan.setText(profileUser.getIban());
	
	}
	/**
	 * 
	 * @param message of the error
	 */
	private void displayErrorToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Intent homeIntent = new Intent(this, HomeActivity.class);
	        	startActivity(homeIntent);
	            return true;
	        case R.id.action_settings:
	        	Intent profileModifIntent = new Intent(this, ProfileChange.class);
	        	startActivity(profileModifIntent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
