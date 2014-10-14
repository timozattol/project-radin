package ch.epfl.sweng.radin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyListsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_lists);
		
		Button addBtn = (Button) findViewById(R.id.addBtn);
		addBtn.setOnClickListener(addButtonListener);
		
		/*We'll need then to import the list, and put the listener to all
		 * this one is only to work the exemple.
		*/
		TextView exempleList = (TextView) findViewById(R.id.anListExemple);
		exempleList.setOnClickListener(selectListListener);
		
	}
	
	private OnClickListener addButtonListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), NewListActivity.class);
	        startActivity(displayActivityIntent);	
			
		}
	};
	
	private OnClickListener selectListListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), ListViewActivity.class);
			
//			TextView selectedList = (TextView) v;
			String listTitle = ((TextView) v).getText().toString();
			
			displayActivityIntent.putExtra("key", listTitle);
	        startActivity(displayActivityIntent);
			
		}
	};

}
