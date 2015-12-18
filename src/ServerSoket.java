import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

public class ServerSoket extends Soket implements Runnable {
	
	
	
	public static final String SABIRANJE = "sabiranje";
	public static final String ODUZIMANJE = "oduzimanje";
	public static final String MNOZENJE = "mnozenje";
	public static final String DELJENJE = "deljenje";
	public static final String KRAJ="kraj";
	public static final String[] dozvoljeneVrednosti = { SABIRANJE, ODUZIMANJE, MNOZENJE, DELJENJE };
	private Thread t;
	ServerSocket soketZaOsluskivanje;
	public ServerSoket(Socket soket,ServerSocket soketZaOsluskivanje) {
		super(soket);
		this.soketZaOsluskivanje=soketZaOsluskivanje;
		t = new Thread(this);
		t.setDaemon(true);
		System.out.println("tu sam");
		t.start();	
		System.out.println("tu sam");
	}

	@Override
	public synchronized void run() {
		try {
			while (true) {
				System.out.println("eo me");
				wait(2000);
				System.out.println("Sto me zajebavas");
				System.out.println(ulazniTok.toString());
				System.out.println(izlazniTok.toString());
				String komanda = ulazniTok.readLine();
				System.out.println("prosa sam");
				System.out.println(komanda);
				if(KRAJ.equals(komanda)){
					zatvoriSoket();
				}
				if (!dozvoljenaOperacija(komanda))
												continue;
				System.out.println("zezanje");
				izlazniTok.println("moze");
				Socket soketZaPodatke = soketZaOsluskivanje.accept();
				PodaciSoket podaci = new PodaciSoket(soketZaPodatke, komanda);
				podaci.run();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
