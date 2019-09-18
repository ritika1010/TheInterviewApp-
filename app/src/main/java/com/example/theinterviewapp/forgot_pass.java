package com.example.theinterviewapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class forgot_pass extends AppCompatActivity {
DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        myDB=new DatabaseHelper(this);
    }
    EditText email;
    public void submit(View v) {
        email = findViewById(R.id.editText2);
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Cannot be empty");
            return;
        }
        else {
            boolean a = myDB.onVerify(email.getText().toString());
            if (a == true) {
                Toast.makeText(this, "Email registered", Toast.LENGTH_SHORT).show();
                String mob_no = myDB.search_mob(email.getText().toString());
                sendPassword(mob_no);
            } else {
                Toast.makeText(this, "Email not registered", Toast.LENGTH_SHORT).show();
                Context context = this;
                Class destActivity = login.class;

                Intent intent = new Intent(context, destActivity);
                startActivity(intent);
                finish();
            }
         }


    }

    public void sendPassword(String mob_no) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        final int min = 1000;
        final int max = 9999;
        final int random = new Random().nextInt((max - min) + 1) + min;


        String txtMessage = "OTP: " + random;
        try {
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(mob_no, null, txtMessage, null, null);
            Toast.makeText(this, "SMS Sent Successfully to registered phone", Toast.LENGTH_SHORT).show();


            String var = String.valueOf(random);
            Context context = this;
            Class destActivity = verifyOTP.class;

            Intent intent = new Intent(context, destActivity);

            intent.putExtra("otp", String.valueOf(random));
           intent.putExtra("email",email.getText().toString());
            startActivity(intent);
            finish();

        } catch (Exception e) {
            AlertDialog.Builder alertDialogBuilder = new
                    AlertDialog.Builder(this);
            AlertDialog dialog = alertDialogBuilder.create();
            dialog.setMessage(e.getMessage());
            dialog.show();
            Toast.makeText(this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }
}
