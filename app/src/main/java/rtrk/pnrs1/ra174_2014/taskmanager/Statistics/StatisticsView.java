package rtrk.pnrs1.ra174_2014.taskmanager.Statistics;


/**
 * Created by airjetsrka on 3/28/17.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rtrk.pnrs1.ra174_2014.taskmanager.R;
import rtrk.pnrs1.ra174_2014.taskmanager.TaskManagerMainScreen.StartScreen;

public class StatisticsView extends AppCompatActivity implements StatisticsModel.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_view);
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
