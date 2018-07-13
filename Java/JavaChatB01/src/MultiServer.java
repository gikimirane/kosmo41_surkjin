import java.io.*;
import java.net.*;

public class MultiServer {

	public static void main(String[] args){

		ServerSocket serverSochet = null;
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		String s = "";
		
		try {
			serverSochet = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");
			
			socket = serverSochet.accept();
			System.out.println(socket.getInetAddress() + ":" + socket.getPort());
			
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
										socket.getInputStream()));
			s = in.readLine();
			System.out.println(s);
			out.println(s);
			
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

}
