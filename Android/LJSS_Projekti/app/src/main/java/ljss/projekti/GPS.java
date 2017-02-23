package ljss.projekti;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GPS extends Service implements LocationListener {

    public Context mContext;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Location location; // location
    Double latitude; // latitude
    Double longitude; // longitude
    Double altitude;  // altitude
    Long time;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 1; // 1 minute  1000 60 1
    public LocationManager locationManager;

    public GPS(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {

        locationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);
        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        this.canGetLocation = true;

        if (!isNetworkEnabled && !isGPSEnabled)
        {
            CharSequence text = "GPS|Network not Enabled!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(mContext, text, duration);

            toast.show();
        }

        if (isNetworkEnabled) {


            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }


            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);


              if (locationManager != null) {

                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                location.setTime(System.currentTimeMillis());

                if (location != null) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    altitude = location.getAltitude();
                }
            }
        }

        // if GPS Enabled get lat/long using GPS Services
        if (isGPSEnabled) {

            if (location == null) {

                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                if (locationManager != null) {

                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    location.setTime(System.currentTimeMillis());

                    if (location != null) {

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        altitude = location.getAltitude();
                    }
                }
            }
        }
        return location;
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    public double getAltitude() {
        if(location != null){
            longitude = location.getAltitude();
        }

        // return altitude
        return altitude;
    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = getLatitude();
        longitude = getLongitude();
        altitude = getAltitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}