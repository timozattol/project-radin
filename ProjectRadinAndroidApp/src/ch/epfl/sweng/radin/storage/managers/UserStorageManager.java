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
    

    
    public void verifyLogin(String username, String password, 
    		RadinListener<UserModel> callback) {
    	if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask connTask = 
						new ServerConnectionTask(callback, RequestType.POST,
								SERVER_BASE_URL + "login/" + username);
				connTask.execute("{\"password\": \"" + password + "\"}");
			}
    	}
    }

    /**
     * Gets a list of users who are friends with the user with ID userId
     * @param userId ID of the users that we want the friends of.
     * @param callback UserModel callback
     */
    public void getFriendsOfUserWithId(int userId, RadinListener<UserModel> callback) {
    	final String ACCESS_URL = "userRelationships";
		if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.GET,
				        SERVER_BASE_URL + ACCESS_URL + "/" + String.valueOf(userId));
				//Example url: http://radin.epfl.ch/userRelationships/1
				connTask.execute();
				return;
			}
		}
    }
    
    /**
     * Get all members of the radinGroup with id radinGroupId.
     * @param radinGroupId
     * @param callback
     */
    public void getAllForGroupId(int radinGroupId, RadinListener<UserModel> callback) {
		final String ACCESS_URL = "radingroups";
		if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.GET,
				        SERVER_BASE_URL + ACCESS_URL + "/" + String.valueOf(radinGroupId) + "/" + getTypeUrl());
				//Example url: http://radin.epfl.ch/radingroups/1/users
				connTask.execute();
				return;
			}
		}
    }
    
    /**
     * Post one user as a member of the radinGroup with id radinGroupId
     * @param radinGroupId RadinGroup's ID, to which the user will be added
     * @param user Used to add to the RadinGroup 
     * @param callback callback
     */
    public void postMemberToRadinGroup(int radinGroupId, List<UserModel> user, RadinListener<UserModel> callback) {
    	final String ACCESS_URL = "radingroups";
		if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.POST,
				        SERVER_BASE_URL + ACCESS_URL + "/" + String.valueOf(radinGroupId) + "/" + "adduser");
				//Example url: http://radin.epfl.ch/radingroups/1/adduser
				connTask.execute();

				return;
			}
		}
    }
}

