package rtrk.pnrs1.ra174_2014.taskmanager.AddTaskMainScreen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import rtrk.pnrs1.ra174_2014.taskmanager.ListAdapterStuff.ListAdapter;
import rtrk.pnrs1.ra174_2014.taskmanager.ListAdapterStuff.ListData;
import rtrk.pnrs1.ra174_2014.taskmanager.R;
import rtrk.pnrs1.ra174_2014.taskmanager.TaskManagerMainScreen.StartScreen;

public class AddTaskView extends AppCompatActivity implements AddTaskModel.View {
     Button btnAddTask;
     Button btnCancelTask;
     Button btnGreen;
     Button btnRed;
     Button btnYellow;
     EditText txtTaskName;
     EditText txtTaskDescription;
     TimePicker taskTimePicker;
     CheckBox chkReminder;
     DatePicker taskDatePicker;
     ListData listItem;
     ArrayList<ListData> listOfTasks;
     int pickedColor;
     boolean canEdit;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_view);
        initStuff();
    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            CheckAddTaskButton();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            CheckAddTaskButton();
        }

        @Override
        public void afterTextChanged(Editable s) {
            CheckAddTaskButton();
        }
    }

    @Override
    public void initStuff() {
        txtTaskName = (EditText) findViewById(R.id.txtTaskName);
        txtTaskDescription = (EditText) findViewById(R.id.txtTaskDescription);
        taskTimePicker = (TimePicker) findViewById(R.id.timePicker);
        taskDatePicker = (DatePicker) findViewById(R.id.datePicker);
        chkReminder = (CheckBox) findViewById(R.id.chkReminder);
        btnRed = (Button) findViewById(R.id.btnRed);
        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnYellow = (Button) findViewById(R.id.btnYellow);
        btnCancelTask = (Button) findViewById(R.id.btnCancelTask);
        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        btnAddTask.setEnabled(false);
        pickedColor = 0;
        taskTimePicker.setIs24HourView(true);
        taskTimePicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        taskDatePicker.setMinDate(System.currentTimeMillis());
        btnAddTask.setText(getIntent().getStringExtra(getResources().getString(R.string.btn1)));
        btnCancelTask.setText(getIntent().getStringExtra(getResources().getString(R.string.btn2)));
        listOfTasks=new ArrayList<>();
        intent = getIntent();

        if(intent.hasExtra("EDIT")) {
            canEdit = true;
            listItem = (ListData) intent.getSerializableExtra("EDIT");
            txtTaskName.setText(listItem.taskName);
            taskDatePicker.updateDate(listItem.year,listItem.month+1,listItem.day);
            taskTimePicker.setCurrentHour(listItem.hour);
            taskTimePicker.setCurrentMinute(listItem.minute);
            txtTaskDescription.setText(listItem.taskDescription);
            if(listItem.reminder)
                chkReminder.setChecked(true);
            else
                chkReminder.setChecked(false);

            switch (listItem.priority){
                case 1:
                    btnRed.setEnabled(false);
                    btnRed.setBackgroundColor(Color.GRAY);
                    btnYellow.setEnabled(false);
                    btnYellow.setBackgroundColor(Color.GRAY);
                    pickedColor = 1;
                    break;
                case 2:
                    btnRed.setEnabled(false);
                    btnRed.setBackgroundColor(Color.GRAY);
                    btnGreen.setEnabled(false);
                    btnGreen.setBackgroundColor(Color.GRAY);
                    pickedColor = 2;
                    break;
                case 3:
                    btnGreen.setEnabled(false);
                    btnGreen.setBackgroundColor(Color.GRAY);
                    btnYellow.setEnabled(false);
                    btnYellow.setBackgroundColor(Color.GRAY);
                    pickedColor = 3;
                    break;
                default:
                    pickedColor = 0;
                    break;

            }
            CheckAddTaskButton();

        }
        else
            canEdit = false;





        //Initialize Listeners!

        txtTaskName.addTextChangedListener(new MyTextWatcher());

        txtTaskDescription.addTextChangedListener(new MyTextWatcher());

        btnCancelTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ShowCancelToast();
                Intent intent = new Intent(getBaseContext(),StartScreen.class);
                if(btnCancelTask.getText().equals(getResources().getString(R.string.delete_list_element))) {
                    listItem=new ListData(txtTaskName.getText().toString(),
                            txtTaskDescription.getText().toString(),pickedColor,
                            taskDatePicker.getDayOfMonth(),taskDatePicker.getMonth()+1,
                            taskDatePicker.getYear(),taskTimePicker.getCurrentHour(),
                            taskTimePicker.getCurrentMinute(),false,chkReminder.isChecked());
                    intent.putExtra("Deleted", listItem);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (btnAddTask.isEnabled()) {
                    Intent intent = new Intent(getBaseContext(),StartScreen.class);
                    listItem=new ListData(txtTaskName.getText().toString(),
                            txtTaskDescription.getText().toString(),pickedColor,
                            taskDatePicker.getDayOfMonth(),taskDatePicker.getMonth()+1,
                            taskDatePicker.getYear(),taskTimePicker.getCurrentHour(),
                            taskTimePicker.getCurrentMinute(),false,chkReminder.isChecked());
                    listOfTasks.add(listItem);
                    intent.putExtra("Task", listItem);
                    ShowToast();
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

        btnGreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (pickedColor == 0) {
                    btnRed.setEnabled(false);
                    btnRed.setBackgroundColor(Color.GRAY);
                    btnYellow.setEnabled(false);
                    btnYellow.setBackgroundColor(Color.GRAY);
                    pickedColor = 1;
                    CheckAddTaskButton();
                    return;
                } else if (pickedColor == 1) {
                    btnRed.setEnabled(true);
                    btnRed.setBackgroundColor(Color.RED);
                    btnYellow.setEnabled(true);
                    btnYellow.setBackgroundColor(Color.YELLOW);
                    pickedColor = 0;
                    CheckAddTaskButton();
                    return;
                }
            }
        });
        btnRed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (pickedColor == 0) {
                    btnGreen.setEnabled(false);
                    btnGreen.setBackgroundColor(Color.GRAY);
                    btnYellow.setEnabled(false);
                    btnYellow.setBackgroundColor(Color.GRAY);
                    pickedColor = 3;
                    CheckAddTaskButton();
                    return;
                } else if (pickedColor == 3) {
                    btnGreen.setEnabled(true);
                    btnGreen.setBackgroundColor(Color.GREEN);
                    btnYellow.setEnabled(true);
                    btnYellow.setBackgroundColor(Color.YELLOW);
                    pickedColor = 0;
                    CheckAddTaskButton();
                    return;
                }
            }
        });

        btnYellow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (pickedColor == 0) {
                    btnRed.setEnabled(false);
                    btnRed.setBackgroundColor(Color.GRAY);
                    btnGreen.setEnabled(false);
                    btnGreen.setBackgroundColor(Color.GRAY);
                    pickedColor = 2;
                    CheckAddTaskButton();
                    return;
                } else if (pickedColor == 2) {
                    btnRed.setEnabled(true);
                    btnRed.setBackgroundColor(Color.RED);
                    btnGreen.setEnabled(true);
                    btnGreen.setBackgroundColor(Color.GREEN);
                    pickedColor = 0;
                    CheckAddTaskButton();
                    return;
                }
            }
        });
    }



    @Override
    public void proceedToNextActivity() {
        Intent intent = new Intent(getBaseContext(),StartScreen.class);
        listItem=new ListData(txtTaskName.getText().toString(),
                txtTaskDescription.getText().toString(), pickedColor,
                taskDatePicker.getDayOfMonth(),taskDatePicker.getMonth()+1,
                taskDatePicker.getYear(),taskTimePicker.getCurrentHour(),
                taskTimePicker.getCurrentMinute(),false,chkReminder.isChecked());
        listOfTasks.add(listItem);
        intent.putExtra("Task", listItem);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShowCancelToast();
        Intent intent = new Intent(getBaseContext(),StartScreen.class);



        setResult(RESULT_CANCELED,intent);
        finish();
    }

    @Override
    public void CheckAddTaskButton(){
        Calendar c= Calendar.getInstance();
        long millis=System.currentTimeMillis();
        c.setTimeInMillis(millis);
        int hours   =c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);
        if(pickedColor >=1 && !txtTaskDescription.getText().toString().isEmpty()
           && !txtTaskName.getText().toString().isEmpty()){
            btnAddTask.setEnabled(true);
        }else{
            btnAddTask.setEnabled(false);
        }



    }

    public void ShowToast(){
        Toast toast = Toast.makeText(this, R.string.toast_add_task, Toast.LENGTH_LONG);
        toast.show();
    }

    public void ShowCancelToast(){
        Toast toast = Toast.makeText(this, R.string.toast_cancel_task, Toast.LENGTH_LONG);
        toast.show();
    }
}
