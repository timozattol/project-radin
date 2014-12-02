/**
 * 
 */
package ch.epfl.sweng.radin.storage.managers;

import android.util.Log;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;
import ch.epfl.sweng.radin.storage.parsers.UserJSONParser;


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
    
        
    public void verifyLogin(String username, String password, 
    		RadinListener<UserModel> callback) {
    	if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask connTask = 
						new ServerConnectionTask(callback, RequestType.POST,
						SERVER_BASE_URL + "login/" + username);
				Log.d("URL", SERVER_BASE_URL + "login/" + username );
				connTask.execute("{\"password\": \"" + password + "\"}");
				return;
			}
		}
    }
    

    public void getAllForGroupId(int groupId, RadinListener<UserModel> callback) {
    	//TODO: implement
    }

}
