package com.chinazhang.zjy.todo.todo.edit;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

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
                    boolean flag = model.isRemind();
                    binding.todoRemind.setChecked(flag);
                    if (flag) {
                        binding.todoRemindTime.setText(String.valueOf(model.getRemindTime()));
                        binding.todoRemindType.setText(String.valueOf(model.getRemindType()));
                    }
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
        setLazy(false);
        type = getSelfActivity().getIntent().getStringExtra("action");
        id = getSelfActivity().getIntent().getLongExtra("id", -1);
    }

    @Override
    public void initEvent() {
        binding.todoSave.setOnClickListener(this);
        binding.todoRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.remindGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
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
            boolean checked = binding.todoRemind.isChecked();
            if (type.equals("add")) {
                model = new TodoModel(binding.todoContent.getText().toString(), binding.todoTitle.getText().toString());
                model.setRemind(checked);
                if (checked) {
                    model.setRemindTime(System.currentTimeMillis());
                    model.setRemindType(0x01);
                } else {
                    model.setRemindTime(0);
                    model.setRemindType(0);
                }
                viewModel.addTodo(model);
//                for (int i = 0; i < 5; i++) {
//                    TodoModel a = new TodoModel("content" + i, "title" + i);
//                    a.setRemindTime(0);
//                    a.setRemindType(0);
//                    a.setCreateTime(System.currentTimeMillis() - 24 * 60 * 60 * 1000 * (i + 1));
//                    viewModel.addTodo(a);
//                }
                text = "添加成功";
            } else {
                model.setContent(binding.todoContent.getText().toString());
                model.setTitle(binding.todoTitle.getText().toString());
                model.setRemind(checked);
                if (checked) {
                    model.setRemindTime(System.currentTimeMillis());
                    model.setRemindType(0x01);
                } else {
                    model.setRemindTime(0);
                    model.setRemindType(0);
                }
                viewModel.updateTodo(model);
                text = "修改成功";
            }
            flag = true;
        }
        ToastUtils.showToastShort(getSelfActivity(), text);
        if (flag)
            getSelfActivity().finish();
    }

}
