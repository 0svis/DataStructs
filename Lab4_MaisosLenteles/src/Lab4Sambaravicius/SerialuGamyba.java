package Lab4Sambaravicius;

import laborai.gui.MyException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SerialuGamyba{

    private static final String ID_CODE = "Nr.";      //  ***** nauja
    private static int serNr = 1;               //  ***** nauja

    private Serialas[] serialai;
    private String[] raktai;
    private int kiekis = 0, idKiekis = 0;

    public static Serialas[] gamintiSerialus(int kiekis) {
        Serialas[] serialai = IntStream.range(0, kiekis)
                .mapToObj(i -> new Serialas.Builder().buildRandom())
                .toArray(Serialas[]::new);
        Collections.shuffle(Arrays.asList(serialai));
        return serialai;
    }

    public static String[] gamintiSerIds(int kiekis) {
        String[] raktai = IntStream.range(0, kiekis)
                .mapToObj(i -> ID_CODE + (serNr++))
                .toArray(String[]::new);
        Collections.shuffle(Arrays.asList(raktai));
        return raktai;
    }

    public Serialas[] gamintiIrZiuretiSerialus(int aibesDydis,
                                                       int aibesImtis) throws MyException {
        if (aibesImtis > aibesDydis) {
            aibesImtis = aibesDydis;
        }
        serialai = gamintiSerialus(aibesDydis);
        raktai = gamintiSerIds(aibesDydis);
        this.kiekis = aibesImtis;
        return Arrays.copyOf(serialai, aibesImtis);
    }

    // Imamas po vienas elementas iš sugeneruoto masyvo. Kai elementai baigiasi sugeneruojama
    // nuosava situacija ir išmetamas pranešimas.
    public Serialas ziuretiSerialus() {
        if (serialai == null) {
            throw new MyException("seriesNotGenerated");
        }
        if (kiekis < serialai.length) {
            return serialai[kiekis++];
        } else {
            throw new MyException("allSetStoredToMap");
        }
    }

    public String gautiIsBazesSerId() {
        if (raktai == null) {
            throw new MyException("seriesIdsNotGenerated");
        }
        if (idKiekis >= raktai.length) {
            idKiekis = 0;
        }
        return raktai[idKiekis++];
    }
}