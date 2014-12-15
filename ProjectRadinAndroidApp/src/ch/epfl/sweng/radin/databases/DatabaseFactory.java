package ch.epfl.sweng.radin.databases;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author Simonchelbc
 * 
 */
public class DatabaseFactory {
	
	private static final String CREATE_TABLE_MEMBER_IN_RADIN = "CREATE TABLE MEMBER_IN_RADIN("
        + "_UID INT NOT NULL,"
        + "_RID INT NOT NULL,"
        + "START_DATE TEXT NOT NULL,"
        + "PERMISSION INT NOT NULL,"
        + "DELETED_AT TEXT,"
        + "FOREIGN KEY (_UID) REFERENCES USER(_UID),"
        + "FOREIGN KEY (_RID) REFERENCES RADIN_GROUP(_RID),"
        + "PRIMARY KEY (_UID, _RID));";
	private static final String CREATE_TABLE_USER_CONCERNED_BY_TRANSACTION = 
	        "CREATE TABLE USER_CONCERNED_BY_TRANSACTION(\r\n"
        + "_TID INT NOT NULL,"
        + "_UID INT NOT NULL,"
        + "COEFFICIENT INT NOT NULL,"
        + "FOREIGN KEY (_TID) REFERENCES TRANSACTION(_TID),"
        + "FOREIGN KEY (_UID) REFERENCES USER(_UID),"
        + "PRIMARY KEY (_TID, _UID)" + ");";

	private static final String CREATE_TABLE_GROUP_PRESET = "CREATE TABLE GROUP_PRESET("
        + "_GID INT NOT NULL,"
        + "_RID INT NOT NULL,"
        + "NAME TEXT NOT NULL,"
        + "DELETED_AT TEXT,"
        + "FOREIGN KEY (_RID) REFERENCES TABLE_RADIN(_RID),"
        + "PRIMARY KEY (_GID)" + ");";
	private static final String CREATE_TABLE_MEMBER_IN_GROUP = "CREATE TABLE MEMBER_IN_GROUP("
        + "_GID INT NOT NULL,"
        + "_UID INT NOT NULL,"
        + "DELETED_AT TEXT,"
        + "FOREIGN KEY (_GID) REFERENCES GROUP_PRESET(_GID),"
        + "FOREIGN KEY (_UID) REFERENCES USER(_UID),"
        + "PRIMARY KEY (_GID, _UID)" + ");";
	private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE TRANSACTION("
        + "_TID INT NOT NULL,"
        + "_RID INT NOT NULL,"
        + "ADDED_BY_UID INT NOT NULL,"
        + "DEBITOR_UID INT NOT NULL,"
        + "AMOUNT INT NOT NULL,"
        + "PURPOSE TEXT NOT NULL,"
        + "CURRENCY INT NOT NULL,"
        + "DATE TEXT NOT NULL,"
        + "TRANSACTION_TYPE INT NOT NULL,"
        + "JUSTIFICATIVE TEXT,"
        + "MASTER_TID INT,"
        + "DELETED_AT TEXT,"
        + "PRIMARY KEY (_RID)"
        + ");";
	private static final String CREATE_TABLE_INVITATION = "CREATE TABLE INVITATION("
        + "_UID INT NOT NULL,"
        + "_RID INT NOT NULL,"
        + "START_DATE TEXT NOT NULL,"
        + "ACCEPTED_DATE TEXT,"
        + "TARGET_UID INT NOT NULL,"
        + "DELETED_AT TEXT,"
        + "FOREIGN KEY (_UID) REFERENCES USER(_UID),"
        + "FOREIGN KEY (_RID) REFERENCES RADIN_GROUP(_RID),"
        + "PRIMARY KEY(_UID, _RID)" + ");";


	public static void createMembersTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_MEMBER_IN_RADIN);
	}

	public static void createUsersConcernedByTransactionTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USER_CONCERNED_BY_TRANSACTION);
	}
	
	public static void createGroupPresetsTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_GROUP_PRESET);
	}

	public static void createMembersInGroupTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_MEMBER_IN_GROUP);
	}

	public static void createTransactionsTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_TRANSACTION);
	}

	public static void createInvitationsTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_INVITATION);
	}
}