package com.chinazhang.zjy.todo.todo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TimeDao {

    @Query("SELECT * FROM TimeModel")
    Flowable<List<TimeModel>> getTimeList();

    @Insert
    Long addTime(TimeModel model);
}
