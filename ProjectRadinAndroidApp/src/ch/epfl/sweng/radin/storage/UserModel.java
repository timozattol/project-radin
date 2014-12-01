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
