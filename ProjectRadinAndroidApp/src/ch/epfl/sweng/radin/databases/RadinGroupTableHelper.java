package ch.epfl.sweng.radin.databases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
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
				"RG_DESCRIPTION"), RG_MASTER_RID("RG_MASTER_RID"), RG_AVATAR(
				"RG_AVATAR"), RG_DELETED_AT("RG_DELETED_AT"), RG_END_DATE(
				"RG_END_DATE"); // RG_GROUP("RG_GROUP"), TODO #radinGroup

		private final String mInString;

		private Column(String sqlName) {
			mInString = sqlName;
		}
		@Override
		public String toString() {
			return mInString;
		}
	}

	public static final String TABLE_RADIN_GROUP = "RADIN_GROUP";

	RadinGroupTableHelper() {
	}

	private static final String TEXT = " TEXT,";
	private static final String TEXT_NOT_NULL = " TEXT NOT NULL,";
	private static final String CREATE_TABLE_RADIN_GROUP = "CREATE TABLE "
			+ TABLE_RADIN_GROUP + "(" + Column.RID.mInString
			+ " INT NOT NULL," + Column.RG_NAME.mInString + TEXT_NOT_NULL
			+ Column.RG_CREATION_DATE.mInString + TEXT_NOT_NULL
			+ Column.RG_DESCRIPTION.mInString + TEXT
			// + Column.RG_GROUP.getSqlName + " TEXT," //TODO #rgGroup
			+ Column.RG_MASTER_RID.mInString + ","
			+ Column.RG_AVATAR.mInString + TEXT
			+ Column.RG_END_DATE.mInString + TEXT
			+ Column.RG_DELETED_AT.mInString + TEXT + "PRIMARY KEY ("
			+ Column.RID.mInString + "));";

	// private final String[] mNames = new String[Column.values().length];
	// //TODO possibility to make it look like a
	// lazy val?

	/**
	 * @return array of all columns names as {@link String}, thus an array of
	 *         {@code mNames}
	 */
	public synchronized static String[] names() {
		final Column[] values = Column.values();
		final int enumSize = values.length;
		final String[] names = new String[enumSize];
		for (int i = 0; i < enumSize; i++) {
			names[i] = values[i].mInString;
		}
		return names;
	}
	
	/**
	 * @return size of the table for Radin Group
	 */
	protected static int getRadinGroupTableSize() {
		Cursor cursor = Database.query(RadinGroupTableHelper.TABLE_RADIN_GROUP, null,
				null, null, null, null, null);
		return cursor.getCount();
	}
	
	/**
	 * used as auxiliary method of {@link Database.getEverythingFromRadinGroupTable} 
	 * @return {@code rowsColumnToValue} that associates to each row a map from column name to value 
	 */
	public static List<Map<String, String>> getEverythingFromRadinGroupTable() {
		Cursor cursor = Database.query(RadinGroupTableHelper.TABLE_RADIN_GROUP, null,
				null, null, null, null, null);
		List<Map<String, String>> rowsColumnToValue = new ArrayList<Map<String, String>>();
		
		cursor.moveToFirst();
		do {
			rowsColumnToValue.add(CursorHelper.rowPointedBy(cursor));
		} while (cursor.moveToNext());
		cursor.close();
		return rowsColumnToValue;
	}

	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_RADIN_GROUP);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO if we need to get previous data from old database
		// and put them in a new Database schema
	}

}
