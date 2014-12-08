package ch.epfl.sweng.radin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Fabien Zellweger
 * The home activity to choose which fonctionality you want to try
 */
public class HomeActivity extends Activity {
	private int mUserId;
	private SharedPreferences mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Button listBtn = (Button) findViewById(R.id.myRadinGroupBtn);
		listBtn.setOnClickListener(homeButtonListener);
		Button profileBtn = (Button) findViewById(R.id.profileBtn);
		profileBtn.setOnClickListener(homeButtonListener);
		Button notificationBtn = (Button) findViewById(R.id.notificationBtn);
		notificationBtn.setOnClickListener(homeButtonListener);
		Button contactsBtn = (Button) findViewById(R.id.contactsBtn);
		contactsBtn.setOnClickListener(homeButtonListener);
		Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
		settingsBtn.setOnClickListener(homeButtonListener);
		Button owerview = (Button) findViewById(R.id.overviewBtn);
		owerview.setOnClickListener(homeButtonListener);
		
		mPrefs = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
		mUserId = Integer.parseInt(mPrefs.getString(getString(R.string.username), ""));
	}
	private OnClickListener homeButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int selectedId = v.getId();
			Intent displayActivityIntent = null;

			switch (selectedId) {
				case R.id.myRadinGroupBtn:
					displayActivityIntent = new Intent(v.getContext(), MyRadinGroupsActivity.class);
					break;
				case R.id.profileBtn:
					displayActivityIntent = new Intent(v.getContext(), ProfileActivity.class);
					displayActivityIntent.putExtra("userId", mUserId);
					break;
				case R.id.notificationBtn:
					displayActivityIntent = new Intent(v.getContext(), NotificationsActivity.class);
					break;
				case R.id.contactsBtn:
					displayActivityIntent = new Intent(v.getContext(), ContactsActivity.class);
					break;
				case R.id.settingsBtn:
					displayActivityIntent = new Intent(v.getContext(), SettingsActivity.class);
					break;
				case R.id.overviewBtn:
					displayActivityIntent = new Intent(v.getContext(), OverviewActivity.class);
					break;
				default:
					Toast.makeText(v.getContext(), "Error, this button shouldn't exist!",
						Toast.LENGTH_SHORT).show();				
			}
			if (!(displayActivityIntent == null)) {
				startActivity(displayActivityIntent);	
			}
		}
	};

}
