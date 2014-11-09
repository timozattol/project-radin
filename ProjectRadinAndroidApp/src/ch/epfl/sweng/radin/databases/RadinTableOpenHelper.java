package ch.epfl.sweng.radin.databases;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Simonchelbc
 *
 */
public abstract class RadinTableOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "radin_database";
	
	public RadinTableOpenHelper(Context context, int version) {
		super(context, DATABASE_NAME, null, version);
	}
}
