package com.example.roomtestappalljava.DBClasses;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roomtestappalljava.DBClasses.FieldDAO;
import com.example.roomtestappalljava.FieldDBO;
import com.example.roomtestappalljava.GypsyRecordsDatabase;

import java.util.List;

public class GypsyRepository {
    private FieldDAO mFieldDao;
    private LiveData<List<FieldDBO>> mAllFields;

    public GypsyRepository(Application application){
        GypsyRecordsDatabase db = GypsyRecordsDatabase.getDatabase(application);
        mFieldDao = db.fieldDAO();
        mAllFields = mFieldDao.getAllFieldDBOs();
    }

    public LiveData<List<FieldDBO>> getAllFields(){
        return mAllFields;
    }

    public void insert(FieldDBO fieldDBO){
        GypsyRecordsDatabase.databaseWriteExecutor.execute(()->{
            mFieldDao.insertFieldDBO(fieldDBO);
        });
    }

    public void insertAllFields(List<FieldDBO> fieldDBOList){
        GypsyRecordsDatabase.databaseWriteExecutor.execute(()->{
            mFieldDao.insertAllFieldDBOs(fieldDBOList.toArray(new FieldDBO[0]));
        });
    }

}
