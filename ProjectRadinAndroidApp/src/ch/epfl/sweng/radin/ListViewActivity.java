package ch.epfl.sweng.radin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		
		String listTitle = "nothing";
		Bundle extras = getIntent().getExtras(); 
		if (extras !=null) {
			listTitle = extras.getString("key");
			Toast.makeText(this, listTitle, Toast.LENGTH_SHORT).show();
		}
		
		TextView textView = (TextView) findViewById(R.id.listSelected);
	    textView.setText(listTitle);
	}

}
