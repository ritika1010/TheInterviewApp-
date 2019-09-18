package com.example.theinterviewapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
public DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB=new DatabaseHelper(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.about) {

            Context context = MainActivity.this;
            Class destActivity = about.class;

            Intent intent = new Intent(context, destActivity);
            startActivity(intent);

        }
        return true;
    }

    public void new_log(View v)
    {
        Context context = MainActivity.this;
        Class destActivity = login.class;

        Intent intent = new Intent(context, destActivity);
        startActivity(intent);
    }

    public void search_record(View v)
    {
        EditText et1=findViewById(R.id.email_et);
        EditText et2=findViewById(R.id.password_et);

        if(TextUtils.isEmpty(et1.getText().toString())) {
            et1.setError("Cannot be empty");
            return;
        }
        else if(TextUtils.isEmpty(et2.getText().toString())) {
        et2.setError("Cannot be empty");
        return;
    }
    else {
            boolean A = myDB.onSearch(et1.getText().toString(), et2.getText().toString());
            if (A == true) {
                Toast.makeText(this, "Found", Toast.LENGTH_SHORT).show();
                String name = myDB.find_name(et1.getText().toString());
                Context context = MainActivity.this;
                Class destActivity = user_homepage.class;

                Intent intent = new Intent(context, destActivity);
                intent.putExtra(Intent.EXTRA_TEXT, name);
                startActivity(intent);
            } else
                Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show();
        }
    }
    public void forgotpass(View v)
    {
        Context context = MainActivity.this;
        Class destActivity = forgot_pass.class;

        Intent intent = new Intent(context, destActivity);
        startActivity(intent);
    }
}
