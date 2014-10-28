package ch.epfl.sweng.radin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Button listBtn = (Button) findViewById(R.id.myListBtn);
		listBtn.setOnClickListener(myListsButtonListener);
		Button profileBtn = (Button) findViewById(R.id.profileBtn);
		profileBtn.setOnClickListener(profileButtonListener);
		Button notificationBtn = (Button) findViewById(R.id.notificationBtn);
		notificationBtn.setOnClickListener(notificationButtonListener);
		Button contactsBtn = (Button) findViewById(R.id.contactsBtn);
		contactsBtn.setOnClickListener(contactsButtonListener);
		Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
		settingsBtn.setOnClickListener(settingsButtonListener);
		Button owerview = (Button) findViewById(R.id.overviewBtn);
		owerview.setOnClickListener(overviewButtonListener);
	}
	private OnClickListener myListsButtonListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), MyRadinGroupsActivity.class);
	        startActivity(displayActivityIntent);
			
		}
	};
	
	private OnClickListener profileButtonListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), ProfileActivity.class);
	        startActivity(displayActivityIntent);			
		}
	};
	
	private OnClickListener notificationButtonListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), NotificationsActivity.class);
	        startActivity(displayActivityIntent);			
		}
	};
	
	private OnClickListener contactsButtonListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), ContactsActivity.class);
	        startActivity(displayActivityIntent);			
		}
	};
	
	private OnClickListener settingsButtonListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), SettingsActivity.class);
	        startActivity(displayActivityIntent);			
		}
	};
	private OnClickListener overviewButtonListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), OverviewActivity.class);
	        startActivity(displayActivityIntent);			
		}
	};
}
