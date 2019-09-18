package com.example.theinterviewapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class verifyOTP extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);


    }

    public void verifyotp(View v)
    {String email = null, otp = null;
        EditText et;
        Intent curr=getIntent();
        //if (extras != null) {
            email = curr.getStringExtra("email");
            otp = curr.getStringExtra("otp");
       // }
        //else
       // {
       //     Toast.makeText(this,"no extras",Toast.LENGTH_SHORT).show();
      //  }

        et=(EditText)findViewById(R.id.editText3);
        String otp_pass= et.getText().toString();

        if(otp_pass.equalsIgnoreCase(otp))
        {
            Toast.makeText(this,"otp verified",Toast.LENGTH_SHORT).show();
            Context context = this;
            Class destActivity = newPass.class;

            Intent intent = new Intent(context, destActivity);
            intent.putExtra(Intent.EXTRA_TEXT,email);
            startActivity(intent);
            finish();
        }
        else
        {
           et.setText("");
            Toast.makeText(this,"invalid",Toast.LENGTH_SHORT).show();
           // finish();
        }
    }
}
