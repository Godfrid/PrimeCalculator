import logic.Test.MillerRabin;
import logic.Test.TrialDivision.TDThreadHandler.TDThreadHandler;

import java.math.BigInteger;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
// Test nums: not prime: 8545321313331233313L,
// prime: 8223331132123323353L, 3240987423698742331L, 948895995818594437L, "2074722246773485207821695222107608587480996474721117292752992589912196684750549658310084416732550077"

        /*        EventQueue.invokeLater(() -> {
                    Ui ui = new Ui();
        });*/
        long runStart = System.currentTimeMillis();

        TDThreadHandler test1 = new TDThreadHandler(3240987423698742331L, 0);
        test1.start();


/*        long probe = 948895995818594437L;
        BigInteger probeBig = new BigInteger("19");

        MillerRabin mrTest = new MillerRabin();
        mrTest.test(probeBig);
        System.out.println("Prime: " + mrTest.isPrime());*/

/*        SqrtIntervalDivider kek = new SqrtIntervalDivider(probe, 1);
        TrialDivision first = new TrialDivision(probe ,kek.getThresholds()[0], kek.getThresholds()[1]);
        first.test();
        System.out.println("finished: " + first.isFinished());
        System.out.println("Prime: " + first.isPrime());*/

/*        AKS bigtest = new AKS (probeBig);*/

/*        while(true){
            if (first.isFinished()){
                System.out.println("finished: " + first.isFinished());
                System.out.println("Prime: " + first.isPrime());
                break;
            }
            sleep(50000);
        }*/


/*
        for (long threshold : kek.getThresholds()) {
            System.out.println("At i:" + threshold);
        }
*/

   /*     int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of threads:" + cores);*/

        System.out.println("runtime: " + (System.currentTimeMillis() - runStart));
    }
}