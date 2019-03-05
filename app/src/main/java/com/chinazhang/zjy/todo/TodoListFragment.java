package com.chinazhang.zjy.todo;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chinazhang.zjy.todo.databinding.FragmentTodoListBinding;
import com.chinazhang.zjy.todo.databinding.ItemTodoBinding;
import com.zjy.simplemodule.adapter.BindingAdapter;
import com.zjy.simplemodule.base.fragment.AbsBindingFragment;

import java.util.List;

public class TodoListFragment extends AbsBindingFragment<TodoListViewModel, FragmentTodoListBinding> implements View.OnClickListener {

    private BindingAdapter<TodoModel, ItemTodoBinding> adapter;

    public static TodoListFragment newInstance() {
        Bundle args = new Bundle();
        TodoListFragment fragment = new TodoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void observe() {
        viewModel.getTodoListData().observe(this, new Observer<List<TodoModel>>() {
            @Override
            public void onChanged(@Nullable List<TodoModel> todoModels) {
                if (todoModels != null) {
                    adapter.setList(todoModels);
                }
            }
        });
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_todo_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        adapter = new BindingAdapter<TodoModel, ItemTodoBinding>(getSelfActivity()) {
            @Override
            protected void convert(ItemTodoBinding binding, final TodoModel todoModel, int position) {
                binding.itemTodoContent.setText(todoModel.getContent());
                binding.itemTodoTime.setText(String.valueOf(todoModel.getCreateTime()));
                binding.itemTodoTitle.setText(todoModel.getTitle());
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.deleteTodo(todoModel);
                    }
                });
                binding.itemTodoContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String c = String.valueOf(System.currentTimeMillis());
                        todoModel.setContent(todoModel.getContent() + c);
                        viewModel.updateTodo(todoModel);
                    }
                });
            }

            @Override
            protected int getLayoutId(int type) {
                return R.layout.item_todo;
            }
        };
        adapter.setLoadEnable(false);
        binding.recyclerTodo.setLayoutManager(new LinearLayoutManager(getSelfActivity()));
        binding.recyclerTodo.setAdapter(adapter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initEvent() {
        binding.fabAdd.setOnClickListener(this);
        binding.refreshTodo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.queryTodoList();
                if (binding.refreshTodo.isRefreshing())
                    binding.refreshTodo.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        viewModel.queryTodoList();
    }

    @Override
    public boolean isLazy() {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add:
//                String oldData = SPUtils.getInstance().getString(Contracts.TODO_DATA_NAME);
//                List<TodoModel> list = new Gson().fromJson(oldData, new TypeToken<List<TodoModel>>(){}.getType());
//                ArrayList<TodoModel> modelList = new ArrayList<>(list);
//                TodoModel model = new TodoModel();
//                model.setTitle("new" + modelList.size());
//                model.setContent("new content" + modelList.size());
//                modelList.add(model);
//                String data = new Gson().toJson(modelList, new TypeToken<List<TodoModel>>(){}.getType());
//                SPUtils.getInstance().putString(Contracts.TODO_DATA_NAME, data);
                viewModel.addTodo(new TodoModel("content", "title"));
                break;
        }
    }
}
