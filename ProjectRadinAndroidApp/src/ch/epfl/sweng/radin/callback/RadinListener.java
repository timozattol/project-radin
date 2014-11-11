/**
 * 
 */
package ch.epfl.sweng.radin.callback;

import java.util.List;

import ch.epfl.sweng.radin.storage.Model;

/**
 * @author CedricCook
 *
 */
public interface RadinListener {
	
	//turn on the listener
	void callFromStorageManagerTrue();

	
	public void callback(List<Model> items);


	void setId(int userId);


}
