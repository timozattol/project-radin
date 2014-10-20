package ch.epfl.sweng.radin;

import ch.epfl.sweng.radin.R.id;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Simonchelbc
 *
 */
public class RegisterActivity extends Activity {

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
	 * Sends the new client information to the server
	 * 
	 */
	private OnClickListener signUpButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			submitInformationToDatabase();
		}
	};
	
	private void submitInformationToDatabase() {
		
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
