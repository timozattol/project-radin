package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.radin.R.id;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Simonchelbc
 * 
 */
public class RegisterActivity extends Activity {
	private boolean newUserDataUsable = false;
	private UserModel mNewUser = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		Button signUpButton = (Button) findViewById(id.sign_up_button);
		signUpButton.setOnClickListener(signUpButtonListener);
		mNewUser = new UserModel();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	/**
	 * Change to {@code HomeActivity} if the data could be sent to the server
	 */
	private OnClickListener signUpButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				retrieveNewUserData();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (newUserDataUsable) {
				try {
					submitUserToServer();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent displayHomeActivityIntent = new Intent(v.getContext(),
						HomeActivity.class);
				// TODO destroy this activity when communication with server
				// done
				startActivity(displayHomeActivityIntent);

			}

		}
	};

	/**
	 * Sends the new user's data to the server
	 * @throws JSONException 
	 */
	private void submitUserToServer() throws JSONException {
		
		
		UserStorageManager userStorageManager = 
				UserStorageManager.getStorageManager();
		
		List<UserModel> newUserList = new ArrayList<UserModel>();
		newUserList.add(mNewUser);
		
		JSONParser<UserModel> newUserParser = 
				userStorageManager.getJSONParser();
		//The JSON I want to send to the server
		JSONObject JSONNewUser = newUserParser.getJsonFromModels(newUserList);
		
		
		userStorageManager.sendNewUser(JSONNewUser, 
				new RadinListener<UserModel>() {

			@Override
			public void callback(List<UserModel> items,
					StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.FAILURE) {
					Toast.makeText(getApplicationContext(),
							R.string.server_error, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(),
							R.string.user_created, Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		

	}

	/**
	 * Package fields of the different {@code TextView} in a UserModel that 
	 * will be sent to the server, if a {@code TextView} contains badly 
	 * formatted input resets the field of the {@code TextView} and sets
	 * {@code newUserDataUsable} to false sets {@code newUserDataUsable} to true
	 * if input is in good format
	 * @throws JSONException 
	 */
	private void retrieveNewUserData() throws JSONException {
		
		final CharSequence newUserFirstName = 
				((TextView) findViewById(id.first_name_new_user)).getText();	      
		final CharSequence newUserLastName = 
				((TextView) findViewById(id.last_name_new_user)).getText();
		final CharSequence newUserUsername = 
				((TextView) findViewById(id.username_new_user)).getText();
		final CharSequence newUserEmail = 
				((TextView) findViewById(id.email_new_user)).getText();
		final CharSequence newUserPassword = 
				((TextView) findViewById(id.password_new_user)).getText();
		final CharSequence newUserAddress = 
				((TextView) findViewById(id.address_new_user)).getText();
		final CharSequence newUserIban = 
				((TextView) findViewById(id.iban_new_user)).getText();
		final CharSequence newUserBicSwift = 
				((TextView) findViewById(id.bic_swift_address_new_user))
				.getText();
		
		mNewUser.setFirstName(newUserFirstName.toString());
		mNewUser.setLastName(newUserLastName.toString());
		mNewUser.setUsername(newUserUsername.toString());
		mNewUser.setPassword(newUserPassword.toString()); 
		mNewUser.setEmail(newUserEmail.toString()); 
		mNewUser.setAddress(newUserAddress.toString()); 
		mNewUser.setIban(newUserIban.toString()); 
		mNewUser.setBicSwift(newUserBicSwift.toString());
		mNewUser.setPicture("drawable/profile_pic");
		mNewUser.setId(-1);

		newUserDataUsable = true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
