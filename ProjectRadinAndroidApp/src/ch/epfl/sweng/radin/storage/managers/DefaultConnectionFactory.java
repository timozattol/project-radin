/**
 * 
 */
package ch.epfl.sweng.radin.storage.managers;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.Model;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;

/**
 * A factory that will create ServerConnectionTask objects
 * @author CedricCook
 * @param <M> The Model to parametrize with
 */
public class DefaultConnectionFactory<M extends Model> implements ConnectionFactory<M> {

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.radin.storage.managers.ConnectionFactory#createTask(
	 * ch.epfl.sweng.radin.callback.RadinListener, ch.epfl.sweng.radin.storage.RequestType, java.lang.String, 
	 * ch.epfl.sweng.radin.storage.parsers.JSONParser)
	 */
	@Override
	public ServerConnectionTask<M> createTask(RadinListener<M> listener,
			RequestType reqType, String url, JSONParser<M> jsonParser) {
		return new ServerConnectionTask<M>(listener, reqType, url, jsonParser);
	}


}