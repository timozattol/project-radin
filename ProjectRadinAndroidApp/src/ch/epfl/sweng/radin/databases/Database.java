package ch.epfl.sweng.radin.databases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.radin.storage.Model;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * @author Simonchelbc Singleton Database object that can be used accross
 *         multiple threads
 */
public final class Database {
	private static Database dbInstance = null;
	private static DatabaseOpenHelper mOpenHelper = null;

	private static Context mCxt;

	private Database() {
	}
	
	
	public static boolean initialized() {
		return dbInstance != null && mOpenHelper != null;
	}

	/**
	 * @param cxt
	 *            {@link Context} of the Activity calling this method Shall
	 *            always be called before using the Database
	 */
	public static synchronized void initialize(Context cxt) {
		if (cxt == null) {
			throw new IllegalArgumentException(Context.class.getName()
					+ " cannot be null");
		}
		mCxt = cxt;
		if (!initialized()) {
			dbInstance = new Database();
			mOpenHelper = new DatabaseOpenHelper(mCxt);
		}
	}

	/**
	 * used in every public method
	 */
	private static void instanceNotNullCheck() {
		if (!initialized()) {
			throw new IllegalStateException(Database.class.getName()
					+ " is not initialized, " + "call initialize(..) first");
		}
	}

	/**
	 * @param radinGroups
	 * @return a map that associated row id of 
	 * the table where the model has been inserted
	 */
	public static Map<Long, Model> insert(List<RadinGroupModel> radinGroups) {
		instanceNotNullCheck();
		SQLiteDatabase sqlDb = mOpenHelper.getWritableDatabase();
		if (radinGroups == null) {
			throw new IllegalArgumentException(
					"radinGroups pointing to null");
		} else if (radinGroups.isEmpty()) {
			throw new IllegalArgumentException(
					"radinGroups cannot be Empty"); //TODO or do nothing instead?
		} else {
			final Map<Long, Model> rowIDToRadinGroup = new HashMap<Long, Model>(
					radinGroups.size());
			for (RadinGroupModel rg : radinGroups) {
				long rowWhereInsertedID = insertSingleRadinGroupInDB(rg, sqlDb);
				rowIDToRadinGroup.put(rowWhereInsertedID, rg);
			}
			return rowIDToRadinGroup;
		}
	}
	
	private static long insertSingleRadinGroupInDB(RadinGroupModel rg, SQLiteDatabase sqlDb) {
		instanceNotNullCheck();
		if (rg == null) {
			throw new IllegalArgumentException(
					"RadinGroup to insert cannot be null");
		}
		final long rowID = sqlDb.insert(
				RadinGroupTableHelper.TABLE_RADIN_GROUP, null,
				rg.getContentValues());
		return rowID;
	}
	
	public static List<Map<String, String>> getEverythingFromRadinGroupTable() {
		instanceNotNullCheck();
		return RadinGroupTableHelper.getEverythingFromRadinGroupTable();
	}
	
	/**
	 * @return size of the RadinGroup table
	 */
	public static int getRadinGroupTableSize() {
		instanceNotNullCheck();
		return RadinGroupTableHelper.getRadinGroupTableSize();
	}
	/**
	 * @param table
	 * @param columns
	 * @param whereClause
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param sortOrder
	 * @return a Cursor object that can explore each rows of the table asked
	 */
	public static Cursor query(String table, String[] columns,
			String whereClause, String[] selectionArgs, String groupBy,
			String having, String sortOrder) {
		instanceNotNullCheck();
		SQLiteQueryBuilder buider = new SQLiteQueryBuilder();
		buider.setTables(table);
		Cursor cursor = buider.query(mOpenHelper.getReadableDatabase(), columns, whereClause,
				selectionArgs, groupBy, having, sortOrder);

		if (cursor == null) {
			return null;
		} else if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}
		return cursor;
	}

	/**
	 * @author Simonchelbc
	 * 
	 */
	private static final class DatabaseOpenHelper extends SQLiteOpenHelper {
		public static final String DB_NAME = "radin_database";
		private static final int VERSION = 1;
		private static SQLiteDatabase mDatabase;

		private TableHelper radinGroupTableHelper;
		private TableHelper userRelationshipsTableHelper;

		/**
		 * @param context
		 *            , Context of calling activity
		 * @param factory
		 *            can be null for the moment
		 * @param version
		 *            Database version, 1 at first, used to see how to upgrade a
		 *            database
		 */
		private DatabaseOpenHelper(Context cxt) {
			super(cxt, DB_NAME, null, VERSION);
			radinGroupTableHelper = new RadinGroupTableHelper();
			userRelationshipsTableHelper = new UserRelationshipsTableHelper();
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			mDatabase = db;
			radinGroupTableHelper.onCreate(mDatabase);
			//userRelationshipsTableHelper.onCreate(mDatabase); //TODO uncomment and debug
			//SQLiteException due to maybe not enough related table or 
			//badly written statement
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			switch (oldVersion) {
				case 1:
					// do something;
					break;
				case 2:
					// do something
					break;
				default:
					// default action
					break;
			}
		}
	}
}