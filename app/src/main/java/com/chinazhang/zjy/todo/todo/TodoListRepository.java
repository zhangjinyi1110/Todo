package com.chinazhang.zjy.todo.todo;

import android.arch.lifecycle.MutableLiveData;

import com.chinazhang.zjy.todo.MyDatabase;
import com.chinazhang.zjy.todo.utils.DBFlowableUtils;
import com.chinazhang.zjy.todo.utils.DBSimpleSubscribe;
import com.chinazhang.zjy.todo.utils.DBSubscribe;
import com.chinazhang.zjy.todo.utils.TimeUtils;
import com.zjy.simplemodule.base.BaseRepository;

import java.util.List;

public class TodoListRepository extends BaseRepository {

    private final String TAG = getClass().getSimpleName();

    private MutableLiveData<List<TodoModel>> todoListData;
    private MutableLiveData<TodoModel> queryData;
    private MutableLiveData<List<TimeModel>> timeModels;
    private TodoDao todoDao;
    private TimeDao timeDao;

    @Override
    protected void init() {
        todoDao = MyDatabase.getInstance().getTodoDao();
        timeDao = MyDatabase.getInstance().getTimeDao();
    }

    public MutableLiveData<List<TodoModel>> getTodoListData() {
        if (todoListData == null)
            todoListData = new MutableLiveData<>();
        return todoListData;
    }

    public MutableLiveData<List<TimeModel>> getTimeModels() {
        if (timeModels == null)
            timeModels = new MutableLiveData<>();
        return timeModels;
    }

    public MutableLiveData<TodoModel> getQueryData() {
        if (queryData == null)
            queryData = new MutableLiveData<>();
        return queryData;
    }

    public void queryTimeList() {
        DBFlowableUtils.query(timeDao.getTimeList(), new DBSimpleSubscribe<List<TimeModel>>() {
            @Override
            public void onNext(List<TimeModel> timeModels) {
                TodoListRepository.this.timeModels.postValue(timeModels);
            }
        });
    }

    public void addDate(String date) {
        DBFlowableUtils.getResult(date, new DBSubscribe<String, Long>() {
            @Override
            public Long value(String s) {
                return timeDao.addTime(new TimeModel(s));
            }
        });
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

    public void queryTodoList(long startTime, long endTime) {
        DBFlowableUtils.query(todoDao.getTodoList(startTime, endTime), new DBSimpleSubscribe<List<TodoModel>>() {
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
