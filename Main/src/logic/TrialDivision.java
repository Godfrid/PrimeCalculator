package logic;


public class TrialDivision implements Runnable {

    private long number;
    private long startAt;
    private long endAt;
    private boolean isPrime;
    private boolean isFinished;

    public TrialDivision(long number, long startAt, long endAt) {
        this.number = number;
        this.startAt = startAt;
        this.endAt = endAt;
        this.isPrime = true;
    }

    public void test() {
        long divider = (startAt % 2 == 0) ? startAt + 1 : startAt;
        System.out.println(" num: " + number + " startAt: " + startAt + " divider: " + divider + " endAt: " + endAt);
        while (divider <= endAt) {
            if (number % divider == 0) {
                isPrime = false;
                System.out.println("Divider: " + divider);
                break;
            }
            divider += 2;
        }
        isFinished = true;
    }

    @Override
    public void run() {
        test();
    }

    public boolean getIsPrime() {
        return isPrime;
    }

    public boolean getIsFinished() {
        return isFinished;
    }
}
