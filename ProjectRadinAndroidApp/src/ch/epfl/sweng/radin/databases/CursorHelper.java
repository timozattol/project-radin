package ch.epfl.sweng.radin.databases;

import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Simonchelbc
 * add some static methods to manipulate the {@link Cursor} obtained by querying the {@link SQLiteDatabase}
 */
public class CursorHelper {
	/**
	 * @param cursor
	 * @return a mapping from column id to the value contained in this column at row pointed by {@code cursor}
	 */
	static Map<String, String> rowPointedBy(Cursor cursor) {
		Map<String, String> columnNameToValue = new HashMap<String, String>();
		for (String column : cursor.getColumnNames()) {
			columnNameToValue.put(column,
					cursor.getString(cursor.getColumnIndex(column)));
		}
		return columnNameToValue;
	}
}
