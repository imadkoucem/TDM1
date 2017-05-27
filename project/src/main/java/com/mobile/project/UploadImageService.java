package com.mobile.project;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import model.Data;


public class UploadImageService extends IntentService {
    private DatabaseReference databaseReference;

    Notification noti1,noti2;
    NotificationManager notificationManager1,notificationManager2;
    Notification.Builder builder1,builder2;
    Integer notificationID1 = 1001;  Integer notificationID2 = 1002;

    public UploadImageService() {
        super("UploadImageService");
        databaseReference = FirebaseDatabase.getInstance().getReference("folders");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i("imad","onHandleIntent");

        if (intent.getStringExtra("task").equals("photo")){
            Log.i("imad","start");
            PendingIntent pIntent = PendingIntent.getService(this, (int) System.currentTimeMillis(), intent, 0);
            builder1 = new Notification.Builder(this)
                    .setContentTitle("Uploading picture...")
                    //.setContentText("Notification when uploading")
                    .setSmallIcon(android.R.drawable.ic_menu_upload)
                    .setContentIntent(pIntent).setAutoCancel(true)
                    .setOngoing(true)
                    .setProgress(100,0,false)
                    .setAutoCancel(true);
            noti1 = builder1.build();
            notificationManager1 = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager1.notify(notificationID1, noti1);
            uploadPicture();
        }
        if (intent.getStringExtra("task").equals("video")){
            Log.i("imad","start uploading video");
            PendingIntent pIntent = PendingIntent.getService(this, (int) System.currentTimeMillis(), intent, 0);
            builder2 = new Notification.Builder(this)
                    .setContentTitle("Uploading video...")
                    //.setContentText("Notification when uploading")
                    .setSmallIcon(android.R.drawable.ic_menu_upload)
                    .setContentIntent(pIntent).setAutoCancel(true)
                    .setOngoing(true)
                    .setProgress(100,0,false)
                    .setAutoCancel(true);
            noti2 = builder2.build();
            notificationManager2 = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager2.notify(notificationID2, noti2);
            uploadVideo();
        }

    }


    private void uploadVideo() {
        if (Data.videoUri!=null){
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("videos");
            UploadTask uploadTask = storageRef.child(Data.fileName).putFile(Data.videoUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    //Toast.makeText(MainActivity.this,"failed to add the video",Toast.LENGTH_LONG).show();
                    Log.e("imad",exception.toString());

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    //Toast.makeText(MainActivity.this,"video added with succes",Toast.LENGTH_LONG).show();
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Data.folder.setVideo(downloadUrl.toString());
                    //p.setOldPhoto(downloadUrl.toString());

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred()*8) / taskSnapshot.getTotalByteCount()*8;
                    Log.i("imad","getBytesTransferred() is  " +taskSnapshot.getBytesTransferred() + " bytes");
                    int currentprogress = (int) progress+1;
                    //progressBar.setProgress(currentprogress);
                    builder2.setProgress(100,currentprogress,false);
                    builder2.setContentTitle("Uploading video "+"("+currentprogress+"%)");
                    Log.i("imad","("+currentprogress+"%)Progress");
                    noti2 = builder2.build();
                    notificationManager2.notify(notificationID2, noti2);

                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Log.i("imad","complete");
                    builder2.setProgress(100,100,false);
                    builder2.setContentTitle("Video uploaded with success");
                    builder2.setSmallIcon(android.R.drawable.stat_sys_upload_done);
                    noti2 = builder2.build();
                    notificationManager2.notify(notificationID2, noti2);
                }
            });;
        }
    }

    private void uploadData() {
        databaseReference.child(Data.fileName).setValue(Data.folder);
        Toast.makeText(this,"Saved", Toast.LENGTH_LONG).show();
    }

    private void uploadPicture() {

        int bytes = 0;
        try {
           // bytes = inputToSize(oldInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final int bytes_ = bytes;

        if (Data.pictureUri!=null){
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("photos");
            UploadTask uploadTask = storageRef.child(Data.fileName).putFile(Data.pictureUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    //Toast.makeText(MainActivity.this,"failed to add the picture",Toast.LENGTH_LONG).show();
                    Log.e("imad",exception.toString());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    //Toast.makeText(MainActivity.this,"Picture added with succes",Toast.LENGTH_LONG).show();
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Data.folder.setPicture(downloadUrl.toString());
                    //p.setOldPhoto(downloadUrl.toString());
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred()*8) / taskSnapshot.getTotalByteCount()*8;
                    Log.i("imad","getBytesTransferred() is  " +taskSnapshot.getBytesTransferred() + " bytes");
                    int currentprogress = (int) progress+1;
                    //progressBar.setProgress(currentprogress);
                    builder1.setProgress(100,currentprogress,false);
                    builder1.setContentTitle("Uploading image "+"("+currentprogress+"%)");
                    Log.i("imad","("+currentprogress+"%)Progress");
                    noti1 = builder1.build();
                    notificationManager1.notify(notificationID1, noti1);

                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Log.i("imad","complete");
                    builder1.setProgress(100,100,false);
                    builder1.setContentTitle("Image uploaded with success");
                    builder1.setSmallIcon(android.R.drawable.stat_sys_upload_done);
                    noti1 = builder1.build();
                    notificationManager1.notify(notificationID1, noti1);
                }
            });
        }
    }
}
