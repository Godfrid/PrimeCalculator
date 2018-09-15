package logic;

import logic.Test.TrialDivision;

public class TDThreadEvaluator implements Runnable {

    private TrialDivision[] trialDivisions;
    private boolean isFinished;
    private boolean isPrime;

    public TDThreadEvaluator(TrialDivision[] trialDivisions) {
        this.trialDivisions = trialDivisions;
        this.isFinished = false;
        this.isPrime = true;
    }

    private void evaluateThreads() {
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
                if (i == trialDivisions.length){
                    isFinished = true;
                }
            }
        }
    }

    @Override
    public void run() {
        while (!isFinished) evaluateThreads();
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isPrime() {
        return isPrime;
    }
}
