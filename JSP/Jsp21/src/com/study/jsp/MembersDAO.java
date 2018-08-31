package com.study.jsp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MembersDAO {

	public static final int MEMBER_NONEXIT = 0;
	public static final int MEMBER_EXIT = 1;
	public static final int MEMBER_JOIN_FAIL = 0;
	public static final int MEMBER_JOIN_SUCESS = 1;
	public static final int MEMBER_LOGIN_PW_NO = 0;
	public static final int MEMBER_LOGIN_SUCESS  = 1;
	public static final int MEMBER_LOGIN_IS_NOT  = -1;
	
	private static MembersDAO instance = new MembersDAO();
	
	private MembersDAO() {
	}
	
	public static MembersDAO getInstance() {
		return instance;
	}
	
	public int insertMembers (MembersDTO dto) {
		int ri = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into members values (?, ?, ?, ?, ?, ?)";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.geteMail());
			pstmt.setTimestamp(5, dto.getrDate());
			pstmt.setString(6, dto.getAddress());
			pstmt.executeUpdate();
			ri= MembersDAO.MEMBER_JOIN_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if( pstmt != null) pstmt.close();
				if( con  != null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return ri;
	}

	public int confirmId(String id) {
		int ri =0;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		String sql = "select id from members where id = ?";
	
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				ri = MembersDAO.MEMBER_EXIT;
			} else {
				ri = MembersDAO.MEMBER_NONEXIT;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if( pstmt != null) pstmt.close();
				if( con  != null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return ri;
	}
	
	public int userCheck (String id, String pw) {
		int ri = 0;
		String dbPw;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select pw from members where id = ?";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbPw = rs.getString("pw");
				if(dbPw.equals(pw)) {
					System.out.println("login OK");
					ri = MembersDAO.MEMBER_LOGIN_SUCESS;
				} else {
					System.out.println("login Fail");
					ri = MembersDAO.MEMBER_LOGIN_PW_NO;
				}
			} else {
				System.out.println("login Fail");
				ri = MembersDAO.MEMBER_LOGIN_IS_NOT;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if( pstmt != null) pstmt.close();
				if( con  != null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
		}
	}
	
	return ri;
	}
	
	public MembersDTO getMember(String id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from members where id = ?";
		MembersDTO dto = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new MembersDTO();
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));
				dto.seteMail(rs.getString("eMail"));
				dto.setrDate(rs.getTimestamp("rDate"));
				dto.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if( pstmt != null) pstmt.close();
				if( con  != null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	
	public int updateMember(MembersDTO dto) {
		int ri =0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update members set pw=?, eMail=?, address=? where id = ?";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getPw());
			pstmt.setString(2, dto.geteMail());
			pstmt.setString(3, dto.getAddress());
			pstmt.setString(4, dto.getId());
			
			ri = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if( pstmt != null) pstmt.close();
				if( con  != null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return ri;
	}
	
	private Connection getConnection() {
		Context context = null;
		DataSource dataSource = null;
		Connection con = null;
		
		try {
			context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
			con = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
}