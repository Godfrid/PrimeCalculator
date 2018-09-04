package logic;


public class TrialDivision implements Runnable  {

    private long number;
    private long startAt;
    private long endAt;
    private boolean isPrime;
    private boolean isFinished;

    public TrialDivision(long number, long startAt, long endAt) {
        this.number = number;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void test(long number, long startAt, long endAt){
    }


    @Override
    public void run() {

    }
}
