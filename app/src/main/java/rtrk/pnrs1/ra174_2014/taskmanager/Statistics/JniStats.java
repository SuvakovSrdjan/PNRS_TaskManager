package rtrk.pnrs1.ra174_2014.taskmanager.Statistics;

/**
 * Created by airjetsrka on 6/12/17.
 */

public class JniStats {

    public native int calcStats(int total,int done);

    static {
        System.loadLibrary("jnistats");
    }
}
