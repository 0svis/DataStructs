package laborai.Lab3Zilinskas;

import laborai.demo.Automobilis;
import laborai.studijosktu.Ks;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.SortedSetADTx;
import laborai.studijosktu.SetADT;
import laborai.studijosktu.BstSetKTUx;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
public class RiesutuTestai {
    static Riesutas[] RiesutuMas;
    static SortedSetADTx<Riesutas> rRies = new BstSetKTUx(new Riesutas(), Riesutas.pagalPav);

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
    }
}
