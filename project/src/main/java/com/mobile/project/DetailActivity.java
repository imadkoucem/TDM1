package com.mobile.project;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import model.Folder;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final int pos = getIntent().getIntExtra("id",0);

        Folder folder = HistoryActivity.list.get(pos);

        Toast.makeText(this,folder.getId(),Toast.LENGTH_LONG).show();

        TextView tv1 = (TextView)findViewById(R.id.detail_amount);
        tv1.setText(folder.getAmount()+"");
        TextView tv2 = (TextView)findViewById(R.id.detail_date);
        tv2.setText(folder.getDate());
        TextView tv3 = (TextView)findViewById(R.id.detail_id);
        tv3.setText(folder.getId());
        TextView tv4 = (TextView)findViewById(R.id.detail_state);
        tv4.setText(folder.getState());

        ImageView img = (ImageView)findViewById(R.id.detail_img);
        StorageReference mStorageRef =
                FirebaseStorage.getInstance().getReference("photos").child(HistoryActivity.list.get(pos).getId());

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .into(img);

        final VideoView vid = (VideoView)findViewById(R.id.detail_video);
        StorageReference storageRef2 = FirebaseStorage.getInstance().getReference("videos");
        storageRef2.child(HistoryActivity.list.get(pos).getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                vid.setVideoURI(uri);
                vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        vid.start();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(DetailActivity.this,"failed to Doanload the video",Toast.LENGTH_LONG).show();
            }
        });

        TextView tv5 = (TextView)findViewById(R.id.detail_tp_name);
        tv5.setText(folder.getOpponent().getName());
        TextView tv6 = (TextView)findViewById(R.id.detail_tp_address);
        tv6.setText(folder.getOpponent().getAdress());
        TextView tv7 = (TextView)findViewById(R.id.detail_tp_phone);
        tv7.setText(folder.getOpponent().getPhone());
        TextView tv8 = (TextView)findViewById(R.id.detail_tp_licence);
        tv8.setText(folder.getOpponent().getLicenceNumber());

        try{



        }catch (Exception e){
            e.getStackTrace();
            Log.i("DetailActivity",e.toString());
            Toast.makeText(this,"exeption",Toast.LENGTH_LONG).show();
        }



    }
}
