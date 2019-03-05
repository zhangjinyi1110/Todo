package com.chinazhang.zjy.todo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM TodoModel")
    List<TodoModel> getTodoList();

    @Insert
    void addTodo(TodoModel todoModel);

    @Delete
    int deleteTodo(TodoModel todoModel);

    @Update
    int updateTodo(TodoModel todoModel);

}
