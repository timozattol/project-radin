package ch.epfl.sweng.radin.storage.managers;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.Model;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;

/**
 * @author CedricCook
 *	A StorageManager allows CRUD operations and handles on & offline data storage.
 * @param <M> the type of Model handled by the StorageManager
 */
public abstract class StorageManager<M extends Model> {

	private static Context mContext = null;
	private ConnectionFactory<M> mConnectionFactory = new ConnectionFactory<M>();
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
	 * 
	 * @return the connectionFactory of the class
	 */
	protected ConnectionFactory<M> getConnectionFactory() {
		return mConnectionFactory;
	}
	
	/**
	 * set A different connectionFactory
	 */
	public void setConnectionFactory(ConnectionFactory<M> connFactory) {
		mConnectionFactory = connFactory;
	}


	/**
	 * Initiates the StorageManager with the application Context
	 * The Context is needed when we check the connection
	 * @param appContext the context of the Application
	 * @param networkProvider the networkProvider to get a connection from
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
				ServerConnectionTask<M> connTask = mConnectionFactory.createTask(callback, RequestType.GET, 
				        SERVER_BASE_URL + getTypeUrl() + "/" + String.valueOf(id), getJSONParser());
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
				ServerConnectionTask<M> connTask = mConnectionFactory.createTask(callback, RequestType.GET,
				        SERVER_BASE_URL + getTypeUrl(), getJSONParser());
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
		createHelper("", entries, callback);
		return;
	}
	
	/**
	 * create entries in database, used with an ID to create relationships.
	 *
	 */
	public void createWithID(int id, List<M> entries, RadinListener<M> callback) {
		if (id == -1) {
			createHelper("", entries, callback);
			return;
		} else {
			createHelper(String.valueOf(id), entries, callback);
			return;
		}
	}
	
	/**
	 * Auxiliary method to create an entry in the database.
	 * @param id	depends on the url of the root, it is a number encoded as a string when we want to add 
	 * a list of {@link Model} items to an entry with this id, it is an empty string otherwise. 
	 * @param entries the data to put on the database
	 * @param callback callback
	 */
	private void createHelper(String id, List<M> entries, RadinListener<M> callback) {
		String endUrl = "";
		if (!id.isEmpty()) {
			endUrl += "/" + id;
		}
		if (isConnected()) {
			ServerConnectionTask<M> connTask = mConnectionFactory.createTask(callback, RequestType.POST,
			        SERVER_BASE_URL + getTypeUrl() + endUrl, getJSONParser());
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
			ServerConnectionTask<M> connTask = mConnectionFactory.createTask(callback, RequestType.PUT,
			        SERVER_BASE_URL + getTypeUrl(), getJSONParser());
			
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
			ServerConnectionTask<M> connTask = mConnectionFactory.createTask(callback, RequestType.DELETE,
			        SERVER_BASE_URL + getTypeUrl(), getJSONParser());
			
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


}
