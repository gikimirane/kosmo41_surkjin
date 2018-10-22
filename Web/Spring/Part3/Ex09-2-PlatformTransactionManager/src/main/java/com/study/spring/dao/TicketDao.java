package com.study.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.study.spring.dto.TicketDto;

public class TicketDao {
	
	JdbcTemplate template = null;
	PlatformTransactionManager transactionManager;
	
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	public void TicketDao() {
		System.out.println(template);
	}
	
	public void buyTicket(final TicketDto dto) {
		System.out.println("buyTicket()");
		System.out.println("dto.getConsumerId() : "+dto.getConsumerId());
		System.out.println("dto.getAmount() : "+dto.getAmount());
		
		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(definition);
		
		try {
			
			template.update(new PreparedStatementCreator(){
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					String query = "insert into card (consumerId,amount) values (?,?)";
					PreparedStatement pstmt = con.prepareStatement(query);
					pstmt.setString(1, dto.getConsumerId());
					pstmt.setString(2, dto.getAmount());
					
					return pstmt;
				}
			});
			
			template.update(new PreparedStatementCreator() {
	
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					String query = "insert into ticket (consumerId,countnum) values (?,?)";
					PreparedStatement pstmt = con.prepareStatement(query);
					pstmt.setString(1, dto.getConsumerId());
					pstmt.setString(2, dto.getAmount());
					return pstmt;
				}
			});
			transactionManager.commit(status);
		}catch(Exception e) {
			e.getMessage();
			transactionManager.rollback(status);
		}
	}
}
