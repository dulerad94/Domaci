import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class MainServer {
	volatile static LinkedList<ServerSoket> soketi = new LinkedList<>();
	public static final int BROJ_PORTA_KOMANDI = 1908;
	public static final int BROJ_PORTA_PODATAKA = 123;

	public static void main(String[] args) {
		try {
			int id = 0;
			ServerSocket soketZaOsluskivanjeKomandi = new ServerSocket(BROJ_PORTA_KOMANDI);
			ServerSocket soketZaOsluskivanjePodataka = new ServerSocket(BROJ_PORTA_PODATAKA);
			while (true) {
				Socket soketZaKomunikaciju = soketZaOsluskivanjeKomandi.accept();
				soketi.add(new ServerSoket(soketZaKomunikaciju, soketZaOsluskivanjePodataka, id++));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void distribuiraj(int id, String komanda) {
		for (int i = 0; i < soketi.size(); i++) {
			if (id == soketi.get(i).id) {
				soketi.get(i).pokreniPodatke(komanda);
			}

		}
	}
}
