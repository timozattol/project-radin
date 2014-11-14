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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import ch.epfl.sweng.radin.callback.RadinListener;

/**
 * @author CedricCook
 *	A StorageManager allows CRUD operations and handles on & offline data storage.
 * @param <M> the type of Model handled by the StorageManager
 */
public abstract class StorageManager<M extends Model> {
    
	private static Context context = null;
	static final String SERVER_BASE_URL = "radin.epfl.ch/";
	private JSONParser<M> mJsonParser = null;
	
	public abstract StorageManager<M> getStorageManager();
	
	/**
	 * Meant to be overridden by child classes, to fill jsonParser with a new 
	 * type-specific parser if empty, then return the mJsonParser
	 * @return the type-specific json parser.
	 */
	public abstract JSONParser<M> getJSONParser();

	/**
	 * Meant to be overridden by child classes, to be the specific-to-type server url.
	 * For example "user", or "radinGroup"
	 * @return the type url.
	 */
    protected abstract String getTypeUrl();
	
	/**
	 * Initiates the StorageManager with the application Context
	 * The Context is needed when we check the connection
	 * @param appContext the context of the Application
	 */
	public static void init(Context appContext) {
	    if (context == null) {
	        context = appContext;
	    }
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getById(int, android.app.Activity)
	 */
	public boolean getById(int id, RadinListener<M> callback) {
		ServerConnectionTask connTask = new ServerConnectionTask(callback);
		connTask.execute(SERVER_BASE_URL + getTypeUrl(), "GET", String.valueOf(id));
		
		//TODO make this value represent something.
		return true;
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getAll(android.app.Activity)
	 */
	public boolean getAll(RadinListener<M> callback) {
		//TODO make this value represent something.
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#create(java.util.List, android.app.Activity)
	 */

	public boolean create(List<M> entries, RadinListener<M> callback) {
		//TODO make this value represent something.
		return false;
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#update(java.util.List, android.app.Activity)
	 */
	public boolean update(List<M> entries, RadinListener<M> callback) {
		// TODO make this value represent something.
		return false;
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#delete(java.util.List, android.app.Activity)
	 */
	public boolean delete(List<M> entries, RadinListener<M> callback) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		return networkInfo != null && networkInfo.isConnected();
	}

	/**
	 * An Asynchronous task who communicates with the server. 
	 * The execute method takes 3 String arguments: 
	 * 1. The url to connect to
	 * 2. The request method (GET, POST, PUT, DELETE).
	 * 3. The json data to post or put. (Can be empty if request method is get or delete).
	 * @author timozattol
	 *
	 */
	private class ServerConnectionTask extends AsyncTask<String, Void, String> {

		private RadinListener<M> mListener;
		
		public ServerConnectionTask(RadinListener<M> listener) {
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
				String jsonData = params[2];
				
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
			//TODO check that result is valid json
			JSONObject json;
			try {
				json = new JSONObject(result);
				
				//HACK because the parser takes a list, should it take just one object instead?
				List<JSONObject> jsonList = new ArrayList<JSONObject>();
				jsonList.add(json);
				List<Model> models = mJsonParser.getModelsFromJson(jsonList);
				
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
