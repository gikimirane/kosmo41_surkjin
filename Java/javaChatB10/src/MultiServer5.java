import java.io.*;
import java.net.*;

public class MultiServer5 {
	
	ServerSocket serverSochet = null;
	Socket socket = null;
	
	public MultiServer5() {
		
	}
	
	public void init() {
		try {
			serverSochet = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");
			
			while(true) {
				socket = serverSochet.accept();
				System.out.println(socket.getInetAddress() + ":" + socket.getPort());
			
				Thread msr = new MultiServerT(socket);
				msr.start();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {	
				serverSochet.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
		
	public void sendAllMsg(String msg, PrintWriter out) {
		try {
			out.println(msg);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){

		MultiServer5 ms = new MultiServer5();
		ms.init();		
	}	
	
	class MultiServerT extends Thread{
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		public MultiServerT(Socket socket) {
			this.socket = socket;
			try {
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(
											socket.getInputStream()));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			
			String s = "";
			try {
				while(in != null) {
					s = in.readLine();
					
					if(s == null) break;
					if(s.equalsIgnoreCase("q")) break;
					
					System.out.println(s);
					sendAllMsg(s, out);
				}
				System.out.println("Bye...");
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					in.close();
					out.close();	
					socket.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
