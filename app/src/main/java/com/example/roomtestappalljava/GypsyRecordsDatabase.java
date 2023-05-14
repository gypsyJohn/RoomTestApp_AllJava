package com.example.roomtestappalljava;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.roomtestappalljava.DBClasses.FieldDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: 03/05/2023 Figure out exportSchema
@Database(entities = {FieldDBO.class}, version = 1, exportSchema = false)
public abstract class GypsyRecordsDatabase extends RoomDatabase {
    public abstract FieldDAO fieldDAO();

    private static volatile GypsyRecordsDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static GypsyRecordsDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (GypsyRecordsDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GypsyRecordsDatabase.class,"gypsy_records_database").build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
//            databaseWriteExecutor.execute(() -> {
//                // Populate the database in the background.
//                // If you want to start with more words, just add them.
//                WordDao dao = INSTANCE.wordDao();
//                dao.deleteAll();
//
//                Word word = new Word("Hello");
//                dao.insert(word);
//                word = new Word("World");
//                dao.insert(word);
//            });
        }
    };


}
