package com.example.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.core.database.sqlite.SQLiteDatabaseKt;

import java.util.ArrayList;

//class for database helper
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //create table on database
        sqLiteDatabase.execSQL(Constants.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //upgrade table if any structure change in db

        //drop table if exists
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        //create table again
        onCreate(sqLiteDatabase);

    }

    //insert function to insert data in database
    public long insertContact(String image, String name, String phone, String email, String note, String addedTime, String updatedTime){
        //get writeable database to write data on db
        SQLiteDatabase db = this.getWritableDatabase();

        //Create contentValue class object to save data
        ContentValues contentValues = new ContentValues();

        //id will save auto as we write query
        contentValues.put(Constants.C_IMAGE,image);
        contentValues.put(Constants.C_NAME,name);
        contentValues.put(Constants.C_PHONE,phone);
        contentValues.put(Constants.C_EMAIL,email);
        contentValues.put(Constants.C_NOTE,note);
        contentValues.put(Constants.C_ADDED_TIME,addedTime);
        contentValues.put(Constants.C_UPDATED_TIME,updatedTime);

        //insert data in row, it will return id of record
        long id = db.insert(Constants.TABLE_NAME,null,contentValues);

        //close db
        db.close();

        //return id
        return id;
    }

    //getdata
    public ArrayList<ModelContact> getAllData(){

        //create arraylist
        ArrayList<ModelContact> arrayList = new ArrayList<>();
        //sql command query
        String selectQuery = "SELECT * FROM "+Constants.TABLE_NAME;

        //get readable db
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //looping
        if (cursor.moveToFirst()){
            do {

                ModelContact modelContact = new ModelContact(
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_IMAGE)),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_NOTE)),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME)),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME))
                );

                arrayList.add(modelContact);

            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }

}
