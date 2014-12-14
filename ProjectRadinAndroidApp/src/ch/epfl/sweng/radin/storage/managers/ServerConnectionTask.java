/**
 * 
 */
package ch.epfl.sweng.radin.storage.managers;


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

import android.os.AsyncTask;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.Model;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;

/**
 * An Asynchronous task who communicates with the server.
 * @param <M> the type of model to be transmitted to the server. 
 * The execute method the json data to post or put. 
 * (Can be empty if request method is get or delete).
 * @author timozattol
 *
 */
public class ServerConnectionTask<M extends Model> extends AsyncTask<String, Void, String> {

	private static final int SUCCESS_CODE = 200;
	private RadinListener<M> mListener;
	private RequestType mRequestType;
	private URL mURL;
	private NetworkProvider mNetworkProvider;
	private JSONParser<M> mJSONParser;

	public ServerConnectionTask(RadinListener<M> listener, RequestType requestType, 
			String url, JSONParser<M> jsonParser) {
		if (listener == null) {
			throw new IllegalArgumentException("The RadinListener should not be null");
		} else if (requestType == null) {
			throw new IllegalArgumentException("The RequestType should not be null");
		} else if (url == null || url.equals("")) {
			throw new IllegalArgumentException("The url should not be null nor empty");
		} else if (jsonParser == null) {
			throw new IllegalArgumentException("The JsonParser shouldn't be null");
		}

		mListener = listener;
		mRequestType = requestType;
		mJSONParser = jsonParser;
		mNetworkProvider = new NetworkProvider() {
			/* (non-Javadoc)
			 * @see ch.epfl.sweng.radin.storage.managers.NetworkProvider#getConnection(java.net.URL)
			 */
			@Override
			public HttpURLConnection getConnection(URL url) throws IOException {
				return (HttpURLConnection) url.openConnection();
			}
		};

		try {
			mURL = new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("The url should be a valid url");
		}
	}

	public void setNetworkProvider(NetworkProvider networkProvider) {
		if (networkProvider == null) {
			throw new IllegalArgumentException("Networkprovider must be non-null");
		}
		mNetworkProvider = networkProvider;
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

			HttpURLConnection conn = mNetworkProvider.getConnection(mURL);
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
				e.printStackTrace();
				mListener.callback(new ArrayList<M>(), StorageManagerRequestStatus.FAILURE);
				return;
			}

			List<M> parsedModels;

			try {
				parsedModels = mJSONParser.getModelsFromJson(jsonResult);
				mListener.callback(parsedModels, StorageManagerRequestStatus.SUCCESS);
			} catch (JSONException e) {
				mListener.callback(new ArrayList<M>(), StorageManagerRequestStatus.FAILURE);
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