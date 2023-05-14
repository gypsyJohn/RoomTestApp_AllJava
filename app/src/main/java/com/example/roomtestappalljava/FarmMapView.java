package com.example.roomtestappalljava;

import static android.view.MotionEvent.INVALID_POINTER_ID;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;

import com.example.roomtestappalljava.GypsyClasses.GPSCoordsClass;
import com.example.roomtestappalljava.GypsyClasses.LatLng;
import com.example.roomtestappalljava.GypsyClasses.LatLngAlt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FarmMapView extends View {
    Context context;
    int width, height;
    ArrayList<FieldDBO> listOfFields;
    double mostNortherlyPoint, mostSouthernlyPoint, mostEasternlyPoint, mostWesterlyPoint;
    GPSCoordsClass gpsOrigin;
    private static final int RADIUS_OF_EARTH_AT_53_DEGREES = 6364900;
    double numberOfUnzoomedPixelsPerMetre;
    Paint fieldOutlinePaint = new Paint();
    ArrayList<SavedFieldOnscreenDataClass> onscreenFields = new ArrayList<>();
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f, xZoomFocusPoint, yZoomFocusPoint;
    private float mLastTouchX, mLastTouchY, mPosY, mPosX;
    private int mActivePointerId = INVALID_POINTER_ID;
    boolean currentlyMoving = false;
    float distanceToScrollX, distanceToScrollY;
    private GestureDetector gesturePanListener;
    int touchPointX, touchPointY, adjustedTapPositionX,adjustedTapPositionY;
    Paint redPaint = new Paint();
    Rect singleFieldSelectionBoundingrect = new Rect(410,10,1270,790);
    double[] centrePointOfSelectedField = new double[2];


    public FarmMapView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
        fieldOutlinePaint.setColor(Color.BLACK);
        fieldOutlinePaint.setStrokeWidth(2f);
        fieldOutlinePaint.setAntiAlias(true);
        fieldOutlinePaint.setStyle(Paint.Style.STROKE);
        fieldOutlinePaint.setTextSize(20f);
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(2f);
        redPaint.setAntiAlias(true);
        redPaint.setStyle(Paint.Style.STROKE);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        xZoomFocusPoint = width/2;
        yZoomFocusPoint = height/2;
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                touchPointX = (int) x;
                touchPointY = (int) y;

                // Remember where we started (for dragging)
                mLastTouchX = x;
                mLastTouchY = y;
                // Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                // Find the index of the active pointer and fetch its position
                final int pointerIndex =
                        MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                currentlyMoving = true;

                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);

                // Calculate the distance moved
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;


                mPosX += dx;
                mPosY += dy;

                invalidate();

                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;
                break;
            }

            case MotionEvent.ACTION_UP:
                if(!currentlyMoving){
                    adjustedTapPositionX = (int) ((ev.getX() - mPosX)/mScaleFactor);
                    adjustedTapPositionY = (int) ((ev.getY() - mPosY)/mScaleFactor);
                }
                currentlyMoving=false;
                break;

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {

                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = MotionEventCompat.getX(ev, newPointerIndex);
                    mLastTouchY = MotionEventCompat.getY(ev, newPointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                }
                break;
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        adjustedTapPositionX = (int) ((touchPointX - mPosX)/(mScaleFactor*mScaleFactor));
//        adjustedTapPositionY = (int) ((touchPointY - mPosY)/(mScaleFactor*mScaleFactor));
//        canvas.drawText("x = " + touchPointX + ", y = " + touchPointY ,50,50,fieldOutlinePaint);
        canvas.drawText("mPosX = " + mPosX + ",",1050,150,fieldOutlinePaint);
        canvas.drawText("mPosY = " + mPosY ,1050,200,fieldOutlinePaint);
//        canvas.drawText("x = " + adjustedTapPositionX + ", y = " + adjustedTapPositionY ,200,50,fieldOutlinePaint);
//        canvas.drawText("sf = " + mScaleFactor,300,50,fieldOutlinePaint);
        canvas.save();
        canvas.translate(mPosX,mPosY);
//        canvas.translate(width/2 - (float) centrePointOfSelectedField[0],(float) (height/2 - centrePointOfSelectedField[1]));
        canvas.drawCircle(width/2, height/2,5,redPaint);
        canvas.scale(mScaleFactor, mScaleFactor,xZoomFocusPoint,yZoomFocusPoint);
        if(!onscreenFields.isEmpty()){
            for (SavedFieldOnscreenDataClass field : onscreenFields ) {
                canvas.drawPath(field.fieldOutlinePath,fieldOutlinePaint);
            }
        }
//        canvas.drawLine(0,0,width,0,fieldOutlinePaint);
//        canvas.drawLine(0,height,width,height,fieldOutlinePaint);
//        canvas.drawLine(0,0,0,height,fieldOutlinePaint);
//        canvas.drawLine(width,0,width,height,fieldOutlinePaint);

        canvas.restore();
//        for (int i = 0; i < 5; i++) {
//            canvas.drawLine((width*i)/4,0,(width*i)/4,height,redPaint);
//
//        }
        canvas.drawRect(singleFieldSelectionBoundingrect,redPaint);
        canvas.drawLine((singleFieldSelectionBoundingrect.left + singleFieldSelectionBoundingrect.right)/2,singleFieldSelectionBoundingrect.top,
                (singleFieldSelectionBoundingrect.left + singleFieldSelectionBoundingrect.right)/2,singleFieldSelectionBoundingrect.bottom,redPaint);
        canvas.drawLine(singleFieldSelectionBoundingrect.left,(singleFieldSelectionBoundingrect.top + singleFieldSelectionBoundingrect.bottom)/2,
                singleFieldSelectionBoundingrect.right,(singleFieldSelectionBoundingrect.top + singleFieldSelectionBoundingrect.bottom)/2,redPaint);
        canvas.drawLine(width/2,0,width/2,height,redPaint);
        canvas.drawCircle((float) centrePointOfSelectedField[0],(float) centrePointOfSelectedField[1],10,redPaint);
    }

    void displaySingleFieldInSingleFieldSelectionBoundingrect(int fieldIdOfSelectedField){
        SavedFieldOnscreenDataClass selectedField = null;
        for (SavedFieldOnscreenDataClass f : onscreenFields) {
            if(f.fieldID == fieldIdOfSelectedField){
                selectedField = f;
                break;
            }
        }
        // TODO: 11/05/2023 Check if this check makes sense, works for arbitrary object
        if(selectedField == null){
            Toast.makeText(context, "Error - Field Not Found", Toast.LENGTH_SHORT).show();
            return;
        }
        double northSouthDistanceOfField = selectedField.mostNortherlyPointInMetres - selectedField.mostSoutherlyPointInMetres;
        double eastWestDistanceOfField = selectedField.mostEasterlyPointInMetres - selectedField.mostWesterlyPointInMetres;
        double northSouthDistanceScale = Math.abs((singleFieldSelectionBoundingrect.top - singleFieldSelectionBoundingrect.bottom) / northSouthDistanceOfField);
        double eastWestDistanceScale = Math.abs((singleFieldSelectionBoundingrect.right - singleFieldSelectionBoundingrect.left) / eastWestDistanceOfField);
        centreAndRescaleMapToFitScreen();
        double numberOfUnscaledPixelsPerMetreToHoldSelectedField = Math.min(northSouthDistanceScale,eastWestDistanceScale);
        double centreOfFieldInUnzoomedPixelsX = (selectedField.mostWesterlyPointInMetres + eastWestDistanceOfField/2)*numberOfUnzoomedPixelsPerMetre;
        double centreOfFieldInUnzoomedPixelsY = (selectedField.mostNortherlyPointInMetres - (northSouthDistanceOfField/2))*numberOfUnzoomedPixelsPerMetre;
        int centreOfSingleFieldSelectionBoundingrectX = (singleFieldSelectionBoundingrect.right + singleFieldSelectionBoundingrect.left)/2;
        int centreOfSingleFieldSelectionBoundingrectY = (singleFieldSelectionBoundingrect.top + singleFieldSelectionBoundingrect.bottom)/2;
        mPosX = (float) (centreOfSingleFieldSelectionBoundingrectX - centreOfFieldInUnzoomedPixelsX);
        mPosY = (float) (centreOfSingleFieldSelectionBoundingrectY - centreOfFieldInUnzoomedPixelsY);
        mScaleFactor = (float) (numberOfUnscaledPixelsPerMetreToHoldSelectedField/numberOfUnzoomedPixelsPerMetre);
        xZoomFocusPoint = (float) (centreOfFieldInUnzoomedPixelsX);
        yZoomFocusPoint = (float) (centreOfFieldInUnzoomedPixelsY);
        centrePointOfSelectedField[0] = centreOfFieldInUnzoomedPixelsX;
        centrePointOfSelectedField[1] = centreOfFieldInUnzoomedPixelsY;
//        centrePointOfSelectedField[0] = selectedField.mostWesterlyPointInMetres * numberOfUnzoomedPixelsPerMetre;
//        centrePointOfSelectedField[1] = selectedField.mostNortherlyPointInMetres*numberOfUnzoomedPixelsPerMetre;
//        mPosX = (float) (selectedField.mostWesterlyPointInMetres * numberOfUnzoomedPixelsPerMetre);
        invalidate();
        // TODO: 11/05/2023 Finish this, must use mposx and mposy because otherwise there is a huge jump as soon as I try and zoom after selecting a field (they are still 0)
    }

    void centreAndRescaleMapToFitScreen(){
        mScaleFactor = 1;
        mPosX = 0;
        mPosY = 0;
    }

    public void setListOfFields(ArrayList<FieldDBO> listOfFields) {
        this.listOfFields = listOfFields;
        setTotalMapBoundary();
        loadFieldOutlinesFromFiles();
        createFieldOutlinePaths();
        invalidate();
    }

    void createFieldOutlinePaths(){
        for(SavedFieldOnscreenDataClass onscreenField : onscreenFields) {
            Path savedFieldPath = new Path();
            for (double[] pointInMetres : onscreenField.metrePostionsOfFieldBoundaryPoints) {
                double[] pointInPixels = new double[]{pointInMetres[0] * numberOfUnzoomedPixelsPerMetre, pointInMetres[1] * numberOfUnzoomedPixelsPerMetre};
                if (savedFieldPath.isEmpty()) {
                    savedFieldPath.moveTo((float) pointInPixels[0], (float) pointInPixels[1]);
                    continue;
                }
                savedFieldPath.lineTo((float) pointInPixels[0], (float) pointInPixels[1]);
            }
            savedFieldPath.close();
            onscreenField.fieldOutlinePath = new Path(savedFieldPath);
        }

    }

    void loadFieldOutlinesFromFiles(){
        for (FieldDBO field : listOfFields) {
            SavedFieldRawData fieldRawData;
            File savedFieldRawDataFile = new File(field.getFieldURI());
            int length = (int) savedFieldRawDataFile.length();
            byte[] fieldDataAsBytes = new byte[length];
            try {
                FileInputStream fis = new FileInputStream(savedFieldRawDataFile);
                fis.read(fieldDataAsBytes);
                String savedFieldRawDataAsString = new String(fieldDataAsBytes);
                Gson gsonOfSavedData = new Gson();
                Type type = new TypeToken<SavedFieldRawData>() {
                }.getType();
                fieldRawData = gsonOfSavedData.fromJson(savedFieldRawDataAsString, type);
                ArrayList<double[]> metrePostionsOfFieldBoundaryPoints = new ArrayList<>();
                for(LatLngAlt latLngAlt : fieldRawData.listOfLatLngs){
                    metrePostionsOfFieldBoundaryPoints.add(coordsToMetres(latLngAlt,gpsOrigin));
                }
                double[] mostNortherlyAndWesterlyPointsInMetres = coordsToMetres(new LatLng(Double.parseDouble(field.getNorthernMostPointCoordinateString()),Double.parseDouble(field.getWesternMostPointCoordinateString())),gpsOrigin);
                double[] mostSoutherlyAndEasterlyPointsInMetres = coordsToMetres(new LatLng(Double.parseDouble(field.getSouthernMostPointCoordinateString()),Double.parseDouble(field.getEasternMostPointCoordinateString())),gpsOrigin);

                onscreenFields.add(new SavedFieldOnscreenDataClass(metrePostionsOfFieldBoundaryPoints,field.getFieldID(),
                        mostNortherlyAndWesterlyPointsInMetres[1],
                        mostSoutherlyAndEasterlyPointsInMetres[1],
                        mostSoutherlyAndEasterlyPointsInMetres[0],
                        mostNortherlyAndWesterlyPointsInMetres[0]
                ));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error Loading Data raw outline data from file", Toast.LENGTH_SHORT).show();
            }

        }
    }

    void setTotalMapBoundary(){
        if(listOfFields.isEmpty()){
            Toast.makeText(context, "EmptyList", Toast.LENGTH_SHORT).show();
            return;
        }
        mostNortherlyPoint = Double.parseDouble(listOfFields.get(0).northernMostPointCoordinateString);
        mostEasternlyPoint = Double.parseDouble(listOfFields.get(0).easternMostPointCoordinateString);
        mostSouthernlyPoint = Double.parseDouble(listOfFields.get(0).southernMostPointCoordinateString);
        mostWesterlyPoint = Double.parseDouble(listOfFields.get(0).westernMostPointCoordinateString);

        for (FieldDBO field : listOfFields ) {
            mostNortherlyPoint = Math.max(Double.parseDouble(field.getNorthernMostPointCoordinateString()), mostNortherlyPoint);
            mostEasternlyPoint = Math.max(Double.parseDouble(field.easternMostPointCoordinateString), mostEasternlyPoint);
            mostSouthernlyPoint = Math.min(Double.parseDouble(field.getSouthernMostPointCoordinateString()), mostSouthernlyPoint);
            mostWesterlyPoint = Math.min(Double.parseDouble(field.westernMostPointCoordinateString),mostWesterlyPoint);
        }
        gpsOrigin = new GPSCoordsClass(mostNortherlyPoint,mostWesterlyPoint);
        findUnzoomedScaleOfMap();
    }

    void findUnzoomedScaleOfMap(){
        double[] mostNortherlyAndWesterlyPoints = coordsToMetres(new LatLng(mostNortherlyPoint,mostWesterlyPoint),gpsOrigin);
        double[] mostSoutherlyAndEasterlyPoints = coordsToMetres(new LatLng(mostSouthernlyPoint,mostEasternlyPoint),gpsOrigin);

        double northSouthDistance = mostNortherlyAndWesterlyPoints[1] - mostSoutherlyAndEasterlyPoints[1];
        double eastWestDistance = mostSoutherlyAndEasterlyPoints[0] - mostNortherlyAndWesterlyPoints[0];

        double northSouthDistanceScale = Math.abs(height / northSouthDistance);
        double eastWestDistanceScale = Math.abs(width / eastWestDistance);

        numberOfUnzoomedPixelsPerMetre =  Math.min(northSouthDistanceScale,eastWestDistanceScale);
    }

    //Returns data in the form of Lng, Lat NOT Lat, Lng
    private double[] coordsToMetres(LatLng coordsToConvert, GPSCoordsClass coordsOfOrigin) {
        //These are in degrees...
        double dTheta = coordsToConvert.latitude - coordsOfOrigin.getLatitude();
        double dPhi = coordsToConvert.longitude - coordsOfOrigin.getLongitude();

        //...so I need to convert to radians. Duh.
        double metresInLatitude = -1 * RADIUS_OF_EARTH_AT_53_DEGREES * Math.toRadians(dTheta);
        double metresInLongitude = RADIUS_OF_EARTH_AT_53_DEGREES * Math.cos(Math.toRadians(coordsToConvert.latitude)) * Math.toRadians(dPhi);

        return new double[] {metresInLongitude,metresInLatitude};
    }





    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            xZoomFocusPoint = detector.getFocusX();
            yZoomFocusPoint = detector.getFocusY();
            invalidate();
            return true;
        }
    }


}

