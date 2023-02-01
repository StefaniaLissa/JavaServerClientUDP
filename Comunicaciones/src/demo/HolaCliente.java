package demo;

import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class HolaCliente {
	
	public static void main( String[] args) {
		Scanner sc = new Scanner(System.in);
		String ip = "192.168.1.135";
		int port = 7777;
		
		try {
			
			Socket conn = new Socket (ip, port);
			System.out.println("Conectando con el servidor");
			
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			DataInputStream in = new DataInputStream(conn.getInputStream());
			
			System.out.println("Server dice: "+ in.readUTF());
			
			String msg;
			
			do {
				msg = sc.nextLine();
				out.writeUTF(msg);
				String msgMod = in.readUTF();
				System.out.println("El servidor dice: "+msgMod);
			}while(!msg.equalsIgnoreCase(HolaServer.MENSAJE_FIN));
			
			sc.close();
			in.close();
			out.close();
			conn.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
