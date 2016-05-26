package main;

import java.text.ParseException;
import java.util.List;

import komunikacija.ParlamentAPIKomunikacija;
import poslanik.Poslanik;

public class Main {

	public static void main(String[] args) throws ParseException {
		
		ParlamentAPIKomunikacija kom = new ParlamentAPIKomunikacija();
		List<Poslanik> poslanici = kom.vratiPoslanike();
		
		for(Poslanik p : poslanici)
			System.out.println(p);
	}

}
