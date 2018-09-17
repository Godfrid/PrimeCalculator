package logic.Test.TrialDivision.TDThreadHandler;

import logic.Test.TrialDivision.TrialDivision;

import java.util.Observable;
import java.util.Observer;

import static java.lang.Thread.sleep;

public class TDThreadHandler implements Observer, Runnable {
    private TrialDivision[] trialDivisions;
    private Thread[] trialDivisionThreads;
    private TDThreadEvaluator evaluator;

    public TDThreadHandler(long number, int cores) {
        trialDivisions = TDCreator.create(number, cores);
        trialDivisionThreads = new Thread[trialDivisions.length];
        evaluator = new TDThreadEvaluator(trialDivisions);
        int i = 0;
        for (TrialDivision td: trialDivisions) {
            td.addObserver(this);
            trialDivisionThreads[i] = new Thread(td);
            i++;
        }
    }

    public void start() {
        for (Thread trialDivisionThread: trialDivisionThreads) {
            trialDivisionThread.start();
        }
        for (Thread trialDivisionThread: trialDivisionThreads) {
            try {
                trialDivisionThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void kill() {
        for (TrialDivision td: trialDivisions) {
            td.stop();
        }
    }

 private long calls = 0;
//TODO: IF threads finsih at the same time only one call.

    @Override
    public void update(Observable o, Object arg) {
        calls++;
        if (!evaluator.isFinished()) {
            evaluator.evaluateThreads();
            if (evaluator.isFinished()) {
                kill();
                System.out.println("Killing threads...");
                System.out.println("FINISHED. Prime: " + evaluator.isPrime());
            }
        }
    }

// Might not need to be on a different path.
    @Override
    public void run() {
        start();
    }
}
