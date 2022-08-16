package com.llezhnin.eisenhowermatrix.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.llezhnin.eisenhowermatrix.category.CategoryDataManager;

public class DatabaseManager {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public SQLiteDatabase getDb() {
        return db;
    }

    public DatabaseManager(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public void openDB() {
        db = databaseHelper.getWritableDatabase();
        databaseHelper.onOpen(db);
    }

    public void readDB() {
        db = databaseHelper.getReadableDatabase();
    }

    public void insertToDb(String TABLE_NAME, ContentValues values) {
        openDB();
        db.insert(TABLE_NAME, null, values);
        onDataChanged();
    }

    public void updateInsideDB(String TABLE_NAME, int id, ContentValues contentValues){
        openDB();
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{String.valueOf(id)});
        onDataChanged();
    }

    public Cursor readAllData(String TABLE_NAME) {
        String query = "SELECT * FROM " + TABLE_NAME;
        readDB();
        return db.rawQuery(query, null);
    }

    public ContentValues getById(String TABLE_NAME, int position) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
        openDB();
        try (Cursor cursor = getDb().rawQuery(sql, new String[]{String.valueOf(position)})) {
            ContentValues contentValues = new ContentValues();
            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    contentValues.put(cursor.getColumnName(i), cursor.getString(i));
                }
                return contentValues;
            } else {
                return null;
            }
        }
    }

    public void deleteById(String TABLE_NAME, int id) {
        openDB();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE id = " + id);
        onDataChanged();
    }

    public void onDataChanged() {
        CategoryDataManager.updateDataset();
    }
}
