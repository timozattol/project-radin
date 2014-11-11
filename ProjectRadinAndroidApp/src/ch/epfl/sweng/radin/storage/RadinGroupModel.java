package ch.epfl.sweng.radin.storage;

import org.joda.time.DateTime;

/**
 * @author Simonchelbc
 * RadinGroupModel is an object with only immutable fields which provides a representation of
 * the design of our RadinGroupModel
 */
public class RadinGroupModel extends Model{
	private final int mRadinGroupID;
	private final DateTime mGroupCreationDateTime;

	private DateTime mRadinGroupEndDateTime;
	private DateTime mRadinGroupDeletionDateTime;
	
	private String mRadinGroupName;
	private String mGroupDescription;
	private String mAvatar;
	
	// Set if this group is contained in another RadinGroup which ID we will set as this.mMasterID
	private int mMasterID;
	private boolean mHasMasterID;
	

	public RadinGroupModel(int radinGroupID, DateTime groupCreationDateTime,
			String radinGroupName, String groupDescription, String avatar) {
		checkArgumentPositive("radinGroupID", radinGroupID);
		mRadinGroupID = radinGroupID;

		checkArgumentNotNull("groupCreationDateTime", groupCreationDateTime);
		mGroupCreationDateTime = groupCreationDateTime;

		checkString("radinGroupName", radinGroupName);
		mRadinGroupName = radinGroupName;

		checkString("groupDescription", groupDescription);
		mGroupDescription = groupDescription;
		
		checkString("avatar", avatar);
		mAvatar = avatar;
		
		mHasMasterID = false;
	}

	public RadinGroupModel(int radinGroupID, DateTime groupCreationDateTime,
			String radinGroupName, String groupDescription,
			String avatar, int masterID) {
		this(radinGroupID, groupCreationDateTime, radinGroupName,
				groupDescription, avatar);
		checkArgumentPositive("masterID", masterID);
		mMasterID = masterID;
		mHasMasterID = true;
	}

	private void checkArgumentPositive(String argName, double arg) {
		if (arg < 0) {
			throw new IllegalArgumentException(argName + " cannot be negative");
		}
	}

	private void checkArgumentNotNull(String argName, Object arg) {
		if (arg == null) {
			throw new IllegalArgumentException(argName + " cannot be null");
		}
	}

	private void checkEmptyStringArgument(String argName, String string) {
		if (string.equals("")) {
			throw new IllegalArgumentException(argName
					+ " cannot be the empty String");
		}
	}
	
	private void checkString(String argName, String string) {
		checkArgumentNotNull(argName, string);
		checkEmptyStringArgument(argName, string);
	}

	public DateTime getRadinGroupEndDateTime() {
		return mRadinGroupEndDateTime;
	}

	public void setRadinGroupEndDateTime(DateTime radinGroupEndDateTime) {
		checkArgumentNotNull("radinGroupEndDateTime", radinGroupEndDateTime);
		checkFirstHappensBeforeSecond(mGroupCreationDateTime, radinGroupEndDateTime);
		mRadinGroupEndDateTime = radinGroupEndDateTime;
	}

	public DateTime getRadinGroupDeletionDateTime() {
		return mRadinGroupDeletionDateTime;
	}

	public void setRadinGroupDeletionDateTime(DateTime radinGroupDeletionDateTime) {
		checkArgumentNotNull("radinGroupDeletionDateTime", radinGroupDeletionDateTime);
		checkFirstHappensBeforeSecond(mGroupCreationDateTime, radinGroupDeletionDateTime);
		mRadinGroupDeletionDateTime = radinGroupDeletionDateTime;
	}
    
	private void checkFirstHappensBeforeSecond(DateTime firstEvent, DateTime secondEvent) {
	    if (firstEvent.isAfter(secondEvent)) {
	        throw new IllegalArgumentException("cannot be set as wanted since first event must happen before");
	    }
	}
	
	public String getRadinGroupName() {
		return mRadinGroupName;
	}

	public void setRadinGroupName(String radinGroupName) {
		checkString("radinGroupName", radinGroupName);
		mRadinGroupName = radinGroupName;
	}

	public String getGroupDescription() {
		return mGroupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		checkString("groupDescription", groupDescription);
		mGroupDescription = groupDescription;
	}

	public String getAvatar() {
		return mAvatar;
	}

	public void setAvatar(String avatar) {
		checkString("avatar", avatar);
		mAvatar = avatar;
	}
	
	public boolean hasMasterID() {
	    return mHasMasterID;
	}

	public int getMasterID() {
		return mMasterID;
	}

	public void setMasterID(int masterID) {
		checkArgumentPositive("masterID", masterID); 
		mMasterID = masterID;
		mHasMasterID = true;
	}

	public int getRadinGroupID() {
		return mRadinGroupID;
	}

	public DateTime getGroupCreationDateTime() {
		return mGroupCreationDateTime;
	}
}
