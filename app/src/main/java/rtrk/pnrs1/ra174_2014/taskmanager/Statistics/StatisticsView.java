package rtrk.pnrs1.ra174_2014.taskmanager.Statistics;


/**
 * Created by airjetsrka on 3/28/17.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import rtrk.pnrs1.ra174_2014.taskmanager.R;
import rtrk.pnrs1.ra174_2014.taskmanager.TaskManagerMainScreen.StartScreen;

public class StatisticsView extends AppCompatActivity implements StatisticsModel.View {

    Button statsBack;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_view);
        init();

    }

    private void init(){
        statsBack = (Button)findViewById(R.id.statsBack);
        statsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToNextActivity();
            }
        });

        layout=(RelativeLayout)findViewById(R.id.realtive_layout);
        layout.addView(new ChartsView(this,90,68,30));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        proceedToNextActivity();
    }
    public void proceedToNextActivity() {
        Intent intent = new Intent(StatisticsView.this, StartScreen.class);
        startActivity(intent);
        finish();
    }
}
