package ch.epfl.sweng.radin.databases;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;

/**
 * @author Simonchelbc Represents the database of users takes care of opening
 *         the database if it exists, creating it if it does not, and upgrading
 *         it if necessary
 */
class UserDatabaseOpenHelper extends DatabaseOpenHelper {
	private static final String USER_DATABASE_NAME = "users.db";
	private static final String USERS_TABLE_NAME = "USERSTABLE";
	private static final String UID = "_id";
	private static final String NAME = "UserName";
	private static final String PASSWORD = "Password";
	private static final String IBAN = "Iban";
	private static final String LANGUAGE = "Language";
	private static final String ADDRESS = "Address";
	private static final String OPTIONS = "Options";
//	private static final AvatarClass AVATAR = "Avatar"; // TODO trouver un moyen
	// de stocker l'avatar, ne pas oublier de rajouter Avatar a DB quand on aura trouvé

	private static final int DATABASE_VERSION = 1;
	private Context mContext;

	/**
	 * @param context
	 *            null in the CursorFactory field means that we are going to use
	 *            the default cursor
	 */
	public UserDatabaseOpenHelper(Context context) {
		super(context, USER_DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
	}

	/*
	 * gets called when the database is created for the first time, initial
	 * tables initial data inside tables should be put here in db
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		synchronized (db) {
			final List<String> fields = Collections.unmodifiableList(Arrays
					.asList(UID + "INTEGER PRIMARY KEY AUTOINCREMENT", NAME, PASSWORD, IBAN, LANGUAGE, ADDRESS, OPTIONS)); // TODO
			// put
			// avatar
			// somewhere
			try {
				db.execSQL(createTableStatementString(USERS_TABLE_NAME,
						fields));
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	/*
	 * called when db need to be upgraded, to drop tables, add tables or do
	 * anything to upgrade to the new database schema can use ALTER TABLE to
	 * insert new columns into live table or rename or remove them from old
	 * table and then create new table with content of old table Put only one
	 * statement per db.execSQL that must not use SQL statement that return data
	 * because method is void
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		synchronized (db) {

		}
	}

}
