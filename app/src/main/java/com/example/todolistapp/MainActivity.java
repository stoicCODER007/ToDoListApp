package com.example.todolistapp;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private TaskStorage taskStorage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        taskStorage = new TaskStorage(this);
        taskList = taskStorage.getTasks();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Initialize task list and adapter
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);
        findViewById(R.id.fab_add_task).setOnClickListener(view -> showAddTaskDialog());


    }
    private void showAddTaskDialog() {
        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);

        // Get references to dialog views
        EditText editTaskName = dialogView.findViewById(R.id.edit_task_name);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnAdd = dialogView.findViewById(R.id.btn_add);

        // Build and show the AlertDialog
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        alertDialog.show();
        btnCancel.setOnClickListener(view -> alertDialog.dismiss());
        btnAdd.setOnClickListener(view -> {
            String taskName = editTaskName.getText().toString().trim();
            if (!taskName.isEmpty()) {
                // Add task to the list
                taskList.add(new Task(taskName, false));
                taskAdapter.notifyItemInserted(taskList.size() - 1);
                recyclerView.scrollToPosition(taskList.size() - 1);
                taskStorage.saveTasks(taskList);
                alertDialog.dismiss();
            } else {
                editTaskName.setError("Task name cannot be empty");
            }
        });
    }

    }