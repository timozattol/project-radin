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
	
	void callback(List<Model> items);
}
