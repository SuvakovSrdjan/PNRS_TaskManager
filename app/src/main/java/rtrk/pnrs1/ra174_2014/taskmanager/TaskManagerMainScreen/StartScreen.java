package rtrk.pnrs1.ra174_2014.taskmanager.TaskManagerMainScreen;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import rtrk.pnrs1.ra174_2014.taskmanager.AddTaskMainScreen.AddTaskModel;
import rtrk.pnrs1.ra174_2014.taskmanager.AddTaskMainScreen.AddTaskView;
import rtrk.pnrs1.ra174_2014.taskmanager.DatabaseStuff.TaskDbHelper;
import rtrk.pnrs1.ra174_2014.taskmanager.ListAdapterStuff.ListAdapter;
import rtrk.pnrs1.ra174_2014.taskmanager.ListAdapterStuff.ListData;
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
    private ListView listView;
    private String changedItemName;
    ArrayList<ListData> listData;
    ListAdapter listAdapter;
    ListData listItem;
    TaskReminderService taskReminderService;
    TaskDbHelper taskDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);


        initStuff();
        makeService();
        taskDbHelper = new TaskDbHelper(this);
        for (ListData newListItem : taskDbHelper.readTasks()) {
            listData.add(newListItem);

        }
        listAdapter.notifyDataSetChanged();



    }

    private void makeService() {
        Intent intent = new Intent(StartScreen.this,TaskReminderService.class);
        intent.putExtra("Task", listData);

        ServiceConnection reminderConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                taskReminderService = ((TaskReminderService.ReminderBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        bindService(intent,reminderConnection,BIND_AUTO_CREATE);
        startService(intent);
    }


    private void initStuff() {
        btnAddTask = (Button) findViewById(R.id.btnNewTask);
        btnStatistics = (Button) findViewById(R.id.btnStatistics);
        listData=new ArrayList<>();
        listAdapter=new ListAdapter(getBaseContext(),listData);
        listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(listAdapter);

        btnAddTask.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(StartScreen.this, AddTaskView.class);
                intent.putExtra(getResources().getString(R.string.btn1),getResources().getString(R.string.add_task));
                intent.putExtra(getResources().getString(R.string.btn2),getResources().getString(R.string.cancel_task));
                startActivityForResult(intent,1);

            }
        });

        btnStatistics.setOnClickListener(new View.OnClickListener(){

         @Override
            public void onClick(View view){
                Intent intent = new Intent(StartScreen.this, StatisticsView.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), AddTaskView.class);
                intent.putExtra(getResources().getString(R.string.btn1),getResources().getString(R.string.save_changes));
                intent.putExtra(getResources().getString(R.string.btn2),getResources().getString(R.string.delete_list_element));
                intent.putExtra("EDIT",(ListData) listView.getItemAtPosition(position));
                changedItemName = ((ListData) listView.getItemAtPosition(position)).taskName;
                startActivityForResult(intent, 2);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Log.d("TAG","got in1");
        if (requestCode == 1) {
            //   Log.d("TAG","got in2");
            if (resultCode == Activity.RESULT_OK) {
                // Log.d("TAG","got in3");
                listItem = ((ListData) data.getSerializableExtra("Task"));
                listData.add(listItem);
                taskDbHelper.insert(listItem);
                listAdapter.notifyDataSetChanged();
                taskReminderService.updateTasks(listData);
            }
        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                if (data.hasExtra("Deleted")) {
                    listItem = ((ListData) data.getSerializableExtra("Task"));
                    Log.d("TAG", "got in2");
                    taskDbHelper.deleteTask(changedItemName);
                    Log.d("TAG", "got in3");

                    listData.clear();
                    Log.d("TAG", "got in1");

                    for (ListData newListData : taskDbHelper.readTasks()) {
                        listData.add(newListData);
                        Log.e("TAG", "got WILD");

                    }
                    taskReminderService.updateTasks(listData);
                    listAdapter.notifyDataSetChanged();
                } else {
                    listItem = ((ListData) data.getSerializableExtra("Task"));
                    listData.clear();
                    taskDbHelper.deleteTask(changedItemName);
                    taskDbHelper.insert(listItem);
                    for (ListData newListItem : taskDbHelper.readTasks()) {
                        listData.add(newListItem);

                    }
                    taskReminderService.updateTasks(listData);

                    listAdapter.notifyDataSetChanged();


                }
            }
        }
    }
}



