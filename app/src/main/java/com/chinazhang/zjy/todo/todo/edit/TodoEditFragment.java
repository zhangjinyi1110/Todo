package com.chinazhang.zjy.todo.todo.edit;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chinazhang.zjy.todo.R;
import com.chinazhang.zjy.todo.databinding.FragmentTodoEditBinding;
import com.chinazhang.zjy.todo.todo.TodoListViewModel;
import com.chinazhang.zjy.todo.todo.TodoModel;
import com.zjy.simplemodule.base.fragment.AbsBindingFragment;
import com.zjy.simplemodule.utils.ToastUtils;

public class TodoEditFragment extends AbsBindingFragment<TodoListViewModel, FragmentTodoEditBinding> implements View.OnClickListener {

    private String type;
    private long id;
    private TodoModel model;

    public static TodoEditFragment newInstance() {
        Bundle args = new Bundle();
        TodoEditFragment fragment = new TodoEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void observe() {
        viewModel.getQueryData().observe(this, new Observer<TodoModel>() {
            @Override
            public void onChanged(@Nullable TodoModel todoModel) {
                if (todoModel != null) {
                    model = todoModel;
                    binding.todoContent.setText(model.getContent());
                    binding.todoTitle.setText(model.getTitle());
                }
            }
        });
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_todo_edit;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        type = getSelfActivity().getIntent().getStringExtra("action");
        id = getSelfActivity().getIntent().getLongExtra("id", -1);
    }

    @Override
    public void initEvent() {
        binding.todoSave.setOnClickListener(this);
    }

    @Override
    public void initData() {
        if (id >= 0) {
            viewModel.queryTodo(id);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.todo_save:
                save();
                break;
        }
    }

    private void save() {
        String text;
        boolean flag = false;
        if (TextUtils.isEmpty(binding.todoContent.getText())) {
            text = "内容不能为空";
        } else if (TextUtils.isEmpty(binding.todoTitle.getText())) {
            text = "标题不能为空";
        } else {
            if (type.equals("add")) {
                model = new TodoModel(binding.todoContent.getText().toString(), binding.todoTitle.getText().toString());
                model.setRemind(binding.todoRemind.isChecked());
                viewModel.addTodo(model);
                text = "添加成功";
            } else {
                model.setContent(binding.todoContent.getText().toString());
                model.setTitle(binding.todoTitle.getText().toString());
                model.setRemind(binding.todoRemind.isChecked());
                viewModel.updateTodo(model);
                text = "修改成功";
            }
            flag = true;
        }
        ToastUtils.showToastShort(getSelfActivity(), text);
        if (flag)
            getSelfActivity().finish();
    }

    @Override
    public boolean isLazy() {
        return false;
    }
}
