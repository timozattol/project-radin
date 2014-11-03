package ch.epfl.sweng.radin.test.storage;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.storage.RadinGroupModel;
import android.test.AndroidTestCase;

/**
 * @author Simonchelbc
 * 
 */
public class RadingGroupModelTest extends AndroidTestCase {
	private static final int DEFAULT_RADIN_GROUP_ID = 0;
	private static final int NEGATIVE_VALUE = -1;
	private static final int DEFAULT_MASTER_ID = 10;
	private static final String DEFAULT_AVATAR = "<path-to-default-avatar>";
	private static final String DEFAULT_GROUP_NAME = "SuperRadins";
	private static final String DEFAULT_DESCRIPTION = "Throw your debts away";
	private static final DateTime RIGHT_NOW = DateTime.now();
	private static final DateTime TOMORROW = RIGHT_NOW.plusDays(1);

	private RadinGroupModel radinGroupWithoutMaster;
	private RadinGroupModel radinGroupWithMaster;

	// TODO replace by a default avatar when avatar will be defined

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		radinGroupWithoutMaster = new RadinGroupModel(DEFAULT_RADIN_GROUP_ID,
				RIGHT_NOW, DEFAULT_GROUP_NAME, DEFAULT_DESCRIPTION,
				DEFAULT_AVATAR);
		radinGroupWithMaster = new RadinGroupModel(DEFAULT_RADIN_GROUP_ID,
				RIGHT_NOW, DEFAULT_GROUP_NAME, DEFAULT_DESCRIPTION,
				DEFAULT_AVATAR, DEFAULT_MASTER_ID);
	}

	public void testWithoutMasterIDConstructorNullFields() {
		try {
			new RadinGroupModel(DEFAULT_RADIN_GROUP_ID, null, null, null, null);
			fail("groupCreationDateTime cannot be null");
			fail("radinGroupName cannot be null");
			fail("groupDescription cannot be null");
			fail("avatar cannot be null");
		} catch (IllegalArgumentException e) {
			// Good
		}
	}

	public void testWithoutMasterIDConstructorNegativeID() {
		try {
			new RadinGroupModel(NEGATIVE_VALUE, RIGHT_NOW, DEFAULT_GROUP_NAME,
					DEFAULT_DESCRIPTION, DEFAULT_AVATAR);
			fail("radinGroupID cannot be negative");
		} catch (IllegalArgumentException e) {
			// Good
		}
	}

	public void testWithMasterIDConstructorNullFields() {
		try {
			new RadinGroupModel(DEFAULT_RADIN_GROUP_ID, null, null, null, null,
					DEFAULT_MASTER_ID);
			fail("groupCreationDateTime cannot be null");
			fail("radinGroupName cannot be null");
			fail("groupDescription cannot be null");
			fail("avatar cannot be null");
		} catch (IllegalArgumentException e) {
			// good
		}
	}

	public void testWithMasterIDConstructorNegativeIDs() {
		try {
			new RadinGroupModel(NEGATIVE_VALUE, RIGHT_NOW, DEFAULT_GROUP_NAME,
					DEFAULT_DESCRIPTION, DEFAULT_AVATAR, NEGATIVE_VALUE);
			fail("radinGroupID cannot be negative");
			fail("masterID cannot be negative");
		} catch (IllegalArgumentException e) {
			// good
		}
	}

	public void testConstructorsWithEmptyStringFields() {
		try {
			new RadinGroupModel(DEFAULT_RADIN_GROUP_ID, RIGHT_NOW, "", "", "");
			fail("radinGroupName cannot be the empty String");
			fail("groupDescription cannot be the empty String");
			fail("avatar cannot be the empty String");
			new RadinGroupModel(DEFAULT_RADIN_GROUP_ID, RIGHT_NOW, "", "", "", DEFAULT_MASTER_ID);
			fail("radinGroupName cannot be the empty String");
			fail("groupDescription cannot be the empty String");
			fail("avatar cannot be the empty String");
		} catch (IllegalArgumentException e) {
			// good
		}
	}
}
