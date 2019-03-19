package com.chinazhang.zjy.todo.todo.list;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chinazhang.zjy.todo.R;
import com.chinazhang.zjy.todo.databinding.FragmentTodoListBinding;
import com.chinazhang.zjy.todo.databinding.ItemTodoBinding;
import com.chinazhang.zjy.todo.todo.TodoListViewModel;
import com.chinazhang.zjy.todo.todo.TodoModel;
import com.chinazhang.zjy.todo.todo.edit.TodoEditActivity;
import com.chinazhang.zjy.todo.widget.ScrollView;
import com.google.gson.Gson;
import com.zjy.simplemodule.adapter.BindingAdapter;
import com.zjy.simplemodule.base.fragment.AbsBindingFragment;
import com.zjy.simplemodule.utils.SizeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoListFragment extends AbsBindingFragment<TodoListViewModel, FragmentTodoListBinding> implements View.OnClickListener {

    private BindingAdapter<TodoModel, ItemTodoBinding> adapter;
    private Map<String, Boolean> openMap;
    private String date;

    public static TodoListFragment newInstance(String date) {
        Bundle args = new Bundle();
        args.putString("date", date);
        TodoListFragment fragment = new TodoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            date = bundle.getString("date");
        }
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

    @SuppressLint("UseSparseArrays")
    @Override
    public void init(Bundle savedInstanceState) {
        setLazy(false);
        openMap = new HashMap<>();
        adapter = new BindingAdapter<TodoModel, ItemTodoBinding>(getSelfActivity()) {
            @Override
            protected void convert(ItemTodoBinding binding, final TodoModel todoModel, final int position) {
                if (!openMap.containsKey(String.valueOf(todoModel.getId()))) {
                    openMap.put(String.valueOf(todoModel.getId()), false);
                }
                binding.itemTodoContent.setText(todoModel.getContent());
                binding.itemTodoTitle.setText(todoModel.getTitle());
                binding.itemTodoLayout.setCancelText("删除");
                binding.itemTodoLayout.setOkText("编辑");
                binding.itemTodoLayout.setShowMore(false);
                binding.itemTodoLayout.setItemClickListener(new ScrollView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int id) {
                        switch (id) {
                            case ScrollView.TYPE_CANCEL:
                                new AlertDialog.Builder(getSelfActivity())
                                        .setTitle("删除")
                                        .setMessage("确定删除订单？")
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                viewModel.deleteTodo(todoModel);
                                                dialog.dismiss();
                                            }
                                        })
                                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .create()
                                        .show();
                                break;
                            case ScrollView.TYPE_OK:
                                startActivity(new Intent(getSelfActivity(), TodoEditActivity.class)
                                        .putExtra("action", "edit")
                                        .putExtra("id", todoModel.getId()));
                                break;
                        }
                    }
                });
                Boolean flag = openMap.get(String.valueOf(todoModel.getId()));
                binding.itemTodoLayout.setBtnChangeListener(new ScrollView.OnBtnChangeListener() {
                    @Override
                    public void onChange(boolean flag) {
                        openMap.put(String.valueOf(todoModel.getId()), flag);
                    }
                });
                if (flag != null && flag) {
                    binding.itemTodoLayout.openBtn();
                } else {
                    binding.itemTodoLayout.closeBtn();
                }
                binding.itemTodoLayout.setContentListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getSelfActivity())
                                .setTitle("信息")
                                .setMessage(new Gson().toJson(todoModel))
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create()
                                .show();
                    }
                });
            }

            @Override
            protected int getLayoutId(int type) {
                return R.layout.item_todo;
            }
        };
        adapter.setLoadEnable(false);
        adapter.setFooterEnable(false);
        binding.recyclerTodo.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildLayoutPosition(view) != 0) {
                    outRect.top = SizeUtils.dp2px(getSelfActivity(), 1);
                }
            }
        });
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
                viewModel.queryTodoList(date);
                if (binding.refreshTodo.isRefreshing())
                    binding.refreshTodo.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        viewModel.queryTodoList(date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add:
                startActivity(new Intent(getSelfActivity(), TodoEditActivity.class)
                        .putExtra("action", "add"));
                break;
        }
    }
}
