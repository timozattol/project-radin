package ch.epfl.sweng.radin.test.storage;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.storage.RadinGroupModel;
import android.test.AndroidTestCase;

/**
 * @author Simonchelbc
 * 
 */
public class RadingGroupModelTest extends AndroidTestCase {
    private static final int NEGATIVE_VALUE = -1;

    private static final int DEFAULT_RADIN_GROUP_ID = 0;
    private static final int DEFAULT_MASTER_ID = 10;
    private static final String DEFAULT_AVATAR = "<path-to-default-avatar>"; // TODO
    // change
    // when
    // we
    // have
    // avatars
    private static final String DEFAULT_GROUP_NAME = "SuperRadins";
    private static final String DEFAULT_DESCRIPTION = "Throw your debts away";

    private static final int NEW_MASTER_ID = 11;
    private static final String NEW_AVATAR = "<path-to-new-avatar>"; // TODO
    // change
    // when we
    // have
    // avatars
    private static final String NEW_GROUP_NAME = "VeryRadins";
    private static final String NEW_DESCRIPTION = "Throw the debters away";
    private static final DateTime RIGHT_NOW = DateTime.now();
    private static final DateTime YESTERDAY = RIGHT_NOW.minusDays(1);
    private static final DateTime TOMORROW = RIGHT_NOW.plusDays(1);
    private static final DateTime AFTER_TOMORROW = RIGHT_NOW.plusDays(2);

    private RadinGroupModel radinGroupWithoutMaster;
    private RadinGroupModel radinGroupWithMaster;

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
    
    @Override
    protected void tearDown() throws Exception {
    	// TODO Auto-generated method stub
    	super.tearDown();
    }

    public void testMasterID() throws Exception {
        assertTrue(radinGroupWithMaster.hasMasterID());
        assertEquals(RadinGroupModel.TypeOfRadinGroup.WITH_MASTER_ID, radinGroupWithMaster.getType());
        assertFalse(radinGroupWithoutMaster.hasMasterID());
        assertEquals(RadinGroupModel.TypeOfRadinGroup.WITHOUT_MASTER_ID, radinGroupWithoutMaster.getType());
    }

    public void testWithoutMasterIDConstructorNullFields() {
        try {
            new RadinGroupModel(DEFAULT_RADIN_GROUP_ID, null, null, null, null);
            fail("groupCreationDateTime cannot be null");
            fail("radinGroupName cannot be null");
            fail("groupDescription cannot be null");
            fail("avatar cannot be null");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    public void testWithoutMasterIDConstructorNegativeID() {
        try {
            new RadinGroupModel(NEGATIVE_VALUE, RIGHT_NOW, DEFAULT_GROUP_NAME,
                    DEFAULT_DESCRIPTION, DEFAULT_AVATAR);
            fail("radinGroupID cannot be negative");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
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
            assertNotNull(e);
        }
    }

    public void testWithMasterIDConstructorNegativeIDs() {
        try {
            new RadinGroupModel(NEGATIVE_VALUE, RIGHT_NOW, DEFAULT_GROUP_NAME,
                    DEFAULT_DESCRIPTION, DEFAULT_AVATAR, NEGATIVE_VALUE);
            fail("radinGroupID cannot be negative");
            fail("masterID cannot be negative");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    public void testConstructorsWithEmptyStringFields() {
        try {
            new RadinGroupModel(DEFAULT_RADIN_GROUP_ID, RIGHT_NOW, "", "", "");
            fail("radinGroupName cannot be the empty String");
            fail("groupDescription cannot be the empty String");
            fail("avatar cannot be the empty String");
            new RadinGroupModel(DEFAULT_RADIN_GROUP_ID, RIGHT_NOW, "", "", "",
                    DEFAULT_MASTER_ID);
            fail("radinGroupName cannot be the empty String");
            fail("groupDescription cannot be the empty String");
            fail("avatar cannot be the empty String");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    public void testModifyingValuesWithSettersWithMasterID() {
        radinGroupWithMaster.setAvatar(NEW_AVATAR);
        assertEquals(radinGroupWithMaster.getAvatar(),NEW_AVATAR);

        radinGroupWithMaster.setGroupDescription(NEW_DESCRIPTION);
        assertEquals(radinGroupWithMaster.getGroupDescription(),
                NEW_DESCRIPTION);

        radinGroupWithMaster.setMasterID(NEW_MASTER_ID);
        assertEquals(radinGroupWithMaster.getMasterID(), NEW_MASTER_ID);
        assertTrue(radinGroupWithMaster.hasMasterID());
        assertEquals(RadinGroupModel.TypeOfRadinGroup.WITH_MASTER_ID, radinGroupWithMaster.getType());

        radinGroupWithMaster.setRadinGroupEndDateTime(TOMORROW);
        assertEquals(radinGroupWithMaster.getRadinGroupEndDateTime(),
                TOMORROW);

        radinGroupWithMaster.setRadinGroupDeletionDateTime(AFTER_TOMORROW);
        assertEquals(radinGroupWithMaster.getRadinGroupDeletionDateTime(),
                AFTER_TOMORROW);

        radinGroupWithMaster.setRadinGroupName(NEW_GROUP_NAME);
        assertEquals(radinGroupWithMaster.getRadinGroupName(),
                NEW_GROUP_NAME);

    }

    public void testSetMasterIDToGroupWithoutMasterID() throws Exception {
        radinGroupWithoutMaster.setMasterID(NEW_MASTER_ID);
        assertTrue(radinGroupWithoutMaster.hasMasterID());
        assertEquals(RadinGroupModel.TypeOfRadinGroup.WITH_MASTER_ID, radinGroupWithoutMaster.getType());
        assertEquals(radinGroupWithoutMaster.getMasterID(), NEW_MASTER_ID);
    }

    public void testSetDeletionTimeHappeningBeforeCreationTime() {
        try {
            radinGroupWithMaster.setRadinGroupDeletionDateTime(YESTERDAY);
            fail("cannot be set as wanted since first event must happen before");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    public void testSetGroupEndDateTimeHappeningBeforeCreationTime() {
        try {
            radinGroupWithMaster.setRadinGroupEndDateTime(YESTERDAY);
            fail("cannot be set as wanted since first event must happen before");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }
}
