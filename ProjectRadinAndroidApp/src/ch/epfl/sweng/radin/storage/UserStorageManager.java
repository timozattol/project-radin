/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import org.json.JSONObject;

import ch.epfl.sweng.radin.callback.RadinListener;

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
    
    
    /**
     * To be modified once we discussed how to deal with new users
     * @param callback
     */
    public void sendNewUser(JSONObject json, RadinListener<UserModel> callback) {
    	if(isConnected()) {
    		if(!isHashMatchServer()) {
    			ServerConnectionTask connTask = 
    					new ServerConnectionTask(callback, RequestType.POST, 
    							SERVER_BASE_URL + "receiveUser");
    			connTask.execute(json.toString());
    			return;
    		}
    	}
    	
    }
}
