package ch.epfl.sweng.radin.databases;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author Simonchelbc
 *
 */
public class UserRelationshipsTableHelper implements TableHelper {
	private static final String CREATE_TABLE_USER_RELATIONSHIP = "CREATE TABLE USER_RELATIONSHIP(" 
			+ "_UID_SOURCE INT NOT NULL,"
			+ "_UID_TARGET INT NOT NULL,"
			+ "UR_RELATION INT NOT NULL,"
			+ "FOREIGN KEY (_UID_SOURCE, _UID_TARGET),"
			+ "REFERENCES USER(_UID, _UID),"
			+ "PRIMARY KEY (_UID_SOURCE, _UID_TARGET)" 
			+ ");";

	UserRelationshipsTableHelper() { }
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USER_RELATIONSHIP);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO if we need to get previous data from old database
		// and put them in a new Database schema

	}
}
