/**
 * 
 */
package ch.epfl.sweng.radin;

/**
 * @author julied20
 *
 */
public class UserModel {
	
	private String mFirstName;
	private String mLastName;
	private String mEmail;
	private String mAddress;
	private String mIban;
	private String mPicture;
	
	UserModel(String firstName, String lastName, String email, String address, 
			String iban, String picture) {
		mFirstName = firstName;
		mLastName = lastName;
		mEmail = email;
		mAddress = address;
		mIban = iban;
		mPicture = picture;
	}
	
	public String getFirstName () {
		return mFirstName;
	}
	
	public String getLastName () {
		return mLastName;
	}
	
	public String getEmail () {
		return mEmail;
	}
	
	public String getAddress () {
		return mAddress;
	}
	
	public String getIban () {
		return mIban;
	}
	
	public String getPicture () {
		return mPicture;
	}

}
