package ch.epfl.sweng.radin.storage;

import java.util.List;

import org.json.JSONObject;


/**
 * @author CedricCook
 *	A StorageManager allows CRUD operations and handles on & offline data storage.
 */
public interface StorageManager {
	
	abstract StorageManager getStorageManager();
	
	public JSONObject getById(int id);
	
	public List<JSONObject> getAll();

	public boolean create(List<JSONObject> entries);
	
	public boolean update(List<JSONObject> entries);
	
	public boolean delete(List<JSONObject> entries);
	
}
