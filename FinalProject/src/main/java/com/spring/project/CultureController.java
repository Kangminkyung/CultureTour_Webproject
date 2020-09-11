package com.spring.project;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.common.MyUtil;
import com.spring.project.culture.model.CultureVO;
import com.spring.project.culture.service.InterCultureService;
import com.spring.project.member.model.MemberVO;

@Controller
@Component
public class CultureController {
	
	@Autowired
	private InterCultureService cultureService;
	
	// 문화재 보기 페이지 이동
	@RequestMapping(value="/showCulture.action", method= {RequestMethod.GET})
	public String showculture(HttpServletRequest req) {
		
		String ccbakdcd = req.getParameter("ccbaKdcd");
	//	System.out.println(ccbakdcd);
		
		List<CultureVO> cultureList = null;
		
		//	boardList = service.boardList(); // 검색어가 없는 경우 전체 조회해주는 것
			
			//**** 돌아갈 페이지를 위해서 현재 페이지를 뷰단으로 넘겨준다.****//
			String gobackURL = MyUtil.getCurrentURL(req);
			req.setAttribute("gobackURL", gobackURL);
			
			// ==== #106. 검색어가 포함되었으므로 
			//      먼저 위의  boardList = service.boardList(); 부분을
			//      주석처리하고서 아래의 작업을 한다. ====
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("ccbakdcd", ccbakdcd);
			
		
			
			// ===== #110. 페이징 처리 하기 =====
			String str_currentShowPageNo = req.getParameter("currentShowPageNo"); 
			
			int totalCount = 0;         // 총게시물건수
			int sizePerPage = 6;        // 한 페이지당 보여줄 게시물 건수 
			int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
			int totalPage = 0;          // 총페이지수 (웹브라우저상에 보여줄 총 페이지 갯수)
			
			int startRno = 0;           // 시작행 번호
			int endRno = 0;             // 끝행 번호
			
			int blockSize = 10;         // "페이지바" 에 보여줄 페이지의 갯수 
			
			//System.out.println("str_currentShowPageNo : " + str_currentShowPageNo);
			/*
			    ==== 총페이지수 구하기 ====    
			        검색조건이 없을 때의 총페이지 수와
			        검색조건이 있을 때의 총페이지 수를 구해야 한다.
			        
			        검색조건이 없을 때의 총페이지 수 ==> colname 과 search 값이 null 인 것이고,     
			 */
			// 먼저 총게시물 건수를 구한다.
			
			//System.out.println("map ccbakdcd : " + ccbakdcd);
			totalCount = cultureService.getCultureTotalCount(map); // 검색어가 없는 총게시물 건수
			//System.out.println("totalCount : " + totalCount);
			
			totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
			
			
			if(str_currentShowPageNo == null) {
				// 게시판 초기화면에 보여지는 것은 
				// req.getParameter("currentShowPageNo"); 값이 없으므로
				// str_currentShowPageNo 는 null 이 된다.
				
				currentShowPageNo = 1; 
			}
			else {
				try {
					currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
					
					if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
						currentShowPageNo = 1;
					}
					
				} catch (NumberFormatException e) {
					currentShowPageNo = 1;
				}
			}
			
			// **** 가져올 게시글의 범위를 구한다.(공식임!!!) ****
			/*
			      currentShowPageNo    startRno    endRno
			      ---------------------------------------
			           1 page      ==>     1          5
			           2 page      ==>     6         10
			           3 page      ==>    11         15
			           4 page      ==>    16         20
			           5 page      ==>    21         25
			           6 page      ==>    26         30
			           7 page      ==>    31         35
			 */
			
			startRno = (currentShowPageNo - 1) * sizePerPage + 1;
			endRno = startRno + sizePerPage - 1;
			
			//System.out.println("startRno : " + startRno);
			//System.out.println("endRno : " + endRno);
			// ==== #111. 페이징 처리를 위해 startRno, endRno 를 map 에 추가하여 
			//            파라미터로 넘겨서 select 되도록 한다. 
			map.put("startRno", String.valueOf(startRno));
			map.put("endRno", String.valueOf(endRno));
			
			
			cultureList = cultureService.culturePagingList(map); // 검색어가 없는 페이징처리
			
			for(int i=0; i< cultureList.size(); i++) {
				//System.out.println("imageurl : " + cultureList.get(i).getImageurl());
			}
			// ==== #113. 페이지바 만들기
			//   (먼저, 페이지바에 나타낼 총페이지 갯수 구해야 하는데 우리는 위에서 구했다.) ===== 
			
			String pagebar = "<ul>"; 
			
			pagebar += MyUtil.getPageBarCulture("showCulture.action", currentShowPageNo, sizePerPage, totalPage, blockSize, ccbakdcd);     
			pagebar += "</ul>";
			
			req.setAttribute("pagebar", pagebar);
			
			req.setAttribute("cultureList", cultureList);
			
		
		return "culture/showCulture.tiles";
	} 
	
	// 퀴즈를 푸는 페이지 이동요청
	@RequestMapping(value="/quiz.action", method= {RequestMethod.GET})
	public String requireLogin_quiz(HttpServletRequest req,HttpServletResponse res) {		
		
		return "culture/quiz.tiles";
	} 
	
	//addWishCulture.action
		@RequestMapping(value="addWishCulture.action",method= {RequestMethod.POST})
		public String requireLogin_addWishCulture(HttpServletRequest req,HttpServletResponse res) {

			String idx = req.getParameter("idx");
			String userid = req.getParameter("userid");
			

			HashMap<String,String> map = new HashMap<String,String>();
			map.put("idx",idx);
			map.put("userid",userid);
			
			//wish 중복되는 문화재 확인
			int existIdx = cultureService.countWishIdxCulture(map);
			
			String msg = "";
			String loc = "";
			//wish 중복되는 문화재가 없을시
			if(existIdx==0) {
				int n = cultureService.addWishCulture(map);
				
				if(n==1) {
					 msg = "위시 문화재 추가 성공!";
					 loc = "javascript:history.back();";
				}else {
					 msg = "위시 문화재 추가 실패!";
					 loc = "javascript:history.back();";
				}	
			}
			//wish 중복되는 문화재가 있다면
			else {
				msg = "위시 문화재는 중복이 안됩니다.!!!!";
				loc = "javascript:history.back();";
			}
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			return "msg.notiles";
		}

	// 문화재 상세 페이지 이동요청
	@RequestMapping(value="cultureDetail.action",method= {RequestMethod.GET})
	public String cultureDetail(HttpServletRequest req) {
		
		String idx = req.getParameter("idx");
		
		HttpSession session = req.getSession();
		session.setAttribute("Countseachculture", "yes");
		
		//System.out.println("확인용 : idx "+idx);		
		
		CultureVO culturevo = cultureService.getCultureDetail(idx);
		
		
		req.setAttribute("culturevo", culturevo);
		
		
		return "culture/cultureDetail.tiles";
	}
	
	// 문화재 검색
		@RequestMapping(value="cultureSearch.action",method= {RequestMethod.GET})
		public String cultureSearch(HttpServletRequest req) {
			
			String cultureSearchWord = req.getParameter("frmCultureSearch");
			
		//	System.out.println("cultureSearchWord 확인용 : " + cultureSearchWord);		
			
			req.setAttribute("cultureSearchWord", cultureSearchWord);
			
			HttpSession session = req.getSession();
			
			if("yes".equals(session.getAttribute("Countseachculture"))) {
				
			int n = cultureService.getcultureCount(cultureSearchWord); // 문화재 검색 카운트
			
			session.removeAttribute("Countseachculture");
			}
			List<CultureVO> cultureList = null;
			
			//	boardList = service.boardList(); // 검색어가 없는 경우 전체 조회해주는 것
				
				//**** 돌아갈 페이지를 위해서 현재 페이지를 뷰단으로 넘겨준다.****//
				String gobackURL = MyUtil.getCurrentURL(req);
				req.setAttribute("gobackURL", gobackURL);
				
				// ==== #106. 검색어가 포함되었으므로 
				//      먼저 위의  boardList = service.boardList(); 부분을
				//      주석처리하고서 아래의 작업을 한다. ====
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("cultureSearchWord", cultureSearchWord);
				
			
				
				// ===== #110. 페이징 처리 하기 =====
				String str_currentShowPageNo = req.getParameter("currentShowPageNo"); 
				
				int totalCount = 0;         // 총게시물건수
				int sizePerPage = 6;        // 한 페이지당 보여줄 게시물 건수 
				int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
				int totalPage = 0;          // 총페이지수 (웹브라우저상에 보여줄 총 페이지 갯수)
				
				int startRno = 0;           // 시작행 번호
				int endRno = 0;             // 끝행 번호
				
				int blockSize = 10;         // "페이지바" 에 보여줄 페이지의 갯수 
				
				//System.out.println("str_currentShowPageNo : " + str_currentShowPageNo);
				/*
				    ==== 총페이지수 구하기 ====    
				        검색조건이 없을 때의 총페이지 수와
				        검색조건이 있을 때의 총페이지 수를 구해야 한다.
				        
				        검색조건이 없을 때의 총페이지 수 ==> colname 과 search 값이 null 인 것이고,     
				 */
				// 먼저 총게시물 건수를 구한다.
				
				//System.out.println("map ccbakdcd : " + ccbakdcd);
				totalCount = cultureService.getCultureSearchTotalCount(map); // 검색어가 없는 총게시물 건수
				//System.out.println("totalCount : " + totalCount);
				
				totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
				
				
				if(str_currentShowPageNo == null) {
					// 게시판 초기화면에 보여지는 것은 
					// req.getParameter("currentShowPageNo"); 값이 없으므로
					// str_currentShowPageNo 는 null 이 된다.
					
					currentShowPageNo = 1; 
				}
				else {
					try {
						currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
						
						if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
							currentShowPageNo = 1;
						}
						
					} catch (NumberFormatException e) {
						currentShowPageNo = 1;
					}
				}
				
				// **** 가져올 게시글의 범위를 구한다.(공식임!!!) ****
				/*
				      currentShowPageNo    startRno    endRno
				      ---------------------------------------
				           1 page      ==>     1          5
				           2 page      ==>     6         10
				           3 page      ==>    11         15
				           4 page      ==>    16         20
				           5 page      ==>    21         25
				           6 page      ==>    26         30
				           7 page      ==>    31         35
				 */
				
				startRno = (currentShowPageNo - 1) * sizePerPage + 1;
				endRno = startRno + sizePerPage - 1;
				
				//System.out.println("startRno : " + startRno);
				//System.out.println("endRno : " + endRno);
				// ==== #111. 페이징 처리를 위해 startRno, endRno 를 map 에 추가하여 
				//            파라미터로 넘겨서 select 되도록 한다. 
				map.put("startRno", String.valueOf(startRno));
				map.put("endRno", String.valueOf(endRno));
				
				//cultureService.getCultureSearch(cultureSearchWord);
				cultureList = cultureService.getCultureSearch(map); // 검색어가 없는 페이징처리
				
				for(int i=0; i< cultureList.size(); i++) {
					//System.out.println("imageurl : " + cultureList.get(i).getImageurl());
				}
				// ==== #113. 페이지바 만들기
				//   (먼저, 페이지바에 나타낼 총페이지 갯수 구해야 하는데 우리는 위에서 구했다.) ===== 
				
				String pagebar = "<ul>"; 
				
				pagebar += MyUtil.getPageBarSearchCulture("cultureSearch.action", currentShowPageNo, sizePerPage, totalPage, blockSize, cultureSearchWord);     
				pagebar += "</ul>";
				
				req.setAttribute("pagebar", pagebar);
				
				req.setAttribute("cultureList", cultureList);
			
			return "culture/cultureSearch.tiles";
		}
	
		
	
	
	//quizDeatil.action
	@RequestMapping(value="quizDeatil.action",method= {RequestMethod.GET})
	public String quizDeatil(HttpServletRequest req) {

		
		return "quizDetail";
	}
	
	
	//gradeUpdate.action
	@RequestMapping(value="/gradeUpdate.action",method= {RequestMethod.POST})
	public String gradeUpdate(HttpServletRequest req) {

		String userid = "";
		HttpSession  session =  req.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("loginuser");
		userid = mvo.getUserid();
		
		String grade = mvo.getQuizgrade();
		
		if(!grade.equals("고급")) {
			int n = cultureService.updateGrade(userid);		
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("n", n);
			req.setAttribute("n", jsonObject);		
			
			return "gradeUpdate.notiles";
		}else {
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("n", 0);
			req.setAttribute("n", jsonObject);		
			
			return "gradeUpdate.notiles";
		
		}		
	}	
	
	@RequestMapping(value="/quizupdate.action", method= {RequestMethod.POST})
	public String quizupdate(HttpServletRequest req) {
	
		String userid = req.getParameter("userid");
		
		//System.out.println("확인용 퀴즈업데이트 액션이 실행됨..");
		
		int n = cultureService.quizupdate(userid);
		
/*		String loc = "culture/quiz.tiles";
		String msg = "퀴즈 start~";	*/	
		
		HashMap<String,String> map = new HashMap<String,String>();
		HttpSession session = req.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginuser");
		String pwd = mvo.getPwd();		
		map.put("userid", userid);
		map.put("pwd", pwd);	
		
		MemberVO loginuser = cultureService.getLoginMember(map);
		
		session.setAttribute("loginuser", loginuser);
		
	/*	
	    req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);
	*/
		
		return "msg.notiles";
		
	} 
	
	// 문화재 위시 페이징 리스트
	@RequestMapping(value="CultureWishList.action",method= {RequestMethod.GET})
	public String CultureWishList(HttpServletRequest req, HttpSession session) {
		
		session = req.getSession();
		
		if(session.getAttribute("loginuser")==null) {
			String msg = "로그인을 하고 이용하실수 있습니다.";
			String loc = "index.action";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			return "msg.notiles";
		}
		
		MemberVO mvo = (MemberVO)session.getAttribute("loginuser");
		String userid=mvo.getUserid();
		//System.out.println("userid : " +userid);
		
		List<HashMap<String,String>> cultureWishList = null;
		
		//	boardList = service.boardList(); // 검색어가 없는 경우 전체 조회해주는 것
			
			//**** 돌아갈 페이지를 위해서 현재 페이지를 뷰단으로 넘겨준다.****//
			String gobackURL = MyUtil.getCurrentURL(req);
			req.setAttribute("gobackURL", gobackURL);
			
			// ==== #106. 검색어가 포함되었으므로 
			//      먼저 위의  boardList = service.boardList(); 부분을
			//      주석처리하고서 아래의 작업을 한다. ====
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("userid", userid);
			
		
			
			// ===== #110. 페이징 처리 하기 =====
			String str_currentShowPageNo = req.getParameter("currentShowPageNo"); 
			
			int totalCount = 0;         // 총게시물건수
			int sizePerPage = 6;        // 한 페이지당 보여줄 게시물 건수 
			int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
			int totalPage = 0;          // 총페이지수 (웹브라우저상에 보여줄 총 페이지 갯수)
			
			int startRno = 0;           // 시작행 번호
			int endRno = 0;             // 끝행 번호
			
			int blockSize = 10;         // "페이지바" 에 보여줄 페이지의 갯수 
			
			//System.out.println("str_currentShowPageNo : " + str_currentShowPageNo);
			/*
			    ==== 총페이지수 구하기 ====    
			        검색조건이 없을 때의 총페이지 수와
			        검색조건이 있을 때의 총페이지 수를 구해야 한다.
			        
			        검색조건이 없을 때의 총페이지 수 ==> colname 과 search 값이 null 인 것이고,     
			 */
			// 먼저 총게시물 건수를 구한다.
			
			
			totalCount = cultureService.getCultureWishTotalCount(map); // 검색어가 없는 총게시물 건수
			//System.out.println("totalCount : " + totalCount);
			
			totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
			
			
			if(str_currentShowPageNo == null) {
				// 게시판 초기화면에 보여지는 것은 
				// req.getParameter("currentShowPageNo"); 값이 없으므로
				// str_currentShowPageNo 는 null 이 된다.
				
				currentShowPageNo = 1; 
			}
			else {
				try {
					currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
					
					if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
						currentShowPageNo = 1;
					}
					
				} catch (NumberFormatException e) {
					currentShowPageNo = 1;
				}
			}
			
			// **** 가져올 게시글의 범위를 구한다.(공식임!!!) ****
			/*
			      currentShowPageNo    startRno    endRno
			      ---------------------------------------
			           1 page      ==>     1          5
			           2 page      ==>     6         10
			           3 page      ==>    11         15
			           4 page      ==>    16         20
			           5 page      ==>    21         25
			           6 page      ==>    26         30
			           7 page      ==>    31         35
			 */
			
			startRno = (currentShowPageNo - 1) * sizePerPage + 1;
			endRno = startRno + sizePerPage - 1;
			
			//System.out.println("startRno : " + startRno);
			//System.out.println("endRno : " + endRno);
			// ==== #111. 페이징 처리를 위해 startRno, endRno 를 map 에 추가하여 
			//            파라미터로 넘겨서 select 되도록 한다. 
			map.put("startRno", String.valueOf(startRno));
			map.put("endRno", String.valueOf(endRno));
			
			
			cultureWishList = cultureService.culturePagingWishList(map); // 검색어가 없는 페이징처리
			
			for(int i=0; i< cultureWishList.size(); i++) {
				
				if(cultureWishList.get(i).get("ccbakdcd").equals("11")) {
					cultureWishList.get(i).put("ccbakdcd","국보");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("12")) {
					cultureWishList.get(i).put("ccbakdcd","보물");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("13")) {
					cultureWishList.get(i).put("ccbakdcd","사적");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("14")) {
					cultureWishList.get(i).put("ccbakdcd","사적및명승");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("15")) {
					cultureWishList.get(i).put("ccbakdcd","명승");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("16")) {
					cultureWishList.get(i).put("ccbakdcd","천연기념물");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("17")) {
					cultureWishList.get(i).put("ccbakdcd","국가무형문화재");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("18")) {
					cultureWishList.get(i).put("ccbakdcd","국가민속문화재");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("21")) {
					cultureWishList.get(i).put("ccbakdcd","시도유형문화재");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("22")) {
					cultureWishList.get(i).put("ccbakdcd","시도무형문화재");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("23")) {
					cultureWishList.get(i).put("ccbakdcd","시도기념물");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("24")) {
					cultureWishList.get(i).put("ccbakdcd","시도민속문화재");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("31")) {
					cultureWishList.get(i).put("ccbakdcd","문화재자료");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("79")) {
					cultureWishList.get(i).put("ccbakdcd","등록문화재");
				}else if(cultureWishList.get(i).get("ccbakdcd").equals("80")) {
					cultureWishList.get(i).put("ccbakdcd","이북5도무형문화재");
				}else if((cultureWishList.get(i).get("ccbakdcd"))==null) {
					cultureWishList.get(i).put("ccbakdcd","없음");
				}
				
				if(cultureWishList.get(i).get("ccbactcd").equals("11")) {
					cultureWishList.get(i).put("ccbactcd","서울");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("21")) {
					cultureWishList.get(i).put("ccbactcd","부산");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("22")) {
					cultureWishList.get(i).put("ccbactcd","대구");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("23")) {
					cultureWishList.get(i).put("ccbactcd","인천");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("24")) {
					cultureWishList.get(i).put("ccbactcd","광주");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("25")) {
					cultureWishList.get(i).put("ccbactcd","대전");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("26")) {
					cultureWishList.get(i).put("ccbactcd","울산");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("45")) {
					cultureWishList.get(i).put("ccbactcd","세종");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("31")) {
					cultureWishList.get(i).put("ccbactcd","경기");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("32")) {
					cultureWishList.get(i).put("ccbactcd","강원");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("33")) {
					cultureWishList.get(i).put("ccbactcd","충북");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("34")) {
					cultureWishList.get(i).put("ccbactcd","충남");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("35")) {
					cultureWishList.get(i).put("ccbactcd","전북");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("36")) {
					cultureWishList.get(i).put("ccbactcd","전남");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("37")) {
					cultureWishList.get(i).put("ccbactcd","경북");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("38")) {
					cultureWishList.get(i).put("ccbactcd","경남");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("50")) {
					cultureWishList.get(i).put("ccbactcd","제주");
				}else if(cultureWishList.get(i).get("ccbactcd").equals("ZZ")) {
					cultureWishList.get(i).put("ccbactcd","전국일원");
				}else if((cultureWishList.get(i).get("ccbactcd"))==null) {
					cultureWishList.get(i).put("ccbactcd","없음");
				}
				
				if(cultureWishList.get(i).get("cccename")==null) {
					cultureWishList.get(i).put("cccename","시대미상");
				}
				//System.out.println("imageurl : " + cultureWishList.get(i).get("ccbakdcd"));
			}
			// ==== #113. 페이지바 만들기
			//   (먼저, 페이지바에 나타낼 총페이지 갯수 구해야 하는데 우리는 위에서 구했다.) ===== 
			
			String pagebar = "<ul>"; 
			
			pagebar += MyUtil.getPageBar("CultureWishList.action", currentShowPageNo, sizePerPage, totalPage, blockSize);     
			pagebar += "</ul>";
			
			req.setAttribute("pagebar", pagebar);
			
			req.setAttribute("cultureWishList", cultureWishList);
		
		
		return "culture/cultureWishList.tiles";
	}	

	
		
		//oneDelete
		@RequestMapping(value="oneDeleteWish.action",method={RequestMethod.POST})
		public String oneDeleteWish(HttpServletRequest req) {
			
			String idx = (String)req.getParameter("cultureIdxModal");
		//System.out.println(idx);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idx", idx);
		int n = cultureService.oneDeleteWish(map); // 검색어가 없는 페이징처리
		
		String msg ="";
		String loc ="";
		if(n==1) {
			msg = "삭제 성공 !!";
			loc = "CultureWishList.action";
		}else {
			msg = "삭제 실패 !!";
			loc = "CultureWishList.action";
		}
		
		
		req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);
		return "msg.notiles";
		}
		//arrDelete
		@RequestMapping(value="arrDeleteWish.action",method={RequestMethod.POST})
		public String arrDeleteWish(HttpServletRequest req) {
			String[] delChkboxArr = req.getParameterValues("arrDelIdx"); 
		
		/*
		  	for(int i=0; i<delChkboxArr.length; i++) {
			System.out.println("delChkboxArr : " + delChkboxArr[i]);
		}
		*/
		
		
		//System.out.println("===> 확인용 str : " + str);
		// ===> 확인용 str : 108,105,103,100,99
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("delChkboxArr", delChkboxArr);
		int n = cultureService.ArrDeleteWish(map); // 검색어가 없는 페이징처리
		
		String msg ="";
		String loc ="";
		
		if(n==0) {
			msg = "삭제 실패 !!";
			loc = "CultureWishList.action";
		}else {
			msg = "삭제 성공 !!";
			loc = "CultureWishList.action";
		}
		
		
		req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);
		return "msg.notiles";
		}
			
	
		//cultureSearchJSON.action
		@RequestMapping(value="cultureSearchJSON.action",method= {RequestMethod.GET})
		public String cultureSearchJSON(HttpServletRequest req) {
			
			String searchword = req.getParameter("cultureSearch");
			
			//System.out.println("확인용  json.action에들어옴. " + searchword);
			
			
			req.setAttribute("cultureSearchWord", searchword);
			JSONArray jonsArray = new JSONArray();
			
			if(!searchword.trim().isEmpty()) {
				
			//	List<String> nameList = adao.getSearchName(searchword); service 단 만들기.
				
				List<String> cultureList = cultureService.getcultureSearchJSON(searchword);
				
			// System.out.println("cultureList" + cultureList.size());
				
				if(cultureList !=null && cultureList.size() >0) {
					
					for(String culturename : cultureList) {
						JSONObject jsonObj = new JSONObject();
						
						jsonObj.put("culturename" ,culturename);
						
					//	System.out.println("///////////////"+culturename);
						
						jonsArray.put(jsonObj);
						
					}	
				
				}
				
			}// end of if(!searchword.trim().isEmpty())
			
			String str_name = jonsArray.toString();
			
			//System.out.println("확인용 cultureList : "+str_name);
			
			req.setAttribute("str_name", str_name);
			
			return "cultureSearchJSON.notiles";
		}
		
		

		// 문화재 실시간 검색어 rankShowJSON.action
		@RequestMapping(value="rankShowJSON.action",method= {RequestMethod.GET})
		public String rankShowJSON(HttpServletRequest req) {
			
			String str_jsonarray = cultureService.rankShowJSON();
			
			req.setAttribute("str_jsonarray", str_jsonarray);
			
			return "rankShowJSON.notiles";
		}
		
		// 문화재 이름으로 워드클라우드 차트		
		@RequestMapping(value="wordCloud.action",method= {RequestMethod.GET})
		public String word(HttpServletRequest req) {
			
			
			List<HashMap<String,String>> culturelist = cultureService.wordCloud();
			
			String text="";
			
			for(int i=0; i<culturelist.size(); i++) {
			
				for(int j = 0;j<Integer.parseInt(culturelist.get(i).get("COUNT"));j++) {
					
					String word =culturelist.get(i).get("CCMANAME"); 
					 word = word.replaceAll(" ", "");
					 text += word +" ";
					
				}
			}
			
			
			req.setAttribute("word", text);			
			
			
			
			
			return "culture/chart.tiles";
		}
		
		
	
	
}
