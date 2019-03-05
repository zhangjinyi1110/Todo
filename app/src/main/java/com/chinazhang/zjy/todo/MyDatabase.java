package com.chinazhang.zjy.todo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

@Database(version = 1, entities = {TodoModel.class}, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase database;

    public static MyDatabase getInstance() {
        if (database == null) {
            synchronized (MyDatabase.class) {
                if (database == null) {
                    database = Room.databaseBuilder(SimpleApplication.getContext(), MyDatabase.class, "todo.bd").build();
                }
            }
        }
        return database;
    }

    public abstract TodoDao getTodoDao();

}
