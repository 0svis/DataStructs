/**
 * Naujų sąrašų kūrimas padal duotus kriterijus.
 *  IŠSIAIŠKINKITE metodų sudarymą, jų paskirtį.
 *  PASIRINKITE savo objektų klasę ir sudarykite keletą analogiškų metod.
 *  GERESNIAM ĮSISAVINIMUI rekomenduojame pradėti nuo tuščios klasės.
 ***************************************************************************
 */
package Laboras2demo;

import java.util.Iterator;
import studijosKTU.*;

public class AutomobiliuTurgus {

	public ListKTUx<Automobilis> visiAuto = new ListKTUx<>(new Automobilis());
	private static final Automobilis bazinisEgz = new Automobilis();

	// suformuojamas sąrašas automobilių, kurių kaina yra nurodytame intervale
	public ListKTUx<Automobilis> atrinktiPagalKainą(int riba1, int riba2) {
		ListKTUx<Automobilis> vidutiniaiAuto = new ListKTUx(bazinisEgz);
		for (Automobilis a : visiAuto) {
			if (a.getKaina() >= riba1 && a.getKaina() <= riba2) {
				vidutiniaiAuto.add(a);
			}
		}
		return vidutiniaiAuto;
	}

	// suformuojamas sąrašas automobilių, turinčių max kainą 
	//	(tą pačią max kainą gali turėti ir keli automobiliai)
	public ListKTUx<Automobilis> maksimaliosKainosAuto() {
		ListKTUx<Automobilis> brangiausiAuto = new ListKTUx(bazinisEgz);
		// formuojamas sąrašas su maksimalia reikšme vienos peržiūros metu
		double maxKaina = 0;
		for (Automobilis a : visiAuto) {
			double kaina = a.getKaina();
			if (kaina >= maxKaina) {
				if (kaina > maxKaina) {
					brangiausiAuto.clear();
					maxKaina = kaina;
				}
				brangiausiAuto.add(a);
			}
		}
		return brangiausiAuto;
	}

	// suformuojams sąrašas automobilių, kurių modelio kodas atitinka nurodytą
	// Ciklas su iteratoriumi
	public ListKTUx<Automobilis> atrinktiMarkęModelį(String modelioKodas) {
		ListKTUx<Automobilis> firminiaiAuto = new ListKTUx(bazinisEgz);
		for (Iterator <Automobilis> it = visiAuto.iterator(); it.hasNext(); ) {
			Automobilis a = it.next() ;			
			String pilnasModelis = a.getMarkė() + " " + a.getModelis();
			if (pilnasModelis.startsWith(modelioKodas)) {
				firminiaiAuto.add(a);
			}
		}
		return firminiaiAuto;
	}
	// metodo main nėra -> demo bandymai klasėje AutomobiliuBandymai
}
