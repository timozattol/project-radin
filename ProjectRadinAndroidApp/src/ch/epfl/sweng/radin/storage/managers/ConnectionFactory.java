/**
 * 
 */
package ch.epfl.sweng.radin.storage.managers;

import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.storage.Model;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.storage.parsers.JSONParser;

/**
 * @author CedricCook
 * @param M
 */
public class ConnectionFactory<M extends Model> {

	public ServerConnectionTask<M> createTask(
			RadinListener<M> listener, RequestType reqType, String url, JSONParser<M> jsonParser ){
		return new ServerConnectionTask<M>(listener, reqType, url, jsonParser);
	}
}
