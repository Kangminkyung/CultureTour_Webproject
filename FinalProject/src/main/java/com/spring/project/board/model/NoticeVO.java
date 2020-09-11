package com.spring.project.board.model;

public class NoticeVO {
	
	private String seq;
	private String userid;
	private String subject;
	private String content;
	private String writeday;
	private String status;
	
	public NoticeVO() {}
	
	public NoticeVO(String seq, String userid, String subject, String content, String writeday, String status) {
		super();
		this.seq = seq;
		this.userid = userid;
		this.subject = subject;
		this.content = content;
		this.writeday = writeday;
		this.status = status;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriteday() {
		return writeday;
	}

	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
