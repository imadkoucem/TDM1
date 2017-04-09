package com.mobile.project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;
    List<MyDocument> list = new ArrayList<>();
    DocumentsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = (ListView) findViewById(R.id.list_view);

        File directory = new File(MainActivity.dirFile);
        File[] files = directory.listFiles();
        for (File tmpf : files){
            //Do something with the files
            list.add(new MyDocument(tmpf.getName(),new Date(tmpf.lastModified()).toString(),tmpf.getAbsolutePath() ));
        }

        adapter = new DocumentsAdapter(HistoryActivity.this,list);
        listView.setAdapter(adapter);

    }

    public void longClick(final int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(list.get(i).getName())
                .setItems(R.array.array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which){
                            case 0:
                                displaypdf(list.get(i).getPath());
                                break;
                            case 1:
                                sendemail(list.get(i).getPath());
                                break;
                        }
                    }
                });
        builder.create();
        builder.show();
    }

    public void sendemail(String path) {

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
    }


}
