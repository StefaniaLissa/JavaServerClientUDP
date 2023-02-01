package demo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HolaServer {

    public static final String MENSAJE_FIN = "adios";

    public static void main (String [] args) {

        ServerSocket server;
        String msg = "";

        try {
            
            server = new ServerSocket(7777);
            System.out.println("Escuchando en puerto: 7777");

            while(true) {
                //Bloqueo para aceptar peticiones
                Socket connCliente = server.accept();
                System.out.println("Comienzo thread");

                new Thread(()->{
                    atenderPeticion(connCliente, msg);
                }).start();

                System.out.println("Escucho otro cliente");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void atenderPeticion(Socket connCliente, String msg) {

        try {

            System.out.println("Se conectó un cliente: " + connCliente.getInetAddress());

            // Genero los dos streams de entrada/salida
            DataOutputStream out = new DataOutputStream(connCliente.getOutputStream());
            DataInputStream in = new DataInputStream(connCliente.getInputStream());
            
            // Escribir mensaje de bienvenida
            out.writeUTF("Bienvenido Cliente\n");
            out.flush();

            // Leer el mensaje que nos manda y escribirlo por pantalla
            // System.out.println("Leido mensaje de cliente " + in.readUTF());
            
            do {
                msg = in.readUTF();
                out.writeUTF(msg.toUpperCase());
            } while(!msg.equalsIgnoreCase(MENSAJE_FIN));

            System.out.println("Cliente cierra conexión");

            in.close();
            out.close();
            connCliente.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}