package logic.test;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabin implements Test {

    private boolean isFinished;
    private boolean isPrime;
    private BigInteger n;

    public MillerRabin(BigInteger n) {
        this.n = n;
    }


    private boolean millerRabinPass(BigInteger a, BigInteger n) {

        BigInteger nMinusOne = n.subtract(BigInteger.ONE);

        BigInteger d = nMinusOne;
        int s = d.getLowestSetBit();
        d = d.shiftRight(s);
        BigInteger aToPower = a.modPow(d, n);

        if (aToPower.equals(BigInteger.ONE)) {
            return true;
        }

        for (int i = 0; i < s-1; i++) {
            if (aToPower.equals(nMinusOne)) {
                return true;
            }
            aToPower = aToPower.multiply(aToPower).mod(n);
        }
        return aToPower.equals(nMinusOne);
    }

    public void test() {
        for (int repeat = 0; repeat < 20; repeat++) {

            BigInteger a;
            do {
                a = new BigInteger(n.bitLength(), new Random());
            } while (a.equals(BigInteger.ZERO) || a.equals(n));

            if (!millerRabinPass(a, n)) {
                isPrime = false;
                isFinished = true;
                return;
            }
        }
        isPrime = true;
        isFinished = true;
    }


    public boolean isFinished() {
        return isFinished;
    }

    public boolean isPrime() {
        return isPrime;
    }
}
