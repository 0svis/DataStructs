/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab2Zilinskas;

/**
 *
 * @author Osvaldas
 */
import studijosKTU.Ks;
import studijosKTU.ListKTU;
import studijosKTU.ListKTUx;
import studijosKTU.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class Greitaveika {
    Kompiuteris[] kompiuteriuMas1;
    ListKTU<Kompiuteris> aKomp = new ListKTU<>();
    Random ag = new Random();
    
    int[] tiriamiKiekiai = {2000, 4000, 8000, 16000, 32000, 64000}; // Tiriami objektų kiekiai greitaveikoje
    
    void generuotiKompiuterius(int kiekis)
    {
        String[][] am = { //galimi kompiuteriai ir ju procesoriai
            {"Lenovo", "i9-12700K", "5800X", "i7-9700F", "5950X", "i5-11600K", "3600X"},
            {"Dell", "3300X", "G5600", "3000G", "E5-6800", "9999X", "i7-12345U", "4700G"},
            {"MSI", "2600H", "J4105", "300GE", "M3-7100Y", "3200+", "i5-7600T", "6800XT"},
            {"HP", "i7-9700F", "i9-12700K", "5800X", "i5-11600K", "3600X", "i3-9100", "3300X"},
            {"Acer", "5950X", "i7-12345U", "4700G", "i9-8888K", "2600H", "3000G", "E5-6800"},
            {"Asus", "i5-7600T", "6800XT", "W-3275", "M3-7100Y", "3200+", "J4105", "300GE"},
            {"Toshiba", "i9-12700K", "5800X", "i7-9700F", "5950X", "i5-11600K", "3600X", "i3-9100", "3300X"},
            {"Sony", "i7-12345U", "4700G", "i9-8888K", "2600H", "3000G", "E5-6800", "9999X"}        
        };
        String[][] bm = { //galimi kompiuteriai ir ju grafines plokstes
            {"Lenovo", "RTX4060", "RTX3060", "RTX2080", "GTX960", "GTX980TI", "GT670"},
            {"Dell", "RTX3090", "RTX3070", "RTX2060", "GTX1660", "GTX1050Ti", "GT1030"},
            {"MSI", "RTX3060Super", "RTX3050Ti", "RTX2060Super", "GTX1070", "GTX970", "GT610"},
            {"HP", "RTX3080", "RTX3060", "RTX2070", "GTX970", "GTX980", "GT640"},
            {"Acer", "RTX3080Ti", "RTX3070", "RTX2070Super", "GTX1060", "GTX1050", "GT720"},
            {"Asus", "RTX3090", "RTX3060", "RTX2070", "GTX1660", "GTX1050Ti", "GT1030"},
            {"Toshiba", "RTX3080", "RTX3060", "RTX2060", "GTX1660", "GTX1050", "GT730"},
            {"Sony", "RTX4080", "RTX4060", "RTX2080Ti", "GTX950", "GTX980Ti", "GT620"}
        };
        kompiuteriuMas1 = new Kompiuteris[kiekis];
        ag.setSeed(2016);
        for (int i=0; i<kiekis; i++)
        {
            int ma = ag.nextInt(am.length);
            int mo = ag.nextInt(am[ma].length-3)+1;
            kompiuteriuMas1[i]= new Kompiuteris(am[ma][0], 2006 + ag.nextInt(15), am[ma][mo], ag.nextDouble(20), bm[ma][mo], ag.nextDouble(), "HDD", ag.nextInt(10000), 50 + ag.nextDouble(1600));
        }
        Collections.shuffle(Arrays.asList(kompiuteriuMas1));
        aKomp.clear();
        for(Kompiuteris k : kompiuteriuMas1){
            aKomp.add(k);
        }
    }
    void paprastasTyrimas(int elementuKiekis){
        long t0 = System.nanoTime();
        generuotiKompiuterius(elementuKiekis);
        ListKTU<Kompiuteris> aKomp2 = aKomp.clone();
        ListKTU<Kompiuteris> aKomp3 = aKomp.clone();
        ListKTU<Kompiuteris> aKomp4 = aKomp.clone();
        ListKTU<Kompiuteris> aKomp5 = aKomp.clone();
        ListKTU<Kompiuteris> aKomp6 = aKomp.clone();
        
        long t1 = System.nanoTime();
        System.gc();
        System.gc();
        System.gc();
        long t2 = System.nanoTime();
        
        aKomp.sortJava();
        long t3 = System.nanoTime();
        aKomp2.sortJava(Kompiuteris.pagalProc);
        long t4 = System.nanoTime();
        aKomp3.sortBuble();
        long t5 = System.nanoTime();
        aKomp4.sortBuble(Kompiuteris.pagalProc);
        long t6 = System.nanoTime();
        aKomp5.sortMinMax();
        long t7 = System.nanoTime();
        aKomp6.sortMinMax(Kompiuteris.pagalProc);
        long t8 = System.nanoTime();
        Ks.ouf("%7d %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f \n", elementuKiekis,
                (t1 - t0) / 1e9, (t2 - t1) / 1e9, (t3 - t2) / 1e9,
                (t4 - t3) / 1e9, (t5 - t4) / 1e9, (t6 - t5) / 1e9,
                (t7-t6) / 1e9, (t8 - t7) / 1e9);
    }
        /**
     * Metodas skirtas generuoti objektus ir atlikti greitaveika
     */
    void tyrimasRusiavimas(){

        generuotiKompiuterius(100);
        for(Kompiuteris s : aKomp){
            Ks.oun(s);
        }
        Ks.oun("1 - Pasiruošimas tyrimui - duomenų generavimas");
        Ks.oun("2 - Pasiruošimas tyrimui - šiukšlių surinkimas");
        Ks.oun("3 - Rūšiavimas sisteminiu greitu būdu be Comparator");
        Ks.oun("4 - Rūšiavimas sisteminiu greitu būdu su Comparator");
        Ks.oun("5 - Rūšiavimas List burbuliuku be Comparator");
        Ks.oun("6 - Rūšiavimas List burbuliuku su Comparator");
        Ks.oun("7 - Rūšiavimas List MinMax be Comparator");
        Ks.oun("8 - Rūšiavimas List MinMax su Comparator");
        Ks.ouf("%6d %7d %7d %7d %7d %7d %7d %7d %7d \n", 0, 1, 2, 3, 4, 5, 6, 7, 8);
        for (int n : tiriamiKiekiai) {
            paprastasTyrimas(n);
        }
        Ks.oun("Rikiavimo metodų greitaveikos tyrimas su kompiuteriais baigtas.");
        

    }

    /**
     * Main metodas
     * @param args
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("LT"));
        new Greitaveika().tyrimasRusiavimas();
    }
}
