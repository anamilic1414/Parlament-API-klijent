package gui;

import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.List;

import poslanik.Poslanik;

public class GUIKontroler {

	
		private static ParlamentGUI glavniProzor;
		private static List<Poslanik> poslanici;
		
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
		
		public static List<Poslanik> vratiPoslanike(){
			return poslanici;
		}
}
