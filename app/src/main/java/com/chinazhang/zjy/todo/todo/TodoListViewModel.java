package com.chinazhang.zjy.todo.todo;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.chinazhang.zjy.todo.utils.TimeUtils;
import com.zjy.simplemodule.base.BaseViewModel;

import java.util.List;

public class TodoListViewModel extends BaseViewModel<TodoListRepository> {

    private MutableLiveData<List<TodoModel>> todoListData;
    private MutableLiveData<TodoModel> queryData;
    private MutableLiveData<List<TimeModel>> timeModels;

    public TodoListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<TodoModel>> getTodoListData() {
        if (todoListData == null)
            todoListData = repository.getTodoListData();
        return todoListData;
    }

    public MutableLiveData<TodoModel> getQueryData() {
        if (queryData == null)
            queryData = repository.getQueryData();
        return queryData;
    }

    public MutableLiveData<List<TimeModel>> getTimeList() {
        if (timeModels == null)
            timeModels = repository.getTimeModels();
        return timeModels;
    }

    public void queryTimeList() {
        repository.queryTimeList();
    }

    public boolean addDate(long time) {
        String date = TimeUtils.long2string(time);
        List<TimeModel> list = timeModels.getValue();
        if (list != null) {
            for (TimeModel model : list) {
                if (model.getDate().equals(date)) {
                    return false;
                }
            }
        }
        repository.addDate(date);
        return true;
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

    public void queryTodoList(String date) {
        repository.queryTodoList(TimeUtils.string2long(date), TimeUtils.string2long(date) + 24 * 60 * 60 * 1000 - 1);
    }

    public void queryTodo(long id) {
        repository.queryTodo(id);
    }

}
