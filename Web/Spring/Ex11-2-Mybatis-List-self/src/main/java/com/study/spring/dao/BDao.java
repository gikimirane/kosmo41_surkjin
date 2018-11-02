package com.study.spring.dao;

import java.util.ArrayList;

import com.study.spring.dto.BDto;
import com.study.spring.dto.BPageInfo;

public interface BDao {
	public ArrayList<BDto> listDao(int end,int start);
	public BDto contentview(String bId);
	public void deleteDao(String bId);
	public void modifyDao(String bName, String bTitle, String bContent, String bId);
	public void writeDao(String bName, String bTitle, String bContent);
	public void replyDao(String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent);
	public void replyShape(String bGroup, String bIndent);
	public void upHit(String bId);
	public int countpage();
}
