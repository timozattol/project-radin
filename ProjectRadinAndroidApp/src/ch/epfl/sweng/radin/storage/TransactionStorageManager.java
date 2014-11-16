/**
 * 
 */
package ch.epfl.sweng.radin.storage;

/**
 * @author CedricCook
 *
 */
public final class TransactionStorageManager extends StorageManager<TransactionModel> {
    private TransactionStorageManager transactionStorageManager = null;
    
    private TransactionStorageManager() {
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.StorageManager#getStorageManager()
     */
    @Override
    public StorageManager<TransactionModel> getStorageManager() {
        if (transactionStorageManager == null) {
            transactionStorageManager = new TransactionStorageManager();
        }
        return transactionStorageManager;
    }

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.StorageManager#getJSONParser()
     */
    @Override
    public JSONParser<TransactionModel> getJSONParser() {
        return new TransactionJSONParser();
    }

    /* (non-Javadoc)
     * @see ch.epfl.sweng.radin.storage.StorageManager#getTypeUrl()
     */
    @Override
    protected String getTypeUrl() {
        return "transaction";
    }

}
