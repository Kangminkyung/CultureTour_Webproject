package com.spring.project.band.model;

public class BandImageVO {

	private String bandimageseq;  // 밴드추가이미지 일련번호(Primary Key)
	private String fk_bcode;      //밴드번호(Foreign Key)
	private String imagefilename; //이미지파일명. WAS에 저장될 파일명(2016082545435345464367524654634.png)
	private String imageorgFilename; //진짜 이미지파일명(쉐보레우측.png) // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
	private String imagefileSize;  //파일크기
	private String thumbnailFileName;
	
	public BandImageVO() {}
	public BandImageVO(String bandimageseq, String fk_bcode, String imagefilename, String imageorgFilename,
			String imagefileSize, String thumbnailFileName) {
		
		this.bandimageseq = bandimageseq;
		this.fk_bcode = fk_bcode;
		this.imagefilename = imagefilename;
		this.imageorgFilename = imageorgFilename;
		this.imagefileSize = imagefileSize;
		this.thumbnailFileName = thumbnailFileName;
	}
	public String getBandimageseq() {
		return bandimageseq;
	}
	public void setBandimageseq(String bandimageseq) {
		this.bandimageseq = bandimageseq;
	}
	public String getFk_bcode() {
		return fk_bcode;
	}
	public void setFk_bcode(String fk_bcode) {
		this.fk_bcode = fk_bcode;
	}
	public String getImagefilename() {
		return imagefilename;
	}
	public void setImagefilename(String imagefilename) {
		this.imagefilename = imagefilename;
	}
	public String getImageorgFilename() {
		return imageorgFilename;
	}
	public void setImageorgFilename(String imageorgFilename) {
		this.imageorgFilename = imageorgFilename;
	}
	public String getImagefileSize() {
		return imagefileSize;
	}
	public void setImagefileSize(String imagefileSize) {
		this.imagefileSize = imagefileSize;
	}
	public String getThumbnailFileName() {
		return thumbnailFileName;
	}
	public void setThumbnailFileName(String thumbnailFileName) {
		this.thumbnailFileName = thumbnailFileName;
	}
	
	
}
