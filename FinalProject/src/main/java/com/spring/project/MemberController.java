package com.spring.project;


import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.spring.common.MyUtil;
import com.spring.mail.GoogleMail;
import com.spring.project.member.model.MemberVO;
import com.spring.project.member.service.InterMemberService;





@Controller
@Component
public class MemberController {
	
	@Autowired
	private InterMemberService service;

	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;
	@Autowired
	private OAuth2Parameters googleOAuth2Parameters;
	
		
	@RequestMapping(value="/login.action", method= {RequestMethod.GET})
	public String login(HttpServletRequest req) {
		
		
		String url = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=eQANcuilpNUa_vw3_tsb&redirect_uri=http%3A%2F%2Flocalhost%3A9090%2Fproject%2Fcallback.action&state=c1a79561-b58c-453f-a2a2-563f4a04ed4c";
		
		req.setAttribute("url", url);
		
		
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String googleurl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
		
		
		
		
		
		
		req.setAttribute("google_url", googleurl);
			
		return "member/login.tiles";
	}
	

	//LoginEnd.action
	
	@RequestMapping(value="/loginEnd.action", method= {RequestMethod.POST})
	public String loginEnd(HttpServletRequest req,HttpServletResponse res) {	
		
		String userid = req.getParameter("userid");
		String pwd = req.getParameter("pwd");		
		String saveid = req.getParameter("saveid");
		
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("userid", userid);
		map.put("pwd",pwd);		
		MemberVO loginuser = service.getLoginMember(map);
		if(loginuser !=null) {
			HttpSession session = req.getSession();	
			session.setAttribute("loginuser", loginuser);	
			String gobackURL = (String)session.getAttribute("gobackurl");
			req.setAttribute("gobackURL", gobackURL);
			session.removeAttribute("gobackurl");	
			
			Cookie cookie = new Cookie("saveid", loginuser.getUserid());
			// 로그인하는 사용자의 아이디값을 saveid 라는 이름의 키값으로 쿠키객체를 생성한다.
			
			if(saveid != null) {
				// 40번 라인에서 받은 것이 null 이 아닌 "on" 이라면
				cookie.setMaxAge(7*24*60*60); // 쿠키의 생존기간을 7일(단위 초)로 설정한다. 쿠키저장          
			}
			else {
				// 40번 라인에서 받은 것이 null 이라면
				cookie.setMaxAge(0); // 쿠키의 생존기간을 0초로 한다. 즉, 이것인 쿠키 삭제이다.
			}
			
			cookie.setPath("/");
			/*
			   쿠키가 사용되어질 디렉토리 정보 설정.
			  cookie.setPath("사용되어질 경로"); 
			   만일 "사용되어질 경로" 가 "/" 일 경우
			   해당 도메인(예 iei.or.kr)의 모든 페이지에서 사용가능하다. 
			 */
			
			res.addCookie(cookie);
			
			
		}		
		
		
		return "member/loginEnd.tiles";				
	}
	
	
	@RequestMapping(value="/logout.action", method= {RequestMethod.GET})
	public String logout(HttpServletRequest req) {	
				
		HttpSession session = req.getSession();
		//session.removeAttribute("loginuser");
		session.invalidate();
		
		
		return "member/logout.tiles";		
	}
	
	
	@RequestMapping(value="/memberform.action", method= {RequestMethod.GET})
	public String memberform(HttpServletRequest req) {
	
		return "member/memberform.tiles";
	}
	
	// 아이디 중복검사
	@RequestMapping(value="/idcheck.action", method= {RequestMethod.GET})
	public String idcheck(HttpServletRequest req) {
		
		String userid = req.getParameter("userid");
		if(userid != null) {			
			int isUseuserid = service.idDuplicateCheck(userid);
			JSONObject jsonObject = new JSONObject();			
			jsonObject.put("cnt",isUseuserid);				
			req.setAttribute("cnt", jsonObject);
			
		}
		
		return "idDuplicateCheck.notiles";		
	}
	
	@RequestMapping(value="/memberRegisterEnd.action", method= {RequestMethod.POST})
	public String memberRegisterEnd(HttpServletRequest req) {
	
		String method = req.getMethod();
		
		if(!"post".equalsIgnoreCase(method)) {
			// POST 방식으로 넘어온 것이 아니라면
			
			String msg = "비정상적인 경로로 들어왔습니다.";
			String loc = "javascript:history.back();";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
		}
		else {
		
			String name = req.getParameter("name");
			String userid = req.getParameter("userid");
			String pwd = req.getParameter("pwd");
			String email = req.getParameter("email");
			String hp1 = req.getParameter("hp1");
			String hp2 = req.getParameter("hp2");
			String hp3 = req.getParameter("hp3");
			String post = req.getParameter("post");
			String addr1 = req.getParameter("addr1");
			String addr2 = req.getParameter("addr2");	
			
			MemberVO membervo = new MemberVO();
			
			membervo.setName(name);
			membervo.setUserid(userid);
			membervo.setPwd(pwd);
			membervo.setEmail(email);
			membervo.setHp1(hp1);
			membervo.setHp2(hp2);
			membervo.setHp3(hp3);
			membervo.setPost(post);
			membervo.setAddr1(addr1);
			membervo.setAddr2(addr2);

			int n = service.registerMember(membervo); 
			
			if(n>0) {
			
				HttpSession session = req.getSession();	
				session.setAttribute("n", n);	
				String gobackURL = (String)session.getAttribute("gobackurl");
				req.setAttribute("gobackURL", gobackURL);

				session.removeAttribute("gobackurl");

			}
	
		} // end of if~else-------------------------
		
		return "member/memberRegisterEnd.tiles";
	}
		
	// 아이디 찾기
	// idFind.action
	@RequestMapping(value="/idFind.action",method= {RequestMethod.GET})
	public String idFind(HttpServletRequest req,HttpServletResponse res) {
			
		String method = req.getMethod();		
		req.setAttribute("method", method);			
						
		if("post".equalsIgnoreCase(method)) {
			// 아이디 찾기 모달창에서 찾기 버튼을 클릭했을 경우
			String name = req.getParameter("name");
			String mobile = req.getParameter("mobile");
				
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("name", name);
			map.put("mobile", mobile);
				
			String userid = service.getUserid(map); 
				
				if(userid != null) {
					req.setAttribute("userid", userid);
				}
				else {
					req.setAttribute("userid", "존재하지 않습니다.");
				}
			
			req.setAttribute("name", name);
			req.setAttribute("mobile", mobile);
			}
			
		return "idFind.notiles";
			
	}
	
	// 비밀번호 찾기를 위해 먼저 userid 와 email 을 가지는 사용자가 존재하는지 검증해주는 폼 생성
	// pwdFind.action
	@RequestMapping(value="/pwdFind.action",method= {RequestMethod.GET})
	public String pwdFind(HttpServletRequest req,HttpServletResponse res) {
		
		String method = req.getMethod();
		
		req.setAttribute("method", method);
		
		if("post".equalsIgnoreCase(method)) {
			// 비밀번호 찾기 모달창에서 찾기 버튼을 클릭했을 경우
			String userid = req.getParameter("userid");
			String email = req.getParameter("email");
			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("userid", userid);
			map.put("email", email);
			
			int n = service.isUserExists(map); 
			
			if(n==1) {
				
				GoogleMail mail = new GoogleMail();
				
				Random rnd = new Random();
				
				String certificationCode = "";
				// certificationCode ==> "ewtyq0452029"
				
				char randchar = ' ';
				for(int i=0; i<5; i++) {
					// min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면
					// int rndnum = rnd.nextInt(max - min + 1) + min;  
					randchar = (char)(rnd.nextInt('z' - 'a' + 1) + 'a');
					certificationCode += randchar;
				}
				
				int randnum = 0;
				for(int i=0; i<7; i++) {
					randnum = (int)(rnd.nextInt(10-0+1)+0);
					certificationCode += randnum;
				}
				
				try {
					mail.sendmail(email, certificationCode);
					req.setAttribute("certificationCode", certificationCode);
					
				} catch(Exception e) {
					e.printStackTrace();
					
					req.setAttribute("sendFailmsg", "메일발송에 실패했습니다.");
					n = -1;
					
				}
		//		System.out.println(n);
			}
			
			req.setAttribute("n", n);  
			// n이 0이면 존재하지 않은 userid 또는 email 인 경우
			// n이 1이면 userid 와 email 존재하면서 메일발송이 성공한 경우
			// n이 -1이면 userid 와 email 존재하는데 메일발송이 실패한 경우
			
			req.setAttribute("userid", userid);
			req.setAttribute("email", email);
		}
		return "pwdFind.notiles";
		
	}
	
	// 암호를 새암호로 변경하는 폼 생성
	// pwdConfirm.action
	@RequestMapping(value="/pwdConfirm.action",method= {RequestMethod.POST})
	public String pwdConfirm(HttpServletRequest req,HttpServletResponse res) {
		
		String method = req.getMethod();
		req.setAttribute("method", method);
		
	    String userid =	req.getParameter("userid");
	    req.setAttribute("userid", userid);
	    
	    HashMap<String,String> map = new HashMap<String,String>();
	    
	    if("POST".equalsIgnoreCase(method)) {
	    	String pwd = req.getParameter("pwd");
	    	
	    	map.put("userid", userid);
	    	map.put("pwd", pwd);
	    	
	    	int n = 0;
	    	if(userid != null && pwd != null) {
	    		n = service.updatePwdUser(map);
	    	}
	    	
	    	req.setAttribute("n", n);
	    }
		
		return "pwdConfirm.notiles";
		
	}	
	
	////////////////////////////////////////////////////////////////////////
	// 관리자 로그인 했을때 
	
	@RequestMapping(value="/memberlist.action", method= {RequestMethod.GET})
	public String requireAdmin_memberlist(HttpServletRequest req,HttpServletResponse res) {
			
		String currentURL = MyUtil.getCurrentURL(req);
		
		req.setAttribute("currentURL", currentURL);
		// 회원 조회/삭제/복구/변경 시 현재 그페이지로 그대로 머물기 위한 용도임. 
			    
		HashMap<String,String> map = new HashMap<String,String>();
		
	    String searchName = req.getParameter("searchName");
	    String searchType = req.getParameter("searchType");
		String str_currentShowPageNo = req.getParameter("currentShowPageNo");
	    String str_sizePerPage = req.getParameter("sizePerPage"); 
	    if(searchType==null) {
	    	searchType="name";
	    }
	
	    if(searchName == null)   // 초기화면
	    {
	    	searchName = "";
	    }
	    
	    if(str_sizePerPage == null)  // 초기화면
	    	str_sizePerPage = "5";
	    
	    int sizePerPage = 5;
	    
	    req.setAttribute("sizePerPage", sizePerPage);
	    // 3. 전체 페이지 갯수 알아오기
	    // -- 총 회원갯수를 알아오기
	    HttpSession session = req.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	    
		if(loginuser == null) {
			   // 로그인을 하지 않은 경우
			   String msg = "관리자로  로그인 하셔야 합니다.";
			   String loc = "index.action";
			   
			   req.setAttribute("msg", msg);
			   req.setAttribute("loc", loc);			   
			   
			   return "msg.notiles";
		   }
		
		
		int totalMemberCount = 0;
	    int startRno = 0;
	    int endRno = 0 ;
	
	    int currentShowPageNo = 0; // 사용자가 보고자 하는 페이지번호    
	    
	 
	    map.put("searchName", searchName);
	    map.put("searchType", searchType);
	    
	//    System.out.println("확인용 = "+searchName);
	 //   System.out.println("확인용 searchType = " + searchType);
	    
	    

	//	totalMemberCount = mdao.getTotalCountWithDel(searchName); 회원총원을 구하는 서비스단.
		 
		totalMemberCount = service.gettotalmember(map);
		
	    req.setAttribute("totalMemberCount", totalMemberCount); 
//	    System.out.println("totalMemberCount : "+totalMemberCount);
	    
	    int totalPage = (int)Math.ceil((double)totalMemberCount / sizePerPage);   
	    
        req.setAttribute("totalPage", totalPage);
  //      System.out.println("totalPage : "+totalPage);
	    
	
			    
	    if(str_currentShowPageNo == null || str_currentShowPageNo.trim().isEmpty() ) {
	    	currentShowPageNo = 1;
	    	// 초기화면은 현재페이지 번호로 1 페이지로 설정한다.
	    }
	    else {
	    	try{
	    		currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
	        	// 사용자가 보고자하는 페이지번호를 클릭했을 때
	        	// 페이지번호를 현재페이지로 설정함.
	        	
	        	if(currentShowPageNo < 1 || currentShowPageNo > totalPage) { // totalPage 토탈페이지가 위에잇어야함.
	        		currentShowPageNo = 1;
	        	}
	        	
	    	}catch(NumberFormatException e) {
	    		currentShowPageNo = 1;
	    	}
	    	
	    }
	    
	    // 4. memberDao 객체변수를 사용하여  jsp_member 테이블에 
	    //    저장된 모든 데이터를 불러와서 List 타입의 변수에 저장한다.
		   		   
	    // List<MemberVO> memberList = mdao.getSearchMembers(currentShowPageNo, sizePerPage,searchName); 회원들 모두가져와서 리스트<membervo> 에저장
	
	    
	    
	    startRno = (currentShowPageNo - 1) * sizePerPage + 1;
		endRno = startRno + sizePerPage - 1;
	    
		
	//	System.out.println("startRno = " + startRno);
	//	System.out.println("endRno = " + endRno);
		
		
	    map.put("startRno", String.valueOf(startRno));
	    map.put("endRno", String.valueOf(endRno));
	    
	    List<MemberVO> memberList = service.getMemberlist(map);
	    
	    // 5. 위에서 불러온 데이터가 저장된 memberList 를  VIEW단 페이지로 넘겨주면 된다.
		   req.setAttribute("memberList", memberList); // 서비스단 에서 멤버 리스트를받아와서 뷰단에뿌려줌
		   
		   req.setAttribute("currentShowPageNo", currentShowPageNo); 
		   // VIEW단 페이지에 현재 페이지를 출력하기 위한 용도(확인용임!!)
	
		//   System.out.println("currentShowPageNo : "+currentShowPageNo);
		   
        // 6. 페이징 처리된 페이지바를 생성하여 VIEW 단 페이지로 넘겨준다. 
		// **** 페이지바 만들기 **** //
		  int blockSize = 10;
		  
		  String url = "memberlist.action";
		  
		  String pageBar ="<ul>";
		   pageBar += MyUtil.getSearchPageBar(url, currentShowPageNo, sizePerPage, totalPage, blockSize,searchType, searchName);
		  pageBar +="</ul>";
		  req.setAttribute("pageBar", pageBar);
		  
		//  System.out.println("pageBar : " + pageBar);
		  
		return "member/memberList.tiles";
		
	}
	
	//naverRegister.action
	// 네이버 아이디로 회원가입
	@RequestMapping(value="/naverRegister.action", method= {RequestMethod.POST})
	public String naverRegister(HttpServletRequest req) {
	
		
		
		
		String method = req.getMethod();
		
		if(!"post".equalsIgnoreCase(method)) {
			// POST 방식으로 넘어온 것이 아니라면
			
			String msg = "비정상적인 경로로 들어왔습니다.";
			String loc = "javascript:history.back();";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
		}
		else {
		
			String name = req.getParameter("name");
			String userid = req.getParameter("userid");
			String pwd = req.getParameter("pwd");
			String email = req.getParameter("userid");
			
			MemberVO membervo = new MemberVO();			
	
		//	System.out.println("name : "+name);
		//	System.out.println("userid : "+userid);
		//	System.out.println("pwd : "+pwd);
		//	System.out.println("email : "+email);
			
			
			membervo.setName(name);
			membervo.setUserid(userid);
			membervo.setPwd(pwd);
			membervo.setEmail(email);		
			int n = service.NaverRegisterMember(membervo); 	
	
		
			if(n>0) {			
			
				HashMap<String,String> map = new HashMap<String,String>();
	
				map.put("userid", userid);
				map.put("pwd",pwd);
				
				HttpSession session = req.getSession(); 
				MemberVO loginuser = service.getLoginMember(map);
				session.setAttribute("loginuser", loginuser);
				
				return "main/index.tiles";
				
			
			}
			
	
		} // end of if~else-------------------------
		
		return "msg.notiles";
	}
	
	// 회원 정보 수정 페이지 출력
		@RequestMapping(value="/memberEdit.action",method= {RequestMethod.GET})
		public String memberEdit(HttpServletRequest req, HttpServletResponse res) {
		
			HttpSession session = req.getSession();
			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
			String idx = req.getParameter("idx");
		
		 	if(loginuser == null){
		 	   // 로그인을 하지 않은 상태에서는 볼수가 없도록 해야 한다.
		 	   // 로그인을 했지만 다른 사용자의 정보는 변경이 불가하도록 해야 한다.

		 		String msg = "비정상적인 경로로 들어왔습니다.";
		 		String loc = "javascript:history.back();";
		 		
		 		req.setAttribute("msg", msg);
		 		req.setAttribute("loc", loc);

		 	}
		 	else if(loginuser != null ) {
		 		
		 		HashMap<String,String> map = new HashMap<String,String>();
				map.put("idx", idx);
				
		//		System.out.println("idx 확인용: " + map);
				MemberVO membervo = null;	    
			    membervo = service.getMemberOneByIdx(map);
			    
		 		session.setAttribute("mvo", membervo);
							
		 	}
		 	return "member/memberEdit.tiles";
	}
		
		// 회원 정보 수정 여부 처리
		// memberEdit.action
		@RequestMapping(value="/memberEditEnd.action",method= {RequestMethod.POST})
		public String memberEditEnd(HttpServletRequest req,HttpServletResponse res) {
			
			String idx = req.getParameter("idx");			
			String name = req.getParameter("name");
			String userid = req.getParameter("userid");
			String pwd = req.getParameter("pwd");
			String email = req.getParameter("email");
			String hp1 = req.getParameter("hp1");
			String hp2 = req.getParameter("hp2");
			String hp3 = req.getParameter("hp3");
			String post = req.getParameter("post");
			String addr1 = req.getParameter("addr1");
			String addr2 = req.getParameter("addr2"); 
			
			MemberVO membervo = new MemberVO();
			
			membervo.setIdx(idx);
			membervo.setName(name);
			membervo.setUserid(userid);
			membervo.setPwd(pwd);
			membervo.setEmail(email);
			membervo.setHp1(hp1);
			membervo.setHp2(hp2);
			membervo.setHp3(hp3);
			membervo.setPost(post);
			membervo.setAddr1(addr1);
			membervo.setAddr2(addr2);
			
			int n = service.updateMember(membervo); 
			
			// 로그인한 회원이 관리자가 아닌 일반회원일 경우 나의정보를 클릭해서 자신의 회원정보를 수정한 이후에
			// 로그인 되어진 이름이 바로 변경되어지도록
			// 세션에 저장된 loginuser 의 정보값을 수정한 membervo 로 변경시킨다.
			HttpSession session = req.getSession();
			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
			String loginuserid = loginuser.getUserid();
			
			if(!"admin".equals(loginuserid) && n>0 ) {
				membervo.setUserid(loginuserid);
				session.setAttribute("loginuser", membervo);
			}
			
			if(n>0) {
				
				session = req.getSession();	
				session.setAttribute("n", n);	
				String gobackURL = (String)session.getAttribute("gobackurl");
				req.setAttribute("gobackURL", gobackURL);

				session.removeAttribute("gobackurl");

			}
					
			return "memberEditEnd.notiles";								
		}
		
		// 회원상세페이지
		// memberDetail.action
		@RequestMapping(value="/memberDetail.action",method= {RequestMethod.GET})
		public String memberDetail(HttpServletRequest req,HttpServletResponse res) {
			
			String idx = req.getParameter("idx");
			String goBackURL = req.getParameter("goBackURL");
			//String goBackURL = req.getParameter("goBackURL");	
			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("idx", idx);
			
		//	System.out.println("idx 확인용: " + map);
			MemberVO membervo = null;	    
		    membervo = service.getMemberOneByIdx(map);
		    
			if(membervo != null) {					
				HttpSession session = req.getSession();	
				session.setAttribute("membervo", membervo);	
				String gobackURL = (String)session.getAttribute("gobackurl");
				req.setAttribute("gobackURL", gobackURL);
				session.removeAttribute("gobackurl");
			}		
		   	return "member/memberDetail.tiles";
		}
		@RequestMapping(value="/memberDetailJSON.action",method= {RequestMethod.GET})
		public String memberDetailJSON(HttpServletRequest req,HttpServletResponse res) {
			
			String idx = req.getParameter("idx");
			String goBackURL = req.getParameter("goBackURL");
			//String goBackURL = req.getParameter("goBackURL");	
			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("idx", idx);
			
		//	System.out.println("idx 확인용: " + map);
			MemberVO membervo = null;	    
		    membervo = service.getMemberOneByIdx(map);
		    
			if(membervo != null) {					
				HttpSession session = req.getSession();	
				session.setAttribute("membervo", membervo);	
				String gobackURL = (String)session.getAttribute("gobackurl");
				req.setAttribute("gobackURL", gobackURL);
				session.removeAttribute("gobackurl");
				
				
				
			}	
			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("idx", membervo.getIdx());
			jsonObj.put("name", membervo.getName());
			jsonObj.put("userid", membervo.getUserid());
			jsonObj.put("email", membervo.getEmail());
			jsonObj.put("phone", membervo.getAllHp());
			jsonObj.put("post", membervo.getPost());
			jsonObj.put("addr", membervo.getAddr());
			jsonObj.put("quizgrade", membervo.getQuizgrade());
			jsonObj.put("registerday", membervo.getRegisterday());
	
			
			
			
			req.setAttribute("jsonObject", jsonObj);
			
			
		   	return "memberDetailJSON.notiles";
		}
		
		
		
		// 회원 탈퇴 하는 폼
				// memberDelete.action
				@RequestMapping(value="/memberDelete.action",method= {RequestMethod.GET})
				public String memberDelete(HttpServletRequest req,HttpServletResponse res) {
					
					HttpSession session = req.getSession();
					MemberVO loginuser = (MemberVO)session.getAttribute("loginuser"); 

					String idx = req.getParameter("idx");
					
			    //	System.out.println("==> 확인용 goBackURL : " + goBackURL);
				// ==> 확인용 goBackURL : memberList.do?currentShowPageNo=19&sizePerPage=3&searchType=name&searchWord=%EA%B8%B8%EB%8F%99&period=-1
					
					int n = service.deleteMember(idx); 
				    
				    if(n>0) {				
						session = req.getSession();	
						session.setAttribute("n", n);	
						String gobackURL = (String)session.getAttribute("gobackurl");
						req.setAttribute("gobackURL", gobackURL);
						session.removeAttribute("gobackurl");
					} else if(n==1) {
						session.removeAttribute("loginuser");				
					}
				    
				    session.invalidate();

					return "member/memberDelete.tiles";			
				}
	
	
		//kakaoDuplicate.action
		@RequestMapping(value="/kakaoDuplicate.action",method={RequestMethod.POST})
		public String kakaoDuplicate(HttpServletRequest req) {
			
			
			//var data_form ={"userid":id,pwd:nickname,email:email};
			
			String userid = req.getParameter("userid");
			String pwd = req.getParameter("pwd");
		
			
			//System.out.println("확인용 kakao : "+userid);
			//System.out.println("확인용 kakao : "+pwd);
		
			
			int isUserable = service.idDuplicateCheck(userid);
			//System.out.println("카카오 : "+isUserable);
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("cnt", isUserable);
			
			req.setAttribute("cnt", jsonObj);
			
			return "kakoDuplicateJSON.notiles";
		}
		
/*		  var frm = document.kakaoFrm;
  		  frm.id.value=id;
  		  frm.email.value=email;
  		  frm.nickname.value=nickname;
  		  frm.method="POST";
  		  frm.action="kakaoRegister.action";
  		  frm.submit();		   

	*/
		//kakaoRegister.action
		@RequestMapping(value="/kakaoRegister.action",method={RequestMethod.POST})
		public String kakaoRegister(HttpServletRequest req) {
	
			
			String name = req.getParameter("nickname");
			String userid = req.getParameter("id");
			String pwd = req.getParameter("nickname");
			String email = req.getParameter("email");
			
			MemberVO membervo = new MemberVO();			
	
		//	System.out.println("name : "+name);
		//	System.out.println("userid : "+userid);
		//	System.out.println("pwd : "+pwd);
		//	System.out.println("email : "+email);
			
			
			membervo.setName(name);
			membervo.setUserid(userid);
			membervo.setPwd(pwd);
			membervo.setEmail(email);		
			int n = service.NaverRegisterMember(membervo); 	
	
		
			if(n>0) {			
			
				HashMap<String,String> map = new HashMap<String,String>();
	
				map.put("userid", userid);
				map.put("pwd",pwd);
				
				HttpSession session = req.getSession(); 
				MemberVO loginuser = service.getLoginMember(map);
				session.setAttribute("loginuser", loginuser);
				
				return "main/index.tiles";				
			
			}
			return "main/index.tiles";		
		
		}
		
		//googleRegister.action
		@RequestMapping(value="/googleRegister.action",method={RequestMethod.POST})
		public String googleRegister(HttpServletRequest req) {
	
			/*<input type="hidden" name="userid" id="userid"/>
			<input type="hidden" name="pwd" id="pwd"/>
			<input type="hidden" name="name" id="name"/>*/
			
			String name = req.getParameter("name");
			String userid = req.getParameter("userid");
			String pwd = req.getParameter("pwd");
			String email = req.getParameter("userid");
			
			MemberVO membervo = new MemberVO();			
	
		//	System.out.println("name : "+name);
		//	System.out.println("userid : "+userid);
		//	System.out.println("pwd : "+pwd);
		//	System.out.println("email : "+email);
			
			
			membervo.setName(name);
			membervo.setUserid(userid);
			membervo.setPwd(pwd);
			membervo.setEmail(email);		
			int n = service.NaverRegisterMember(membervo); 	
	
		
			if(n>0) {			
			
				HashMap<String,String> map = new HashMap<String,String>();
	
				map.put("userid", userid);
				map.put("pwd",pwd);
				
				HttpSession session = req.getSession(); 
				MemberVO loginuser = service.getLoginMember(map);
				session.setAttribute("loginuser", loginuser);
				
				return "main/index.tiles";				
			
			}
			return "main/index.tiles";		
		
		}
		
		
	
}
