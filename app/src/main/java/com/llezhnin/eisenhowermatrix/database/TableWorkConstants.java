package com.llezhnin.eisenhowermatrix.database;

public final class TableWorkConstants {

    public static final String TABLE_NAME = "Work";

    public static final String COLUMN_NAME_ID = "Id";

    public static final String COLUMN_NAME_DESCRIPTION = "Description";

    public static final String COLUMN_NAME_PRIORITY = "Priority";

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME_DESCRIPTION + " TEXT," + COLUMN_NAME_PRIORITY + " INTEGER)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
