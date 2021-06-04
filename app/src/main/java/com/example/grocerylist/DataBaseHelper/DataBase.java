package com.example.grocerylist.DataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    Context context;


    public static final String TABLE_LIST = "ALL_LISTS";
    public static final String TABLE_ITEMS = "ALL_ITEMS";

    //    TABLE_LIST Table Columns
    public static final String List_ID = "id";
    public static final String List_name = "name";
    public static final String List_status = "status";


    //    TABLE_LIST Table Columns
    public static final String Item_ID = "id";
    public static final String Item_List_ID = "lis_id";
    public static final String Item_Name = "name";
    public static final String Item_Info = "info";

    // Database Information
    public static final String DB_NAME = "VEHICLE.DB";

    // database version
    public static final int DB_VERSION = 1;


    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    String CREATE_TABLE_LIST = "CREATE TABLE " + TABLE_LIST +
            " ( " +
            List_ID + " INTEGER PRIMARY KEY," +
            List_name + " TEXT NOT NULL," +
            List_status + " INTEGER )";

    String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS +
            " ( " +
            Item_ID + " INTEGER PRIMARY KEY," +
            Item_List_ID + " INTEGER," +
            Item_Name + " TEXT NOT NULL," +
            Item_Info + " TEXT NOT NULL )";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LIST);
        db.execSQL(CREATE_TABLE_ITEMS);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);

    }

    //Insert
    public Boolean InsertList(String name, int status) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(List_name, name);
        contentValues.put(List_status, status);
        db.insert(TABLE_LIST, null, contentValues);
        db.close();
        return true;
    }

    public Boolean InsertItem(int listId, String name, String info) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item_List_ID, listId);
        contentValues.put(Item_Name, name);
        contentValues.put(Item_Info, info);
        db.insert(TABLE_ITEMS, null, contentValues);
        db.close();
        return true;
    }

    //Display
    public Cursor DisplayList() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("Select * from " + TABLE_LIST, null);
    }

    public Cursor DisplayPending() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("Select * from " + TABLE_LIST + " WHERE " + List_status + "=" + 0, null);
    }

    public Cursor DisplayCompleted() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("Select * from " + TABLE_LIST + " WHERE " + List_status + "=" + 1, null);
    }

    public Cursor DisplayItems(Integer id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("Select * from " + TABLE_ITEMS + " WHERE " + Item_List_ID + "=" + id, null);
    }

    //    Update
    public void updateList(int id, int status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(List_status, status);
        //updating row
        sqLiteDatabase.update(TABLE_LIST, contentValues, "ID=" + id, null);
        sqLiteDatabase.close();
    }
}

