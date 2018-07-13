import java.io.*;
import java.net.*;

public class MultiServer4 {
	
	ServerSocket serverSochet = null;
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	String s = "";
	
	public MultiServer4() {
		
	}
	
	public void init() {
		try {
			serverSochet = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");
			
			socket = serverSochet.accept();
			System.out.println(socket.getInetAddress() + ":" + socket.getPort());
			
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
										socket.getInputStream()));
			while(in != null) {
				s = in.readLine();
				
				if(s == null) break;
				if(s.equalsIgnoreCase("q")) break;
				
				System.out.println(s);
				sendAllMsg(s);
			}
			System.out.println("Bye...");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
				out.close();
				
				socket.close();
				serverSochet.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void sendAllMsg(String msg) {
		try {
			out.println(msg);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){

		MultiServer4 ms = new MultiServer4();
		ms.init();		
	}	

}
