/**
 * 
 */
package ch.epfl.sweng.radin.storage;

/**
 * @author julied20
 *
 */
public class UserModel extends Model{

	private String mFirstName;
	private String mLastName;
	private String mEmail;
	private String mAddress;
	private String mIban;
	private String mPicture;
	private int mId;

	public UserModel(String firstName, String lastName, String email, 
			String address, String iban, String picture, int id) {
		mFirstName = firstName;
		mLastName = lastName;
		mEmail = email;
		mAddress = address;
		mIban = iban;
		mPicture = picture;
		mId = id;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public String getLastName() {
		return mLastName;
	}

	public String getEmail() {
		return mEmail;
	}

	public String getAddress() {
		return mAddress;
	}

	public String getIban() {
		return mIban;
	}

	public String getPicture() {
		return mPicture;
	}

	public int getId() {
		return mId;
	}

	public void setFirstName(String firstName) {
		mFirstName = firstName;
	}
	public void setLastName(String lastName) {
		mLastName = lastName;
	}
	public void setEmail(String email) {
		mEmail = email;
	}
	public void setAddress(String address) {
		mAddress = address;
	}
	public void setIban(String iban) {
		mIban = iban;
	}
	public void setPicture(String picture) {
		mPicture = picture;
	}
}