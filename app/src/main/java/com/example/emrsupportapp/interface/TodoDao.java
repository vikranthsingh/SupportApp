package com.example.emrsupportapp.in;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insertTodo(FaqTodo todo);

    @Query("SELECT title FROM faq_table")
    List<FaqTodo> getAllTileList();

}
