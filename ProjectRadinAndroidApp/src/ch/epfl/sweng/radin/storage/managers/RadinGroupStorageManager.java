/**
 * 
 */
package ch.epfl.sweng.radin.storage.managers;

import java.util.List;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;
import ch.epfl.sweng.radin.storage.parsers.RadinGroupJSONParser;

/**
 * @author CedricCook
 * A storage manager for the model type RadinGroupModel
 */

public final class RadinGroupStorageManager extends StorageManager<RadinGroupModel> {

	private static RadinGroupStorageManager radinGroupStorageManager = null;
	
	private RadinGroupStorageManager() {
	    
	}
	
    /**
     * @return the singleton RadinGroupStorageManager
     */
	public static RadinGroupStorageManager getStorageManager() {
		if (radinGroupStorageManager == null) {
			radinGroupStorageManager = new RadinGroupStorageManager();
		}
		return radinGroupStorageManager;
	}
	
    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.StorageManager#getJSONParser()
     */
    @Override
    public JSONParser<RadinGroupModel> getJSONParser() {
        return new RadinGroupJSONParser();
    }
    /* (non-Javadoc)
 	* @see ch.epfl.sweng.radin.storage.StorageManager#getTypeUrl()
 	*/
    @Override
	protected String getTypeUrl() {
    	return "radingroups";
    }
    
	public void getAllByUserId(int userId, RadinListener<RadinGroupModel> callback) {
		//TODO use base class method
		if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask<RadinGroupModel> connTask = getConnectionFactory().createTask(
						callback, RequestType.GET, SERVER_BASE_URL + "radingroups/" + userId, getJSONParser());
				connTask.execute();
				return;
			}
		}
		// TODO take the data from the local DB
	}
	
	/**
	 * Posts a Radin group (without participants) to the database.
	 * @param radinGroup List consisting of one RadinGroup to post
	 * @param callback callback
	 */
	public void createRadinGroup(List<RadinGroupModel> radinGroup, RadinListener<RadinGroupModel> callback) {
		createWithID(-1, radinGroup, callback);
	}
}


