package com.mobile.project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import model.Data;


public class LocationFragment extends Fragment {

    View view = null;

    private Button button;

    private TextView tv;

    private double latitude;
    private double longitude;
    private LocationManager lm;
    private GoogleMap map;

    private LinearLayout myLayout;


    public LocationFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve location and camera position from saved instance state.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_location, container, false);
        }

        tv = (TextView) view.findViewById(R.id.tv_location);
        myLayout = (LinearLayout) view.findViewById(R.id.fragment_location);
        button = (Button) view.findViewById(R.id.btn_location);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isNetworkAvailable(getActivity())){
                    lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    boolean isLocationEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    if(isLocationEnabled){
                        new loadLocationTask().execute();
                    }
                    else showSettingsAlert();
                }
                else {
                    showSnackBarMessage("No internet connection !");
                }



            }
        });

        ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
            }
        });

        return view;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Use location ?");

        // Setting Dialog Message
        alertDialog.setMessage("Location is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.location);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getActivity().startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private class loadLocationTask extends AsyncTask<String,String,String> {
        SweetAlertDialog pDialog;
        List<Address> addresses = null;
        @Override
        protected void onPreExecute() {
            //display ProgressDialog
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText(getString(R.string.serching));
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {

            if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {

                showSnackBarMessage("Permissions non granted !");
                return "";
            }
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location !=null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());

                try {
                    addresses = gcd.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {  e.printStackTrace(); }
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            if (addresses!=null) tv.setText(
                    addresses.get(0).getAdminArea()
                    +", "+addresses.get(0).getLocality()
                    +", "+addresses.get(0).getSubLocality() );
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
            CameraUpdate zoom   = CameraUpdateFactory.zoomTo(15);
            map.moveCamera(center);
            map.animateCamera(zoom);

            map.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude, longitude)) );
            Data.location = tv.getText().toString();
            pDialog.dismiss();
        }
    }

    public void showSnackBarMessage(String str){
        Snackbar snackbar = Snackbar
                .make(myLayout, str, Snackbar.LENGTH_SHORT);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

}
