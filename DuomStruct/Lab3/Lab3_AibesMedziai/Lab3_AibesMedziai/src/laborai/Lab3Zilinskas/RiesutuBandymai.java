package laborai.Lab3Zilinskas;

import laborai.studijosktu.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

public class RiesutuBandymai {

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US);
        aibes();
    }
    static Riesutas[] Skaityti(String txt) throws IOException {

        Riesutas[] ries = new Riesutas[100];
        FileReader fr = new FileReader(txt);
        BufferedReader reader = new BufferedReader(fr);
        String line;
        int i = 0;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            String pav = parts[0];
            String tipas = parts[1];
            int kiekis = Integer.parseInt(parts[2]);
            double kgKaina = Double.parseDouble(parts[3]);
            boolean sveriamas = Boolean.parseBoolean(parts[4]);
            String kilmesSalis = parts[5];
            ries[i] = new Riesutas(pav, tipas,
                    kiekis, kgKaina, sveriamas, kilmesSalis);
            i++;
        }
        return ries;
    }
    public static void aibes() throws CloneNotSupportedException{
        Riesutas a1 = new Riesutas.Builder().buildRandom();
        Riesutas a2 = new Riesutas.Builder().buildRandom();
        Riesutas a3 = new Riesutas.Builder().buildRandom();
        Riesutas a4 = new Riesutas.Builder().buildRandom();
        Riesutas a5 = new Riesutas.Builder().buildRandom();
        Riesutas a6 = new Riesutas.Builder().buildRandom();
        Riesutas a7 = new Riesutas.Builder().buildRandom();

        Riesutas[] riesMasyvas = {a1, a2, a3, a4, a5};

        Ks.oun("Riesutu aibe:");
        SortedSetADTx<Riesutas> riesAibe = new BstSetKTUx<>(new Riesutas());


        for(Riesutas s : riesMasyvas){
            riesAibe.add(s);
            Ks.oun("Aibe papildoma: "+s+ ". Jos dydis: " + riesAibe.size());
        }

        Ks.oun("");
        Ks.oun(riesAibe.toVisualizedString(""));

        SortedSetADTx<Riesutas> riesKopija = (SortedSetADTx<Riesutas>) riesAibe.clone();
        riesKopija.add(a6);
        riesKopija.add(a7);

        Ks.oun("Papipldyta riesutu aibes kopija: ");
        Ks.oun(riesKopija.toVisualizedString(""));
        Ks.oun("");

        Ks.oun("Ar elementai egzsistuoja aibeje?");
        for(Riesutas s: riesMasyvas){
            Ks.oun(s + " : " + riesAibe.contains(s));
        }
        Ks.oun(a6 + " : " + riesAibe.contains(a6));
        Ks.oun(a7 + " : " + riesAibe.contains(a7));
        Ks.oun("");

        Ks.oun("Ar elementai egzsistuoja kopijos aibeje?");
        for(Riesutas s: riesMasyvas){
            Ks.oun(s + " : " + riesKopija.contains(s));
        }
        Ks.oun(a6 + " : " + riesKopija.contains(a6));
        Ks.oun(a7 + " : " + riesKopija.contains(a7));
        Ks.oun("");


        //=======================================
        // SALINIMAS SU INDEKSU
        //=======================================
        Ks.oun("Elementu salinimas is kopijis. Aibes dydis pries salinima: " + riesKopija.size());
        int i = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Iveskite salinamo elemento indeksa");
        int riesSk = sc.nextInt();
        for(Riesutas s : new Riesutas[]{a1, a2, a3, a1, a4, a5, a6, a7, a6}){

            if(i == riesSk){
                riesKopija.remove(s);
                Ks.oun("Iš riesAibes kopijos pašalinama: " + s + ". Jos dydis: " + riesKopija.size());
                break;
            }
            else{
                i++;
            }

        }
        Ks.oun("");
        //=====================================
        // PRIDEJIMAS SU INDEKSU
        //=====================================
        Ks.oun("Elementu pridejimas prie kopijos is masyvo. Aibes dydis pries pridejima: " + riesKopija.size());
        int j = 0;
        Scanner sd = new Scanner(System.in);
        System.out.println("Iveskite pridedamo elemento indeksa");
        int pridSk = sd.nextInt();
        for(Riesutas s: new  Riesutas[]{a1, a2, a3, a1, a4, a5, a6, a7, a6}){
            if(j == pridSk){
                riesKopija.add(s);
                Ks.oun("Prie riesutu aibes kopijos pridedamas: " + s + ". Jos dydis: " + riesKopija.size());
                break;
            }
            else{
                j++;
            }
        }

        Ks.oun("Riesutu aibe su iteratoriumi");
        Ks.oun("");
        for(Riesutas s : riesAibe){
            Ks.oun(s);
        }
        Ks.oun("");

        Ks.oun("Riesutu aibe AVL-medyje");
        SortedSetADTx<Riesutas> riesAibe2 = new AvlSetKTUx<>(new Riesutas());
        for(Riesutas s :riesMasyvas){
            riesAibe2.add(s);

        }
        Ks.oun(riesAibe2.toVisualizedString(""));

        Ks.oun("Riesutu aibe su iteratoriumi");
        Ks.oun("");
        for(Riesutas s : riesAibe2){
            Ks.oun(s);
        }
        Ks.oun("");

        Ks.oun("Riesutu aibe su atvirkstiniu iteratoriumi");
        Ks.oun("");
        Iterator iter = riesAibe2.descendingIterator();
        while(iter.hasNext()){
            Ks.oun(iter.next());
        }
        Ks.oun("");

        Ks.oun("Riesutu aibes toString metodas");
        Ks.ounn(riesAibe2);

        riesAibe.clear();
        riesAibe2.clear();

        // Isvalome ir suformuojame aibes skaitydami is failo
        Ks.oun("");
        Ks.oun("Riesutu aibe DP-medyje");
        riesAibe.load("d1.txt");
        Ks.ounn(riesAibe.toVisualizedString(""));

        Ks.oun("");
        Ks.oun("Riesutu aibe AVL-medyje");
        riesAibe2.load("d1.txt");
        Ks.ounn(riesAibe2.toVisualizedString(""));
      

        SetADT<String> riesAibe3 = RiesutuApskaita.riesutuPav(riesMasyvas);

        Ks.oun("Pasikartojancios automibiliu markes:\n" + riesAibe3.toString());
       

    }
}
