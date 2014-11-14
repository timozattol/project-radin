package ch.epfl.sweng.radin.databases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.radin.storage.Model;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import android.animation.AnimatorSet.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;

/**
 * @author Simonchelbc
 * 
 */
public class Database {
	private final DatabaseOpenHelper mOpenHelper;

	public Database(Context context) {
		mOpenHelper = new DatabaseOpenHelper(context);
	}

	public Map<Long, Model> insert(List<RadinGroupModel> radinGroups) {
		return mOpenHelper.insert(radinGroups);
	}

	/**
	 * @author Simonchelbc
	 * 
	 */
	private static class DatabaseOpenHelper extends SQLiteOpenHelper {
		public static final String DB_NAME = "radin_database";
		public static final int VERSION = 1;
		private final Context mHelperContext;
		private SQLiteDatabase mDatabase;
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
		DatabaseOpenHelper(Context context) {
			super(context, DB_NAME, null, VERSION);
			mHelperContext = context;
			radinGroupTableHelper = new RadinGroupTableHelper();
			userRelationshipsTableHelper = new UserRelationshipsTableHelper();
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			mDatabase = db;
			radinGroupTableHelper.onCreate(mDatabase);
			userRelationshipsTableHelper.onCreate(mDatabase);
		}

		// TODO add to interface
		public Map<Long, Model> insert(List<RadinGroupModel> radinGroups) {
			if (radinGroups == null) {
				throw new IllegalArgumentException(
						"radinGroups pointing to null");
			} else if (radinGroups.isEmpty()) {
				throw new IllegalArgumentException(
						"radinGroups cannot be Empty");
			} else {
				final Map<Long, Model> rowIDToRadinGroup = new HashMap<Long, Model>(
						radinGroups.size());
				for (RadinGroupModel rg : radinGroups) {
					if (rg == null) {
						throw new IllegalArgumentException(
								"RadinGroup to insert cannot be null");
					}
					final long rowID = mDatabase.insert(
							RadinGroupTableHelper.TABLE_RADIN_GROUP, null,
							rg.getContentValues());
					rowIDToRadinGroup.put(rowID, rg);
				}
				return rowIDToRadinGroup;
			}
		}

		/**
		 * @param table
		 *            the name of the table to search into
		 * @return
		 */
		public Cursor queryIn(String table, String[] columns,
				String whereClause, String[] selectionArgs, String groupBy,
				String having, String sortOrder) {
			SQLiteQueryBuilder buider = new SQLiteQueryBuilder();
			buider.setTables(table);
			Cursor cursor = buider.query(mDatabase, columns, whereClause,
					selectionArgs, groupBy, having, sortOrder);

			if (cursor == null) {
				return null;
			} else if (!cursor.moveToFirst()) {
				cursor.close();
				return null;
			}
			return cursor;
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