import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public abstract class Soket {
	protected Socket soket;
	protected BufferedReader ulazniTok;
	protected PrintStream izlazniTok;
	
	
	public Soket(Socket soket) {
		this.soket = soket;
		uspostaviVeze();
	}
	
	public void uspostaviVeze() {
		try {
			ulazniTok = new BufferedReader(new InputStreamReader(soket.getInputStream()));
			System.out.println("Ulaz uspostavljen");
			izlazniTok = new PrintStream(soket.getOutputStream());
			System.out.println("Izlaz uspostavljen");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void zatvoriSoket(){
		try {
			ulazniTok.close();
			izlazniTok.close();
			soket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
