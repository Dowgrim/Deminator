package net.util;

import net.util.Receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * 
 * @author MEUS
 *
 */
public class SockCom extends Socket implements Runnable{
	
	 private final BufferedReader br;

	 private final PrintWriter pw;
	 
	 private boolean isListening = false;
	 
	 private Receiver rec;
	 
	 
	 public SockCom(String host, int port, Receiver r) throws UnknownHostException, IOException {
		 super(host, port);
		 rec = r;
		 br = new BufferedReader(new InputStreamReader(getInputStream()));
		 pw = new PrintWriter(getOutputStream(), true);
		 
		 receive();
	 }

	 
	 public void send(String msg) {
		 
	 }
	 
	 public void receive() {
		 isListening = true;
		 String str;
		 while(isListening) {
			 try {
				if((str = br.readLine()) != null) {
					rec.receive(str);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
	 }

	@Override
	public void run() {
		receive();
	}
}
