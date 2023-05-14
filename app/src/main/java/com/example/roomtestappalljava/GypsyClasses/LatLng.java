package com.example.roomtestappalljava.GypsyClasses;
public class LatLng {
    public double latitude;
    public double longitude;
    public LatLng(double latitude, double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public LatLng(LatLng latLng){
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
