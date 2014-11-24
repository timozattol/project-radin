package ch.epfl.sweng.radin.storage.managers;

import java.util.List;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;

/**
 * TODO Class to implement
 * @author 
 *
 */
public final class TransactionWithParticipantsStorageManager extends StorageManager<TransactionWithParticipantsModel> {

	private static TransactionWithParticipantsStorageManager transWParticipantsStorageManager = null;
	
	private TransactionWithParticipantsStorageManager() {

	}
	
    /**
     * @return the singleton UserStorageManager
     */
	public static TransactionWithParticipantsStorageManager getStorageManager() {
		if (transWParticipantsStorageManager == null) {
			transWParticipantsStorageManager = new TransactionWithParticipantsStorageManager();
		}		
		return transWParticipantsStorageManager;
	}
	
	
	@Override
	public JSONParser<TransactionWithParticipantsModel> getJSONParser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTypeUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void create(List<TransactionWithParticipantsModel> entries,
			RadinListener<TransactionWithParticipantsModel> callback) {
		//TODO implement method
	}
}
