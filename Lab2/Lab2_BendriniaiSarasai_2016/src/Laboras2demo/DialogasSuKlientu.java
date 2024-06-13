/**
 * @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 * Koreguota: Aleksas Riškus, MIK, 2016
 *
 * Tai yra konsolinio dialogo klasė, kurioje naudojami klasės Ks metodai.
 * Vartotojas nurodo kokius veiksmus su Automobilių turgumi reikia atlikti.
 *  IŠSIAIŠKINKITE metodų sudarymą, jų paskirtį.
 *  PAKEISKITE šią DialogasSuKlientu klasę grafinės vartotojo sąsajos klase.
 *  ĮDĖKITE ne tik skaitymą iš failo, objektų atranką, bet ir rikiavimo testus 
 *    bei kitų jūsų sukurtų metodų testus.
 * P.S: Failo pasirinkimas turi būti atliekamas iš standartinio failo pasirinkimo dialogo
 *   (pavyzdžiui, naudojant JFileChooser klasę).
 */
package Laboras2demo;

import java.util.Locale;
import studijosKTU.*;

public class DialogasSuKlientu {

	AutomobiliuTurgus aTurgus = new AutomobiliuTurgus();

	void bendravimasSuKlientu() {
		ListKTUx<Automobilis> atranka = null;
		int varNr;  // skaičiavimo varijantas pasirenkamas nurodant jo numerį
		String dialogMeniu = "Pasirinkimas: "
				+ "1-skaityti iš failo; 2-papildyti sąrašą; 3-naujų atranka;\n    "
				+ "4-atranka pagal kainą; 5-brangiausi auto;\n    "
				+ "0-baigti skaičiavimus > ";
		while ((varNr = Ks.giveInt(dialogMeniu, 0, 6)) != 0) {
			if (varNr == 1) {
				aTurgus.visiAuto.load(Ks.giveFileName());
				aTurgus.visiAuto.println("Visų automobilių sąrašas");
			} else if (varNr == 2) {
				String autoDuomenys = Ks.giveString("Nurodykite auto markę, "
						+ "modelį, gamybos metus, ridą ir kainą\n ");
				Automobilis a = new Automobilis();
				a.parse(autoDuomenys);
				String klaidosPožymis = a.validate();
				if (klaidosPožymis.isEmpty()) // dedame tik su gerais duomenimis
				{
					aTurgus.visiAuto.add(a);
				} else {
					Ks.oun("!!! Automobilis į sąrašą nepriimtas " + klaidosPožymis);
				}
			} else {  // toliau vykdomos atskiri atrankos metodai
				switch (varNr) {
					case 3:
						int nR = Ks.giveInt("Nurodykite naujų auto metų ribą: ");
						Ks.oun("Naujų automobilių atranka dar nerealizuota.");
						break;
					case 4:
						int r1 = Ks.giveInt("Nurodykite apatinę kainos ribą: ");
						int r2 = Ks.giveInt("Nurodykite viršutinę kainos ribą: ");
						atranka = aTurgus.atrinktiPagalKainą(r1, r2);
						break;
					case 5:
						atranka = aTurgus.maksimaliosKainosAuto();
						break;
				}
				if (atranka != null) {
					atranka.println("Štai atrinktų automobilių sąrašas");
					atranka.save(Ks.giveString("Kur saugoti atrinktus auto (jei ne-tai ENTER) ? "));
				}
			}
		}
	}

	public static void main(String[] args) {
		// suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
		Locale.setDefault(new Locale("LT"));
		new DialogasSuKlientu().bendravimasSuKlientu();
	}
}
