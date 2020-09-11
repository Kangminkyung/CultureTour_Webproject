package com.spring.project.band.model;

// ==== #82. 댓글쓰기용 VO 생성하기 ====
// 먼저 오라클에서 tblComment테이블을 생성한다.
// 또한, tblBoard 테이블에 commentCount컬럼을 추가한다.

public class PCommentVO {
	
	private String seq; // 댓글번호
	private String userid; // 사용자ID
	private String name; // 성명
	private String content; // 댓글내용
	private String regDate; // 작성일자
	private String parentBcode; // 원게시물 밴드번호
	private String parentPnum; // 원게시물 글번호
	private String status; // 글삭제여부
	                       // 1 : 사용가능한 글,  0 : 삭제된 글
	                       // 댓글은 원글이 삭제되면 자동적으로 삭제되어야 한다.
	
	public PCommentVO() {}
	
	public PCommentVO(String seq, String userid, String name, String content, String regDate, String parentBcode, String parentPnum,
			String status) {
		super();
		this.seq = seq;
		this.userid = userid;
		this.name = name;
		this.content = content;
		this.regDate = regDate;
		this.parentBcode = parentBcode;
		this.parentPnum = parentPnum;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	
	
	public String getParentBcode() {
		return parentBcode;
	}

	public void setParentBcode(String parentBcode) {
		this.parentBcode = parentBcode;
	}

	public String getParentPnum() {
		return parentPnum;
	}

	public void setParentPnum(String parentPnum) {
		this.parentPnum = parentPnum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
