package com.mobile.project;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private DetailsFragment detailsFragment;
    private LocationFragment locationFragment;
    private PicturesFragment picturesFragment;
    private DamageFragment damageFragment;
    private ViewPager mViewPager;

    public static String dirFile;
    public static String APPLICATION_PACKAGE_NAME ;
    public static String fileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APPLICATION_PACKAGE_NAME = this.getBaseContext().getPackageName();
        dirFile = Environment.getExternalStorageDirectory()+"/"+APPLICATION_PACKAGE_NAME+"/";
        File path = new File( dirFile );
        if ( !path.exists() ){ path.mkdir(); }



        detailsFragment = new DetailsFragment();
        locationFragment = new LocationFragment();
        picturesFragment = new PicturesFragment();
        damageFragment = new DamageFragment();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.action_save:
                if(fileName.equals("")) showFileNamePopUp();
                else new myTask().execute();
                break;

            case R.id.action_visualize:
                if(fileName.equals("")){
                    showMessage("Save document first !");
                }
                else displaypdf();
                break;

            case R.id.action_send:
                showMessage("imadddddddddddddd");
                break;

            case R.id.action_tell_friend:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                startActivity(Intent.createChooser(share, "Tell a friend"));
                break;

            case R.id.action_history:
                startActivity(new Intent(this,HistoryActivity.class));
                break;
            case R.id.action_language:

                break;
            case R.id.action_about:

                break;

            case R.id.action_english:
                setLocale("en");
                break;

            case R.id.action_frensh:
                setLocale("fr");
                break;

        }

        return true;
    }

    private void showFileNamePopUp(){
        LayoutInflater inflater = this.getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_file_name, null);
        final TextView file_name = (TextView) dview.findViewById(  R.id.dialog_file_name_text );

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(!file_name.getText().toString().equals(""))
                            fileName = file_name.getText().toString();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    public void displaypdf() {

        File file =  new File(dirFile+ fileName+ ".pdf");
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

    private void createPDF()  {
        Document doc = new Document();
        String outPath = dirFile + fileName + ".pdf";
        try {
            PdfWriter.getInstance(doc,new FileOutputStream(outPath));
            doc.open();
            doc.add(new Paragraph("Helle World !"));
            doc.close();
        } catch (DocumentException e) {
            e.printStackTrace();
            Log.i("MainActivity",e.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("MainActivity",e.toString());
        }

    }

    private void showMessage(String msg){
        new SweetAlertDialog(this)
                .setTitleText(msg)
                .show();
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

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    protected class myTask extends AsyncTask<String,String,String> {
        SweetAlertDialog pDialog;
        @Override
        protected void onPreExecute() {
            //display ProgressDialog
            pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText(getString(R.string.saving));

            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {

            createPDF();
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.setTitleText(getString(R.string.saved))
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        }
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return detailsFragment;
                case 1: return locationFragment;
                case 2: return picturesFragment;
                case 3: return damageFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.section1);
                case 1:
                    return getString(R.string.section2);
                case 2:
                    return getString(R.string.section3);
                case 3:
                    return getString(R.string.section4);
            }
            return null;
        }
    }
}
