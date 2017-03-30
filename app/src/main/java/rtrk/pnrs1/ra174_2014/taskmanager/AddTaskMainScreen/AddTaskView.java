package rtrk.pnrs1.ra174_2014.taskmanager.AddTaskMainScreen;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

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
    int pickedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_view);
        initStuff();
    }


    @Override
    public void initStuff(){
        txtTaskName=(EditText)findViewById(R.id.txtTaskName);
        txtTaskDescription=(EditText)findViewById(R.id.txtTaskDescription);
        taskTimePicker=(TimePicker) findViewById(R.id.timePicker);
        chkReminder=(CheckBox)findViewById(R.id.chkReminder);
        btnRed=(Button)findViewById(R.id.btnRed);
        btnGreen=(Button)findViewById(R.id.btnGreen);
        btnYellow=(Button)findViewById(R.id.btnYellow);
        btnCancelTask=(Button)findViewById(R.id.btnCancelTask);
        btnAddTask=(Button)findViewById(R.id.btnAddTask);
        btnAddTask.setEnabled(false);
        pickedColor = 0;



        //Initialize Listeners!

        txtTaskName.addTextChangedListener(new TextWatcher() {
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
        });

        txtTaskDescription.addTextChangedListener(new TextWatcher() {
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
        });

        btnCancelTask.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                proceedToNextActivity();
            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                if(btnAddTask.isEnabled()){
                    proceedToNextActivity();
                }
            }
        });

        btnGreen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                if(pickedColor == 0){
                    btnRed.setEnabled(false);
                    btnRed.setBackgroundColor(Color.GRAY);
                    btnYellow.setEnabled(false);
                    btnYellow.setBackgroundColor(Color.GRAY);
                    pickedColor = 3;
                    return;
                }
                else if(pickedColor == 3){
                    btnRed.setEnabled(true);
                    btnRed.setBackgroundColor(Color.RED);
                    btnYellow.setEnabled(true);
                    btnYellow.setBackgroundColor(Color.YELLOW);
                    pickedColor = 0;
                    return;
                }
            }
        });
        btnRed.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                if(pickedColor == 0){
                    btnGreen.setEnabled(false);
                    btnGreen.setBackgroundColor(Color.GRAY);
                    btnYellow.setEnabled(false);
                    btnYellow.setBackgroundColor(Color.GRAY);
                    pickedColor = 1;
                    return;
                }
                else if(pickedColor == 1){
                    btnGreen.setEnabled(true);
                    btnGreen.setBackgroundColor(Color.GREEN);
                    btnYellow.setEnabled(true);
                    btnYellow.setBackgroundColor(Color.YELLOW);
                    pickedColor = 0;
                    return;
                }
            }
        });

        btnYellow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                if(pickedColor == 0){
                    btnRed.setEnabled(false);
                    btnRed.setBackgroundColor(Color.GRAY);
                    btnGreen.setEnabled(false);
                    btnGreen.setBackgroundColor(Color.GRAY);
                    pickedColor = 2;
                    return;
                }
                else if(pickedColor == 2){
                    btnRed.setEnabled(true);
                    btnRed.setBackgroundColor(Color.RED);
                    btnGreen.setEnabled(true);
                    btnGreen.setBackgroundColor(Color.GREEN);
                    pickedColor = 0;
                    return;
                }
            }
        });


    }


    @Override
    public void proceedToNextActivity() {
        Intent intent = new Intent(AddTaskView.this, StartScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        proceedToNextActivity();
    }

    @Override
    public void CheckAddTaskButton(){
        if(pickedColor >=1 && !txtTaskDescription.getText().toString().isEmpty()
           && !txtTaskName.getText().toString().isEmpty()){
            btnAddTask.setEnabled(true);
        }else{
            btnAddTask.setEnabled(false);
        }

    }
}
