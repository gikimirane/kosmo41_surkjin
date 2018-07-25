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
			out.println(name);
			while (out != null) {
				try {
					String s2 = s.nextLine();
					if(s2.equals("") || s2.length()==0)	continue;
					if(s2.equalsIgnoreCase("q")) {
						System.out.println("Bye~~");
						break;
					}else { 
						String[] splt = s2.split(" ");
						
						if(splt[0].equals("/to") && splt.length>2 && !talkToOne) 	
								out.println(s2);
							//	out.println(name + "=>" + s2);
						else if(splt[0].equals("/to") && splt.length == 2 && !talkToOne) {	
								talkToOne = true;
								tName = splt[1];
						}
						else if(splt[0].equals("/to") && splt.length == 2 && talkToOne)	
								talkToOne = false;
						else if(talkToOne)	out.println("/to " + tName + " " + s2);
		//				else if(talkToOne)	out.println(name + "=>" + "/to " + tName + " " + s2);
						else	out.println(s2);
						//else	out.println(name + "=>" + s2);
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