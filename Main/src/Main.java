import logic.AKS;
import logic.TDTaskAssigner.SqrtIntervalDivider;
import logic.TrialDivision;

import java.math.BigInteger;
import java.sql.Time;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        /*        EventQueue.invokeLater(() -> {
                    Ui ui = new Ui();
        });*/
        long runStart = System.currentTimeMillis();

        long probe = 948895995818594437L;
        BigInteger probeBig = new BigInteger("20747222467734852078216952221076085874809964747211172927529925899121966847505496583100844167325500772");

     /*   SqrtIntervalDivider kek = new SqrtIntervalDivider(probe, 1);
        TrialDivision first = new TrialDivision(probe ,kek.getThresholds()[0], kek.getThresholds()[1]);
        first.test();
        System.out.println("finished: " + first.getIsFinished());
        System.out.println("Prime: " + first.getIsPrime());
*/

        AKS bigtest = new AKS (probeBig);

/*        while(true){
            if (first.getIsFinished()){
                System.out.println("finished: " + first.getIsFinished());
                System.out.println("Prime: " + first.getIsPrime());
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