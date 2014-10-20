package ch.epfl.sweng.radin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author Fabien Zellweger
 * Login activity, to log an user, if right login, can go further in the app, or
 * give the opportunity to register.
 *
 */
public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button loginBtn = (Button) findViewById(R.id.loginButton);
		loginBtn.setOnClickListener(loginButtonListener);
		Button registerBtn = (Button) findViewById(R.id.registerButton);
		registerBtn.setOnClickListener(registerButtonListener);
	}
	
	private OnClickListener registerButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent displayRegisterActivityIntent = new Intent(v.getContext(), RegisterActivity.class);
			startActivity(displayRegisterActivityIntent);
		}
	};
	
	private OnClickListener loginButtonListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			Intent displayHomeActivityIntent = new Intent(v.getContext(), HomeActivity.class);
	        startActivity(displayHomeActivityIntent);			
		}
	};



}
