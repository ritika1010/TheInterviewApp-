package com.example.theinterviewapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String Database_name="users.db";
    public static final String Table_name="users_table";
    public static final String col_0="id";
    public static final String col_1="Name";
    public static final String col_2="Email_id";
    public static final String col_3="Mob_no";
    public static final String col_4="Password";
    public static final String col_5="Interview_file";

    public static final String Table_2="questionaire_table";
    public static final String c_1="ques_id";
    public static final String c_2="question";
    public static final String c_3="time";

    public static final String Table_3="master_table";
    public static final String cl_1="id";
    public static final String cl_2="ques_id";
    public static final String cl_3="time_given";
    public static final String cl_4="time_taken";


    public DatabaseHelper(Context context) {
        super(context, Database_name,null,1);
        SQLiteDatabase db=this.getWritableDatabase();


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_name + " (id INTEGER PRIMARY KEY AUTOINCREMENT,Name VARCHAR(20), Email_id VARCHAR(30), Mob_no INTEGER(11), Password INTEGER(10), Interview_file VARCHAR(50)  )");
        db.execSQL("create table " + Table_2 + " (ques_id INTEGER PRIMARY KEY AUTOINCREMENT, question VARCHAR(50), time INTEGER(3))");
        db.execSQL("Insert into "+ Table_2+" (question,time) values ('Talk about yourself',60)");
        db.execSQL("Insert into "+ Table_2+" (question,time) values ('Which domain you plan on working and what previous work?',120)");
        db.execSQL("Insert into "+ Table_2+" (question,time) values ('What outcomes do you expect?',60)");
        db.execSQL("create table " + Table_3 + " (id INTEGER(2),ques_id INTEGER(2), time_given INTEGER(3),time_taken INTEGER(3))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);
    }

    public void onInsert(String name,String email,String mobno,String pass)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(new StringBuilder().append("INSERT INTO ").append(Table_name).append("(Name,Email_id,Mob_no,Password,Interview_file) VALUES ('").append(name).append("','").append(email).append("','").append(mobno).append("','").append(pass).append("','null');").toString());

    }

    public boolean onSearch(String email, String pass)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        //String search=new StringBuilder().append("SELECT * FROM ").append(Table_name).append(" WHERE ").append(col_2).append(" = '").append(email).append("' AND ").append(col_4).append(" = '").append(pass).append("';").toString();
        Cursor cursor= (Cursor) db.rawQuery("SELECT * FROM "+ Table_name + " WHERE "
                + col_4 + " = " + pass + " AND " + col_2 + " = '" +email + "'" ,null);
        if(cursor.getCount()>0) {
            return true;
        }
        else
            return false;
    }

    public boolean onVerify(String email)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= (Cursor) db.rawQuery("SELECT * FROM "+ Table_name + " WHERE "
                + col_2 + " = '" + email + "'",null);
        if(cursor.getCount()>0) {
            return true;
        }
        else
            return false;
    }

    public String find_name(String email) {
        int flag=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = (Cursor) db.rawQuery("SELECT * FROM " + Table_name + " WHERE "
                + col_2 + " = '" + email + "'", null);
        if (cursor.getCount() > 0) {

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                if (email.equalsIgnoreCase(cursor.getString(2))) {
                    flag = 1;
                    break;
                }
            }

            if (flag == 1) {
                return cursor.getString(1);
            }
            else
                return "none";

        }
        else
       return "none";

    }

    public void on_video(String path,String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+Table_name+ " SET " + col_5+ " = '"+ path + "' where "+ col_1 + " = '"+ name+ "'");

    }

    public String search_mob(String email)
    {
        int flag=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = (Cursor) db.rawQuery("SELECT * FROM " + Table_name + " WHERE "
                + col_2 + " = '" + email + "'", null);
        if (cursor.getCount() > 0) {

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                if (email.equalsIgnoreCase(cursor.getString(2))) {
                    flag = 1;
                    break;
                }
            }

            if (flag == 1) {
                return cursor.getString(3);
            }
            else
                return "none";

        }
        else
            return "none";
    }

    public void new_pass(String email,String pass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+Table_name+ " SET " + col_4+ " = '"+ pass + "' where "+ col_2+ " = '"+ email+ "'");

    }

    public int find_id(String name) {
        int flag=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = (Cursor) db.rawQuery("SELECT * FROM " + Table_name + " WHERE "
                + col_1+ " = '" + name + "'", null);
        if (cursor.getCount() > 0) {

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                if (name.equalsIgnoreCase(cursor.getString(1))) {
                    flag = 1;
                    break;
                }
            }

            if (flag == 1) {
                return cursor.getInt(0);
            }
            else
                return 0;

        }
        else
            return 0;

    }

    public String search_ques(int no) {
        int flag=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = (Cursor) db.rawQuery("SELECT * FROM " + Table_2+ " WHERE "
                + c_1+ " = '" + no + "'", null);
        if (cursor.getCount() > 0) {

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                if (no==cursor.getInt(0)) {
                    flag = 1;
                    break;
                }
            }

            if (flag == 1) {
                return cursor.getString(1);
            }
            else
                return "none";

        }
        else
            return "none";

    }

    public int search_ques_time(int no) {
        int flag=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = (Cursor) db.rawQuery("SELECT * FROM " + Table_2+ " WHERE "
                + c_1+ " = '" + no + "'", null);
        if (cursor.getCount() > 0) {

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                if (no==cursor.getInt(0)) {
                    flag = 1;
                    break;
                }
            }

            if (flag == 1) {
                return cursor.getInt(2);
            }
            else
                return 0;

        }
        else
            return 0;

    }

    public void make_record(int id,int ques_id,int time_given,int time_taken)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(" INSERT INTO "+ Table_3 + " VALUES( '"+id+ "','"+ ques_id + "','"+ time_given+ "','"+ time_taken+ "');");
    }
}
