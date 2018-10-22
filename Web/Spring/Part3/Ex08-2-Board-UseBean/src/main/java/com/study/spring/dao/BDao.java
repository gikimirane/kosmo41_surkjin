package com.study.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import com.study.spring.dto.BDto;

public class BDao {

	private static BDao instance = new BDao();
	DataSource dataSource = null;
	int listCount = 10;
	int pageCount = 10;
	
	public BDao() {
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
	
	public void write (String bName, String bTitle, String bContent) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into mvc_board " + 
					 "(bid, bname, bTitle, bcontent, bgroup, bstep, bindent) " +
					 " values "+
					 "(mvc_board_seq.nextval, ?, ?, ?, mvc_board_seq.currval, 0, 0)";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(pstmt, con);
		}
	}
	
	public ArrayList<BDto> list() {
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent " + 
                	 "from mvc_board " +
				     "order by bGroup desc, bStep asc";

		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
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
	
//	public BpageInfo acticlePage(int curPage) {
//				
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		String sql = "select count(*) as total from mvc_board";
//		int totalCount = 0;	
//		try {
//			con = dataSource.getConnection();
//			pstmt = con.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				totalCount = rs.getInt("total");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			oraClose(rs, pstmt, con);
//		}
//
//		int totalPage = totalCount / listCount;
//
//		if (totalCount % listCount > 0) totalPage++;
//
//		int myCurPage = curPage;
//		if (myCurPage > totalPage)	myCurPage = totalPage;
//		if (myCurPage < 1)			myCurPage = 1;
//
//		int startPage = ((curPage - 1) / pageCount) * pageCount + 1;
//		int endPage = startPage + pageCount - 1;
//		if (endPage > totalPage)     endPage = totalPage;
//
//		BpageInfo pinfo = new BpageInfo(totalCount, listCount, totalPage, 
//								myCurPage, pageCount, startPage, endPage);
//		
//		return pinfo;
//	}
	
	public BDto content_view (String strID) {
		
		upHit(strID);
		
		BDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from mvc_board " +
					 "where bId = ?";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(strID));
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
	
	public void modify (String bId, String bName, String bTitle, String bContent) 
	{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update mvc_board " + 
					 "set bName=?, bTitle=?, bContent=? where bId=?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(pstmt, con);
		}
	}
	
	public void upHit (String bId) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update mvc_board " + 
					 "set bHit = bHit+1 where bId=?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(bId));
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(pstmt, con);
		}
	}
	
	public void delete (String bId) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "delete from mvc_board where bId=?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(bId));
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(pstmt, con);
		}
	}
	
	public BDto reply_view (String strID) {
		
		BDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from mvc_board where bId = ?";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(strID));
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
					   String bGroup, String bStep, String bIndent) {
		
		replyShape(bGroup, bStep);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into mvc_board " + 
					 "(bid, bname, bTitle, bcontent, bgroup, bstep, bindent) " +
					 " values "+
					 "(mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep) + 1);
			pstmt.setInt(6, Integer.parseInt(bIndent) +1);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oraClose(pstmt, con);
		}
	}
	
	public void replyShape (String sGroup, String sStep) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update mvc_board " + 
					 "set bStep = bStep +1 " + 
					 "where bGroup = ? and bStep > ?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(sGroup));
			pstmt.setInt(2, Integer.parseInt(sStep));
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
			
}