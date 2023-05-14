package com.example.roomtestappalljava.GypsyClasses;

public class GPSCoordsClass {
    private int id;

    private String timeOfFix;
    private double latitude;
    private double longitude;
    private double speed;
    private double trackAngle;
    private String date;
    private Double altitude;
    private int numberOfSatellites;

    public GPSCoordsClass(String timeOfFix, String latitude, String orientationLatitude,
                          String longitude, String orientationLongitude, String speed, String trackAngle, String date) {
        this.timeOfFix = timeOfFix;

        if (orientationLatitude.equals("N")) {
            this.latitude = Double.parseDouble(latitude);
        }else if(orientationLatitude.equals("S")){
            this.latitude = Double.parseDouble(latitude) * -1;
        }

        if (orientationLongitude.equals("E")) {
            this.longitude = Double.parseDouble(longitude);
        }else if(orientationLongitude.equals("W")){
            this.longitude = Double.parseDouble(longitude) * -1;
        }
        if (!speed.equals("")) {
            this.speed = Double.parseDouble(speed);
        }else{
            this.speed = 0.0;
        }

        if(!trackAngle.equals("")) {
            this.trackAngle = Double.parseDouble(trackAngle);
        }else{
            this.trackAngle = 0.0;//This needs to be changed, only shows because no satellite data being obtained
        }
        this.date = date;

    }

    public GPSCoordsClass(String timeOfFix, String latitude, String orientationLatitude, String longitude, String orientationLongitude, String altitude, String numberOfSatellites){
        this.timeOfFix = timeOfFix;

        if (orientationLatitude.equals("N")) {
            this.latitude = Double.parseDouble(latitude);
        }else if(orientationLatitude.equals("S")){
            this.latitude = Double.parseDouble(latitude) * -1;
        }

        if (orientationLongitude.equals("E")) {
            this.longitude = Double.parseDouble(longitude);
        }else if(orientationLongitude.equals("W")){
            this.longitude = Double.parseDouble(longitude) * -1;
        }
        if(!altitude.equals("")){
            this.altitude = Double.parseDouble(altitude);
        }
        if(!numberOfSatellites.equals("")) {
            this.numberOfSatellites = Integer.parseInt(numberOfSatellites);
        }
    }


    public GPSCoordsClass(double latitude, double longitude) {
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public GPSCoordsClass(GPSCoordsClass gps) {
        this.latitude = gps.getLatitude();
        this.id = gps.getId();
        this.longitude = gps.getLongitude();
        this.timeOfFix = gps.getTimeOfFix();
        this.speed = gps.getSpeed();
        this.trackAngle = gps.getTrackAngle();
        this.date = gps.getDate();
    }


    private int getId() {
        return id;
    }

    public String getTimeOfFix() {
        return timeOfFix;
    }

    public double getTimeOfFixDouble(){
        return Double.parseDouble(timeOfFix);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    double getSpeed() {
        return speed;
    }

    public double getTrackAngle() {
        return trackAngle;
    }

    String getDate() {
        return date;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setTimeOfFix(String timeOfFix) {
        this.timeOfFix = timeOfFix;
    }

    public void setLatitude(String latitude,String orientationLatitude) {
        if (orientationLatitude.equals("N")) {
            this.latitude = Double.parseDouble(latitude);
        }else if(orientationLatitude.equals("S")){
            this.latitude = Double.parseDouble(latitude) * -1;
        }
    }

    public void setLongitude(String longitude,String orientationLongitude) {
        if (orientationLongitude.equals("E")) {
            this.longitude = Double.parseDouble(longitude);
        }else if(orientationLongitude.equals("W")){
            this.longitude = Double.parseDouble(longitude) * -1;
        }
    }

    public void setSpeed(String speed) {
        this.speed = Double.parseDouble(speed);
    }

    public void setTrackAngle(String trackAngle) {
        this.trackAngle = Double.parseDouble(trackAngle);
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setTrackAngle(double trackAngle) {
        this.trackAngle = trackAngle;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
