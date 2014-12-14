package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.UserStorageManager;

/**
 * @author Fabien Zellweger
 *	Display a list of all current group member and set it clickable with option on it.
 */
public class RadinGroupConfigurationActivity extends Activity {
	private RadinGroupModel mCurrentRadinGroupModel;
	private List<UserModel> mGroupMembers = new ArrayList<UserModel>();
	private ListView mGroupMemberListView;
	private UserArrayAdapter mUserInGroupAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radingroup_configuration);
		
		Bundle extras = getIntent().getExtras();
		mCurrentRadinGroupModel = ActionBar.getRadinGroupModelFromBundle(extras);
		
		LinearLayout thisLayout = (LinearLayout) findViewById(R.id.configurationRadinGroupLayout);
		ActionBar.addActionBar(this, thisLayout, mCurrentRadinGroupModel);
		
		UserStorageManager userStorageManager = UserStorageManager.getStorageManager();
		userStorageManager.getAllForGroupId(mCurrentRadinGroupModel.getRadinGroupID(), new RadinListener<UserModel>(){

			@Override
			public void callback(List<UserModel> items,
				  	StorageManagerRequestStatus status) {
				
				if (status == StorageManagerRequestStatus.SUCCESS) {
					mUserInGroupAdapter.setUserModels(items);
					mGroupMembers.clear();
					mGroupMembers.addAll(items);
				    } else {
				        displayErrorToast(getString(R.string.retrieving_user_group_error));				        
				    }
			}
		});
		mGroupMemberListView = (ListView) findViewById(R.id.radinConfigGroupMemberList);
		mUserInGroupAdapter = new UserArrayAdapter(this, R.layout.radin_group_config_member_list_row, mGroupMembers);
		mGroupMemberListView.setAdapter(mUserInGroupAdapter);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.radingroup_stats, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Intent intent = new Intent(this, HomeActivity.class);
	        	startActivity(intent);
	            return true;
	        case R.id.action_settings:
	         
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void displayErrorToast(String message) {
	    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	private OnClickListener radinGroupConfigButtonListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			//TODO : Change this to add a AlertDialog or else to edit the right of the group member		
		}		
	};

    /**
     * @param context the context
     * @param resource the view resource representing a row in the ListView
     * @param objects the list of TransactionModel
     */
	private class UserArrayAdapter extends ArrayAdapter<UserModel> {
		private List<UserModel> mGroupUserNamesList;
		private Context mContext;

		public UserArrayAdapter(Context context, int resource,
					 List<UserModel> objects) {
			super(context, resource, objects);
			mGroupUserNamesList = objects;
			mContext = context;				
		}

		/* (non-Javadoc)
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			View rowView = inflater.inflate(R.layout.radin_group_config_member_list_row, parent, false);
			//TODO put the right avatar of our user
			TextView textUserName = (TextView) rowView.findViewById(R.id.radinGroupConfigUserNameRaw);

			UserModel user = mGroupUserNamesList.get(position);
			textUserName.setText(user.getUsername());
			rowView.setOnClickListener(radinGroupConfigButtonListener);
			return rowView;

		}

		public void setUserModels(List<UserModel> newData) {
			mGroupUserNamesList.clear();
			mGroupUserNamesList.addAll(newData);
			notifyDataSetChanged();
		}
	}
}
