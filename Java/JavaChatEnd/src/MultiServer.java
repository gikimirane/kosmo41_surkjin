import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.*;

public class MultiServer {
	ServerSocket serverSochet = null;
	Socket socket = null;
	Connection con=null;
	Map<String, PrintWriter> clientMap;
	int room_no = 0;
	
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
		
	public void sendAllMsg(String name, String msg) {
			
		//서버 금칙어 처리
		String[] str = msg.split(" ");
		PreparedStatement pstmt=null;
		String sql = "";
		int banWord = 0;
		
		for(int i=0; i<str.length; i++) {
			sql = "select count(*) from van_word where mbr_nm= '$system$' and word = '"+str[i].trim()+"'";		
			try {
				pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next())  	banWord = rs.getInt(1);
				if(banWord > 0) {
					clientMap.get(name).println(str[i].trim() + "는 금칙어입니다.");
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
					
		Iterator<String> it = clientMap.keySet().iterator();
		String mName="", blk_nm="";
		while(it.hasNext()) {
			try {
				mName = it.next();
				
				//개인금칙어 처리
				for(int i=0; i<str.length; i++) {
					banWord = 0;
					sql = "select count(*) from van_word where mbr_nm='"+mName+"' and word ='"+str[i].trim()+"'";
					pstmt = con.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					while(rs.next())  	banWord = rs.getInt(1);
					if(banWord > 0) break;
				}
				if(banWord > 0)		continue;
				
				//대화차단자 처리
				sql = "select distinct mbr_nm from block_list where blk_nm='"+mName+"'";
				pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) { 	blk_nm = rs.getString(1);}
				if(mName.equalsIgnoreCase(blk_nm))	continue; 
				
				PrintWriter it_out = (PrintWriter)clientMap.get(mName);
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
//			Connection con=null;
			String name = "";
			try {
				con = ConnectionPool.getConnection("env " + noThread);
				//con.setAutoCommit(false);
				String sql = "select sysdate from dual";
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) 
					System.out.println(rs.getDate(1) + " : " + noThread + " : " + con);
				ConnectionPool.listCacheInfos();
				
				while(true) {
					name = in.readLine().trim();
					int[] check_f = check_member(name);
					if(check_f[1]==1)	
						out.println(name + "-> 블랙리스트 대상자입니다. 접속할 수 없습니다.");	
					else if(check_f[0]==1) 
						out.println(name + 	": 동일한 이름이 접속되어 있습니다\n"
										 +	"다른 이름으로 접속해 주십시요.");
					else break;
				}
				
				clientMap.put(name, out);
				sendAllMsg(name, "님이 입장하셨습니다.");	
				System.out.println("현재 접속자 수는 " + clientMap.size() + "명 입니다.");

				String s = "";
				while(in != null) {
					s = in.readLine();
					System.out.println(s);
					
					if(s.equals("/list")) 						showList(name);
					else if(s.equals("/lsrm"))					listRoom(name);
					else if(s.substring(0, 3).equals("/to"))	talkToOne(name, s);
					else if(s.substring(0, 4).equals("/blk"))	blockList(name, s);
					else if(s.substring(0, 4).equals("/ban"))	banWord(name, s);					
					else if(s.substring(0, 5).equals("/noti"))	notifyMsg(name, s);
					else if(s.substring(0, 6).equals("/mkorm"))	makeRoom(name, s, "0");
					else if(s.substring(0, 6).equals("/mksrm"))	makeRoom(name, s, "1");
					else										sendAllMsg(name, s);
					
//					else if(s.length()>4 && s.substring(0, 3).equals("/to"))	
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
				
				clientMap.remove(name);
				sendAllMsg(name, "님이 퇴장하셨습니다.");
				System.out.println("현재 접속자 수는 " + clientMap.size() + "명 입니다.");
				deleteMbr(name);
				try {
					in.close();
					out.close();	
					socket.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				System.out.println("sleep..." + noThread);
				try {
					Thread.sleep(1000);
				}catch(Exception e){}			
			}
		}
	}
	
	public static boolean isNumber(String input) {
		  try{Integer.parseInt(input);	  	return true;
		  }catch (NumberFormatException e){ return false;}
	}
	
	public void listRoom(String name) {
		String sql =  "select to_char(rum_no,'99')||'.'||room_nm||decode(open_cd, '0', '[공개방]','[비공개방]') "
					+ "from room_list order by rum_no";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			String str=null;
			while(rs.next()) { 	clientMap.get(name).print(rs.getString(1)+"\t");}
			clientMap.get(name).println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void makeRoom(String name, String s, String open_cd) {
		String[] str = s.split(" ");
		if(open_cd.equals("0") && (str.length != 3 || !(isNumber(str[2])))) {	
			clientMap.get(name).println("'/mkorm 방이름 정원' 형식으로 해주세요.");
			return;
		}
		if(open_cd.equals("1") && (str.length != 4 || !(isNumber(str[2])))) {	
			clientMap.get(name).println("'/mksrm 방이름 정원 비밀번호' 형식으로 해주세요.");
			return;
		}
		
		try {
			String sql = "select count(*) from room_list where room_nm = '"+str[1].trim()+"'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int cnt =0;
			while(rs.next()) { 	cnt = rs.getInt(1);}
			if(cnt == 0) {
				sql = "insert into room_list values(rum_no_seq.nextval, '"+str[1].trim()+"', to_number('"+str[2].trim()+"'),"
						+ "'"+open_cd+"', decode('"+open_cd+"','0', null, '"+str[str.length-1].trim()+"'), '"+name+"')";  
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				clientMap.get(name).println(str[1].trim() + "방이 만들어졌습니다.");
			}
			else	clientMap.get(name).println(str[1].trim() + "의 방이 이미 있습니다. 다른 방이름으로 다시 해주세요.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int[] check_member(String name) {
		int[] check = {0, 0};
		try {
			String sql = "select rownum, to_number(black_f)  from member_nm " 
						+"where mbr_nm = '"+name+"'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 	check[0] = rs.getInt(1); check[1] = rs.getInt(2);}
			if(check[0]==0) {
				sql = "insert into member_nm(mbr_nm) values('"+name+"')";  
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public void notifyMsg(String name, String msg) {
		
		String[] str = msg.split(" ");
		if(str.length == 1) {
			clientMap.get(name).println("'/noti 공지내용' 형식으로 해주세요.");
			return;
		}
		
		Iterator<String> it = clientMap.keySet().iterator();
		while(it.hasNext()) {
			try {
				PrintWriter it_out = (PrintWriter)clientMap.get(it.next());
				it_out.println("[공지사항] " + msg.substring(6).trim());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void showList(String name) {
			
		Set<String> k = clientMap.keySet();
		PrintWriter n_out = (PrintWriter)clientMap.get(name);
		n_out.println("현재 접속자입니다("+ clientMap.size()+"명).");
		//for(String s : k )	n_out.println(s) ;
		n_out.println(k);
	}
	
	@SuppressWarnings("resource")
	public void blockList(String name, String s) {
		String[] s1 = s.split(" ");
		if(s1.length != 2) {	
			clientMap.get(name).println("'/blk 상대방이름' 형식으로 해주세요.");
			return;
		}
		
		try {
			
			String sql = "select count(*) from block_list where mbr_nm='"+name+"' and blk_nm='"+s1[1].trim()+"'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int cnt = 0;
			while(rs.next()) 	cnt = rs.getInt(1);
			if(cnt==0) {
				sql = "insert into block_list values('"+name+"', '"+s1[1].trim()+"')";
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				clientMap.get(name).println(s1[1] + "와의 대화를 차단합니다.");
			}
			else {
				sql = "delete from block_list where mbr_nm='"+name+"' and blk_nm='"+s1[1].trim()+"')";
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				clientMap.get(name).println(s1[1] + "와의 차단을 해제합니다.");	
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMbr(String name) {
		try {
			String sql = "delete from member_nm where mbr_nm = '"+name+"' and black_f ='0'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			sql = "delete from block_list where mbr_nm = '"+name+"'";
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			sql = "delete from van_word where mbr_nm='"+name+"'";  
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void banWord(String name, String s){
		String[] str = s.split(" ");
		if(str.length == 1) {
			clientMap.get(name).println("'/ban 금칙어' 형식으로 해주세요.");
			return;
		}
		
		PreparedStatement pstmt;
		try {
			String sql = "select count(*) from van_word where mbr_nm != '"+name+"' and word='"+s.substring(5).trim()+"'"; 
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int cnt = 0;
			while(rs.next()) 	cnt = rs.getInt(1);
			if(cnt > 0) {
				clientMap.get(name).println(s.substring(5).trim() + "는 이미 서버금칙어입니다.");
			}
			else{
				sql = "select count(*) from van_word where mbr_nm = '"+name+"' and word='"+s.substring(5).trim()+"'"; 
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				int cnt1 = 0;
				while(rs.next()) 	cnt1 = rs.getInt(1);
				if(cnt1 == 0) {
					sql = "insert into van_word values('"+name+"','"+s.substring(5).trim()+"')";  
					pstmt = con.prepareStatement(sql);
					pstmt.executeUpdate();
					clientMap.get(name).println(s.substring(5).trim() + "는 금칙어에 포함됩니다.");
				}
				else {
					sql = "delete from van_word where mbr_nm='"+name+"' and word='"+s.substring(5).trim()+"'";  
					pstmt = con.prepareStatement(sql);
					pstmt.executeUpdate();
					clientMap.get(name).println(s.substring(5).trim() + "는 금칙어에서 해제합니다.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public void talkToOne(String name, String s) {
		
		String[] s1 = s.split(" ");
		if(s1.length == 1) {
			clientMap.get(name).println("'/to 상대방 [메시지]' 형식으로 해주세요.");
			return;
		}
		String blk_nm = null;
		
		try {
			String sql = "select distinct blk_nm from block_list where mbr_nm='"+s1[1].trim()+"'";
			PreparedStatement pstmt;
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 	blk_nm = rs.getString(1);}
			if(name.equalsIgnoreCase(blk_nm)) {
				PrintWriter out = (PrintWriter)clientMap.get(name);
				out.println("[" + s1[1].trim() +"] 님이 대화차단자로 설정하여 귓속말을 할 수 없습니다.");
			}
			else {
				PrintWriter out = (PrintWriter)clientMap.get(s1[1].trim());
//				String s2 = "(귓속말)" + nm + "=>" + s.substring(nm.length()+2+ s1[0].length()+s1[1].length()+2);
				out.println("[" + name +"] (귓속말)" + s.substring(4).trim());		
//				out.println(s2) ;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args){

		MultiServer ms = new MultiServer();
		ms.init();		
	}	
}
