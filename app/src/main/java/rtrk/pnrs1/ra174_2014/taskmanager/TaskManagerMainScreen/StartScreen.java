package rtrk.pnrs1.ra174_2014.taskmanager.TaskManagerMainScreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import rtrk.pnrs1.ra174_2014.taskmanager.AddTaskMainScreen.AddTaskView;
import rtrk.pnrs1.ra174_2014.taskmanager.R;
import rtrk.pnrs1.ra174_2014.taskmanager.Statistics.StatisticsView;

/**
 * Created by Srdjan Suvakov on 3/27/17.
 *
 * zasad nije potreban interfejs, mozda ce biti u buducnosti!
 */

public class StartScreen extends AppCompatActivity implements StartScreenModel {

    private Button btnAddTask;
    private Button btnStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        btnAddTask = (Button) findViewById(R.id.btnNewTask);
        btnStatistics = (Button) findViewById(R.id.btnStatistics);

        btnAddTask.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(StartScreen.this, AddTaskView.class);
                startActivity(intent);
                finish();
            }
        });

        btnStatistics.setOnClickListener(new View.OnClickListener(){

         @Override
            public void onClick(View view){
                Intent intent = new Intent(StartScreen.this, StatisticsView.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
