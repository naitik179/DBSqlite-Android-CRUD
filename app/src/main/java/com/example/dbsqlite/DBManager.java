package com.example.dbsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {

    public static final String dbname="dost3.db";
    public static final String TABLE_NAME="studentbl";
    public static final String ID_COL="id";
    public static final String NAME_COL1="name";
    public static final String NAME_COL2="email";
    public static final String NAME_COL3="rollno";

    public DBManager(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query="CREATE TABLE "+TABLE_NAME+" ("+ID_COL+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME_COL1+" TEXT,"
                +NAME_COL2+" Text,"+NAME_COL3+" Text)";
        Log.i("table","table created");
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public String insert(String p1, String p2, String p3)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();

        cv.put(NAME_COL1,p1);
        cv.put(NAME_COL2,p2);
        cv.put(NAME_COL3,p3);

        long result =db.insert(TABLE_NAME,null,cv);

        if (result==-1)
        {
            return "Failed To INSERT";
        }
        else {
            return "INSERTED Successfully";
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor getarow(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(" SELECT "+ID_COL+" from " + TABLE_NAME + " where email =?", new String[]{email});
        //Cursor res = db.rawQuery("select * from "+TABLE_NAME+"Where",null);
        return c;
    }

    public String updateData(String id,String name,String email,String rollno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COL,id);
        contentValues.put(NAME_COL1,name);
        contentValues.put(NAME_COL2,email);
        contentValues.put(NAME_COL3,rollno);
        long result = db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        if(result == -1)
        {
            return "FAILED to Update ";
        }
        else{
            return "SUCCESSFULLY Updated ";
        }
    }

    public String deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "ID = ?",new String[] {id});

        if(result == -1)
        {
            return "FAILED to Delete ";
        }
        else{
            return "SUCCESSFULLY Deleted ";
        }
    }
}
