package rtrk.pnrs1.ra174_2014.taskmanager.ListAdapterStuff;

import java.io.Serializable;
/**
 * Created by airjetsrka on 4/25/17.
 */

public class ListData implements Serializable{

    public String taskName;
    public int priority; // 1-red, 2-yellow, 3-green
    public int day;     //selected day
    public int month;   //selected month
    public int year;    //selected year
    public boolean checkBox; //on or off
    public boolean reminder; // reminder on or off

    public ListData(String taskName, int priority, int day
            ,int month,int year,boolean CheckBox, boolean reminder){
        this.taskName=taskName;
        this.priority=priority;
        this.day=day;
        this.month=month;
        this.year=year;
        this.checkBox=CheckBox;
        this.reminder = reminder;
        }
}
