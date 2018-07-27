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
		String mName="";
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
				int blk_cnt = 0;
				sql = "select count(*) from block_list where blk_nm='"+name+"' and mbr_nm='"+mName+"'";
				pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) { 	blk_cnt = rs.getInt(1);}
				if(blk_cnt == 1)	continue;
				
				//대화방참여자인 경우 
				int room_no1=0, room_no2=0;
				sql = "select room_no from member_nm where mbr_nm='"+name+"'";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) { 	room_no1 = rs.getInt(1);}
				sql = "select room_no from member_nm where mbr_nm='"+mName+"'";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) { 	room_no2 = rs.getInt(1);}
				if(room_no1 != room_no2)	continue;
				
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
					name = in.readLine().replaceAll(" ", "");
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
					
					if(s.equals("/list")) 										showList(name);
					else if(s.equals("/rlist"))									listRoom(name);
					else if(s.equals("/wlist"))									waitlist(name);
					else if(s.equals("/mlist"))									myRoomlist(name);
					else if(s.equals("/exit"))									exitRoom(name);
					else if(s.length()>2 && s.substring(0,3).equals("/to"))		talkToOne(name, s);
					else if(s.length()>2 && s.substring(0,3).equals("/cd"))		enterRoom(name, s);
					else if(s.length()>3 && s.substring(0,4).equals("/blk"))	blockList(name, s);
					else if(s.length()>3 && s.substring(0,4).equals("/ban"))	banWord(name, s);	
					else if(s.length()>3 && s.substring(0,4).equals("/out"))	outRoom(name, s);		
					else if(s.length()>4 && s.substring(0,5).equals("/noti"))	notifyMsg(name, s);	
					else if(s.length()>4 && s.substring(0,5).equals("/succ"))	successCap(name, s);	
					else if(s.length()>5 && s.substring(0,6).equals("/omake"))	makeRoom(name, s, "0");
					else if(s.length()>5 && s.substring(0,6).equals("/smake"))	makeRoom(name, s, "1");
					else														sendAllMsg(name, s);
					
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
	
	public void successCap(String name, String s) {
		String[] str = s.split(" ");
		if(str.length != 2) {
			clientMap.get(name).println("'/succ 승계자명' 형식으로 해주세요.");
			return;
		}
		try {
			String sql ="select nvl(cap_nm, ' '), a.room_nm, b.room_no, c.room_no " 
					+	"from room_list a, member_nm b, member_nm c " 
					+ 	"where a.room_no(+)=b.room_no "
					+	"and b.mbr_nm='"+str[1].trim()+"' and c.mbr_nm='"+name+"'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int myNo=0, sNo=0;
			String cap_nm = "", room_nm="";
			while(rs.next()) { 	cap_nm = rs.getString(1); room_nm = rs.getString(2); 
								sNo = rs.getInt(3); myNo = rs.getInt(3);}
			
			if(!name.equals(cap_nm)) {
				clientMap.get(name).println("방장만이 승계시킬 수 있습니다.");	return;
			}else if(myNo==0 || sNo==0) {
				clientMap.get(name).println("현재 대기실에 있습니다. 승계시킬 수 없습니다.");	return;
			}else if(myNo != sNo) {
				clientMap.get(name).println("다른 대화방에 있습니다. 승계시킬 수 없습니다.");	return;
			}	

			sql = "update room_list set cap_nm = '"+str[1].trim()+"' "
				+ "where room_no = (select room_no from room_list where cap_nm = '"+name+"')"; 
			pstmt = con.prepareStatement(sql);
			int upCnt = pstmt.executeUpdate();
			if(upCnt > 0) 	sendAllMsg(name, str[1].trim()+"님이 방장으로 승계시켰습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void outRoom(String name, String s) {
		String[] str = s.split(" ");
		if(str.length != 2) {
			clientMap.get(name).println("'/out 강퇴자명' 형식으로 해주세요.");
			return;
		}
		try {
			String sql ="select nvl(cap_nm, ' '), a.room_nm, b.room_no, c.room_no " 
					+	"from room_list a, member_nm b, member_nm c " 
					+ 	"where a.room_no(+)=b.room_no "
					+	"and b.mbr_nm='"+str[1].trim()+"' and c.mbr_nm='"+name+"'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int myNo=0, outNo=0;
			String cap_nm = "", room_nm="";
			while(rs.next()) { 	cap_nm = rs.getString(1); room_nm = rs.getString(2); 
								outNo = rs.getInt(3); myNo = rs.getInt(3);}
			
			if(!name.equals(cap_nm)) {
				clientMap.get(name).println("방장만이 강퇴시킬 수 있습니다.");	return;
			}else if(myNo==0 || outNo==0) {
				clientMap.get(name).println("현재 대기실에 있습니다. 강퇴시킬 수 없습니다.");	return;
			}else if(myNo != outNo) {
				clientMap.get(name).println("다른 대화방에 있습니다. 강퇴시킬 수 없습니다.");	return;
			}	

			sql = "update member_nm set room_no = 0 where mbr_nm = '"+str[1].trim()+"'"; 
			pstmt = con.prepareStatement(sql);
			int upCnt = pstmt.executeUpdate();
			if(upCnt > 0) {	
				sendAllMsg(name, str[1].trim()+"님을 강퇴시켰습니다.");
				clientMap.get(str[1].trim()).println(room_nm.toString() + " 방에서 강퇴되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void myRoomlist(String name) {
		String sql ="select mbr_nm, cap_nm, room_nm from member_nm a, room_list b where a.room_no=b.room_no and black_f='0' "
				+	"and a.room_no = (select room_no from member_nm where mbr_nm ='"+name+"')";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			String str = "", cap_nm="", room_nm="";
			int cnt=0;
			while(rs.next()) { 	
					str = str + rs.getString(1) + ", "; 
					cap_nm=rs.getString(2); room_nm=rs.getString(3); cnt++;}
			clientMap.get(name).println("현재 " +room_nm.toString()+" 방 사용자입니다("+cnt+"명)-방장:"+cap_nm.toString());
			clientMap.get(name).println("["+str.substring(0, str.length()-2).toString() + "]");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void waitlist(String name) {
		String sql =  "select mbr_nm from member_nm where room_no=0 and  black_f='0'";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			String str = "";
			int cnt=0;
			while(rs.next()) { 	str = str + rs.getString(1) + ", "; cnt++;}
			clientMap.get(name).println("현재 대기자입니다("+cnt+"명).");
			clientMap.get(name).println("["+str.substring(0, str.length()-2).toString() + "]" +"\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void exitRoom(String name) {
		try {
			String sql = "select a.room_no, nvl(b.room_nm, '대기방') " 
					+	 "from member_nm a, room_list b " 
					+    "where a.room_no=b.room_no(+) and mbr_nm='"+name+"'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int	room_no=0;
			String room_nm="";
			while(rs.next()) { 	room_no = rs.getInt(1); room_nm = rs.getString(2);}
			if(room_no == 0)	clientMap.get(name).println("현재 대기방에 있습니다.");
			else{
				sendAllMsg(name, "님이 "+ room_nm +" 방에서 퇴장하셨습니다.");
				sql = "update member_nm set room_no = 0 where mbr_nm = '"+name+"'"; 
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void enterRoom(String name, String s) {
		String[] str = s.split(" ");
		if(str.length == 1) {
			clientMap.get(name).println("'/cd 숫자or방이름 비밀번호' 형식으로 해주세요.");
			return;
		}
		
		try {
			String sql = "select open_cd, nvl(passwd, ' '), room_nm, b.room_no from room_list a, member_nm b "
					   + "where (to_char(a.room_no)='"+str[1].trim()+"' or a.room_nm='"+str[1].trim()+"') and b.mbr_nm='"+name+"'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int	room_no=0;
			String open_cd="", passwd="", room_nm="";
			while(rs.next()) { 	
				open_cd = rs.getString(1); passwd = rs.getString(2); 
				room_nm=rs.getString(3); room_no=rs.getInt(4);}
			if(room_no > 0) {
					clientMap.get(name).println("대기방에서만 다른 방으로 입장이 가능합니다.");
					return;}
			if(open_cd.equals("1") && !(passwd.equals(str[2].trim()))) {
					clientMap.get(name).println("비공개방 비밀번호와 일치하지 않습니다.");
					return;}
						
			sql = "update member_nm "
					+ "set room_no = (select room_no from room_list where to_char(room_no)='"+str[1].trim()+"' "
					+ "or room_nm = '"+str[1].trim()+"') where mbr_nm = '"+name+"'"; 
			pstmt = con.prepareStatement(sql);
			int uCnt = pstmt.executeUpdate();
			if(uCnt ==1) {
				sendAllMsg(name, "님이 "+ room_nm +" 방에 입장하셨습니다.");
			}
					
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static boolean isNumber(String input) {
		  try{Integer.parseInt(input);	  	return true;
		  }catch (NumberFormatException e){ return false;}
	}
	
	public void listRoom(String name) {
		String sql =  "select to_char(room_no,'99')||'.'||room_nm||decode(open_cd, '0', '[공개방]','[비공개방]')" 
					+ "||'('||(select count(*) from member_nm b where b.room_no=a.room_no and black_f='0')||'/'||FIX_NUM||')'"
					+ "||decode(room_no, (select room_no from member_nm where mbr_nm='"+name+"'),'-입장','')"
					+ "from room_list a  where use_cd='1' order by room_no";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 	clientMap.get(name).print(rs.getString(1)+"\t");}
			clientMap.get(name).println();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void makeRoom(String name, String s, String open_cd) {
		String[] str = s.split(" ");
		if(str.length > 1 && (isNumber(str[1]))) {	
			clientMap.get(name).println("방이름에는 문자가 포함되어야 합니다.");
			return;
		}
		if(open_cd.equals("0") && (str.length != 3 || !(isNumber(str[2])))) {	
			clientMap.get(name).println("'/omake 방이름 정원' 형식으로 해주세요.");
			return;
		}
		if(open_cd.equals("1") && (str.length != 4 || !(isNumber(str[2])))) {	
			clientMap.get(name).println("'/smake 방이름 정원 비밀번호' 형식으로 해주세요.");
			return;
		}
		
		try {
			String sql = "select count(*) from room_list where use_cd ='1' and room_nm = '"+str[1].trim()+"'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int cnt =0;
			while(rs.next()) { 	cnt = rs.getInt(1);}
			if(cnt == 0) {
				sql = "update room_list "
						+ "set use_cd = '1', room_nm = '"+str[1].trim()+"', "
						+ "fix_num = to_number('"+str[2].trim()+"'), "
						+ "open_cd = '"+open_cd+"', "
						+ "passwd= decode('"+open_cd+"','0', null, '"+str[str.length-1].trim()+"'), "
						+ "cap_nm = '"+name+"' "
						+ "where room_no = (select min(room_no) from room_list where use_cd='0')";  
				pstmt = con.prepareStatement(sql);
				int uCnt = pstmt.executeUpdate();
				if(uCnt ==1) clientMap.get(name).println(str[1].trim() + "방이 만들어졌습니다.");
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
