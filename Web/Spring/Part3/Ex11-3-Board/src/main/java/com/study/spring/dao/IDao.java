package com.study.spring.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.study.spring.dto.BDto;

@Repository
public interface IDao {

	public void write (String bName, String bTitle, String bContent);
	public ArrayList<BDto> list(int Page1, int Page2);
	public BDto content_view (String bId);
	public void modify (int bId, String bName, String bTitle, String bContent);
	public void upHit (String bId);
	public void delete (String bId);
	public BDto reply_view (String bId);
	public void reply (String bName, String bTitle, 
			String bContent, int bGroup, int bStep, int bIndent);
	public void replyShape (int sGroup,  int sStep);
	public int acticlePage(int curPage);
}