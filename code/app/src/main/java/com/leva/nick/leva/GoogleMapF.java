package com.leva.nick.leva;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;


public class GoogleMapF extends Fragment implements LocationListener {

    private static final String KEY_TITLE = "map";
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    private MapView mapView;
    private GoogleMap map;
    private LocationManager mLocationManager;
    private Location mLocation;

    private HashMap<Marker, SpotsMarker> mMarkersHashMap;
    private ArrayList<SpotsMarker> mMyMarkersArray;

    private MapView mMapView;
    private ImageView mImageView;
    private Image image;

    private boolean mediaIsOn;
    private  String selectedSpotName;

    public static GoogleMapF newInstance(CharSequence title, ArrayList<SpotsMarker> markersArray) {

        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);
        bundle.putParcelableArrayList("myMarker", markersArray);

        GoogleMapF fragment = new GoogleMapF();
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

        mMyMarkersArray = getArguments().getParcelableArrayList("myMarker");
        mediaIsOn = false;
        selectedSpotName = "";
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

        setUpMap();
        plotSpotsMarkers(mMyMarkersArray);

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


    private void setUpMap() {

        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);

        if (MapsInitializer.initialize(this.getActivity()) != ConnectionResult.SUCCESS)
            Toast.makeText(getActivity().getApplicationContext(), "Connection à Google Map impossible", Toast.LENGTH_SHORT).show();
        // break if true


        mImageView = (ImageView) getView().findViewById(R.id.imageView);
        mMapView = (MapView) getView().findViewById(R.id.map);

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {



                if (mediaIsOn && marker.getTitle().compareTo(selectedSpotName) == 0) {

                    map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

                    ImageView imageView = (ImageView) getView().findViewById(R.id.imageView);

                    LinearLayout.LayoutParams paramView = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1f);

                    LinearLayout.LayoutParams paramMap = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT, 0f);

                    mapView.setLayoutParams(paramMap);
                    imageView.setLayoutParams(paramView);

                    mediaIsOn = false;
                    selectedSpotName = "";

                } else {

                    map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

                    ImageView imageView = (ImageView) getView().findViewById(R.id.imageView);

                    LinearLayout.LayoutParams paramView = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT, 0.2f);

  /*              ValueAnimator animMap = ValueAnimator.ofFloat(1f, 0.2f);
                ValueAnimator animImage = ValueAnimator.ofFloat(0f, 0.8f);
                AnimatorSet set = new AnimatorSet();

                animMap.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float val = (float) animation.getAnimatedValue();
                        mMapView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, val));

                        mMapView.requestLayout();
                    }
                });

                animImage.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {

                        float val = (float) animation.getAnimatedValue();
                        mImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, val));

                        mImageView.requestLayout();
                    }
                });

                set.setDuration(500);
                set.playTogether(animMap, animImage);
                set.start();*/

                    LinearLayout.LayoutParams paramMap = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT, 0.8f);

                    mapView.setLayoutParams(paramMap);
                    imageView.setLayoutParams(paramView);

                    SpotsMarker spotTemp = mMarkersHashMap.get(marker);
                    if (spotTemp.getmImage() == null) {
                        Drawable resImg = getResources().getDrawable(R.drawable.quebec);
                        imageView.setImageDrawable(resImg);
                    }
                    else {
                        Bitmap image = loadImageFromStorage(spotTemp.getmImage());
                        imageView.setImageBitmap(image);
                    }
                    selectedSpotName = marker.getTitle();
                    mediaIsOn = true;
                }

                return true;
            }


        });
    }


    private void plotSpotsMarkers(ArrayList<SpotsMarker> markers)
    {

        mMarkersHashMap = new HashMap<Marker, SpotsMarker>();

        if(markers.size() > 0)
        {
            for (SpotsMarker myMarker : markers)
            {
                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(myMarker.getmLatLng());
                markerOption.title(myMarker.getmLabel());

                int id = getActivity().getResources().getIdentifier(myMarker.getmIcon(), "drawable", getActivity().getPackageName());
                markerOption.icon(BitmapDescriptorFactory.fromResource(id));

                Marker currentMarker = map.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                map.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }
        }
    }

    public void addImageToSpots(String image) {

        Location locationA = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locationA == null)
            locationA = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location locationB = new Location("point B");

        float minDistance = 999999999;
        int markerIndex = -1;

        for (int counter = 0; counter < mMyMarkersArray.size(); counter++)
        {
            SpotsMarker myMarker = mMyMarkersArray.get(counter);
            LatLng b = myMarker.getmLatLng();
            locationB.setLongitude(b.longitude);
            locationB.setLatitude(b.latitude);

            float distanceTemp = locationA.distanceTo(locationB);
            if (distanceTemp < minDistance && distanceTemp < 1000) { // 1 km pour tester
                minDistance = distanceTemp;
                markerIndex = counter;
            }
        }
        mMyMarkersArray.get(markerIndex).setmImage(image);
}


    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        public MarkerInfoWindowAdapter()
        {
        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            marker.hideInfoWindow();
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {

          View v  = getActivity().getLayoutInflater().inflate(R.layout.spots_infos, null);

/*            SpotsMarker myMarker = mMarkersHashMap.get(marker);

            ImageView markerIcon = (ImageView) v.findViewById(R.id.info_picture);
            int id = getActivity().getResources().getIdentifier(myMarker.getmPicture(), "drawable", getActivity().getPackageName());

            markerIcon.setImageResource(id);

            LatLng temp = map.getProjection().getVisibleRegion().nearLeft;

            CameraUpdate  cameraUpdate = CameraUpdateFactory.newLatLng(temp);;
            map.moveCamera(cameraUpdate);
            map.animateCamera(cameraUpdate);*/

           return v;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        map.animateCamera(cameraUpdate);
        mLocationManager.removeUpdates(this);
    }

    private void centerMapOnMyLocation() {

        Location locationA = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locationA == null) {
            locationA = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (locationA == null) {
                LatLng myLocation = new LatLng(46.80283, -71.22465);
                locationA.setLatitude(myLocation.latitude);
                locationA.setLongitude(myLocation.longitude);
            }
        }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationA.getLatitude(), locationA.getLongitude()), 20));
    }

    private Bitmap loadImageFromStorage(String path)
    {
        File imgFile = new File(path);
        if (imgFile.exists())
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        else
            return null;
    }

}


