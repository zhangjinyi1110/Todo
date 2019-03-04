package com.chinazhang.zjy.todo;

import android.app.Application;
import android.support.annotation.NonNull;

import com.zjy.simplemodule.base.BaseViewModel;
import com.zjy.simplemodule.base.fragment.TodoListRepository;

public class TodoListViewModel extends BaseViewModel<TodoListRepository> {

    public TodoListViewModel(@NonNull Application application) {
        super(application);
    }

}
