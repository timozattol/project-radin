package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;
/**
 * 
 * @author topali2
 *
 */
public class ProfileChange extends DashBoardActivity {

	private UserModel newProfileModel;
	private List<UserModel> userModelList = new ArrayList<UserModel>();
	private UserStorageManager userStorageManager;
	private int userId;
	private SharedPreferences prefs;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_profile_change);
		setHeader(getString(R.string.title_activity_profile_change), true, true);

		prefs = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
		
		
//		This is a fake userId used to test the app		
		userId = Integer.parseInt(prefs.getString(getString(R.string.username), ""));



		Button buttonSave = (Button) findViewById(R.id.profileButtonSaveChanges);
		userStorageManager = UserStorageManager.getStorageManager();

		buttonSave.setOnClickListener(profileChangeButtonListener);

		retrieveUserInformation();

	}


	private OnClickListener profileChangeButtonListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {


			EditText newFirstName = (EditText) findViewById(R.id.editProfileFirstName);
			if (!newFirstName.getText().toString().isEmpty()) {
				newProfileModel.setFirstName(newFirstName.getText().toString());
			}

			EditText newLastName = (EditText) findViewById(R.id.editProfileLastName);
			if (!newLastName.getText().toString().isEmpty()) {
				newProfileModel.setLastName(newLastName.getText().toString());
			}

			EditText newUsername = (EditText) findViewById(R.id.editProfileUsername);
			if (!newUsername.getText().toString().isEmpty()) {
				newProfileModel.setUsername(newUsername.getText().toString());
			}

			EditText newAddress = (EditText) findViewById(R.id.editProfileAddress);
			if (!newAddress.getText().toString().isEmpty()) {
				newProfileModel.setAddress(newAddress.getText().toString());
			}
			
			EditText newEmail = (EditText) findViewById(R.id.editProfileEmail);
			if (!newEmail.getText().toString().isEmpty()) {
				newProfileModel.setEmail(newEmail.getText().toString());
			}

			EditText newIBan = (EditText) findViewById(R.id.editProfileIBan);
			if (!newIBan.getText().toString().isEmpty()) {
				newProfileModel.setIban(newIBan.getText().toString());
			}
			
			EditText newBicSwift = (EditText) findViewById(R.id.editProfileBicSwift);
			if (!newBicSwift.getText().toString().isEmpty()) {
				newProfileModel.setBicSwift(newBicSwift.getText().toString());
			}

			userModelList.add(newProfileModel);

			userStorageManager.update(userModelList, new RadinListener<UserModel>() {

				@Override
				public void callback(List<UserModel> items,
						StorageManagerRequestStatus status) {
					if (status == StorageManagerRequestStatus.SUCCESS) {

						Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
						startActivity(profile);
					} else {

						displayErrorToast("Error uploading userProfile informations");
					}

				}

			});

		};

	};

	/**
	 * 
	 */
	private void retrieveUserInformation() {

		userStorageManager.getById(userId, new RadinListener<UserModel>() {

			@Override
			public void callback(List<UserModel> items,
					StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.SUCCESS) {
					if (items.size() == 1) {						
						newProfileModel = items.get(0);

					} else {
						displayErrorToast("Error wrong user informations");
					}

				} else {

					newProfileModel = new UserModel(null, null, null, null, null, null, null, null, userId);
					displayErrorToast("connection server error");

				}


			}
		});

	}

	/**
	 * 
	 * @param message of the error
	 */
	private void displayErrorToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_change, menu);
		return true;
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

	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
