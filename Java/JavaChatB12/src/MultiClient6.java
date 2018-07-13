import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import javax.swing.*;


public class MultiClient6 {

	public static void main(String[] args) throws UnknownHostException, IOException {

		System.out.println("이름을 입력해 주세요.");
		Scanner s = new Scanner(System.in);
		String s_name = s.nextLine();
					
		try {
			String ServerIP = "localhost";
			Socket socket = new Socket(args.length==0 ? ServerIP : args[0], 9999);
			System.out.println("서버와 연결되었습니다.");
			
			Thread receiver = new Receiver6(socket);
			receiver.start();
			
//			Thread sender = new Sender6(socket, s_name);
//			sender.start();
			
			new ChatWin(socket, s_name);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
