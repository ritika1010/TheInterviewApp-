package com.example.theinterviewapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class newPass extends AppCompatActivity {
String email;
DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        myDB=new DatabaseHelper(this);


    }

    public void set_pass(View view)
    {
        Intent curr=getIntent();

        if(curr.hasExtra(Intent.EXTRA_TEXT))
        {
            email=curr.getStringExtra(Intent.EXTRA_TEXT);
        }
        EditText et1,et2;
        et1=findViewById(R.id.editText4);
        et2=findViewById(R.id.editText5);

        if(TextUtils.isEmpty(et1.getText().toString())) {
            et1.setError("Cannot be empty");
            return;
        }
        else if(TextUtils.isEmpty(et2.getText().toString())) {
            et2.setError("Cannot be empty");
            return;
        }
        else {

            if (et1.getText().toString().equals(et2.getText().toString())) {
                myDB.new_pass(email, et2.getText().toString());
                Toast.makeText(this, "Password reset", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Password dongt match", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
