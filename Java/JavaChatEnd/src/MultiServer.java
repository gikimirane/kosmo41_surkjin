import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.*;

public class MultiServer {
	ServerSocket serverSochet = null;
	Socket socket = null;
	Map<String, PrintWriter> clientMap;
	
	public MultiServer() {
		clientMap = new HashMap<String, PrintWriter>();
		Collections.synchronizedMap(clientMap);
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
		
	public void sendAllMsg(String msg) {
	
		Iterator<String> it = clientMap.keySet().iterator();
		String name="";
		while(it.hasNext()) {
			try {
				name = it.next();
				PrintWriter it_out = (PrintWriter)clientMap.get(name);
				it_out.println("["+name+"] "+ msg);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	class MultiServerT extends Thread{
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		private String noThread = "00";
		
		MultiServerT(int n){
			if(n<10)	noThread = "0" + n;
			else		noThread = "" + n;
		}
		
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
			Connection con=null;
			String name = "";
			try {
				con = ConnectionPool.getConnection("env " + noThread);
				con.setAutoCommit(false);
				String sql = "select sysdate from dual";
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) 
					System.out.println(rs.getDate(1) + " : " + noThread + " : " + con);
				//pstmt.close();
				ConnectionPool.listCacheInfos();
				
				while(true) {
					name = in.readLine();
					int[] check_f = check_member(con, name);
					if(check_f[1]==1)	
						out.println(name + "-> 블랙리스트 대상자입니다. 접속할 수 없습니다");	
					else if(check_f[0]==1) 
						out.println(name + 	": 동일한 이름이 접속되어 있습니다\n"
										 +	"다른 이름으로 접속해 주십시요.");
					else break;
				}
				
				clientMap.put(name, out);
				sendAllMsg("님이 입장하셨습니다.");	
				System.out.println("현재 접속자 수는 " + clientMap.size() + "명 입니다.");

				String s = "";
				while(in != null) {
					s = in.readLine();
					System.out.println(s);
					
					if(s.equals("/list")) 										showList(name);
					else if(s.length()>3 && s.substring(0, 3).equals("/no"))	blockList(con, name, s, out);
					else if(s.length()>3 && s.substring(0, 3).equals("/to"))	
																				talkToOne(name, s);
					else														sendAllMsg(s);

///					if(s.equals(name+"=>"+"/list")) 	showList(name);
//					else if(s.length()>name.length()+5 && s.substring(name.length()+2, name.length()+2+3).equals("/to"))	
//														talkToOne(name, s);
//					else								sendAllMsg(s);
				}
				pstmt.close();
				con.close();
			}catch(SQLException  e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				System.out.println("sleep..." + noThread);
				try {
					Thread.sleep(1000);
				}catch(Exception e){}
				
				clientMap.remove(name);
				sendAllMsg("님이 퇴장하셨습니다.");
				System.out.println("현재 접속자 수는 " + clientMap.size() + "명 입니다.");
				deleteMbr(con, name);
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
	
	public int[] check_member(Connection con, String name) {
		int[] count = {0, 0};
		try {
			String sql = "select rownum, to_number(check_f)  from member_nm " 
						+"where mbr_nm = '"+name+"'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 	count[0] = rs.getInt(1); count[1] = rs.getInt(2);}
			if(count[0]==0) {
				sql = "insert into member_nm(mbr_nm) values('"+name+"')";  
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				con.commit();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public void showList(String name) {
		
			Set<String> k = clientMap.keySet();
			PrintWriter n_out = (PrintWriter)clientMap.get(name);
			n_out.println("현재 접속자입니다("+ clientMap.size()+"명).");
			//for(String s : k )	n_out.println(s) ;
			n_out.println(k);
	}
	
	public void blockList(Connection con, String name, String s, PrintWriter out) {
		try {
			String[] s1 = s.split(" ");
			String sql = "select count(*) from block_list where mbr_nm='"+name+"' and blk_nm='"+s1[1]+"'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int cnt = 0;
			while(rs.next()) 	cnt = rs.getInt(1);
			if(cnt==0) {
				sql = "insert into block_list values('"+name+"', '"+s1[1]+"')";
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				con.commit();
				out.println(s1[1] + "와의 대화를 차단합니다.");
			}
			else {
				sql = "delete from block_list where mbr_nm='"+name+"' and blk_nm='"+s1[1]+"')";
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				con.commit();
				out.println(s1[1] + "와의 차단을 해제합니다.");	
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMbr(Connection con, String name) {
		try {
			String sql = "delete from member_nm where mbr_nm = '"+name+"'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			sql = "delete from block_list where mbr_nm = '"+name+"'";
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			con.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void talkToOne(String nm, String s) {
		
//		String[] s1 = s.substring(nm.length()+2).split(" ");
		String[] s1 = s.split(" ");
		
		PrintWriter out = (PrintWriter)clientMap.get(s1[1]);
//		String s2 = "(귓속말)" + nm + "=>" + s.substring(nm.length()+2+ s1[0].length()+s1[1].length()+2);
		String s2 = "(귓속말)" + nm + "=>" + s.substring(4);		
		out.println(s2) ;	
	}
	
	public static void main(String[] args){

		MultiServer ms = new MultiServer();
		ms.init();		
	}	
}
