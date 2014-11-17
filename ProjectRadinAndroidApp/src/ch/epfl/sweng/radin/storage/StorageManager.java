package ch.epfl.sweng.radin.storage;

import java.io.DataOutputStream;
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
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;

/**
 * @author CedricCook
 *	A StorageManager allows CRUD operations and handles on & offline data storage.
 * @param <M> the type of Model handled by the StorageManager
 */
public abstract class StorageManager<M extends Model> {

	private static Context mContext = null;
	static final String SERVER_BASE_URL = "http://radin.epfl.ch/";

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
		if (mContext == null) {
			mContext = appContext;
		}
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getById(int, android.app.Activity)
	 */
	public void getById(int id, RadinListener<M> callback) {
		if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.GET, 
				        SERVER_BASE_URL + getTypeUrl() + "/" + String.valueOf(id));
				connTask.execute();
				return;
			}
		}
		//TODO take the data from the local DB
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
			JSONObject json;
			
            try {
                json = (JSONObject) getJSONParser().getJsonFromModels(entries);
                connTask.execute(json.toString());
            } catch (JSONException e) {
                // TODO Handle error
                e.printStackTrace();
            }
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
			
			JSONObject json;
            try {
                json = (JSONObject) getJSONParser().getJsonFromModels(entries);
                connTask.execute(json.toString());
            } catch (JSONException e) {
                // TODO Handle error
            }
			
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
			
			JSONObject json;
            try {
                json = (JSONObject) getJSONParser().getJsonFromModels(entries);
                connTask.execute(json.toString());
            } catch (JSONException e) {
                // TODO Handle error
            }
		}
		//TODO delete the data in the local DB
		return;
	}

	protected boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		return networkInfo != null && networkInfo.isConnected();
	}
	
	protected boolean isHashMatchServer() {
		// TODO Create a method that verifies that our hash matches the one from the server
		return false;
	}

	/**
	 * An Asynchronous task who communicates with the server. 
	 * The execute method takes 3 String arguments: 
	 * 1. The url to connect to
	 * 2. The json data to post or put. (Can be empty if request method is get or delete).
	 * @author timozattol
	 *
	 */
	protected class ServerConnectionTask extends AsyncTask<String, Void, String> {

	    private static final int SUCCESS_CODE = 200;
		private RadinListener<M> mListener;
		private RequestType mRequestType;
		private URL mURL;
		
		public ServerConnectionTask(RadinListener<M> listener, RequestType requestType, String url) {
		    if (listener == null) {
		        throw new IllegalArgumentException("The RadinListener should not be null");
		    } else if (requestType == null) {
		        throw new IllegalArgumentException("The RequestType should not be null");
		    } else if (url == null || url.equals("")) {
		        throw new IllegalArgumentException("The url should not be null nor empty");
		    }
		    
			mListener = listener;
			mRequestType = requestType;
			
			try {
                mURL = new URL(url);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("The url should be a valid url");
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
			        if (mRequestType == RequestType.GET || mRequestType == RequestType.DELETE) {
			            throw new IllegalArgumentException("There shouldn't be jsonData "
                                + "with GET or DELETE");
			        }
                    
			        jsonData = params[0];

                } else if (params.length == 0) {
			        if (mRequestType == RequestType.POST || mRequestType == RequestType.PUT) {
			            throw new IllegalArgumentException("There should be jsonData "
                                + "when POST or PUT");
                        
                    }
			    } else {
			        throw new IllegalArgumentException("There should be zero or one argument "
                            + "to the execute method");
			    }
			    
			    HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
			    conn.setRequestMethod(mRequestType.name());
			    
				switch(mRequestType) {
                    case GET:
                        conn.connect();

                        break;

                    case POST:
                    case PUT:
                        conn.setDoOutput(true);
                        conn.setRequestProperty("Content-Type", "application/json");
                        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                        wr.writeBytes(jsonData);
                        wr.flush();
                        wr.close();

                        break;
                        
                    case DELETE:
                        conn.connect();

                        break;
                        
                    default:
                        throw new IllegalStateException("The request type must be one of the 4 values,"
                        		+ " since it should not be null");
				}
				
				if (conn.getResponseCode() != SUCCESS_CODE) {
				    return "FAILURE";
				}

				String response = fetchContent(conn);

				return response;
            } catch (IOException e) {
                return "FAILURE";
            }
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			 
			if (result.equals("FAILURE")) {
			    mListener.callback(new ArrayList<M>(), StorageManagerRequestStatus.FAILURE);
			} else {
			    JSONObject jsonResult;

                try {
                    jsonResult = new JSONObject(result);
                } catch (JSONException e) {
                    mListener.callback(new ArrayList<M>(), StorageManagerRequestStatus.FAILURE);
                    return;
                }
                
			    List<M> parsedModels;
			    
                try {
                    parsedModels = getJSONParser().getModelsFromJson(jsonResult);
                    mListener.callback(parsedModels, StorageManagerRequestStatus.SUCCESS);
                } catch (JSONException e) {
                    // TODO Handle error
                }
			}
		}

		private String fetchContent(HttpURLConnection conn) throws IOException {
			// Credits go to http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
			InputStream is = conn.getInputStream();
			try {
			    java.util.Scanner s = new java.util.Scanner(is);
				java.util.Scanner scanner = s.useDelimiter("\\A");
				
				String result = scanner.hasNext() ? scanner.next() : "";
				
				s.close();
				scanner.close();
				
				return result;
			} finally {
				is.close();
			}
		}
	}

}
