package com.example.todolistapp;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {
    private static final String PREF_NAME = "task_pref";
    private static final String TASK_LIST_KEY = "task_list";
    private SharedPreferences sharedPreferences;
    private Gson gson;
    public TaskStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }
    // Save task list to SharedPreferences
    public void saveTasks(List<Task> taskList) {
        String json = gson.toJson(taskList);
        sharedPreferences.edit().putString(TASK_LIST_KEY, json).apply();
    }

    public List<Task> getTasks() {
        String json = sharedPreferences.getString(TASK_LIST_KEY, null);
        if (json != null) {
            Type type = new TypeToken<List<Task>>() {}.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>();
        }
    }
}
