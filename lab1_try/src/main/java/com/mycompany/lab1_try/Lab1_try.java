/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lab1_try;

import java.io.*;
import javax.swing.*;

/**
 * 1-as Laboratorinis darbas
 * @author Osvaldas Zilinskas
 */
public class Lab1_try {
    /**
     * Main method
     * @param args string
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Lab1_try_GUI());
    }
/**
 * Data reading method
 * @param fv data file
 * @param komandos Team container
 */
    static void Skaitymas(String fv, Komanda komandos) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fv))) {
            String line = reader.readLine();
            String[] parts = line.split(" ");
            komandos.DėtiKomandosDuomenis(parts[0], Integer.parseInt(parts[1]));

            int kiekis = 0;
            while ((line = reader.readLine()) != null && kiekis <= 100) {
                String[] dalys = line.split(" ");
                Zaidejai zaide = new Zaidejai(dalys[0], dalys[1],
                        Double.parseDouble(dalys[2]),
                        Integer.parseInt(dalys[3]),
                        Double.parseDouble(dalys[4]),
                        Integer.parseInt(dalys[5]));
                komandos.Dėti(zaide);
                kiekis++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading input file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
/**
 * Calculates average team points
 * @param komandos Team container
 * @return 
 */
    static double Vidurkis(Komanda komandos) {
        double vidurk;
        double taskuSuma = 0;

        for (int i = 0; i < komandos.Imti(); i++) {
            taskuSuma += komandos.Imti(i).ImtiTaskuKieki();
        }

        vidurk = taskuSuma / (double) komandos.Imti();
        return vidurk;
    }
/**
 * Creates new container that includes players above average points
 * @param komandos Team container
 * @param atnaujinta New team container
 * @param vidurkis Team's average point
 */
    static void Formuoti(Komanda komandos, Komanda atnaujinta, double vidurkis) {
        for (int i = 0; i < komandos.Imti(); i++) {
            if (komandos.Imti(i).ImtiTaskuKieki() > vidurkis &&
                    komandos.Imti(i).ImtiZaistuRungtyniuSk() == komandos.ImtiKomandosVarzybuSk()) {
                atnaujinta.Dėti(komandos.Imti(i));
            }
        }
    }
/**
 * Prints data to text file
 * @param komandos Team object
 * @param antraste Headline
 * @param writer Writer variable
 */
    static void Spausdinti(Komanda komandos, String antraste, PrintWriter writer) {
        String lent = new String(new char[80]).replace('\0', '-');
        writer.println(komandos.ImtiKomandosPavadinima());
        writer.println(antraste);
        writer.println(lent);
        writer.println("Pavardė        Vardas         Ūgis           Rungtynių sk.  Taškai         Klaidos");
        writer.println(komandos.toString());
        writer.println(lent);
        writer.println();
    }
}
/**
 * Players class with Comparable implementation that allows to use CompareTo method
 * @author Osvaldas Zilinskas
 */
    class Zaidejai implements Comparable<Zaidejai> {
    private String pavarde;
    private String vardas;
    private double ugis;
    private int zaistosRungtynes;
    private double taskuKiekis;
    private int klaiduKiekis;
/**
 * Players class constructor
 * @param pavarde Player's surname
 * @param vardas Player's name
 * @param ugis Player's height
 * @param zaistosRungtynes Player's played games
 * @param taskuKiekis Player's gained points
 * @param klaiduKiekis Player's gained mistake
 */
    public Zaidejai(String pavarde, String vardas, double ugis,
                    int zaistosRungtynes, double taskuKiekis, int klaiduKiekis) {
        this.pavarde = pavarde;
        this.vardas = vardas;
        this.ugis = ugis;
        this.zaistosRungtynes = zaistosRungtynes;
        this.taskuKiekis = taskuKiekis;
        this.klaiduKiekis = klaiduKiekis;
    }
/**
 * @return player's surname
 */
    public String ImtiPavarde() {
        return pavarde;
    }
/**
 * 
 * @return player's name 
 */
    public String ImtiVarda() {
        return vardas;
    }
/**
 * 
 * @return Player's height
 */
    public double ImtiUgi() {
        return ugis;
    }
/**
 * 
 * @return Player's played games
 */
    public int ImtiZaistuRungtyniuSk() {
        return zaistosRungtynes;
    }
/**
 * 
 * @return Player's gained points
 */
    public double ImtiTaskuKieki() {
        return taskuKiekis;
    }
/**
 * 
 * @return Player's gained mistake
 */
    public int ImtiKlaiduKieki() {
        return klaiduKiekis;
    }
/**
 * Overrided toString method
 * @return formated string
 */
    @Override
    public String toString() {
        return String.format("%-15s %-15s %-15.2f %-15d %-15.2f %-15d", 
                pavarde, vardas, ugis, zaistosRungtynes, taskuKiekis, klaiduKiekis);
    }
/**
 * 
 * @param other other comparable player
 * @return comparition result
 */
    @Override
    public int compareTo(Zaidejai other) {
        int p = pavarde.compareTo(other.pavarde);
        if (p == 0) {
            return Double.compare(ugis, other.ugis);
        }
        return p;
    }
}
/**
 * Team container that includes players as array
 * @author Osvaldas Zilinskas
 */
class Komanda {
    private static final int CMaxim = 100;
    private Zaidejai[] zaid;
    private int n;
    private String komandosPavadinimas;
    private int suzaistuVarzybuKiekis;
/**
 * Team's container constructor
 */
    public Komanda() {
        komandosPavadinimas = "";
        suzaistuVarzybuKiekis = 0;
        zaid = new Zaidejai[CMaxim];
        n = 0;
    }
/**
 * 
 * @param indeksas Player index in array
 * @return Desired index
 */
    public Zaidejai Imti(int indeksas) {
        return zaid[indeksas];
    }
/**
 * 
 * @return Amount of players in container
 */
    public int Imti() {
        return n;
    }
/**
 * Puts player into container
 * @param zaide Player's object
 */
    public void Dėti(Zaidejai zaide) {
        zaid[n++] = zaide;
    }
/**
 * Puts Team's data
 * @param pavadinimas Team's name
 * @param varzybuSk Team's amount of played games
 */
    public void DėtiKomandosDuomenis(String pavadinimas, int varzybuSk) {
        komandosPavadinimas = pavadinimas;
        suzaistuVarzybuKiekis = varzybuSk;
    }
/**
 * 
 * @return Team's name
 */
    public String ImtiKomandosPavadinima() {
        return komandosPavadinimas;
    }
/**
 * 
 * @return Team's amount of played games
 */
    public int ImtiKomandosVarzybuSk() {
        return suzaistuVarzybuKiekis;
    }
/**
 * Overrided string method
 * @return formated table as string
 */
    @Override
    public String toString() {
        StringBuilder lentele = new StringBuilder();
        for (int i = 0; i < n; i++) {
            lentele.append(zaid[i].toString()).append("\n");
        }
        return lentele.toString();
    }
/**
 * Sorting method
 */
    public void Rikiuoti() {
        for (int i = 0; i < n; i++) {
            Zaidejai min = zaid[i];
            int iMin = i;
            for (int j = i + 1; j < n; j++) {
                if (zaid[j].compareTo(min) <= 0) {
                    min = zaid[j];
                    iMin = j;
                }
            }
            zaid[iMin] = zaid[i];
            zaid[i] = min;
        }
    }
/**
 * Method to remove objects
 */
    public void Ismetimas() {
        double n1 = 1, n2 = 149;
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (zaid[i].ImtiUgi() >= n1 && zaid[i].ImtiUgi() <= n2) {
                zaid[m++] = zaid[i];
            }
        }
        n = m;
    }
}