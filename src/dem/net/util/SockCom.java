package dem.net.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * @author MEUS
 *
 */
public class SockCom extends Socket implements Runnable {
	 private final BufferedReader br;
	 private final PrintWriter pw;
	 private boolean isListening = false;
	 private Communicator comm;
	 
	 public SockCom(String host, int port, Communicator comm) throws IOException {
		 super(host, port);
		 this.comm = comm;
		 br = new BufferedReader(new InputStreamReader(getInputStream()));
		 pw = new PrintWriter(getOutputStream(), true);
		 
		 receive();
	 }

	 public void send(String msg) {
		 comm.send(msg);
	 }
	 
	 public void receive() {
		 isListening = true;
		 String str;
		 while(isListening) {
			 try {
				if((str = br.readLine()) != null) {
					String[] spl = str.split(" ");
					List<String> params = Arrays.asList(spl);
					comm.receive(params.remove(0), params);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }

	 public void disconnect() {
	 	isListening = false;
	 }

	@Override
	public void run() {
		receive();
	}
}
