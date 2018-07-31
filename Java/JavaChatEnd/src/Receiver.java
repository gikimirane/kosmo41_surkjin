import java.io.*;
import java.net.*;

public class Receiver extends Thread{
	Socket socket;
	BufferedReader in = null;
	
	public Receiver(Socket socket) {
		this.socket = socket;
		
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "UTF-8"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (in != null) {
			try {
				System.out.println(">> " + URLDecoder.decode(in.readLine(), "UTF-8"));
			}catch(java.net.SocketException e) {
				break;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		try {
			in.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}