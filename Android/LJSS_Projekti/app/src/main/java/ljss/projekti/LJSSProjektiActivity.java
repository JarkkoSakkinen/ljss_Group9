package ljss.projekti;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LJSSProjektiActivity extends Activity {



    //Following code is used for a timer to run the code specified in Runnable R -> Start(), every 1 second.
    Timer timer;
    myTimerTask timerTask;

    // Following is used for calculations
    GpsDataCalculations calculations;
    Double latitude;
    Double latitude1;
    Double latitude2;
    Double longitude;
    Double longitude1;
    Double longitude2;
    Double altitude;

    // GPS class
    GPS gps;

    // Database connection class
    connectDB database;

    //Following variables are used to control textView & editText objects.
    TextView latitudeView;
    TextView longitudeView;
    TextView altitudeView;
    TextView timeView;
    TextView distanceTraveledView;
    TextView currentSpeedView;
    EditText fsView;

    // Following variables are used to set sampling frequency. AKA rescheduling timer from default 10seconds.
    int fs;
    Double tmp;

    // Seconds since start
    int secondsSinceStart;

    private static final int MY_PERMISSION_REQUESTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ljssprojekti);

        // Following code is to control textView objects.
        latitudeView = (TextView) findViewById(R.id.LatitudeView);
        longitudeView = (TextView) findViewById(R.id.LongitudeView);
        altitudeView = (TextView) findViewById(R.id.AltitudeView);
        timeView = (TextView) findViewById(R.id.TimeView);
        distanceTraveledView = (TextView) findViewById(R.id.distanceTraveledView);
        currentSpeedView = (TextView) findViewById(R.id.currentSpeedView);
        fsView = (EditText) findViewById(R.id.fsView);

        // Following code is needed to grant permissions for location services.
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

        // Following class is used to get GPS data
        gps = new GPS(this.getApplicationContext());

        // Following class is used to do calculations with GPS data
        calculations = new GpsDataCalculations();

        // Following class is used to upload data to database
        database = new connectDB(this.getApplicationContext());

        // Creating timer with default (10second) value.
        getFS();
        timerTask = new myTimerTask();
        //Used to run code every 10sec (10000 ms).
        timer = new Timer();
        timer.schedule(timerTask, 0, fs);

        // Reschedules the timer when user clicks "Done" button when editing fsView.
        fsView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    getFS();
                    reschduleTimer();
                    hideKeyboard(fsView);
                    return true;
                }
                else {
                    return false;
                }
            }
        });

        }

    // Get the value of fsView textView, and convert it into int (ms)
    public void getFS() {
        tmp = Double.parseDouble(fsView.getText().toString());
        if (tmp == null || tmp == 0){
            tmp = 10.0;
            fsView.setText("10");
        }
            tmp = tmp * 1000;
            fs = tmp.intValue();
    }

    // Stops the timerTask & timer. and schedules(creates) it again. getFs() should be called before using this method.
    public void reschduleTimer() {
        timerTask.cancel();
        timer.cancel();
        timer.purge();

        timer = new Timer();
        timerTask = new myTimerTask();
        timer.schedule(timerTask, 0, fs);
    }

    // Following function is needed for granting location permissions.
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

    // Code to hide keyboard. ( in this project this is used when user clicks "Done" button while editing fsView.
    private void hideKeyboard(EditText editText)
    {
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    //The following code is ran every second or a specified internval.
    final Runnable r = new Runnable()
    {
        @Override
        public void run() {

            secondsSinceStart += fs;

            // Get current location
            gps.getLocation();

            // Get latitude value of current location and display it.
            latitude = gps.getLatitude();

            // Current and previous values are needed to calculate distance between points.
            if ( latitude1 == null) {
                latitude1 = latitude;
            } else if ( latitude2 == null) {
                latitude2 = latitude;
            } else if ( latitude2 != null){
                latitude1 = latitude2;
                latitude2 = latitude;
            }

            latitudeView.setText(latitude.toString());

            // Get longitude value of current location and display it.
            longitude = gps.getLongitude();

            // Current and previous values are needed to calculate distance between points.
            if ( longitude1 == null) {
                longitude1 = longitude;
            } else if ( longitude2 == null) {
                longitude2 = longitude;
            } else if ( longitude2 != null){
                longitude1 = longitude2;
                longitude2 = longitude;
            }

            longitudeView.setText(longitude.toString());

            // Get altitude value of current location and display it.
            altitude = gps.getAltitude();
            altitudeView.setText(altitude.toString());

            // Create a timestamp for current location and display it.
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String millisInString  = dateFormat.format(new Date());
            timeView.setText(millisInString);

            if ( longitude2 != null && latitude2 != null) {

                calculations.calculateDistance(
                        latitude1, latitude2,
                        longitude1, longitude2);
                distanceTraveledView.setText(calculations.getDistanceTraveled().toString());

                calculations.calculateSpeed(secondsSinceStart);
                currentSpeedView.setText(calculations.getSpeed().toString());

                // Upload data to database
                database.insertData(
                        longitude,
                        latitude,
                        altitude,
                        calculations.getDistanceTraveled(),
                        calculations.getSpeed());
            }
        }
    };

    public class myTimerTask extends TimerTask {
        @Override
        public void run()
        {
            //runOnUiThread is needed to run code handling Ui elements.
            runOnUiThread(r);
        }
    }
}




