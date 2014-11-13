package ch.epfl.sweng.radin.databases;

import ch.epfl.sweng.radin.storage.RadinGroupModel;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Simonchelbc Used to create the table associated to Radin Group of our
 *         database
 */
public final class RadinGroupTableHelper implements TableHelper {
	/**
	 * @author Simonchelbc
	 *
	 */
	public enum Column {// TODO problem letting it public might
		// be bad for privacy but we need it
		// for test.databases so far
		RID("_RID"), RG_NAME("RG_NAME"), RG_CREATION_DATE("RG_CREATION_DATE"), RG_DESCRIPTION(
				"RG_DESCRIPTION"),  RG_MASTER_RID(
				"RG_MASTER_RID"), RG_AVATAR("RG_AVATAR"), RG_DELETED_AT(
				"RG_DELETED_AT"), RG_END_DATE("RG_END_DATE"); //RG_GROUP("RG_GROUP"), TODO #radinGroup

		private final String getSqlName; 

		private Column(String sqlName) {
			getSqlName = sqlName;
		}
		
		public String getSqlName() {
			return getSqlName;
		}
	}

	public static final String TABLE_RADIN_GROUP = "RADIN_GROUP";

	private static final String CREATE_TABLE_RADIN_GROUP = "CREATE TABLE "
			+ TABLE_RADIN_GROUP + "(" + Column.RID.getSqlName + " INT NOT NULL,"
			+ Column.RG_NAME.getSqlName + " TEXT NOT NULL,"
			+ Column.RG_CREATION_DATE.getSqlName + " TEXT NOT NULL,"
			+ Column.RG_DESCRIPTION.getSqlName + " TEXT,"
//			+ Column.RG_GROUP.getSqlName + " TEXT," //TODO #rgGroup
			+ Column.RG_MASTER_RID.getSqlName + "," + Column.RG_AVATAR.getSqlName
			+ " TEXT," + Column.RG_END_DATE.getSqlName + " TEXT,"
			+ Column.RG_DELETED_AT.getSqlName + " TEXT," + "PRIMARY KEY ("
			+ Column.RID.getSqlName + "));";

//	private final String[] mNames = new String[Column.values().length]; //TODO possibility to make it look like a 
	//lazy val?

	/**
	 * @return array of all columns names as {@link String}, thus an array of
	 *         {@code mNames}
	 */
	public synchronized static String[] names() {
		final Column[] values = Column.values();
		final int enumSize = values.length;
		final String[] names = new String[enumSize];
		for (int i = 0; i < enumSize; i++) {
			names[i] = values[i].getSqlName;
		}
		return names;
	}


	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_RADIN_GROUP);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO if we need to get previous data from old database
		// and put them in a new Database schema
	}

}
