package ch.epfl.sweng.radin.databases;


import android.database.sqlite.SQLiteDatabase;

/**
 * @author Simonchelbc 
 * Used to create the table associated to Radin Group of our database
 */
public class RadinGroupTableHelper implements TableHelper{
	private static final String TABLE_RADIN_GROUP = "RADIN_GROUP";
	
	private static final String CREATE_TABLE_RADIN_GROUP = "CREATE TABLE "
			+ TABLE_RADIN_GROUP + "(_RID INT NOT NULL,"
			+ "RG_NAME TEXT NOT NULL," + "RG_CREATION_DATE TEXT NOT NULL,"
			+ "RG_DESCRIPTION TEXT," + "RG_GROUP TEXT," + "RG_MASTER_RID,"
			+ "RG_AVATAR TEXT," + "RG_END_DATE TEXT," + "RG_DELETED_AT TEXT,"
			+ "PRIMARY KEY (_RID)" + ");";

	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_RADIN_GROUP);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//TODO if we need to get previous data from old database
		//and put them in a new Database schema

	}

}
