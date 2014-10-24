/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import org.json.JSONObject;

/**
 * @author CedricCook
 *
 */
public class StorageManagerFactory implements AbstractStorageManager {

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.AbstractStorageManager#readItem(int)
	 */
	@Override
	public JSONObject readItem(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.AbstractStorageManager#createItem(org.json.JSONObject)
	 */
	@Override
	public boolean createItem(JSONObject fields) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.AbstractStorageManager#updateItem(org.json.JSONObject)
	 */
	@Override
	public boolean updateItem(JSONObject fields) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.AbstractStorageManager#deleteItem(org.json.JSONObject)
	 */
	@Override
	public boolean deleteItem(JSONObject fields) {
		// TODO Auto-generated method stub
		return false;
	}

}
