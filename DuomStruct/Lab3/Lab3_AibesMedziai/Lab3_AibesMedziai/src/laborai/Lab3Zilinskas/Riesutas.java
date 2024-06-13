package laborai.Lab3Zilinskas;


import laborai.studijosktu.KTUable;
import laborai.studijosktu.Ks;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.*;

public final class Riesutas implements KTUable<Riesutas> {

    final static private int maziausiasKiekis = 10; // Mažiausias galimas riešutų kiekis
    final static private int didziausiasKiekis = 80; // Didžiausias galimas riešutų kiekis

    private String pavadinimas; // Riešuto pavadinimas
    private String tipas; // Riešuto tipas
    private int kiekis; // Riešutų kiekis
    private double kgKaina; // Riešuto kilogramo kaina
    private Boolean sveriamas; // Ar sveriamas riešutas
    private String kilmesSalis; // Riešuto pagaminimo šalis

    /**
     * Tuscias riešuto konstruktorius
     */
    public Riesutas(){

    }

    /**
     * Riešuto konstruktorius su parametrais
     * @param pavadinimas Riešuto pavadinimas
     * @param tipas Riešuto tipas
     * @param kiekis Riešutų kiekis
     * @param kgKaina Riešuto kilogramo kaina
     * @param sveriamas Ar sveriamas riešutas
     * @param kilmesSalis Riešuto pagaminimo šalis
     */
    public Riesutas(String pavadinimas, String tipas, int kiekis,
                     double kgKaina, boolean sveriamas, String kilmesSalis){
        this.pavadinimas = pavadinimas;
        this.tipas = tipas;
        this.kiekis = kiekis;
        this.kgKaina = kgKaina;
        this.sveriamas = sveriamas;
        this.kilmesSalis = kilmesSalis;
    }

    /**
     * Metodas, kuriame nustatomi riešuto duomenys pagal tekstą
     * @param dataString
     */
    public Riesutas(String dataString){
        this.parse(dataString);
    }

    public Riesutas(Builder builder){
        this.pavadinimas = builder.pavadinimas;
        this.tipas = builder.tipas;
        this.kiekis = builder.kiekis;
        this.kgKaina = builder.kgKaina;
        this.sveriamas = builder.sveriamas;
        this.kilmesSalis = builder.kilmesSalis;
        validate();
    }
    /**
     * Metodas, kuris sukuria naują objektą pagal duotus duomenis
     * @param dataString
     * @return
     */
    @Override
    public Riesutas create(String dataString){
        return new Riesutas(dataString);
    }

    /**
     * Metodas nuskaitantis duomenis į objektą
     * @param dataString
     */
    @Override
    public void parse(String dataString){
        try{
            Scanner ed = new Scanner(dataString);
            pavadinimas = ed.next();
            tipas = ed.next();
            kiekis = ed.nextInt();
            kgKaina = ed.nextDouble();
            sveriamas = ed.nextBoolean();
            kilmesSalis = ed.next();
        } catch (InputMismatchException e){
            Ks.ern("Blogas duomenų formatas apie riesuta -> " + dataString);
        } catch (NoSuchElementException e){
            Ks.ern("Trūksta duomenų apie riesuta -> " + dataString);
        }

    }

    /**
     * Tikrinimas, ar riešutų kiekis yra tarp ribų
     * @return
     */
    @Override
    public String validate(){
        String klaidosRusis = "";
        if(kiekis < maziausiasKiekis || kiekis > didziausiasKiekis){
            klaidosRusis = "Netinkamas riešutų kiekis. Mažiausias kiekis: " + maziausiasKiekis
                    + " Didziausias kiekis: " + didziausiasKiekis;
        }
        return klaidosRusis;
    }

    /**
     * Duomenų teksto formavimas į eilutę toString
     * @return
     */
    @Override
    public String toString(){
        return String.format("| %-10s  | %-12s | %4d   | %-7f |   %5b    |  %-8s|", pavadinimas, tipas,
                kiekis, kgKaina, sveriamas, kilmesSalis);
    }
    public String getPavadinimas() { return pavadinimas; } // grąžina pavadinimą
    public String getTipas() { return tipas; } // grąžina tipą
    public int getKieki() { return kiekis; } // grąžina kiekį
    public double getKgKaina() { return kgKaina; } // grąžina kainą
    public Boolean getSveriamas() { return sveriamas; } // grąžina true arba false
    public String getKilmesSalis() { return kilmesSalis; } // grąžina šalį

    /**
     * Komparatorius pagal kainą
     * @param s the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Riesutas s){
        double kaina = s.getKgKaina();
        return  Double.compare(kaina, kgKaina);
    }

    /**
     *
     */
    public final static Comparator<Riesutas> pagalPav = new Comparator<Riesutas>() {
        @Override
        public int compare(Riesutas o1, Riesutas o2) {
            int sk = o1.getPavadinimas().compareTo(o2.getPavadinimas());
            return sk;
        }
    };
    /**
     * Komparatorius pagal tipą
     */
    public final static Comparator<Riesutas> pagalTipa = new Comparator<Riesutas>() {
        @Override
        public int compare(Riesutas o1, Riesutas o2) {
            int sk = o1.getTipas().compareTo(o2.getTipas());
            return sk;
        }
    };
    /**
     * Komparatorius pagal šalį
     */
    public final static Comparator<Riesutas> pagalSali = new Comparator<Riesutas>() {
        @Override
        public int compare(Riesutas o1, Riesutas o2) {
            int sk = o1.getKilmesSalis().compareTo(o2.getKilmesSalis());
            return sk;
        }
    };

    // Riesutu klases objektu gamintojas

    public static class Builder{
        private final static Random RANDOM = new Random(100);
        private final static String[][] TIPAI  = {
                {"Lazdynas", "Sokoladinis", "Kietas", "Skystas", "Minkstas"},
                {"Kokosas", "Sokoladinis", "Su sald", "Istirpes", "Skystas", "Skanus"},
                {"Migdolas", "Sokoladinis", "Skanus"},
                {"Graikinis", "Ledinis", "Istirpes", "Skanus"},
                {"Muskatas", "Kietas", "Karamelinis", "Karamelinis", "Skanus"},
                {"Pistacija", "Sokoladinis", "Istirpes", "Skanus"}

        };
        private final static String[][] SALYS  = {
                {"Lazdynas", "Austrija", "Sveicarija", "L*nkija", "Skotija"},
                {"Kokosas", "Bulgarija", "Rumunija", "Slovakija", "Serbija", "Zelandija"},
                {"Migdolas", "Makedonija", "Graikija"},
                {"Graikinis", "Ukraina", "Vokietija", "Kenija"},
                {"Muskatas", "Madagaskaras", "Nigerija", "Zambija", "Turkija"},
                {"Pistacija", "Egiptas", "Alzyras", "Marokas"}

        };
        private String pavadinimas = "";
        private String tipas = "";
        private int kiekis = -1;
        private double kgKaina = -1.0;
        private Boolean sveriamas = true;
        private String kilmesSalis = "";

        public Riesutas build(){return new Riesutas(this);}

        public Riesutas buildRandom(){
            int ma = RANDOM.nextInt(TIPAI.length);
            int mo = RANDOM.nextInt(TIPAI[ma].length-1)+1;
            return new Riesutas(TIPAI[ma][0],
                    TIPAI[ma][mo],
                    10 + RANDOM.nextInt(30),
                    10 + RANDOM.nextDouble() * 100,
                    RANDOM.nextBoolean(), SALYS[ma][mo]);
        }
        public Builder pavadinimas(String pavadinimas){
            this.pavadinimas = pavadinimas;
            return this;
        }
        public Builder tipas(String tipas){
            this.tipas = tipas;
            return this;
        }
        public Builder kiekis(int kiekis){
            this.kiekis = kiekis;
            return this;
        }
        public Builder kgKaina(double kgKaina){
            this.kgKaina = kgKaina;
            return this;
        }
        public Builder sveriamas(boolean sveriamas){
            this.sveriamas = sveriamas;
            return this;
        }
        public Builder kilmesSalis(String kilmesSalis){
            this.kilmesSalis = kilmesSalis;
            return this;
        }
    }


}
