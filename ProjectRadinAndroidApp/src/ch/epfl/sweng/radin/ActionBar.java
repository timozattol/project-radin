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
	
	public static enum ListButton {
		SETTINGS (0),
		MY_LISTS (1),
		ADD_EXPENSE (2),
		STATS (3),
		BALANCE (4);
		
		private int mValue;
		
		ListButton(int value){
			this.mValue=value;
		}
		
		public int getValue(){
			return mValue;
		}
	}
	
	public static void addActionBar(Context context, RelativeLayout currentLayout, String listName){

		mListName = listName;
		final int ACTION_BAR_COUNT = 5;
		Button[] actionBarContent = new Button[ACTION_BAR_COUNT];

		Button settingsBtn = new Button(context);
		settingsBtn.setText("set");
		settingsBtn.setId(R.id.settingsActionBar);
		actionBarContent[ListButton.SETTINGS.getValue()] = settingsBtn;
		settingsBtn.setTag(ListButton.SETTINGS);

		Button myListsBtn = new Button(context);
		myListsBtn.setText("Lists");
		myListsBtn.setId(R.id.myListsActionBar);
		actionBarContent[ListButton.MY_LISTS.getValue()] = myListsBtn;
		myListsBtn.setTag(ListButton.MY_LISTS);

		Button addExpeseBtn = new Button(context);
		addExpeseBtn.setText("+");
		addExpeseBtn.setTextSize(24);
		addExpeseBtn.setId(R.id.addExpeseActionBar);
		actionBarContent[ListButton.ADD_EXPENSE.getValue()] = addExpeseBtn;
		addExpeseBtn.setTag(ListButton.ADD_EXPENSE);

		Button statsBtn = new Button(context);
		statsBtn.setText("stats");
		statsBtn.setId(R.id.statsActionBar);
		actionBarContent[ListButton.STATS.getValue()] = statsBtn;
		statsBtn.setTag(ListButton.STATS);

		Button balanceBtn = new Button(context);
		balanceBtn.setText("bal");
		balanceBtn.setId(R.id.balanceActionBar);
		actionBarContent[ListButton.BALANCE.getValue()] = balanceBtn;
		balanceBtn.setTag(ListButton.BALANCE);

		for (int i = 0; i < actionBarContent.length; i++) {
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
			ListButton viewTag = (ListButton) v.getTag();
			Intent displayActivityIntent = null;

			
			switch (viewTag) {
			case SETTINGS: displayActivityIntent = new Intent(v.getContext(), ListConfigurationActivity.class);
				break;
			case MY_LISTS: displayActivityIntent = new Intent(v.getContext(), ListViewActivity.class);
				break;
			case ADD_EXPENSE: displayActivityIntent = new Intent(v.getContext(), ListAddExpenseActivity.class);
				break;
			case STATS: displayActivityIntent = new Intent(v.getContext(), ListStatsActivity.class);
				break;
			case BALANCE: displayActivityIntent = new Intent(v.getContext(), ListBalanceActivity.class);
				break;
			default: Toast.makeText(v.getContext(), "Error, this button shouldn't exist!",
					Toast.LENGTH_SHORT).show();
			}
			if (!(displayActivityIntent == null)){
				displayActivityIntent.putExtra("key",mListName);
				v.getContext().startActivity(displayActivityIntent);
			}
		}
	};

}
