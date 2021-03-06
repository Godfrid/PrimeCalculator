package logic.test.TrialDivision;


import java.util.Observable;

import static java.lang.Thread.sleep;

public class TrialDivision extends Observable implements Runnable {

    private long number;
    private long startAt;
    private long endAt;
    private boolean isPrime;
    private volatile boolean isFinished;

    public TrialDivision(long number, long startAt, long endAt) {
        this.number = number;
        this.startAt = startAt;
        this.endAt = endAt;
        this.isPrime = true;
    }

    public void test() {

        if (number == 3 || number == 2) {
            this.Finish();
            return;
        } else if (number % 2 == 0) {
            isPrime = false;
            this.Finish();
            return;
        }

        long divider = (startAt % 2 == 0) ? startAt + 1 : startAt;
        System.out.println(" NUMBER TESTED: " + number + " Test starts at: " + startAt + " ends at: " + endAt);
        while (divider < endAt & !isFinished) {
            if (number % divider == 0) {
                isPrime = false;
                System.out.println(divider);
                break;
            }
            divider += 2;
        }
        this.Finish();
    }

    private void Finish() {
        if (!isFinished) {
            isFinished = true;
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void run() {
        test();
    }

    public boolean isPrime() {
        return isPrime;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void stop () {
        isFinished = true;
    }

// Just for testing:
    public long getStartAt() {
        return startAt;
    }

    public long getEndAt() {
        return endAt;
    }
}
