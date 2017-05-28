package com.mobile.project;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// import com.google.android.gms.location.LocationListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int PLACE_PICKER_REQUEST;
    private LocationManager lm;
    private LocationListener ll;
    private Location location;
    Location l = new Location("a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location newLocation) {
            //your code here
            location = newLocation;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // myRef.setValue("Hello, World!");
        mMap = googleMap;

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MapsActivity.this, "Permission problem!", Toast.LENGTH_SHORT).show();
            return;
        }
        location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) {
            location = new Location("l");
            Toast.makeText(this, "GPS inactif!", Toast.LENGTH_SHORT).show();
        }
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(pos).title("Your position"));
        float zoom = 13;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoom));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("experts");
        myRef.addValueEventListener(new ValueEventListener() {
            private double lat;
            private double lon;
            String fullname;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post

                    lat = Double.parseDouble(postSnapshot.child("locationY").getValue(String.class));
                    lon = Double.parseDouble(postSnapshot.child("locationX").getValue(String.class));
                    l.setLatitude(lat); l.setLongitude(lon);
                    fullname = postSnapshot.child("familyname").getValue(String.class) +" "+ postSnapshot.child("name").getValue(String.class);
                    // String myTopPostsQuery = postSnapshot.child("experts").child("locationX").getClass(String.class);
                    //Toast.makeText(MapsActivity.this, ""+lat, Toast.LENGTH_LONG).show();
                    Marker m = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lon))
                            .title(fullname)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    if (location.distanceTo(l) < 2000){
                        m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


        // Toast.makeText(MapsActivity.this, pos.toString(), Toast.LENGTH_LONG).show();
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

//        LatLng sydney = new LatLng(36.134456, 3.367122);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


}
