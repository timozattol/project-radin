/**
 * 
 */
package ch.epfl.sweng.radin.storage;

/**
 * @author CedricCook
 *
 */
public final class UserStorageManager extends StorageManager<UserModel> {
	
	private UserStorageManager userStorageManager;
	
	private UserStorageManager() {

	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.StorageManager#getStorageManager()
	 */
	@Override
	public StorageManager<UserModel> getStorageManager() {
		if (userStorageManager == null) {
			userStorageManager = new UserStorageManager();
		}
		
		return userStorageManager;
	}

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.StorageManager#getJSONParser()
     */
    @Override
    public JSONParser<UserModel> getJSONParser() {
        return new UserJSONParser();
    }

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.StorageManager#getTypeUrl()
     */
    @Override
    protected String getTypeUrl() {
        return "user";
    }

}
