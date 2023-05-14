package com.example.roomtestappalljava;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roomtestappalljava.DBClasses.GypsyRepository;

import java.util.ArrayList;

public class FarmMapViewActivity extends AppCompatActivity {
    private GypsyRepository gypsyRepository;
    private static final String TAG = "FarmMapViewActivity";
    FieldsExpandableListAdapter fieldsExpandableListAdapter;
    ArrayList<FieldDBO> listOfFieldDBOs = new ArrayList<>();
    ExpandableListView fieldsExpandableListView;
    FarmMapView farmMapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farm_map_view_activity_layout);
        farmMapView = findViewById(R.id.farm_map_view);
        gypsyRepository = new GypsyRepository(getApplication());
        gypsyRepository.getAllFields().observe(this,fieldDBOList -> {
            farmMapView.setListOfFields((ArrayList<FieldDBO>) fieldDBOList);
            listOfFieldDBOs = new ArrayList<>(fieldDBOList);
            fieldsExpandableListAdapter = new FieldsExpandableListAdapter(this, listOfFieldDBOs);
            fieldsExpandableListView.setAdapter(fieldsExpandableListAdapter);
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        fieldsExpandableListView = findViewById(R.id.field_expandable_list_view);

        // This method is called when the group is expanded
        fieldsExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),  " List Expanded.", Toast.LENGTH_SHORT).show();
            }
        });

        // This method is called when the group is collapsed
        fieldsExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), " List Collapsed.", Toast.LENGTH_SHORT).show();
            }
        });
        
        fieldsExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(FarmMapViewActivity.this, ""+listOfFieldDBOs.get(childPosition).getFieldName(), Toast.LENGTH_SHORT).show();
                farmMapView.displaySingleFieldInSingleFieldSelectionBoundingrect(listOfFieldDBOs.get(childPosition).getFieldID());
                return false;
            }
        });
    }
}
