package basicUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
  
public class UdpBroadcastCliente
{
    public static void main(String args[]) throws IOException
    {
        Scanner sc = new Scanner(System.in);
  
        // Step 1:Create the socket
        DatagramSocket ds = new DatagramSocket();
        ds.setBroadcast(true);
        //InetAddress ip = InetAddress.getLocalHost();
        InetAddress ip = InetAddress.getByName("192.168.151.255");
        byte buffer[] = null;
        //byte buffer[] = "HolaMundo".getBytes();
  
        // loop while user not enters "bye"
        while (true) {
            String inp = sc.nextLine();
            // convert the String input into the byte array.
            buffer = inp.getBytes();
  
            // Step 2 : Create the datagramPacket
            DatagramPacket DpSend = new DatagramPacket(
            		buffer, 
            		buffer.length, 
            		ip, 
            		7777);
  
            // Step 3 : invoke the send call to actually send
            // the data.
            ds.send(DpSend);
  
            // break the loop if user enters "bye"
            if (inp.equals("bye"))
            break;
        }
    }
}
