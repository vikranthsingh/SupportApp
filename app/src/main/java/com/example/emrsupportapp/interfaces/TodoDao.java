package com.example.emrsupportapp.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.emrsupportapp.activities.FaqTodo;
import com.example.emrsupportapp.activities.TrainingTodo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insertTodo(FaqTodo todo);

    @Insert
    void insertTodoTraining(TrainingTodo trainingTodo);

    @Query("SELECT * FROM faq_table")
    List<FaqTodo> getAllTitleList();

    @Query("SELECT * FROM training_table")
    List<TrainingTodo> getTrainingTitleList();

    @Query("SELECT * FROM faq_table WHERE created_date between :fromDate AND :toDate")
    List<FaqTodo> getDatesList(String fromDate, String toDate);

    @Query("SELECT * FROM training_table WHERE created_date between :fromDate AND :toDate")
    List<TrainingTodo> getTrainingDatesList(String fromDate, String toDate);

    /*@Query("SELECT * FROM faq_table WHERE uid LIKE :uid")
    FaqTodo getTodoById(int uid);*/

}
