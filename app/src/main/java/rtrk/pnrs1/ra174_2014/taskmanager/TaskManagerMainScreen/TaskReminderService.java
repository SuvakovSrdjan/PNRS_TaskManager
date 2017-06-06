package rtrk.pnrs1.ra174_2014.taskmanager.TaskManagerMainScreen;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.FileDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rtrk.pnrs1.ra174_2014.taskmanager.ListAdapterStuff.ListData;
import rtrk.pnrs1.ra174_2014.taskmanager.R;

public class TaskReminderService extends Service{


    ReminderThread remThread = new ReminderThread();
    ArrayList<ListData> tasks = new ArrayList<>();
    private static final String TAG = "RemService";
    IBinder reminderBinder = new ReminderBinder();


    public TaskReminderService() {
    }

    public class ReminderBinder extends Binder {
        public TaskReminderService getService() {
            return TaskReminderService.this;
        }
    }


    @Override
    public void onCreate(){
        super.onCreate();
        remThread.start();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        remThread.interrupt();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Bundle bundle = intent.getExtras();
        tasks = ((ArrayList<ListData>) bundle.get("Task"));

        if(!tasks.isEmpty()){
            for(ListData t:tasks) {
                Log.d(TAG, "onBind: Task: " + t.toString());
            }

        } else {
            Log.d(TAG,"onBind: Tasks empty");
        }


        return reminderBinder;
    }


     class ReminderThread extends Thread {

         List<ListData> tasksWithReminder = new ArrayList<ListData>();
         Date currDate;
         String dateFormat = "dd/MM/yyyy";
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

         public ReminderThread(){}

         @Override
         public synchronized void start(){
             super.start();
         }

         public void run(){
             if(!isInterrupted()){
                 currDate = new Date();

                 if(!tasks.isEmpty()) {
                     for (ListData t : tasks) {
                         Log.d(TAG, "NOTIFICATION THREAD RUN: Task: " + t.toString());
                                       }
                 } else {
                     Log.d(TAG, "NOTIFICATION THREAD RUN: Tasks empty");
                 }

                 while(true){
                     Log.d(TAG,"NOTIFICATION THREAD RUN: Checking tasks with reminders");

                     for(ListData t : tasks){
                         Date taskDate = new Date();
                         int hour = t.hour;
                         int minute = t.minute;
                         try {
                             taskDate = simpleDateFormat.parse(Integer.toString(t.day) +
                                     "/" + Integer.toString(t.month) + "/" + Integer.toString(t.year));
                         } catch (ParseException e) {
                             e.printStackTrace();
                         }


                         if(taskDate.getYear() == currDate.getYear()){
                             if(taskDate.getMonth() == currDate.getMonth()){
                                 if(taskDate.getDay() == currDate.getDay()){
                                     if(hour == currDate.getHours()){
                                         if(minute - currDate.getMinutes() <=15){
                                             if(t.reminder){
                                                 tasksWithReminder.add(t);
                                                 t.reminder = false;
                                             }
                                         }
                                     }
                                 }
                             }
                         }
                     }


                     Log.d(TAG,"NOTIFICATION THREAD RUN, creating notifications");

                     for(ListData t: tasksWithReminder){
                         android.support.v4.app.NotificationCompat.Builder remBuilder =
                                 new NotificationCompat.Builder(TaskReminderService.this)
                                        .setContentTitle("Task Notification")
                                        .setContentText(t.taskName)
                                        .setSmallIcon(R.mipmap.ic_launcher);


                         Intent resultIntent = new Intent(TaskReminderService.this, StartScreen.class);
                         PendingIntent pendingIntent = PendingIntent.getActivity(
                                 TaskReminderService.this,
                                 0,
                                 resultIntent,
                                 PendingIntent.FLAG_UPDATE_CURRENT
                         );

                         remBuilder.setContentIntent(pendingIntent);

                         int reminderNotificationId = 001;
                         NotificationManager reminderNotifMan =
                                 (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                         reminderNotifMan.notify(reminderNotificationId,remBuilder.build());

                         tasksWithReminder.remove(t);
                     }


                     try {
                         Thread.sleep(1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }


             }
         }



     }
    public void updateTasks(ArrayList<ListData> t){

        tasks = new ArrayList<ListData>(t);
    }


}
