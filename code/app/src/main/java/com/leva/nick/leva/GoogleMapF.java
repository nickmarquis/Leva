package com.leva.nick.leva;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class GoogleMapF extends Fragment implements LocationListener {

    private static final String KEY_TITLE = "map";
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    private MapView mapView;
    private GoogleMap map;
    private LocationManager mLocationManager;
    private Location mLocation;

    public static GoogleMapF newInstance(CharSequence title) {

        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);

        GoogleMapF fragment = new GoogleMapF();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        Bundle args = getArguments();

        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);


        if (MapsInitializer.initialize(this.getActivity()) != ConnectionResult.SUCCESS)
            Toast.makeText(getActivity().getApplicationContext(), "Connection à Google Map impossible", Toast.LENGTH_SHORT).show();
// break if true

        centerMapOnMyLocation();

        LatLng blaxtonPos = new LatLng(46.80283, -71.22465);
        //map.addMarker(new MarkerOptions().position(blaxtonPos).icon(BitmapDescriptorFactory.fromResource(R.drawable.yourmarkericon)));
        map.addMarker(new MarkerOptions().position(blaxtonPos));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(blaxtonPos, 14);
        map.moveCamera(cameraUpdate);
        map.animateCamera(cameraUpdate);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        map.animateCamera(cameraUpdate);
        mLocationManager.removeUpdates(this);
    }

    private void centerMapOnMyLocation() {

        map.setMyLocationEnabled(true);

        mLocation = map.getMyLocation();
        LatLng myLocation = new LatLng(46.80283, -71.22465);

        if (mLocation != null) {
            myLocation = new LatLng(mLocation.getLatitude(),
                    mLocation.getLongitude());
        }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                20));
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }

}


