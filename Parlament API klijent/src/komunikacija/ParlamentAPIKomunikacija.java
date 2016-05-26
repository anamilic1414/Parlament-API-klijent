package komunikacija;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import poslanik.Poslanik;

public class ParlamentAPIKomunikacija {

	
	private static final String poslanikURL = "http://147.91.128.71:9090/parlament/api/members";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	
	public List<Poslanik> vratiPoslanike() throws ParseException{
		
			LinkedList<Poslanik> poslanici = new LinkedList<Poslanik>();
		try {
			String rezultat = sendGet(poslanikURL);
			
			Gson gson = new GsonBuilder().create();
			JsonArray poslaniciJson = gson.fromJson(rezultat, JsonArray.class);
			
			
			Poslanik p = null;
			JsonObject poslanikJson = null;
			
			for (int i = 0; i < poslaniciJson.size(); i++) {
				poslanikJson = (JsonObject) poslaniciJson.get(i);
				
				p = new Poslanik();
				p.setId(poslanikJson.get("id").getAsInt());
				p.setIme(poslanikJson.get("name").getAsString());
				p.setPrezime(poslanikJson.get("lastName").getAsString());
				if(poslanikJson.get("birthDate") !=null)
					p.setDatumRodjenja(sdf.parse(poslanikJson.get("birthDate").getAsString()));
				
				poslanici.add(p);
				
			}
			return poslanici;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poslanici;
		
	}
	
	private String sendGet(String poslanikUrl) throws IOException{
		URL url = new URL(poslanikUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		
		boolean kraj = false;
		String nastavak = "";
		while(!kraj){
			String s = in.readLine();
			if(s!=null)
				nastavak += s;
			else 
				kraj = true;
		}
		in.close();
	
		return nastavak.toString();
	}
	
}
