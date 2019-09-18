package com.example.theinterviewapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class user_homepage<et> extends AppCompatActivity {
    DatabaseHelper myDB;
    String var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);
        myDB = new DatabaseHelper(this);
        Intent curr = getIntent();

        if (curr.hasExtra(Intent.EXTRA_TEXT)) {
            var = curr.getStringExtra(Intent.EXTRA_TEXT);
            TextView tv = findViewById(R.id.textView);
            tv.setText("Welcome " + var);
        }
    }

    public void start_int(View view) {
        int id;
        id = myDB.find_id(var);
        Context context = this;
        Class destActivity = interview.class;

        Intent intent = new Intent(context, destActivity);
        intent.putExtra("id", id);
        intent.putExtra("name",var);
        startActivity(intent);


    }
}

