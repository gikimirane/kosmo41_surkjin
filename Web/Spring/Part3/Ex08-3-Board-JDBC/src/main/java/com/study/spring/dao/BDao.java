package com.study.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.study.spring.dto.BDto;
import com.study.spring.util.Constant;

public class BDao {

//	private static BDao instance = new BDao();
	DataSource dataSource = null;

	JdbcTemplate template = null;
	
	public BDao() {
		template = Constant.template;
	}
	
//	public static BDao getInstance() {
//		return instance;
//	}
	
	public void write (final String bName, final String bTitle, final String bContent) {
		
		template.update(new PreparedStatementCreator(){
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) 
						throws SQLException{
				String sql = "insert into mvc_board " + 
						 "(bid, bname, bTitle, bcontent, bgroup, bstep, bindent) " +
						 " values "+
						 "(mvc_board_seq.nextval, ?, ?, ?, mvc_board_seq.currval, 0, 0)";
			
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				//pstmt.executeUpdate();
				
				return pstmt;
			}
		});	
	}
	
	public ArrayList<BDto> list() {
		
		String sql = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent " + 
                	 "from mvc_board order by bGroup desc, bStep asc";
		return (ArrayList<BDto>)template.query(sql, new BeanPropertyRowMapper<BDto>(BDto.class));	
	}
	
	public BDto content_view (String strID) {
		
		upHit(strID);
		
		String sql = "select * from mvc_board where bId = '" + strID + "'";			
		return template.queryForObject(sql, new BeanPropertyRowMapper<BDto>(BDto.class));
	}
	
	public void modify (final String bId, final String bName, final String bTitle, final String bContent) 
	{
		String sql = "update mvc_board " + 
				 "set bName=?, bTitle=?, bContent=? where bId=?";
		template.update(sql, new PreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException{	
		
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, Integer.parseInt(bId));
			}
		});
	}
	
	public void upHit (final String bId) 
	{
		String sql = "update mvc_board set bHit = bHit+1 where bId=?";
		template.update(sql, new PreparedStatementSetter(){		
			@Override
			public void setValues(PreparedStatement ps) throws SQLException{	
				ps.setInt(1, Integer.parseInt(bId));
			}
		});
	}
	
	public void delete (String bId) 
	{
		String sql = "delete from mvc_board where bId=" + bId;
		template.update(sql);
//		template.update(sql, new PreparedStatementSetter(){	
//			@Override
//			public void setValues(PreparedStatement ps) throws SQLException{	
//				ps.setString(1, bId);
//			}
//		});
	}
	
	public BDto reply_view (String strID) {
		
		String sql = "select * from mvc_board where bId = " + strID;
		return (BDto)template.query(sql, new BeanPropertyRowMapper<BDto>(BDto.class));
		//return template.queryForObject(sql, new BeanPropertyRowMapper<BDto>(BDto.class));
	}
	
	public void reply (final String bId, final String bName, final String bTitle, 
			final String bContent, final String bGroup, final String bStep, final String bIndent) {
		
		replyShape(bGroup, bStep);
		
		String sql = "insert into mvc_board " + 
				 "(bid, bname, bTitle, bcontent, bgroup, bstep, bindent) " +
				 " values "+
				 "(mvc_board_seq.nextval,'" +bName+ "','" +bTitle+ "','" +bContent+ "'," +bGroup+ "," +bStep+ "," +bIndent+ ")";
		template.update(sql);
		
//		template.update(new PreparedStatementCreator(){
//			
//			@Override
//			public PreparedStatement createPreparedStatement(Connection con) 
//						throws SQLException{
//				String sql = "insert into mvc_board " + 
//						 "(bid, bname, bTitle, bcontent, bgroup, bstep, bindent) " +
//						 " values "+
//						 "(mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
//				PreparedStatement pstmt = con.prepareStatement(sql);
//				pstmt.setString(1, bName);
//				pstmt.setString(2, bTitle);
//				pstmt.setString(3, bContent);
//				pstmt.setInt(4, Integer.parseInt(bGroup));
//				pstmt.setInt(5, Integer.parseInt(bStep) + 1);
//				pstmt.setInt(6, Integer.parseInt(bIndent) +1);
//				
//				return pstmt;
//			}
//		});
	}
	
	public void replyShape (String sGroup,  String sStep) 
	{
		String sql = "update mvc_board " + 
					 "set bStep = bStep +1 " + 
					 "where bGroup = " +sGroup+  " and bStep > " + sStep;
		template.update(sql);
//		template.update(sql, new PreparedStatementSetter(){		
//			@Override
//			public void setValues(PreparedStatement pstmt) throws SQLException{	
//				pstmt.setInt(1, Integer.parseInt(sGroup));
//				pstmt.setInt(2, Integer.parseInt(sStep));
//			}
//		});
	}		
}