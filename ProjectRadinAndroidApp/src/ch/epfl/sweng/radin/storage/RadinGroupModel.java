package ch.epfl.sweng.radin.storage;

import java.sql.Types;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.databases.RadinGroupTableHelper;

import android.content.ContentValues;

/**
 * @author Simonchelbc RadinGroupModel is an object with only immutable fields
 *         which provides a representation of the design of our RadinGroupModel
 */
public class RadinGroupModel extends Model {
	/**
	 * @author Simonchelbc
	 * Each instance represent all non mandatory fields states in table of radin-group Database
	 */
	public enum TypeOfRadinGroup {
		WITH_MASTER_ID, WITHOUT_MASTER_ID, WITH_DESCRIPTION, 
		WITHOUT_DESCRIPTION, ENDED, NOT_ENDED, DELETED, NOT_DELETED, WITH_AVATAR, WITHOUT_AVATAR;

		public TypeOfRadinGroup opposite() {
			switch (this) {
				case WITH_MASTER_ID:
					return WITHOUT_MASTER_ID;
				case WITHOUT_MASTER_ID:
					return WITH_MASTER_ID;
				case WITH_DESCRIPTION:
					return WITHOUT_DESCRIPTION;
				case WITHOUT_DESCRIPTION:
					return WITH_DESCRIPTION;
				case ENDED:
					return NOT_ENDED;
				case NOT_ENDED:
					return ENDED;
				case DELETED:
					return NOT_DELETED;
				case NOT_DELETED:
					return DELETED;
				case WITH_AVATAR:
					return WITHOUT_AVATAR;
				case WITHOUT_AVATAR:
					return WITH_AVATAR;
				default:
					throw new IllegalStateException("This should never happen"
							+ this + "has no opposite");
			}
		}
	}

	private final int mRID;
	private final DateTime mCreationDateTime;

	private DateTime mEndDateTime;
	private DateTime mDeletionDateTime;

	private String mName;
	private String mDescription;
	private String mAvatar;
	// private String mRgGroup; // TODO #rgGroup

	// Set if this group is contained in another RadinGroup which ID we will set
	// as this.mMasterID
	private int mMasterID;
	private Set<TypeOfRadinGroup> mTypes;

	/**
	 * @param radinGroupID
	 * @param groupCreationDateTime
	 * @param radinGroupName
	 * @param groupDescription
	 * @param avatar
	 */
	public RadinGroupModel(int radinGroupID, DateTime groupCreationDateTime,
			String radinGroupName, String groupDescription, String avatar) {
		mTypes = new HashSet<TypeOfRadinGroup>();
		
		checkArgumentPositive("radinGroupID", radinGroupID);
		mRID = radinGroupID;

		checkArgumentNotNull("groupCreationDateTime", groupCreationDateTime);
		mCreationDateTime = groupCreationDateTime;

		checkString("radinGroupName", radinGroupName);
		mName = radinGroupName;

		if (groupDescription == null) {
			mTypes.add(TypeOfRadinGroup.WITHOUT_DESCRIPTION);
		} else {
			mTypes.add(TypeOfRadinGroup.WITH_DESCRIPTION);
		}
		mDescription = groupDescription;

		if (avatar == null) {
			mTypes.add(TypeOfRadinGroup.WITHOUT_AVATAR);
		} else {
			mTypes.add(TypeOfRadinGroup.WITH_AVATAR);
		}
		mAvatar = avatar;

		mTypes.add(TypeOfRadinGroup.WITHOUT_MASTER_ID);

	}

	/**
	 * @param radinGroupID
	 * @param groupCreationDateTime
	 * @param radinGroupName
	 * @param groupDescription
	 * @param avatar
	 * @param masterID
	 */
	public RadinGroupModel(int radinGroupID, DateTime groupCreationDateTime,
			String radinGroupName, String groupDescription, String avatar,
			int masterID) {
		this(radinGroupID, groupCreationDateTime, radinGroupName,
				groupDescription, avatar);
		checkArgumentPositive("masterID", masterID); // masterID can't be
		// undefined if calling
		// this constructor

		addThatAndRemoveOpposite(TypeOfRadinGroup.WITH_MASTER_ID, mTypes);
		mMasterID = masterID;
	}
	
	
	/**
	 * @param t the {@link TypeOfRadinGroup} we want to add to the set, and which opposite 
	 * we want to remove from the {@link Set} if the {@link Set} contains it
	 * @param types {@link Set} on which we operate
	 */
	private void addThatAndRemoveOpposite(TypeOfRadinGroup t, Set<TypeOfRadinGroup> types) {
		TypeOfRadinGroup oppositeOfT = t.opposite();
		if (types.contains(oppositeOfT)) {
			types.remove(oppositeOfT);
		}
		types.add(t);
	}

	// TODO add getContentValues to Model interface
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put(RadinGroupTableHelper.Column.RID.getSqlName(), mRID);
		values.put(RadinGroupTableHelper.Column.RG_NAME.getSqlName(), mName);
		values.put(RadinGroupTableHelper.Column.RG_CREATION_DATE.getSqlName(),
				mCreationDateTime.toString()); // TODO discuss format of dates
		// in db!
		if (mAvatar != null) {
			values.put(RadinGroupTableHelper.Column.RG_AVATAR.getSqlName(),
					mAvatar);
		}
		if (mDeletionDateTime != null) {
			values.put(RadinGroupTableHelper.Column.RG_DELETED_AT.getSqlName(),
					mDeletionDateTime.toString()); // TODO discuss format of
			// dates in db!
		}
		if (mEndDateTime != null) {
			values.put(RadinGroupTableHelper.Column.RG_END_DATE.getSqlName(),
					mEndDateTime.toString());
		}
		if (mDescription != null) {
			values.put(
					RadinGroupTableHelper.Column.RG_DESCRIPTION.getSqlName(),
					mDescription);
		}
		// values.put(RadinGroupTableHelper.Column.RG_GROUP.getSqlName(),
		// mRgGroup); // TODO #rgGroup

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

	public void setRadinGroupDeletionDateTime(
			DateTime radinGroupDeletionDateTime) {
		checkArgumentNotNull("radinGroupDeletionDateTime",
				radinGroupDeletionDateTime);
		checkFirstHappensBeforeSecond(mCreationDateTime,
				radinGroupDeletionDateTime);
		mDeletionDateTime = radinGroupDeletionDateTime;
	}

	private void checkFirstHappensBeforeSecond(DateTime firstEvent,
			DateTime secondEvent) {
		if (firstEvent.isAfter(secondEvent)) {
			throw new IllegalArgumentException(
					"cannot be set as wanted since first event must happen before");
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

	/**
	 * @param groupDescription cannot be {@code null} but can be empty String in such cases it makes sense
	 * i.e: user changes his mind and still want no description but then result cannot be empty
	 */
	public void setGroupDescription(String groupDescription) {
		checkArgumentNotNull("groupDescription", groupDescription);
		mDescription = groupDescription;
	} //TODO is this right way or should someone take care of this empty string case higher?

	public String getAvatar() {
		return mAvatar;
	}

	public void setAvatar(String avatar) {
		checkArgumentNotNull("avatar", avatar);
		mAvatar = avatar;
	}

	public boolean hasMasterID() {
		return mTypes.contains(TypeOfRadinGroup.WITH_MASTER_ID);
	}
	

	public Set<TypeOfRadinGroup> getTypes() {
		return mTypes;
	}

	public int getMasterID() {
		return mMasterID;
	}

	public void setMasterID(int masterID) {
		checkArgumentPositive("masterID", masterID);
		mMasterID = masterID;
		addThatAndRemoveOpposite(TypeOfRadinGroup.WITH_MASTER_ID, mTypes);
	}

	public int getRadinGroupID() {
		return mRID;
	}

	public DateTime getGroupCreationDateTime() {
		return mCreationDateTime;
	}
}
