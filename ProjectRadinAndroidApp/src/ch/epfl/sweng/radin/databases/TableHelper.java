package ch.epfl.sweng.radin.databases;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author Simonchelbc
 * Used on behalf of DatabaseOpenHelper, it is tipically implemented
 * by classes managing tables of the app database
 */
public interface TableHelper {
	void onCreate(SQLiteDatabase db);
	void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
