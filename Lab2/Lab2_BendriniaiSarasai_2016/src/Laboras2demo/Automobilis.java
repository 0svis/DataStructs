/**
 * @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 * Koreguota: Aleksas Riškus, MIK, 2016
 *
 * Tai yra demonstracinė Automobilio klasė, kurios objektai dedami į ListKTU klasę.
 *   Klasė Automobilis įdiegia interfeisą KTUable<T>
 * IŠSIAIŠKINKITE klasės metodų veikimą, pasinaudojant klase AutomobiliuBandymai. 
 * SUDARYKITE analogiškus metodus savo individualiai klasei
 *   (GERESNIAM ĮSISAVINIMUI rekomenduojame pradėti nuo tuščios klasės).
 */
package Laboras2demo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import studijosKTU.*;

public class Automobilis implements KTUable<Automobilis> {

	// bendri duomenys visiems automobiliams (visai klasei)
	final static private int priimtinųMetųRiba = 1994;
	final static private int esamiMetai = LocalDate.now().getYear();

	final static private double valKursas = esamiMetai <= 2014 ? 1 : 1 / 3.4528;
	final static private double minKaina = 200.0;
	final static private double maxKaina = 120_000.0;

	// kiekvieno automobilio individualūs duomenys
	private String markė;
	private String modelis;
	private int gamMetai;
	private int rida;
	private double kaina;

	public Automobilis() {
	}

	public Automobilis(String markė, String modelis,
			int gamMetai, int rida, double kaina) {
		this.markė = markė;
		this.modelis = modelis;
		this.gamMetai = gamMetai;
		this.rida = rida;
		this.kaina = kaina;
	}

	public Automobilis(String dataString) {
		this.parse(dataString);
	}

	/**
	 * Sukuria naują objektą iš eilutės
	 * @param dataString eilutė objekto sukūrimui
	 * @return Automobilio klasės objektą
	 */
	@Override
	public Automobilis create(String dataString) {
		return new Automobilis(dataString);

	}

	/**
	 * Suformuoja objektą iš eilutės
	 * @param dataString eilutė objektui formuoti
	 */
	@Override
	public final void parse(String dataString) {
		try {   
			Scanner ed = new Scanner(dataString); // numatytieji skirtukai: tarpas, tab, eilutės pabaiga
			// Skiriklius galima pakeisti Scanner klasės metodu useDelimitersr 
			//	Pavyzdžiui, ed.useDelimiter(", *"); reikštų, kad skiriklis bus kablelis ir vienas ar daugiau tarpų.
			markė = ed.next();
			modelis = ed.next();
			gamMetai = ed.nextInt();
			rida = ed.nextInt();
			setKaina(ed.nextDouble());
		} catch (InputMismatchException e) {
			Ks.ern("Blogas duomenų formatas apie auto -> " + dataString);
		} catch (NoSuchElementException e) {
			Ks.ern("Trūksta duomenų apie auto -> " + dataString);
		}
	}

	/**
	 * Patikrina objekto reikšmes pagal norimmas taisykles
	 * @return tuščią eilutę arba eilutę-klaidos tipą
	 */
	@Override
	public String validate() {
		String klaidosTipas = "";
		if (gamMetai < priimtinųMetųRiba || gamMetai > esamiMetai) {
			klaidosTipas = "Netinkami gamybos metai, turi būti ["
					+ priimtinųMetųRiba + ":" + esamiMetai + "]";
		}
		if (kaina < minKaina || kaina > maxKaina) {
			klaidosTipas += " Kaina už leistinų ribų [" + minKaina
					+ ":" + maxKaina + "]";
		}
		return klaidosTipas;
	}

	/**
	 * Objekto reikšmių išvedimas, nurodant išvedime tik objekto vardą
	 * @return Išvedimui suformuota eilutė
	 */
	@Override
	public String toString() {  // surenkama visa reikalinga informacija
		return String.format("%-8s %-8s %4d %7d %8.1f %s",
				markė, modelis, gamMetai, rida, kaina, validate());
	}

	;
    public String getMarkė() {
		return markė;
	}

	public String getModelis() {
		return modelis;
	}

	public int getGamMetai() {
		return gamMetai;
	}

	public int getRida() {
		return rida;
	}

	public double getKaina() {
		return kaina;
	}
	// keisti bus galima tik kainą - kiti parametrai pastovūs

	public void setKaina(double kaina) {
		this.kaina = kaina;
	}

	/**
	 * Interfeiso Comparable metodas, naudojamas objektų rikiavimui
	 * @param a objektas su kuriuo lyginama
	 * @return 0 | 1 | -1
	 */
	@Override
	public int compareTo(Automobilis a) {
		// lyginame pagal svarbiausią požymį - kainą
		double kainaKita = a.getKaina();
		return Double.compare(kainaKita, kaina);
	}
	
	// Rikiavimo pagal modeli komparatorius
	public final static Comparator<Automobilis> pagalMarkę = new Comparator<Automobilis>() {
		@Override
		public int compare(Automobilis a1, Automobilis a2) {
			int cmp = a1.getMarkė().compareTo(a2.getMarkė());
			return cmp;
		}
	};

	/**
	 * Rikiavimo pagal kainą komparatorius
	 *	(geriau panaudoti Double klasės metodą compare)
	 */
	public final static Comparator<Automobilis> pagalKainą = new Comparator<Automobilis>() {
		@Override
		public int compare(Automobilis o1, Automobilis o2) {
			double k1 = o1.getKaina();
			double k2 = o2.getKaina();
			// didėjanti tvarka, pradedant nuo mažiausios
			if (k1 < k2) {
				return -1;
			}
			if (k1 > k2) {
				return 1;
			}
			return 0;
		}
	};
	
   /**
	* Rikiavimo pagal metus ir kainą komparatorius
	*/
	public final static Comparator pagalMetusKainą = new Comparator() {
       // sarankiškai priderinkite prie generic interfeiso ir Lambda funkcijų
       @Override
       public int compare(Object o1, Object o2) {
          Automobilis a1 = (Automobilis) o1;
          Automobilis a2 = (Automobilis) o2;
          // metai mažėjančia tvarka, esant vienodiems metams, lyginama kaina
          if(a1.getGamMetai() < a2.getGamMetai()) return 1;
          if(a1.getGamMetai() > a2.getGamMetai()) return -1;
          if(a1.getKaina() < a2.getKaina()) return 1;
          if(a1.getKaina() > a2.getKaina()) return -1;
          return 0;
       }
    };
	
	/**
	 * Mažas testukas - sukuria ir išveda objektus
	 * @param args 
	 */
	public static void main(String... args) {
		// suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
		Locale.setDefault(new Locale("LT"));
		Automobilis a1 = new Automobilis("Renault", "Laguna", 1997, 50000, 599);
		Automobilis a2 = new Automobilis("Renault", "Megane", 2015, 20000, 3500);
		Automobilis a3 = new Automobilis();
		Automobilis a4 = new Automobilis();
		a3.parse("Toyota Corolla 2001 20000 8500,8");
		a4.parse("Toyota Avensis 1990 20000  500,8");
		Ks.oun(a1);
		Ks.oun(a2);
		Ks.oun(a3);
		Ks.oun(a4);
	}
}
