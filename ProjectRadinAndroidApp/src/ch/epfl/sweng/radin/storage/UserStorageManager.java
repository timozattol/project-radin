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
	
	private UserStorageManager userStorageManager = null;
	
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
	
	@Override
	public JSONParser<UserModel> getJSONParser() {
		if (mJsonParser == null) {
			mJsonParser = new UserJSONParser();
		}
		return mJsonParser;
	}

	@Override
	protected String getTypeUrl() {
		return "user";
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
