package com.gaurab.todoappfinal;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gaurab.todoappfinal.model.Priority;
import com.gaurab.todoappfinal.model.SharedViewModel;
import com.gaurab.todoappfinal.model.Task;
import com.gaurab.todoappfinal.model.TaskViewModel;
import com.gaurab.todoappfinal.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditText entertask;
    private ImageButton calenderButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date dueDate;
    Calendar calendar = Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    private boolean isUpdate;
    private Priority priority;

    Date date;

    public BottomSheetFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("msg", calendar.getTime().toString());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calenderButton = view.findViewById(R.id.today_calendar_button);
        entertask = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);

        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);
        nextWeekChip.setOnClickListener(this);

        date = calendar.getTime();
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        entertask.setText("");

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        calenderButton.setOnClickListener(v -> {
            calendarGroup.setVisibility(calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hideSoftKeyboard(v);
        });

        calendarView.setOnDateChangeListener((view12, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dueDate = calendar.getTime();
            calendar.setTime(date);
        });

        priorityButton.setOnClickListener(view2 -> {
            Utils.hideSoftKeyboard(view2);
            priorityRadioGroup.setVisibility(priorityRadioGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            if(isUpdate){
                priorityRadioGroup.clearCheck();
                Task selectedTask = sharedViewModel.getSelectedItem().getValue();
                priority = selectedTask.getPriority();
                if(priority == Priority.HIGH){
                    priorityRadioGroup.check(R.id.radioButton_high);
                }
                else if(priority == Priority.MEDIUM){
                    priorityRadioGroup.check(R.id.radioButton_med);
                }
                else if(priority == Priority.LOW){
                    priorityRadioGroup.check(R.id.radioButton_low);
                }

            }
            priorityRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                if (priorityRadioGroup.getVisibility() == View.VISIBLE){
                   selectedButtonId = checkedId;
                   selectedRadioButton = view.findViewById(selectedButtonId);
                   if (selectedRadioButton.getId() == R.id.radioButton_high){
                       priority = Priority.HIGH;
                   }
                   else if (selectedRadioButton.getId() == R.id.radioButton_med){
                       priority = Priority.MEDIUM;
                   }
                   else if (selectedRadioButton.getId() == R.id.radioButton_low){
                       priority = Priority.LOW;
                   }
                   else{
                       priority = Priority.LOW;
                   }
                }
                else{
                    priority = Priority.LOW;
                }
            });
        });

        saveButton.setOnClickListener(view1 -> {
            String task = entertask.getText().toString().trim();

            if (!TextUtils.isEmpty(task) && dueDate != null && priority!=null) {
                if(isUpdate){
                    Task updateTask = sharedViewModel.getSelectedItem().getValue();
                    updateTask.setTask(task);
                    updateTask.setDateCreated(Calendar.getInstance().getTime());
                    updateTask.setPriority(priority);
                    updateTask.setDueDate(dueDate);
                    TaskViewModel.update(updateTask);
                    sharedViewModel.setIsEdit(false);
                    calendar.setTime(date);
                }
                else{
                    Task myTask = new Task(task, priority, dueDate, Calendar.getInstance().getTime(), false);
                    TaskViewModel.insert(myTask);
                }
                if(this.isVisible()){
                    this.dismiss();
                }
            }
            else{
                Snackbar.make(saveButton,R.string.empty_field,Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.today_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            dueDate = calendar.getTime();
        } else if (id == R.id.tomorrow_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dueDate = calendar.getTime();
        } else if (id == R.id.next_week_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            dueDate = calendar.getTime();
        }
        calendar.setTime(date);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getSelectedItem().getValue() != null){
            isUpdate = sharedViewModel.getIsEdit();
            Task task = sharedViewModel.getSelectedItem().getValue();
            entertask.setText(task.getTask());
            Log.d("MY", "haha" + task.getTask());
        }
    }
}
