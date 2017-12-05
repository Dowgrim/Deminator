package dem.net.util;

import dem.net.client.ComPing;

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
public class SockCom extends Socket {
	 private final BufferedReader br;
	 private final PrintWriter pw;

	 private static String SEPARATEUR = " ";

	 public SockCom(String host, int port) throws IOException {
		 super(host, port);
		 br = new BufferedReader(new InputStreamReader(getInputStream()));
		 pw = new PrintWriter(getOutputStream(), true);

	 }

	 public void send(String msg) {

	 	pw.write(msg);
	 }

	 public List<String> receive() {
		 String str;
		 List<String> params;
		 try {
			 if((str = br.readLine()) != null) {
			 	params = Arrays.asList(str.split(SEPARATEUR));
				return params;
			 }
		 } catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		 return null;
	 }


}
