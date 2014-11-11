package ch.epfl.sweng.radin.storage;

import java.util.List;

import ch.epfl.sweng.radin.callback.RadinListener;



/**
 * @author CedricCook
 *	A StorageManager allows CRUD operations and handles on & offline data storage.
 */
public interface StorageManager<M extends Model> {
	
	abstract StorageManager<M> getStorageManager();
	
	public boolean getById(int id, RadinListener<M> callback);
	
	public boolean getAll(RadinListener<M> callback);

	public boolean create(List<M> entries, RadinListener<M> callback);
	
	public boolean update(List<M> entries, RadinListener<M> callback);
	
	public boolean delete(List<M> entries, RadinListener<M> callback);
	
	static final String SERVER_BASE_URL = "radin.epfl.ch/";
	
}
