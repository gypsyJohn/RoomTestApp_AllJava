package com.example.roomtestappalljava.GypsyClasses;

import com.example.roomtestappalljava.GypsyClasses.LatLng;

public class LatLngAlt extends LatLng {

    public Integer altitude;//altitude can be set to null in bitmapSpreadingView if there is no data in the bsv altitude field

    public LatLngAlt(double latitude, double longitude) {
        super(latitude, longitude);
    }

    public LatLngAlt(double latitude, double longitude, double altitudeFloat) {
        super(latitude, longitude);
        this.altitude = (int) Math.round(altitudeFloat);
    }

    public LatLngAlt(LatLng latLng){
        super(latLng);
        this.altitude = null;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }
}
