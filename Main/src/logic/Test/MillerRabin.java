package logic.Test;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabin {

    private boolean isFinished;
    private boolean isPrime;

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

    public void millerRabin(BigInteger n) {
        for (int repeat = 0; repeat < 20; repeat++) {
            BigInteger a;
            do {
                a = new BigInteger(n.bitLength(), new Random());
            } while (a.equals(BigInteger.ZERO));

            if (!millerRabinPass(a, n)) {
                isPrime = false;
                return;
            }
        }
        isPrime = true;
    }


    public boolean isFinished() {
        return isFinished;
    }

    public boolean isPrime() {
        return isPrime;
    }
}
