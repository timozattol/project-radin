/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.util.List;

import android.app.Activity;

/**
 * @author CedricCook
 *
 */
public class RadinGroupStorageManager implements StorageManager<T> {

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getStorageManager()
	 */
	@Override
	public StorageManager<T> getStorageManager() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getById(int, android.app.Activity)
	 */
	@Override
	public T getById(int id, Activity caller) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getAll(android.app.Activity)
	 */
	@Override
	public List<T> getAll(Activity caller) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#create(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean create(List<T> entries, Activity caller) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#update(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean update(List<T> entries, Activity caller) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#delete(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean delete(List<T> entries, Activity caller) {
		// TODO Auto-generated method stub
		return false;
	}

}
