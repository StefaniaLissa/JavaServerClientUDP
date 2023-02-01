package juega;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class juegaS3 {

	public static void main(String[] args) {
		final String IP = args[0];
		final int PUERTO = 7777;
		Socket socket = null;
		
		try {
			socket = new Socket(IP, PUERTO);
			
  			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());

			String FIN = "FIN";
			String descubierto="";
			Scanner sc = new Scanner(System.in);

			while(!descubierto.equals(FIN)){
				
				descubierto = in.readUTF();
				System.out.println(descubierto);
				
				System.out.println("Server espera: ");
				out.writeUTF(sc.nextLine());
					
			}
			
		} catch (Exception e) {
		}
	}
	
}
