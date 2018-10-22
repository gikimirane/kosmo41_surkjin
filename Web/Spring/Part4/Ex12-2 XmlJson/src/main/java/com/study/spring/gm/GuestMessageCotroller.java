package com.study.spring.gm;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class GuestMessageCotroller {
	
	@RequestMapping(value="/list.xml")
	@ResponseBody
	public GuestMessageXMLList listxml() {
				
		return getMessageXmlList();
	}
	
	private GuestMessageXMLList getMessageXmlList() {
			
		List<GuestMessage> messages = Arrays.asList(
				new GuestMessage(1, "홍길동", new Date()),
				new GuestMessage(2, "전우치", new Date())
				);
		return new GuestMessageXMLList(messages);
	}
	
	@RequestMapping(value="/list.json")
	@ResponseBody
	public List<GuestMessage> listJson() {
		
		List<GuestMessage> messages = Arrays.asList(
				new GuestMessage(1, "메시지", new Date()),
				new GuestMessage(2, "메시지2", new Date())
				);
			return messages;
	}
	
//	public GuestMessageJSONList listJson() {
//				
//		return getMessageJsonList();
//	}
//	
//	private GuestMessageJSONList getMessageJsonList() {
//			
//		List<GuestMessage> messages = Arrays.asList(
//				new GuestMessage(1, "메시지", new Date()),
//				new GuestMessage(2, "메시지2", new Date())
//				);
//		return new GuestMessageJSONList(messages);
//	}
	
	
}
