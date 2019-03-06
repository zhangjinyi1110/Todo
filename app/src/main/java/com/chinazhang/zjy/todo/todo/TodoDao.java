package com.chinazhang.zjy.todo.todo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM TodoModel")
    Flowable<List<TodoModel>> getTodoList();

    @Insert
    long addTodo(TodoModel todoModel);

    @Delete
    void deleteTodo(TodoModel todoModel);

    @Update
    int updateTodo(TodoModel todoModel);

    @Query("SELECT * FROM TodoModel WHERE id=:id")
    Flowable<TodoModel> queryTodo(long id);

}
