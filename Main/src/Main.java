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
        BigInteger probeBig = new BigInteger("2074722246773485207821695222107608587480996474721117292752992589912196684750549658310084416732550077");

        SqrtIntervalDivider kek = new SqrtIntervalDivider(probe, 1);
        TrialDivision first = new TrialDivision(probe ,kek.getThresholds()[0], kek.getThresholds()[1]);
        first.test();
        System.out.println("finished: " + first.getIsFinished());
        System.out.println("Prime: " + first.getIsPrime());

/*        AKS bigtest = new AKS (probeBig);*/

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