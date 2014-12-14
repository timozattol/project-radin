package ch.epfl.sweng.radin;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;

/**
 * @author topali2
 * Shows the informations of an user.
 */
public class ProfileActivity extends Activity {
	public static final String PREFS = "PREFS";
	private UserModel mProfileUser;
	private SharedPreferences mPrefs;
	private int mCurrentUserId;
	private int mSearchingId;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		Button modifBtn = (Button) findViewById(R.id.modifPofileBtn);
		modifBtn.setOnClickListener(modifProfileButtonListener);

		mPrefs = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
		
		mCurrentUserId = Integer.parseInt(mPrefs.getString(getString(R.string.username), ""));
		mSearchingId = getIntent().getIntExtra("userId", mCurrentUserId);
		
		
		UserStorageManager userStorageManager = UserStorageManager.getStorageManager();
		userStorageManager.getById(mSearchingId, new RadinListener<UserModel>() {

			@Override
			public void callback(List<UserModel> items, StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					if (items.size() == 1) {						
						mProfileUser = items.get(0);
						displayUser();
					} else {
						displayErrorToast(getString(R.string.wrong_user_data));
					}

				} else {
					displayErrorToast(getString(R.string.retrieving_user_error));
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
	 * Refresh view with user data.
	 */
	public void displayUser() {

	    //TODO add profile picture
		//ImageView profilePicture = (ImageView) findViewById(R.id.profilePic);

		TextView firstName = (TextView) findViewById(R.id.profileFirstName);
		firstName.setText(mProfileUser.getFirstName());

		TextView lastName = (TextView) findViewById(R.id.profileLastName);
		lastName.setText(mProfileUser.getLastName());

		TextView username = (TextView) findViewById(R.id.profileUsername);
		username.setText(mProfileUser.getUsername());

		TextView email = (TextView) findViewById(R.id.profileEmail);
		email.setText(mProfileUser.getEmail());

		TextView address = (TextView) findViewById(R.id.profileAddress);
		address.setText(mProfileUser.getAddress());

		TextView iBan = (TextView) findViewById(R.id.profileIban);
		iBan.setText(mProfileUser.getIban());

		TextView bicSwift = (TextView) findViewById(R.id.profileBicSwift);
		bicSwift.setText(mProfileUser.getBicSwift());

	}

	private OnClickListener modifProfileButtonListener = 
			new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int selectedId = v.getId();
						Intent displayActivityIntent = null;

						switch (selectedId){
							case R.id.modifPofileBtn:
								displayActivityIntent = 
								new Intent(v.getContext(), EditProfile.class);
					        	if (mSearchingId == mCurrentUserId) {
					        		displayActivityIntent = 
											new Intent(v.getContext(), EditProfile.class);
					        		startActivity(displayActivityIntent);
					        	} else {
					        		displayErrorToast(getString(R.string.edit_friends_profile));
					        	}
								break;
							default:
								displayErrorToast(
										getString(R.string.invalid_button));
						}
					}
				};

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
	        	if (mSearchingId == mCurrentUserId) {
	        		Intent profileModifIntent = new Intent(this, EditProfile.class);
	        		startActivity(profileModifIntent);
	        	} else {
	        		displayErrorToast(getString(R.string.edit_friends_profile));
	        	}
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
