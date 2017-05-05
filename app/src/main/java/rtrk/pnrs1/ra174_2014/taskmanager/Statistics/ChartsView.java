package rtrk.pnrs1.ra174_2014.taskmanager.Statistics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;



/**
 * Created by airjetsrka on 5/5/17.
 */

public class ChartsView extends View{


    //percentage to be drawn, will be inherited from list
    private Paint mPaint;
    private int greenMax=0;
    private int redMax=0;
    private int yellowMax=0;

    //Percentage currently drawn
    private int greenDrawn=0;
    private int redDrawn=0;
    private int yellowDrawn=0;

    //Init circles, although they are rectangles on which the circles are drawn
    private RectF redCircle = new RectF();
    private RectF yellowCircle = new RectF();
    private RectF greenCircle = new RectF();

    //Ascync Task function
    private ChartAnimate animate = new ChartAnimate();

    //Percentage shown inside circles
    private String greenPercentage;
    private String yellowPercentage;
    private String redPercentage;

    //String below circles
    private String highPryTask = "Visoki Prioritet";
    private String lowPryTask = "Niski Prioritet";
    private String medPryTask = "Srednji Prioritet";


    public ChartsView(Context context){
        super(context);
    }



    public ChartsView(Context context, int red, int green, int yellow){
        super(context);
        this.greenMax = green;
        this.redMax = red;
        this.yellowMax = yellow;
        mPaint = new Paint();
        animate.execute();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);


        //make rectangles on which the circles will be drawn
        redCircle.set(canvas.getWidth() * 4 / 12,
                canvas.getHeight() / 10,
                canvas.getWidth() * 8 / 12,
                canvas.getHeight() * 3 / 10);
        yellowCircle.set(canvas.getWidth() / 12,
                canvas.getHeight() * 5 / 10,
                canvas.getWidth() * 5 / 12,
                canvas.getHeight() * 7 / 10);
        greenCircle.set(canvas.getWidth() * 7 / 12,
                canvas.getHeight() * 5 / 10,
                canvas.getWidth() * 11 / 12,
                canvas.getHeight() * 7 / 10);


        //Animate circles drawing (each time an async task is called, the drawn color integer is
        //going to get bigger

        mPaint.setColor(Color.BLUE);
        canvas.drawOval(redCircle, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(redCircle, -90, (float)(redDrawn * 3.6), true, mPaint);


        mPaint.setColor(Color.BLUE);
        canvas.drawOval(yellowCircle, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(yellowCircle, -90, (float)(yellowDrawn * 3.6), true, mPaint);


        mPaint.setColor(Color.BLUE);
        canvas.drawOval(greenCircle, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(greenCircle, -90, (float)(greenDrawn * 3.6), true, mPaint);

        //Percentage strings
        mPaint.setColor(Color.BLACK); //for text

        //text Scaling
        mPaint.setTextSize(32 * getResources().getDisplayMetrics().density);

        canvas.drawText(redPercentage, 0, redPercentage.length(),
                redCircle.centerX() - 20 * getResources().getDisplayMetrics().density,
                redCircle.centerY() + 11 * getResources().getDisplayMetrics().density,
                mPaint);
        canvas.drawText(yellowPercentage, 0,
                yellowPercentage.length(),
                yellowCircle.centerX() - 20 * getResources().getDisplayMetrics().density,
                yellowCircle.centerY() + 11 * getResources().getDisplayMetrics().density,
                mPaint);
        canvas.drawText(greenPercentage, 0, greenPercentage.length(),
                greenCircle.centerX() - 20 * getResources().getDisplayMetrics().density,
                greenCircle.centerY() + 11 * getResources().getDisplayMetrics().density,
                mPaint);


        //Strings under the circles
        mPaint.setTextSize(16 * getResources().getDisplayMetrics().density);


        canvas.drawText(highPryTask, 0, highPryTask.length(),
                redCircle.centerX() - 50 * getResources().getDisplayMetrics().density,
                redCircle.centerY() + 85 * getResources().getDisplayMetrics().density,
                mPaint); // 220, 200
        canvas.drawText(medPryTask, 0, medPryTask.length(),
                yellowCircle.centerX() - 50 * getResources().getDisplayMetrics().density,
                yellowCircle.centerY() + 85 * getResources().getDisplayMetrics().density,
                mPaint);
        canvas.drawText(lowPryTask, 0, lowPryTask.length(),
                greenCircle.centerX() - 50 * getResources().getDisplayMetrics().density,
                greenCircle.centerY() + 85 * getResources().getDisplayMetrics().density,
                mPaint);
    }


    private class ChartAnimate extends AsyncTask<Void, Void, Void>{
        boolean redDone = false;
        boolean yellowDone = false;
        boolean greenDone = false;

        @Override
        protected Void doInBackground(Void... params) {

            while(!redDone || !yellowDone || !greenDone){

                if(redDrawn < redMax)
                    redDrawn++;
                else
                    redDone = true;
                if(greenDrawn < greenMax)
                    greenDrawn++;
                else
                    greenDone = true;
                if(yellowDrawn < yellowMax)
                    yellowDrawn++;
                else
                    yellowDone = true;

                redPercentage = Integer.toString(redDrawn) + "%";
                yellowPercentage = Integer.toString(yellowDrawn) + "%";
                greenPercentage = Integer.toString(greenDrawn) + "%";

                postInvalidate();
                SystemClock.sleep(1);
            }

            return null;
        }

        @Override
        protected void onPreExecute(){

        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            invalidate();
        }

    }
}
