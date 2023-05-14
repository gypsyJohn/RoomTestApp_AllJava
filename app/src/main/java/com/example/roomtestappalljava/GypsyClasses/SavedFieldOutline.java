package com.example.roomtestappalljava.GypsyClasses;

import android.graphics.Bitmap;

import com.example.roomtestappalljava.GypsyClasses.LatLngAlt;

import java.util.ArrayList;

public class SavedFieldOutline {
    private String fieldName;
    public ArrayList<LatLngAlt> listOfLatLngs = new ArrayList<>();
    private double sizeOfField;//I think this is in metres squared?
    public int sideDimensionOfBitmapInPixels = 110; //This is less than the full cardview size which is 150 atm
    Bitmap outlineOfFieldBitmap;
    int sfoID;//Unique incremental id number for the field outline


    public SavedFieldOutline() {
    }

    public SavedFieldOutline(String fieldName, double sizeOfField){
        this.fieldName = fieldName;
        this.sizeOfField = sizeOfField;
    }

    void addOutlinePoint(LatLngAlt latLngOfPoint){
        listOfLatLngs.add(latLngOfPoint);
    }

    void removeLastPointOfField(){
        if(listOfLatLngs.size() > 0) {
            listOfLatLngs.remove(listOfLatLngs.size() - 1);
        }
    }





    public String getFieldName() {
        return fieldName;
    }

    public ArrayList<LatLngAlt> getListOfLatLngs() {
        return listOfLatLngs;
    }

    public void setListOfLatLngs(ArrayList<LatLngAlt> listOfLatLngs) {
        this.listOfLatLngs = listOfLatLngs;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setSizeOfField(double sizeOfField) {
        this.sizeOfField = sizeOfField;
    }

    public void setSideDimensionOfBitmapInPixels(int sideDimensionOfBitmapInPixels) {
        this.sideDimensionOfBitmapInPixels = sideDimensionOfBitmapInPixels;
    }

    public void setOutlineOfFieldBitmap(Bitmap outlineOfFieldBitmap) {
        this.outlineOfFieldBitmap = outlineOfFieldBitmap;
    }

    public void setSfoID(int sfoID) {
        this.sfoID = sfoID;
    }

    public int getSfoID() {
        return sfoID;
    }

    public double getSizeOfField() {
        return sizeOfField;
    }


}


