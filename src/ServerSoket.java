import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.LinkedList;

public class ServerSoket extends Soket implements Runnable {
	
	
	
	public static final String SABIRANJE = "sabiranje";
	public static final String ODUZIMANJE = "oduzimanje";
	public static final String MNOZENJE = "mnozenje";
	public static final String DELJENJE = "deljenje";
	public static final String KRAJ="kraj";
	public static final String[] dozvoljeneVrednosti = { SABIRANJE, ODUZIMANJE, MNOZENJE, DELJENJE };
	 int id;
	private Thread t;
	ServerSocket soketZaOsluskivanje;
	
	public ServerSoket(Socket soket,ServerSocket soketZaOsluskivanje,int id) {
		super(soket);
		this.soketZaOsluskivanje=soketZaOsluskivanje;
		this.id=id;
		t = new Thread(this);
		t.setDaemon(true);
		t.start();	
	}

	@Override
	public void run() {
		try {
			while (true) {
				String komanda;
				  komanda = ulazniTok.readLine();
				if(KRAJ.equals(komanda)){
					zatvoriSoket();
					return;
				}
				if (!dozvoljenaOperacija(komanda)){
								izlazniTok.println("ne moze");
												continue;
				}
				
				izlazniTok.println("moze");
				izlazniTok.println(String.valueOf(id));
				String IdOdKlijentaS=ulazniTok.readLine();
				int IdOdKlijenta=Integer.parseInt(IdOdKlijentaS);
				MainServer.distribuiraj(IdOdKlijenta, komanda);
			}
		}catch (SocketException e) {
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			zatvoriSoket();
		}

	}

	private boolean dozvoljenaOperacija(String operacija) {
		for (int i = 0; i < dozvoljeneVrednosti.length; i++) {
			if (operacija.equals(dozvoljeneVrednosti[i]))
				return true;
		}
		return false;
	}
	
	public void zatvoriSoket(){
		super.zatvoriSoket();
		MainServer.soketi.remove(this);	
	}
	public void pokreniPodatke(String komanda){
		Socket soketZaPodatke;
		try {
			soketZaPodatke = soketZaOsluskivanje.accept();
			PodaciSoket podaci = new PodaciSoket(soketZaPodatke, komanda);
			podaci.odradiOperaciju();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
