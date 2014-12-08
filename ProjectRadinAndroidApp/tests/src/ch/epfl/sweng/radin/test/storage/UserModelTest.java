/**
 * 
 */
package ch.epfl.sweng.radin.test.storage;

import ch.epfl.sweng.radin.storage.UserModel;
import android.test.AndroidTestCase;

/**
 * @author julied20
 *
 */
public class UserModelTest extends AndroidTestCase {


	private String mFirstName = null;
	private String mLastName = null;
	private String mPassword = null;
	private String mEmail = null;
	private String mAddress = null;
	private String mIban = null;
	private String mBicSwift = null;
	private String mPicture = null;
	private int mId = 0;
	private UserModel user;
	
	/* (non-Javadoc)
	 * @see android.test.AndroidTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		user = new UserModel(mFirstName, mLastName, mPassword, mEmail, mAddress, 
				mIban, mBicSwift, mPicture, mId);
	}

	/**
	 * Tests all the getters and setters
	 */
	public void testGettersSetters() {
		assertTrue(user.getFirstName() == null);
		user.setFirstName("Julie");
		assertTrue(user.getFirstName() == "Julie");

		assertTrue(user.getLastName() == null);
		user.setLastName("Djeffal");
		assertTrue(user.getLastName() == "Djeffal");

		assertTrue(user.getEmail() == null);
		user.setEmail("julie.djeffal@gmail.com");
		assertTrue(user.getEmail() == "julie.djeffal@gmail.com");
		
		assertTrue(user.getPassword() == null);
		user.setPassword("Radin");
		assertTrue(user.getPassword() == "Radin");

		assertTrue(user.getAddress() == null);
		user.setAddress("Avenue de Cour 15 1007 Lausanne");
		assertTrue(user.getAddress() == "Avenue de Cour 15 1007 Lausanne");
		
		assertTrue(user.getIban() == null);
		user.setIban("My Iban");
		assertTrue(user.getIban() == "My Iban");
		
		assertTrue(user.getBicSwift() == null);
		user.setBicSwift("My Bic");
		assertTrue(user.getBicSwift() == "My Bic");
		
		assertTrue(user.getPicture() == null);
		user.setPicture("My Picture");
		assertTrue(user.getPicture() == "My Picture");
		
		assertTrue(user.getId() == 0);
		user.setId(1234);
		assertTrue(user.getId() == 1234);
	}
}
