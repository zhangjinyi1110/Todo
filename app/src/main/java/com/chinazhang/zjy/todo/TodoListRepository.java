package com.chinazhang.zjy.todo;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.zjy.simplemodule.base.BaseRepository;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
//        Flowable.just(model)
//                .subscribeOn(Schedulers.io())
//                .flatMap(new Function<TodoModel, Publisher<Long>>() {
//                    @Override
//                    public Publisher<Long> apply(TodoModel todoModel) throws Exception {
//                        return Flowable.just(todoDao.addTodo(todoModel));
//                    }
//                })
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.e(TAG, "accept: " + aLong);
//                    }
//                });
        FlowableUtils.add(model, new Callback<TodoModel, Long>() {
            @Override
            Long value(TodoModel todoModel) {
                return todoDao.addTodo(todoModel);
            }
        });
    }

    public void deleteTodo(TodoModel model) {
        FlowableUtils.add(model, new Callback<TodoModel, Void>() {
            @Override
            Void value(TodoModel todoModel) {
                todoDao.deleteTodo(todoModel);
                return null;
            }
        });
    }

    public void updateTodo(TodoModel model) {
        FlowableUtils.add(model, new Callback<TodoModel, Integer>() {
            @Override
            Integer value(TodoModel todoModel) {
                return todoDao.updateTodo(todoModel);
            }
        });
    }

    public void queryTodoList() {
        todoDao.getTodoList()
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<TodoModel>>() {
                    @Override
                    public void accept(List<TodoModel> todoModels) throws Exception {
                        todoListData.postValue(todoModels);
                    }
                });
    }

}
