/**
 * 
 */
package ch.epfl.sweng.radin.storage.managers;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;
import ch.epfl.sweng.radin.storage.parsers.TransactionWithParticipantsJSONParser;


/**
 * @author CedricCook
 * A StorageManager that handles storage of the Decorator for Transaction
 *
 */
public final class TransactionWithParticipantsStorageManager extends
		StorageManager<TransactionWithParticipantsModel> {

	private static TransactionWithParticipantsStorageManager transWithParticipantsStorageManager = null;
	
	private TransactionWithParticipantsStorageManager() {
		
	}
	
	public static TransactionWithParticipantsStorageManager getStorageManager() {
        if (transWithParticipantsStorageManager == null) {
        	transWithParticipantsStorageManager = new TransactionWithParticipantsStorageManager();
        }
        return transWithParticipantsStorageManager;
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.managers.StorageManager#getJSONParser()
	 */
	@Override
	public JSONParser<TransactionWithParticipantsModel> getJSONParser() {
		return new TransactionWithParticipantsJSONParser();
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.managers.StorageManager#getTypeUrl()
	 */
	@Override
	protected String getTypeUrl() {
		return "transactions/withcoeffs";
	}
	
	/**
	 * Get all Transactions for a specific radinGroup with coefficients from server
	 * @param groupId RadinGroup's ID
	 * @param callback callback
	 */
	public void getAllForGroupId(int groupId, RadinListener<TransactionWithParticipantsModel> callback) {
		if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask<TransactionWithParticipantsModel> connTask = getConnectionFactory(
						callback, RequestType.GET,
				        SERVER_BASE_URL + getTypeUrl() + "/" + String.valueOf(groupId), getJSONParser());
				//Example url : http://radin.epfl.ch/transactions/withcoeffs/1
				connTask.execute();
				return;
			}
		}
	}
}
