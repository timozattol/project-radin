/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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
	public boolean getById(int id, RadinListener callback) {
		RadinGroupConnectionTask connTask = new RadinGroupConnectionTask(callback);
		connTask.execute(SERVER_BASE_URL, "GET", String.valueOf(id));
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getAll(android.app.Activity)
	 */
	@Override
	public boolean getAll(RadinListener callback) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean getAllByUserId(int userId, RadinListener callback) {
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

		private RadinListener mListener;
		
		public RadinGroupConnectionTask(RadinListener listener) {
			mListener = listener;
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				String requestMethod = params[1];
				String jsonParams = params[2];
				
				//Database AND/OR server:
				// Very rough pseudo code! must see with server and database for specifics.
				Database db = new Database();
				if(requestMethod.equals("GET")) {
					db.select(jsonParams);
				}
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod(requestMethod);
	            conn.setDoInput(true);
	            conn.connect();
	            
	            return fetchContent(conn);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			JSONParser jsonParser = new RadinGroupJSONParser();
			//TODO check that result is valid json
			JSONObject json;
			try {
				json = new JSONObject(result);
				
				//HACK because the parser takes a list, should it take just one object instead?
				List<JSONObject> jsonList = new ArrayList<JSONObject>();
				jsonList.add(json);
				List<Model> models = jsonParser.getModelsFromJson(jsonList);
				
				mListener.callback(models);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		
	    private String fetchContent(HttpURLConnection conn) throws IOException {
	        // Credits go to http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
	        InputStream is = conn.getInputStream();
	        try {
	            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	            return s.hasNext() ? s.next() : "";
	        } finally {
	            is.close();
	        }
	    }
		
	}

}
