/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * @author CedricCook
 *
 */
public class UserStorageManager implements StorageManager<UserModel> {
	
	private UserStorageManager userStorageManager;
	private final static String SERVER_URL = "radin.epfl.ch/";
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getStorageManager()
	 */
	@Override
	public StorageManager<UserModel> getStorageManager() {
		if (userStorageManager != null) {
			return new UserStorageManager();
		} else {
			return userStorageManager;
		}
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getById(int)
	 */
	@Override
	public UserModel getById(int id, Activity caller) {
		String accessUrl = "user/" + id;
		if(id < 0) {
			return new IllegalArgumentException("User id must be positive");
		}
		
		if (isConnected()) {
			new UserConnectionTask().execute(SERVER_URL + accessUrl, "GET", null, caller);
		}
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getAll()
	 */
	@Override
	public List<UserModel> getAll(Activity caller) {
		String accessUrl = "users/";
		
		if(isConnected()) {
			new UserConnectionTask().execute(SERVER_URL + accessUrl, "GET", null, caller);
		}
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#create(java.util.List)
	 */
	@Override
	public boolean create(List<UserModel> entries, Activity caller) {
		String accessUrl = "users/";
		
		if(isConnected()) {
			new UserConnectionTask().execute(SERVER_URL + accessUrl, "POST", entries, caller);
		}
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#update(java.util.List)
	 */
	@Override
	public boolean update(List<UserModel> entries, Activity caller) {
		String accessUrl = "users/";
		
		if(isConnected()) {
			new UserConnectionTask().execute(SERVER_URL + accessUrl, "PUT", entries, caller);
		}
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#delete(java.util.List)
	 */
	@Override
	public boolean delete(List<UserModel> entries, Activity caller) {
		String accessUrl = "users/";
		
		if(isConnected()) {
			new UserConnectionTask().execute(SERVER_URL + accessUrl, "DELETE", entries, caller);
		}
	}
	
	private class UserConnectionTask extends AsyncTask<UserModel, Void, UserModel> {
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected UserModel doInBackground(UserModel... params) {
			// TODO Auto-generated method stub
			return null;
		};
		
		
		protected void onPostExecute(Result result) {}


	}

}
