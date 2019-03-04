package com.chinazhang.zjy.todo;

public class TodoModel {

    private long createTime;
    private String content;
    private String title;
    private boolean perform;
    private int priority;
    private long needPriorityTime;

    public TodoModel() {
        createTime = System.currentTimeMillis();
    }

    public int getPriority() {
        return priority;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getNeedPriorityTime() {
        return needPriorityTime;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNeedPriorityTime(long needPriorityTime) {
        this.needPriorityTime = needPriorityTime;
    }

    public void setPerform(boolean perform) {
        this.perform = perform;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
