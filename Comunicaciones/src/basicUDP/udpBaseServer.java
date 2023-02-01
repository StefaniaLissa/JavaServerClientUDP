package basicUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
  
public class udpBaseServer
{
    private static final int MAX_LENGTH = 65535;
	private static final int PORT = 7777;
	private static final String BYE = "bye";

	public static void main(String[] args) throws IOException {
		String respuesta;
        // Step 1 : Create a socket
        DatagramSocket ds = new DatagramSocket(PORT);
        byte[] buffer = new byte[MAX_LENGTH];
  
        DatagramPacket DpReceive = null;
        while (true)
        {
  
            // Step 2 : create a DatgramPacket to receive the data.
            DpReceive = new DatagramPacket(buffer, buffer.length);
  
            // Step 3 : revieve the data in byte buffer.
            ds.receive(DpReceive);
  
            //System.out.println("Client:-" + data(receive));
           
            respuesta = new String (DpReceive.getData(), 0, DpReceive.getLength());
            System.out.println("Client:-" + respuesta);
  
            // Exit the server if the client sends "bye"
//            if (data(receive).toString().equals("bye"))
            if ( respuesta.contains(BYE)) {
                System.out.println("Client sent bye.....EXITING");
                break; }
  
            // Clear the buffer after every message.
            buffer = new byte[65535];
        }
    }

}