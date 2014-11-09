package ch.epfl.sweng.radin.storage;

import java.util.List;

import ch.epfl.sweng.radin.callback.RadinListener;



/**
 * @author CedricCook
 *	A StorageManager allows CRUD operations and handles on & offline data storage.
 */
public interface StorageManager<T> {
	
	abstract StorageManager<T> getStorageManager();
	
	public T getById(int id, RadinListener callback);
	
	public List<T> getAll(RadinListener callback);

	public boolean create(List<T> entries, RadinListener callback);
	
	public boolean update(List<T> entries, RadinListener callback);
	
	public boolean delete(List<T> entries, RadinListener callback);
	
	static final String SERVER_BASE_URL = "radin.epfl.ch/";
	
}
