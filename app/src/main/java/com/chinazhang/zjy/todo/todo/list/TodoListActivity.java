package com.chinazhang.zjy.todo.todo.list;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.chinazhang.zjy.todo.R;
import com.chinazhang.zjy.todo.databinding.ActivityTodoListBinding;
import com.chinazhang.zjy.todo.todo.TimeModel;
import com.chinazhang.zjy.todo.todo.TodoListViewModel;
import com.chinazhang.zjy.todo.todo.TodoModel;
import com.chinazhang.zjy.todo.utils.TimeUtils;
import com.zjy.simplemodule.base.activity.SimpleActivity;
import com.zjy.simplemodule.base.activity.VMBindingActivity;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends VMBindingActivity<TodoListViewModel, ActivityTodoListBinding> {

    private List<TimeModel> timeModels;
    private List<TodoModel> todoModels;

    @Override
    public int layoutId() {
        return R.layout.activity_todo_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        viewModel.getTodoListData().observe(this, new Observer<List<TodoModel>>() {
            @Override
            public void onChanged(@Nullable List<TodoModel> todoModels) {
                if (todoModels == null || todoModels.size() == 0) {
                    showDate(false);
                } else {
                    TodoListActivity.this.todoModels = todoModels;
                    showDate(true);
                    addTab();
                }
            }
        });
        viewModel.getTimeList().observe(this, new Observer<List<TimeModel>>() {
            @Override
            public void onChanged(@Nullable List<TimeModel> timeModels) {
                if (timeModels == null || timeModels.size() == 0) {
                    viewModel.queryTodoList();
                } else {
                    TodoListActivity.this.timeModels = timeModels;
                    showDate(true);
                    addTab();
                }
            }
        });
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        viewModel.queryTimeList();
    }

    private void addTab() {
        final List<String> dateList = new ArrayList<>();
        if (timeModels != null)
            for (TimeModel model : timeModels) {
                dateList.add(model.getDate());
            }
        if (todoModels != null) {
            for (TodoModel model : todoModels) {
                String date = TimeUtils.long2string(model.getCreateTime());
                if (dateList.contains(date)) {
                    continue;
                }
                dateList.add(date);
            }
        }
        final List<Fragment> fragments = new ArrayList<>();
        for (String date : dateList) {
            binding.todoTab.addTab(binding.todoTab.newTab().setText(date));
            fragments.add(TodoListFragment.newInstance(date));
        }
        binding.todoPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return dateList.get(position);
            }
        });
    }

    private void showDate(boolean flag) {
        binding.todoEmpty.setVisibility(flag ? View.GONE : View.VISIBLE);
        binding.todoPager.setVisibility(flag ? View.VISIBLE : View.GONE);
        binding.todoTab.setVisibility(flag ? View.VISIBLE : View.GONE);
    }
}
