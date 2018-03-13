package dem.common.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
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

	private static final String CMD_WORD_SEPARATOR = " ";

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
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> receive() {
		String str;
		List<String> params;
		try {
			if((str = br.readLine()) != null) {
				System.out.println("YOLO");
				params = Arrays.asList(str.split(CMD_WORD_SEPARATOR));
				return params;
			}
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
