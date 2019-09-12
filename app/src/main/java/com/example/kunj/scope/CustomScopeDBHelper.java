package com.example.kunj.scope;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.database.sqlite.SQLiteDatabase.openDatabase;
import static com.example.kunj.scope.ScopeDBHelper.CREATE_TABLE;
import static com.example.kunj.scope.ScopeDBHelper.DATABASE_NAME;
import static com.example.kunj.scope.ScopeDBHelper.DATABASE_VERSION;


/**
 * Created by kunj on 2/8/2018.
 */

public class CustomScopeDBHelper {


    public Context context;
    SQLiteDatabase db;
    CustomScopeOpenHelper customScopeOpenHelper;


    public static String query;
    public static final String DATABASE_NAME = "scope.db";
    public static final int DATABASE_VERSION = 1;
    String TABLE_NAME;

  public void getQuery(List<String> columnName, String tableName) {
        TABLE_NAME = tableName;
        int i = columnName.size();
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (String str : columnName) {
            count++;
            if (sb.length() == 0) {
                sb.append("create table " + TABLE_NAME + " ( ID text, ");
            }
            if (count != (i)) {
                sb.append(str + " " + "text,");
            } else {
                sb.append(str + " " + "text);");
            }
        }

        query = (sb.toString());

    }
    public CustomScopeDBHelper(Context context) {
        this.context = context;
        customScopeOpenHelper= new CustomScopeOpenHelper(context);
        db = customScopeOpenHelper.getWritableDatabase();
    }

    void addEntry(List<String> data,GenericDTO genericDTO)
    {

        List<Object> cols = genericDTO.getAttributeValues("custom_expense");
        int size = data.size();
        ContentValues values = new ContentValues();
        for(int i =0; i<size;i++) {
           values.put(cols.get(i).toString(), data.get(i));
        }
        long res = db.insert(TABLE_NAME, null, values);


        if (res == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

        }
    }

    private  class CustomScopeOpenHelper extends SQLiteOpenHelper {

        public CustomScopeOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
           db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

