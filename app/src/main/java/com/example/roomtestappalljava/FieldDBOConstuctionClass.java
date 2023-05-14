package com.example.roomtestappalljava;

import android.content.Context;
import android.widget.Toast;

import com.example.roomtestappalljava.GypsyClasses.LatLngAlt;
import com.example.roomtestappalljava.GypsyClasses.SavedFieldOutline;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FieldDBOConstuctionClass {


    static public FieldDBO convertSavedFieldOutlineToFieldDBO(SavedFieldOutline sfo, Context context){
        FieldDBO fieldDBO = new FieldDBO();

        double[] boundaryPoints = getBoundaryPointsOfSavedField(sfo.listOfLatLngs);

        fieldDBO.setFieldName(sfo.getFieldName());
        fieldDBO.setFieldURI(createFieldDBODataFile(sfo.listOfLatLngs, context));
        fieldDBO.setAreaInMetresSquared(sfo.getSizeOfField());
        fieldDBO.setNorthernMostPointCoordinateString(Double.toString(boundaryPoints[0]));
        fieldDBO.setEasternMostPointCoordinateString(Double.toString(boundaryPoints[1]));
        fieldDBO.setSouthernMostPointCoordinateString(Double.toString(boundaryPoints[2]));
        fieldDBO.setWesternMostPointCoordinateString(Double.toString(boundaryPoints[3]));


        return fieldDBO;
    }


    //Returns most northerly, easterly, southerly, westerly points for the field in that order
    static double[] getBoundaryPointsOfSavedField(ArrayList<LatLngAlt> listOfLatLngs){
        double[] boundaryPoints = new double[4];
        boundaryPoints[0] = listOfLatLngs.get(0).latitude;
        boundaryPoints[1] = listOfLatLngs.get(0).longitude;
        boundaryPoints[2] = listOfLatLngs.get(0).latitude;
        boundaryPoints[3] = listOfLatLngs.get(0).longitude;

        for(LatLngAlt latLngAlt : listOfLatLngs){
            if(latLngAlt.getLatitude() > boundaryPoints[0] ){boundaryPoints[0] = latLngAlt.getLatitude();}
            if(latLngAlt.getLongitude() > boundaryPoints[1]){boundaryPoints[1] = latLngAlt.getLongitude();}
            if(latLngAlt.getLatitude() < boundaryPoints[2]){ boundaryPoints[2] = latLngAlt.getLatitude();}
            if(latLngAlt.getLongitude() < boundaryPoints[3]){boundaryPoints[3] = latLngAlt.getLongitude();}
        }

        return boundaryPoints;
    }

    static String createFieldDBODataFile(ArrayList<LatLngAlt> listOfLatLngs , Context context){
        int savedFileName = (int) (Math.random()*1000000);
        File path = context.getFilesDir();
        SavedFieldRawData savedFieldRawData = new SavedFieldRawData(listOfLatLngs);
        Gson gsonOfDataToBeSaved = new Gson();
        String jsonOfDataToBeSaved = gsonOfDataToBeSaved.toJson(savedFieldRawData);
        String savedFieldDataDirectoryPath = path.getAbsolutePath().concat("/savedfields");
        File savedFieldDataDirectory = new File(savedFieldDataDirectoryPath);
        if(!savedFieldDataDirectory.isDirectory()){
            if(!savedFieldDataDirectory.mkdir()){
                Toast.makeText(context, "Not made", Toast.LENGTH_SHORT).show();
            }
        }

        File file = new File(savedFieldDataDirectoryPath,Integer.toString(savedFileName));
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonOfDataToBeSaved.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return file.getPath();

    }
}
