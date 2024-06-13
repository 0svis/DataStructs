/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab2Zilinskas;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import studijosKTU.*;

/**
 *
 * @author Osvaldas
 */
public class Kompiuteris implements KTUable<Kompiuteris> {
        final static private int priimtinuMetuRiba = 2009;
	final static private int esamiMetai = LocalDate.now().getYear();
        final static private double minKaina = 50.0;
	final static private double maxKaina = 120_000.0;
        
        private String gamintojas;
        private int metai;
        private String procesorius;
        private double procDaznis;
        private String gpu;
        private double gpuDaznis;
        private String driveTipas;
        private int driveDydis;
        private double kaina;
        
        public Kompiuteris(){
        }
        public Kompiuteris(String gamintojas, int metai, String procesorius, 
                double procDaznis, String gpu, double gpuDaznis,
                String driveTipas, int driveDydis, double kaina){
        this.gamintojas = gamintojas;
        this.metai = metai;
        this.procesorius = procesorius;
        this.procDaznis = procDaznis;
        this.gpu = gpu;
        this.gpuDaznis = gpuDaznis;
        this.driveTipas = driveTipas;
        this.driveDydis = driveDydis;
        this.kaina = kaina;
        }
        public Kompiuteris(String dataString)
        {
            this.parse(dataString);
        }
        
        @Override
	public Kompiuteris create(String dataString) {
		return new Kompiuteris(dataString);
	}
        @Override
	public final void parse(String dataString) {
		try {   
			Scanner ed = new Scanner(dataString);
			gamintojas = ed.next();
                        metai = ed.nextInt();
			procesorius = ed.next();
			procDaznis = ed.nextDouble();
			gpu = ed.next();
			gpuDaznis = ed.nextDouble();
                        driveTipas = ed.next();
                        driveDydis = ed.nextInt();
                        setKaina(ed.nextDouble());
		} catch (InputMismatchException e) {
			Ks.ern("Blogas duomenų formatas apie kompiuteri -> " + dataString);
		} catch (NoSuchElementException e) {
			Ks.ern("Trūksta duomenų apie kompiuteri -> " + dataString);
		}
	}
        @Override
	public String validate() {
		String klaidosTipas = "";
		if (metai < priimtinuMetuRiba || metai > esamiMetai) {
			klaidosTipas = "Netinkami gamybos metai, turi būti ["
					+ priimtinuMetuRiba + ":" + esamiMetai + "]";
		}
		if (kaina < minKaina || kaina > maxKaina) {
			klaidosTipas += " Kaina už leistinų ribų [" + minKaina
					+ ":" + maxKaina + "]";
		}
		return klaidosTipas;
	}
        @Override
	public String toString() {  // surenkama visa reikalinga informacija
		return String.format("%-10s %-4d %-12s %-1.2f %-12s %-1.2f %-4s %-6d %-8.2f %s",
				gamintojas, metai, procesorius, procDaznis, gpu, gpuDaznis, driveTipas, driveDydis, kaina, validate());
	}
        public String getGamintojas()
        {
            return gamintojas;
        }
        public int getMetai()
        {
            return metai;
        }
        public String getProcesorius()
        {
            return procesorius;
        }
        public double getProcDaznis()
        {
            return procDaznis;
        }
        public String getGpu()
        {
            return gpu;
        }
        public double getGpuDaznis()
        {
            return gpuDaznis;
        }
        public String getDriveTipas()
        {
            return driveTipas;
        }
        public int getDriveDydis()
        {
            return driveDydis;
        }
        public double getKaina()
        {
            return kaina;
        }
        public void setKaina(double kaina)
        {
            this.kaina = kaina;
        }
        @Override
	public int compareTo(Kompiuteris a) {
	// lyginame pagal svarbiausią požymį - kainą
	double kainaKita = a.getKaina();
        int metaiKiti = a.getMetai();
	//return Double.compare(kainaKita, kaina);
        int kainosPalyginimas = Double.compare(kainaKita, kaina);
        if(kainosPalyginimas != 0)
        {
            return kainosPalyginimas;
        }
        else 
        {
            return Integer.compare(metaiKiti, metai);
        }
	}
        public final static Comparator<Kompiuteris> pagalGam = new Comparator<Kompiuteris>() {
        @Override
        public int compare(Kompiuteris o1, Kompiuteris o2) {
            int sk = o1.getGamintojas().compareTo(o2.getGamintojas());
            return sk;
        }
    };
        public final static Comparator<Kompiuteris> pagalProc = new Comparator<Kompiuteris>() {
        @Override
        public int compare(Kompiuteris o1, Kompiuteris o2) {
            int sk = o1.getProcesorius().compareTo(o2.getProcesorius());
            return sk;
        }
    };
        public final static Comparator<Kompiuteris> pagalGPU = new Comparator<Kompiuteris>() {
        @Override
        public int compare(Kompiuteris o1, Kompiuteris o2) {
            int sk = o1.getGpu().compareTo(o2.getGpu());
            return sk;
        }
    };
        public final static Comparator<Kompiuteris> pagalMetusKaina = (Kompiuteris k1, Kompiuteris k2) -> {
            if(k1.getKaina()<k2.getKaina()) return 1;
            if(k1.getKaina()>k2.getKaina()) return -1;
            if(k1.getMetai()<k2.getMetai()) return 1;
            if(k1.getMetai()>k2.getMetai()) return -1;
            return 0;
        };  
}