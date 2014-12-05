/**
 * 
 */
package ch.epfl.sweng.radin.callback;

import java.util.List;

import ch.epfl.sweng.radin.storage.Model;

/**
 * A Listener of a list of Models, with a callback which should be called 
 * when the list of Models is available. 
 * @author CedricCook
 * @param <M> the Model type the Listener is listening to
 */
public interface RadinListener<M extends Model> {
	
    void callback(List<M> items, StorageManagerRequestStatus status);
}
