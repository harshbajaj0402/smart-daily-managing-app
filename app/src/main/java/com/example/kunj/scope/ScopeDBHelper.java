package com.example.kunj.scope;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunj on 10/8/2017.
 */

public class ScopeDBHelper {

    public static final String DATABASE_NAME = "scope.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_EXPENSE = "expense_table";
 //   public static final String TABLE_ATTENDANCE = "attendance_table";

    public static final String COLUMN_ID= "ID";
    public static final String COLUMN_CATEGORY= "CATEGORY";
    public static final String COLUMN_DESCRIPTION= "DESCRIPTION";
    public static final String COLUMN_AMOUNT= "AMOUNT";
    public static final String COLUMN_DATE= "DATE";

    public static final String CREATE_TABLE= "create table "+TABLE_EXPENSE+" ( "+COLUMN_ID+ " text, "+COLUMN_CATEGORY+" text , "+COLUMN_DESCRIPTION+" text, " +COLUMN_AMOUNT+" text , "+COLUMN_DATE+" date );";



    public Context context;
    ScopeOpenHelper scopeOpenHelper;
    SQLiteDatabase db;

    public ScopeDBHelper(Context context) {
        this.context = context;
        scopeOpenHelper = new ScopeOpenHelper(context);
        db = scopeOpenHelper.getWritableDatabase();
    }

     void addExpense(Expense expense) {

        // System.out.print(CREATE_TABLE);
         ContentValues values = new ContentValues();
         values.put(COLUMN_CATEGORY, expense.getCategory());
         values.put(COLUMN_DESCRIPTION, expense.getDescription());
         values.put(COLUMN_AMOUNT, expense.getAmount());
         values.put(COLUMN_DATE, expense.getDate());

         long res = db.insert(TABLE_EXPENSE, null, values);

        if(res == -1)
        {
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();

        }

    }

        List<Expense> getallExpenses()
    {
        List<Expense> expenses = new ArrayList<>();
        Cursor cursor = db.query(TABLE_EXPENSE,null,null,null,null,null,null);

        if(cursor != null && cursor.moveToFirst())
        {
            do {
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String amount = cursor.getString(cursor.getColumnIndex(COLUMN_AMOUNT));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

                expenses.add(new Expense(category,description,amount,date) );

            }while (cursor.moveToNext());

        }
        return expenses;
    }

    public int deleteExpense(Expense ex) {

        return db.delete(TABLE_EXPENSE,COLUMN_DESCRIPTION+"=?",new String[]{ex.description});
    }

   /* public String TABLE_NAME;
    public static String query;
    public void getQuery(List<String> columnName, String tableName) {
        TABLE_NAME = tableName;
        int i = columnName.size();
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (String str : columnName) {
            count++;
            if (sb.length() == 0) {
                sb.append("create table " + TABLE_NAME + "(id integer AUTO_INCREMENT, ");
            }
            if (count != (i)) {
                sb.append(str + " " + "text,");
            } else {
                sb.append(str + " " + "text);");
            }
        }

        query = (sb.toString());



    }

    void addEntry(List<String> data,GenericDTO genericDTO)
    {

        List<Object> cols = genericDTO.getAttributeValues("custom_expense");
        System.out.println("Cols:"+cols.size());
        System.out.println("Data"+data.size());
        // List<String> columnName = new ArrayList<>();
        int size = data.size();
        ContentValues values = new ContentValues();
        for(int i =0; i<size;i++) {
            //  Toast.makeText(context, TABLE_NAME, Toast.LENGTH_SHORT).show();
            values.put(cols.get(i).toString(), data.get(i));


        }
        long res = db.insert(TABLE_NAME, null, values);


        //long res = db.insert(TABLE_NAME,null,values);
        if (res == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

        }
    }*/


    private  class ScopeOpenHelper extends SQLiteOpenHelper {
        public ScopeOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}
