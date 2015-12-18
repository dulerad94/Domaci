import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PodaciSoket extends Soket {
	String operacija;
	double brojevi[];

	public PodaciSoket(Socket soket, String operacija) {
		super(soket);
		this.operacija = operacija;
	}

	private boolean dobarUlaz(String[] ulaz) {
		for (int i = 0; i < ulaz.length; i++) {
			for (int j = 0; j < ulaz[i].length(); j++) {
				if (ulaz[i].charAt(j) - 48 < 0 || ulaz[i].charAt(j) - 57 > 0)
					return false;
			}
		}
		return true;
	}

	private void ucitajPodatke() {
		try {
			String[] podaci = ulazniTok.readLine().split(" ");
			if (dobarUlaz(podaci)) {
				ucitajBrojeve(podaci);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void ucitajBrojeve(String[] podaci) {
		for (int i = 0; i < podaci.length; i++) {
			brojevi[i]=Double.parseDouble(podaci[i]);
		}
	}

	private double izracunaj() {
		if (operacija.equals(ServerSoket.SABIRANJE))
			return saberi();
		else if (operacija.equals(ServerSoket.ODUZIMANJE))
			return oduzmi();
		else if (operacija.equals(ServerSoket.MNOZENJE))
			return mnozi();
		else if (operacija.equals(ServerSoket.DELJENJE))
			return podeli();
		throw new RuntimeException("Ne znam kako je stigao do ovde");
	}

	private double saberi() {
		double rez = 0;
		for (int i = 0; i < brojevi.length; i++) {
			rez += brojevi[i];
		}
		return rez;
	}

	private double oduzmi() {
		double rez = brojevi[0];
		for (int i = 1; i < brojevi.length; i++) {
			rez -=brojevi[i];
		}
		return rez;
	}

	private double mnozi() {
		double rez = 1;
		for (int i = 0; i < brojevi.length; i++) {
			rez *= brojevi[i];
		}
		return rez;
	}

	private double podeli() {
		double rez = brojevi[0];
		for (int i = 1; i < brojevi.length; i++) {
			rez /=brojevi[i];
		}
		return rez;
	}
	public void run() {
		try {
			String[] br=ulazniTok.readLine().split(" ");
			if(dobarUlaz(br)) ucitajBrojeve(br);
			double rezultat=izracunaj();
			izlazniTok.writeUTF(rezultat+"");		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			zatvoriSoket();
		}

	}

}
