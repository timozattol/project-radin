package ch.epfl.sweng.radin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ListViewActivity extends Activity {
	String mListTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);


		Bundle extras = getIntent().getExtras();
		mListTitle = extras.getString("key");

		TextView textView = (TextView) findViewById(R.id.listViewTitle);
		textView.setText(mListTitle);

		RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.listViewListLayout);
		ActionBar.addActionBar(this, thisLayout, mListTitle);
		
		Button notificationsBtn = (Button) findViewById(R.id.notificationButton);
		notificationsBtn.setOnClickListener(notificationsButtonListener);
	}
	
	private OnClickListener notificationsButtonListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			Intent displayActivityIntent = new Intent(v.getContext(), NotificationsActivity.class);
	        startActivity(displayActivityIntent);			
		}
	};

}
