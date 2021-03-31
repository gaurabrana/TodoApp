package com.gaurab.todoappfinal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaurab.todoappfinal.R;
import com.gaurab.todoappfinal.model.Task;
import com.gaurab.todoappfinal.util.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Task> taskList;
    private final OnTaskClickListener taskClickListener;

    public RecyclerViewAdapter(List<Task> taskList, OnTaskClickListener taskClickListener) {
        this.taskClickListener = taskClickListener;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        String formatted = Utils.formatDate(task.getDueDate());

        holder.task.setText(task.getTask());
        holder.todayChip.setText(formatted);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RadioButton radioButton;
        public TextView task;
        public Chip todayChip;

        OnTaskClickListener onTaskClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            task = itemView.findViewById(R.id.todo_row_todo);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            this.onTaskClickListener =  taskClickListener;
            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Task currentTask = taskList.get(getAdapterPosition());
            int id = v.getId();
            if(id == R.id.todo_row_layout){
                onTaskClickListener.onTaskClick(currentTask);
            }
            else if (id == R.id.todo_radio_button){
                onTaskClickListener.onTodoRadioButtonClock(currentTask);
            }
        }
    }
}
