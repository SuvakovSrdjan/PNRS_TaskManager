package rtrk.pnrs1.ra174_2014.taskmanager.ListAdapterStuff;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rtrk.pnrs1.ra174_2014.taskmanager.R;

/**
 * Created by airjetsrka on 4/25/17.
 */

public class ListAdapter extends ArrayAdapter<ListData> {
    public ListAdapter(Context context, ArrayList<ListData> elements) {
        super(context, 0, elements);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ListData element = getItem(position);
        Date currentDate = new Date();
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date selectedDate = null;
        try {
            selectedDate = simpleDateFormat.parse(Integer.toString(element.day) +
                    "/" + Integer.toString(element.month) + "/" + Integer.toString(element.year));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.task_element, parent, false);
        }

        TextView txtDataPriority = (TextView) view.findViewById(R.id.rowPriority);
        final TextView txtDataName = (TextView) view.findViewById(R.id.rowName);
        TextView txtDataDate = (TextView) view.findViewById(R.id.dateShow);
        CheckBox chkBoxData = (CheckBox) view.findViewById(R.id.taskFinished);

        switch (element.priority){
            case 1:
                txtDataPriority.setBackgroundResource(R.color.green);
                break;
            case 2:
                txtDataPriority.setBackgroundResource(R.color.yellow);
                break;
            default:
                txtDataPriority.setBackgroundResource(R.color.red);
        }

        txtDataName.setText(element.taskName);
        chkBoxData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    txtDataName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                else
                    txtDataName.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
            }
        });

        RadioButton reminder = (RadioButton) view.findViewById(R.id.rowReminder);
        reminder.setEnabled(true);
        if(element.reminder){
            reminder.setChecked(true);
        } else {
            reminder.setChecked(false);
        }

        if(currentDate.getYear() == selectedDate.getYear()) {
            if(currentDate.getMonth() == selectedDate.getMonth()) {
                if(currentDate.getDay() == selectedDate.getDay()) {
                    txtDataDate.setText(R.string.date_today);
                } else if((currentDate.getTime()+1*24*60*60*1000)>selectedDate.getTime()) {
                    txtDataDate.setText(R.string.date_tomorow);
                } else if((currentDate.getTime()+7*24*60*60*1000)>selectedDate.getTime()) {
                    String dayOfWeek;
                    Locale RS= new Locale.Builder().setLanguage("sr").setScript("Latn").setRegion("RS").build();
                    SimpleDateFormat simpleFormat = new SimpleDateFormat("EEEEEEE", RS);

                    Calendar calendar = Calendar.getInstance();
                    dayOfWeek = simpleFormat.format(calendar.getTime());
                    txtDataDate.setText(dayOfWeek);
                } else {
                    txtDataDate.setText(simpleDateFormat.format(selectedDate));
                }
            }
            else {
                txtDataDate.setText(simpleDateFormat.format(selectedDate));
            }
        } else {
            txtDataDate.setText(simpleDateFormat.format(selectedDate));
        }

        return view;



    }
}
