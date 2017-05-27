package com.mobile.project;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Data;
import model.Folder;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;
    List<Folder> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = (ListView) findViewById(R.id.list_view);

        populateListFiles();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                longClick(i);
                return false;
            }
        });

    }

    private void populateListFiles() {
        FirebaseDatabase.getInstance().getReference("folders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Folder i = d.getValue(Folder.class);
                    //if (i.getState().equals("en traitment") || i.getState().equals("ouvert") )
                        list.add(i);

                }
                FoldersAdapter adapter = new FoldersAdapter(HistoryActivity.this,list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void longClick(final int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(list.get(i).getId())
                .setItems(R.array.array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which){
                            case 0:
                                //displaypdf(list.get(i).getPath());
                                break;

                            case 1:
                                //sendemail(list.get(i).getPath());
                                break;
                        }
                    }
                });
        builder.create();
        builder.show();
    }

    public void deleteItem(int position){
        StorageReference mStorageRef =
                FirebaseStorage.getInstance().getReference("photos").child(list.get(position).getId());
        mStorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(HistoryActivity.this,"Picture deleted",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HistoryActivity.this,"Failed to delete picture",Toast.LENGTH_SHORT).show();

            }
        });

        StorageReference mStorageRef2 =
                FirebaseStorage.getInstance().getReference("videos").child(list.get(position).getId());
        mStorageRef2.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(HistoryActivity.this,"Video deleted",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HistoryActivity.this,"Failed to delete video",Toast.LENGTH_SHORT).show();

            }
        });

        FirebaseDatabase.getInstance().getReference("folders").child(list.get(position).getId()).
                removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(HistoryActivity.this,"Folder deleted",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HistoryActivity.this,"Failed to delete folder",Toast.LENGTH_SHORT).show();
            }
        });
    }


}




/*public void sendemail(String path) {

        String emailAddress[] = {""};

        Uri uri = Uri.fromFile(new File(path));

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Accident report");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Text");
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(emailIntent, "Send email using:"));

    }

    public void displaypdf(String path) {

        File file =  new File(path);
        if(file.exists()) {
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(file), "application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent intent = Intent.createChooser(target, "Open File");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
                Toast.makeText(this,"install PDF reader please",Toast.LENGTH_LONG).show();
            }
        }
        else
            Toast.makeText(getApplicationContext(), "File path is incorrect." , Toast.LENGTH_LONG).show();
    }  */