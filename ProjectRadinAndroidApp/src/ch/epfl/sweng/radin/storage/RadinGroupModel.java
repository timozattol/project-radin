package ch.epfl.sweng.radin.storage;

import org.joda.time.DateTime;

/**
 * @author Simonchelbc
 * 
 */
public class RadinGroupModel {
	private final int mRadinGroupID;
	private final DateTime mGroupCreationDateTime;

	private DateTime mRadinGroupEndDateTime;
	private DateTime mRadinGroupDeletionDateTime;
	
	private String mRadinGroupName;
	private String mGroupDescription;
	private String mAvatar;
	
	// Set if this group is contained in another RadinGroup
	private int mMasterID;
	

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
	}

	public RadinGroupModel(int radinGroupID, DateTime groupCreationDateTime,
			String radinGroupName, String groupDescription,
			String avatar, int masterID) {
		this(radinGroupID, groupCreationDateTime, radinGroupName,
				groupDescription, avatar);
		checkArgumentPositive("masterID", masterID);
		mMasterID = masterID;
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
		mRadinGroupEndDateTime = radinGroupEndDateTime;
	}

	public DateTime getRadinGroupDeletionDateTime() {
		return mRadinGroupDeletionDateTime;
	}

	public void setRadinGroupDeletionDateTime(DateTime radinGroupDeletionDateTime) {
		checkArgumentNotNull("radinGroupDeletionDateTime", radinGroupDeletionDateTime);
		mRadinGroupDeletionDateTime = radinGroupDeletionDateTime;
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

	public int getMasterID() {
		return mMasterID;
	}

	public void setMasterID(int masterID) {
		checkArgumentPositive("masterID", masterID); 
		mMasterID = masterID;
	}

	public int getRadinGroupID() {
		return mRadinGroupID;
	}

	public DateTime getGroupCreationDateTime() {
		return mGroupCreationDateTime;
	}
}
