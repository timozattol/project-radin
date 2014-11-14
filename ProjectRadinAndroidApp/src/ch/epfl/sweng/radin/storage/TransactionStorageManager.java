/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.util.List;

import org.json.JSONObject;

import ch.epfl.sweng.radin.callback.RadinListener;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

/**
 * @author CedricCook
 *
 */
public class TransactionStorageManager extends StorageManager<TransactionModel> {
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
