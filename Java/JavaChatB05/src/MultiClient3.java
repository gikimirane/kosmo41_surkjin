import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiClient3 {

	public static void main(String[] args) throws UnknownHostException, IOException {

		System.out.println("이름을 입력해 주세요.");
		Scanner s = new Scanner(System.in);
		String s_name = s.nextLine();
		
		PrintWriter out = null;
			
		try {
			String ServerIP = "localhost";
			Socket socket = new Socket(args.length==0 ? ServerIP : args[0], 9999);
			System.out.println("서버와 연결.....");
			
			Thread receiver = new Receiver3(socket);
			receiver.start();
			
			out = new PrintWriter(socket.getOutputStream(), true);
			
			out.println(s_name);
			
			while (out != null){
				try {
					String s2 = s.nextLine();
					if(s2.equalsIgnoreCase("q")) {
						out.println(s2);
						break;
					}else 
						out.println(s_name + "=>" + s2);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			out.close();
			socket.close();			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
