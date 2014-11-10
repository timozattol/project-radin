/**
 * 
 */
package ch.epfl.sweng.radin.callback;

import java.util.List;

import ch.epfl.sweng.radin.storage.RadinGroupModel;

/**
 * @author CedricCook
 *
 */
public interface RadinListener {

	//This need to discuted and changed, maybe with a parent Model class.
	boolean isUpdateRunning(List<RadinGroupModel> rgm);

	//turn on the listener
	void callFromStorageManagerTrue();

}
