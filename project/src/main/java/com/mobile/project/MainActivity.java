package com.mobile.project;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import model.Casualty;
import model.Data;
import model.Vehicule;
import model.Witness;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private DetailsFragment detailsFragment;
    private LocationFragment locationFragment;
    private PicturesFragment picturesFragment;
    private DamageFragment damageFragment;
    private Fragment3 fragment3;
    private ViewPager mViewPager;
    //AdView mAdView;


    private DatabaseReference databaseReference,myRef ;
    private FirebaseDatabase mFirebaseInstance;

    //public static String dirFile;
    //public static String APPLICATION_PACKAGE_NAME ;
    //public static String fileName = "";

    private static final String TAG = "MainActivity";
    private LocationManager locationManager;
    private Location location = new Location("location");
    private Location nLocation;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public static String id;
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseInstance.setPersistenceEnabled(true);
        databaseReference = mFirebaseInstance.getReference("folders");
        myRef = mFirebaseInstance.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double x = Double.parseDouble(dataSnapshot.child("user2").child("locationX").getValue(String.class));
                double y = Double.parseDouble(dataSnapshot.child("user2").child("locationY").getValue(String.class));
                // Toast.makeText(MainActivity.this, ""+y, Toast.LENGTH_SHORT).show();
                location.setLatitude(y);
                location.setLongitude(x);
                nLocation = location;
                useOldLocation();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location newLocation) {
                // Called when a new location is found by the network location provider.
//                float distance = newLocation.distanceTo(location);
                nLocation = newLocation;
                float distance = newLocation.distanceTo(nLocation);
                Toast.makeText(MainActivity.this, "d: " + distance, Toast.LENGTH_SHORT).show();
                useOldLocation();
//                makeUseOfNewLocation(newLocation);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 100, locationListener);

        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-7447963107930464~6538892439");
       // mAdView = (AdView) findViewById(R.id.adView);
       // AdRequest adRequest = new AdRequest.Builder().build();
       // mAdView.loadAd(adRequest);


        Data.APPLICATION_PACKAGE_NAME = this.getBaseContext().getPackageName();
        Data.dirFile = Environment.getExternalStorageDirectory()+"/"+Data.APPLICATION_PACKAGE_NAME+"/";
        File path = new File( Data.dirFile );
        if ( !path.exists() ){ path.mkdir(); }



        detailsFragment = new DetailsFragment();
        locationFragment = new LocationFragment();
        picturesFragment = new PicturesFragment();
        damageFragment = new DamageFragment();
        fragment3 = new Fragment3();

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


        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(this,refreshedToken,Toast.LENGTH_LONG).show();
        Log.i("token", refreshedToken);


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

            case R.id.action_new_file:
                showFileNamePopUp();
                break;

            case R.id.action_reset:
                reset();
                break;

            case R.id.action_save:
                if(Data.fileName.equals("")) {
                    LayoutInflater inflater = this.getLayoutInflater();
                    View dview = inflater.inflate(R.layout.dialog_file_name, null);
                    final TextView file_name = (TextView) dview.findViewById(  R.id.dialog_file_name_text );

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setView(dview)
                            // Add action buttons
                            .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    if(!file_name.getText().toString().equals("")){
                                        Data.fileName = file_name.getText().toString();
                                        Data.folder.setVideo(file_name.getText().toString());
                                        Data.folder.setPicture(file_name.getText().toString());
                                        Data.folder.setId(file_name.getText().toString());
                                        //createPDF();
                                        Data.isFileSaved = true;

                                        uploadPic();

                                        uploadVid();

                                        FirebaseDatabase.getInstance().getReference("folders")
                                                .child(Data.folder.getId()).setValue(Data.folder);

                                        Toast.makeText(MainActivity.this,"Uploading...",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                    builder.setCancelable(false);
                    builder.show();
                }
                else {
                    uploadPic();

                    uploadVid();
                }
                break;

            case R.id.action_visualize:
                if(!Data.isFileSaved){
                    showMessage("Save document first !");
                }
                else displaypdf();
                break;

            case R.id.action_send:
                if(!Data.isFileSaved){
                    showMessage("Save document first !");
                }
                else sendemail(Data.fileName);
                break;

            case R.id.action_tell_friend:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                startActivity(Intent.createChooser(share, "Tell a friend"));
                break;

            case R.id.action_history:
                startActivity(new Intent(this,HistoryActivity.class));
                break;

            case R.id.action_about:
                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("TDM1 !")
                        .setContentText("KOUCEM Imad\nABADA Abderahman")
                        .setCustomImage(R.drawable.custom_img)
                        .show();
                break;

            case R.id.action_english:
                setLocale("en");
                break;

            case R.id.action_frensh:
                setLocale("fr");
                break;

            case R.id.action_signout:
                signOut();
                break;

            case R.id.action_map:
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
                break;

        }

        return true;
    }

    private void uploadPic() {
        if (Data.pictureUri!=null){
            Intent intent = new Intent(MainActivity.this, UploadImageService.class);
            intent.putExtra("task","photo");
            MainActivity.this.startService(intent);
        }
    }

    private void uploadVid() {
        if (Data.videoUri!=null){
            Intent intent = new Intent(MainActivity.this, UploadImageService.class);
            intent.putExtra("task","video");
            MainActivity.this.startService(intent);
        }
    }


    private void reset() {
        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover saved data !")
                .setCancelText("No,cancel plx!")
                .setConfirmText("Yes,delete it!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance, keep widget user state, reset them if you need
                        sDialog.setTitleText("Cancelled!")
                                .setContentText("Your data is safe :)")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Data.isFileSaved = false;
                        Data.fileName = "";
                        Data.dateTime = "";
                        Data.thirdParty = null;
                        Data.listVehicule = new ArrayList<>();
                        Data.insurance = null;
                        Data.police = null;
                        Data.desciption = "";
                        Data.listCasualties = new ArrayList<>();
                        Data.listWitnesses = new ArrayList<>();
                        Data.capture = null;
                        Data.image = new ArrayList<>();
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Your data has been deleted!")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
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
                            Data.fileName = file_name.getText().toString();
                            Data.folder.setVideo(file_name.getText().toString());
                            Data.folder.setPicture(file_name.getText().toString());
                            Data.folder.setId(file_name.getText().toString());
                            Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    public void displaypdf() {

        File file =  new File(Data.dirFile+ Data.fileName+ ".pdf");
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
        File path = new File( Data.dirFile );
        if ( !path.exists() ){ path.mkdir(); }
        Document doc = new Document();
        String outPath = Data.dirFile + Data.fileName + ".pdf";
        try {
            PdfWriter.getInstance(doc,new FileOutputStream(outPath));
            doc.open();
            if (!Data.dateTime.equals("")) doc.add(new Paragraph(Data.dateTime));
            if (!Data.location.equals("")) doc.add(new Paragraph(Data.location));
            if (!Data.desciption.equals("")) doc.add(new Paragraph(Data.desciption));
            doc.add(new Paragraph(""));
            for (Casualty c :Data.listCasualties) doc.add(new Paragraph(c.toString()));

            for (Witness c :Data.listWitnesses) doc.add(new Paragraph(c.toString()));

            for (Vehicule c :Data.listVehicule) doc.add(new Paragraph(c.toString()));

            if (Data.thirdParty !=null) doc.add(new Paragraph(Data.thirdParty.toString()));

            if (Data.police !=null) doc.add(new Paragraph(Data.police.toString()));

            if (Data.insurance !=null) doc.add(new Paragraph(Data.insurance.toString()));

            if(Data.capture != null) { addImageToDoc( doc, Data.capture); }

            for (Bitmap b :Data.image) addImageToDoc( doc, b);

            doc.close();
        } catch (Exception e) {  e.printStackTrace();  }

    }

    private void addImageToDoc(Document doc, Bitmap bm) {
        Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
        // Create new bitmap based on the size and config of the old
        Bitmap newBitmap = Bitmap.createBitmap(resized.getWidth(), resized.getHeight(), resized.getConfig());

// Instantiate a canvas and prepare it to paint to the new bitmap
        Canvas canvas = new Canvas(newBitmap);

// Paint it white (or whatever color you want)
        canvas.drawColor(Color.WHITE);

// Draw the old bitmap ontop of the new white one
        canvas.drawBitmap(resized, 0, 0, null);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
        Image myImg = null;
        try {
            myImg = Image.getInstance(stream.toByteArray());
            myImg.setAlignment(Image.MIDDLE| Image.TEXTWRAP);
            doc.add(myImg);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"no image !!!",Toast.LENGTH_SHORT).show();
        }

    }

    private void showMessage(String msg){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText(msg)
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /*protected class myTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {

            //createPDF();
            Data.isFileSaved = true;
            uploadData();
            //upload();
            //Data.thirdParty;
            return "";
        }
    }*/


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
                case 3: return fragment3;
                case 4: return damageFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 5;
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
                    return "Videos";
                case 4:
                    return getString(R.string.section4);
            }
            return null;
        }
    }


    @Override
    protected void onPause() {
        //mAdView.pause();
        super.onPause();
    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }

    @Override
    protected void onResume() {
       // mAdView.resume();
        super.onResume();
    }



    public void useOldLocation(){
        if (location.distanceTo(nLocation) > 200){
            sendNewLocation(nLocation);
        }
    }

    public void sendNewLocation(Location location) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://us-central1-poised-receiver-164423.cloudfunctions.net/positionChanged?id="+1+"&x=" + location.getLongitude() +"&y=" + location.getLatitude();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Position sent!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "That didn't work!", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        startActivity(new Intent(MainActivity.this,SignInActivity.class));
                        finish();
                        // [START_EXCLUDE]
                        //updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
}
