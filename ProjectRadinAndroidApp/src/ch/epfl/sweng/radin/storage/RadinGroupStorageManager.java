/**
 * 
 */
package ch.epfl.sweng.radin.storage;

/**
 * @author CedricCook
 *
 */

public final class RadinGroupStorageManager extends StorageManager<RadinGroupModel> {

	private RadinGroupStorageManager radinGroupStorageManager = null;
	
	private RadinGroupStorageManager() {
	    
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getStorageManager()
	 */
	public StorageManager<RadinGroupModel> getStorageManager() {
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

}
