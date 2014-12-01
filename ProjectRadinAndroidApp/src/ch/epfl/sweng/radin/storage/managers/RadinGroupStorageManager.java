/**
 * 
 */
package ch.epfl.sweng.radin.storage.managers;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;
import ch.epfl.sweng.radin.storage.parsers.RadinGroupJSONParser;

/**
 * @author CedricCook
 *
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
    	return "groups";
    }
    
    public void getAllByUserId(int userId, RadinListener<RadinGroupModel> callback) {
    	if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.GET,
						SERVER_BASE_URL + "radingroups");
				connTask.execute();
				return;
			}
		}
		//TODO take the data from the local DB
    }
}


