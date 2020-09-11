package com.spring.chatting.model;

//=== #173. (웹채팅관련4) === 

import com.google.gson.Gson;

public class MessageVO {

	private String message;
	private String type;   // all 이면 전체 
	private String to;     // 특정 클라이언트 IP Address
	private String group;
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public static MessageVO convertMessage(String source) {
	    MessageVO messagevo = new MessageVO();
	    Gson gson = new Gson();
	    messagevo = gson.fromJson(source, MessageVO.class);
	 
	    return messagevo;
	}

}
