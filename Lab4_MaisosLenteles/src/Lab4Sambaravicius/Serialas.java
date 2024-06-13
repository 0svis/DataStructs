package Lab4Sambaravicius;


import java.time.LocalDate;
import java.util.*;

import laborai.studijosktu.KTUable;
import laborai.studijosktu.Ks;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Martynas Šambaravičius
 */
public class Serialas implements KTUable{
    /**
     * minimali ir maksimali serialo trukme
     */
    final static private double MIN_TRUKME = 70;
    final static private double MAX_TRUKME = 200;
    private static String PR = "Nr.";

    private static int serialoNr = 0;
	private String pavadinimas;
	private String zanras;
	private int metai;
	private int epizodai;
	private double epizodoTrukme;
        private boolean tesiasi;
    
    public Serialas() {}
    
    public Serialas(String pavadinimas,String zanras,int metai, int epizodai,
        double epizodoTrukme,boolean tesiasi) {
    this.pavadinimas = pavadinimas;
    this.zanras = zanras;
    this.metai = metai;
    this.epizodai = epizodai;
    this.epizodoTrukme = epizodoTrukme;
    this.tesiasi = tesiasi;
    }

    public Serialas(String dataString) {this.parse(dataString);}

    /**
     * Sukuria objekta is eilutes
     * @param eilute eilute sukurti objekta
     * @return Serialas klases objekto
     */
    @Override
    public Serialas create(String eilute) {
            return new Serialas(eilute);
    }

    /**
     * Suformuoja objektą iš eilutės
     * @param eilute eilutė objektui formuoti
     */
    @Override
    public void parse(String eilute) {
        try {   
            Scanner ed = new Scanner(eilute);
            pavadinimas=ed.next();
            zanras=ed.next();
            metai=ed.nextInt();
            epizodai=ed.nextInt();
            epizodoTrukme=ed.nextDouble();
            tesiasi=ed.nextBoolean();
        } catch (InputMismatchException e) {
                Ks.ern("Netinkamas informacijos formatas apie seriala -> "
                        + eilute);
        } catch (NoSuchElementException e) {
                Ks.ern("Truksta informacijos apie seriala -> "
                        + eilute);
        }
    }

    /**
     * Patikrina objekto reikšmes pagal norimas taisykles
     * @return tuščią eilutę arba eilutę-klaidos tipą
     */
    @Override
    public String validate() {
        String errorType = "";
        double trukme = getTrukme();
        if(!tesiasi && trukme < MIN_TRUKME){
            errorType = String.format("Serialas nesitesia ir trukme %.1f"
                    + " val. per maza, turi buti: x > %s .",
                    trukme,MIN_TRUKME);
        }
        if(trukme > MAX_TRUKME){
            errorType += String.format("Serialo trukme %.1f val. per didele, turi buti: [0:%s].",
                    trukme,MAX_TRUKME);
        }
        return errorType;
    }
    /**
     * Objekto reikšmių išvedimas, nurodant išvedime tik objekto vardą
     * @return Išvedimui suformuota eilutė
     */
    @Override
    public String toString() {  // surenkama visa reikalinga informacija
        return String.format("%s %s %d %d %.1f %.1f %b",
            pavadinimas,zanras,metai,epizodai,
            epizodoTrukme,getTrukme(),tesiasi);
    }

    public String getZanras() {
        return zanras;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public int getMetai() {
        return metai;
    }
    
    public int getEpizodai() {
        return epizodai;
    }

    public double getEpizodoTrukme() {
        return epizodoTrukme;
    }
    
    public boolean getTesiasi(){
        return tesiasi;
    }
    
    public double getTrukme(){
        return (epizodoTrukme * epizodai) / 60;
    }
    
    public void setTesiasi(boolean state) {
        tesiasi = state;
    }

    @Override
    public int hashCode(){return Objects.hash(pavadinimas,zanras,metai,epizodai,epizodoTrukme,tesiasi);}

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
                return false;
        }
        final Serialas other = (Serialas) obj;
        if (!Objects.equals(this.pavadinimas, other.pavadinimas)) {
                return false;
        }
        if (!Objects.equals(this.zanras, other.zanras)) {
                return false;
        }
        if (this.metai != other.metai) {
                return false;
        }
        if (this.epizodai != other.epizodai) {
                return false;
        }
        if (this.epizodoTrukme != other.epizodoTrukme) {
                return false;
        }
        return this.tesiasi == other.tesiasi;
    }

    public Serialas(Builder builder) {
        this.pavadinimas = builder.pavadinimas;
        this.zanras = builder.zanras;
        this.metai = builder.metai;
        this.epizodai = builder.epizodai;
        this.epizodoTrukme = builder.epizodoTrukme;
        this.tesiasi = builder.tesiasi;
        validate();
    }

   /**
	* Rikiavimas pagal zanra ir epizodu skaiciu 
	*/
    public final static Comparator pagalZanraEpizodus = new Comparator() {
    @Override
    public int compare(Object o1, Object o2) {
      Serialas a1 = (Serialas) o1;
      Serialas a2 = (Serialas) o2;
      int cmp = a1.getZanras().compareTo(a2.getZanras());
      // zanras, esant vienodam zanrui, lyginamas epizodu skaicius
      if(cmp != 0) return cmp;
      if(a1.getEpizodai() < a2.getEpizodai()) return 1;
      if(a1.getEpizodai() > a2.getEpizodai()) return -1;
      return 0;
   }
    };
	
    // Serialo klases objektų gamintojas
    public static class Builder {

        private final static Random RANDOM = new Random(2023-11);  // Atsitiktinių generatorius
        private final static String[][] PavZanras = { // galimų serialu pavadinimai ir jų zanrai
            {"The_Boys","action","comedy","crime","sci-fi"},
            {"The_Chandelian_Family","drama","romance","comedy"},
            {"Quantum_Chronicles","sci-fi","mystery","thriller","adventure"},
            {"Echoes_of_Eternity","fantasy","adventure","romance","mystery"},
            {"Neon_Noir","thriller","cyberpunk","crime","drama"},
            {"Code_Red","action","crime","drama","mystery"},
            {"Virtual_Nexus","sci-fi","vr","thriller","mystery"},
            {"Rogue_Renegades","action","comedy","sci-fi","drama"},
            {"Starlight_Sonata","opera","romance","drama","action"},
            {"Tech_Titans","drama","sci-fi","action","adventure"},
            {"Sherlock", "mystery", "crime", "drama"},
            {"The_Wire", "crime", "drama", "thriller"},
            {"Peaky_Blinders", "crime", "drama","adventure","action"},
            {"Mindhunter", "crime", "drama", "thriller"},
            {"Firefly", "sci-fi", "adventure", "drama"},
            {"Fleabag", "comedy", "drama"},
            {"The_Expanse", "sci-fi", "mystery", "thriller"},
            {"Brooklyn_Nine_Nine", "comedy", "crime"},
            {"Ozark", "crime", "drama", "thriller"},
            {"The_Good_Place", "comedy", "drama", "fantasy"},
            {"Altered_Carbon", "action", "drama", "sci-fi"},
            {"Vikings", "action", "adventure", "drama"},
            {"The_Sopranos", "crime", "drama"},
            {"Archer", "animation", "action", "comedy"},
            {"Dark", "crime", "drama", "sci-fi"},
            {"The_Last_Dance", "documentary", "biography", "sport"},
            {"Breaking_Bad", "crime", "drama", "thriller"},
            {"Stranger_Things", "drama", "fantasy", "horror"},
            {"The_Crown", "biography", "drama", "history"},
            {"The_Simpsons", "animation", "comedy"},
            {"Black_Mirror", "drama", "sci-fi", "thriller"},
            {"Narcos", "biography", "crime", "drama"},
            {"The_Big_Bang_Theory", "comedy", "romance"},
            {"Westworld", "drama", "mystery", "sci-fi"},
            {"Money_Heist", "action", "crime", "drama"},
            {"The_Office_US", "comedy","drama","adventure"},
            {"Fargo", "crime", "drama", "thriller"},
            {"Chernobyl", "drama", "history", "thriller"},
            {"The_Umbrella_Academy", "action", "adventure", "comedy", "drama", "fantasy"},
            {"The_Witcher", "action", "adventure", "drama", "fantasy", "mystery"},
            {"Friends", "comedy", "romance","adventure"},
            {"Game_of_Thrones", "action", "adventure", "drama", "fantasy"},
            {"The_Office_UK", "comedy", "drama","adventure"},
            {"Breaking_Bad_Movie", "crime", "drama", "thriller"}
        };

        private String pavadinimas = "";
	private String zanras = "";
	private int metai = -1;
	private int epizodai = -1;
	private double epizodoTrukme = -1.0;
        private boolean tesiasi = false;

        public Serialas build() {
            return new Serialas(this);
        }

        public Serialas buildRandom() {
            int pi = RANDOM.nextInt(PavZanras.length);
            int zi = RANDOM.nextInt(PavZanras[pi].length - 1) + 1;             
            return new Serialas(PavZanras[pi][0],PavZanras[pi][zi],
                    1913+ RANDOM.nextInt(2023-1913),
                    1+RANDOM.nextInt(1500),
                    10+(RANDOM.nextDouble()*248),
                    RANDOM.nextBoolean());
        }

        public Builder pavadinimas(String pavadinimas){
            this.pavadinimas = pavadinimas;
            return this;
        }
        
        public Builder zanras(String zanras){
            this.zanras = zanras;
            return this;
        }
        
        public Builder metai(int metai){
            this.metai = metai;
            return this;
        }
        
        public Builder epizodai(int epizodai){
            this.epizodai = epizodai;
            return this;
        }
        
        public Builder epizodoTrukme(double epizodoTrukme){
            this.epizodoTrukme = epizodoTrukme;
            return this;
        }
        
        public Builder tesiasi(boolean tesiasi){
            this.tesiasi = tesiasi;
            return this;
        }
    }
}
