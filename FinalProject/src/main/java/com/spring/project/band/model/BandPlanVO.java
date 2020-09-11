package com.spring.project.band.model;

public class BandPlanVO {

	private String fk_bcode;   // 밴드번호
	private String pnum; // 플랜번호
	private String fk_userid; //작성자
	private String pdate; //플랜날짜
	private String ptitle; //일정제목
	private String cultureSearch; //문화재이름
	private String pstime; //출발시간
	private String petime; //도착시간
	private String pmoney; // 예상비용
	private String pcontent;//글내용
	private String pwritedate; // 작성일
	private String status; // 삭제여부
	private String readCount; // 글조회수
	
	public BandPlanVO() {}
	public BandPlanVO(String fk_bcode, String pnum, String fk_userid, String pdate, String ptitle, String cultureSearch,
			String pstime, String petime, String pmoney, String pcontent, String pwritedate, String status, String readCount) {
		
		this.fk_bcode = fk_bcode;
		this.pnum = pnum;
		this.fk_userid = fk_userid;
		this.pdate = pdate;
		this.ptitle = ptitle;
		this.cultureSearch = cultureSearch;
		this.pstime = pstime;
		this.petime = petime;
		this.pmoney = pmoney;
		this.pcontent = pcontent;
		this.pwritedate = pwritedate;
		this.status = status;
		this.readCount = readCount;
	}

	public String getFk_bcode() {
		return fk_bcode;
	}

	public void setFk_bcode(String fk_bcode) {
		this.fk_bcode = fk_bcode;
	}
	
	public String getPnum() {
		return pnum;
	}

	public void setPnum(String pnum) {
		this.pnum = pnum;
	}

	public String getFk_userid() {
		return fk_userid;
	}

	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getPtitle() {
		return ptitle;
	}

	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}

	public String getCultureSearch() {
		return cultureSearch;
	}

	public void setCultureSearch(String cultureSearch) {
		this.cultureSearch = cultureSearch;
	}

	public String getPstime() {
		return pstime;
	}

	public void setPstime(String pstime) {
		this.pstime = pstime;
	}

	public String getPetime() {
		return petime;
	}

	public void setPetime(String petime) {
		this.petime = petime;
	}

	public String getPmoney() {
		return pmoney;
	}

	public void setPmoney(String pmoney) {
		this.pmoney = pmoney;
	}

	public String getPcontent() {
		return pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}
	
	public String getPwritedate() {
		return pwritedate;
	}

	public void setPwritedate(String pwritedate) {
		this.pwritedate = pwritedate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getReadCount() {
		return readCount;
	}
	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}
	
	
}
