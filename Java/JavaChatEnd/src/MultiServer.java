import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.*;

public class MultiServer {
	ServerSocket serverSochet = null;
	Socket socket = null;
	Map<String, PrintWriter> clientMap;
	Connection con = null;
	
	public MultiServer() {
		clientMap = new HashMap<String, PrintWriter>();
		Collections.synchronizedMap(clientMap);
	}
	
	public void init() {
		try {
			serverSochet = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");
			con = ConnectionPool.getConnection();
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
				con.close();
				serverSochet.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
		
	public void sendAllMsg(String name, String msg) {
			
		//서버 금칙어 처리
		String[] str = msg.split(" ");		
		int banWord = 0;
		
		try {
			String sql = "select count(*) from van_word where mbr_nm= '$system$' and word = ?";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			for(int i=0; i<str.length; i++) {
				pstmt.setString(1, str[i]);
				ResultSet rs = pstmt.executeQuery();
				rs.next(); 	
				banWord = rs.getInt(1);	
				rs.close();
				if(banWord > 0) {
					clientMap.get(name).println(URLEncoder.encode(str[i] + "는 금칙어입니다.", "UTF-8"));
					pstmt.close(); //con.close();
					return;
				}
			} 
			pstmt.close(); //con.close();
		}catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}			
					
		Iterator<String> it = clientMap.keySet().iterator();
		String mName="";
		while(it.hasNext()) {
			try {
				mName = it.next();
				//개인금칙어 처리
				String sql = "select count(*) from van_word where mbr_nm='"+mName+"' and word =?";
				//Connection con = ConnectionPool.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				for(int i=0; i<str.length; i++) {
					banWord = 0;				
					pstmt.setString(1, str[i]);
					ResultSet rs = pstmt.executeQuery();
					rs.next();
					banWord = rs.getInt(1);
					rs.close();
					if(banWord > 0) break;
				}	
				pstmt.close(); //con.close();
				if(banWord > 0)		continue;
				
				//대화차단자 처리
				int blk_cnt = 0;
				sql = "select count(*) from block_list where blk_nm='"+name+"' and mbr_nm='"+mName+"'";
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				blk_cnt = rs.getInt(1);
				rs.close(); pstmt.close(); //con.close();
				if(blk_cnt == 1)	continue;
				
				//대화방참여자인 경우 //초대중인 사람 //게임참여자
				int room_no1=0, room_no2=0;
				String call_f="", game_f="";
				sql = "select a.room_no, b.room_no, b.call_f, b.game_f "
					+ "from member_nm a, member_nm b where a.mbr_nm='"+name+"' and b.mbr_nm='"+mName+"'";
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()==true) { 	room_no1 = rs.getInt(1); room_no2 = rs.getInt(2); 
										call_f = rs.getString(3); game_f = rs.getString(4);
										rs.close(); pstmt.close();} //con.close();}
				else {					rs.close(); pstmt.close(); //con.close();
										continue;}
				
				if(room_no1 != room_no2)	continue;
				if(call_f.equals("1"))		continue;
				if(!game_f.equals("0"))		continue;
				
				PrintWriter it_out = (PrintWriter)clientMap.get(mName);
				it_out.println(URLEncoder.encode("["+name+"] "+ msg, "UTF-8"));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	class MultiServerT extends Thread{
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
				
		public MultiServerT(Socket socket) {
			this.socket = socket;
			
			try {
				out = new PrintWriter(this.socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(
						this.socket.getInputStream(), "UTF-8"));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
//			//Connection con=null;
			String name = "";
			List<String> ls = Arrays.asList("/list","/ls", "/rls", "/lsd","/ls0","/lsw","/ls.","/my",
					"/exit", "/ex" ,"/to", "/cd","/blk", "/ban","/out","/cap","/del","/call","/make",
					"/noti", "/gam1", "/gam2");
			ls = new ArrayList<>(ls);
			
			try {
				//con = ConnectionPool.getConnection();
				String sql = "select sysdate from dual";
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				rs.next(); System.out.println(rs.getDate(1) + " : " + con);
				rs.close(); pstmt.close(); //con.close();
				ConnectionPool.listCacheInfos();
				
				while(true) {
					name = in.readLine().replaceAll(" ", "");
					name = URLDecoder.decode(name, "UTF-8");
					int[] check_f = check_member(name);
					if(check_f[1]==1)	
						out.println(URLEncoder.encode(name + "-> 블랙리스트 대상자입니다. 접속할 수 없습니다.", "UTF-8"));	
					else if(check_f[0]==1) 
						out.println(URLEncoder.encode(name + 	": 동일한 이름이 접속되어 있습니다\n"
														   +	">> 다른 이름으로 접속해 주십시요.", "UTF-8"));
					else break;
				}
				
				clientMap.put(name, out);
				sendAllMsg(name, "님이 입장하셨습니다.");	
				System.out.println("현재 접속자 수는 " + clientMap.size() + "명 입니다.");

				String s = "";
				while(in != null) {
					s = in.readLine();
					s = URLDecoder.decode(s, "UTF-8");
					String[] s1 = s.split(" ");	
					if(s.substring(0,1).equals("/") && !ls.contains(s1[0]))
						{ out.println(URLEncoder.encode("'"+s1[0]+"'" + "는 없는 명령어입니다.", "UTF-8"));	
						  continue;}
					String[] calls = {"0", " ", "0"};
//					if(s.equalsIgnoreCase("y") || s.equalsIgnoreCase("n"))		calls = checkCall(name);
					calls = checkCall(name);
					
					if(calls[0].equals("1")) 	s = "/$$cal " + s + " " + calls[1].toString();
					else if(calls[2].equals("1")) s = "/$$g1$ " + s + " " + calls[1].toString() + " " + calls[2];
					else if(calls[2].equals("2")) s = "/$$g2$ " + s + " " + calls[1].toString() + " " + calls[2];
					else if(calls[2].equals("3")) s = "/$$g3$ " + s + " " + calls[1].toString() + " " + calls[2];
					System.out.println(s);
					
					if(s.equals("/list") || s.equals("/ls")) 					showList(name);
					else if(s.equals("/rls") || s.equals("/lsd"))				roomList(name);
					else if(s.equals("/ls0") || s.equals("/lsw"))				waitlist(name);
					else if(s.equals("/ls.") || s.equals("/my"))				myRoomlist(name);
					else if(s.equals("/exit") || s.equals("/ex"))				exitRoom(name, "1");
					else if(s.length()>2 && s.substring(0,3).equals("/to"))		talkToOne(name, s);
					else if(s.length()>2 && s.substring(0,3).equals("/cd"))		changeRoom(name, s);
					else if(s.length()>3 && s.substring(0,4).equals("/blk"))	blockList(name, s);
					else if(s.length()>3 && s.substring(0,4).equals("/ban"))	banWord(name, s);	
					else if(s.length()>3 && s.substring(0,4).equals("/out"))	outRoom(name, s, "out");	
					else if(s.length()>3 && s.substring(0,4).equals("/cap"))	changeCap(name, s);
					else if(s.length()>3 && s.substring(0,4).equals("/del"))	deleteRoom(name);
					else if(s.length()>4 && s.substring(0,5).equals("/call"))	callName(name, s);
					else if(s.length()>4 && s.substring(0,5).equals("/noti"))	notifyMsg(name, s);					
					else if(s.length()>4 && s.substring(0,5).equals("/make"))	makeRoom(name, s);
					else if(s.length()>4 && s.substring(0,5).equals("/gam1"))	gameCheck(name, s, "1");
					else if(s.length()>4 && s.substring(0,5).equals("/gam2"))	gameCheck(name, s, "2");
					else if(s.length()>5 && s.substring(0,6).equals("/$$cal"))	callYn(name, s);
					else if(s.length()>5 && s.substring(0,6).equals("/$$g1$"))	baseBall(name, s);
					else if(s.length()>5 && s.substring(0,6).equals("/$$g2$"))	ticTacToe(name, s);
					else														sendAllMsg(name, s);
				}
			} catch(SQLException  e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {	
				exitRoom(name, "0");
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
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void ticTacToe(String name, String s) {//$$g2$ + 1:ox + 2:상대방 + 3:game_f
		String[][] brd= 
			{{" ","1"," ","|"," ","2"," ","|"," ","3"," "},
			 {"-","-","-","|","-","-","-","|","-","-","-"},
			 {" ","4"," ","|"," ","5"," ","|"," ","6"," "},
			 {"-","-","-","|","-","-","-","|","-","-","-"},
			 {" ","7"," ","|"," ","8"," ","|"," ","9"," "}};
		int[] x = {0,0,0,0,2,2,2,4,4,4};
		int[] y = {0,1,5,9,1,5,9,1,5,9};
			
		String[] str = s.split(" ");	
		try {	
			if(str[1].equals("/ex") || str[1].equals("/exit")) {
				clientMap.get(str[2]).println(URLEncoder.encode(name + " 님이 게임을 중단하셨습니다.", "UTF-8"));
				String sql = "update member_nm set game_f='0', check_f='0', game_val=0, game_cnt=0, "
						+ "ox_val='' where mbr_nm = ?"; 
				//Connection	con = ConnectionPool.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);   pstmt.executeUpdate();
				pstmt.setString(1, str[2]); pstmt.executeUpdate();
				pstmt.close(); //con.close();
				return;
			}
			String sql = "select check_f from member_nm where mbr_nm='"+name+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			String chk_f="";
			rs.next(); 	chk_f = rs.getString(1); 
			rs.close(); pstmt.close(); //con.close();
			
			if(str[1].equalsIgnoreCase("n") && chk_f.equals("0")) {
				clientMap.get(str[2]).println(URLEncoder.encode(name + " 님이 초대를 거절하셨습니다.", "UTF-8"));
				sql = "update member_nm set game_f='0' where mbr_nm ='"+name+"'"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
				return;
			}
			else if(str[1].equalsIgnoreCase("y") && chk_f.equals("0")) {
				clientMap.get(str[2]).println(URLEncoder.encode("TicTacToe 게임 시작.", "UTF-8"));
				clientMap.get(str[2]).println(URLEncoder.encode("잠시만 기다려주세요.", "UTF-8"));
				sql = "update member_nm set check_f='1' where mbr_nm ='"+name+"'"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
				prnBoard(name, brd, "X", 0);
				return;
			}
			
			if(chk_f.equals("0") && !isNumber(str[1])) {
				clientMap.get(name).println(URLEncoder.encode("숫자만 가능합니다.", "UTF-8"));
				return;}
			if(chk_f.equals("0") && (str[1].length() > 1 || Integer.parseInt(str[1]) ==0)) {
				clientMap.get(name).println(URLEncoder.encode("한자리 숫자[1~9]만 가능합니다.", "UTF-8"));
				return;}
			
			sql = "select a.ox_val, a.game_cnt, a.score, b.ox_val, b.game_cnt, b.score "
				+ "from member_nm a, member_nm b where a.mbr_nm ='"+name+"' and b.mbr_nm ='"+str[2]+"'";
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int m_cnt=0, u_cnt=0, m_sco=0, u_sco=0;
			String m_ox="", u_ox="";
			rs.next();  	
			m_ox = rs.getString(1); m_cnt = rs.getInt(2); m_sco = rs.getInt(3);
			u_ox = rs.getString(4);	u_cnt = rs.getInt(5); u_sco = rs.getInt(6);
			rs.close(); pstmt.close();
			if((u_cnt - m_cnt) > 1 || (u_cnt + m_cnt)==9) return;
			if(m_ox.length() > 1)
				for(int i=1; i<m_ox.length(); i++)
					brd[x[Integer.parseInt(m_ox.substring(i,i+1))]][y[Integer.parseInt(m_ox.substring(i,i+1))]] = m_ox.substring(0, 1);
			if(u_ox.length() > 1)
				for(int i=1; i<u_ox.length(); i++)
					brd[x[Integer.parseInt(u_ox.substring(i,i+1))]][y[Integer.parseInt(u_ox.substring(i,i+1))]] = u_ox.substring(0, 1);				
			
			if(brd[x[Integer.parseInt(str[1])]][y[Integer.parseInt(str[1])]].equals("X") ||
					brd[x[Integer.parseInt(str[1])]][y[Integer.parseInt(str[1])]].equals("O")) return;			
			brd[x[Integer.parseInt(str[1])]][y[Integer.parseInt(str[1])]] = m_ox.substring(0,1);
			
			Boolean Wins = okBoard(brd, m_ox.substring(0,1));
			if(Wins == true || (m_cnt+u_cnt+1) == 9) {
			
				prnBoard(name, brd, m_ox.substring(0,1), 1);
				prnBoard(str[2], brd, u_ox.substring(0,1), 1);
				if(Wins == true) {
					m_sco += 100; u_sco -= 100;
					clientMap.get(name).println(URLEncoder.encode("<TicTacToe> Bingo!! Yon Winner. 점수: " + m_sco, "UTF-8"));
					clientMap.get(str[2]).println(URLEncoder.encode("<TicTacToe> Bingo!! You Loser. 점수: " + u_sco, "UTF-8"));
				}else {
					clientMap.get(name).println(URLEncoder.encode("<TicTacToe> Game is up.", "UTF-8"));
					clientMap.get(str[2]).println(URLEncoder.encode("<TicTacToe> Game is up.", "UTF-8"));
				}
				sql = "update member_nm set game_val=0, check_f='0', game_cnt=0, ox_val=?, "
					+ "score = score + ?, call_nm=? where mbr_nm = ?"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "O"); pstmt.setInt(2, 100); 
				pstmt.setString(3, str[2]); pstmt.setString(4, name);   pstmt.executeUpdate();
				pstmt.setString(1, "X"); pstmt.setInt(2,-100); 
				pstmt.setString(3, name);pstmt.setString(4, str[2]); pstmt.executeUpdate();
				pstmt.close(); //con.close();
				clientMap.get(str[2]).println(URLEncoder.encode("다시 하시겠습니까? (y/n) ", "UTF-8"));
			}else {
				prnBoard(str[2], brd, u_ox.substring(0,1), 0);
				clearScreen(name);
				clientMap.get(name).println(URLEncoder.encode("상대방 차례입니다. 잠시만 기다려주세요.", "UTF-8"));
				sql = "update member_nm set game_cnt=game_cnt+1, ox_val = ox_val||'"+str[1]+"' "
						+ "where mbr_nm ='"+name+"'"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
			}
		} catch (UnsupportedEncodingException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean okBoard(String[][] brd, String s) {	
		if(brd[0][1].equals(s) && brd[0][5].equals(s) && brd[0][9].equals(s)) return true;
		if(brd[2][1].equals(s) && brd[2][5].equals(s) && brd[2][9].equals(s)) return true;
		if(brd[4][1].equals(s) && brd[4][5].equals(s) && brd[4][9].equals(s)) return true;
		if(brd[0][1].equals(s) && brd[2][1].equals(s) && brd[4][1].equals(s)) return true;
		if(brd[0][5].equals(s) && brd[2][5].equals(s) && brd[4][5].equals(s)) return true;
		if(brd[0][9].equals(s) && brd[2][9].equals(s) && brd[4][9].equals(s)) return true;
		if(brd[0][1].equals(s) && brd[2][5].equals(s) && brd[4][9].equals(s)) return true;
		if(brd[0][9].equals(s) && brd[2][5].equals(s) && brd[4][1].equals(s)) return true;
		
		return false;
	}
	
	public void prnBoard(String name, String[][] brd, String ox, int check) {		
		
		String msg = "Please enter the number of squarer where you want to place your ";
		clearScreen(name);
		try {
			for(int i=0; i<brd.length; i++) {
				for(int j=0; j<brd[i].length; j++) 
					clientMap.get(name).printf("%s", URLEncoder.encode(brd[i][j], "UTF-8"));
				clientMap.get(name).println(URLEncoder.encode("", "UTF-8"));
			}
			clientMap.get(name).println(URLEncoder.encode("", "UTF-8"));
			if(check==0) clientMap.get(name).println(URLEncoder.encode(msg + ox, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
		}
	}
	
	public void clearScreen(String name)
	{
		for(int i=0; i<50; i++) { 
			try {
				clientMap.get(name).println(URLEncoder.encode("", "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void baseBall(String name, String s) { ///$$g1$ + 1:숫자 + 2:상대방 + 3:game_f
		String[] str = s.split(" ");		
		
		try {
			if(str[1].equals("/ex") || str[1].equals("/exit")) {
				clientMap.get(str[2]).println(URLEncoder.encode(name + " 님이 게임을 중단하셨습니다.", "UTF-8"));
				String sql = "update member_nm set game_f='0', check_f='0', game_val=0, game_cnt=0 where mbr_nm = ?"; 
				//Connection con = ConnectionPool.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);   pstmt.executeUpdate();
				pstmt.setString(1, str[2]); pstmt.executeUpdate();
				pstmt.close(); //con.close();
				return;
			}
			String sql = "select check_f from member_nm where mbr_nm='"+name+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
				
			String chk_f="";
			rs.next();
			chk_f = rs.getString(1);
			rs.close(); pstmt.close(); //con.close();
			
			if(str[1].equalsIgnoreCase("n") && chk_f.equals("0")) {
				clientMap.get(str[2]).println(URLEncoder.encode(name + " 님이 초대를 거절하셨습니다.", "UTF-8"));
				sql = "update member_nm set game_f='0' where mbr_nm ='"+name+"'"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
				return;
			}
			else if(str[1].equalsIgnoreCase("y") && chk_f.equals("0")) {
				clearScreen(name); clearScreen(str[2]);
				clientMap.get(name).println(URLEncoder.encode("숫자로 하는 야구게임 시작.", "UTF-8"));
				clientMap.get(str[2]).println(URLEncoder.encode("숫자로 하는 야구게임 시작.", "UTF-8"));
				clientMap.get(name).println(URLEncoder.encode("서로 다른 세자리 자기 숫자를 입력하세요.", "UTF-8"));
				clientMap.get(str[2]).println(URLEncoder.encode("서로 다른 세자리 자기 숫자를 입력하세요.", "UTF-8"));
				sql = "update member_nm set check_f='1' where mbr_nm ='"+name+"'"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
				return;
			}
			clearScreen(name);
			if(!isNumber(str[1])) {
				clientMap.get(name).println(URLEncoder.encode("숫자만 가능합니다.", "UTF-8"));
				return;}
			if(str[1].length() !=3 ) {
				clientMap.get(name).println(URLEncoder.encode("세자리 숫자만 가능합니다.", "UTF-8"));
				return;}
			
			int c = Integer.parseInt(str[1]);
			int c1 = c/100,	c2 = (c%100)/10, c3 = c%10;
			
			if(c1==c2 || c1==c3 || c2==c3) {
				clientMap.get(name).println(URLEncoder.encode("서로 다른 숫자만 가능합니다.", "UTF-8"));
				return;}
			if(c1*c2*c3==0) {
				clientMap.get(name).println(URLEncoder.encode("1에서 9까지 숫자만 가능합니다.", "UTF-8"));
				return;	}
			
			sql = "update member_nm "
				+ "set game_val=decode(game_cnt, 0, to_number('"+str[1]+"'), game_val),"
				+ "game_cnt=decode((select game_cnt from member_nm where mbr_nm = '"+str[2]+"'), 0, 1, game_cnt+1) "
				+ "where mbr_nm='"+name+"'"; 
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close(); ////con.close();
		
			sql = "select b.game_val, b.game_cnt, a.game_cnt, b.score, a.score from member_nm a, member_nm b "
				+ "where a.mbr_nm = '"+name+"' and b.mbr_nm='"+str[2]+"'";
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
		
			int u=0, strk=0, ball=0, m_cnt=0, u_cnt=0, m_score=0, u_score=0;
			rs.next();
			u = rs.getInt(1); u_cnt = rs.getInt(2); m_cnt = rs.getInt(3);
			u_score = rs.getInt(4); m_score = rs.getInt(5); 
			rs.close(); pstmt.close(); //con.close();
			if(u==0 || (u_cnt*m_cnt) < 2) { 
				clientMap.get(name).println(URLEncoder.encode("상대방이 입력하기 전 입니다. 다시 입력하세요.", "UTF-8"));
				return;
			}		
			
			int u1 = u/100,	u2 = (u%100)/10, u3 = u%10;
			
			if(u1==c1)					strk++;
			if(u2==c2)					strk++;
			if(u3==c3)					strk++;
			
			if(u2==c1 || u2 ==c3)		ball++;
			if(u1==c2 || u1 ==c3)		ball++;
			if(u3==c2 || u3 ==c1)		ball++;
			
			clientMap.get(name).println(URLEncoder.encode("<Baseball> " + c1 + ":" + c2 + ":" +c3, "UTF-8"));
			clientMap.get(name).println(URLEncoder.encode("<Baseball> " + strk + " strike " + ball + " Ball", "UTF-8"));
		
			if(strk==3) {
				clearScreen(str[2]);
				m_score += 100; u_score -= 100;
				clientMap.get(name).println(URLEncoder.encode("<Baseball> Winner! 점수: " + m_score, "UTF-8"));
				clientMap.get(str[2]).println(URLEncoder.encode("<Baseball> " + c1 + ":" + c2 + ":" +c3, "UTF-8"));
				clientMap.get(str[2]).println(URLEncoder.encode("<Baseball> Loser! 점수: "+ u_score, "UTF-8"));
			
				sql = "update member_nm set game_val=0, check_f='0', game_cnt=0, score = score + ?, "
					+ "call_nm=? where mbr_nm = ?"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, 100); pstmt.setString(2, str[2]); pstmt.setString(3, name); pstmt.executeUpdate();
				pstmt.setInt(1,-100); pstmt.setString(2, name); pstmt.setString(3, str[2]); pstmt.executeUpdate();
				pstmt.close(); //con.close();
				clientMap.get(str[2]).println(URLEncoder.encode("다시 하시겠습니까? (y/n) ", "UTF-8"));
			}else clientMap.get(name).println(URLEncoder.encode("서로 다른 세자리 숫자를 입력하세요", "UTF-8"));
		} catch (UnsupportedEncodingException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void gameCheck(String name, String s, String game) {
		String[] str = s.split(" ");
		
		try {
			if(name.equals(str[1])){ clientMap.get(name).println(URLEncoder.encode("이름을 확인하세요.", "UTF-8")); return;}
			if(str.length != 2) {
				clientMap.get(name).println(URLEncoder.encode("Usage: /game" +game+" 상대자명", "UTF-8")); 
				return;}	
			String sql ="select a.room_no, b.room_no, b.game_f from member_nm a, member_nm b "
					+	"where a.mbr_nm = '"+name+"' and b.black_f='0' and b.mbr_nm = '"+str[1]+"'";	
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int myNo=0, toNo=0;
			String game_f = "";
			if(rs.next()) { 	myNo = rs.getInt(1); toNo = rs.getInt(2); game_f = rs.getString(3);
								rs.close(); pstmt.close();} //con.close();}
			else {	clientMap.get(name).println(URLEncoder.encode("상대방 이름을 확인하세요.", "UTF-8"));
					return;}
			
			if(myNo != toNo) {
				clientMap.get(name).println(URLEncoder.encode("게임은 대기실이나 같은 대화방에서만 가능합니다.", "UTF-8"));
				return;
			}
			if(!(game_f.equals("0"))) {
				clientMap.get(name).println(URLEncoder.encode(str[1] + " 님은 게임 중 입니다.", "UTF-8"));
				return;
			}
			sql = "update member_nm set game_f = ?, call_nm = ?, ox_val =? where mbr_nm = ?"; 
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, game); pstmt.setString(2, str[1]); 
			pstmt.setString(3, "o"); pstmt.setString(4, name); 
			pstmt.executeUpdate();
			pstmt.setString(1, game); pstmt.setString(2, name); 
			pstmt.setString(3, "x"); pstmt.setString(4, str[1]); 
			pstmt.executeUpdate();
			pstmt.close(); //con.close();
			if(game.equals("1")) 
				clientMap.get(str[1]).println(URLEncoder.encode(name + " 님이 " + " 숫자로 하는 야구게임에 초대합니다. 수락하시겠습니까? (y/n)", "UTF-8"));
			else
				clientMap.get(str[1]).println(URLEncoder.encode(name + " 님이 " + " TicTacToe 게임에 초대합니다. 수락하시겠습니까? (y/n)", "UTF-8"));

		} catch (UnsupportedEncodingException | SQLException e) {
				e.printStackTrace();
		}
	}
	
	public String[] checkCall(String name) {
		String[] calls = {"0", " ", "0"};
		try {
			String sql ="select call_f, nvl(call_nm, ' '), game_f from member_nm where mbr_nm = '"+name+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();			
			rs.next(); 
			calls[0] = rs.getString(1); calls[1] = rs.getString(2); calls[2] = rs.getString(3);
			rs.close(); pstmt.close(); //con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return calls;
	}
	
	public void deleteRoom(String name) {
		try {	
			String sql ="select cap_nm, room_no, room_no||'.'||room_nm, "
					+ "(select count(*) from member_nm b where a.room_no=b.room_no) " 
					+ "from room_list a where a.cap_nm ='"+name+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int room_no=0, cnt = 0;
			String cap_nm = "", room_nm="";
			rs.next(); 	
			cap_nm = rs.getString(1); room_no = rs.getInt(2); 
			room_nm = rs.getString(3); cnt = rs.getInt(4);
			rs.close(); pstmt.close(); //con.close();
			if(!name.equals(cap_nm)) {
				clientMap.get(name).println(URLEncoder.encode("방장만이 대화방을 폭파시킬 수 있습니다.", "UTF-8"));	return;
			}
			
			String[] mbr_nm = new String[cnt-1];
			sql = "select mbr_nm from member_nm where room_no = '"+room_no+"' and mbr_nm != '"+name+"'";
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int i=0;
			while(rs.next()) { mbr_nm[i] = rs.getString(1); i++;}
			rs.close(); pstmt.close(); //con.close();
			
			for(i=0; i<cnt-1; i++)	outRoom(name, "/out " + mbr_nm[i], "del");
			
			sql = "update member_nm set room_no = 0 where mbr_nm = '"+name+"'";
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close(); ////con.close();
			
			sql = "delete from room_list where room_no = '"+room_no+"'";
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			int upCnt = pstmt.executeUpdate();
			pstmt.close(); //con.close();
			if(upCnt > 0) 	sendAllMsg(name, room_nm + " 방을 폭파시켰습니다.");			
		} catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	
	public void callName(String name, String s) {
		String[] str = s.split(" ");
		try {
			if(str.length != 2) {
				clientMap.get(name).println(URLEncoder.encode("Usage: /call 초대자명", "UTF-8"));
				return;	}	
			if(name.equals(str[1])){ clientMap.get(name).println(URLEncoder.encode("이름을 확인하세요.", "UTF-8")); return;}
			
			String sql ="select a.room_nm, b.room_no, c.room_no, c.call_f " 
					+	"from room_list a, member_nm b, member_nm c " 
					+ 	"where a.room_no(+)=b.room_no "
					+	"and c.black_f='0' and c.mbr_nm='"+str[1]+"' and b.mbr_nm='"+name+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int myNo=0, cNo=0;
			String room_nm="", call_f="";
			if(rs.next()) {	room_nm = rs.getString(1); myNo = rs.getInt(2); 
							cNo = rs.getInt(3); call_f = rs.getString(4);
							rs.close(); pstmt.close();} //con.close();}
			else {	clientMap.get(name).println(URLEncoder.encode("상대방 이름을 확인하세요.", "UTF-8"));
					return;}
						
			if(myNo==0) {
				clientMap.get(name).println(URLEncoder.encode("현재 대기실입니다. 대화방에서만 초대할 수 있습니다.", "UTF-8"));	return;}
			if(cNo > 0) {
				clientMap.get(name).println(URLEncoder.encode(str[1]+" 님이 다른 대화방에 있습니다. 초대할 수 없습니다.", "UTF-8"));	return;}
			if(call_f.equals("1")) {
				clientMap.get(name).println(URLEncoder.encode(str[1]+" 님이 다른 사람에게 초대받고 있습니다. 초대할 수 없습니다.", "UTF-8"));	return;}	
			
			sql = "update member_nm set call_f='1', call_nm = '"+name+"' where mbr_nm = '"+str[1]+"'";
			//con = ConnectionPool.getConnection();
			PreparedStatement upstmt = con.prepareStatement(sql);
			int upCnt = upstmt.executeUpdate();
			pstmt.close(); //con.close();
			if(upCnt > 0) clientMap.get(str[1]).println(URLEncoder.encode(name + " 님이 " + room_nm + " 방에 초대합니다. 수락하시겠습니까? (y/n)", "UTF-8"));
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void callYn(String name, String s) {
		String[] str = s.split(" ");
		
		try {	
			if(str[1].equalsIgnoreCase("y")) {	
								
				String sql =  "select room_no, room_nm, fix_num-(select count(*) from member_nm a "
							+ "where a.room_no=b.room_no) from room_list b "
							+ "where room_no = (select room_no from member_nm where mbr_nm ='"+str[2]+"')";
				//Connection con = ConnectionPool.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				int room_no = 0, chk_no=1;
				String room_nm = "";
				rs.next();
				room_no = rs.getInt(1); room_nm = rs.getString(2); chk_no = rs.getInt(3);
				rs.close(); pstmt.close(); //con.close();
				if(chk_no < 1) {
					clientMap.get(str[2]).println(URLEncoder.encode("정원초과로 입장에 실패하셨습니다.", "UTF-8"));
					clientMap.get(name).println(URLEncoder.encode("정원초과로 입장에 실패하셨습니다.", "UTF-8"));
					return;
				}
				clientMap.get(str[2]).println(URLEncoder.encode(name + " 님이 초대를 수락하셨습니다.", "UTF-8"));
				clientMap.get(name).println(URLEncoder.encode(room_nm + " 방에 입장하셨습니다.", "UTF-8"));
				sendAllMsg(str[2], " 님이 입장하셨습니다.");
				
				sql = "update member_nm set room_no='"+room_no+"', call_f='0' where mbr_nm = '"+name+"'"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);				
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
			}else if(str[1].equalsIgnoreCase("n"))
					clientMap.get(str[2]).println(URLEncoder.encode(name + " 님이 초대를 거절하셨습니다.", "UTF-8"));
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void changeCap(String name, String s) {
		String[] str = s.split(" ");
	
		try {
			if(str.length != 2) {
				clientMap.get(name).println(URLEncoder.encode("Usage: /cap 승계자명", "UTF-8"));
				return;	}	
			if(name.equals(str[1])){ clientMap.get(name).println(URLEncoder.encode("이름을 확인하세요.", "UTF-8")); return;}
			String sql ="select nvl(cap_nm, ' '), b.room_no, c.room_no " 
					+	"from room_list a, member_nm b, member_nm c " 
					+ 	"where a.room_no(+)=b.room_no "
					+	"and b.mbr_nm='"+str[1]+"' and b.black_f='0' and c.mbr_nm='"+name+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int myNo=0, sNo=0;
			String cap_nm = "";
			if(rs.next()){cap_nm = rs.getString(1); sNo = rs.getInt(2); myNo = rs.getInt(3);
						  rs.close(); pstmt.close();} //con.close();}
			else {	clientMap.get(name).println(URLEncoder.encode("상대방 이름을 확인하세요.", "UTF-8"));
					rs.close(); pstmt.close(); //con.close();
					return;}

			if(!name.equals(cap_nm)) {
				clientMap.get(name).println(URLEncoder.encode("방장만이 승계시킬 수 있습니다.", "UTF-8"));	return;
			}else if(myNo==0 || sNo==0) {
				clientMap.get(name).println(URLEncoder.encode("현재 대기실에 있습니다. 승계시킬 수 없습니다.", "UTF-8"));	return;
			}else if(myNo != sNo) {
				clientMap.get(name).println(URLEncoder.encode("다른 대화방에 있습니다. 승계시킬 수 없습니다.", "UTF-8"));	return;
			}
			sql = "update room_list set cap_nm = '"+str[1]+"' "
				+ "where room_no = (select room_no from room_list where cap_nm = '"+name+"')";
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			int upCnt = pstmt.executeUpdate();
			pstmt.close(); //con.close();
			if(upCnt > 0) 	sendAllMsg(str[1], "님이 방장이 되셨습니다.");
			
		} catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	
	public void outRoom(String name, String s, String check) {
		String[] str = s.split(" ");
		
		try {
			if(str.length != 2) {
				clientMap.get(name).println(URLEncoder.encode("Usage: /out 강퇴자명", "UTF-8"));
				return;}
			if(name.equals(str[1])){ clientMap.get(name).println(URLEncoder.encode("이름을 확인하세요.", "UTF-8")); return;}
			String sql ="select nvl(cap_nm, ' '), a.room_nm, b.room_no, c.room_no " 
					+	"from room_list a, member_nm b, member_nm c " 
					+ 	"where a.room_no(+)=b.room_no "
					+	"and b.mbr_nm='"+str[1]+"' and b.black_f='0' and c.mbr_nm='"+name+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int myNo=0, outNo=0;
			String cap_nm = "", room_nm="";
			if(rs.next()) { 	
				cap_nm = rs.getString(1); room_nm = rs.getString(2); 
				outNo = rs.getInt(3); myNo = rs.getInt(4);
				rs.close(); pstmt.close();} //con.close();}
			else { 	clientMap.get(name).println(URLEncoder.encode("상대방 이름을 확인하세요.", "UTF-8"));
				rs.close(); pstmt.close(); //con.close();
				return;}
			
			if(myNo==0 || outNo==0) {
				clientMap.get(name).println(URLEncoder.encode("대화방 참가자가 아닙니다. 강퇴시킬 수 없습니다.", "UTF-8"));	return;
			}else if(myNo != outNo) {
				clientMap.get(name).println(URLEncoder.encode("다른 대화방에 있습니다. 강퇴시킬 수 없습니다.", "UTF-8"));	return;
			}else if(!name.equals(cap_nm)) {
				clientMap.get(name).println(URLEncoder.encode("방장만이 강퇴시킬 수 있습니다.", "UTF-8"));	return;}
			sql = "update member_nm set room_no = 0 where mbr_nm = '"+str[1]+"'";
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			int upCnt = pstmt.executeUpdate();
			pstmt.close(); //con.close();
			
			if(!check.equals("del")) {
				sql = "insert into out_list values('"+str[1]+"', '"+myNo+"', '"+name+"')";
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
			}
			if(upCnt > 0) {	
				sendAllMsg(name, str[1]+"님을 강퇴시켰습니다.");
				clientMap.get(str[1]).println(URLEncoder.encode(room_nm + " 방에서 강퇴되었습니다.", "UTF-8"));
			}		
		} catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	
	public void myRoomlist(String name) {		
		try {
			String sql ="select mbr_nm, cap_nm, room_nm, a.room_no from member_nm a, room_list b "
					+ 	"where a.room_no=b.room_no and black_f='0' "
					+	"and a.room_no = (select room_no from member_nm where mbr_nm ='"+name+"')";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			String str = "", cap_nm="", room_nm="";
			int cnt=0, room_no=0;
			while(rs.next()) { 	
					str = str + rs.getString(1) + ", "; cap_nm=rs.getString(2); 
					room_nm=rs.getString(3); room_no=rs.getInt(4); cnt++;}
			if(str.length() > 2 && room_no > 0 && rs.getRow() > 0) {
				clientMap.get(name).println(URLEncoder.encode("현재 " +room_nm.toString()+" 방에 참여 중입니다("+cnt+"명)-방장:"+cap_nm.toString(), "UTF-8"));
				clientMap.get(name).println(URLEncoder.encode("["+str.substring(0, str.length()-2).toString() + "]", "UTF-8"));
				rs.close(); pstmt.close(); //con.close();
			}else {
				rs.close(); pstmt.close(); //con.close();
				waitlist(name);		
			}
		} catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	
	public void exitRoom(String name, String s) {
		try {
			String sql = "select a.room_no, nvl(b.room_nm, '대기방') " 
					+	 "from member_nm a, room_list b " 
					+    "where a.room_no=b.room_no(+) and mbr_nm='"+name+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int	room_no=0;
			String room_nm="";
			rs.next();
			room_no = rs.getInt(1); room_nm = rs.getString(2);
			rs.close(); pstmt.close(); //con.close();
			if(room_no == 0 && s.equals("1"))	
				clientMap.get(name).println(URLEncoder.encode("현재 대기방입니다.", "UTF-8"));
			else{
				sql = "delete from room_list "  //1인 경우 방삭제
					+ "where (1, room_no, cap_nm) = (select count(room_no), max(room_no), '"+name+"' "
					+ "from member_nm where room_no = '"+room_no+"')"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
				//방장이 나가는 경우 방장승계
				sql = "select a.mbr_nm, b.cap_nm from member_nm a, room_list b where rownum=1 and a.room_no=b.room_no "
					+ "and 1 < (select count(*) from member_nm where room_no = '"+room_no+"') and mbr_nm != '"+name+"'"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				String cap_nm="", mbr_nm="";
				if(rs.next()) {
					mbr_nm = rs.getString(1); cap_nm = rs.getString(2);
					rs.close(); pstmt.close();} //con.close();
				if(name.equals(cap_nm))	changeCap(name, "/cap " + mbr_nm);
				if(s.equals("1")) sendAllMsg(name, "님이 "+ room_nm +" 방에서 퇴장하셨습니다.");
				
				sql = "update member_nm set room_no = 0 where mbr_nm = '"+name+"'"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
				//방장이 나가는 경우 강퇴자 삭제
				sql = "delete from out_list where cap_nm = '"+name+"'"; 
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
			}
		}catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	
	public void changeRoom(String name, String s) {
		String[] str = s.split(" ");
		
		try {
			if(str.length == 1) {
				clientMap.get(name).println(URLEncoder.encode("Usage: /cd 숫자or방이름 비밀번호", "UTF-8"));
				return;}
			if(str[1].equals("~") || str[1].equals("0")) {	exitRoom(name, "1"); return;}	
			
			String sql = "select nvl(a.passwd, '공개방'), nvl(a.room_nm,' '), b.room_no, a.room_no, "
					+  "nvl((select room_no from out_list d where d.room_no=a.room_no and mbr_nm='"+name+"'),-1), "
					   + "(a.fix_num-(select count(*) from member_nm c where c.room_no=a.room_no)) "
					   + "from member_nm b, room_list a "
					   + "where b.mbr_nm='"+name+"' and (to_char(a.room_no)='"+str[1]+"' or a.room_nm='"+str[1]+"')";
				
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int	my_no=0, chk_no=1, room_no=0, out_no=-99;
			String passwd="", room_nm="";
			if(rs.next()) { 	
					passwd = rs.getString(1); room_nm=rs.getString(2); my_no=rs.getInt(3); 
					room_no = rs.getInt(4); out_no=rs.getInt(5); chk_no=rs.getInt(6);
					rs.close(); pstmt.close();} //con.close();}
			else {	clientMap.get(name).println(URLEncoder.encode("방 이름을 확인하세요.", "UTF-8"));
					rs.close(); pstmt.close(); //con.close();
					return;}
			if(room_no==out_no) {clientMap.get(name).println(URLEncoder.encode("강퇴당한 방입니다.", "UTF-8")); return;}	
			if(my_no > 0) {
				clientMap.get(name).println(URLEncoder.encode("대기방에서만 다른 방으로 입장이 가능합니다.", "UTF-8"));
				return;}
			if(chk_no < 1) {
				clientMap.get(name).println(URLEncoder.encode("정원 초과로 입장이 불가능합니다.", "UTF-8"));
				return;}
			if(!(passwd.equals("공개방")) && !(passwd.equals(str[str.length-1]))) {
				clientMap.get(name).println(URLEncoder.encode("비공개방 비밀번호와 일치하지 않습니다.", "UTF-8"));
				return;}
			
			sql = "update member_nm "
					+ "set room_no = (select room_no from room_list where to_char(room_no)='"+str[1]+"' "
					+ "or room_nm = '"+str[1]+"') where mbr_nm = '"+name+"'"; 
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			int uCnt = pstmt.executeUpdate();
			pstmt.close(); //con.close();	
			if(uCnt ==1) sendAllMsg(name, "님이 "+ room_nm +" 방에 입장하셨습니다.");				
		}catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	
	public static boolean isNumber(String input) {
		  try{Integer.parseInt(input);	  	return true;
		  }catch (NumberFormatException e){ return false;}
	}
	
	public void roomList(String name) {
		String sql =  "select to_char(room_no,'99')||'.'||room_nm||decode(passwd, '', '[공개방]','[비공개방]')" 
					+ "||'('||(select count(*) from member_nm b where b.room_no=a.room_no and black_f='0')||'/'||FIX_NUM||')'"
					+ "||decode(room_no, (select room_no from member_nm where mbr_nm='"+name+"'),'-입장','')"
					+ "from room_list a  order by room_no";
		try {
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 	clientMap.get(name).print(URLEncoder.encode(rs.getString(1)+"\t", "UTF-8"));}		
			if(rs.getRow()==0) 	clientMap.get(name).println(URLEncoder.encode("현재 개설된 대화방이 없습니다.", "UTF-8"));
			else				clientMap.get(name).println();	
			rs.close(); pstmt.close(); //con.close();
		} catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	
	public void makeRoom(String name, String s) {
		String[] str = s.split(" ");
		
		try {
			if(str.length==1) {
				clientMap.get(name).println(URLEncoder.encode("Usage: /make 방이름 [정원] [비밀번호]", "UTF-8"));
				return;	}
			else if(str.length > 2 && !(isNumber(str[2]))) {	
				clientMap.get(name).println(URLEncoder.encode("정원은 숫자이어야 합니다.", "UTF-8"));
				return;	}

			String fix_num = (str.length==3 ? str[str.length-1].toString() : "20");
			String passwd = (str.length==4 ? str[str.length-1].toString() : "");

			String sql = "select count(*) from room_list where room_nm = '"+str[1]+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int cnt =0;
			rs.next(); 	cnt = rs.getInt(1);
			rs.close(); pstmt.close(); //con.close();
			if(cnt == 0) {
				sql = "insert into room_list (room_no, room_nm, fix_num, passwd, cap_nm)"
					+ "values((select nvl(max(room_no),0)+1 from room_list), '"+str[1]+"',"
					+ "to_number('"+fix_num+"'), '"+passwd+"', '"+name+"')";						 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				int uCnt = pstmt.executeUpdate();
				pstmt.close(); //con.close();
				if(uCnt ==1) {
					clientMap.get(name).println(URLEncoder.encode(str[1] + "방이 만들어졌습니다.", "UTF-8"));
					sql = "update member_nm "
							+ "set room_no = (select room_no from room_list where room_nm='"+str[1]+"') "
							+ "where mbr_nm='"+name+"'";  
					//con = ConnectionPool.getConnection();
					pstmt = con.prepareStatement(sql);
					pstmt.executeUpdate();
					pstmt.close(); //con.close();
					clientMap.get(name).println(URLEncoder.encode(str[1] + "방에 입장 하셨습니다.", "UTF-8"));
				}
			}
			else	clientMap.get(name).println(URLEncoder.encode(str[1] + "의 방이 이미 있습니다. 다른 방이름으로 다시 해주세요.", "UTF-8"));
		} catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public int[] check_member(String name) {
		int[] check = {0, 0};
		try {
			String sql = "select rownum, to_number(black_f)  from member_nm " 
						+"where mbr_nm = '"+name+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) { 	check[0] = rs.getInt(1); check[1] = rs.getInt(2);
			rs.close(); pstmt.close();} //con.close();
			if(check[0]==0) {
				sql = "insert into member_nm(mbr_nm) values('"+name+"')"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public void notifyMsg(String name, String msg) {
		
		String[] str = msg.split(" ");
		try {
			if(str.length == 1) {
				clientMap.get(name).println(URLEncoder.encode("Usage: /noti 공지내용", "UTF-8"));
				return;	}
		} catch( UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Iterator<String> it = clientMap.keySet().iterator();
		while(it.hasNext()) {
			try {
				PrintWriter it_out = (PrintWriter)clientMap.get(it.next());
				it_out.println(URLEncoder.encode("[공지사항] " + msg.substring(6).trim(), "UTF-8"));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void waitlist(String name) {
		String sql =  "select mbr_nm from member_nm where room_no=0 and  black_f='0'";
		try {
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			String str = "";
			int cnt=0;
			while(rs.next()) { 	str = str + rs.getString(1) + ", "; cnt++;}
			rs.close(); pstmt.close(); //con.close();
			clientMap.get(name).println(URLEncoder.encode("현재 대기실입니다("+cnt+"명).", "UTF-8"));
			if(!str.equals("")) clientMap.get(name).println(URLEncoder.encode("["+str.substring(0, str.length()-2) + "]", "UTF-8"));			
		} catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	
	public void showList(String name) {
			
		Set<String> k = clientMap.keySet();
		try {
			PrintWriter n_out = (PrintWriter)clientMap.get(name);
			n_out.println(URLEncoder.encode("현재 접속자입니다("+ clientMap.size()+"명).", "UTF-8"));
		
			//for(String s : k )	n_out.println(s) ;
			n_out.println(URLEncoder.encode(k.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void blockList(String name, String s) {
		String[] s1 = s.split(" ");	
		
		try {
			if(s1.length != 2) {	
				clientMap.get(name).println(URLEncoder.encode("Usage: /blk 차단자이름", "UTF-8"));
				return;}
			String sql = "select count(*) from block_list where mbr_nm='"+name+"' and blk_nm='"+s1[1]+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int cnt = 0;
			rs.next(); 	cnt = rs.getInt(1);
			rs.close(); pstmt.close(); //con.close();
			
			if(cnt==0) {
				sql = "insert into block_list values('"+name+"', '"+s1[1]+"')";
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
				clientMap.get(name).println(URLEncoder.encode(s1[1] + "와의 대화를 차단합니다.", "UTF-8"));
			}
			else {
				sql = "delete from block_list where mbr_nm='"+name+"' and blk_nm='"+s1[1]+"')";
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); //con.close();
				clientMap.get(name).println(URLEncoder.encode(s1[1] + "와의 차단을 해제합니다.", "UTF-8"));	
			}
		}catch(SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMbr(String name) {
		try {
			String sql = "delete from member_nm where mbr_nm = '"+name+"' and black_f ='0'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close(); //con.close();
			sql = "delete from block_list where mbr_nm = '"+name+"'";
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close(); //con.close();
			sql = "delete from van_word where mbr_nm='"+name+"'";
			//con = ConnectionPool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close(); //con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void banWord(String name, String s){
		String[] str = s.split(" ");
				
		try {
			if(str.length == 1) {
				clientMap.get(name).println(URLEncoder.encode("Usage: /ban 금칙어", "UTF-8"));
				return;}
			String sql = "select count(*) from van_word where mbr_nm != '$system$' and word='"+str[1]+"'"; 
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int cnt = 0;
			rs.next(); 	cnt = rs.getInt(1);	rs.close(); pstmt.close(); //con.close();
			if(cnt > 0) {
				clientMap.get(name).println(URLEncoder.encode(str[1] + "는 이미 서버금칙어입니다.", "UTF-8"));
			}
			else{
				sql = "select count(*) from van_word where mbr_nm = '"+name+"' and word='"+str[1]+"'"; 
				//con = ConnectionPool.getConnection();
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				int cnt1 = 0;
				rs.next(); 	cnt1 = rs.getInt(1); rs.close(); pstmt.close(); //con.close();
				if(cnt1 == 0) {
					sql = "insert into van_word values('"+name+"','"+str[1]+"')";  
					//con = ConnectionPool.getConnection();
					pstmt = con.prepareStatement(sql);
					pstmt.executeUpdate();
					pstmt.close(); //con.close();
					clientMap.get(name).println(URLEncoder.encode(str[1] + "는 금칙어에 포함됩니다.", "UTF-8"));
				}
				else {
					sql = "delete from van_word where mbr_nm='"+name+"' and word='"+str[1]+"'";  
					//con = ConnectionPool.getConnection();
					pstmt = con.prepareStatement(sql);
					pstmt.executeUpdate();
					pstmt.close(); //con.close();
					clientMap.get(name).println(URLEncoder.encode(str[1] + "는 금칙어에서 해제합니다.", "UTF-8"));
				}
				//con.close();
			}
		} catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
	}
	
	public void talkToOne(String name, String s) {
		
		String[] s1 = s.split(" ");
		
		try {
			if(s1.length == 1) {
				clientMap.get(name).println(URLEncoder.encode("Usage: /to 상대방 [메시지]", "UTF-8"));
				return;}
			if(name.equals(s1[1])){ clientMap.get(name).println(URLEncoder.encode("이름을 확인하세요.", "UTF-8")); return;}
			String blk_nm = "";
			int myNo=0, toNo=0;
			String sql = "select nvl(blk_nm,' '), b.room_no, c.room_no "
					+	 "from block_list a, member_nm b, member_nm c "
					+	 "where a.mbr_nm(+)=b.mbr_nm and b.mbr_nm='"+s1[1]+"' and c.mbr_nm='"+name+"'";
			//Connection con = ConnectionPool.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {  	blk_nm=rs.getString(1); toNo=rs.getInt(2); myNo=rs.getInt(3);
								rs.close(); pstmt.close();} //con.close();}
			else { 	clientMap.get(name).println(URLEncoder.encode("상대방 이름을 확인하세요.", "UTF-8"));
					return;}
			if(!(name.equals(blk_nm)) && myNo == toNo) {
				PrintWriter out = (PrintWriter)clientMap.get(s1[1]);
//				String s2 = "(귓속말)" + nm + "=>" + s.substring(nm.length()+2+ s1[0].length()+s1[1].length()+2);
				out.println(URLEncoder.encode("[" + name +"] (귓속말) " + s.substring(name.length()+5).trim(), "UTF-8"));		
			}
			
		} catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args){

		MultiServer ms = new MultiServer();
		ms.init();		
	}	
}