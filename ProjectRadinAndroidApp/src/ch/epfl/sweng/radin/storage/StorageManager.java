package ch.epfl.sweng.radin.storage;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;


/**
 * @author CedricCook
 *	A StorageManager allows CRUD operations and handles on & offline data storage.
 */
public interface StorageManager<T> {
	
	abstract StorageManager<T> getStorageManager();
	
	public T getById(int id, Activity caller);
	
	public List<T> getAll(Activity caller);

	public boolean create(List<T> entries, Activity caller);
	
	public boolean update(List<T> entries, Activity caller);
	
	public boolean delete(List<T> entries, Activity caller);
	
}
