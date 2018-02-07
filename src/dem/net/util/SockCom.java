package dem.net.util;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * @author MEUS
 *
 */
public class SockCom{

	private Socket s;

	private BufferedReader br;
	private OutputStreamWriter osw;

	private static String SEPARATEUR = " ";

	// Client
	public SockCom(String host, int port) throws IOException {
		s = new Socket(host, port);
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
            osw.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}



	public List<String> receive() {
		String str;
		List<String> params;
		try {
			if((str = br.readLine()) != null) {
				System.out.println("YOLO");
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
