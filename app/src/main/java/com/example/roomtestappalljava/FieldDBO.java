package com.example.roomtestappalljava;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fields_table")
public class FieldDBO {
    @PrimaryKey(autoGenerate = true)
    public int fieldID;

    public String fieldName;

    public String fieldURI;

    public int containingFarmDBO;

    public String easternMostPointCoordinateString;

    public String northernMostPointCoordinateString;

    public String westernMostPointCoordinateString;

    public String southernMostPointCoordinateString;

    public double areaInMetresSquared;

    public int getFieldID() {
        return fieldID;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldURI() {
        return fieldURI;
    }

    public void setFieldURI(String fieldURI) {
        this.fieldURI = fieldURI;
    }

    public int getContainingFarmDBO() {
        return containingFarmDBO;
    }

    public void setContainingFarmDBO(int containingFarmDBO) {
        this.containingFarmDBO = containingFarmDBO;
    }

    public String getEasternMostPointCoordinateString() {
        return easternMostPointCoordinateString;
    }

    public void setEasternMostPointCoordinateString(String easternMostPointCoordinateString) {
        this.easternMostPointCoordinateString = easternMostPointCoordinateString;
    }

    public String getNorthernMostPointCoordinateString() {
        return northernMostPointCoordinateString;
    }

    public void setNorthernMostPointCoordinateString(String northernMostPointCoordinateString) {
        this.northernMostPointCoordinateString = northernMostPointCoordinateString;
    }

    public String getWesternMostPointCoordinateString() {
        return westernMostPointCoordinateString;
    }

    public void setWesternMostPointCoordinateString(String westernMostPointCoordinateString) {
        this.westernMostPointCoordinateString = westernMostPointCoordinateString;
    }

    public String getSouthernMostPointCoordinateString() {
        return southernMostPointCoordinateString;
    }

    public void setSouthernMostPointCoordinateString(String southernMostPointCoordinateString) {
        this.southernMostPointCoordinateString = southernMostPointCoordinateString;
    }

    public double getAreaInMetresSquared() {
        return areaInMetresSquared;
    }

    public void setAreaInMetresSquared(double areaInMetresSquared) {
        this.areaInMetresSquared = areaInMetresSquared;
    }
}

