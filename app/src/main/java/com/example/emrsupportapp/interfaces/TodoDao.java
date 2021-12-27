package com.example.emrsupportapp.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.emrsupportapp.activities.FaqTodo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insertTodo(FaqTodo todo);

    @Query("SELECT * FROM faq_table")
    List<FaqTodo> getAllTitleList();

    @Query("SELECT * FROM faq_table WHERE uid LIKE :uid")
    FaqTodo getTodoById(int uid);

}
