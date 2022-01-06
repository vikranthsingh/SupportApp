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

    @Query("SELECT * FROM faq_table WHERE created_date between :fromDate AND :toDate")  //This will work
    //@Query("SELECT * FROM faq_table WHERE strftime('%Y-%m-%d',created_date) between :fromDate AND :toDate")  //This will work
    List<FaqTodo> getDatesList(String fromDate, String toDate); // from date and ...is this what you want ? Ha ill pass the from Date from here

    /*@Query("SELECT * FROM faq_table")
    List<FaqTodo> getAllDateList(String fromDate, String toDate);*/     //2 to get data between fromDate and todate i wrote this query

    @Query("SELECT * FROM faq_table WHERE uid LIKE :uid")
    FaqTodo getTodoById(int uid);

}
