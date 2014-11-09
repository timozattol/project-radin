package ch.epfl.sweng.radin.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * @author Simonchelbc
 * Used to create the RadinGroupTable of our database
 */
public class RadinGroupTableOpenHelper extends RadinTableOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE_RADIN_GROUP = "CREATE TABLE RADIN_GROUP("
			+ "_RID INT NOT NULL,"
			+ "NAME TEXT NOT NULL,"
			+ "START_DATE TEXT NOT NULL,"
			+ "DESCRIPTION TEXT,"
			+ "GROUP TEXT,"
			+ "MASTER_RID,"
			+ "AVATAR TEXT,"
			+ "END_DATE TEXT,"
			+ "DELETED_AT TEXT," + "PRIMARY KEY (_RID)" + ");";

	public RadinGroupTableOpenHelper(Context context) {
		super(context, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_RADIN_GROUP);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
