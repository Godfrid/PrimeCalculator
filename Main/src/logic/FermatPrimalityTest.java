package logic;

public class FermatPrimalityTest implements Runnable {
    private long a;
    private long p;
    private int tests;
    private boolean isPrime;

    public FermatPrimalityTest (long a, long p){
        this.a = a;
        this.p = p;
        this.tests = 1;
    }

    public FermatPrimalityTest (long a, long p, int tests) {
        this.a = a; // testing number
        this.p = p; // number tested
        this.tests = tests;
    }
// TODO: More tests, and fast modular exponentiation.
    public void test() {
        for (int testsMade = 0; tests > testsMade; testsMade++){
            long x = (long) Math.pow(a, p-1) % p;
            isPrime = x == 1;
            if(!isPrime) {
                break;
            }
        }
    }

    public boolean getIsPrime() {
        return isPrime;
    }

    @Override
    public void run() {

    }
}
