package logic.Test.TrialDivision.TDThreadHandler;

import logic.Test.TrialDivision.TrialDivision;

import java.util.Observable;
import java.util.Observer;

import static java.lang.Thread.sleep;

public class TDThreadHandler implements Observer {
    private TrialDivision[] trialDivisions;
    private Thread[] trialDivisionThreads;
    private final TDThreadEvaluator evaluator;

    public TDThreadHandler(long number, int cores) {
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

 private long calls = 0;
//TODO: IF threads finsih at the same time only one call.

    @Override
    public void update(Observable o, Object arg) {
        if (!evaluator.isFinished()) {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName());
                try {
                    wait(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }

                if (!evaluator.isFinished()) {
                    evaluator.evaluateThreads();
                    if (evaluator.isFinished()) {
                        kill();
                        System.out.println("Killing threads...");
                        System.out.println("FINISHED. Prime: " + evaluator.isPrime());
                    }
                }
            }
        }
    }

}
