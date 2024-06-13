package laborai.Lab3Zilinskas;

import laborai.demo.AutoGamyba;
import laborai.demo.Automobilis;
import laborai.demo.Timekeeper;
import laborai.gui.MyException;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.BstSetKTUx;
import laborai.studijosktu.BstSetKTUx2;
import laborai.studijosktu.SortedSetADTx;

import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

public class GreitaveikaRies {

    public static final String FINISH_COMMAND = "finish";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");

    private static final String[] TYRIMU_VARDAI = {"addBstRec", "addBstIte", "addAvlRec", "removeBst", "removeAVL",
            "containsBst", "addAVL"};
    private static final int[] TIRIAMI_KIEKIAI = {10000, 20000, 40000, 80000, 16000, 32000};

    private final BlockingQueue resultsLogger = new SynchronousQueue();
    private final Semaphore semaphore = new Semaphore(-1);
    private final Timekeeper tk;
    private final String[] errors;

    private final SortedSetADTx<Riesutas> aSeries = new BstSetKTUx(new Riesutas(), Riesutas.pagalPav); // pagal kaina reik
    private final SortedSetADTx<Riesutas> aSeries2 = new BstSetKTUx2(new Riesutas());
    private final SortedSetADTx<Riesutas> aSeries3 = new AvlSetKTUx(new Riesutas());

    public GreitaveikaRies() {
        semaphore.release();
        tk = new Timekeeper(TIRIAMI_KIEKIAI, resultsLogger, semaphore);
        errors = new String[]{
            MESSAGES.getString("error1"),
            MESSAGES.getString("error2"),
            MESSAGES.getString("error3"),
            MESSAGES.getString("error4")
        };
    }

    public void pradetiTyrima() {
        try {
            SisteminisTyrimas();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void SisteminisTyrimas() throws InterruptedException {
        try {
            for (int k : TIRIAMI_KIEKIAI) {
                Riesutas[] riesMas = RiesutGamyba.generuotiIrIsmaisyti(k, 1.0);
                aSeries.clear();
                aSeries2.clear();
                aSeries3.clear();
                tk.startAfterPause();
                tk.start();
                for (Riesutas a : riesMas) {
                    aSeries.add(a);
                }
                tk.finish(TYRIMU_VARDAI[0]);
                for (Riesutas a : riesMas) {
                    aSeries2.add(a);
                }
                tk.finish(TYRIMU_VARDAI[1]);
                for (Riesutas a : riesMas) {
                    aSeries3.add(a);
                }
                tk.finish(TYRIMU_VARDAI[2]);
                for (Riesutas a : riesMas) {
                    aSeries.remove(a);
                }
                tk.finish(TYRIMU_VARDAI[3]);
                /* reikia realizuoti metoda
                for(Riesutas a : riesMas){
                    aSeries3.remove(a);
                }
                */

                tk.finish(TYRIMU_VARDAI[4]);
                for(Riesutas a : riesMas){
                    aSeries.contains(a);
                }
                tk.finish(TYRIMU_VARDAI[5]);
                for(Riesutas a : riesMas){
                    aSeries3.add(a);
                }
                tk.finish(TYRIMU_VARDAI[6]);
                tk.seriesFinish();
            }
            tk.logResult(FINISH_COMMAND);
        } catch (MyException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                tk.logResult(errors[e.getCode()] + ": " + e.getMessage());
            } else if (e.getCode() == 4) {
                tk.logResult(MESSAGES.getString("msg3"));
            } else {
                tk.logResult(e.getMessage());
            }
        }
    }

    public BlockingQueue<String> getResultsLogger() {
        return resultsLogger;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
