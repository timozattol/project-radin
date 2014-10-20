package ch.epfl.sweng.radin;

import android.content.Context;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ActionBar {
	
	protected static String mListName;
	
	public static void addActionBar(Context context, RelativeLayout currentLayout, String listName){
		mListName = listName;
		final int ACTION_BAR_COUNT = 5;
		Button[] actionBarContent = new Button[ACTION_BAR_COUNT];

		Button settingsBtn = new Button(context);
		settingsBtn.setText("set");
		settingsBtn.setId(R.id.settingsActionBar);
		actionBarContent[0] = settingsBtn;

		Button myListsBtn = new Button(context);
		myListsBtn.setText("Lists");
		myListsBtn.setId(R.id.myListsActionBar);
		actionBarContent[1] = myListsBtn;

		Button addExpeseBtn = new Button(context);
		addExpeseBtn.setText("+");
		addExpeseBtn.setTextSize(24);
		addExpeseBtn.setId(R.id.addExpeseActionBar);
		actionBarContent[2] = addExpeseBtn;

		Button statsBtn = new Button(context);
		statsBtn.setText("stats");
		statsBtn.setId(R.id.statsActionBar);
		actionBarContent[3] = statsBtn;

		Button balanceBtn = new Button(context);
		balanceBtn.setText("bal");
		balanceBtn.setId(R.id.balanceActionBar);
		actionBarContent[4] = balanceBtn;

		for (int i = 0; i < actionBarContent.length; i++) {
			actionBarContent[i].setTag(i);
			actionBarContent[i].setOnClickListener(ActionBarButtonListener);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT, 
					RelativeLayout.LayoutParams.WRAP_CONTENT);				
			if (i > 0) {
				layoutParams.addRule(RelativeLayout.RIGHT_OF, actionBarContent[i-1].getId());
			}
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			currentLayout.addView(actionBarContent[i], layoutParams);
		}

	}

	

	private static OnClickListener ActionBarButtonListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			int viewTag = (Integer) v.getTag();
			Intent displayActivityIntent = null;

			
			switch (viewTag) {
			case 0: displayActivityIntent = new Intent(v.getContext(), ListConfigurationActivity.class);
				break;
			case 1: displayActivityIntent = new Intent(v.getContext(), ListViewActivity.class);
				break;
			case 2: displayActivityIntent = new Intent(v.getContext(), ListAddExpenseActivity.class);
				break;
			case 3: displayActivityIntent = new Intent(v.getContext(), ListStatsActivity.class);
				break;
			case 4: displayActivityIntent = new Intent(v.getContext(), ListBalanceActivity.class);
				break;
			default: Toast.makeText(v.getContext(), "Error, this button shouldn't exist!",
					Toast.LENGTH_SHORT).show();
			}
			if (!displayActivityIntent.equals(null)){
				displayActivityIntent.putExtra("key",mListName);
				v.getContext().startActivity(displayActivityIntent);
			}
		}
	};

}
