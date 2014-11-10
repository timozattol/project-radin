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
	private static final String DB_NAME = "radin_database";
	private TableHelper radinGroupTableHelper;

	public DatabaseOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		radinGroupTableHelper = new RadinGroupTableHelper();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		radinGroupTableHelper.onCreate(db);
		
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
