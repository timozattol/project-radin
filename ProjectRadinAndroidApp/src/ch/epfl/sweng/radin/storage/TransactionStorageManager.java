/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

/**
 * @author CedricCook
 *
 */
public class TransactionStorageManager implements StorageManager<TransactionModel> {
	TransactionStorageManager transactionStorageManager;
	private Parser parser = new TransactionParser();
	
	private TransactionStorageManager() {
		
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getStorageManager()
	 */
	@Override
	public StorageManager<TransactionModel> getStorageManager() {
		if(transactionStorageManager == null) {
			transactionStorageManager = new TransactionStorageManager();
		}
		return transactionStorageManager;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getById(int, android.app.Activity)
	 */
	@Override
	public TransactionModel getById(int id, Activity caller) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getAll(android.app.Activity)
	 */
	@Override
	public List<TransactionModel> getAll(Activity caller) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#create(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean create(List<TransactionModel> entries, Activity caller) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#update(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean update(List<TransactionModel> entries, Activity caller) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#delete(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean delete(List<TransactionModel> entries, Activity caller) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		return (networkInfo != null && networkInfo.isConnected());
	}
	
	private class TransactionConnectionTask extends AsyncTask<String, Void, String> {

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
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
