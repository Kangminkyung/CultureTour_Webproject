package com.spring.project.member.model;

public class MemberVO {

	private String idx;
	private String userid;
	private String name;
	private String pwd;
	private String email;
	private String hp1;
	private String hp2;
	private String hp3;
	private String post;
	private String addr1;
	private String addr2;
	private String registerday;
	private String status;
	private String point;
	private String quiz;
	private String quizgrade;
	
	public MemberVO() {}

	public MemberVO(String idx, String userid, String name, String pwd, String email, String hp1, String hp2,
			String hp3, String post, String addr1, String addr2, String registerday, String status, String point,
			String quiz, String quizgrade) {
		super();
		this.idx = idx;
		this.userid = userid;
		this.name = name;
		this.pwd = pwd;
		this.email = email;
		this.hp1 = hp1;
		this.hp2 = hp2;
		this.hp3 = hp3;
		this.post = post;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.registerday = registerday;
		this.status = status;
		this.point = point;
		this.quiz = quiz;
		this.quizgrade = quizgrade;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHp1() {
		return hp1;
	}

	public void setHp1(String hp1) {
		this.hp1 = hp1;
	}

	public String getHp2() {
		return hp2;
	}

	public void setHp2(String hp2) {
		this.hp2 = hp2;
	}

	public String getHp3() {
		return hp3;
	}

	public void setHp3(String hp3) {
		this.hp3 = hp3;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getRegisterday() {
		return registerday;
	}

	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getQuiz() {
		return quiz;
	}

	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}

	public String getQuizgrade() {
		
		String grade="";
		if(quizgrade.equals("1")) {
			grade= "초급";
		}
		else if(quizgrade.equals("2")) {
			grade= "중급";
		}
		else if(quizgrade.equals("3")) {
			grade= "고급";
		}
		
		return grade;
		
	}

	public void setQuizgrade(String quizgrade) {
		this.quizgrade = quizgrade;
	}
	
	public String getAddr() {
		return this.addr1+" "+this.addr2;
	}
	
	public String getPhone() {
		return this.hp1+"-"+this.hp2+"-"+this.hp3;
		
		
	}
	
	public String getAllHp() {
		return hp1+"-"+hp2+"-"+hp3;
	}
	
	public String getAllPost() {
		return post;
	}
	
	public String getAllAddr() {
		return addr1+" "+addr2;
	}
	
}
