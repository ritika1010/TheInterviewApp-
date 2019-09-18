package com.example.theinterviewapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class interview extends AppCompatActivity {
DatabaseHelper myDB;
    String var,ques;
    int id,ques_time;
    int ques_no=1;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);
        myDB = new DatabaseHelper(this);
        ques = myDB.search_ques(ques_no);
        tv=findViewById(R.id.Question);
        tv.setText(ques);
        ques_no++;

    }

    public void record_int(View view)
    {
        if(ques_no<=4) {

            ques_time = myDB.search_ques_time(ques_no-1);
            Intent curr = getIntent();
            var = curr.getStringExtra("name");
            id=curr.getExtras().getInt("id");
     
            dispatchTakeVideoIntent();
        }
        else
        {
            finish();
        }
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;
    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            takeVideoIntent.putExtra("android.intent.extra.durationLimit", ques_time);
            takeVideoIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
            takeVideoIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            takeVideoIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            takeVideoIntent.putExtra("EXTRA_VIDEO_QUALITY", 0);
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            //VideoView videoView=findViewById(R.id.videoView);
            // videoView.setVideoURI(videoUri);

            String path=saveToInternalStorage(videoUri);
            if(path==null)
            {
                Toast.makeText(this,"no path mentioned",Toast.LENGTH_SHORT).show();
                return;
            }
            else {

                myDB.on_video(path, var);
                Toast.makeText(this, "Interview added", Toast.LENGTH_SHORT).show();
                Cursor cursor = MediaStore.Video.query(getContentResolver(),intent.getData(),
                        new String[] { MediaStore.Video.VideoColumns.DURATION });
                System.out.println(">>>>>>>>>>"+cursor.getCount());
                cursor.moveToFirst();

                String duration = cursor.getString(cursor.getColumnIndex("duration"));
                //Toast.makeText(this, duration, Toast.LENGTH_LONG).show();
                int time_taken=parseInt(duration)/1000;

                myDB.make_record(id,ques_no-1,ques_time,time_taken);
                if(ques_no<=3) {
                    ques = myDB.search_ques(ques_no);
                    tv = findViewById(R.id.Question);
                    tv.setText(ques);
                    ques_no++;
                }
                else
                {
                    finish();
                }
                //finish();
            }


        }
    }

    File directory=null;
    private String saveToInternalStorage(Uri videouri) {

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/videoDir

        directory = cw.getDir(var, Context.MODE_PRIVATE);

        File mypath = new File(directory, ques_no-1 + ".mp4");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            //  bitmapImage.compress(Bitmap.CompressFormat.W, 100, fos);
            Toast.makeText(this, "video saved", Toast.LENGTH_SHORT).show();
        }

            catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return directory.getAbsolutePath();
    }



}





