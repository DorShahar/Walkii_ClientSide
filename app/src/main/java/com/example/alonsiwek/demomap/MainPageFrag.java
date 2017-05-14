package com.example.alonsiwek.demomap;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by dor on 1/11/2017.
 * This class is the Fragment calls of the main screen
 */

public class MainPageFrag extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_screen_frag, null);

        // get the widgets reference from Fragment XML layout
        Button btn_go = (Button) view.findViewById(R.id.go_walking_btn);

///////////////   part of toast //////////////////////////////

        // Set a click listener for Fragment button
        btn_go.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                this.sendLocationOnClick(v);

                // Get the application context
                Toast toast = new Toast(getContext());
                // Set the Toast display position layout center
                toast.setGravity(Gravity.CENTER, 0, 0);

                LayoutInflater inflater = getLayoutInflater(savedInstanceState);
                View layout = inflater.inflate(R.layout.go_massage_toast, null);

                // Set the Toast duration
                toast.setDuration(Toast.LENGTH_LONG);

                // Set the Toast custom layout
                toast.setView(layout);
                toast.show();
            }
         ///////////////  END part of toast //////////////////////////////

          /////////////////part of send location to server //////////////////////

            public void sendLocationOnClick(View v) {
                double latitude;
                double longitude;
                Location location = null;
                LocationManager locationManager = (LocationManager) getContext()
                        .getSystemService(Context.LOCATION_SERVICE);

                // getting GPS status
                Boolean isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // getting network status
                Boolean isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                } else {
                    if (isNetworkEnabled) {
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {

                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {

                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();

                                }
                            }
                        }
                    }
                }
            }
        });

    /////////// Make Get request ////////////////////
//    new GetUsersLocation().execute
//            ("https://walkiii.herokuapp.com/api/locationStatus/search_users_around_me");

    return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}



