package com.example.sqlitetutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StudentScoreRecord extends SQLiteOpenHelper {

    private static String TAG = "Record Handler";

    private static final String DB_NAME = "Student Score Record";
    private static final String DB_TABLE = "Score";
    private static final int DB_VER = 1;

    Context ctx;
    SQLiteDatabase scoreDb;

    public StudentScoreRecord(Context ct) {
        super(ct, DB_NAME, null,DB_VER);
        ctx = ct;
    }

    //Create DB
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " +DB_TABLE+ "(row_id integer primary key autoincrement, stu_name text, stu_id text, stu_score text);";
        db.execSQL(query);
        Log.i(TAG, "Table Created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
        onCreate(db);
    }

    public void insertData(String toString, String toString1, String toString2) {
        scoreDb = getWritableDatabase();
        String query ="INSERT INTO "+DB_TABLE+ " (stu_name,stu_id,stu_score) VALUES ('"+toString+"','"+toString1+"', '"+toString2+"');";
        scoreDb.execSQL(query);
        Toast.makeText(ctx, "Data saved successfully!", Toast.LENGTH_SHORT).show();
    }

    public Cursor getAllData() {
        scoreDb = getReadableDatabase();
        String query = "SELECT * FROM " + DB_TABLE;
        return scoreDb.rawQuery(query, null);
    }

    public Cursor getDataID(int col, String data){
        scoreDb = getReadableDatabase();

        String col_title ="invalid";
        switch(col){
            case 0:
                col_title = "stu_name";
                break;
            case 1:
                col_title = "stu_id";
                break;
            case 2:
                col_title = "stu_score";
                break;
        }
        Log.i(TAG, "Column Title: " + col_title);

        String query = "SELECT * FROM " + DB_TABLE +
                " WHERE " + col_title + " = " + '"' + data + '"';
        Log.i(TAG, "SQL query: " + query);

        Cursor cr = scoreDb.rawQuery(query, null);
        Log.i(TAG, "Cursor saved!" );
        return cr;
    }

    public void updateData(int record_id, String newName, String newSID, String newScore,
                           String edit_name, String edit_sid, String edit_score) {
        scoreDb = getWritableDatabase();
        Log.i("Score Database", "Entered updateData()");

        String query = "UPDATE " + DB_TABLE + " SET" +
                " stu_name = '" + newName + "', stu_id = '" + newSID + "', stu_score = '" + newScore + "' " +
                "WHERE row_id = " + record_id + " AND stu_name = '" + edit_name + "' AND stu_id = '" + edit_sid +
                "' AND stu_score = '" + edit_score + "'";
        Log.i(TAG, "SQL query_UPDATE: " + query);
        scoreDb.execSQL(query);
        Toast.makeText(ctx, "Data on row " + record_id + " has been updated", Toast.LENGTH_SHORT).show();
    }

    public void deleteData(int record_id, String edit_name, String edit_sid, String edit_score){
        scoreDb = getWritableDatabase();
        String query = "DELETE FROM " + DB_TABLE + " WHERE row_id = '" + record_id +
                "' AND stu_name = '" + edit_name + "' AND stu_id = '" + edit_sid +
                "' AND stu_score = '" + edit_score + "'";
        Toast.makeText(ctx, "Record " + record_id + " has been deleted!", Toast.LENGTH_SHORT).show();
        scoreDb.execSQL(query);
    }

}
