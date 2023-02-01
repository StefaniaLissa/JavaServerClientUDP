package demo;

import java.io.*;
import java.io.IOException;
import java.net.Socket;

public class HolaSocket {

	public static void main( String[] args) {

			try {

				Socket con = new Socket("192.168.1.135", 7777);
				System.out.println("Conectado a Server");
				
				DataInputStream is = new DataInputStream(con.getInputStream());
				System.out.println("Cliente dice: " + is.readUTF());
				
				DataOutputStream out = new DataOutputStream(con.getOutputStream());
				out.write("Hola\n".getBytes());
				
				out.close();
				con.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
