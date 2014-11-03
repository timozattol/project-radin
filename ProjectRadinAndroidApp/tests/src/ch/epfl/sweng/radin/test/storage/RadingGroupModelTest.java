package ch.epfl.sweng.radin.test.storage;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.storage.RadinGroupModel;
import android.test.AndroidTestCase;

public class RadingGroupModelTest extends AndroidTestCase {
	private static final int DEFAULT_RADIN_GROUP_ID = 0;
	private static final int DEFAULT_MASTER_ID = 10;
	private static final String DEFAULT_AVATAR = "<path-to-default-avatar>";
	private static final String DEFAULT_GROUP_NAME = "SuperRadins";
	private static final String DEFAULT_DESCRIPTION = "Throw your debts away";
	private static final Object NULL_OBJECT = null;
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

	public void testWithoutMasterConstructorWithCreationTimeNull() {

		try {
			new RadinGroupModel(DEFAULT_RADIN_GROUP_ID, null,
					DEFAULT_GROUP_NAME, DEFAULT_DESCRIPTION, DEFAULT_AVATAR);
			fail("groupCreationDateTime cannot be null");
		} catch (IllegalArgumentException e) {
			// Good
		}
	}

	public void testWithoutMasterConstructorWithRadinGroupNameNull() {
		try {
			new RadinGroupModel(DEFAULT_RADIN_GROUP_ID, RIGHT_NOW, null,
					DEFAULT_DESCRIPTION, DEFAULT_AVATAR);
			fail("radinGroupName cannot be null");
		} catch (IllegalArgumentException e) {
			// Good
		}
	}

	public void testConstructorWithMasterNullFields() throws Exception {

	}

	public void testGettersAndSettersGroupWithoutMaster() throws Exception {
	}
}
