package fr.meusnnog.deminator.net.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Michael Eusebe, Nathaël Noguès
 * @since 2016-04-29
 */
public class SockCom {

	private Socket s;

	private BufferedReader br;
	private OutputStreamWriter osw;

	private static final String SEPARATOR = " ";

	// Client
	public SockCom(String host, int port) throws IOException {
		s = new Socket(host, port);
		System.out.println("Connection au serveur réussi!");
		streamInit();
	}

	// Server
	public SockCom(Socket socket) throws IOException {
		s = socket;
		streamInit();
	}

	private void streamInit() throws IOException {
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		osw = new OutputStreamWriter(s.getOutputStream());
	}

	public void send(String msg) {
		try {
			osw.write(msg + "\n");
			osw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> receive() {
		String str;
		try {
			if ((str = br.readLine()) != null) {
				return new ArrayList<>(Arrays.asList(str.split(SEPARATOR)));
			}
		} catch (IOException e) {
		}
		return null;
	}

	public void close() {
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
