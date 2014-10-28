package ch.epfl.sweng.radin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Fabien Zellweger
 * This Activity give a view of the selected radin group
 *
 */
public class RadinGroupViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radingroup_view);


		Bundle extras = getIntent().getExtras();
		String radinGroupTitle = extras.getString("key");

		TextView textView = (TextView) findViewById(R.id.radinGroupViewTitle);
		textView.setText(radinGroupTitle);

		RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.radinGroupViewLayout);
		ActionBar.addActionBar(this, thisLayout, radinGroupTitle);
		
		Button notificationsBtn = (Button) findViewById(R.id.notificationButton);
		notificationsBtn.setOnClickListener(notificationsButtonListener);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.radingroup_view, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
	}
    
	private OnClickListener notificationsButtonListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), NotificationsActivity.class);
	        startActivity(displayActivityIntent);			
		}
	};

}
