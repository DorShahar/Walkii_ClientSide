package com.example.alonsiwek.demomap;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

/**
 * Created by dor on 1/11/2017.
 * This class is the Fragment calls of the MapActivity.
 */

public class MapActivityFrag extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    Boolean mIsRunning_atMAF;

    private static int MAP_ZOOM_RATE = 30;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.activity_maps, null, false);

        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            Log.e(MapActivityFrag.class.toString(),"error to display map: " + e.toString());
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // for display my current location at the map (without marker)
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));

                Log.d("MapActivityFrag","location is: " + location.toString());

                if (location != null) {
                    double myLat = location.getLatitude();
                    double myLong = location.getLongitude();

                    Log.d("MapActivityFrag","lat: " + myLat);
                    Log.d("MapActivityFrag","long: " + myLong);

                    // For zooming automatically to my location
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(myLat, myLong), MAP_ZOOM_RATE);
                    mMap.animateCamera(cameraUpdate);
                }
            }
        });

        ImageButton finish = (ImageButton)rootView.findViewById(R.id.finish_btn);

        ////////////// Part of UPDATE DB ////////////////////////////////

        /* update DB only when mIsRunning_atMAF == false.
         * update DB only when mIsRunning == true  will be with btn_go button.
         */

        finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mIsRunning_atMAF = false;

                Log.d("MapActivityFrag","mIsRunning_atMAF :" + mIsRunning_atMAF.toString());

                if (mIsRunning_atMAF == false) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MainPageFrag.updateRunningState(mIsRunning_atMAF);
                            } catch (IOException e) {
                                Log.e(MapActivityFrag.class.toString(), e.toString());
                                e.printStackTrace();
                                return;
                            }
                        }
                    }).start();
                }
            }
        });

        new DisplayRunnersOnMap(getActivity(),rootView,R.id.runners_list).execute();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}
