package com.chinazhang.zjy.todo;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.chinazhang.zjy.todo.todo.TodoDao;
import com.chinazhang.zjy.todo.todo.TodoModel;

@Database(version = 2, entities = {TodoModel.class}, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase database;

    public static MyDatabase getInstance() {
        if (database == null) {
            synchronized (MyDatabase.class) {
                if (database == null) {
                    database = Room.databaseBuilder(SimpleApplication.getContext(), MyDatabase.class, "todo.bd")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return database;
    }

    public abstract TodoDao getTodoDao();

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE todomodel "
                    + " ADD COLUMN remind BOOL");
        }
    };

}
