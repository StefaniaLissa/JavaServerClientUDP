package chatUDP;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
   public static void main(String[] args) throws IOException {
      DatagramSocket serverSocket = new DatagramSocket(9876);
      byte[] receiveData = new byte[1024];

      while (true) {
         DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
         serverSocket.receive(receivePacket);
         String sentence = new String(receivePacket.getData());
         InetAddress clientAddress = receivePacket.getAddress();
         int clientPort = receivePacket.getPort();

         // If the message starts with "LOGIN:", process it as a login request
         if (sentence.startsWith("LOGIN:")) {
            String username = sentence.substring(6);
            System.out.println(username + " has logged in.");
            clients.put(username, new ClientInfo(clientAddress, clientPort));

            // Send a welcome message back to the client
            String welcomeMessage = "Welcome, " + username + "!";
            byte[] sendData = welcomeMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);
         } else {
        	 
            System.out.println("Received: " + sentence + " from " + clientAddress + ":" + clientPort);
            String capitalizedSentence = sentence.toUpperCase();

            // Start a new thread to send the capitalized sentence back to all clients except the sender
            new Thread(() -> {
               for (Map.Entry<String, ClientInfo> entry : clients.entrySet()) {
                  String username = entry.getKey();
                  ClientInfo clientInfo = entry.getValue();
                  InetAddress address = clientInfo.getAddress();
                  int port = clientInfo.getPort();

                  if (!clientAddress.equals(address) || clientPort != port) {
                     byte[] sendData = capitalizedSentence.getBytes();
                     DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
                     try {
                        serverSocket.send(sendPacket);
                     } catch (IOException e) {
                        e.printStackTrace();
                     }
                  }
               }
            }).start();
         }
      }
   }

   private static Map<String, ClientInfo> clients = new HashMap<>();

   private static class ClientInfo {
      private InetAddress address;
      private int port;

      public ClientInfo(InetAddress address, int port) {
         this.address = address;
         this.port = port;
      }

      public InetAddress getAddress() {
         return address;
      }

      public int getPort() {
         return port;
      }
   }
}