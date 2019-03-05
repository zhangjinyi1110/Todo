package com.chinazhang.zjy.todo;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zjy.simplemodule.base.BaseViewModel;

import java.util.List;

public class TodoListViewModel extends BaseViewModel<TodoListRepository> {

    private MutableLiveData<List<TodoModel>> todoListData;

    public TodoListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<TodoModel>> getTodoListData() {
        if (todoListData == null)
            todoListData = repository.getTodoListData();
        return todoListData;
    }

    public void addTodo(TodoModel model) {
        repository.addTodo(model);
    }

    public void deleteTodo(TodoModel model) {
        repository.deleteTodo(model);
    }

    public void updateTodo(TodoModel model) {
        repository.updateTodo(model);
    }

    public void queryTodoList() {
        repository.queryTodoList();
    }

}
