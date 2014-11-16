/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import ch.epfl.sweng.radin.callback.RadinListener;

/**
 * @author CedricCook
 *
 */

public final class RadinGroupStorageManager extends StorageManager<RadinGroupModel> {

	private static RadinGroupStorageManager radinGroupStorageManager = null;
	
	private RadinGroupStorageManager() {
	    
	}
	
    /**
     * @return the singleton RadinGroupStorageManager
     */
	public static RadinGroupStorageManager getStorageManager() {
		if (radinGroupStorageManager == null) {
			radinGroupStorageManager = new RadinGroupStorageManager();
		}
		return radinGroupStorageManager;
	}

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.StorageManager#getJSONParser()
     */
    @Override
    public JSONParser<RadinGroupModel> getJSONParser() {
        return new RadinGroupJSONParser();
    }

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.StorageManager#getTypeUrl()
     */
    @Override
    protected String getTypeUrl() {
        return "radinGroup";
    }

    //TODO implement this, and add javadoc
    public void getAllByUserId(int userId,
            RadinListener<RadinGroupModel> radinListener) {
        
    }

}
