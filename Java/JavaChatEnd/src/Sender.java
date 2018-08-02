import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Sender extends Thread{

	Socket socket;
	PrintWriter out = null;
	String name = "";
	Boolean talkToOne = false;
	String tName = "";

	public Sender(Socket socket, String name) {
		this.socket = socket;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);	
			this.name = name;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public void run() {
		Scanner s = new Scanner(System.in);
		try {
			out.println(URLEncoder.encode(name, "UTF-8"));
			while (out != null) {
				try {
					String s2 = s.nextLine();
					
					if(s2.equalsIgnoreCase("q")) {
						System.out.println("Bye~~");
						break;}
					else if(s2.equals("") || s2.length()==0)	continue;
					else { 						
						String[] splt = s2.split(" ");
						
						if(splt[0].equals("/to") && splt.length>2 && !talkToOne) 	
							out.println(URLEncoder.encode(s2, "UTF-8"));
						else if(splt[0].equals("/to") && splt.length == 2 && !talkToOne) {	
							talkToOne = true;
							tName = splt[1];
							System.out.println("귓속말이 설정 되었습니다.");
						}
						else if(splt[0].equals("/to") && splt.length == 2 && talkToOne)	{
							talkToOne = false;
							System.out.println("귓속말이 해제 되었습니다.");	
						}
						else if(talkToOne)	out.println(URLEncoder.encode("/to " + tName + " " + s2, "UTF-8"));
						else	out.println(URLEncoder.encode(s2, "UTF-8"));
					}
				}catch(Exception e) {
					e.printStackTrace();
					break;
				}
			}
			out.close();
			socket.close();	
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
}