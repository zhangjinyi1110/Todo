package com.chinazhang.zjy.todo;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.zjy.simplemodule.base.BaseRepository;

import java.util.List;

public class TodoListRepository extends BaseRepository {

    private final String TAG = getClass().getSimpleName();

    private MutableLiveData<List<TodoModel>> todoListData;
    private TodoDao todoDao;

    @Override
    protected void init() {
        todoDao = MyDatabase.getInstance().getTodoDao();
    }

    public MutableLiveData<List<TodoModel>> getTodoListData() {
        if (todoListData == null)
            todoListData = new MutableLiveData<>();
        return todoListData;
    }

    public void addTodo(TodoModel model) {
        todoDao.addTodo(model);
    }

    public void deleteTodo(TodoModel model) {
        int i = todoDao.deleteTodo(model);
        Log.e(TAG, "deleteTodo: " + i);
    }

    public void updateTodo(TodoModel model) {
        int i = todoDao.updateTodo(model);
        Log.e(TAG, "updateTodo: " + i);
    }

    public void queryTodoList() {
        todoListData.setValue(todoDao.getTodoList());
    }

}
