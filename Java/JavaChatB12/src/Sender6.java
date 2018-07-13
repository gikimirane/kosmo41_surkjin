import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Sender6 extends Thread{

	Socket socket;
	PrintWriter out = null;
	String name = "";
	Boolean talkToOne = false;
	
	public Sender6(Socket socket, String name) {
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
			out.println(name);
			while (out != null) {
				try {
					String s2 = s.nextLine();
					if(s2.equalsIgnoreCase("q")) {
						//out.println(s2);
						break;
					}else { 
						String[] splt = s2.split(" ");
						System.out.println("----" + talkToOne);
						if(splt[0].equals("/to") && splt.length>2 && !talkToOne) 		
								out.println(name + "=>" + s2);
						else if(splt[0].equals("/to") && splt.length == 2 && !talkToOne)	
								talkToOne = true;		
						else if(splt[0].equals("/to") && splt.length == 2 && talkToOne)	
								talkToOne = false;
						else if(talkToOne)	out.println(name + "=>" + "/to1" + s2);
						else	out.println(name + "=>" + s2);
					}
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
