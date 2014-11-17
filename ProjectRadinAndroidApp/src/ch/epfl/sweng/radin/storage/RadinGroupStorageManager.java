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

import android.os.AsyncTask;
import ch.epfl.sweng.radin.callback.RadinListener;

/**
 * @author CedricCook
 *
 */

public final class RadinGroupStorageManager extends StorageManager<RadinGroupModel> {

	private RadinGroupStorageManager radinGroupStorageManager = null;
	
	private RadinGroupStorageManager() {
	    
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getStorageManager()
	 */
	public StorageManager<RadinGroupModel> getStorageManager() {
		if (radinGroupStorageManager == null) {
			radinGroupStorageManager = new RadinGroupStorageManager();
		}
		return radinGroupStorageManager;
	}
	
	@Override
	public JSONParser<RadinGroupModel> getJSONParser() {
		if (mJsonParser == null) {
			mJsonParser = new RadinGroupJSONParser();
		}
		return mJsonParser;
	}

	@Override
	protected String getTypeUrl() {
		return "radinGroup";
	}
    
    public boolean getAllByUserId(int userId, RadinListener<RadinGroupModel> callback) {
    	if (isConnected()) {
			if (!isHashMatchServer()) {
				RadinGroupConnectionTask connTask = new RadinGroupConnectionTask(callback);
				connTask.execute(SERVER_BASE_URL + getTypeUrl() + "/" + String.valueOf(userId), "GET");
			}
		}
		//TODO take the data from the local DB		
		//TODO make this value represent something.
		return true;
    }

	
	private class RadinGroupConnectionTask extends AsyncTask<String, Void, String> {

		private RadinListener<RadinGroupModel> mListener;

		public RadinGroupConnectionTask(RadinListener<RadinGroupModel> listener) {
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
				if (requestMethod.equals("GET")) {
					db.select(jsonParams);
				}
				
//				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	            conn.setRequestMethod(requestMethod);
//	            conn.setDoInput(true);
//	            conn.connect();
//
//	            return fetchContent(conn);

				return null;
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
			JSONParser<RadinGroupModel> jsonParser = new RadinGroupJSONParser();
			//TODO check that result is valid json
			JSONObject json;
			try {
				json = new JSONObject(result);
				
				//HACK because the parser takes a list, should it take just one object instead?
				List<JSONObject> jsonList = new ArrayList<JSONObject>();
				jsonList.add(json);
				List<RadinGroupModel> models = jsonParser.getModelsFromJson(jsonList);
				
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


