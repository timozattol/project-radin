/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import ch.epfl.sweng.radin.callback.RadinListener;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * @author CedricCook
 *
 */
public class UserStorageManager extends StorageManager<UserModel> {
	
	private UserStorageManager userStorageManager;
	private Parser parser = new UserParser();
	
	private UserStorageManager() {}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getStorageManager()
	 */
	@Override
	public StorageManager<UserModel> getStorageManager() {
		if (userStorageManager == null) {
			userStorageManager = new UserStorageManager();
			return userStorageManager;
		} else {
			return userStorageManager;
		}
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getById(int)
	 */
	@Override
	public boolean getById(int id, RadinListener callback) {
		String accessUrl = "user/" + id;
		if(id < 0) {
			throw new IllegalArgumentException("User id must be positive");
		}
		
		if (isConnected()) {
			new UserConnectionTask().execute(SERVER_BASE_URL + accessUrl, "GET");
		}
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getAll()
	 */
	@Override
	public boolean getAll(RadinListener callback) {
		String accessUrl = "users/";
		
		if(isConnected()) {
			new UserConnectionTask().execute(SERVER_BASE_URL + accessUrl, "GET");
		}
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#create(java.util.List)
	 */
	@Override
	public boolean create(List<UserModel> entries, RadinListener callback) {
		String accessUrl = "users/";
		
		if(isConnected()) {
			new UserConnectionTask().execute(SERVER_BASE_URL + accessUrl, "POST", parser.modelsToJson(entries));
		}
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#update(java.util.List)
	 */
	@Override
	public boolean update(List<UserModel> entries, RadinListener callback) {
		String accessUrl = "users/";
		
		if (isConnected()) {
			new UserConnectionTask().execute(SERVER_BASE_URL + accessUrl, "PUT", parser.modelsToJson(entries));
		}
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#delete(java.util.List)
	 */
	@Override
	public boolean delete(List<UserModel> entries, RadinListener callback) {
		String accessUrl = "users/";
		
		if(isConnected()) {
			new UserConnectionTask().execute(SERVER_BASE_URL + accessUrl, "DELETE", parser.modelsToJson(entries));
		}
	}
	
	private class UserConnectionTask extends AsyncTask<String, Void, UserModel> {
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected UserModel doInBackground(String... params) {
			URL url = new URL(params[0]);
			String requestMethod = params[1];
			String jsonParams = params[2];
		};
		
		
		protected void onPostExecute(Result result) {}


	}

}
