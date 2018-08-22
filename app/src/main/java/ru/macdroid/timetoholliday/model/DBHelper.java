package ru.macdroid.timetoholliday.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


import static ru.macdroid.timetoholliday.model.Constants.DB_COLUMN_EVENT_ID;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                "create table " + Constants.DB_TABLE_TASK + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT , " + Constants.DB_COLUMN + " TEXT NOT NULL)"
        );

        sqLiteDatabase.execSQL(
                "Create TABLE " + Constants.DB_TABLE_HOLIDAY + " ( " + DB_COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Constants.DB_COLUMN_EVENT_NAME + " TEXT , " + Constants.DB_COLUMN_EVENT_DATE + " TEXT , " + Constants.DB_COLUMN_EVENT_DATE_IN_SQL + " TEXT , " + Constants.DB_COLUMN_EVENT_TIME + " TEXT , " + Constants.DB_COLUMN_EVENT_PICTURE + " TEXT )"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists " + Constants.DB_TABLE_HOLIDAY);
        sqLiteDatabase.execSQL("drop table if exists " + Constants.DB_TABLE_TASK);

        onCreate(sqLiteDatabase);

    }

    /* Tasks */

    public void insertNewTask(String task) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.DB_COLUMN, task);
        db.insertWithOnConflict(Constants.DB_TABLE_TASK, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }


    public void deleteTask(String task) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.DB_TABLE_TASK, Constants.DB_COLUMN + " = ?", new String[]{task});
        db.close();

    }

    public ArrayList<String> getTaskList() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.DB_TABLE_TASK, new String[]{Constants.DB_COLUMN}, null, null, null, null, null);

        while (cursor.moveToNext()) {

            int index = cursor.getColumnIndex(Constants.DB_COLUMN);

            taskList.add(cursor.getString(index));
        }

        cursor.close();
        db.close();
        return taskList;
    }


    /* Events */

    public void insertNewEvent(String eventName, String eventDate, String eDateInSql, String eventTime, String eventPicture) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.DB_COLUMN_EVENT_NAME, eventName);
        contentValues.put(Constants.DB_COLUMN_EVENT_DATE, eventDate);
        contentValues.put(Constants.DB_COLUMN_EVENT_DATE_IN_SQL, eDateInSql);
        contentValues.put(Constants.DB_COLUMN_EVENT_TIME, eventTime);
        contentValues.put(Constants.DB_COLUMN_EVENT_PICTURE, eventPicture);

        sqLiteDatabase.insert(Constants.DB_TABLE_HOLIDAY, null, contentValues);
        sqLiteDatabase.close();

    }
}
