package com.example.roomtestappalljava;

import android.graphics.Path;

import java.util.ArrayList;

public class SavedFieldOnscreenDataClass {
    ArrayList<double[]> metrePostionsOfFieldBoundaryPoints;
    int fieldID;
    double mostNortherlyPointInMetres;
    double mostSoutherlyPointInMetres;
    double mostEasterlyPointInMetres;
    double mostWesterlyPointInMetres;
    Path fieldOutlinePath;

    public SavedFieldOnscreenDataClass(ArrayList<double[]> metrePostionsOfFieldBoundaryPoints, int fieldID, double mostNortherlyPoint, double mostSoutherlyPoint, double mostEasterlyPoint, double mostWesterlyPoint) {
        this.metrePostionsOfFieldBoundaryPoints = metrePostionsOfFieldBoundaryPoints;
        this.fieldID = fieldID;
        this.mostNortherlyPointInMetres = mostNortherlyPoint;
        this.mostSoutherlyPointInMetres = mostSoutherlyPoint;
        this.mostEasterlyPointInMetres = mostEasterlyPoint;
        this.mostWesterlyPointInMetres = mostWesterlyPoint;
    }
}
