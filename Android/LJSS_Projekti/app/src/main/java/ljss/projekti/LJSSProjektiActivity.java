package ljss.projekti;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.location.*;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.Manifest;


public class LJSSProjektiActivity extends Activity {

    //Following code is used for a timer to run the code specified in Runnable R -> Start(), every 1 second.
    Timer timer = new Timer();

    //The following code is ran every second.
    final Runnable r = new Runnable()
    {
        @Override
        public void run() {
            //Runnable code goes here
            gps.getLocation();

            Double longitude = gps.getLongitude();
            altView.setText(longitude.toString());

            Double latitude = gps.getLatitude();
            latView.setText(latitude.toString());
        }
    };
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run()
        {
            //runOnUiThread is needed to run code handling Ui elements.
            runOnUiThread(r);
        }
    };

    GPS gps;

    //Following variables are used to control textView objects.
    TextView altView;
    TextView latView;

    private static final int MY_PERMISSION_REQUESTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ljssprojekti);

        //Following code is to control textView objects.
        altView = (TextView) findViewById(R.id.textView2);
        latView = (TextView) findViewById(R.id.textView3);



        if (this.checkSelfPermission(
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            if (this.shouldShowRequestPermissionRationale(
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                this.requestPermissions(
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUESTS
                );
            }
        }

        if (this.checkSelfPermission(
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            if (this.shouldShowRequestPermissionRationale(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {

                this.requestPermissions(
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_REQUESTS
                );

            }
        }




        //Context m = getApplicationContext();
            gps = new GPS(this.getApplicationContext());

            //Location location = new Location();
            //location = gps.getLocation();

            //Used to run code every 1sec (1000 ms).
            timer.schedule(timerTask, 0, 10000);
        }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUESTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.



                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void permissions()
    {
        if (this.checkSelfPermission(
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            if (this.shouldShowRequestPermissionRationale(
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {

                this.requestPermissions(
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUESTS
                );
            }
        }

        if (this.checkSelfPermission(
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            if (this.shouldShowRequestPermissionRationale(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {

                this.requestPermissions(
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_REQUESTS
                );

            }
        }
    }

}
