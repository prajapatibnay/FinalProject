package org.example.fohormalai;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


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
        mMap = googleMap;
/*
        // Add a marker in Thimi and move the camera
        LatLng thimi = new LatLng(27.68381755, 85.38984043769214);
        mMap.addMarker(new MarkerOptions().position(thimi).title("Marker in Thimi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(thimi));*/

        LatLng latLng1 = new LatLng(27.68381755, 85.38984043769214);
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(latLng1);
        mMap.clear();
        markerOptions1.title("Current Position");
        markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_marker));
        markerOptions1.getPosition();
        mMap.addMarker(markerOptions1);


        LatLng latLng2 = new LatLng(27.685875600000003, 85.38284751890998);
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(latLng2);
        markerOptions2.title("Current Position");
        markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_marker));
        markerOptions2.getPosition();
        mMap.addMarker(markerOptions2);

        LatLng latLng3 = new LatLng(27.689786, 85.3901234);
        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(latLng3);
        markerOptions3.title("Current Position");
        markerOptions3.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_marker));
        markerOptions3.getPosition();
        mMap.addMarker(markerOptions3);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1,15));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }
}
