package logic.GCD;

import java.math.BigInteger;

public  class  GreatestCommonDivisor {

    public static long GCD(long a, long b) {
        long t;
        while(b != 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public static BigInteger GCD(BigInteger number1, BigInteger number2) {
        return number1.gcd(number2);
    }

}
