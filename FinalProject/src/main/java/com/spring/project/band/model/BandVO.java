package com.spring.project.band.model;

import org.springframework.web.multipart.MultipartFile;

public class BandVO {

	private String bcode;   //밴드코드(시퀀스)
	private String bname;   //밴드이름
	private String badmin;  //밴드관리자 (밴드만든멤버아이디, final_member userid 참조)
	private String bthema;   //밴드 테마코드
	private String binfo;    //밴드정보소개
	private String bmembercnt;   //밴드참가인원
	private String bregdate; //밴드생성일

	private String status; // 밴드삭제여부(1은 존재, 0은 삭제)
	
/*	private String fileName; // WAS(톰캣)에 저장될 파일명(20161121324325454354353333432.png)
	private String orgFilename; // 진짜 파일명(강아지.png)   
							    // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명
	private String fileSize; // 파일크기
	
	private MultipartFile attach; // 진짜 파일 ==> WAS(톰캣)디스크에 저장됨.
								  // 오라클 데이터베이스 tblBoard테이블의 컬럼이 아니다.
*/	
	
	public BandVO() {}
	
	public BandVO(String bcode, String bname, String badmin, String bthema, String binfo, String bmembercnt, String bregdate, String status) {
		
		this.bcode = bcode;
		this.bname = bname;
		this.badmin = badmin;
		this.bthema = bthema;
		this.binfo = binfo;
		this.bmembercnt = bmembercnt;
		this.bregdate = bregdate;
	
		this.status = status;
		/*this.fileName = fileName;
		this.orgFilename = orgFilename;
		this.fileSize = fileSize;*/
		
	}
	
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getBadmin() {
		return badmin;
	}
	public void setBadmin(String badmin) {
		this.badmin = badmin;
	}
	public String getBthema() {
		return bthema;
	}
	public void setBthema(String bthema) {
		this.bthema = bthema;
	}
	public String getBinfo() {
		return binfo;
	}
	public void setBinfo(String binfo) {
		this.binfo = binfo;
	}
	public String getBmembercnt() {
		return bmembercnt;
	}
	public void setBmembercnt(String bmembercnt) {
		this.bmembercnt = bmembercnt;
	}
	public String getBregdate() {
		return bregdate;
	}
	public void setBregdate(String bregdate) {
		this.bregdate = bregdate;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
