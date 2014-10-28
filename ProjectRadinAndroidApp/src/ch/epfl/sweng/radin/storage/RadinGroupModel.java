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
			String radinGroupName, String groupDescription, int masterID,
			String avatar) {
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

	public DateTime getmRadinGroupEndDateTime() {
		return mRadinGroupEndDateTime;
	}

	public void setmRadinGroupEndDateTime(DateTime radinGroupEndDateTime) {
		checkArgumentNotNull("radinGroupEndDateTime", radinGroupEndDateTime);
		mRadinGroupEndDateTime = radinGroupEndDateTime;
	}

	public DateTime getmRadinGroupDeletionDateTime() {
		return mRadinGroupDeletionDateTime;
	}

	public void setmRadinGroupDeletionDateTime(DateTime radinGroupDeletionDateTime) {
		checkArgumentNotNull("radinGroupDeletionDateTime", radinGroupDeletionDateTime);
		mRadinGroupDeletionDateTime = radinGroupDeletionDateTime;
	}

	public String getmRadinGroupName() {
		return mRadinGroupName;
	}

	public void setmRadinGroupName(String radinGroupName) {
		checkString("radinGroupName", radinGroupName);
		mRadinGroupName = radinGroupName;
	}

	public String getmGroupDescription() {
		return mGroupDescription;
	}

	public void setmGroupDescription(String groupDescription) {
		checkString("groupDescription", groupDescription);
		mGroupDescription = groupDescription;
	}

	public String getmAvatar() {
		return mAvatar;
	}

	public void setmAvatar(String avatar) {
		checkString("avatar", avatar);
		mAvatar = avatar;
	}

	public int getmMasterID() {
		return mMasterID;
	}

	public void setmMasterID(int masterID) {
		checkArgumentPositive("masterID", masterID); 
		mMasterID = masterID;
	}

	public int getmRadinGroupID() {
		return mRadinGroupID;
	}

	public DateTime getmGroupCreationDateTime() {
		return mGroupCreationDateTime;
	}
}
