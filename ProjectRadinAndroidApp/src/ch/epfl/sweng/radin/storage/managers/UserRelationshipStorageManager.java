package ch.epfl.sweng.radin.storage.managers;

import java.util.List;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;
import ch.epfl.sweng.radin.storage.parsers.UserJSONParser;

/**
 * A StorageManager allows CRUD operations and handles on data storage.
 * @author Simonchelbc & Jokau
 *
 */
public final class UserRelationshipStorageManager extends StorageManager<UserModel> {

	private static UserRelationshipStorageManager userRelationShipsStorageManager = null;
	
	private UserRelationshipStorageManager() {
	}
	
    /**
     * @return the singleton UserRelationshipStorageManager
     */
	public static UserRelationshipStorageManager getStorageManager() {
		if (userRelationShipsStorageManager == null) {
			userRelationShipsStorageManager = new UserRelationshipStorageManager();
		}		
		return userRelationShipsStorageManager;
	}
	
	@Override
	public JSONParser<UserModel> getJSONParser() {
		return new UserJSONParser();
	}

	@Override
	protected String getTypeUrl() {
		return "userRelationships";
	}
	
	/**
	 * Gets a list of users who are friends with the user with ID userId
	 * @param userId ID of the users that we want the friends of.
	 * @param callback UserModel callback
	 */
    public void getFriendsOfUserWithID(int userId, RadinListener<UserModel> callback) {
    	getById(userId, callback);
    }
    
    public void createRelationshipWithId(int userId, List<UserModel> users, RadinListener<UserModel> callback) {
    	createWithID(userId, users, callback);
		//TODO create RelationShipUserParser!! Should not be here?
    }

}
