package ch.epfl.sweng.radin;

import java.util.ArrayList;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.storage.RadinGroupModel;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * @author Fabien Zellweger
 * Class to add the ActionBar on all concerned list activity.
 * It generates the buttons, and places them correctly with a listener
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

	public static void addActionBar(Context context, LinearLayout currentLayout, RadinGroupModel radinGroupName) {
	    final float halfInFloat = 0.5f;
	    final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
	    final float buttonRelativePadding = 15f;
	    int buttonAbsolutePadding = (int) (metrics.density * buttonRelativePadding + halfInFloat);
	    
		mRadinGroupModel = radinGroupName;

		ImageButton[] actionBarContent = new ImageButton[ACTION_BAR_COUNT];

		ImageButton settingsBtn = new ImageButton(context);
		settingsBtn.setImageResource(R.drawable.glyphicon_settings);
		settingsBtn.setBackgroundColor(context.getResources().getColor(R.color.header));
		settingsBtn.setId(R.id.settingsActionBar);
		actionBarContent[ListButton.SETTINGS.getValue()] = settingsBtn;
		settingsBtn.setTag(ListButton.SETTINGS);

		ImageButton myListsBtn = new ImageButton(context);
		myListsBtn.setImageResource(R.drawable.glyphicon_list);
		myListsBtn.setId(R.id.myRadinGroupsActionBar);
		myListsBtn.setBackgroundColor(context.getResources().getColor(R.color.header));
		actionBarContent[ListButton.MY_RADIN_GROUP.getValue()] = myListsBtn;
		myListsBtn.setTag(ListButton.MY_RADIN_GROUP);

		ImageButton addExpenseBtn = new ImageButton(context);
		addExpenseBtn.setImageResource(R.drawable.glyphicon_plus);
		addExpenseBtn.setId(R.id.addExpeseActionBar);
		addExpenseBtn.setBackgroundColor(context.getResources().getColor(R.color.header));
		actionBarContent[ListButton.ADD_EXPENSE.getValue()] = addExpenseBtn;
		addExpenseBtn.setTag(ListButton.ADD_EXPENSE);

		ImageButton statsBtn = new ImageButton(context);
		statsBtn.setImageResource(R.drawable.glyphicon_charts);
		statsBtn.setId(R.id.statsActionBar);
		statsBtn.setBackgroundColor(context.getResources().getColor(R.color.header));
		actionBarContent[ListButton.STATS.getValue()] = statsBtn;
		statsBtn.setTag(ListButton.STATS);

		ImageButton balanceBtn = new ImageButton(context);
		balanceBtn.setImageResource(R.drawable.glyphicon_pie_chart);
		balanceBtn.setId(R.id.balanceActionBar);
		balanceBtn.setBackgroundColor(context.getResources().getColor(R.color.header));
		actionBarContent[ListButton.BALANCE.getValue()] = balanceBtn;
		balanceBtn.setTag(ListButton.BALANCE);

		for (int i = 0; i < actionBarContent.length; i++) {
			actionBarContent[i].setOnClickListener(actionBarButtonListener);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					  LinearLayout.LayoutParams.MATCH_PARENT, 
					  LinearLayout.LayoutParams.MATCH_PARENT,
					  (float) (1.0/ACTION_BAR_COUNT));
			actionBarContent[i].setPadding(
			        buttonAbsolutePadding, buttonAbsolutePadding,
			        buttonAbsolutePadding, buttonAbsolutePadding);
			actionBarContent[i].setAdjustViewBounds(true);
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
					Toast.makeText(v.getContext(), v.getContext().getResources().getString(R.string.invalid_button),
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
