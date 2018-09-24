package logic.test.TrialDivision.TDThreadHandler;
import logic.test.TrialDivision.TrialDivision;
import logic.plugin.SqrtIntervalDivider;

public class TDCreator {

// Creates an array of Trial division instances depending on given number or available processor cores (if threads: 0 param is given in constructor).
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
