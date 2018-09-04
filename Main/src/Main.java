import logic.TDTaskAssigner.SqrtIntervalDivider;

public class Main {
    public static void main(String[] args) {
/*        EventQueue.invokeLater(() -> {
                    Ui ui = new Ui();
        });*/

        SqrtIntervalDivider kek = new SqrtIntervalDivider(10L, 1);

        for (long threshold : kek.getThresholds()) {
            System.out.println("At i:" + threshold);
        }

   /*     int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of threads:" + cores);*/
    }
}