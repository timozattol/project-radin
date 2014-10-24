/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.util.List;

import org.json.JSONObject;

/**
 * @author CedricCook
 *
 */
public class UserStorageManager implements StorageManager {

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getStorageManager()
	 */
	@Override
	public StorageManager getStorageManager() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getById(int)
	 */
	@Override
	public JSONObject getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getAll()
	 */
	@Override
	public List<JSONObject> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#create(java.util.List)
	 */
	@Override
	public boolean create(List<JSONObject> entries) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#update(java.util.List)
	 */
	@Override
	public boolean update(List<JSONObject> entries) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#delete(java.util.List)
	 */
	@Override
	public boolean delete(List<JSONObject> entries) {
		// TODO Auto-generated method stub
		return false;
	}

}
