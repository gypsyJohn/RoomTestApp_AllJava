package com.example.roomtestappalljava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.example.roomtestappalljava.DBClasses.GypsyRepository;
import com.example.roomtestappalljava.GypsyClasses.SavedFieldOutline;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<SavedFieldOutline> listOfSavedFieldOutlines;
    RecyclerView recyclerView;
    GypsyRepository gypsyRepository;
    private LiveData<List<FieldDBO>> liveListOfFields;
    private GypsyDatabaseViewModel gypsyDatabaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        final FieldAdapter fieldAdapter = new FieldAdapter(new FieldAdapter.FieldDiff());
        recyclerView.setAdapter(fieldAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gypsyDatabaseViewModel = new ViewModelProvider(this).get(GypsyDatabaseViewModel.class);
        gypsyDatabaseViewModel.getAllFields().observe(this, fieldDBOList -> fieldAdapter.submitList(fieldDBOList));

        CountDownTimer timer = new CountDownTimer(10000, 5000) {
            int i = 0;
            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                Toast.makeText(MainActivity.this, "" + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Finished" + i, Toast.LENGTH_SHORT).show();
                addFieldsToDatabaseFromFile();
            }
        }.start();

    }

    public void addFieldsToDatabaseFromFile(){
        getFieldDataFromFile();

        if(listOfSavedFieldOutlines == null){
            Toast.makeText(this, "Null found", Toast.LENGTH_SHORT).show();
            return;
        }

        List<FieldDBO> fieldDBOs = new ArrayList<>();
        for (SavedFieldOutline sfo : listOfSavedFieldOutlines) {
//            fieldDBOs.add(FieldDBOConstuctionClass.convertSavedFieldOutlineToFieldDBO(sfo,getApplicationContext()));
        }

        gypsyDatabaseViewModel.insertAllFields(fieldDBOs);
    }

    void getFieldDataFromFile(){
        File savedFieldOutlineFile = new File(getFilesDir(),"savedFieldOutlines");
        int length = (int) savedFieldOutlineFile.length();
        byte[] bytes = new byte[length];
        try{
            try(FileInputStream in = new FileInputStream(savedFieldOutlineFile)){
                in.read(bytes);
            }
            String savedFieldOutlinesDataAsString = new String(bytes);
            Gson gsonOfSavedFieldOutlines = new Gson();
            Type type = new TypeToken<ArrayList<SavedFieldOutline>>(){}.getType();
            listOfSavedFieldOutlines = gsonOfSavedFieldOutlines.fromJson(savedFieldOutlinesDataAsString,type);

        }catch(IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Unable to read Saved Field Data", Toast.LENGTH_LONG).show();
        }
    }

    public void goToViewScreen(View view) {
        Intent goToViewScreenIntent = new Intent(this,FarmMapViewActivity.class);
        goToViewScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(goToViewScreenIntent);
    }
}