package ch.epfl.sweng.radin;

import java.util.ArrayList;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.storage.RadinGroupModel;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
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

	private static RadinGroupModel mRadinGroupModel;
	final static int ACTION_BAR_COUNT = 5;

	/**
	 * 
	 * @author Fabien Zellweger
	 * Enum to supress magic number in code
	 *
	 */
	private static enum ListButton {
		SETTINGS,
		MY_RADIN_GROUP,
		ADD_EXPENSE,
		STATS,
		BALANCE;

		public int getValue() {
			return ordinal();
		}
	}
	
	/**
	 * 
	 * @author Fabien Zellweger
	 * Enum to supress magic number in code
	 *
	 */
	private static enum RadinGroupIndex {
		RADINGROUPID,
		RADINGROUPCREATIONDATETIME,
		RADINGROUPENAME,
		RADINGROUPDESCRIPTION,
		RADINGROUPAVATAR,
		RADINGROUPMASTERID;
		
		public int getValue() {
			return ordinal();
		}
	}
	
	private final static String NOMASTERIDDETECTOR = "noArg";

	public static void addActionBar(Context context, RelativeLayout currentLayout, RadinGroupModel radinGroupName) {

		mRadinGroupModel = radinGroupName;

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

	public static Bundle makeModelToBundle(RadinGroupModel rgm) {
		Bundle bundle = new Bundle();
		
		ArrayList<String> modelList = new ArrayList<String>();
		modelList.add(Integer.toString(rgm.getRadinGroupID()));
		modelList.add(rgm.getGroupCreationDateTime().toString());
		modelList.add(rgm.getRadinGroupName());
		modelList.add(rgm.getGroupDescription());
		modelList.add(rgm.getAvatar());
		modelList.add(rgm.hasMasterID() ? Integer.toString(rgm.getMasterID()) : "noArg");
		
		bundle.putStringArrayList("ModelKey", modelList);
		
		return bundle;
	}
	
	public static RadinGroupModel getRadinGroupModelFromBundle(Bundle bundle) {
		ArrayList<String> modelList = bundle.getStringArrayList("ModelKey");
		if (modelList.get(RadinGroupIndex.RADINGROUPMASTERID.getValue()).equals(NOMASTERIDDETECTOR)) {
			return new RadinGroupModel(Integer.parseInt(modelList.get(RadinGroupIndex.RADINGROUPID.getValue())),
					new DateTime(modelList.get(RadinGroupIndex.RADINGROUPCREATIONDATETIME.getValue())),
					modelList.get(RadinGroupIndex.RADINGROUPENAME.getValue()),
					modelList.get(RadinGroupIndex.RADINGROUPDESCRIPTION.getValue()),
					modelList.get(RadinGroupIndex.RADINGROUPAVATAR.getValue()));
		} else {
		
			return new RadinGroupModel(Integer.parseInt(modelList.get(RadinGroupIndex.RADINGROUPID.getValue())),
					new DateTime(modelList.get(RadinGroupIndex.RADINGROUPCREATIONDATETIME.getValue())),
					modelList.get(RadinGroupIndex.RADINGROUPENAME.getValue()),
					modelList.get(RadinGroupIndex.RADINGROUPDESCRIPTION.getValue()),
					modelList.get(RadinGroupIndex.RADINGROUPAVATAR.getValue()),
					Integer.parseInt(modelList.get(RadinGroupIndex.RADINGROUPMASTERID.getValue())));
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
				Bundle bundle = makeModelToBundle(mRadinGroupModel);
				displayActivityIntent.putExtras(bundle);
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
