/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.RadinGroupModel;

/**
 * @author CedricCook
 *
 */
public class RadinGroupStorageManager implements StorageManager<RadinGroupModel> {

	private RadinGroupStorageManager radinGroupStorageManager = null;
	
	private RadinGroupStorageManager() {}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getStorageManager()
	 */
	@Override
	public StorageManager<RadinGroupModel> getStorageManager() {
		if(radinGroupStorageManager == null) {
			radinGroupStorageManager = new RadinGroupStorageManager();
		}
		return radinGroupStorageManager;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getById(int, android.app.Activity)
	 */
	@Override
	public RadinGroupModel getById(int id, RadinListener callback) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getAll(android.app.Activity)
	 */
	@Override
	public List<RadinGroupModel> getAll(RadinListener callback) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<RadinGroupModel> getAllByUserId(int userId, RadinListener callback) {
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#create(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean create(List<RadinGroupModel> entries, RadinListener callback) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#update(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean update(List<RadinGroupModel> entries, RadinListener callback) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#delete(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean delete(List<RadinGroupModel> entries, RadinListener callback) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		return (networkInfo != null && networkInfo.isConnected());
	}
	
	private class RadinGroupConnectionTask extends AsyncTask<String, Void, String> {

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			URL url = new URL(params[0]);
			String requestMethod = params[1];
			String jsonParams = params[2];
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
	}

}
