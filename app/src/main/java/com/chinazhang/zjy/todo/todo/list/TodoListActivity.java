package com.chinazhang.zjy.todo.todo.list;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.chinazhang.zjy.todo.R;
import com.chinazhang.zjy.todo.databinding.ActivityTodoListBinding;
import com.chinazhang.zjy.todo.todo.TimeModel;
import com.chinazhang.zjy.todo.todo.TodoListViewModel;
import com.chinazhang.zjy.todo.todo.TodoModel;
import com.chinazhang.zjy.todo.todo.edit.TodoEditActivity;
import com.chinazhang.zjy.todo.utils.FragmentUtils;
import com.chinazhang.zjy.todo.utils.TimeUtils;
import com.zjy.simplemodule.base.activity.VMBindingActivity;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends VMBindingActivity<TodoListViewModel, ActivityTodoListBinding> {

    private List<TimeModel> timeModels;
    private List<TodoModel> todoModels;
    private List<String> dateList;
    private List<Fragment> fragments;
    private Fragment currFragment;

    @Override
    public int layoutId() {
        return R.layout.activity_todo_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        dateList = new ArrayList<>();
        fragments = new ArrayList<>();
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
        binding.todoTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() < fragments.size())
                    currFragment = FragmentUtils.showOrHide(getSupportFragmentManager(), getLayoutId(), fragments.get(tab.getPosition()), currFragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void initEvent() {
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSelf(), TodoEditActivity.class)
                        .putExtra("action", "add"));
            }
        });
    }

    @Override
    public void initData() {
        viewModel.queryTimeList();
    }

    private void addTab() {
        if (timeModels != null)
            for (TimeModel model : timeModels) {
                if (dateList.contains(model.getDate())) {
                    continue;
                }
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
        for (int i = 0; i < dateList.size(); i++) {
            if (binding.todoTab.getTabCount() < i && binding.todoTab.getTabAt(i).getText().equals(dateList.get(i))) {
                continue;
            }
            binding.todoTab.addTab(binding.todoTab.newTab().setText(dateList.get(i)));
            fragments.add(TodoListFragment.newInstance(dateList.get(i)));
        }
        if (fragments.size() > 0 && currFragment == null) {
            currFragment = fragments.get(0);
            FragmentUtils.add(getSupportFragmentManager(), getLayoutId(), currFragment);
        }
    }

    private int getLayoutId() {
        return R.id.todo_item;
    }

    private void showDate(boolean flag) {
        binding.todoEmpty.setVisibility(flag ? View.GONE : View.VISIBLE);
        binding.todoItem.setVisibility(flag ? View.VISIBLE : View.GONE);
        binding.todoTab.setVisibility(flag ? View.VISIBLE : View.GONE);
    }
}
