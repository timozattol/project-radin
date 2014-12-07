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
	private String mUsername;
	private String mPassword;
	private String mEmail;
	private String mAddress;
	private String mIban;
	private String mBicSwift;
	private String mPicture;
	private int mId;
	
	/*
	 * Public constructors of UserModel
	 */
	public UserModel() { } 
	
		// A Constructor used for the registration
	public UserModel(String firstName, String lastName, String username, 
			String password, String email, String address, String iban, 
			String bicSwift, String picture, int id) {
		mFirstName = firstName;
		mLastName = lastName;
		mUsername = username;
		mPassword = password;
		mEmail = email;
		mAddress = address;
		mIban = iban;
		mBicSwift = bicSwift;
		mPicture = picture;
		mId = id;
	}
	
		//A constructor used for representing a User in all other situations 
	public UserModel(String firstName, String lastName, String username, 
			String email, String address, String iban, String bicSwift, 
			String picture, int id) {
		mFirstName = firstName;
		mLastName = lastName;
		mUsername = username;
		mEmail = email;
		mAddress = address;
		mIban = iban;
		mBicSwift = bicSwift;
		mPicture = picture;
		mId = id;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public String getLastName() {
		return mLastName;
	}
	
	public String getUsername() {
		return mUsername;
	}
	
	public String getPassword() {
		return mPassword;
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

	public String getBicSwift() {
		return mBicSwift;
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
	
	public void setUsername(String username) {
		mUsername = username;
	}
	
	public void setPassword(String password) {
		mPassword = password;
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
	public void setBicSwift(String bicSwift) {
		mBicSwift = bicSwift;
	}
	public void setPicture(String picture) {
		mPicture = picture;
	}
	public void setId(int id) {
		mId = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		return mId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			
			return true;
		}
		if (obj == null) {
			
			return false;
		}
		if (getClass() != obj.getClass()) {
			
			return false;
		}
		UserModel other = (UserModel) obj;
		if (mAddress == null) {
			if (other.mAddress != null) {
				
				return false;
			}
		} else if (!mAddress.equals(other.mAddress)) {
			
			return false;
		}
		if (mEmail == null) {
			if (other.mEmail != null) {
				
				return false;
			}
		} else if (!mEmail.equals(other.mEmail)) {
			
			return false;
		}
		if (mFirstName == null) {
			if (other.mFirstName != null) {
				
				return false;
			}
		} else if (!mFirstName.equals(other.mFirstName)) {
			
			return false;
		}
		if (mIban == null) {
			if (other.mIban != null) {
				
				return false;
			}
		} else if (!mIban.equals(other.mIban)) {
			
			return false;
		}
		if (mId != other.mId) {
			
			return false;
		}
		if (mLastName == null) {
			if (other.mLastName != null) {
				
				return false;
			}
		} else if (!mLastName.equals(other.mLastName)) {
			
			return false;
		}
		if (mPicture == null) {
			if (other.mPicture != null) {
				
				return false;
			}
		} else if (!mPicture.equals(other.mPicture)) {
			
			return false;
		}
		return true;
	}


}
