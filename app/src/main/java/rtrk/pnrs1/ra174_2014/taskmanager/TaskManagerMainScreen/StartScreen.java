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
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

import rtrk.pnrs1.ra174_2014.taskmanager.AddTaskMainScreen.AddTaskModel;
import rtrk.pnrs1.ra174_2014.taskmanager.AddTaskMainScreen.AddTaskView;
import rtrk.pnrs1.ra174_2014.taskmanager.DatabaseStuff.TaskDbHelper;
import rtrk.pnrs1.ra174_2014.taskmanager.ListAdapterStuff.ListAdapter;
import rtrk.pnrs1.ra174_2014.taskmanager.ListAdapterStuff.ListData;
import rtrk.pnrs1.ra174_2014.taskmanager.R;
import rtrk.pnrs1.ra174_2014.taskmanager.Statistics.JniStats;
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
    private JniStats jniStats;
    int yellowTasks,doneYellowTasks,redTasks,doneRedTasks,greenTasks,doneGreenTasks;
    ArrayList<ListData> listData;
    ListAdapter listAdapter;
    ListData listItem;
    TaskReminderService taskReminderService;
    TaskDbHelper taskDbHelper;
    CheckBox chxBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);


        initStuff();
        makeService();
        readDatabase();



    }

    private void readDatabase() {
        taskDbHelper = new TaskDbHelper(this);
        if(taskDbHelper.readTasks() != null) {
            for (ListData newListItem : taskDbHelper.readTasks()) {
                listData.add(newListItem);

            }
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
       // startService(intent);
    }


    private void initStuff() {
        btnAddTask = (Button) findViewById(R.id.btnNewTask);
        btnStatistics = (Button) findViewById(R.id.btnStatistics);
        listData=new ArrayList<>();
        listAdapter=new ListAdapter(getBaseContext(),listData);
        listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(listAdapter);
        chxBox = (CheckBox)findViewById(R.id.taskFinished);
        jniStats = new JniStats();
        redTasks =0;
        doneRedTasks=0;
        yellowTasks = 0;
        doneYellowTasks = 0;
        greenTasks = 0;
        doneGreenTasks =0;

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
             redTasks =0;
             doneRedTasks=0;
             yellowTasks = 0;
             doneYellowTasks = 0;
             greenTasks = 0;
             doneGreenTasks =0;
             if(listData != null) {
                 int[] statTasks = new int[3];

                 for (ListData l : listData) {
                     Log.e("IT'S A NOT ME,MARIO"," ");
                     switch (l.priority) {
                         case 1:
                             greenTasks++;
                             if (l.checkBox) {
                                 doneGreenTasks++;
                                 Log.e("IT'S GREEN BEAN"," ");
                             }
                             break;
                         case 2:
                             yellowTasks++;
                             if (l.checkBox) {
                                 doneYellowTasks++;
                             }
                             break;
                         case 3:
                             redTasks++;
                             if (l.checkBox) {
                                 doneRedTasks++;
                                 Log.e("IT'S RED NOT ME,SAM"," ");
                             }
                             break;
                         default:
                             break;
                     }
                 }
                 statTasks[0] = jniStats.calcStats(greenTasks,doneGreenTasks); //(int) ((doneGreenTasks / greenTasks) * 100);
                 statTasks[1] = jniStats.calcStats(yellowTasks,doneYellowTasks); //(int) ((doneYellowTasks / yellowTasks  ) * 100);
                 statTasks[2] = jniStats.calcStats(redTasks,doneRedTasks); //(int) ((doneRedTasks / redTasks) * 100);
                 intent.putExtra("Tasks", statTasks);
                 Log.e("[0] ", Integer.toString(statTasks[0]));
                 Log.e("[1] ", Integer.toString(statTasks[1]));
                 Log.e("[2] ", Integer.toString(statTasks[2]));
             }
                startActivityForResult(intent, 0);

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
                    taskDbHelper.deleteTask(changedItemName);
                    listData.clear();
                    readDatabase();
                    taskReminderService.updateTasks(listData);
                } else {
                    listItem = ((ListData) data.getSerializableExtra("Task"));
                    listData.clear();
                    taskDbHelper.deleteTask(changedItemName);
                    taskDbHelper.insert(listItem);
                    readDatabase();
                    taskReminderService.updateTasks(listData);


                }
            }
        }
    }
}



