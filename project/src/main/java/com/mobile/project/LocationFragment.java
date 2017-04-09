package com.mobile.project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationFragment extends Fragment {

    TextView tv;
    private MapView mapView;
    private Button button;

    double lon;
    double lat;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        tv = (TextView) view.findViewById(R.id.adress);
        mapView = (MapView) view.findViewById(R.id.mapView);
        button = (Button) view.findViewById(R.id.btn_location);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                LocationManager locationManager;
                MyLocationListner myLocationListner = new MyLocationListner();
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                        getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, myLocationListner);
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Location getLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        boolean enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
// check if enabled and if not send user to the GSP settings
// Better solution would be to display a dialog and suggesting to
// go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        else {
            /*String provider = locationManager.getBestProvider(new Criteria(), true);

            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location location = locationManager.getLastKnownLocation(provider);
            tv.setText(location.getLatitude()+" "+location.getLongitude());
            return location;*/
        }
        return null;
    }

    public void setLocationName(Location location){
        try {

            Geocoder geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
            List<Address> addresses = null;

                addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.isEmpty()) {
                    tv.setText("Waiting for Location...");
                }
                else {
                    if (addresses.size() > 0) {
                        tv.setText(addresses.get(0).getFeatureName() + ", "
                                + addresses.get(0).getLocality() +", "
                                + addresses.get(0).getAdminArea()
                                + ", " + addresses.get(0).getCountryName());
                    }
                }

        }catch (Exception e){}

    }

    private class MyLocationListner implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            if(location !=null){
                lon = location.getLongitude();
                lat = location.getLatitude();
                tv.setText(lon+" "+lat);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }
}
