package com.study.spring;

import com.study.spring.dto.BPageInfo;

public class MyMethod {
	
	public BPageInfo articlePage(int curPage,int totalCount) {
		
		int listCount=5;
		int pageCount=5;
		
		//총 페이지 수
		int totalPage;
		if(totalCount%listCount>0) {
			totalPage = (totalCount/listCount)+1;
		}else {
			totalPage = totalCount/listCount;
		}
		
		//현재 페이지
		int myCurPage = curPage;
		if(myCurPage>totalPage) 
			myCurPage = totalPage;
		if(myCurPage<1) 
			myCurPage=1;
		
		int startPage = ((myCurPage-1)/pageCount)*pageCount+1;
		
		
		int endPage = startPage+pageCount-1;
		if(endPage > totalPage) 
			endPage = totalPage;
		
		BPageInfo pinfo = new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		pinfo.setTotalPage(totalPage);
		pinfo.setListCount(listCount);
						
		return pinfo;
	}
}
