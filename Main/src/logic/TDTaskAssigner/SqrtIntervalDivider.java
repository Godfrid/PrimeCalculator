package logic.TDTaskAssigner;

import java.lang.reflect.Array;

public class SqrtIntervalDivider {
    private long[] thresholds;

    public SqrtIntervalDivider(long number, int divideInto) {
        thresholds = new long[divideInto + 1];
        thresholds[0] = 3;
        thresholds[divideInto] = (long) Math.sqrt(number);
        //TODO: Handle: to low number or too big divider could close invalid intervals.
        for (int i = 1; i < divideInto; i++){
            double x = ((Math.sqrt(number)/divideInto) * i);
            thresholds[i] = (long) x;
        }
    }

    public long[] getThresholds() {
        return thresholds;
    }
}
