/**
 * 
 */
package ch.epfl.sweng.radin.storage.managers;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsJSONParser;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;

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
		return "transactions";
	}
	
	public void getAllForGroupId(int groupId, RadinListener<TransactionWithParticipantsModel> callback) {
		final String ACCESS_URL = "withcoeffs";
		if (isConnected()) {
			if (!isHashMatchServer()) {
				ServerConnectionTask connTask = new ServerConnectionTask(callback, RequestType.GET,
				        SERVER_BASE_URL + getTypeUrl() + "/" + ACCESS_URL + "/" + String.valueOf(groupId));
				connTask.execute();
				return;
			}
		}
	}

}
