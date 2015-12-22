import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import javax.management.RuntimeErrorException;

public class PodaciSoket extends Soket {
	private String operacija;
	private double brojevi[];
	private double rezultat;

	public PodaciSoket(Socket soket, String operacija) {
		super(soket);
		this.operacija = operacija;
	}

	private boolean ucitajPodatke() {
		try {
			String izraz = ulazniTok.readLine();
			String[] podaci = srediIzraz(izraz);
			if (dobarUlaz(podaci)) {
				ucitajBrojeve(podaci);
			} else {
				izlazniTok.println("Molimo vas unesite brojeve");
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	private void ucitajBrojeve(String[] podaci) {
		brojevi = new double[podaci.length];
		for (int i = 0; i < podaci.length; i++) {
			brojevi[i] = Double.parseDouble(podaci[i]);
		}
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

	private boolean izracunaj() {
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

	private boolean saberi() {
		rezultat = 0;
		for (int i = 0; i < brojevi.length; i++) {
			rezultat += brojevi[i];
		}
		return true;
	}

	private boolean oduzmi() {
		rezultat = brojevi[0];
		for (int i = 1; i < brojevi.length; i++) {
			rezultat -= brojevi[i];
		}
		return true;
	}

	private boolean mnozi() {
		rezultat = 1;
		for (int i = 0; i < brojevi.length; i++) {
			rezultat *= brojevi[i];
		}
		return true;
	}

	private boolean podeli() {
		rezultat = brojevi[0];
		for (int i = 1; i < brojevi.length; i++) {
			if (brojevi[i] == 0) {
				izlazniTok.println("Ne sme se deliti sa nulom");
				return false;
			}
			rezultat /= brojevi[i];

		}
		return true;
	}

	public void odradiOperaciju() {
		if (ucitajPodatke() && izracunaj())
			izlazniTok.println(rezultat);
		zatvoriSoket();

	}

	private String[] srediIzraz(String izraz) {
		if (operacija.equals(ServerSoket.SABIRANJE))
			return srediSabiranje(izraz);
		else if (operacija.equals(ServerSoket.ODUZIMANJE))
			return srediOduzimanje(izraz);
		else if (operacija.equals(ServerSoket.MNOZENJE))
			return srediMnozenje(izraz);
		else if (operacija.equals(ServerSoket.DELJENJE))
			return srediDeljenje(izraz);
		throw new RuntimeException("Otkud ovde rodjace");
	}
//		Razmisli posle
	private String[] srediSabiranje(String izraz) {
		String[] brojevi=new String[0];
		while(izraz.indexOf('+')!=-1){
			String broj=izraz.substring(0, izraz.indexOf('+'));
			String[] noviBrojevi=new String[brojevi.length+1];
			noviBrojevi[noviBrojevi.length-1]=broj;
			brojevi=noviBrojevi;
		}
		return brojevi;
	}

	private String[] srediOduzimanje(String izraz) {
		return izraz.split("-");
	}

	private String[] srediMnozenje(String izraz) {
		return izraz.split("*");
	}

	private String[] srediDeljenje(String izraz) {
		return izraz.split("/");
	}

}
