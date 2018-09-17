package logic.Test.TrialDivision.TDThreadHandler;
import logic.Test.TrialDivision.TrialDivision;
import logic.plugin.SqrtIntervalDivider;

public class TDCreator {

// Creates an array of Trial division instances depending on available threads.
    public static TrialDivision[] create(long number, int threads) {
        if (threads == 0) {
            threads = Runtime.getRuntime().availableProcessors();
        }

        SqrtIntervalDivider intervals = new SqrtIntervalDivider(number, threads);
        long[] thresholds = intervals.getThresholds();
        TrialDivision[] TDInstances = new TrialDivision[threads];

        for (int i = 0; i < threads; i++) {
            TDInstances[i] = new TrialDivision(number, thresholds[i], thresholds[i+1]);
        }
        return TDInstances;
    }

}
