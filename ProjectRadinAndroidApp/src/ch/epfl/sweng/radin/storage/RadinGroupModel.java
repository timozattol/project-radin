package ch.epfl.sweng.radin.storage;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.databases.RadinGroupTableHelper;

import android.content.ContentValues;

/**
 * @author Simonchelbc
 * RadinGroupModel is an object with only immutable fields which provides a representation of
 * the design of our RadinGroupModel
 */
public class RadinGroupModel extends Model{
	/**
	 * @author Simonchelbc
	 */
	public enum TypeOfRadinGroup {
		WITH_MASTER_ID, WITHOUT_MASTER_ID
	}
	private final int mRID;
	private final DateTime mCreationDateTime;

	private DateTime mEndDateTime;
	private DateTime mDeletionDateTime;
	
	private String mName;
	private String mDescription;
	private String mAvatar;
	private String mRgGroup;
	
	// Set if this group is contained in another RadinGroup which ID we will set as this.mMasterID
	private int mMasterID;
	private TypeOfRadinGroup mType;
	

	public RadinGroupModel(int radinGroupID, DateTime groupCreationDateTime,
			String radinGroupName, String groupDescription, String avatar) {
		checkArgumentPositive("radinGroupID", radinGroupID);
		mRID = radinGroupID;

		checkArgumentNotNull("groupCreationDateTime", groupCreationDateTime);
		mCreationDateTime = groupCreationDateTime;

		checkString("radinGroupName", radinGroupName);
		mName = radinGroupName;

		checkString("groupDescription", groupDescription);
		mDescription = groupDescription;
		
		checkString("avatar", avatar);
		mAvatar = avatar;
		
		mType = TypeOfRadinGroup.WITHOUT_MASTER_ID;
		
	}

	public RadinGroupModel(int radinGroupID, DateTime groupCreationDateTime,
			String radinGroupName, String groupDescription,
			String avatar, int masterID) {
		this(radinGroupID, groupCreationDateTime, radinGroupName,
				groupDescription, avatar);
		checkArgumentPositive("masterID", masterID);
		mMasterID = masterID;
		mType = TypeOfRadinGroup.WITH_MASTER_ID;
	}
	//TODO add getContentValues to Model interface
	public ContentValues getContentValues() {
		ContentValues values= new ContentValues();
		values.put(RadinGroupTableHelper.Column.RID.getSqlName(),
				mRID);
		values.put(RadinGroupTableHelper.Column.RG_AVATAR.getSqlName(),
				mAvatar);
		values.put(RadinGroupTableHelper.Column.RG_CREATION_DATE.getSqlName(), 
				mCreationDateTime.toString()); //TODO discuss format of dates in db!
		values.put(RadinGroupTableHelper.Column.RG_DELETED_AT.getSqlName(), 
				mDeletionDateTime.toString()); //TODO discuss format of dates in db!
		values.put(RadinGroupTableHelper.Column.RG_END_DATE.getSqlName(), 
				mEndDateTime.toString());
		values.put(RadinGroupTableHelper.Column.RG_DESCRIPTION.getSqlName(), 
				mDescription);
		values.put(RadinGroupTableHelper.Column.RG_GROUP.getSqlName(),
				mRgGroup);
		values.put(RadinGroupTableHelper.Column.RG_NAME.getSqlName(), 
				mName);
		
		if (hasMasterID()) {
			values.put(RadinGroupTableHelper.Column.RG_MASTER_RID.getSqlName(), 
					mMasterID);			
		}
		return values;
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
		return mEndDateTime;
	}

	public void setRadinGroupEndDateTime(DateTime radinGroupEndDateTime) {
		checkArgumentNotNull("radinGroupEndDateTime", radinGroupEndDateTime);
		checkFirstHappensBeforeSecond(mCreationDateTime, radinGroupEndDateTime);
		mEndDateTime = radinGroupEndDateTime;
	}

	public DateTime getRadinGroupDeletionDateTime() {
		return mDeletionDateTime;
	}

	public void setRadinGroupDeletionDateTime(DateTime radinGroupDeletionDateTime) {
		checkArgumentNotNull("radinGroupDeletionDateTime", radinGroupDeletionDateTime);
		checkFirstHappensBeforeSecond(mCreationDateTime, radinGroupDeletionDateTime);
		mDeletionDateTime = radinGroupDeletionDateTime;
	}
    
	private void checkFirstHappensBeforeSecond(DateTime firstEvent, DateTime secondEvent) {
	    if (firstEvent.isAfter(secondEvent)) {
	        throw new IllegalArgumentException("cannot be set as wanted since first event must happen before");
	    }
	}
	
	public String getRadinGroupName() {
		return mName;
	}

	public void setRadinGroupName(String radinGroupName) {
		checkString("radinGroupName", radinGroupName);
		mName = radinGroupName;
	}

	public String getGroupDescription() {
		return mDescription;
	}

	public void setGroupDescription(String groupDescription) {
		checkString("groupDescription", groupDescription);
		mDescription = groupDescription;
	}

	public String getAvatar() {
		return mAvatar;
	}

	public void setAvatar(String avatar) {
		checkString("avatar", avatar);
		mAvatar = avatar;
	}
	
	
	public boolean hasMasterID() {
	    return mType == TypeOfRadinGroup.WITH_MASTER_ID;
	}
	
	public TypeOfRadinGroup getType() {
		return mType;
	}

	public int getMasterID() {
		return mMasterID;
	}

	public void setMasterID(int masterID) {
		checkArgumentPositive("masterID", masterID); 
		mMasterID = masterID;
		mType = TypeOfRadinGroup.WITH_MASTER_ID;
	}

	public int getRadinGroupID() {
		return mRID;
	}

	public DateTime getGroupCreationDateTime() {
		return mCreationDateTime;
	}
}
