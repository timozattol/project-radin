package ch.epfl.sweng.radin;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class ListBalanceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_balance);
		
		Bundle extras = getIntent().getExtras();
		String listTitle = extras.getString("key");
		
		RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.balanceListLayout);
		ActionBar.addActionBar(this, thisLayout, listTitle);
	}
}
