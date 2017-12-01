package com.freelance.netanel.androidsearchapp.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Netanel on 26/11/2017.
 */

public class DAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME  = "SearchHistory";
    private static final int DATABASE_VERSION  = 1;

    private static final String TABLE_HISTORY  = "history" ;
    private static final String COLUMN_ID  = "id" ;
    private static final String COLUMN_WORD  = "word" ;

    public DAO(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HISTORY_TABLE =
                "CREATE TABLE " + TABLE_HISTORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                        + COLUMN_WORD + " TEXT" + ")";
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DAO.this.clear();
    }

    public void addQuery(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_WORD,query);
        db.insert(TABLE_HISTORY,null,values);
    }

    public List<String> getHistory() {
//        Stack<String> stack = new Stack<>();
        List<String> history = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToLast()) {
            do {
                history.add(cursor.getString(1));
//                stack.push(cursor.getString(1));
            }while (cursor.moveToPrevious());
        }
        cursor.close();

        return history;
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }
}
