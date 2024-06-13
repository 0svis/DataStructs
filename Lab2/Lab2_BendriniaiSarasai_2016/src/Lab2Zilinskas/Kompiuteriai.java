/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab2Zilinskas;

/**
 *
 * @author Osvaldas
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTextArea;

import studijosKTU.*;

public class Kompiuteriai {
    public ListKTUx<Kompiuteris> visiKomp = new ListKTUx<>(new Kompiuteris());
    public ListKTUx<Kompiuteris> antrasKomp = new ListKTUx<>(new Kompiuteris());
    
    public Kompiuteriai(){
        
    }
    /**
     * Atrenka ir pasalina kompiuterius is saraso pagal gpuDazni
     * @param gpuDaznis GPU Daznis
     */
    public void atrinktiPasalintus(double gpuDaznis)
    {
        int i=0;
        for (Kompiuteris k : visiKomp){
            if(k.getGpuDaznis()<gpuDaznis)
            {
                visiKomp.remove(i);
            }
            else { i++; }
        }
    }
    /**
     * Metodas, randantis brangiausia PC, tarp kainu ribu
     * @param kaina didziausia galima kaina
     * @return brangiausias kompiuteri
     */
    public Kompiuteris brangiausias(double kaina)
    {
        Kompiuteris komp = new Kompiuteris();
        double kaina1 = 0.0;
        for (Kompiuteris k : visiKomp)
        {
            if(k.getKaina() > kaina1 && k.getKaina() <= kaina)
            {
                komp = k;
                kaina1 = k.getKaina();
            }
        }
        return komp;
    }
    
    /**
     * Metodas, randantis pigiausia PC tarp kainos ribu
     * @param kaina maziausia galima kaina
     * @return pigiausias kompiuteris
     */
    public Kompiuteris pigiausias(double kaina)
    {
        Kompiuteris komp = new Kompiuteris();
        double kaina1 = visiKomp.get(0).getKaina();
        for (Kompiuteris k : visiKomp)
        {
            if(k.getKaina() < kaina1 && k.getKaina() >= kaina)
            {
                komp = k;
                kaina1 = k.getKaina();
            }
    }
        return komp;
    }
    /**
     * nuskaitymo metodas
     * @param fv failo pavadinimas
     * @return 
     */
    static ListKTUx<Kompiuteris> read(File fv)
    {
        String klaidosK = null;
        ListKTUx<Kompiuteris> komp = new ListKTUx<Kompiuteris>(new Kompiuteris());
        try (BufferedReader reader = new BufferedReader(new FileReader(fv)))
        {
            String line;
            while ((line=reader.readLine()) != null)
            {
                String[] parts = line.split(" ");
                String gamintojas = parts[0];
                int metai = Integer.parseInt(parts[1]);
                String procesorius = parts[2];
                double cpuDaznis = Double.parseDouble(parts[3]);
                String gpu = parts[4];
                double gpuDaznis = Double.parseDouble(parts[5]);
                String driveTipas = parts[6];
                int driveDydis = Integer.parseInt(parts[7]);
                double kaina = Double.parseDouble(parts[8]);
                
                Kompiuteris kompiuteris = new Kompiuteris(gamintojas, metai, procesorius, cpuDaznis, gpu, gpuDaznis, driveTipas, driveDydis, kaina);
                komp.add(kompiuteris);
            }
            reader.close();
        } catch (Exception e) {
            klaidosK = "Failo" + fv.getName() + "skaitymo klaida";
        }
        return komp;
        }
    /**
     * toString teksto formatavimo metodas
     * @return formatuotas tekstas
     */
    @Override
    public String toString()
    {
        String collection = "";
        for (Kompiuteris k : visiKomp)
        {
             collection += k.toString() + "\n";
        }
        return collection;
    }
    /**
     * Metodas failo nuskaitymui ir duomenu atvaizdavimui
     * @param name failo pavadinimas
     * @param ta teksto laukas
     * @return klaidos pranseimas, jei reikalingas
     */
    public String loadAndPrint(File name, JTextArea ta)
    {
        String klaidosK = null;
        try {
            visiKomp.clear();
            BufferedReader fReader = new BufferedReader(new FileReader(name));
            String line;
            ta.append(" D U O M E N Y S   I S   F A I L O ");
            while((line = fReader.readLine()) != null)
            {
                visiKomp.add(new Kompiuteris(line));
            }
            print(visiKomp, "Kompiuteriu sarasas", ta);
            fReader.close();
        } catch (IOException e) {
            klaidosK = "Failo " + name.getName() + " skaitymo klaida";
        }
        return klaidosK;
    }
    /**
     * Spausdinimo metodas
     * @param komp kompiuterio objektas
     * @param heading teksto virsus
     * @param ta teksto laukas
     */
    public void print(ListKTUx<Kompiuteris> komp, String heading, JTextArea ta)
    {
        ta.append("\n");
        ta.append(heading);
        ta.append("\n");
        ta.append("|-------------------------------------------------------------------------------------------|\n");
        ta.append("| Gamintojas | Metai | CPU | CPU Daznis | GPU | GPU Daznis | HDD/SSD? | Drive dydis | Kaina |\n");
        ta.append("|-------------------------------------------------------------------------------------------|\n");
        for (Kompiuteris k = komp.get(0); k!=null; k=komp.getNext())
        {
            ta.append(k.toString() + "\n");
        }
        ta.append("|-------------------------------------------------------------------------------------------|\n");
    }
    /**
     * Rikiavimo pagal Metus ir Kaina metodas
     */
    public void Sort()
    {
        visiKomp.sortBuble(Kompiuteris.pagalMetusKaina);
    }
}
