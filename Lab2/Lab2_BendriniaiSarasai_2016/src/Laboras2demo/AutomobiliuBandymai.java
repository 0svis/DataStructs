/**
 * @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 * Koreguota: Aleksas Riškus, MIK, 2016
 *
 * Tai yra demonstracinė Automobilio bandymų klasė, kuri skirta tiesiog
 * veiksmų su sąrašais išbandymui
 *  IŠSIAIŠKINKITE metodų sudarymą, jų paskirtį.
 *  PASIRINKITE savo objektų klasę ir sudarykite analogiškus metodus
 *  GERESNIAM ĮSISAVINIMUI rekomenduojame pradėti nuo tuščios klasės
 ***************************************************************************
 */
package Laboras2demo;

import java.util.Locale;
import studijosKTU.*;

public class AutomobiliuBandymai {

	ListKTUx<Automobilis> bandomieji = new ListKTUx<>(new Automobilis());

	ListKTU<Automobilis> kopija; // klonavimo metodo clone testui

	void metodoParinkimas() {
		tikrintiAtskirusAuto();
		//formuotiAutoSąrašą();
		//peržiūrėtiSąrašą();
        //papildytiSąrašą();
        //patikrintiTurgausApskaitą();
        //patikrintiRikiavimą();
	}

	void tikrintiAtskirusAuto() {
		Automobilis a1 = new Automobilis("Renault", "Laguna", 1997, 50000, 1700);
		Automobilis a2 = new Automobilis("Renault", "Megane", 2001, 20000, 3500);
		Automobilis a3 = new Automobilis("Toyota", "Corolla", 2001, 20000, 8500.8);

		Automobilis a4 = new Automobilis();
		Automobilis a5 = new Automobilis();
		Automobilis a6 = new Automobilis();
		a4.parse("Renault Laguna 2001 115900 7500");
		a5.parse("Renault Megane 1946 365100 9500");
		a6.parse("Honda   Civic  2007  36400 8500,3");

		Ks.oun(a1);
		Ks.oun(a2);
		Ks.oun(a3);
		Ks.oun("Pirmų 3 auto ridos vidurkis= "
				+ (a1.getRida() + a2.getRida() + a3.getRida()) / 3);
		Ks.oun(a4);
		Ks.oun(a5);
		Ks.oun(a6);
		Ks.oun("Kitų 3 auto kainų suma= "
				+ (a4.getKaina() + a5.getKaina() + a6.getKaina()));
	}

	void formuotiAutoSąrašą() {
		// Kartu patikrina ir clone metodo veikimą
		Automobilis a1 = new Automobilis("Renault", "Laguna", 1997, 50000, 1700);
		Automobilis a2 = new Automobilis("Renault", "Megane", 2001, 20000, 3500);
		Automobilis a3 = new Automobilis("Toyota", "Corolla", 2001, 20000, 8500.8);
		bandomieji.add(a1);
		bandomieji.add(a2);
		bandomieji.add(a3);
		bandomieji.println("Pirmi 3 auto");

		kopija = bandomieji.clone(); // Kitas sąrašas - pirmų trijų automobilių kopija

		// Papildome sąrašą bandomisji dar trimis aotomobiliais (sąrašas kopija nesikeis):
		bandomieji.add("Renault Laguna 2001 115900 7500");
		bandomieji.add("Renault Megane 1946 365100 9500");
		bandomieji.add("Honda   Civic  2007  36400 8500,3");

		bandomieji.println("Visi 6 auto");

		Ks.oun("Kopijos elementai");
		for (Automobilis a : kopija) {
			Ks.oun(a);
		}
		Ks.oun("Kopijos elementai su nuoroda ::");
		kopija.forEach(System.out::println); // Galima "mandriau" - su nuoroda ::

		Ks.oun("\nPirmų 3 auto ridos vidurkis= "
				+ (bandomieji.get(0).getRida() + bandomieji.get(1).getRida()
				+ bandomieji.get(2).getRida()) / 3);

	}

	void peržiūrėtiSąrašą() {
		int sk = 0;
		for (Automobilis a : bandomieji) {
			if (a.getMarkė().compareTo("Renault") == 0) {
				sk++;
			}
		}
		Ks.oun("Renault automobilių yra = " + sk);

		// Kopijos testas:
		sk = 0;
		for (Automobilis a : kopija) {
			if (a.getMarkė().compareTo("Renault") == 0) {
				sk++;
			}
		}
		Ks.oun("Kopijoje Renault automobilių yra = " + sk);
	}

	void papildytiSąrašą() {
		for (int i = 0; i < 8; i++) {
			bandomieji.add(new Automobilis("Ford", "Focus",
					2009 - i, 40000 + i * 10000, 36000 - i * 2000));
		}
		bandomieji.add("Ford Mondeo  2009 37000 36000.0");
		bandomieji.add("Fiat Bravo   2008 27000 32500,0");
		bandomieji.add("Ford Fiesta  2009 37000 16000,0");
		bandomieji.add("Audi A6      2006 87000 36000,0");
		bandomieji.println("Testuojamų automobilių sąrašas");
		bandomieji.save("ban.txt");
	}

	void patikrintiTurgausApskaitą() {
		AutomobiliuTurgus aTurgus = new AutomobiliuTurgus();

		aTurgus.visiAuto.load("ban.txt");
		aTurgus.visiAuto.println("Bandomasis rinkinys");

		bandomieji = aTurgus.atrinktiPagalKainą(3000, 10000);
		bandomieji.println("Kaina tarp 3000 ir 10000");

		bandomieji = aTurgus.maksimaliosKainosAuto();
		bandomieji.println("Patys brangiausi");

		bandomieji = aTurgus.atrinktiMarkęModelį("F");
		bandomieji.println("Turi būti tik Fiatai ir Fordai");

		bandomieji = aTurgus.atrinktiMarkęModelį("Ford M");
		bandomieji.println("Turi būti tik Ford Mondeo");
		Ks.oun("Ford Mondeo kiekis = " + bandomieji.size());
	}
	// išbandykite veikimą, o po to pakeiskite į Lambda stiliaus komparatorius.

	void patikrintiRikiavimą() {
		AutomobiliuTurgus aps = new AutomobiliuTurgus();

		aps.visiAuto.load("ban.txt");
		Ks.oun("========" + aps.visiAuto.get(0));
		aps.visiAuto.println("Bandomasis rinkinys");
		aps.visiAuto.sortBuble(Automobilis.pagalMarkę);
		aps.visiAuto.println("Rūšiavimas pagal Markę");
		aps.visiAuto.sortBuble(Automobilis.pagalKainą);
		aps.visiAuto.println("Rūšiavimas pagal kainą");
		aps.visiAuto.sortBuble();
		aps.visiAuto.println(" Rūšiavimas pagal compareTo - Kainą ");
	}

	/**
	 * Metodų testas
	 * @param args 
	 */
	public static void main(String... args) {
		// suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
		Locale.setDefault(new Locale("LT"));
		new AutomobiliuBandymai().metodoParinkimas();
	}
}
