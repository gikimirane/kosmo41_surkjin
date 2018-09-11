package com.study.jsp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BDao {

	private static BDao instance = new BDao();
	DataSource dataSource = null;
	int listCount = 10;
	int pageCount = 10;
	
	private BDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static BDao getInstance() {
		return instance;
	}
	
	public void write (String bName, String bTitle, String bContent, String brdStr) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql1 = "insert into mvc_board " + 
					 "(board, bid, bname, bTitle, bcontent, bgroup, bstep, bindent) " +
					 " values ";
		String[] sql2 = {"(?, mvc_board_seq.nextval, ?, ?, ?, mvc_board_seq.currval, 0, 0)",
						 "(?, mvc_board_seq2.nextval, ?, ?, ?, mvc_board_seq.currval2, 0, 0)",
						 "(?, mvc_board_seq3.nextval, ?, ?, ?, mvc_board_seq.currval3, 0, 0)"};
		String board="";
		if(brdStr.equals("게시판"))			board = "1";
		else if(brdStr.equals("공지사항"))	board = "2";
		else								board = "3";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql1+sql2[Integer.parseInt(board)-1]);
			pstmt.setString(1, board);
			pstmt.setString(2, bName);
			pstmt.setString(3, bTitle);
			pstmt.setString(4, bContent);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(pstmt, con);
		}
	}
	
	public ArrayList<BDto> list(int curPage, String brdStr) {
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * " + 
					 "from (select rownum num, A.* " +
					 	   "from (select * from mvc_board " + 
					 	   		 "where board=? order by bgroup desc, bstep asc) A " +
					 	   "where rownum <= ?) B " +
					 "where B.num >= ?";		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board(brdStr));
			pstmt.setInt(2, curPage*listCount);
			pstmt.setInt(3, (curPage-1)*listCount + 1);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle  = rs.getString("bTitle");
				String bContent  = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, 
											bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(rs, pstmt, con);
		}
		return dtos;
	}
	
	public BpageInfo acticlePage(int curPage, String brdStr) {
				
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select count(*) as total from mvc_board where board=?";
		int totalCount = 0;	
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board(brdStr));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalCount = rs.getInt("total");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(rs, pstmt, con);
		}

		int totalPage = totalCount / listCount;

		if (totalCount % listCount > 0) totalPage++;

		int myCurPage = curPage;
		if (myCurPage > totalPage)	myCurPage = totalPage;
		if (myCurPage < 1)			myCurPage = 1;

		int startPage = ((curPage - 1) / pageCount) * pageCount + 1;
		int endPage = startPage + pageCount - 1;
		if (endPage > totalPage)     endPage = totalPage;

		BpageInfo pinfo = new BpageInfo(totalCount, listCount, totalPage, 
								myCurPage, pageCount, startPage, endPage);
		
		return pinfo;
	}
	
	public BDto content_view (String strID, String brdStr) {
		
		upHit(strID, brdStr);
		
		BDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from mvc_board " +
					 "where bId = ? and board=?";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(strID));
			pstmt.setString(2, board(brdStr));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle  = rs.getString("bTitle");
				String bContent  = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, 
										bHit, bGroup, bStep, bIndent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(rs, pstmt, con);
		}
		return dto;
	}
	
	public void modify (String bId, String bName, String bTitle, String bContent, String brdStr) 
	{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update mvc_board " + 
					 "set bName=?, bTitle=?, bContent=? where bId=? and board=?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
			pstmt.setString(5, board(brdStr));	
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(pstmt, con);
		}
	}
	
	public void upHit (String bId, String brdStr) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update mvc_board " + 
					 "set bHit = bHit+1 where bId=? and board=?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(bId));
			pstmt.setString(2, board(brdStr));
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(pstmt, con);
		}
	}
	
	public void delete (String bId, String brdStr) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "delete from mvc_board where bId=? and board=?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(bId));
			pstmt.setString(2, board(brdStr));
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(pstmt, con);
		}
	}
	
	public BDto reply_view (String strID, String brdStr) {
		
		BDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from mvc_board where bId = ? and board=?";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(strID));
			pstmt.setString(2, board(brdStr));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle  = rs.getString("bTitle");
				String bContent  = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, 
										bHit, bGroup, bStep, bIndent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(rs, pstmt, con);
		}
		return dto;
	}
	
	public void reply (String bId, String bName, String bTitle, String bContent,
					   String bGroup, String bStep, String bIndent, String brdStr) {
		
		replyShape(bGroup, bStep, brdStr);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql1 = "insert into mvc_board " + 
					 "(board, bid, bname, bTitle, bcontent, bgroup, bstep, bindent) " +
					 " values ";
		String[] sql2 = {"(?, mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)",
						 "(?, mvc_board_seq2.nextval, ?, ?, ?, ?, ?, ?)",
						 "(?, mvc_board_seq3.nextval, ?, ?, ?, ?, ?, ?)",};
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql1 + sql2[Integer.parseInt(board(brdStr))]);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep) + 1);
			pstmt.setInt(6, Integer.parseInt(bIndent) +1);
			pstmt.setString(7, board(brdStr));
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(pstmt, con);
		}
	}
	
	public void replyShape (String sGroup, String sStep, String brdStr) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update mvc_board " + 
					 "set bStep = bStep +1 " + 
					 "where bGroup = ? and bStep > ? and board=?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(sGroup));
			pstmt.setInt(2, Integer.parseInt(sStep));
			pstmt.setString(3, board(brdStr));
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(pstmt, con);
		}
	}
	
	public void oraClose (PreparedStatement pstmt, Connection con) {
		try {
			if( pstmt != null) pstmt.close();
			if( con  != null) con.close();
		}catch(Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void oraClose (ResultSet rs, PreparedStatement pstmt, Connection con) {
		try {
			if (rs != null) rs.close();
			if( pstmt != null) pstmt.close();
			if( con  != null) con.close();
		}catch(Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public String board(String brdStr) {
		String board = "";
		if(brdStr.equals("게시판"))			board = "1";
		else if(brdStr.equals("공지사항"))	board = "2";
		else								board = "3";
		
		return board;
	}
	
			
}