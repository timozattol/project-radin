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
public class ConnectionFactory<M extends Model> {

	/**
	 * Creates a ServerConnectionTask (extends AsyncTask) that can get data from a server 
	 */
	public ServerConnectionTask<M> createTask(
			RadinListener<M> listener, RequestType reqType, String url, JSONParser<M> jsonParser) {
		return new ServerConnectionTask<M>(listener, reqType, url, jsonParser);
	}
}
