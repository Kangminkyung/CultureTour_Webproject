package com.spring.project.band.model;

import org.springframework.web.multipart.MultipartFile;

public class PhotoVO {
	
	private String seq;
	private String fk_bcode;
	private String userid;
	private String fileName;
	private String orgFilename;
	private String fileSize;
	private String regDate;
	private String status;
	
	private MultipartFile attach;
	
	public PhotoVO() {}

	public PhotoVO(String seq, String fk_bcode, String userid, String fileName, String orgFilename, String fileSize,
			String regDate, String status) {
		this.seq = seq;
		this.fk_bcode = fk_bcode;
		this.userid = userid;
		this.fileName = fileName;
		this.orgFilename = orgFilename;
		this.fileSize = fileSize;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOrgFilename() {
		return orgFilename;
	}

	public void setOrgFilename(String orgFilename) {
		this.orgFilename = orgFilename;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
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
	
	public MultipartFile getAttach() {
		return attach;
	}

	public void setAttach(MultipartFile attach) {
		this.attach = attach;
	}
	
}