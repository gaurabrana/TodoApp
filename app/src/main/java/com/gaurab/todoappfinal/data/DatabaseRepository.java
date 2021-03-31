package com.gaurab.todoappfinal.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gaurab.todoappfinal.model.Task;
import com.gaurab.todoappfinal.util.TaskRoomDatabase;

import java.util.List;

public class DatabaseRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTodoTask;

    public DatabaseRepository(Application application){
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        taskDao = database.taskDao();
        allTodoTask = taskDao.getTasks();
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getAllTodoTask() {
        return allTodoTask;
    }

    public LiveData<List<Task>> getSingleTask(long id) {
        return taskDao.getSingleTask(id);
    }

    public void setAllTodoTask(LiveData<List<Task>> allTodoTask) {
        this.allTodoTask = allTodoTask;
    }

    public void insert(Task task){
        new insertTodoAysncTask(taskDao).execute(task);
    }

    public void delete(Task task){
        new deleteTodoAysncTask(taskDao).execute(task);
    }

    public void update(Task task){
        new updateTodoAysncTask(taskDao).execute(task);
    }

    public void deleteAll(){
        new deleteAllTodoAysncTask(taskDao).execute();
    }

    private static class insertTodoAysncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;
        private insertTodoAysncTask(TaskDao taskDao){
            this.taskDao=taskDao;
        }

        @Override
        protected Void doInBackground(Task... task) {
            taskDao.insert(task[0]);
            return null;
        }
    }

    private static class deleteAllTodoAysncTask extends AsyncTask<Task, Void, Void>{
        private TaskDao taskDao;
        private deleteAllTodoAysncTask(TaskDao taskDao){
            this.taskDao=taskDao;
        }

        @Override
        protected Void doInBackground(Task... task) {
            taskDao.deleteAll();
            return null;
        }
    }

    private static class deleteTodoAysncTask extends AsyncTask<Task, Void, Void>{
        private TaskDao taskDao;
        private deleteTodoAysncTask(TaskDao taskDao){
            this.taskDao=taskDao;
        }

        @Override
        protected Void doInBackground(Task... task) {
            taskDao.deleteById(task[0]);
            return null;
        }
    }

    private static class updateTodoAysncTask extends AsyncTask<Task, Void, Void>{
        private TaskDao taskDao;
        private updateTodoAysncTask(TaskDao taskDao){
            this.taskDao=taskDao;
        }

        @Override
        protected Void doInBackground(Task... task) {
            taskDao.update(task[0]);
            return null;
        }
    }
}
