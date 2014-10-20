package ch.epfl.sweng.radin;

import ch.epfl.sweng.radin.R.id;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Simonchelbc
 * 
 */
public class RegisterActivity extends Activity {
	private boolean newUserDataUsable = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		Button signUpButton = (Button) findViewById(id.sign_up_button);
		signUpButton.setOnClickListener(signUpButtonListener);
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
			retrieveNewUserData();
			if (newUserDataUsable) {
				submitUserToServer();
				Intent displayHomeActivityIntent = new Intent(v.getContext(),
						HomeActivity.class);
				startActivity(displayHomeActivityIntent);
			}

		}
	};

	/**
	 * Sends the new user's data to the server
	 */
	private void submitUserToServer() {
		
	}

	/**
	 * Package fields of the different {@code TextView} in a object that will be sent to the server
	 * if a {@code TextView} contains badly formatted input resets the field of the {@code TextView} and sets
	 * {@code newUserDataUsable} to false
	 * sets {@code newUserDataUsable} to true if input is in good format
	 */
	private void retrieveNewUserData() {
		final CharSequence newUserFirstName = ((TextView) findViewById(id.first_name_new_user))
				.getText();
		final CharSequence newUserLastName = ((TextView) findViewById(id.last_name_new_user))
				.getText();
		final CharSequence newUserEmail = ((TextView) findViewById(id.email_new_user))
				.getText();
		final CharSequence newUserPassword = ((TextView) findViewById(id.password_new_user))
				.getText();
		final CharSequence newUserIban = ((TextView) findViewById(id.iban_new_user))
				.getText();
		final CharSequence newUserBicSwift = ((TextView) findViewById(id.bic_swift_address_new_user))
				.getText();
		
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
