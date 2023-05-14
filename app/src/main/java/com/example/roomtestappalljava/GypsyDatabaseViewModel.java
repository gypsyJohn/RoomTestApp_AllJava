package com.example.roomtestappalljava;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roomtestappalljava.DBClasses.GypsyRepository;

import java.util.List;

public class GypsyDatabaseViewModel extends AndroidViewModel {

    private GypsyRepository gypsyRepository;

    private final LiveData<List<FieldDBO>> allFields;

    public GypsyDatabaseViewModel(Application application){
        super(application);
        gypsyRepository = new GypsyRepository(application);
        allFields = gypsyRepository.getAllFields();
    }

    LiveData<List<FieldDBO>> getAllFields(){return allFields;}

    public void insert(FieldDBO fieldDBO){gypsyRepository.insert(fieldDBO);}

    public void insertAllFields(List<FieldDBO> listOfFields){gypsyRepository.insertAllFields(listOfFields);}
}
