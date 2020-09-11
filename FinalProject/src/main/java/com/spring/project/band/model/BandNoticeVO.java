package com.spring.project.band.model;

import org.springframework.web.multipart.MultipartFile;

// ==== #53-1. VO 생성하기 ==== //
public class BandNoticeVO {
	
	private String seq; // 글번호
	private String fk_bcode; // 밴드번호
	private String userid; // 사용자ID
	private String name; // 글쓴이
	private String subject; // 글제목
	private String content; // 글내용 
	private String pw; // 글암호
	private String readCount; // 글조회수
	private String regDate; // 글쓴시간
	private String status; // 글삭제여부  1:사용가능한글,  0:삭제된글 
	
	public BandNoticeVO() {}
	
	public BandNoticeVO(String seq, String fk_bcode, String userid, String name, String subject, String content, String pw, String readCount,
			String regDate, String status) {
		this.seq = seq;
		this.fk_bcode = fk_bcode;
		this.userid = userid;
		this.name = name;
		this.subject = subject;
		this.content = content;
		this.pw = pw;
		this.readCount = readCount;
		this.regDate = regDate;
		this.status = status;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	public String getFk_bcode() {
		return fk_bcode;
	}

	public void setFk_bcode(String fk_bcode) {
		this.fk_bcode = fk_bcode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getReadCount() {
		return readCount;
	}

	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
		
}
