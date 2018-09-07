package com.study.jsp;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.websocket.*;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


@ServerEndpoint("/websocketendpoint2")
public class WsServer2 {
	
		DataSource dataSource = null;
		private static final Map<String, String> clientMap = (Map<String, String>) new HashMap<String, String>();

		private static final java.util.Set<Session> sessions =
				java.util.Collections.synchronizedSet(new java.util.HashSet<Session>());
		
		@OnOpen
		public void onOpen(Session session) {
			System.out.println("Open session id : " + session.getId());
			Collections.synchronizedMap(clientMap);
			try {
				final Basic basic = session.getBasicRemote();
				basic.sendText("2 Connection Established");
				Context context = new InitialContext();
				dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
			} catch (IOException | NamingException e) {
				System.out.println(e.getMessage());
			}
			sessions.add(session);
		}
		
		@OnClose
		public void onClose(Session session) {
			try {
				MembersDAO dao = MembersDAO.getInstance();
				System.out.println("Session " + session.getId() + " has ended");
				System.out.println(clientMap.size());
				System.out.println("closed: " + clientMap.get((String)session.getId()));
				dao.updateLogin(clientMap.get(session.getId()), -1);
				String sql = "update members set call_f='0', game_f='0', check_f='0', game_val=0, game_cnt=0 where id = ?"; 
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, clientMap.get(session.getId()));   pstmt.executeUpdate();
				sql = "update members set call_f='0', game_f='0', check_f='0', game_val=0, game_cnt=0 where call_id = ?"; 
				pstmt.setString(1, clientMap.get(session.getId()));   pstmt.executeUpdate();
				pstmt.close(); con.close();
				clientMap.remove(session.getId());
				sessions.remove(session);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		@OnMessage
		public void onMessage(String message, Session session) {
			List<String> ls = Arrays.asList("/list","/ls", "/rls", "/ls0","/lsw","/ls.","/my",
					"/exit", "/ex" ,"/to", "/cd","/blk", "/ban", "/bls","/out","/cap","/del","/call","/make",
					"/noti", "/gam1", "/gam2");
			ls = new ArrayList<>(ls);
			System.out.println("Message from : " + session.getId() + " : " + message);
			String[] str = message.split(" ");
			String s = message.substring(str[0].length()+3);
			MembersDAO dao = MembersDAO.getInstance();
			try {
				final Basic basic = session.getBasicRemote();
				basic.sendText("2" + " " + str[0] + " : " + s);
				
				String[] s1 = s.split(" ");	
				if(s.substring(0,1).equals("/") && !ls.contains(s1[0])){ 
					//basic.sendText(str[1] + " '"+s1[0]+"'" + "는 없는 명령어입니다.");
					basic.sendText("4" + " '"+s1[0]+"'" + "는 없는 명령어입니다.");	
					return;}
				
				String[] calls = {"0", " ", "0"};
				if(!clientMap.containsKey(session.getId())) dao.updateLogin(str[0], 0);
				calls = checkCall(str[0]);
				
				if(calls[0].equals("1")) 	s = "/$$cal " + s + " " + calls[1].toString();
				else if(calls[2].equals("1")) s = "/$$g1$ " + s + " " + calls[1].toString() + " " + calls[2];
				else if(calls[2].equals("2")) s = "/$$g2$ " + s + " " + calls[1].toString() + " " + calls[2];
				else if(calls[2].equals("3")) s = "/$$g3$ " + s + " " + calls[1].toString() + " " + calls[2];
				
				if(clientMap.get(session.getId())==null)
							clientMap.putIfAbsent(session.getId(), str[0]);
				
				if(s.equals("/list") || s.equals("/ls"))  					showList(session, str[1]);
				else if(s.equals("/ls0") || s.equals("/lsw"))				waitlist(session, str[0], str[1]);
				else if(s.equals("/bls"))									banlist(session, str[0], str[1]);
				else if(s.equals("/ls.") || s.equals("/my"))				myRoomlist(session, str[0], str[1]);
				else if(s.equals("/exit") || s.equals("/ex"))				exitRoom(session, str[0], str[1], "1");		
				else if(s.length()>2 && s.substring(0,3).equals("/to"))		talkToOne(session, str[0], str[1], s);
				else if(s.length()>2 && s.substring(0,3).equals("/cd"))		changeRoom(session, str[0], str[1], s);
				else if(s.length()>3 && s.substring(0,4).equals("/blk"))	blockList(session, str[0], str[1], s);
				else if(s.length()>3 && s.substring(0,4).equals("/ban"))	banWord(session, str[0], str[1], s);	
				else if(s.length()>3 && s.substring(0,4).equals("/rls"))	roomList(session, str[0], str[1], s);	
				else if(s.length()>3 && s.substring(0,4).equals("/out"))	outRoom(session, str[0], str[1], s, "out");	
				else if(s.length()>3 && s.substring(0,4).equals("/cap"))	changeCap(session, str[0], str[1], s);
				else if(s.length()>3 && s.substring(0,4).equals("/del"))	deleteRoom(session, str[0], str[1]);
				else if(s.length()>4 && s.substring(0,5).equals("/call"))	callName(session, str[0], str[1], s);
				else if(s.length()>4 && s.substring(0,5).equals("/noti"))	notifyMsg(session, str[0], str[1], s);					
				else if(s.length()>4 && s.substring(0,5).equals("/make"))	makeRoom(session, str[0], str[1], s);
				else if(s.length()>4 && s.substring(0,5).equals("/gam1"))	gameCheck(session, str[0], str[1], s, "1");
				else if(s.length()>4 && s.substring(0,5).equals("/gam2"))	gameCheck(session, str[0], str[1], s, "2");
				else if(s.length()>5 && s.substring(0,6).equals("/$$cal"))	callYn(session, str[0], str[1], s);
				else if(s.length()>5 && s.substring(0,6).equals("/$$g1$"))	baseBall(session, str[0], str[1], s);
				else if(s.length()>5 && s.substring(0,6).equals("/$$g2$"))	ticTacToe(session, str[0], str[1], s);
				else 														sendAllSessionToMessage(session, str[0], str[1], s);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		public void ticTacToe(Session self, String name, String webId, String msg) {//$$g2$ + 1:ox + 2:상대방 + 3:game_f
//			String[][] brd= 
//				{{"&nbsp","1","&nbsp","|","&nbsp","2","&nbsp","|","&nbsp","3","&nbsp"},
//				 {"-","-","-","|","-","-","-","|","-","-","-"},
//				 {"&nbsp","4","&nbsp","|","&nbsp","5","&nbsp","|","&nbsp","6","&nbsp"},
//				 {"-","-","-","|","-","-","-","|","-","-","-"},
//				 {"&nbsp","7","&nbsp","|","&nbsp","8","&nbsp","|","&nbsp","9","&nbsp"}};
//			int[] x = {0,0,0,0,2,2,2,4,4,4};
//			int[] y = {0,1,5,9,1,5,9,1,5,9};
//			
			String[][] brd= 
			{{"&nbsp","1","&nbsp","|","&nbsp","2","&nbsp","|","&nbsp","3","&nbsp"},
			 {"&nbsp","4","&nbsp","|","&nbsp","5","&nbsp","|","&nbsp","6","&nbsp"},
			 {"&nbsp","7","&nbsp","|","&nbsp","8","&nbsp","|","&nbsp","9","&nbsp"}};
		int[] x = {0,0,0,0,1,1,1,2,2,2};
		int[] y = {0,1,5,9,1,5,9,1,5,9};
				
			String[] str = msg.split(" ");	
			try {	
				
				Set<String> k = clientMap.keySet();
				String sId="";
				for(String s : k )
					if(str[2].equals(clientMap.get(s))) sId = s;
					
				Basic b1 = self.getBasicRemote();
				Basic b2 = null;
				for( Session session : WsServer2.sessions) {
					if (sId.equals(session.getId()))
						 b2 = session.getBasicRemote();
				}
				
				if(str[1].equals("/ex") || str[1].equals("/exit")) {
					b2.sendText("4" + " " + name + " 님이 게임을 중단하셨습니다.");
					String sql = "update members set game_f='0', check_f='0', game_val=0, game_cnt=0, "
							+ "ox_val='' where id = ?"; 
					Connection con = dataSource.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.setString(1, name);   pstmt.executeUpdate();
					pstmt.setString(1, str[2]); pstmt.executeUpdate();
					pstmt.close(); con.close();
					return;
				}
				String sql = "select check_f from members where id ='"+name+"'";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				
				String chk_f="";
				rs.next(); 	chk_f = rs.getString(1); 
				rs.close(); pstmt.close(); 
				
				if(str[1].equalsIgnoreCase("n") && chk_f.equals("0")) {
					b2.sendText("4" + " " + name + " 님이 초대를 거절하셨습니다.");
					sql = "update members set game_f='0' where id =?"; 
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, name);   pstmt.executeUpdate();
					pstmt.setString(1, str[2]); pstmt.executeUpdate();
					pstmt.close(); con.close();
					return;
				}
				else if(str[1].equalsIgnoreCase("y") && chk_f.equals("0")) {
					b2.sendText("4" + " TicTacToe 게임 시작." + "<br>" + " 잠시만 기다려주세요.");
					sql = "update members set check_f='1' where id ='"+name+"'"; 
					PreparedStatement upstmt = con.prepareStatement(sql);
					upstmt.executeUpdate();
					upstmt.close(); con.close();
					prnBoard(b1, brd, "X", 0);
					return;
				}
				
				if(chk_f.equals("0") && !isNumber(str[1])) {
					b1.sendText("4" + " 숫자만 가능합니다.");
					return;}
				if(chk_f.equals("0") && (str[1].length() > 1 || Integer.parseInt(str[1]) ==0)) {
					b1.sendText("4" + " 한자리 숫자[1~9]만 가능합니다.");
					return;}
				
				sql = "select a.ox_val, a.game_cnt, a.score, b.ox_val, b.game_cnt, b.score "
					+ "from members a, members b where a.id ='"+name+"' and b.id ='"+str[2]+"'";
				con = dataSource.getConnection();
				PreparedStatement  spstmt = con.prepareStatement(sql);
				rs = spstmt.executeQuery();
				int m_cnt=0, u_cnt=0, m_sco=0, u_sco=0;
				String m_ox="", u_ox="";
				rs.next();  	
				m_ox = rs.getString(1); m_cnt = rs.getInt(2); m_sco = rs.getInt(3);
				u_ox = rs.getString(4);	u_cnt = rs.getInt(5); u_sco = rs.getInt(6);
				rs.close(); spstmt.close(); con.close();
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
				
					prnBoard(b1, brd, m_ox.substring(0,1), 1);
					prnBoard(b2, brd, u_ox.substring(0,1), 1);
					if(Wins == true) {
						m_sco += 100; u_sco -= 100;
						b1.sendText(webId + " <TicTacToe> Bingo!! Yon Winner. 점수: " + m_sco);
						b2.sendText(webId + " <TicTacToe> Bingo!! You Loser. 점수: " + u_sco);
					}else {
						b1.sendText(webId + " <TicTacToe> Game is up.");
						b2.sendText(webId + " <TicTacToe> Game is up.");
					}
					sql = "update members set game_val=0, check_f='0', game_cnt=0, ox_val=?, "
						+ "score = score + ?, call_id=? where id = ?"; 
					con = dataSource.getConnection();
					PreparedStatement pstmt1 = con.prepareStatement(sql);
					pstmt1.setString(1, "O"); pstmt1.setInt(2, 100); 
					pstmt1.setString(3, str[2]); pstmt1.setString(4, name);   pstmt1.executeUpdate();
					pstmt1.setString(1, "X"); pstmt1.setInt(2,-100); 
					pstmt1.setString(3, name);pstmt1.setString(4, str[2]); pstmt1.executeUpdate();
					pstmt1.close(); con.close();
					b2.sendText("yn" + " 다시 하시겠습니까? (y/n) ");
				}else {
					prnBoard(b2, brd, u_ox.substring(0,1), 0);
					//clearScreen(name);
					b1.sendText("4" + " 상대방 차례입니다. 잠시만 기다려주세요.");
					sql = "update members set game_cnt=game_cnt+1, ox_val = ox_val||'"+str[1]+"' "
							+ "where id ='"+name+"'"; 
					Connection con1 = dataSource.getConnection();
					PreparedStatement pstmt2 = con1.prepareStatement(sql);
					pstmt2.executeUpdate();
					pstmt2.close(); con.close();
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		
		public boolean okBoard(String[][] brd, String s) {	
//			if(brd[0][1].equals(s) && brd[0][5].equals(s) && brd[0][9].equals(s)) return true;
//			if(brd[2][1].equals(s) && brd[2][5].equals(s) && brd[2][9].equals(s)) return true;
//			if(brd[4][1].equals(s) && brd[4][5].equals(s) && brd[4][9].equals(s)) return true;
//			if(brd[0][1].equals(s) && brd[2][1].equals(s) && brd[4][1].equals(s)) return true;
//			if(brd[0][5].equals(s) && brd[2][5].equals(s) && brd[4][5].equals(s)) return true;
//			if(brd[0][9].equals(s) && brd[2][9].equals(s) && brd[4][9].equals(s)) return true;
//			if(brd[0][1].equals(s) && brd[2][5].equals(s) && brd[4][9].equals(s)) return true;
//			if(brd[0][9].equals(s) && brd[2][5].equals(s) && brd[4][1].equals(s)) return true;

			if(brd[0][1].equals(s) && brd[0][5].equals(s) && brd[0][9].equals(s)) return true;
			if(brd[1][1].equals(s) && brd[1][5].equals(s) && brd[1][9].equals(s)) return true;
			if(brd[2][1].equals(s) && brd[2][5].equals(s) && brd[2][9].equals(s)) return true;
			if(brd[0][1].equals(s) && brd[1][1].equals(s) && brd[1][1].equals(s)) return true;
			if(brd[0][5].equals(s) && brd[1][5].equals(s) && brd[2][5].equals(s)) return true;
			if(brd[0][9].equals(s) && brd[1][9].equals(s) && brd[2][9].equals(s)) return true;
			if(brd[0][1].equals(s) && brd[1][5].equals(s) && brd[2][9].equals(s)) return true;
			if(brd[0][9].equals(s) && brd[1][5].equals(s) && brd[2][1].equals(s)) return true;
			
			return false;
		}

		public void prnBoard(Basic b, String[][] brd, String ox, int check) {		
			
			String msg = "Please enter the number of squarer where you want to place your ";
			//clearScreen(name);
			try {
				String str="";
				for(int i=0; i<brd.length; i++) {
					for(int j=0; j<brd[i].length; j++) { 
						str += String.format("%s", brd[i][j]);
					}
					str += "<br>";
				}
//				b.sendText("0 " + str);
//				if(check==0) b.sendText("0 " +msg + ox);
				if(check==0) {
					b.sendText("9 " + str + msg + ox);
				} else b.sendText("9 " + str);
			} catch (IOException e) {
					e.printStackTrace();
			}
		}
		
		public void gameCheck(Session self, String name, String webId, String s, String game) {
			String[] str = s.split(" ");
			
			try {
				if(name.equals(str[1])){ self.getBasicRemote().sendText("4" + " 이름을 확인하세요."); return;}
				if(str.length != 2) {
					self.getBasicRemote().sendText("4" + " Usage: /gam" +game+" 상대자명"); 
					return;}	
				String sql ="select a.room_no, b.room_no, b.game_f from members a, members b "
						+	"where a.id = '"+name+"' and b.black_f='0' and b.id = '"+str[1]+"'";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				int myNo=0, toNo=0;
				String game_f = "";
				if(rs.next()) { 	myNo = rs.getInt(1); toNo = rs.getInt(2); game_f = rs.getString(3);
									rs.close(); pstmt.close(); con.close();} 
				else {	self.getBasicRemote().sendText("4" + " 상대방 이름을 확인하세요.");
						return;}
				
				if(myNo != toNo) {
					self.getBasicRemote().sendText("4" + " 게임은 대기실이나 같은 대화방에서만 가능합니다.");
					return;
				}
				if(!(game_f.equals("0"))) {
					self.getBasicRemote().sendText("4" + " " + str[1] + " 님은 게임 중 입니다.");
					return;
				}
				sql = "update members set game_f = ?, call_id = ?, ox_val =? where id = ?"; 
				con = dataSource.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, game); pstmt.setString(2, str[1]); 
				pstmt.setString(3, "o"); pstmt.setString(4, name); 
				pstmt.executeUpdate();
				pstmt.setString(1, game); pstmt.setString(2, name); 
				pstmt.setString(3, "x"); pstmt.setString(4, str[1]); 
				pstmt.executeUpdate();
				pstmt.close(); con.close();
				
				Set<String> k = clientMap.keySet();
				String sId="";
				for(String ss : k )
					if(str[1].equals(clientMap.get(ss))) sId = ss;
					
				Basic b2 = null;
				for( Session session : WsServer2.sessions) {
					if (sId.equals(session.getId()))
						 b2 = session.getBasicRemote();
				}
				
				if(game.equals("1")) 
					b2.sendText("yn" + " " +name + " 님이 " + " 숫자로 하는 야구게임에 초대합니다. 수락하시겠습니까? (y/n)");
				else
					b2.sendText("yn" + " " +name + " 님이 " + " TicTacToe 게임에 초대합니다. 수락하시겠습니까? (y/n)");
				self.getBasicRemote().sendText("4 " + str[1] + " 님을 초대했습니다");
			} catch (IOException | SQLException e) {
					e.printStackTrace();
			}
		}

		public void baseBall(Session self, String name, String webId, String s) { ///$$g1$ + 1:숫자 + 2:상대방 + 3:game_f
			String[] str = s.split(" ");		
			
			try {
				Set<String> k = clientMap.keySet();
				String sId="";
				for(String ks : k )
					if(str[2].equals(clientMap.get(ks))) sId = ks;
					
				Basic b2 = null;
				for( Session session : WsServer2.sessions) {
					if (sId.equals(session.getId()))
						 b2 = session.getBasicRemote();
				}
				
				if(str[1].equals("/ex") || str[1].equals("/exit")) {
					b2.sendText("4" + " " +name + " 님이 게임을 중단하셨습니다.");
					String sql = "update members set game_f='0', check_f='0', game_val=0, game_cnt=0 where id = ?"; 
					Connection con = dataSource.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.setString(1, name);   pstmt.executeUpdate();
					pstmt.setString(1, str[2]); pstmt.executeUpdate();
					pstmt.close(); con.close();
					return;
				}
				String sql = "select check_f from members where id='"+name+"'";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
					
				String chk_f="";
				rs.next();
				chk_f = rs.getString(1);
				rs.close(); pstmt.close(); 
				
				if(str[1].equalsIgnoreCase("n") && chk_f.equals("0")) {
					b2.sendText("4" + " " + name + " 님이 초대를 거절하셨습니다.");
					self.getBasicRemote().sendText("4" + " 게임이 종료되었습니다.");
					sql = "update members set game_f='0' where id =?"; 
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, name);   pstmt.executeUpdate();
					pstmt.setString(1, str[2]); pstmt.executeUpdate();
					pstmt.close(); con.close(); 
					return;
				}
				else if(str[1].equalsIgnoreCase("y") && chk_f.equals("0")) {
					//clearScreen(name); clearScreen(str[2]);
					self.getBasicRemote().sendText("4" + " 숫자로 하는 야구게임 시작.");
					b2.sendText("4" + " 숫자로 하는 야구게임 시작.");
					self.getBasicRemote().sendText("4" + " 서로 다른 세자리 자기 숫자를 입력하세요.");
					b2.sendText("4" + " 서로 다른 세자리 자기 숫자를 입력하세요.");
					sql = "update member_nm set check_f='1' where mbr_nm ='"+name+"'"; 
					con = dataSource.getConnection();
					PreparedStatement pstmt1 = con.prepareStatement(sql);
					pstmt1.executeUpdate();
					pstmt1.close(); con.close();
					return;
				}
				//clearScreen(name);
				if(!isNumber(str[1])) {
					self.getBasicRemote().sendText("4" + " 숫자만 가능합니다.");
					return;}
				if(str[1].length() !=3 ) {
					self.getBasicRemote().sendText("4" + " 세자리 숫자만 가능합니다.");
					return;}
				
				int c = Integer.parseInt(str[1]);
				int c1 = c/100,	c2 = (c%100)/10, c3 = c%10;
				
				if(c1==c2 || c1==c3 || c2==c3) {
					self.getBasicRemote().sendText("4" + " 서로 다른 숫자만 가능합니다.");
					return;}
				if(c1*c2*c3==0) {
					self.getBasicRemote().sendText("4" + " 1에서 9까지 숫자만 가능합니다.");
					return;	}
				
				sql = "update members "
					+ "set game_val=decode(game_cnt, 0, to_number('"+str[1]+"'), game_val),"
					+ "game_cnt=decode((select game_cnt from members where id = '"+str[2]+"'), 0, 1, game_cnt+1) "
					+ "where id='"+name+"'";
				Connection con1 = dataSource.getConnection();
				PreparedStatement pstmt1 = con1.prepareStatement(sql);
				pstmt1.executeUpdate();
				pstmt1.close();
			
				sql = "select b.game_val, b.game_cnt, a.game_cnt, b.score, a.score from members a, members b "
					+ "where a.id = '"+name+"' and b.id='"+str[2]+"'";
				PreparedStatement pstmt2 = con1.prepareStatement(sql);
				rs = pstmt2.executeQuery();
			
				int u=0, strk=0, ball=0, m_cnt=0, u_cnt=0, m_score=0, u_score=0;
				rs.next();
				u = rs.getInt(1); u_cnt = rs.getInt(2); m_cnt = rs.getInt(3);
				u_score = rs.getInt(4); m_score = rs.getInt(5); 
				rs.close(); pstmt2.close(); con1.close();
				if(u==0 || u_cnt==0) { 
					self.getBasicRemote().sendText("4" + " 상대방이 입력하기 전 입니다. 조금 기다리신 후 입력하세요.");
					return;
				}		
				
				int u1 = u/100,	u2 = (u%100)/10, u3 = u%10;
				
				if(u1==c1)					strk++;
				if(u2==c2)					strk++;
				if(u3==c3)					strk++;
				
				if(u2==c1 || u2 ==c3)		ball++;
				if(u1==c2 || u1 ==c3)		ball++;
				if(u3==c2 || u3 ==c1)		ball++;
				
				self.getBasicRemote().sendText(webId + " <Baseball> " + c1 + ":" + c2 + ":" +c3);
				self.getBasicRemote().sendText(webId + " <Baseball> " + strk + " strike " + ball + " Ball");
			
				if(strk==3) {
					//clearScreen(str[2]);
					m_score += 100; u_score -= 100;
					self.getBasicRemote().sendText("4" + " <Baseball> Winner! 점수: " + m_score);
					b2.sendText("4" + " <Baseball> " + c1 + ":" + c2 + ":" +c3);
					b2.sendText("4" + " <Baseball> Loser! 점수: "+ u_score);
				
					sql = "update members set game_val=0, check_f='0', game_cnt=0, score = score + ?, "
						+ "call_id=? where id = ?"; 
					Connection con3 = dataSource.getConnection();
					PreparedStatement pstmt3 = con3.prepareStatement(sql);
					pstmt3.setInt(1, 100); pstmt3.setString(2, str[2]); pstmt3.setString(3, name); pstmt3.executeUpdate();
					pstmt3.setInt(1,-100); pstmt3.setString(2, name); pstmt3.setString(3, str[2]); pstmt3.executeUpdate();
					pstmt3.close(); con3.close();
					b2.sendText("yn" + " 다시 하시겠습니까? (y/n) ");
				}else self.getBasicRemote().sendText("4" + " 서로 다른 세자리 숫자를 입력하세요");
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void callName(Session self, String name, String webId, String s) {
			String[] str = s.split(" ");
			try {
				if(str.length != 2) {
					self.getBasicRemote().sendText("4" + " Usage: /call 초대자");
					return;	}	
				if(name.equals(str[1])){ self.getBasicRemote().sendText("4" + " 이름을 확인하세요."); return;}
				
				String sql ="select a.room_nm, b.room_no, c.room_no, c.call_f " 
						+	"from room_list a, members b, members c " 
						+ 	"where a.room_no(+)=b.room_no "
						+	"and c.black_f='0' and c.id='"+str[1]+"' and b.id='"+name+"'";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				int myNo=0, cNo=0;
				String room_nm="", call_f="";
				if(rs.next()) {	room_nm = rs.getString(1); myNo = rs.getInt(2); 
								cNo = rs.getInt(3); call_f = rs.getString(4);
								rs.close(); pstmt.close(); con.close();}
				else {	self.getBasicRemote().sendText("4" + " 상대방 이름을 확인하세요.");
						rs.close(); pstmt.close(); con.close();
						return;}
							
				if(myNo==0) {
					self.getBasicRemote().sendText("4" + " 현재 대기실입니다. 대화방에서만 초대할 수 있습니다.");	return;}
				if(cNo > 0) {
					self.getBasicRemote().sendText("4" + " " +str[1]+" 님이 다른 대화방에 있습니다. 초대할 수 없습니다.");	return;}
				if(call_f.equals("1")) {
					self.getBasicRemote().sendText("4" + " " +str[1]+" 님이 다른 사람에게 초대받고 있습니다. 초대할 수 없습니다.");	return;}	
				
				sql = "update members set call_f='1', call_id = '"+name+"' where id = '"+str[1]+"'";
				con = dataSource.getConnection();
				pstmt = con.prepareStatement(sql);
				int upCnt = pstmt.executeUpdate();
				pstmt.close(); con.close();
				if(upCnt > 0) {
					Set<String> k = clientMap.keySet();
					String sId="";
					for(String ss : k )
						if(str[1].equals(clientMap.get(ss))) sId = ss;
						
					Basic b2 = null;
					for( Session session : WsServer2.sessions) {
						if (sId.equals(session.getId()))
							 b2 = session.getBasicRemote();
					}
					b2.sendText("yn" + " " + name + " 님이 " + room_nm + " 방에 초대합니다. 수락하시겠습니까? (y/n)");
					self.getBasicRemote().sendText("4 " + str[1] + " 님을 초대했습니다");
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}	
		}
		
		public void callYn(Session self, String name, String webId, String s) {
			String[] str = s.split(" ");
			
			try {	
				if(str.length != 3) { 
					self.getBasicRemote().sendText("4" + " 인자가 부족합니다.");
					return;
				}
				Set<String> k = clientMap.keySet();
				String sId="";
				for(String ks : k )
					if(str[2].equals(clientMap.get(ks))) sId = ks;
					
				Basic b2 = null;
				for( Session session : WsServer2.sessions) {
					if (sId.equals(session.getId()))
						 b2 = session.getBasicRemote();
				}
				
				if(str[1].equalsIgnoreCase("y")) {	
										
					String sql =  "select room_no, room_nm, fix_num-(select count(*) from members a "
								+ "where a.room_no=b.room_no) from room_list b "
								+ "where room_no = (select room_no from members where id ='"+str[2]+"')";
					Connection con = dataSource.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					int room_no = 0, chk_no=1;
					String room_nm = "";
					rs.next();
					room_no = rs.getInt(1); room_nm = rs.getString(2); chk_no = rs.getInt(3);
					rs.close(); pstmt.close(); con.close();
					if(chk_no < 1) {
						b2.sendText("4" + " 정원초과로 입장에 실패하셨습니다.");
						self.getBasicRemote().sendText("4" + " 정원초과로 입장에 실패하셨습니다.");
						return;
					}
					b2.sendText("4" + " " + name + " 님이 초대를 수락하셨습니다.");
					self.getBasicRemote().sendText("4" + " " + room_nm + " 방에 입장하셨습니다.");
					sendAllSessionToMessage(self, str[2], webId, " 님이 입장하셨습니다.");
					
					sql = "update members set room_no='"+room_no+"', call_f='0' where id = '"+name+"'"; 
					con = dataSource.getConnection();
					pstmt = con.prepareStatement(sql);				
					pstmt.executeUpdate();
					pstmt.close(); con.close();
				}else if(str[1].equalsIgnoreCase("n"))
					b2.sendText("4" + " " + name + " 님이 초대를 거절하셨습니다.");
					String sql = "update members set call_f='0' where id = '"+name+"'"; 
					Connection con = dataSource.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);				
					pstmt.executeUpdate();
					pstmt.close(); con.close();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}	
		}
		
		public void makeRoom(Session self, String name, String webId, String s) {
			String[] str = s.split(" ");
			
			try {
				if(str.length==1) {
					self.getBasicRemote().sendText("4" + " Usage: /make 방이름 [정원] [비밀번호]");
					return;	}
				else if(str.length > 2 && !(isNumber(str[2]))) {	
					self.getBasicRemote().sendText("4" + " 정원은 숫자이어야 합니다.");
					return;	}

				String fix_num = (str.length==3 ? str[str.length-1].toString() : "20");
				String passwd = (str.length==4 ? str[str.length-1].toString() : "");

				String sql = "select count(*) from room_list where room_nm = '"+str[1]+"'";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				int cnt =0;
				rs.next(); 	cnt = rs.getInt(1);
				rs.close(); pstmt.close(); 
				if(cnt == 0) {
					sql = "insert into room_list (room_no, room_nm, fix_num, passwd, cap_id)"
						+ "values((select nvl(max(room_no),0)+1 from room_list), '"+str[1]+"',"
						+ "to_number('"+fix_num+"'), '"+passwd+"', '"+name+"')";						 
					pstmt = con.prepareStatement(sql);
					int uCnt = pstmt.executeUpdate();
					pstmt.close(); 
					if(uCnt ==1) {
						self.getBasicRemote().sendText(webId + " " + str[1] + "방이 만들어졌습니다.");
						sql = "update members "
								+ "set room_no = (select room_no from room_list where cap_id=id and "
								+ "room_nm='"+str[1]+"') where id='"+name+"'";  
						pstmt = con.prepareStatement(sql);
						pstmt.executeUpdate();
						pstmt.close(); 
						self.getBasicRemote().sendText("4" + " " + str[1] + "방에 입장 하셨습니다.");
					}
				}
				else	self.getBasicRemote().sendText("4" + " " + str[1] + "의 방이 이미 있습니다.<br>다른 방이름으로 다시 해주세요.");
				con.close();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		
		public void deleteRoom(Session self, String name, String webId) {
			
			try {	
				String sql ="select cap_id, room_no, room_no||'.'||room_nm, "
						+ "(select count(*) from members b where a.room_no=b.room_no) " 
						+ "from room_list a where a.cap_id ='"+name+"'";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				int room_no=0, cnt = 0;
				String cap_nm = "", room_nm="";
				if(rs.next()) { 	
					cap_nm = rs.getString(1); room_no = rs.getInt(2); 
					room_nm = rs.getString(3); cnt = rs.getInt(4);}
				rs.close(); pstmt.close(); con.close();
	
				if(!name.equals(cap_nm)) {
					self.getBasicRemote().sendText("4" + " 방장만이 대화방을 폭파시킬 수 있습니다.");	return;
				}
				
				String[] mbr_nm = new String[cnt];
				sql = "select id from members where room_no = '"+room_no+"' and id != '"+name+"'";
				con = dataSource.getConnection();
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				int i=0;
				while(rs.next()) { mbr_nm[i] = rs.getString(1); i++;}
				rs.close(); pstmt.close(); con.close();
				
				for(i=0; i<cnt-1; i++)	outRoom(self, name, webId, "/out " + mbr_nm[i], "del");
				
				sql = "update members set room_no = 0 where id = '"+name+"'";
				con = dataSource.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close(); 
				
				sql = "delete from room_list where room_no = '"+room_no+"'";
				pstmt = con.prepareStatement(sql);
				int upCnt = pstmt.executeUpdate();
				pstmt.close(); con.close();
				if(upCnt > 0) 	sendAllSessionToMessage(self, name, webId, room_nm + " 방을 폭파시켰습니다.");			
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}	
		}
		
		public void changeRoom(Session self, String name, String webId, String s) {
			String[] str = s.split(" ");
			
			try {
				if(str.length == 1) {
					self.getBasicRemote().sendText("4" + " Usage: /cd 숫자or방이름 비밀번호");
					return;}
				if(str[1].equals("~") || str[1].equals("0")) {	exitRoom(self, name, webId, "1"); return;}	
				
				String sql = "select nvl(a.passwd, '공개방'), nvl(a.room_nm,' '), b.room_no, a.room_no, "
						+  "nvl((select room_no from out_list d where d.room_no=a.room_no and id='"+name+"'),-1), "
						   + "(a.fix_num-(select count(*) from members c where c.room_no=a.room_no)) "
						   + "from members b, room_list a "
						   + "where b.id='"+name+"' and (to_char(a.room_no)='"+str[1]+"' or a.room_nm='"+str[1]+"')";
				Connection con = dataSource.getConnection();	
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				int	my_no=0, chk_no=1, room_no=0, out_no=-99;
				String passwd="", room_nm="";
				if(rs.next()) { 	
						passwd = rs.getString(1); room_nm=rs.getString(2); my_no=rs.getInt(3); 
						room_no = rs.getInt(4); out_no=rs.getInt(5); chk_no=rs.getInt(6);
						rs.close(); pstmt.close(); con.close();} 
				else {	self.getBasicRemote().sendText("4" + " 방 이름을 확인하세요.");
						rs.close(); pstmt.close(); con.close();
						return;}
				if(room_no==out_no) {self.getBasicRemote().sendText("4" + " 강퇴당한 방입니다."); return;}	
				if(my_no > 0) {
					self.getBasicRemote().sendText("4" + " 대기방에서만 다른 방으로 입장이 가능합니다.");
					return;}
				if(chk_no < 1) {
					self.getBasicRemote().sendText("4" + " 정원 초과로 입장이 불가능합니다.");
					return;}
				if(!(passwd.equals("공개방")) && !(passwd.equals(str[str.length-1]))) {
					self.getBasicRemote().sendText("4" + " 비공개방 비밀번호와 일치하지 않습니다.");
					return;}
				
				sql = "update members "
						+ "set room_no = (select room_no from room_list where to_char(room_no)='"+str[1]+"' "
						+ "or room_nm = '"+str[1]+"') where id = '"+name+"'";
				System.out.println(sql);
				con = dataSource.getConnection();
				Statement stmt = con.createStatement();
				int uCnt = stmt.executeUpdate(sql);
				stmt.close(); con.close();
				//System.out.println(str[1] + " --- " + uCnt +"==" + name);
				if(uCnt ==1) {
					self.getBasicRemote().sendText("4" + " " + room_nm +" 방에 입장하셨습니다.");
					sendAllSessionToMessage(self, name, webId, "님이 "+ room_nm +" 방에 입장하셨습니다.");				
				}
			}catch (SQLException | IOException e) {
				e.printStackTrace();
			}	
		}
		
		private void sendAllSessionToMessage(Session self, String id, String webId, String msg) {
			//서버 금칙어 처리
			String[] str = msg.split(" ");		
			int banWord = 0;
			
			try {
				String sql = "select count(*) from van_word where id= '$system$' and word = ?";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				for(int i=0; i<str.length; i++) {
					pstmt.setString(1, str[i]);
					ResultSet rs = pstmt.executeQuery();
					rs.next(); 	
					banWord = rs.getInt(1);	
					rs.close(); 
					if(banWord > 0) {
						self.getBasicRemote().sendText("4" + " " + str[i] + "는 금칙어입니다.");
						pstmt.close(); 	con.close();
						return;
					}
				} 
				pstmt.close(); 	con.close();
			}catch (SQLException | IOException e) {
				e.printStackTrace();
			}		
			try {
				String name="";
				Connection con = dataSource.getConnection();
				for( Session session : WsServer2.sessions) {
					name = clientMap.get(session.getId());
					//개인금칙어 처리
					String sql = "select count(*) from van_word where id='"+name+"' and word =?";
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
					pstmt.close();
					if(banWord > 0)		continue;
					
					//대화차단자 처리
					int blk_cnt = 0;
					sql = "select count(*) from block_list where blk_id='"+id+"' and id='"+name+"'";
					pstmt = con.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					rs.next();
					blk_cnt = rs.getInt(1);
					rs.close(); pstmt.close(); 
					if(blk_cnt == 1)	continue;
					
					//대화방참여자인 경우 //초대중인 사람 //게임참여자
					int room_no1=0, room_no2=0;
					String call_f="", game_f="";
					sql = "select a.room_no, b.room_no, b.call_f, b.game_f "
						+ "from members a, members b where a.id='"+id+"' and b.id='"+name+"'";
					pstmt = con.prepareStatement(sql);
					rs = pstmt.executeQuery();
					if(rs.next()) { 	room_no1 = rs.getInt(1); room_no2 = rs.getInt(2); 
										call_f = rs.getString(3); game_f = rs.getString(4);
										rs.close(); pstmt.close();} 
					else {				rs.close(); pstmt.close(); 
										continue;}
					
					if(room_no1 != room_no2)	continue;
					if(call_f.equals("1"))		continue;
					if(!game_f.equals("0"))		continue;
					
					if (! self.getId().equals(session.getId()))
						session.getBasicRemote().sendText(webId + " " +"["+id+"] "+ msg);
				}
				con.close();
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}		
		}
		
		public String[] checkCall(String name) {
			String[] calls = {"0", " ", "0"};
			try {
				String sql ="select call_f, nvl(call_id, ' '), game_f from members where id = '"+name+"'";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();			
				rs.next(); 
				calls[0] = rs.getString(1); calls[1] = rs.getString(2); calls[2] = rs.getString(3);
				rs.close(); pstmt.close(); con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}	
			return calls;
		}
		
		public static boolean isNumber(String input) {
			  try{Integer.parseInt(input);	  	return true;
			  }catch (NumberFormatException e){ return false;}
		}
		
		public void exitRoom(Session session, String name, String webId, String s) {
			try {
				String sql = "select a.room_no, nvl(b.room_nm, '대기방') " 
						+	 "from members a, room_list b " 
						+    "where a.room_no=b.room_no(+) and id='"+name+"'";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				int	room_no=0;
				String room_nm="";
				rs.next();
				room_no = rs.getInt(1); room_nm = rs.getString(2);
				rs.close(); pstmt.close(); 
				if(room_no == 0 && s.equals("1"))	
					session.getBasicRemote().sendText("4" + " 현재 대기방입니다.");
				else{
					sql = "delete from room_list "  //1인 경우 방삭제
						+ "where (1, room_no, cap_id) = (select count(room_no), max(room_no), '"+name+"' "
						+ "from members where room_no = '"+room_no+"')"; 
					pstmt = con.prepareStatement(sql);
					pstmt.executeUpdate();
					pstmt.close(); //con.close();
					//방장이 나가는 경우 방장승계
					sql = "select a.id, b.cap_id from members a, room_list b where rownum=1 and a.room_no=b.room_no "
						+ "and 1 < (select count(*) from members where room_no = '"+room_no+"') and id != '"+name+"'"; 
					pstmt = con.prepareStatement(sql);
					rs = pstmt.executeQuery();
					String cap_nm="", mbr_nm="";
					if(rs.next()) {
						mbr_nm = rs.getString(1); cap_nm = rs.getString(2);
						rs.close(); pstmt.close(); con.close();} 
					if(name.equals(cap_nm))	changeCap(session, name, webId, "/cap " + mbr_nm);
					if(s.equals("1")) 		sendAllSessionToMessage(session, name, webId, "님이 "+ room_nm +" 방에서 퇴장하셨습니다.");
					
					sql = "update members set room_no = 0 where id = '"+name+"'"; 
					con = dataSource.getConnection();
					pstmt = con.prepareStatement(sql);
					pstmt.executeUpdate();
					pstmt.close(); 
					//방장이 나가는 경우 강퇴자 삭제
					sql = "delete from out_list where cap_id = '"+name+"'"; 
					pstmt = con.prepareStatement(sql);
					pstmt.executeUpdate();
					pstmt.close(); con.close();
				}
			}catch (SQLException | IOException e) {
				e.printStackTrace();
			}	
		}
		
		public void changeCap(Session session, String name, String webId, String s) {
			String[] str = s.split(" ");
		
			try {
				if(str.length != 2) {
					session.getBasicRemote().sendText("4" + " Usage: /cap 승계자명");
					return;	}	
				if(name.equals(str[1])){ session.getBasicRemote().sendText("4" + " 이름을 확인하세요."); return;}
				String sql ="select nvl(cap_id, ' '), b.room_no, c.room_no " 
						+	"from room_list a, members b, members c " 
						+ 	"where a.room_no(+)=b.room_no "
						+	"and b.id='"+str[1]+"' and b.black_f='0' and c.id='"+name+"'";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				int myNo=0, sNo=0;
				String cap_nm = "";
				if(rs.next()){cap_nm = rs.getString(1); sNo = rs.getInt(2); myNo = rs.getInt(3);
							  rs.close(); pstmt.close(); con.close();} 
				else {	session.getBasicRemote().sendText("4" + " 상대방 이름을 확인하세요.");
						rs.close(); pstmt.close(); con.close();
						return;}

				if(!name.equals(cap_nm)) {
					session.getBasicRemote().sendText("4" + " 방장만이 승계시킬 수 있습니다.");	return;
				}else if(myNo==0 || sNo==0) {
					session.getBasicRemote().sendText("4" + " 현재 대기실에 있습니다. 승계시킬 수 없습니다.");	return;
				}else if(myNo != sNo) {
					session.getBasicRemote().sendText("4" + " 다른 대화방에 있습니다. 승계시킬 수 없습니다.");	return;
				}
				sql = "update room_list set cap_id = '"+str[1]+"' "
					+ "where room_no = (select room_no from room_list where cap_id = '"+name+"')";
				con = dataSource.getConnection();
				pstmt = con.prepareStatement(sql);
				int upCnt = pstmt.executeUpdate();
				pstmt.close(); con.close();
				if(upCnt > 0) { 	
					sendAllSessionToMessage(session, str[1], webId, "님이 방장이 되셨습니다.");
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}	
		}
		
		public void outRoom(Session mysess, String name, String webId, String s, String check) {
			String[] str = s.split(" ");
			
			try {
				if(str.length != 2) {
					mysess.getBasicRemote().sendText("4" + " Usage: /out 강퇴자명");
					return;}
				if(name.equals(str[1])){ mysess.getBasicRemote().sendText("4" + " 이름을 확인하세요."); return;}
				String sql ="select nvl(cap_id, ' '), a.room_nm, b.room_no, c.room_no " 
						+	"from room_list a, members b, members c " 
						+ 	"where a.room_no(+)=b.room_no "
						+	"and b.id='"+str[1]+"' and b.black_f='0' and c.id='"+name+"'";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				int myNo=0, outNo=0;
				String cap_nm = "", room_nm="";
				if(rs.next()) { 	
					cap_nm = rs.getString(1); room_nm = rs.getString(2); 
					outNo = rs.getInt(3); myNo = rs.getInt(4);
					rs.close(); pstmt.close(); con.close();} 
				else { 	mysess.getBasicRemote().sendText("4" + " 상대방 이름을 확인하세요.");
					rs.close(); pstmt.close(); con.close();
					return;}
				
				if(myNo==0 || outNo==0) {
					mysess.getBasicRemote().sendText("4" + " 대화방 참가자가 아닙니다. 강퇴시킬 수 없습니다.");	return;
				}else if(myNo != outNo) {
					mysess.getBasicRemote().sendText("4" + " 다른 대화방에 있습니다. 강퇴시킬 수 없습니다.");	return;
				}else if(!name.equals(cap_nm)) {
					mysess.getBasicRemote().sendText("4" + " 방장만이 강퇴시킬 수 있습니다.");	return;}
				sql = "update members set room_no = 0 where id = '"+str[1]+"'";
				con = dataSource.getConnection();
				pstmt = con.prepareStatement(sql);
				int upCnt = pstmt.executeUpdate();
				pstmt.close(); 
				
				if(!check.equals("del")) {
					sql = "insert into out_list values('"+str[1]+"', '"+myNo+"', '"+name+"')";
					pstmt = con.prepareStatement(sql);
					pstmt.executeUpdate();
					pstmt.close(); 
				}
				if(upCnt > 0) {	
					Set<String> k = clientMap.keySet();
					String sId=null;
					
					for(String ks : k ) 
						if(str[1].equals(clientMap.get(ks))) sId = (String)ks;
					
					Basic b2 = null;
					for( Session session : WsServer2.sessions) {
						if (sId.equals(session.getId())) {
							 b2 = session.getBasicRemote();}
					}
					sendAllSessionToMessage(mysess, name, webId, str[1]+"님을 강퇴시켰습니다.");
					b2.sendText("4" + " " + room_nm + " 방에서 강퇴되었습니다.");
				}	
				con.close();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}	
		}
		
		public void myRoomlist(Session session, String name, String webId) {		
			try {
				String sql ="select id, cap_id, room_nm, a.room_no from members a, room_list b "
						+ 	"where a.room_no=b.room_no and black_f='0' "
						+	"and a.room_no = (select room_no from members where id ='"+name+"')";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				String str = webId + " ", cap_nm="", room_nm="";
				int cnt=0, room_no=0;
				while(rs.next()) { 	
						String sId = "\"" + rs.getString(1) + "\"";			
						str = str + "<a id="+sId+ " href=\"javascript:void(0)\" onclick=\"javascript:fclick(4, this.id);\" >" + rs.getString(1) + "<br>";
						cap_nm=rs.getString(2); room_nm=rs.getString(3); room_no=rs.getInt(4); cnt++;}
				if(str.length() > 2 && room_no > 0 && rs.getRow() > 0) {
					session.getBasicRemote().sendText(webId + " " +room_nm+ "(" + cnt + "명) - 방장:"+cap_nm);
					session.getBasicRemote().sendText(str);
					rs.close(); pstmt.close(); con.close();
				}else {
					rs.close(); pstmt.close(); con.close();
					waitlist(session, name, webId);		
				}
			} catch (SQLException | IOException  e) {
				e.printStackTrace();
			}	
		}
		
		public void blockList(Session session, String name, String webId, String s) {
			String[] s1 = s.split(" ");	
			
			try {
				if(s1.length != 2) {	
					session.getBasicRemote().sendText("4" + " Usage: /blk 차단자이름");
					return;}
				String sql = "select sum(cnt) from " +
							 "(select count(*) cnt from block_list where id='"+name+"' and blk_id='"+s1[1]+"'" +
							 "union all select count(*) cnt from members where id='"+s1[1]+"')";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				int cnt = 0;
				rs.next(); 	cnt = rs.getInt(1);
				rs.close(); pstmt.close(); 
				if(cnt==0) session.getBasicRemote().sendText("4" + " " + s1[1] + "는 현재 이용자가 아닙니다.");
				else if(cnt==1) {
					sql = "insert into block_list values('"+name+"', '"+s1[1]+"')";
					pstmt = con.prepareStatement(sql);
					pstmt.executeUpdate();
					pstmt.close(); con.close();
					session.getBasicRemote().sendText("4" + " " + s1[1] + "와의 대화를 차단합니다.");
				}
				else {
					sql = "delete from block_list where id='"+name+"' and blk_id='"+s1[1]+"'";
					PreparedStatement dpstmt = con.prepareStatement(sql);
					dpstmt.executeUpdate();
					dpstmt.close(); con.close();
					session.getBasicRemote().sendText("4" + " " + s1[1] + "와의 차단을 해제합니다.");	
				}
			}catch(SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		
		
		public void roomList(Session session, String id, String webId, String s) {
			
			String[] s1 = s.split(" ");
			String sql = "select room_no||decode(passwd, '', '', 'p'), to_char(room_no,'99')||'.'||room_nm||decode(passwd, '', '[공개방]','[비공개방]')" 
						+ "||'('||(select count(*) from members b where b.room_no=a.room_no and black_f='0')||'/'||FIX_NUM||')'"
						+ "||decode(room_no, (select room_no from members where id='"+id+"'),'-입장','') ";
			String[] addsql = { "from room_list a  order by room_no",
							    "from room_list a  where passwd is null order by room_no",
							    "from room_list a  where passwd is not null order by room_no"};
			try {
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql+addsql[Integer.parseInt(s1[1])]);
				ResultSet rs = pstmt.executeQuery();
				String str = webId + " ";
				int cnt=0;
				while(rs.next()) { 
					String sId = "\"" + rs.getString(1) + "\""; cnt++;
					str = str + "<a id="+sId+ " href=\"javascript:void(0)\" onclick=\"javascript:fclick(3, this.id);\" >" + rs.getString(2) + "<br>"; }
				session.getBasicRemote().sendText(webId + " >> 대화방: " + cnt + " 개");
				if(cnt >0 )		 session.getBasicRemote().sendText(str);	
				rs.close(); pstmt.close(); con.close();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}	
		}
	
		public void waitlist(Session session, String name, String webId) {
			
			String sql =  "select id from members where room_no=0 and  black_f='0'";
			try {
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				String str = webId + " ";
				int cnt=0;
				while(rs.next()) { 	
					String sId = "\"" + rs.getString(1) + "\"";
					str = str + "<a id=" +sId+ " href=\"javascript:void(0)\" onclick=\"javascript:fclick(2, this.id);\">" + rs.getString(1) + "<br>"; cnt++;}
				rs.close(); pstmt.close(); con.close();
				session.getBasicRemote().sendText(webId + "  >> 대기실: "+cnt+" 명");
				//if(!str.equals("")) session.getBasicRemote().sendText(webId + " " + str.substring(0, str.length()-2));		
				if(!str.equals("")) session.getBasicRemote().sendText(str);			
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}	
		}
		
		public void banlist(Session session, String name, String webId) {
			
			String sql =  "select word from van_word where id = '"+name+"'";
			try {
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				String str = webId + " ";
				int cnt=0;
				while(rs.next()) { 	
					String sId = "\"" + rs.getString(1) + "\"";
					str = str + "<a id=" +sId+ " href=\"javascript:void(0)\" onclick=\"javascript:fclick(5, this.id);\">" + rs.getString(1) + "<br>"; cnt++;}
				rs.close(); pstmt.close(); con.close();
				session.getBasicRemote().sendText(webId + "  >> 금칙어: "+cnt+" 개");
				//if(!str.equals("")) session.getBasicRemote().sendText(webId + " " + str.substring(0, str.length()-2));		
				if(!str.equals("")) session.getBasicRemote().sendText(str);			
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}	
		}
		
		public void notifyMsg(Session self, String name, String webId, String msg) {
			
			String[] str = msg.split(" ");
			try {
				if(str.length == 1) {
					self.getBasicRemote().sendText("4" + " Usage: /noti 공지내용");
					return;	}
						
				for( Session session : WsServer2.sessions) {
					if (! self.getId().equals(session.getId()))		
						self.getBasicRemote().sendText(webId + " [공지사항] " + msg.substring(6).trim());
				}
			}catch(IOException e) {
					e.printStackTrace();
			}	
		}
		
		public void showList(Session session, String webId) {
			
			Set<String> k = clientMap.keySet();
			try {		
				session.getBasicRemote().sendText(webId + " >> 접속자: " + clientMap.size() + " 명");
				for(String s : k ) {	
					String str = "\"" + clientMap.get(s) + "\"";
					System.out.println(str);
					session.getBasicRemote().sendText(webId + " <a id=" +str + "href=\"javascript:void(0)\" onclick=\"javascript:fclick(1, this.id)\" >" + clientMap.get(s));	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
		public void banWord(Session mysess, String name, String webId, String s){
			String[] str = s.split(" ");
			
			try {
				if(str.length == 1) {
					mysess.getBasicRemote().sendText("4" + " Usage: /ban 금칙어");
					return;}
				String sql = "select count(*) from van_word where id = '$system$' and word='"+str[1]+"'"; 
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				int cnt = 0;
				rs.next(); 	cnt = rs.getInt(1);	rs.close(); pstmt.close(); 
				if(cnt > 0) {
					mysess.getBasicRemote().sendText("4" + " " + str[1] + "는 이미 서버금칙어입니다.");
				}
				else{
					sql = "select count(*) from van_word where id = '"+name+"' and word='"+str[1]+"'"; 
					pstmt = con.prepareStatement(sql);
					rs = pstmt.executeQuery();
					int cnt1 = 0;
					rs.next(); 	cnt1 = rs.getInt(1); rs.close(); pstmt.close(); 
					if(cnt1 == 0) {
						sql = "insert into van_word values('"+name+"','"+str[1]+"')";  
						System.out.println(sql);
						pstmt = con.prepareStatement(sql);
						pstmt.executeUpdate();
						pstmt.close(); con.close();
						mysess.getBasicRemote().sendText("4" + " " + str[1] + "는 금칙어에 포함됩니다.");
					}
					else {
						sql = "delete from van_word where id='"+name+"' and word='"+str[1]+"'";  
						PreparedStatement dpstmt = con.prepareStatement(sql);
						dpstmt.executeUpdate();
						dpstmt.close(); con.close();
						mysess.getBasicRemote().sendText("4" + " " + str[1] + "는 금칙어에서 해제합니다.");
					}
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}		
		}
		
		public void talkToOne(Session mysess, String name, String webId, String msg) {
			
			String[] s1 = msg.split(" ");
			System.out.println(msg);
			try {
				if(s1.length == 1) {
					mysess.getBasicRemote().sendText("4" + " Usage: /to 상대방 [메시지]");
					return;}
				if(name.equals(s1[1])){ mysess.getBasicRemote().sendText("4" + " 이름을 확인하세요."); return;}
				String blk_nm = "";
				int myNo=0, toNo=0;
				String sql = "select nvl(blk_id,' '), b.room_no, c.room_no "
						+	 "from block_list a, members b, members c "
						+	 "where a.id(+)=b.id and b.id='"+s1[1]+"' and c.id='"+name+"'";
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {  	blk_nm=rs.getString(1); toNo=rs.getInt(2); myNo=rs.getInt(3);
									rs.close(); pstmt.close(); con.close();} 
				else { 	mysess.getBasicRemote().sendText("4" + " 상대방 이름을 확인하세요.");
						return;}
				Set<String> k = clientMap.keySet();
				String sId=null;
				
				for(String s : k ) 
					if(s1[1].equals(clientMap.get(s))) sId = (String)s;
				
				Basic b2 = null;
				for( Session session : WsServer2.sessions) {
					if (sId.equals(session.getId())) {
						 b2 = session.getBasicRemote();}
				}
				if(!(name.equals(blk_nm)) && myNo == toNo) {
//					String s2 = "(귓속말)" + nm + "=>" + s.substring(nm.length()+2+ s1[0].length()+s1[1].length()+2);
					b2.sendText(webId + " [" + name +"] (귓속말) " + msg.substring(name.length()+5).trim());		
				}
				
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}	
		}
		
		@OnError
		public void onError(Throwable e, Session session) {
			e.printStackTrace();
		}
}
