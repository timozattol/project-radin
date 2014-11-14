package ch.epfl.sweng.radin.storage;

import java.util.List;

import ch.epfl.sweng.radin.callback.RadinListener;



/**
 * @author CedricCook
 *	A StorageManager allows CRUD operations and handles on & offline data storage.
 * @param <M> the type of Model handled by the StorageManager
 */
public interface StorageManager<M extends Model> {
	
	StorageManager<M> getStorageManager();
	
	boolean getById(int id, RadinListener<M> callback);
	
	boolean getAll(RadinListener<M> callback);

	boolean create(List<M> entries, RadinListener<M> callback);
	
	boolean update(List<M> entries, RadinListener<M> callback);
	
	boolean delete(List<M> entries, RadinListener<M> callback);
	
	String SERVER_BASE_URL = "radin.epfl.ch/";
	
}
