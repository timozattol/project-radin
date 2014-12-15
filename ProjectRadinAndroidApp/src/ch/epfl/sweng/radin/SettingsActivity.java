package ch.epfl.sweng.radin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 *  This activity permits to adjust custom parameters for the app.
 */
public class SettingsActivity extends Activity {

	private SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		prefs = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
		Button logoutBtn = (Button) findViewById(R.id.logout_btn);
		logoutBtn.setOnClickListener(logoutButtonListener);
		displayId();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	private OnClickListener logoutButtonListener = 
            new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int selectedId = v.getId();
					Intent displayActivityIntent = null;

					switch (selectedId){
						case R.id.logout_btn:
							displayActivityIntent = 
							new Intent(v.getContext(), LoginActivity.class);
							finishAffinity();
							break;
						default:
							Toast.makeText(v.getContext(), 
									R.string.invalid_button,
									Toast.LENGTH_SHORT).show();				
					}
					if (!(displayActivityIntent == null)) {
						startActivity(displayActivityIntent);	
					}
				}	
			};

	//@Override
	/*public boolean onOptionsItemSelected(MenuItem item) {
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
	}*/
	
	
	private void displayId() {
		
		TextView userID = (TextView) findViewById(R.id.value_id);
		userID.setText(prefs.getString(getString(R.string.username), "NA"));
		
		
	}
}
