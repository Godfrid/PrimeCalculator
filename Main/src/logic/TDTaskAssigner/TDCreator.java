package logic.TDTaskAssigner;
import logic.Test.TrialDivision;

public class TDCreator {
/*    private int threads;

    TDCreator(){
        threads =
    }*/


// Creates an array of Trial division instances depending on available threads.
    public static TrialDivision[] create(long number) {
        int threads = Runtime.getRuntime().availableProcessors();
        SqrtIntervalDivider intervals = new SqrtIntervalDivider(number, threads);
        long[] thresholds = intervals.getThresholds();
        TrialDivision[] TDThreads = new TrialDivision[threads];

        for (int i = 0; i < threads; i++) {
            TDThreads[i] = new TrialDivision(number, thresholds[i], thresholds[i+1]);
        }
        return TDThreads;
    }

}
