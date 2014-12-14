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
 * A storage manager for the model type TransactionModel
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
