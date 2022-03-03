package com.example.emrsupportapp.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.emrsupportapp.activities.FaqTodo;
import com.example.emrsupportapp.activities.TicketTodo;
import com.example.emrsupportapp.activities.TrainingTodo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insertTodo(FaqTodo todo);

    @Insert
    void insertTodoTraining(TrainingTodo trainingTodo);

    @Insert
    void insertTodoTicket(TicketTodo ticketTodo);

    @Query("SELECT * FROM faq_table")
    List<FaqTodo> getAllTitleList();

    @Query("SELECT * FROM training_table")
    List<TrainingTodo> getTrainingTitleList();

    @Query("SELECT * FROM ticket_table")
    List<TicketTodo> getAllTitleListTicket();

    @Query("SELECT * FROM faq_table WHERE created_date between :fromDate AND :toDate")
    List<FaqTodo> getDatesList(String fromDate, String toDate);

    @Query("SELECT * FROM training_table WHERE created_date between :fromDate AND :toDate")
    List<TrainingTodo> getTrainingDatesList(String fromDate, String toDate);

    @Query("SELECT * FROM ticket_table WHERE created_date between :fromDate AND :toDate")
    List<TicketTodo> getDatesListTicket(String fromDate, String toDate);

    @Query("SELECT * FROM ticket_table WHERE uid LIKE :uid")
    TicketTodo getTodoById(int uid);

    @Update
    void updateTodo(TicketTodo todo);

    @Query("SELECT * FROM ticket_table WHERE ticket_status LIKE :status")
    List<TicketTodo> getStatusList(String status);

}
