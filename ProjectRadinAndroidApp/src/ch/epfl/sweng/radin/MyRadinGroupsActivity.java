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
import android.widget.TextView;

/**
 * 
 * @author Fabien Zellweger
 * Thic activity get all the radin group from the connected user and list them
 * to give access.
 *
 */
public class MyRadinGroupsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_radingroups);
		
		Button addBtn = (Button) findViewById(R.id.addBtn);
		addBtn.setOnClickListener(addButtonListener);
		
		/*We'll need then to import the list, and put the listener to all
		 * this one is only to work the exemple.
		*/
		TextView exempleRadinGroup = (TextView) findViewById(R.id.aRadinGroupExemple);
		exempleRadinGroup.setOnClickListener(selectListListener);
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_radingroups, menu);
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
	
	private OnClickListener addButtonListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), NewRadinGroupActivity.class);
	        startActivity(displayActivityIntent);	
			
		}
	};
	
	private OnClickListener selectListListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), RadinGroupViewActivity.class);
			
//			TextView selectedList = (TextView) v;
			String radinGroupTitle = ((TextView) v).getText().toString();
			
			displayActivityIntent.putExtra("key", radinGroupTitle);
	        startActivity(displayActivityIntent);
			
		}
	};

}
