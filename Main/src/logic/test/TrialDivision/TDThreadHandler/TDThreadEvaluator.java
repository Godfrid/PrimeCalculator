package logic.test.TrialDivision.TDThreadHandler;

import logic.test.TrialDivision.TrialDivision;

import static java.lang.Thread.sleep;

public class TDThreadEvaluator {

    private TrialDivision[] trialDivisions;
    private boolean isFinished;
    private boolean isPrime;

    public TDThreadEvaluator(TrialDivision[] trialDivisions) {
        this.trialDivisions = trialDivisions;
        this.isFinished = false;
        this.isPrime = true;
    }

    public void evaluateThreads() {
        if (isFinished) {
            return;
        }

        int i = 0;

        for (TrialDivision trialDivision: trialDivisions) {
            if (trialDivision.isFinished() & !trialDivision.isPrime()) {
                isFinished = true;
                isPrime = false;
                break;
            } else if (trialDivision.isFinished() & trialDivision.isPrime()) {
                i++;
                if (i == trialDivisions.length) {
                    isFinished = true;
                }
            }
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isPrime() {
        return isPrime;
    }

}
