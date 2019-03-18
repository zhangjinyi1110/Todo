package com.chinazhang.zjy.todo.todo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TodoModel {

    @PrimaryKey(autoGenerate = true)
    private long id;//id
    private long createTime;//创建时间
    private String content;//内容
    private String title;//标题
    private boolean perform;//是否完成
    private int priority;//优先级
    private long remindTime;//需要提醒的时间
    private int remindType;//提醒的类型
    private long performTime;//完成时间
    private boolean remind;//是否需要提醒

    public TodoModel(String content, String title) {
        this.content = content;
        this.title = title;
        this.createTime = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPerform() {
        return perform;
    }

    public void setPerform(boolean perform) {
        this.perform = perform;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(long remindTime) {
        this.remindTime = remindTime;
    }

    public int getRemindType() {
        return remindType;
    }

    public void setRemindType(int remindType) {
        this.remindType = remindType;
    }

    public long getPerformTime() {
        return performTime;
    }

    public void setPerformTime(long performTime) {
        this.performTime = performTime;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public boolean isRemind() {
        return remind;
    }
}
