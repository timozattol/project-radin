/**
 * 
 */
package ch.epfl.sweng.radin.storage.managers;

import java.util.List;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.managers.StorageManager.ServerConnectionTask;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;
import ch.epfl.sweng.radin.storage.parsers.UserJSONParser;

/**
 * @author CedricCook
 *
 */
public final class UserStorageManager extends StorageManager<UserModel> {

	private static UserStorageManager userStorageManager = null;
	
	private UserStorageManager() {

	}
	
    /**
     * @return the singleton UserStorageManager
     */
	public static UserStorageManager getStorageManager() {
		if (userStorageManager == null) {
			userStorageManager = new UserStorageManager();
		}		
		return userStorageManager;
	}
	
    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.StorageManager#getJSONParser()
     */
    @Override
    public JSONParser<UserModel> getJSONParser() {
        return new UserJSONParser();
    }

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.StorageManager#getTypeUrl()
     */
    @Override
    protected String getTypeUrl() {
        return "users";
    }
    
    public void getAllForGroupId(int radinGroupId, RadinListener<UserModel> callback) {
		final String ACCESS_URL = "inradingroup";
		if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.GET,
				        SERVER_BASE_URL + getTypeUrl() + "/" + ACCESS_URL + "/" + String.valueOf(radinGroupId));
				//Example url: http://radin.epfl.ch/users/inradingroup/1
				connTask.execute();
				return;
			}
		}
    }
    

    public void putMembersInRadinGroup(int radinGroupID, List<UserModel> members, RadinListener<UserModel> callback) {
    	createWithID(radinGroupID, members, callback);
    	//Example url: http://radin.epfl.ch/users/1
    }
}
