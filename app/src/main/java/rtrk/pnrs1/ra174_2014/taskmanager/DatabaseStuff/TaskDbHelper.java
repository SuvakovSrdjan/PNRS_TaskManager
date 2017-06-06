package rtrk.pnrs1.ra174_2014.taskmanager.DatabaseStuff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import rtrk.pnrs1.ra174_2014.taskmanager.ListAdapterStuff.ListData;

/**
 * Created by srka on 6/1/17.
 */

public class TaskDbHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "tasks.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "task";
    public static final String COLUMN_TASK_NAME = "TaskName";
    public static final String COLUMN_TASK_COMPLETION_TIME = "CompletionTime";
    public static final String COLUMN_TASK_DESCRIPTION = "TaskDescription";
    public static final String COLUMN_TASK_PRIORITY = "TaskPriority";
    public static final String COLUMN_TASK_REMINDER_ENABLED = "TaskReminderEnabled";
    public static final String COLUMN_TASK_DONE = "TaskDone";

    private SQLiteDatabase mDb = null;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_TASK_NAME + " TEXT, " +
                COLUMN_TASK_COMPLETION_TIME + " TEXT, " +
                COLUMN_TASK_DESCRIPTION + " TEXT, " +
                COLUMN_TASK_PRIORITY + " INTEGER, " +
                COLUMN_TASK_REMINDER_ENABLED + " INTEGER, " +
                COLUMN_TASK_DONE + " INTEGER);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(ListData element){
        SQLiteDatabase dataBase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        int reminder = 0,taskDone = 0;
        if(element.reminder)
            reminder = 1;
        if(element.checkBox)
            taskDone = 1;


        contentValues.put(COLUMN_TASK_NAME, element.taskName);
        contentValues.put(COLUMN_TASK_DESCRIPTION, element.taskDescription);
        contentValues.put(COLUMN_TASK_COMPLETION_TIME, element.parseTime());
        contentValues.put(COLUMN_TASK_PRIORITY, element.priority);
        contentValues.put(COLUMN_TASK_REMINDER_ENABLED, reminder);
        contentValues.put(COLUMN_TASK_DONE, taskDone);
        dataBase.insert(TABLE_NAME, null, contentValues);
        Log.d("INSERT DB","DONE");
        close();
    }


    public ArrayList<ListData> readTasks() {
        SQLiteDatabase dataBase = getReadableDatabase();
        Cursor cursor = dataBase.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<ListData> tasks = new ArrayList<>();

        if(cursor.getCount() <= 0){
            return null;
        }

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            tasks.add(createTask(cursor));
        }
        close();
        return tasks;
    }

    public ListData readTask(String taskName){
        SQLiteDatabase dataBase = getReadableDatabase();
        Cursor cursor = dataBase.query(TABLE_NAME,null,COLUMN_TASK_NAME + "=?",
                new String[] {taskName}, null, null, null);

        cursor.moveToFirst();
        ListData task = createTask(cursor);

        close();
        return task;
    }

    public ListData createTask(Cursor cursor){
        String taskName = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
        String taskTime = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_COMPLETION_TIME));
        String taskDescription = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION));
        int taskPri = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_PRIORITY));
        int reminder = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_REMINDER_ENABLED));
        int isDone = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_DONE));

        int year,month,day,hour,minute;
        String[] date_parts = taskTime.split("/");
        String[] year_and_hours = date_parts[2].split(" ");
        String[] hours_minutes = year_and_hours[1].split(":");

         year = Integer.parseInt(year_and_hours[0]);
         month = Integer.parseInt(date_parts[1]);
         day = Integer.parseInt(date_parts[0]);
         hour = Integer.parseInt(hours_minutes[0]);
         minute = Integer.parseInt(hours_minutes[1]);

        return new ListData(taskName, taskDescription, taskPri,
                day, month, year, hour, minute,
                reminder == 1 ? true : false,
                isDone == 1? true : false);
    }

    public void deleteTask(String taskName){
        SQLiteDatabase dataBase = getWritableDatabase();
        dataBase.delete( TABLE_NAME , COLUMN_TASK_NAME + "=?", new String[] {taskName});
        Log.e("NOT AN ERR","HE DIDS IT");
        close();
    }

    public void deleteDB(){
        SQLiteDatabase dataBase = getWritableDatabase();
        dataBase.delete(TABLE_NAME, null, null);
    }

}
