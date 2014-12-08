package ch.epfl.sweng.radin;
import java.util.List;

import ch.epfl.sweng.radin.R.id;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.StorageManager;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;
import android.content.SharedPreferences; 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 *
 * @author Fabien Zellweger
 * Login activity, to log an user, if right login, can go further in the app, or
 * give the opportunity to register.
 *
 */
public class LoginActivity extends Activity {
	private String mUsername = null;
	private String mPassword = null;
	public static final String PREFS = "PREFS";
	private SharedPreferences prefs;
	private boolean validateLogin = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button loginBtn = (Button) findViewById(R.id.loginButton);
		loginBtn.setOnClickListener(loginActivityButtonListener);
		Button newAccountBtn = (Button) findViewById(R.id.createAcountButton);
		newAccountBtn.setOnClickListener(loginActivityButtonListener);

		StorageManager.init(this);
		
		prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

	}
	private OnClickListener loginActivityButtonListener = 
			new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int selectedId = v.getId();
			Intent displayActivityIntent = null;

			switch (selectedId){
			case R.id.loginButton:
				retrieveRegisterUser();
				verifyUser();
				Log.d("validate Login 2 = ", String.valueOf(validateLogin));
				if (validateLogin == true) {
				displayActivityIntent = 
						new Intent(v.getContext(), HomeActivity.class);
				} else {
					displayActivityIntent = 
							new Intent(v.getContext(), LoginActivity.class);	
				}
				break;
			case R.id.createAcountButton:
				displayActivityIntent = 
				new Intent(v.getContext(), RegisterActivity.class);
				break;
			default:
				Toast.makeText(v.getContext(), 
						"Error, this button shouldn't exist!",
						Toast.LENGTH_SHORT).show();				
			}
			if (!(displayActivityIntent == null)) {
				startActivity(displayActivityIntent);	
			}
		}		
	};

	private void verifyUser() {
		UserStorageManager userStorageManager =
				UserStorageManager.getStorageManager();

		userStorageManager.verifyLogin(mUsername, mPassword, 
				new RadinListener<UserModel>() {

			@Override
			public void callback(List<UserModel> items,
					StorageManagerRequestStatus status) {
				if (status == StorageManagerRequestStatus.FAILURE) {
					Toast.makeText(getApplicationContext(),
							R.string.login_error, Toast.LENGTH_SHORT).show();
					
				} else {
					
					UserModel mUser = items.get(0);
					int mId = mUser.getId();
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString(getString(R.string.username), 
							String.valueOf(mId));
					editor.commit();
					Toast.makeText(getApplicationContext(),
							R.string.success_login, Toast.LENGTH_SHORT).show();
					validateLogin = true;
					Log.d("validate Login 1 = ", String.valueOf(validateLogin));
				}
			}
		});
	}

	private void retrieveRegisterUser() {
		final CharSequence userUsername = 
				((TextView) findViewById(id.login)).getText();
		final CharSequence userPassword = 
				((TextView) findViewById(id.password)).getText();

		mUsername = userUsername.toString();
		mPassword = userPassword.toString();

	}
}