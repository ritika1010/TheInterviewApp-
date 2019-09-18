package com.example.theinterviewapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;


public class login extends AppCompatActivity {
   DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDB=new DatabaseHelper(this);


    }
    public void create_user(View v) {
        EditText et1 = findViewById(R.id.name_loginet);
        EditText et2 = findViewById(R.id.email_loginet);
        EditText et3 = findViewById(R.id.mob_no_loginet);
        EditText et4 = findViewById(R.id.pass_loginet);
        if(TextUtils.isEmpty(et2.getText().toString())) {
            et2.setError("Cannot be empty");
            return;
        }
        else if(TextUtils.isEmpty(et3.getText().toString())) {
            et3.setError("Cannot be empty");
            return;
        }
        else if(TextUtils.isEmpty(et4.getText().toString())) {
            et4.setError("Cannot be empty");
            return;
        }
        else if(TextUtils.isEmpty(et1.getText().toString())) {
            et1.setError("Cannot be empty");
            return;
        }
        else {

            boolean a = myDB.onVerify(et2.getText().toString());
            if (a == true) {
                Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
            } else {
                Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show();
                myDB.onInsert(et1.getText().toString(), et2.getText().toString(), et3.getText().toString(), String.valueOf(et4.getText()));
                Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

}