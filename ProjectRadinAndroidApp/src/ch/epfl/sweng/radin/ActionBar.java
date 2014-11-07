package ch.epfl.sweng.radin;

import android.content.Context;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 
 * @author Fabien Zellweger
 * Class to add the ActionBar on all concerned list activity.
 * It genere the button, and place them correctly with a listener
 *
 */
public class ActionBar {

	private static String mRadinGroupName;
	final static int ACTION_BAR_COUNT = 5;

	/**
	 * 
	 * @author Fabien Zellweger
	 * Enum to supress magic number in code
	 *
	 */
	public static enum ListButton {
		SETTINGS (0),
		MY_RADIN_GROUP (1),
		ADD_EXPENSE (2),
		STATS (3),
		BALANCE (4);

		private int mValue;

		ListButton(int value) {
			this.mValue=value;
		}

		public int getValue() {
			return mValue;
		}
	}

	public static void addActionBar(Context context, RelativeLayout currentLayout, String radinGroupName) {

		mRadinGroupName = radinGroupName;

		Button[] actionBarContent = new Button[ACTION_BAR_COUNT];

		Button settingsBtn = new Button(context);
		settingsBtn.setText("set");
		settingsBtn.setId(R.id.settingsActionBar);
		actionBarContent[ListButton.SETTINGS.getValue()] = settingsBtn;
		settingsBtn.setTag(ListButton.SETTINGS);

		Button myListsBtn = new Button(context);
		myListsBtn.setText("Group");
		myListsBtn.setId(R.id.myRadinGroupsActionBar);
		actionBarContent[ListButton.MY_RADIN_GROUP.getValue()] = myListsBtn;
		myListsBtn.setTag(ListButton.MY_RADIN_GROUP);

		Button addExpeseBtn = new Button(context);
		addExpeseBtn.setText("+");
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
			actionBarContent[i].setOnClickListener(actionBarButtonListener);
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



	private static OnClickListener actionBarButtonListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			ListButton viewTag = (ListButton) v.getTag();
			Intent displayActivityIntent = null;


			switch(viewTag) {
				case SETTINGS: 
					displayActivityIntent = new Intent(v.getContext(),
							RadinGroupConfigurationActivity.class);
					break;
				case MY_RADIN_GROUP: 
					displayActivityIntent = new Intent(v.getContext(),
						RadinGroupViewActivity.class);
					break;
				case ADD_EXPENSE:
					displayActivityIntent = new Intent(v.getContext(),
						RadinGroupAddExpenseActivity.class);
					break;
				case STATS:
					displayActivityIntent = new Intent(v.getContext(),
						RadinGroupStatsActivity.class);
					break;
				case BALANCE:
					displayActivityIntent = new Intent(v.getContext(),
						RadinGroupBalanceActivity.class);
					break;
				default:
					Toast.makeText(v.getContext(), "Error, this button shouldn't exist!",
						Toast.LENGTH_SHORT).show();
			}
			if (!(displayActivityIntent == null)) {
				displayActivityIntent.putExtra("key", mRadinGroupName);
				/*
				 * set flag to correct the behaviors of creating new activity
				 * FLAG_ACTIVITY_REORDER_TO_FRONT check if the activity already exist and reorder it to front
				 * FLAG_ACTIVITY_SINGLE_TOP don't allow creating an new activity of the current activity
				 */
				displayActivityIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
				v.getContext().startActivity(displayActivityIntent);
			}
		}
	};

}
