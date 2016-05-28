package gui;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import gui.model.TableModel;
import komunikacija.ParlamentAPIKomunikacija;
import poslanik.Poslanik;

public class GUIKontroler {

	private static ParlamentGUI glavniProzor;
	private static List<Poslanik> poslanici;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					poslanici = new LinkedList<>();
					glavniProzor = new ParlamentGUI();
					glavniProzor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static LinkedList<Poslanik> vratiPoslanike() {
		return (LinkedList<Poslanik>) poslanici;
	}

	public static void vratiUJson() {

		try {
			JsonArray json = ParlamentAPIKomunikacija.serijalizacijaPoslanika(ParlamentAPIKomunikacija.vratiPoslanike());

			PrintWriter out = new PrintWriter(new BufferedWriter( new FileWriter("data/serviceMembers.json")));

			String tekst = new GsonBuilder().setPrettyPrinting().create().toJson(json);

			out.println(tekst);

			out.close();

			glavniProzor.getTextArea().setText("Podaci su uspesno vraceni u Json!");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), "Nije uspelo vracanje podataka u json",
					"Greska!", JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void napuniTabelu() {
		ubaciIzFajla();
		glavniProzor.osveziTabelu();
		glavniProzor.getTextArea().setText("Tabela je popunjena podacima uzetim sa servisa!");
		
	}

	private static void ubaciIzFajla(){
		poslanici = new LinkedList<Poslanik>();

		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("data/serviceMembers.json"));

		Gson gson = new GsonBuilder().create();
		JsonArray poslaniciJson = gson.fromJson(in, JsonArray.class);


		Poslanik p = null;
		JsonObject poslanikJson = null;

		for (int i = 0; i < poslaniciJson.size(); i++) {
			poslanikJson = (JsonObject) poslaniciJson.get(i);
			p = new Poslanik();
			p.setId(poslanikJson.get("id").getAsInt());
			p.setIme(poslanikJson.get("ime").getAsString());
			p.setPrezime(poslanikJson.get("prezime").getAsString());
			
			if (poslanikJson.get("datumRodjenja") != null)
				
				p.setDatumRodjenja(sdf.parse(poslanikJson.get("datumRodjenja").getAsString()));

			poslanici.add(p);
			}
			in.close();

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), "Nije pronađen fajl","Greška!", JOptionPane.ERROR_MESSAGE);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), "Greška kod unosa datuma","Greška!", JOptionPane.ERROR_MESSAGE);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void izTabeleUListu(JTable tabela){
		TableModel model = (TableModel) tabela.getModel();
		int redovi = model.getRowCount();
		
		poslanici = new LinkedList<Poslanik>();
		
		for (int i = 0; i < redovi; i++) {
			poslanici.add(model.vrednostURedu(i));
		}
	}

	public static void update(LinkedList<Poslanik> poslanici) {
		
		JsonArray json = ParlamentAPIKomunikacija.serijalizacijaPoslanika(poslanici);
		
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data/updatedMembers.json")));
			
			out.write(new GsonBuilder().setPrettyPrinting().create().toJson(poslanici));
			
			out.close();
			
			glavniProzor.getTextArea().setText("Promene su sacuvane!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(glavniProzor, "Greska pri cuvanje promena!", "Greska!",JOptionPane.ERROR_MESSAGE);
		}
		
		
	}

}
