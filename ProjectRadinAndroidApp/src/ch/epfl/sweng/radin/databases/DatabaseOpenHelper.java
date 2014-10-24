package ch.epfl.sweng.radin.databases;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
	static final int SIZE_VAR_CHAR = 255;

	public DatabaseOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public String createTableStatementString(String tableName,
			List<String> tableFields) {
		String statementBaseline = "CREATE TABLE" + tableName + "(";
		StringBuilder statementBuilder = new StringBuilder(statementBaseline);
		for (String field : tableFields) {
			statementBuilder.append(field + "VARCHAR(" + SIZE_VAR_CHAR + ")");
		}
		statementBuilder.append(");");
		return statementBuilder.toString();
	}
}
