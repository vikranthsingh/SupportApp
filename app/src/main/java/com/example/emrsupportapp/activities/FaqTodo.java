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

    @ColumnInfo(name = "created_time")
    String createdTime;

    @ColumnInfo(name = "imagesUrl")
    String imagesUrl;

    @ColumnInfo(name = "videoUrl")
    String videoUrl;

    @ColumnInfo(name = "module_type")
    String moduleType;
    public FaqTodo() {
    }

    public FaqTodo(String title, String description, String createdDate, String createdTime, String imagesUrl, String videoUrl, String moduleType) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.imagesUrl = imagesUrl;
        this.videoUrl = videoUrl;
        this.moduleType = moduleType;
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

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    @Override
    public String toString() {
        return "FaqTodo{" +
                "uid=" + uid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", imagesUrl='" + imagesUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", moduleType='" + moduleType + '\'' +
                '}';
    }
}
