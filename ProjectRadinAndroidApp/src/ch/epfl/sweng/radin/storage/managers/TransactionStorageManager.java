/**
 * 
 */
package ch.epfl.sweng.radin.storage.managers;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;
import ch.epfl.sweng.radin.storage.parsers.TransactionJSONParser;

/**
 * @author CedricCook
 * Manages retrieval & storage of TransactionModels
 */
public final class TransactionStorageManager extends StorageManager<TransactionModel> {
    private static TransactionStorageManager transactionStorageManager = null;
    
    private TransactionStorageManager() {
        
    }
    
    /**
     * @return the singleton TransactionStorageManager
     */
    public static TransactionStorageManager getStorageManager() {
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
        return "transactions";
    }
    
    /**
     * Get all transactionModels that belong to a RadinGroup with the id groupId.
     * @author CedricCook
     * @param groupId the RadinGroup for which to get transactions
     * @param callback the listener to callback on when data is ready
     */
    public void getAllForGroupId(int groupId, RadinListener<TransactionModel> callback) {
    	if (isConnected()) {
    		if (!isHashMatchServer()) {
    			ServerConnectionTask<TransactionModel> connTask = getConnectionFactory().createTask(callback,
    					RequestType.GET,
    					SERVER_BASE_URL + getTypeUrl() + "/" + String.valueOf(groupId),
    					getJSONParser());
    					
				connTask.execute();
				return;
    		}
    	}
    	//TODO take the data from the local DB
    }
}
