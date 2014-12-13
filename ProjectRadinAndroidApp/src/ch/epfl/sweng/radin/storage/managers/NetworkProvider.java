/**
 * 
 */
package ch.epfl.sweng.radin.storage.managers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author CedricCook
 * Constructs HttpURLConnection objects that can be used to get a connection to an url.
 */
public interface NetworkProvider {

	/**
	 * Returns a new HttpURLConnection object for the given url
	 * @author CedricCook
	 * @param url the HTTP or HTTPS url to connect to
	 * @return a new HttpURLConnection object
	 * @throws IOException when the connection was unsuccesful
	 */
	HttpURLConnection getConnection(URL url) throws IOException;
}
