package com.mobile.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;

import model.Data;
import model.Folder;

public class EditActivity extends AppCompatActivity {



    int CAMERA_REQUEST = 1888;
    static final int REQUEST_VIDEO_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);



        final int pos = getIntent().getIntExtra("id",0);

        final Folder folder = HistoryActivity.list.get(pos);

        Toast.makeText(this,folder.getId(),Toast.LENGTH_LONG).show();


        TextView tv2 = (TextView)findViewById(R.id.edit_date);
        folder.setDate(tv2.getText().toString());
        TextView tv3 = (TextView)findViewById(R.id.edit_id);
        folder.setId(tv3.getText().toString());
        folder.setPicture(tv3.getText().toString());
        folder.setVideo(tv3.getText().toString());

        Button edit = (Button)findViewById(R.id.edit_);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadPic();

                uploadVid();

                FirebaseDatabase.getInstance().getReference("folders/imd")
                        .setValue(folder);
                Toast.makeText(EditActivity.this,"update with success",Toast.LENGTH_LONG).show();

            }
        });

        Button img = (Button)findViewById(R.id.edit_btn_img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Data.fileName.equals("")){
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else {
                    Toast.makeText(EditActivity.this,"Set name for folder first !",Toast.LENGTH_LONG).show();
                }

            }
        });


        final Button vid = (Button)findViewById(R.id.edit_btn_vid);
        vid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(EditActivity.this.getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                }

            }
        });


        TextView tv5 = (TextView)findViewById(R.id.edit_tp_name);
        folder.getOpponent().setName(tv5.getText().toString());
        TextView tv6 = (TextView)findViewById(R.id.edit_tp_address);
        folder.getOpponent().setAdress(tv6.getText().toString());
        TextView tv7 = (TextView)findViewById(R.id.edit_tp_phone);
        folder.getOpponent().setPhone(tv7.getText().toString());
        TextView tv8 = (TextView)findViewById(R.id.edit_tp_licence);
        folder.getOpponent().setLicenceNumber(tv8.getText().toString());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == Activity.RESULT_OK) {

            Data.videoUri = intent.getData();

            try {
                Data.videoIS = this.getContentResolver().openInputStream(intent.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Data.pictureUri = intent.getData();

            try {
                Data.pictureIS = this.getContentResolver().openInputStream(intent.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadPic() {
        if (Data.pictureUri!=null){
            Intent intent = new Intent(EditActivity.this, UploadImageService.class);
            intent.putExtra("task","photo");
            EditActivity.this.startService(intent);
        }
    }

    private void uploadVid() {
        if (Data.videoUri!=null){
            Intent intent = new Intent(EditActivity.this, UploadImageService.class);
            intent.putExtra("task","video");
            EditActivity.this.startService(intent);
        }
    }

    public void edit(View v){

    }
}
