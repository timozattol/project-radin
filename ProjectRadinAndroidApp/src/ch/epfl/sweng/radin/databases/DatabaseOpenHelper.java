package ch.epfl.sweng.radin.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Simonchelbc
 * 
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper{
	public static final String DB_NAME = "radin_database";
	public static final int VERSION = 1;
	private TableHelper radinGroupTableHelper;
	private TableHelper userRelationshipsTableHelper;

	/**
	 * @param context, Context of calling activity
	 * @param factory can be null for the moment
	 * @param version Database version, 1 at first, used to see how to upgrade a database
	 */
	public DatabaseOpenHelper(Context context, 
			CursorFactory factory) {
		super(context, DB_NAME, factory, VERSION);
		radinGroupTableHelper = new RadinGroupTableHelper();
		userRelationshipsTableHelper = new UserRelationshipsTableHelper();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		radinGroupTableHelper.onCreate(db);
		userRelationshipsTableHelper.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch(oldVersion) {
			case 1:
				//do something;
				break;
			case 2:
				//do something 
				break;
			default:
				//default action
				break;
		}
		
	}
	
	
	
}
