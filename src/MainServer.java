import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class MainServer {
	static LinkedList<ServerSoket> soketi=new LinkedList<>();
	public static final int BROJ_PORTA_KOMANDI=1908;
	public static final int BROJ_PORTA_PODATAKA = 123;
	public static void main(String[] args) {
		try {
			ServerSocket soketZaOsluskivanjeKomandi=new ServerSocket(BROJ_PORTA_KOMANDI);
			ServerSocket soketZaOsluskivanjePodataka=new ServerSocket(BROJ_PORTA_PODATAKA);
			while(true){
				Socket soketZaKomunikaciju=soketZaOsluskivanjeKomandi.accept();
				soketi.add(new ServerSoket(soketZaKomunikaciju,soketZaOsluskivanjePodataka));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
