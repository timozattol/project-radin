package ch.epfl.sweng.radin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	}

}
