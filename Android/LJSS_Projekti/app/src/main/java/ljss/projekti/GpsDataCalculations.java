package ljss.projekti;

import java.lang.Math;

/**
 * Created by Jarkko on 11.2.2017.
 */

public class GpsDataCalculations {
    // Maximum distance km
    Double distanceTraveled = 0.0;
    // Distancw between two points
    Double distanceBetweenPoints = 0.0;
    // speed km/h
    Double speed = 0.0;

    public void calculateDistance(Double latDeg1,
                                  Double latDeg2,
                                  Double longDeg1,
                                  Double longDeg2) {
        //////////////////////////////////////////////////////////////////////////
        // Calculate distance between two points
        Double latRad1 = Math.toRadians(latDeg1);
        Double latRad2 = Math.toRadians(latDeg2);
        Double longRad1 = Math.toRadians(longDeg1);
        Double longRad2 = Math.toRadians(longDeg2);

        distanceBetweenPoints = Math.acos(
                Math.sin(latRad1) * Math.sin(latRad2)
                + Math.cos(latRad1) * Math.cos(latRad2)
                * Math.cos(longRad2 - longRad1)) * 6371;

        distanceTraveled += distanceBetweenPoints;

    }

    public Double getDistanceTraveled() {
        return  distanceTraveled;
    }

    public void calculateSpeed(int time) {
        speed = distanceTraveled / time * 3600;
    }

    public Double getSpeed() {
        return speed;
    }
}
