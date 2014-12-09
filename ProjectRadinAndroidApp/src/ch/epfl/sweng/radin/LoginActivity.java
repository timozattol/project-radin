package ch.epfl.sweng.radin;
import java.util.List;

import ch.epfl.sweng.radin.R.id;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.StorageManager;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;
import android.content.SharedPreferences; 
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 *
 * @author Fabien Zellweger
 * Login activity, to log an user, if right login, can go further in the app, or
 * give the opportunity to register.
 *
 */

public class LoginActivity extends DashBoardActivity {
	private String mUsername = null;
	private String mPassword = null;
	public static final String PREFS = "PREFS";
	private SharedPreferences prefs;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		Button loginBtn = (Button) findViewById(R.id.loginButton);
		loginBtn.setOnClickListener(loginActivityButtonListener);
		Button newAccountBtn = (Button) findViewById(R.id.createAcountButton);
		newAccountBtn.setOnClickListener(loginActivityButtonListener);
		EditText passwordEditText = (EditText) findViewById(R.id.password);
		passwordEditText.setOnKeyListener(loginActivityOnKeyListener);

		setHeader(getString(R.string.title_project_radin), false, false);
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
					   		  break;
					   	  case R.id.createAcountButton:
					   		  displayActivityIntent = 
					   		  new Intent(v.getContext(), RegisterActivity.class);
					   		  break;
					   	  default:
					   		  Toast.makeText(v.getContext(), 
					   				getString(R.string.invalid_button),
					  				Toast.LENGTH_SHORT).show();				
					  }
					  if (!(displayActivityIntent == null)) {
						  startActivity(displayActivityIntent);	
					  }
				  }		
				  };
	private OnKeyListener loginActivityOnKeyListener = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
		   		retrieveRegisterUser();
		   		verifyUser();
		        return true;
			}
			return false;
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
								Intent myIntent = 
								  		new Intent(getBaseContext(), LoginActivity.class);
								startActivity(myIntent);
								Toast.makeText(getApplicationContext(),
										R.string.login_error, Toast.LENGTH_SHORT).show();
					
							} else {
					
								UserModel mUser = items.get(0);
								int mId = mUser.getId();
								SharedPreferences.Editor editor = prefs.edit();
								editor.putString(getString(R.string.username), 
								  		String.valueOf(mId));
								editor.commit();
								Intent myIntent = 
								  		new Intent(getBaseContext(), HomeActivity.class);
								startActivity(myIntent);
								finish();
								Toast.makeText(getApplicationContext(),
										R.string.success_login, Toast.LENGTH_SHORT).show();
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