package com.example.emrsupportapp.activities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "faq_table")
public class FaqTodo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    int uid;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "created_date")
    String createdDate;

    /*@ColumnInfo(name = "imagesUrl")
    String imagesUrl;

    @ColumnInfo(name = "videoUrl")
    String videoUrl;*/

    public FaqTodo() {
    }

    public FaqTodo(String title, String description, String createdDate) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "FaqTodo{" +
                "\nuid=" + uid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
