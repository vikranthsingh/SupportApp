package com.example.emrsupportapp.activities;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.emrsupportapp.interfaces.TodoDao;

@Database(entities = {FaqTodo.class, TrainingTodo.class, TicketTodo.class}, version = 3)
public abstract class DatabaseHelper extends RoomDatabase {
    public abstract TodoDao todoDao();
    public static volatile DatabaseHelper INSTANCE;
    public static DatabaseHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (DatabaseHelper.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, "Database.db").build();
                }
            }
        }
        return INSTANCE;
    }
}
