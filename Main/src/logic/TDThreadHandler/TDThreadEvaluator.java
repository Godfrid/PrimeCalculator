package logic.TDThreadHandler;

import logic.Test.TrialDivision;

import static java.lang.Thread.sleep;

public class TDThreadEvaluator {

    private TrialDivision[] trialDivisions;
    private volatile boolean isFinished;
    private boolean isPrime;

    public TDThreadEvaluator(TrialDivision[] trialDivisions) {
        this.trialDivisions = trialDivisions;
        this.isFinished = false;
        this.isPrime = true;
    }

    public synchronized void evaluateThreads() {
        System.out.println("Eval started");
        if (isFinished) {
            System.out.println("Eval ended short");
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

    System.out.println("Eval ended");
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isPrime() {
        return isPrime;
    }

}
