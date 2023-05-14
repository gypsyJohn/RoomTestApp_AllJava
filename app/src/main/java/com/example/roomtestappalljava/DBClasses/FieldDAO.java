package com.example.roomtestappalljava.DBClasses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomtestappalljava.FieldDBO;

import java.util.List;

@Dao
public interface FieldDAO {

    @Query("SELECT * FROM fields_table")
    LiveData<List<FieldDBO>> getAllFieldDBOs();

    @Insert
    void insertAllFieldDBOs(FieldDBO... fieldDBOS);

    @Insert
    void insertFieldDBO(FieldDBO fieldDBO);

//    Matches primary key to know which row to change
    //Returns number of rows that have been changed
    @Update
    int updateFieldDBO(FieldDBO fieldDBO);


//    Matches primary key to know which row to change
    //Returns number of rows that have been deleted
    @Delete
    int deleteFieldDBO(FieldDBO fieldDBO);


}
