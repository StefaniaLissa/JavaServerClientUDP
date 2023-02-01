package juega;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class ahorcadoServer {

	private static final int AHORCADO = 6;
	final static int PUERTO = 7777;
	private static final String FIN = "FIN";

	public static void main(String[] args) {

		try {

			ServerSocket servidor = new ServerSocket(PUERTO);
			System.out.println("Conectado a puerto 7777");

			while (true) {
				Socket conexion = servidor.accept();
				System.out.println("Nuevo hilo ejecutando");

				new Thread(() -> {
					nuevaPartida(conexion);
				}).start();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void nuevaPartida(Socket conexion) {

		System.out.println("Nueva partida comenzada por: " + conexion.getInetAddress());

		int puntosPerdidos = 0;
		String palabra = cargarPalabra();
		String descubierto = inicializa(palabra);
		String respuesta = "";
		String copia;
		String letra;
		String test;
		ArrayList<String> letras = new ArrayList<>();

		try {

			DataOutputStream out = new DataOutputStream(conexion.getOutputStream());
			DataInputStream in = new DataInputStream(conexion.getInputStream());

			System.out.println(palabra);

			while (puntosPerdidos < AHORCADO && descubierto.contains("-")) {

				test = cargarJuego(puntosPerdidos) + "\n" + descubierto;
				
				out.writeUTF(test+"\nLetras intentadas: "+letras);

				copia = descubierto;

				letra = in.readUTF().toUpperCase();

				descubierto = actualiza(palabra, descubierto, letra.charAt(0));

				if (!letras.contains(letra)) {
					letras.add(letra);
					if (descubierto.equals(copia)) {
						puntosPerdidos++;
					}
				} 

			}

			if (puntosPerdidos == AHORCADO) {
				respuesta += cargarJuego(6) +"\n"+ "Perdiste!\n";
			} else {
				respuesta += "Ganaste!\n";
			}

			out.writeUTF(respuesta + palabra +"\n"+ FIN);

			System.out.println("Cliente cierra conexión");

			conexion.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String actualiza(String palabra, String descubierto, char letra) {
		String resultado = "";
		for (int i = 0; i < palabra.length(); i++) {
			if (palabra.charAt(i) == letra || palabra.charAt(i) == descubierto.charAt(i)) {
				resultado += palabra.charAt(i);
			} else {
				resultado += "-";
			}
		}
		return resultado;
	}

	private static String inicializa(String palabra) {
		String resultado = "";
		for (int i = 0; i < palabra.length(); i++) {
			resultado += "-";
		}
		return resultado;
	}

	private static String cargarPalabra() {
		String palabra = null;

		try {

			ArrayList<String> palabras = new ArrayList<>();
			BufferedReader in = new BufferedReader(new FileReader("C:/Users/slissa/Desktop/ahorcadoProgramacion.txt"));

			String lineas;
			while ((lineas = in.readLine()) != null) {
				palabras.add(lineas);
			}
			in.close();

			Random rand = new Random();
			int i = rand.nextInt(palabras.size());
			palabra = palabras.get(i);

		} catch (FileNotFoundException e) {
			System.out.println("Archivo de palabras no encontrado" + "\n");
			e.printStackTrace();

		} catch (IOException a) {
			a.printStackTrace();
		}

		return palabra;
	}

	private static String cargarJuego(int puntos) {
		String ahorcado = null;

		switch (puntos) {
		case 0: {
			ahorcado = "      +---+\r\n" + "      |   |\r\n" + "          |\r\n" + "          |\r\n" + "          |\r\n"
					+ "          |\r\n" + "    =========";
			break;
		}
		case 1: {
			ahorcado = "      +---+\r\n" + "      |   |\r\n" + "      O   |\r\n" + "          |\r\n" + "          |\r\n"
					+ "          |\r\n" + "    =========";
			break;
		}
		case 2: {
			ahorcado = "      +---+\r\n" + "      |   |\r\n" + "      O   |\r\n" + "      |   |\r\n" + "          |\r\n"
					+ "          |\r\n" + "    =========";
			break;
		}
		case 3: {
			ahorcado = "      +---+\r\n" + "      |   |\r\n" + "      O   |\r\n" + "     /|   |\r\n" + "          |\r\n"
					+ "          |\r\n" + "    =========";
			break;
		}
		case 4: {
			ahorcado = "      +---+\r\n" + "      |   |\r\n" + "      O   |\r\n" + "     /|\\  |\r\n"
					+ "          |\r\n" + "          |\r\n" + "    =========";
			break;
		}
		case 5: {
			ahorcado = "      +---+\r\n" + "      |   |\r\n" + "      O   |\r\n" + "     /|\\  |\r\n"
					+ "     /    |\r\n" + "          |\r\n" + "    =========";
			break;
		}
		case 6: {
			ahorcado = "      +---+\r\n" + "      |   |\r\n" + "      O   |\r\n" + "     /|\\  |\r\n"
					+ "     / \\  |\r\n" + "          |\r\n" + "    =========";
		}

		}
		return ahorcado;

	}
}
