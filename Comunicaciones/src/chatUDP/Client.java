package chatUDP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
   public static void main(String[] args) throws IOException {
      Scanner keyboard = new Scanner(System.in);
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("localhost");

      System.out.print("Enter your username: ");
      String username = keyboard.nextLine();

      // Send a login request to the server
      String loginRequest = "LOGIN:" + username;
      final byte[] sendData = loginRequest.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
      clientSocket.send(sendPacket);

      // Receive a welcome message from the server
      byte[] receiveData = new byte[1024];
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String welcomeMessage = new String(receivePacket.getData());
      System.out.println(welcomeMessage);

      // Start a new thread to receive messages from the server
      new Thread(() -> {
         while (true) {
            try {
               clientSocket.receive(receivePacket);
               String sentence = new String(receivePacket.getData());
               System.out.println(sentence);
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }).start();

      // Start another new thread to send messages to the server
      new Thread(() -> {
         while (true) {
            String sentence = keyboard.nextLine();
            byte[] data = sentence.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, IPAddress, 9876);
            try {
               clientSocket.send(packet);
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }).start();
   }
}