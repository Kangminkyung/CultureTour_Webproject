package com.spring.project.culture.model;

public class CultureVO {
	// 순번
	private int idx;
	// 종목코드
	private String ccbakdcd;
	// 시도코드
	private String ccbactcd;
	// 문화재코드
	private String ccbaasno;
	// 문화재이름
	private String ccmaname;
	// 분류1
	private String gcodename;
	// 분류2
	private String mcodename;
	// 위치
	private String ccbalcad;
	// 시대
	private String cccename;
	// 소유자
	private String ccbaposs;
	// 이미지url
	private String imageurl;
	// 설명
	private String content;
	// 서브이미지1,2,3
	private String subimage1;
	private String subimage2;
	private String subimage3;

	public CultureVO() {

	}

	public CultureVO(int idx, String ccbakdcd, String ccbactcd, String ccbaasno, String ccmaname, String gcodename,
			String mcodename, String ccbalcad, String cccename, String ccbaposs, String imageurl, String content,
			String subimage1, String subimage2, String subimage3) {
		this.idx = idx;
		this.ccbakdcd = ccbakdcd;
		this.ccbactcd = ccbactcd;
		this.ccbaasno = ccbaasno;
		this.ccmaname = ccmaname;
		this.gcodename = gcodename;
		this.mcodename = mcodename;
		this.ccbalcad = ccbalcad;
		this.cccename = cccename;
		this.ccbaposs = ccbaposs;
		this.imageurl = imageurl;
		this.content = content;
		this.subimage1 = subimage1;
		this.subimage2 = subimage2;
		this.subimage3 = subimage3;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getCcbakdcd() {
		
		if(ccbakdcd.equals("11")) {
			ccbakdcd = "국보";
		}else if(ccbakdcd.equals("12")){
			ccbakdcd = "보물";
		}else if(ccbakdcd.equals("13")){
			ccbakdcd = "사적";
		}else if(ccbakdcd.equals("14")){
			ccbakdcd = "사적및명승";
		}else if(ccbakdcd.equals("15")){
			ccbakdcd = "명승";
		}else if(ccbakdcd.equals("16")){
			ccbakdcd = "천연기념물";
		}else if(ccbakdcd.equals("17")){
			ccbakdcd = "국가무형문화재";
		}else if(ccbakdcd.equals("18")){
			ccbakdcd = "국가민속문화재";
		}else if(ccbakdcd.equals("21")){
			ccbakdcd = "시도유형문화재";
		}else if(ccbakdcd.equals("22")){
			ccbakdcd = "시도무형문화재";
		}else if(ccbakdcd.equals("23")){
			ccbakdcd = "시도기념물";
		}else if(ccbakdcd.equals("24")){
			ccbakdcd = "시도민속문화재";
		}else if(ccbakdcd.equals("31")){
			ccbakdcd = "문화재자료";
		}else if(ccbakdcd.equals("79")){
			ccbakdcd = "등록문화재";
		}else if(ccbakdcd.equals("80")){
			ccbakdcd = "이북5도무형문화재";
		}
		
		
		return ccbakdcd;
	}

	public void setCcbakdcd(String ccbakdcd) {
		this.ccbakdcd = ccbakdcd;
	}

	public String getCcbactcd() {
		return ccbactcd;
	}

	public void setCcbactcd(String ccbactcd) {
		this.ccbactcd = ccbactcd;
	}

	public String getCcbaasno() {
		return ccbaasno;
	}

	public void setCcbaasno(String ccbaasno) {
		this.ccbaasno = ccbaasno;
	}

	public String getCcmaname() {
		return ccmaname;
	}

	public void setCcmaname(String ccmaname) {
		this.ccmaname = ccmaname;
	}

	public String getGcodename() {
		return gcodename;
	}

	public void setGcodename(String gcodename) {
		this.gcodename = gcodename;
	}

	public String getMcodename() {
		return mcodename;
	}

	public void setMcodename(String mcodename) {
		this.mcodename = mcodename;
	}

	public String getCcbalcad() {
		return ccbalcad;
	}

	public void setCcbalcad(String ccbalcad) {
		this.ccbalcad = ccbalcad;
	}

	public String getCccename() {
		return cccename;
	}

	public void setCccename(String cccename) {
		this.cccename = cccename;
	}

	public String getCcbaposs() {
		return ccbaposs;
	}

	public void setCcbaposs(String ccbaposs) {
		this.ccbaposs = ccbaposs;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubimage1() {
		return subimage1;
	}

	public void setSubimage1(String subimage1) {
		this.subimage1 = subimage1;
	}

	public String getSubimage2() {
		return subimage2;
	}

	public void setSubimage2(String subimage2) {
		this.subimage2 = subimage2;
	}

	public String getSubimage3() {
		return subimage3;
	}

	public void setSubimage3(String subimage3) {
		this.subimage3 = subimage3;
	}

	
}
