package ch.epfl.sweng.radin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Fabien Zellweger
 * The home activity to choose which fonctionality you want to try
 */


public class HomeActivity extends DashBoardActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_home);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		setHeader(getString(R.string.title_project_radin), false, false);



		/*Button listBtn = (Button) findViewById(R.id.myRadinGroupBtn);
		//listBtn.setOnClickListener(homeButtonListener);
		Button profileBtn = (Button) findViewById(R.id.profileBtn);
		//profileBtn.setOnClickListener(homeButtonListener);
		Button notificationBtn = (Button) findViewById(R.id.notificationBtn);
		//notificationBtn.setOnClickListener(homeButtonListener);
		Button contactsBtn = (Button) findViewById(R.id.contactsBtn);
		//contactsBtn.setOnClickListener(homeButtonListener);
		Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
		//settingsBtn.setOnClickListener(homeButtonListener);
		Button owerview = (Button) findViewById(R.id.overviewBtn);
		//owerview.setOnClickListener(homeButtonListener);*/
	}
	/*private OnClickListener homeButtonListener = new View.OnClickListener() {
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
		}*/
	/**
     * Button click handler on Main activity
     * @param v
     */
    public void onButtonClicker(View v)
    {
    	Intent intent;
    	
    	switch (v.getId()) {
		case R.id.myRadinGroupBtn:
			intent = new Intent(this, MyRadinGroupsActivity.class);
			startActivity(intent);
			break;

		case R.id.profileBtn:
			intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
			break;
			
		case R.id.notificationBtn:
			intent = new Intent(this,  NotificationsActivity.class);
			startActivity(intent);
			break;
			
		case R.id.contactsBtn:
			intent = new Intent(this, ContactsActivity.class);
			startActivity(intent);
			break;
			
		case R.id.settingsBtn:
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;
			
		case R.id.overviewBtn:
			intent = new Intent(this, OverviewActivity.class);
			startActivity(intent);
			break;	
		default:
			break;
		}
    }
	//};

}
