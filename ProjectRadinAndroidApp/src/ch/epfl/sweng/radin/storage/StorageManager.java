package ch.epfl.sweng.radin.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;

/**
 * @author CedricCook
 *	A StorageManager allows CRUD operations and handles on & offline data storage.
 * @param <M> the type of Model handled by the StorageManager
 */
public abstract class StorageManager<M extends Model> {

	private static Context context = null;
	static final String SERVER_BASE_URL = "radin.epfl.ch/";
	private JSONParser<M> mJsonParser = null;
	
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

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getById(int, android.app.Activity)
	 */
	public boolean getById(int id, RadinListener<M> callback) {
		if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.GET, 
				        SERVER_BASE_URL + getTypeUrl() + "/" + String.valueOf(id));
				connTask.execute();
			}
		}
		//TODO take the data from the local DB		
		//TODO make this value represent something.
		return true;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getAll(android.app.Activity)
	 */
	public void getAll(RadinListener<M> callback) {
		if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.GET,
				        SERVER_BASE_URL + getTypeUrl());
				connTask.execute();
				return;
			}
		}
		//TODO take the data from the local DB
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#create(java.util.List, android.app.Activity)
	 */

	public void create(List<M> entries, RadinListener<M> callback) {
		if (isConnected()) {
			ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.POST,
			        SERVER_BASE_URL + getTypeUrl());
			JSONObject json = (JSONObject) getJSONParser().getJsonFromModels(entries);
			connTask.execute(json.toString());
		}
		return;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#update(java.util.List, android.app.Activity)
	 */
	public void update(List<M> entries, RadinListener<M> callback) {
		if (isConnected()) {
			ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.PUT,
			        SERVER_BASE_URL + getTypeUrl());
			JSONObject json = (JSONObject) getJSONParser().getJsonFromModels(entries);
			connTask.execute(json.toString());
		}
		//TODO modify the data in the local DB
		return;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#delete(java.util.List, android.app.Activity)
	 */
	public void delete(List<M> entries, RadinListener<M> callback) {
		if (isConnected()) {
			ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.DELETE,
			        SERVER_BASE_URL + getTypeUrl());
			JSONObject json = (JSONObject) getJSONParser().getJsonFromModels(entries);
			connTask.execute(json.toString());
		}
		//TODO delet the data in the local DB
		return;
	}

	private boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		return networkInfo != null && networkInfo.isConnected();
	}
	
	private boolean isHashMatchServer() {
		// TODO Creat a methode who verify our hash match the one from the server
		return true;
	}

	/**
	 * An Asynchronous task who communicates with the server. 
	 * The execute method takes 3 String arguments: 
	 * 1. The url to connect to
	 * 2. The json data to post or put. (Can be empty if request method is get or delete).
	 * @author timozattol
	 *
	 */
	private class ServerConnectionTask extends AsyncTask<String, Void, String> {


		private RadinListener<M> mListener;
		private RequestType mRequestType;
		private URL mURL;
		
		public ServerConnectionTask(RadinListener<M> listener, RequestType requestType, String url) {

			mListener = listener;
			mRequestType = requestType;
			
			try {
                mURL = new URL(url);
            } catch (MalformedURLException e) {
                // TODO handle this exception
                e.printStackTrace();
            }
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			try {
			    String jsonData = "";
			    
			    // Sanity checks on params[0] (the jsonData to send)
			    if (params.length == 1) {
			        if (mRequestType != RequestType.POST || mRequestType != RequestType.PUT) {
			            throw new IllegalArgumentException("There should be jsonData "
			            		+ "to send only if POST or PUT");
			        }
                    
			        jsonData = params[0];
                } else if (params.length == 0) {
			        if (mRequestType != RequestType.GET || mRequestType != RequestType.DELETE) {
                        throw new IllegalArgumentException("There shouldn't be jsonData "
                                + "with GET or DELETE");
                    }
			    }
                
			    
				switch(mRequestType) {
                    case GET:
                        break;
                    case POST:
                        break;
                    case PUT:
                        break;
                    case DELETE:
                        break;
                    default:
                        break;
				    
				}


				HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
				conn.setRequestMethod(mRequestType.name());
				conn.setDoInput(true);
				conn.connect();

				return fetchContent(conn);
			} catch (ProtocolException e) {
                // TODO Handle this exception
            } catch (IOException e) {
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
			
			StorageManagerRequestStatus status = StorageManagerRequestStatus.OK;
			if(result.equals("FAIL")) {
				status = StorageManagerRequestStatus.FAIL;
			}
			
			try {
				json = new JSONObject(result);

				//HACK because the parser takes a list, should it take just one object instead?
				List<JSONObject> jsonList = new ArrayList<JSONObject>();
				jsonList.add(json);
				List<M> models = mJsonParser.getModelsFromJson(jsonList);

				mListener.callback(models, status);
			} catch (JSONException e) {
				e.printStackTrace();
				//TODO handle exception
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
