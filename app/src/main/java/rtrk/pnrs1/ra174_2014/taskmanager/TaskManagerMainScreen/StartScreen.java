package rtrk.pnrs1.ra174_2014.taskmanager.TaskManagerMainScreen;

import android.app.Activity;
import android.content.Intent;
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
    ArrayList<ListData> listData;
    ListAdapter listAdapter;
    ListData listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);


        initStuff();

       addToListTestFunction();

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
                finish();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), AddTaskModel.class);
                intent.putExtra(getResources().getString(R.string.btn1),getResources().getString(R.string.save_changes));
                intent.putExtra(getResources().getString(R.string.btn2),getResources().getString(R.string.delete_list_element));
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG","got in1");
        if(requestCode==1) {
            Log.d("TAG","got in2");
            if (resultCode == Activity.RESULT_OK) {
                Log.d("TAG","got in3");
                listItem = ((ListData) data.getSerializableExtra("Task"));
                listData.add(listItem);
                listAdapter.notifyDataSetChanged();
            }
        }
    }


    private void addToListTestFunction() {/*
        ListData tmpString = new ListData("Oh pls work",1,14,5,2017,false,true);
        ListData tmpString1 = new ListData("I really like sleeping",2,30,4,2017,true,false);
        ListData tmpString2 = new ListData("Not sure what to put",3,29,4,2017,false,true);
        ListData tmpString3 = new ListData("Does this even work tho",1,28,4,2017,true,false);
        ListData tmpString4 = new ListData("Pod Mac",3,27,4,2017,true,false);*/
        ListData tmpString5 = new ListData("Pod Mac",3,26,4,2017,true,false);/*
        ListData tmpString6 = new ListData("Pod Mac",3,25,4,2017,true,true);
        ListData tmpString7 = new ListData("Pod Mac",3,24,4,2017,true,true);
        ListData tmpString8 = new ListData("come on",2,2,5,2017,false,true);
        ListData tmpString9 = new ListData("pls work",1,2,6,2017,false,false);
        ListData tmpString10 = new ListData("googly eyes",1,2,5,2018,false,false);
        listAdapter.add(tmpString);
        listAdapter.add(tmpString1);
        listAdapter.add(tmpString2);
        listAdapter.add(tmpString3);
        listAdapter.add(tmpString4);*/
        listData.add(tmpString5);/*
        listAdapter.add(tmpString6);
        listAdapter.add(tmpString7);
        listAdapter.add(tmpString8);
        listAdapter.add(tmpString9);
        listAdapter.add(tmpString10);*/
    }

}
