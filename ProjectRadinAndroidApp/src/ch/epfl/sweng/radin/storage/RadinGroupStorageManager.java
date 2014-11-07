/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.util.List;

import android.app.Activity;
import ch.epfl.sweng.radin.storage.RadinGroupModel;

/**
 * @author CedricCook
 *
 */
public class RadinGroupStorageManager implements StorageManager<RadinGroupModel> {

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getStorageManager()
	 */
	@Override
	public StorageManager<RadinGroupModel> getStorageManager() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getById(int, android.app.Activity)
	 */
	@Override
	public RadinGroupModel getById(int id, Activity caller) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getAll(android.app.Activity)
	 */
	@Override
	public List<RadinGroupModel> getAll(Activity caller) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<RadinGroupModel> getAllByUserId(int userId, Activity caller) {
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#create(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean create(List<RadinGroupModel> entries, Activity caller) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#update(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean update(List<RadinGroupModel> entries, Activity caller) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#delete(java.util.List, android.app.Activity)
	 */
	@Override
	public boolean delete(List<RadinGroupModel> entries, Activity caller) {
		// TODO Auto-generated method stub
		return false;
	}

}
