package com.chinazhang.zjy.todo.todo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.chinazhang.zjy.todo.utils.TimeUtils;

@Entity
public class TimeModel {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String date;
    private long startTime;
    private long endTime;

    public TimeModel(String date) {
        this.date = date;
        startTime = TimeUtils.string2long(date);
        endTime = startTime + 24 * 60 * 60 * 1000 - 1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
