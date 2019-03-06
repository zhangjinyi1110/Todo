package com.chinazhang.zjy.todo.todo;

import android.arch.lifecycle.MutableLiveData;

import com.chinazhang.zjy.todo.MyDatabase;
import com.chinazhang.zjy.todo.utils.DBFlowableUtils;
import com.chinazhang.zjy.todo.utils.DBSimpleSubscribe;
import com.chinazhang.zjy.todo.utils.DBSubscribe;
import com.zjy.simplemodule.base.BaseRepository;

import java.util.List;

public class TodoListRepository extends BaseRepository {

    private final String TAG = getClass().getSimpleName();

    private MutableLiveData<List<TodoModel>> todoListData;
    private MutableLiveData<TodoModel> queryData;
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

    public MutableLiveData<TodoModel> getQueryData() {
        if (queryData == null)
            queryData = new MutableLiveData<>();
        return queryData;
    }

    public void addTodo(TodoModel model) {
        DBFlowableUtils.getResult(model, new DBSubscribe<TodoModel, Long>() {
            @Override
            public Long value(TodoModel todoModel) {
                return todoDao.addTodo(todoModel);
            }
        });
    }

    public void deleteTodo(TodoModel model) {
        DBFlowableUtils.getResult(model, new DBSubscribe<TodoModel, Void>() {
            @Override
            public Void value(TodoModel todoModel) {
                todoDao.deleteTodo(todoModel);
                return null;
            }
        });
    }

    public void updateTodo(TodoModel model) {
        DBFlowableUtils.getResult(model, new DBSubscribe<TodoModel, Integer>() {
            @Override
            public Integer value(TodoModel todoModel) {
                return todoDao.updateTodo(todoModel);
            }
        });
    }

    public void queryTodoList() {
        DBFlowableUtils.query(todoDao.getTodoList(), new DBSimpleSubscribe<List<TodoModel>>() {
            @Override
            public void onNext(List<TodoModel> todoModels) {
                todoListData.postValue(todoModels);
            }
        });
    }

    public void queryTodo(long id) {
        DBFlowableUtils.query(todoDao.queryTodo(id), new DBSimpleSubscribe<TodoModel>() {
            @Override
            public void onNext(TodoModel todoModel) {
                queryData.postValue(todoModel);
            }
        });
    }

}
