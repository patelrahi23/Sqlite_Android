package com.rahi.sqlite_database.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context, "UserData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Userdetails(name TEXT, email TEXT primary key, password TEXT, Profession TEXT ,age TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists Userdetails");
    }

    public int DeleteUser(String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
        return sqLiteDatabase.delete("Userdetails","email=?",new String[]{email});

    }

    public boolean InsertData(String name, String email, String password, String Profession, String age){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long result = 0;
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("Profession",Profession);
        contentValues.put("age",age);
        result = sqLiteDatabase.insert("Userdetails",null,contentValues);
        return result != -1;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Userdetails",null);
        return cursor;
    }

    public boolean UpdateUser(String name, String email, String password, String Profession, int age ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long result = 0;
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("Profession",Profession);
        contentValues.put("age",age);
        result = db.update("Userdetails",contentValues,"email=?",new String[]{email});
        return result != -1;
    }

}
