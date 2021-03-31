package com.gaurab.todoappfinal.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.gaurab.todoappfinal.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Delete
    void deleteById(Task task);

    @Query("SELECT * FROM task_table WHERE taskID=:id")
    LiveData<List<Task>> getSingleTask(long id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Task... task);

    @Query("SELECT * FROM task_table ORDER BY date_created, priority desc")
    LiveData<List<Task>> getTasks();

}
