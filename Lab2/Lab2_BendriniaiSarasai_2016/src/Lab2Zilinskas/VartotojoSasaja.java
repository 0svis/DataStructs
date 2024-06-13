/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab2Zilinskas;

/**
 *
 * @author Osvaldas
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class VartotojoSasaja extends JFrame {
    	private final JMenuBar meniuBaras = new JMenuBar();
	private Container sasajosLangoTurinys;
	private final JTextArea taInformacija = new JTextArea(40, 80);	// Informacijos išvedimo langas
	private final JScrollPane zona = new JScrollPane(taInformacija);
	private final JLabel taAntraste = new JLabel("Rezultatai");
	private final JPanel paInfo = new JPanel();	// Duomenų ir rezultatų panelis. Paleidus programą jis nėra rodomas - 
												//  rodomas tik nuskaičius duomenų failą. 
	
	private JLabel perspejimas = new JLabel("Įveskite duomenis", SwingConstants.CENTER);

	public Kompiuteriai kompiuteriai = new Kompiuteriai(); // metodų klasės objektas
        	public VartotojoSasaja() {

		// Nustatymai:
		Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
		Font f = new Font("Courier New", Font.PLAIN, 12); // Suvienodinam simbolių pločius (lygiavimui JTextArea elemente)
		taInformacija.setFont(f);

		meniuIdiegimas();

		
	}
         /**
	 * Suformuoja meniu, priskiria įvykius ir įdiegia jų veiksmus. 
	 *	Sudėtingesni meniu komandos veiksmai realizuoti atskirais metodais ar atskira klase.
	 */
	private void meniuIdiegimas() {
		setJMenuBar(meniuBaras);
		JMenu mFailai = new JMenu("Failai");
		meniuBaras.add(mFailai);
		JMenu mKomp = new JMenu("Veiksmai");
		mKomp.setMnemonic('a'); // Alt + a
		meniuBaras.add(mKomp);
		JMenu mPagalba = new JMenu("Pagalba");
		meniuBaras.add(mPagalba);

		//  Grupė  "Failai" :
		JMenuItem miSkaityti = new JMenuItem("Skaityti iš failo");
		mFailai.add(miSkaityti);
		miSkaityti.addActionListener(this::veiksmaiSkaitantFailą);  // įdiegimas nuoroda į metodą
		
                // Programos uzdarymas
		JMenuItem miBaigti = new JMenuItem("Uzdaryti");
		miBaigti.setMnemonic('b'); // Alt + b
		mFailai.add(miBaigti);
		miBaigti.addActionListener((ActionEvent e) -> System.exit(0));

		//	Grupė "Veiksmai"
                
                // Pasalintu elementu is saraso atspausdinimas
		JMenuItem miDeleted = new JMenuItem("Istrintu elementu masyvas");
		mKomp.add(miDeleted);
		miDeleted.addActionListener(this::veiksmaiSuIstrintais);
                
                // Rikiavimas
		JMenuItem miRikiuoti = new JMenuItem("Surikiuoja pagal metus ir kaina"); 
		mKomp.add(miRikiuoti);
		miRikiuoti.addActionListener(this:: rikiuotiKompiuterius);
                
                // Pigausias ir brangiausias pagal kaina
                JMenuItem miMaxMin = new JMenuItem("Pigiausias ir brangiausias kompiuteriai");
                mKomp.add(miMaxMin);
                miMaxMin.addActionListener(this:: minMaxRadimas);
                
		//    Grupė  "Pagalba" :
		
		JMenuItem miApie = new JMenuItem("Apie programą");
		mPagalba.add(miApie);
		miApie.addActionListener((ActionEvent event)
				-> JOptionPane.showMessageDialog(VartotojoSasaja.this,
						"Programa sukurta studento: Osvaldas Žilinskas",
						"Apie", JOptionPane.INFORMATION_MESSAGE));

		// Sukuriamas JPanel elementas informacijai išvesti ir padedamas į JFrame langą.
		//  ** Rodomas tik nuskaičius duomenų failą (metode veiksmaiSkaitantFailą) **
		paInfo.setLayout(new BorderLayout());
		paInfo.add(taAntraste, BorderLayout.NORTH);
		paInfo.add(zona, BorderLayout.CENTER);
		perspejimas.setFont(new Font("Arial", Font.PLAIN, 18));
		getContentPane().add(perspejimas); // po failo skaitymo žymė perspejimas bus pašalinta


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // kad veiktų lango uždarymas ("kryžiukas")

	} // Metodo meniuIdiegimas pabaiga
        /**
	 * Metodas yra kviečiamas vykdant meniu punktą "Skaityti iš failo..."
	 *
	 * @param e	klasės ActionEvent objektas. Jis būtinas metodo pagal nuorodą (::) iškvietimui 
	 *		(kviečiant šį metodą su lambda, jo antraštė gali būti ir be šio parametro).
	 */
	public void veiksmaiSkaitantFailą(ActionEvent e) {
		JFileChooser fc = new JFileChooser(".");  // "." tam, kad rodytų projekto katalogą
		fc.setDialogTitle("Atidaryti failą skaitymui");
		fc.setApproveButtonText("Atidaryti");
		int rez = fc.showOpenDialog(VartotojoSasaja.this);
		if (rez == JFileChooser.APPROVE_OPTION) {
			// veiksmai, kai pasirenkamas atsakymas “Open"
			if (!paInfo.isShowing()) {
				// Jei JPanel objektas paInfo dar neįdėtas į JFrame
				sasajosLangoTurinys = getContentPane();
				sasajosLangoTurinys.remove(perspejimas);
				sasajosLangoTurinys.setLayout(new FlowLayout());
				sasajosLangoTurinys.add(paInfo);
				validate();
			}

			File f1 = fc.getSelectedFile();
			String klaidosKodas = kompiuteriai.loadAndPrint(f1, taInformacija);
			
			if (klaidosKodas != null) {
				JOptionPane.showMessageDialog(VartotojoSasaja.this, klaidosKodas,
					"Skaitymas - rašymas", JOptionPane.INFORMATION_MESSAGE);
				
			}

			
		} else if (rez == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(VartotojoSasaja.this, // kad rodyti sąsajos lango centre (null rodytų ekrano centre)
					"Skaitymo atsisakyta (paspaustas ESC arba Cancel)",
					"Skaitymas - rašymas", JOptionPane.INFORMATION_MESSAGE);
		}
                
                
	} // metodo veiksmaiSkaitantFailą pabaiga
        
        public void veiksmaiSuIstrintais(ActionEvent e) {

		if(kompiuteriai.visiKomp.isEmpty()){
			JOptionPane.showMessageDialog(null, "Tuscias masyvas");
		}else{
			String text = JOptionPane.showInputDialog(VartotojoSasaja.this, "Iveskite maziausia GPU dazni",
					"Text", JOptionPane.WARNING_MESSAGE);
			double gpuDaznis = Double.parseDouble(text);
			kompiuteriai.atrinktiPasalintus(gpuDaznis);
			taInformacija.append("Pasalinti elementai");
			taInformacija.append("\n");
			if(!kompiuteriai.visiKomp.isEmpty()){
                            taInformacija.append("|-------------------------------------------------------------------------------------------|");
                            taInformacija.append("| Gamintojas | Metai | CPU | CPU Daznis | GPU | GPU Daznis | HDD/SSD? | Drive dydis | Kaina |");
                            taInformacija.append("|-------------------------------------------------------------------------------------------|");
                            taInformacija.append(kompiuteriai.toString());
                            taInformacija.append("|-------------------------------------------------------------------------------------------|\n");
                            taInformacija.append("\n");
			}
			else{
				taInformacija.append("Masyvas yra tuscias");
				taInformacija.append("\n");
			}
		}
	} // Metodo atrankaPagalMarke pabaiga
        
        	public void rikiuotiKompiuterius(ActionEvent e){


		if(kompiuteriai.visiKomp.isEmpty()){
			JOptionPane.showMessageDialog(null, "Tuscias masyvas");
		}
		else{
			kompiuteriai.Sort();

			if(!kompiuteriai.visiKomp.isEmpty()){
                            taInformacija.append("Surikiuotas sarasas\n");
                            taInformacija.append("|-------------------------------------------------------------------------------------------|");
                            taInformacija.append("| Gamintojas | Metai | CPU | CPU Daznis | GPU | GPU Daznis | HDD/SSD? | Drive dydis | Kaina |");
                            taInformacija.append("|-------------------------------------------------------------------------------------------|");
                            taInformacija.append(kompiuteriai.toString());
                            taInformacija.append("|-------------------------------------------------------------------------------------------|\n");
                            taInformacija.append("\n");
			}
			else{
				taInformacija.append("Masyvas yra tuscias");
				taInformacija.append("\n");
			}
		}
	}
                public void minMaxRadimas(ActionEvent e){
		if(kompiuteriai.visiKomp.isEmpty()){
			JOptionPane.showMessageDialog(null, "Tuscias masyvas");
		}else{
			String text = JOptionPane.showInputDialog(VartotojoSasaja.this, "Iveskite maziausia kompiuterio kaina" +
							" pigiausiam kompiuteriui",
					"Text", JOptionPane.WARNING_MESSAGE);
			double mazKaina = Double.parseDouble(text);
			Kompiuteris pigiausias = kompiuteriai.pigiausias(mazKaina);
			if(pigiausias.getGamintojas() != null){
				taInformacija.append("Pigiausias kompiuteris yra: ");
				taInformacija.append(pigiausias.getGamintojas());
				taInformacija.append("\n");
			}
			else{
				taInformacija.append("Pigiausio kompiuterio pagal kriterijus nera\n");
			}
			String text1 = JOptionPane.showInputDialog(VartotojoSasaja.this, "Iveskite didziausia kompiuterio kaina " +
							"brangiausiam kompiuterui",
					"Text", JOptionPane.WARNING_MESSAGE);
			double kgKaina2 = Double.parseDouble(text1);
			Kompiuteris brang = kompiuteriai.brangiausias(kgKaina2);
			if(brang.getGamintojas() != null){
				taInformacija.append("Brangiausias kompiuteris yra: ");
				taInformacija.append(brang.getGamintojas());
				taInformacija.append("\n");
			}
			else{
				taInformacija.append("Brangiausio kompiuterio pagal kriterijus nera\n");
			}
                }
                }
                
                public static void main(String[] args){
                    VartotojoSasaja langas = new VartotojoSasaja();
                    langas.setSize(800, 600);
                    langas.setLocation(200, 200);
                    langas.setTitle("Duom_Struct_Pgr_LAB2");
                    langas.setVisible(true);
                }
        
}
