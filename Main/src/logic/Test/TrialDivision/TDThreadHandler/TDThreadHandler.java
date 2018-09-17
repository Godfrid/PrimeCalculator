package logic.Test.TrialDivision.TDThreadHandler;

import logic.Test.TrialDivision.TrialDivision;

import java.util.Observable;
import java.util.Observer;

import static java.lang.Thread.sleep;

public class TDThreadHandler implements Observer {
    private TrialDivision[] trialDivisions;
    private Thread[] trialDivisionThreads;
    private final TDThreadEvaluator evaluator;
    private volatile boolean isEvaluating;

    public TDThreadHandler(long number, int cores) {
        isEvaluating = false;
        if (number < 10000) {
            trialDivisions = TDCreator.create(number, 1);
        } else {
            trialDivisions = TDCreator.create(number, cores);
        }
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

    @Override
    public void update(Observable o, Object arg) {
        if (!evaluator.isFinished()) {
            synchronized (this) {
                while (isEvaluating) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
                isEvaluating = true;
                System.out.println("START EVAL PROCESS: " + Thread.currentThread().getName());

                if (!evaluator.isFinished()) {
                    evaluator.evaluateThreads();
                    if (evaluator.isFinished()) {
                        System.out.println("Calling kill: " + Thread.currentThread().getName());
                        kill();

                        System.out.println("Killing threads...");
                        System.out.println("FINISHED. Prime: " + evaluator.isPrime());
                    }
                }
                isEvaluating = false;
                notifyAll();
            }
        }
    }

}
