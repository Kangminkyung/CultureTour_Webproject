package com.spring.project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.common.FileManager;
import com.spring.common.MyUtil;
import com.spring.common.ThumbnailManager;
import com.spring.project.band.model.BandImageVO;
import com.spring.project.band.model.BandNoticeVO;
import com.spring.project.band.model.BandPlanVO;
import com.spring.project.band.model.BandVO;
import com.spring.project.band.model.PCommentVO;
import com.spring.project.band.model.PhotoVO;
import com.spring.project.band.service.InterBandService;
import com.spring.project.board.model.BoardVO;
import com.spring.project.board.model.CommentVO;
import com.spring.project.culture.model.CultureVO;
import com.spring.project.member.model.MemberVO;

@Controller
@Component
public class BandController {
	
	@Autowired
	private InterBandService service;
	 
	@Autowired
	private FileManager fileManager;
	
	// ===== #167. 썸네일을 다루어주는 클래스 의존객체 주입하기(DI: Dependency Injection) =====
	@Autowired
	private ThumbnailManager thumbnailManager;
	
	// 메인메뉴 - 밴드 리스트 불러오는 코드
   @RequestMapping(value="/bandList.action", method= {RequestMethod.GET})
   public String requireLogin_bandList(HttpServletRequest req,HttpServletResponse res) {
   
	   // 썸네일 포함 전 밴드 리스트 불러오기
	  // List<BandVO> bandList = service.getBandListbandList.action();
	  // req.setAttribute("bandList", bandList);
	   
	   HttpSession session = req.getSession();
	   
	   int rnd = -1;
	   
	   // 썸네일 포함한 밴드리스트 불러오기
	//   List<HashMap<String, String>> bandList = service.getBandList();
	//   req.setAttribute("bandList", bandList);
	
	  /* rndArr = new int[bandList.size()];
	   
	   for(int i=0; i<bandList.size(); i++) {
		   rnd = (int)(Math.random()*5)+1;
		   rndArr[i] = rnd;
	   }
	  
	   req.setAttribute("rndArr", rndArr);*/
	   
	   rnd = (int)(Math.random()*5)+1;
	   req.setAttribute("rnd", rnd);
	   
	   
	   // 페이징 처리 ======================================
	   List<HashMap<String, String>> bandList = null;
			
		// ==== 검색어가 포함되었으므로 먼저
		//			위의 boardList = service.boardList(); 부분을 주석처리 하고
		// 			아래의 작업을 한다. ====
		String colname = req.getParameter("colname");
		String search = req.getParameter("search");
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("colname", colname);
		map.put("search", search);
			
		// ===== 페이징 처리하기 =====
		String str_currentShowPageNo = req.getParameter("currentShowPageNo");
		
		int totalCount = 0; // 총게시물건수
		int sizePerPage = 5; // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo =0; // 현재 보여주는 페이지 번호로서, 
		int totalPage = 0;// 총페이지수 (웹브라우저상에 보여줄 총 페이지 갯수)
		
		int startRno = 0; // 시작행 번호
		int endRno =0; // 끝행 번호
		
		int blockSize = 10; // "페이지바" 에 보여줄 페이지의 갯수
	
		
	 	// ===== 총페이지수 구하기 =====
	 	//검색조건이 없을때의 총페이지 수와
	 	//검색조건이 있을때의 총페이지 수를 구해야한다.
	 	
	 	//검색조건이 없을때의 총 페이지수 ==> colname과 search 값이 null 인 것이고,
	 	//검색조건이 있을때의 총 페이지수 ==> colname과 search 값이 null 이 아닌것이다.
	 
	
		// 먼저 총게시물 건수를 구한다.
		if( (colname != null && search != null) &&
			(!colname.equals("null") && !search.equals("null")) &&
			(!colname.trim().isEmpty() && !search.trim().isEmpty()) 
		  ) {
			totalCount = service.getTotalBandCount2(map); // 검색어가 있는 총게시물 건수
			//System.out.println("검색어있는 totalCount: "+totalCount);
		}
		else {
			totalCount = service.getTotalBandCount(); // 검색어가 없는 총게시물 건수
			//System.out.println("검색어없는 totalCount: "+totalCount);
		}
		
		totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
		
		if(str_currentShowPageNo == null) {
			// 게시판 초기화면에 보여지는 것은
			// req.getParameter("currentShowPageNo"); 값이 없으므로
			// str_currentShowPageNo은 null이 된다
			
			currentShowPageNo = 1;
		}else {
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				
				if(currentShowPageNo < 1 || currentShowPageNo > totalPage)
					currentShowPageNo = 1;
				
			}catch (NumberFormatException e) {
				currentShowPageNo = 1;
			}
		}
		
		// **** 가져올 게시글의 범위를 구한다 (공식!!!) ****
		
		/* 	currentShowPageNo	startRno	endRno
		 	----------------------------------------
		 		1page		==> 	1			5
		 		2page		==> 	6			10
		 		3page		==> 	11			15
		 		4page		==> 	16			20
		 		5page		==> 	21			25
		 		6page		==> 	26			30
		 		7page		==> 	31			35
		 */
		startRno = (currentShowPageNo - 1)*sizePerPage + 1;
		endRno = startRno + sizePerPage - 1;
		
		
		// ==== #111. 페이징 처리를 위해 startRno, endRno를 map에 추가하여
		//			파라미터로 넘겨 select 되도록 한다.
		map.put("startRno", String.valueOf(startRno));
		map.put("endRno", String.valueOf(endRno));
		
		if( (colname != null && search != null) &&
				(!colname.equals("null") && !search.equals("null")) &&
				(!colname.trim().isEmpty() && !search.trim().isEmpty()) 
			  ) {
				bandList = service.bandList2(map);// 검색어가 있는 페이징처리
				//System.out.println("검색어있는 bandList: "+bandList);
		}
		else {
			bandList = service.bandList(map); // 검색어가 없는 페이징처리
			//System.out.println("검색어없는 bandList: "+bandList);
		}
		
		// ====페이지바 만들기
		//		(먼저, 페이지바에 나타낼 총 페이지 갯수 구해야하는데 위에서 구했음.) ====
		
		
		String pagebar = "<ul>";
		pagebar += MyUtil.getSearchPageBar("bandList.action", currentShowPageNo, sizePerPage, totalPage, blockSize, colname, search, null);
		pagebar += "</ul>";
		
		req.setAttribute("pagebar", pagebar);
		
		req.setAttribute("bandList", bandList);
		req.setAttribute("colname", colname);
		req.setAttribute("search", search);
		
		 String currentURL = MyUtil.getCurrentURL(req);
		 session.setAttribute("gobackURL", currentURL);

      return "band/bandList.tiles";
   }
   
   // 나의 메뉴 - 밴드 생성하기
   @RequestMapping(value="/bandAdd.action", method= {RequestMethod.GET})
   public String requireLogin_bandAdd(HttpServletRequest req,HttpServletResponse res) {
   
	   	HttpSession session = req.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser == null) {
			session.invalidate();
			
			String msg = "로그인이 필요합니다.";
			String loc = "javascript:history.back();";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			  
			return "msg.notiles";
		}
		else {
			  List<HashMap<String, String>> bandThemaList = service.getBandThema();
			  req.setAttribute("bandThemaList", bandThemaList);
			
			  return "band/bandAdd.tiles";  
		}
   }
   
 
	// 나의 메뉴 - 밴드 생성하기
   @RequestMapping(value="/bandAddEnd.action", method= {RequestMethod.POST})
   public String requireLogin_bandAddEnd(HttpServletRequest request,HttpServletResponse res, HttpSession session,BandVO bandvo,MultipartHttpServletRequest req) {
   

	   String bcode = String.valueOf(service.getBcodeseq()); // 먼저 밴드일련번호 채번해오기
	   String bname = req.getParameter("bname");
	   String badmin = req.getParameter("badmin");
	   String bthema = req.getParameter("bthema");
	   String binfo = req.getParameter("binfo").replaceAll("\r\n", "<br>");
	   
	   List<MultipartFile> attachList = req.getFiles("attach");
	   
	 
	   int rnd = -1;
	   /*List<MultipartFile> attachList = new ArrayList<MultipartFile>();
	   attachList = req.getFiles("attach");*/
	   
	   HashMap<String, String> mapBand = new HashMap<String, String>();
	   mapBand.put("bcode", bcode);
	   mapBand.put("bname", bname);
	   mapBand.put("badmin", badmin);
	   mapBand.put("bthema", bthema);
	   mapBand.put("binfo", binfo);
	   
	   // 첨부파일이 있으면 
	   List<HashMap<String, String>> mapBandimageList = new ArrayList<HashMap<String, String>>();
	   
	   // 이미지 첨부가 있는 경우!!!!!!!
	   /*if(attachList != null ) {*/
	   if(attachList.get(0).getSize() > 0) {
	   /*if(attachList.size() > 0 ) {*/
		 //  System.out.println("======> 이미지 첨부가 있어요~~~~~~ <=======");
		 //  System.out.println("attachList.get(0).getSize() ==> "+attachList.get(0).getSize());
		   
			// WAS 의 webapp 의 절대경로를 알아와야 한다. 
			session = req.getSession();
			String root = session.getServletContext().getRealPath("/"); 
			String path = root + "resources"+File.separator+"files";
			// path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
			
			String newFileName = "";
			// WAS(톰캣) 디스크에 저장할 파일명 
			
			byte[] bytes = null;
			// 첨부파일을 WAS(톰캣) 디스크에 저장할때 사용되는 용도 
			
			long fileSize = 0;
			// 파일크기를 읽어오기 위한 용도
			
			String thumbnailFileName = ""; 
			// WAS 디스크에 저장될 thumbnail 파일명 
			
			for(int i=0; i<attachList.size(); i++) {
				try {
					 bytes = attachList.get(i).getBytes();
					 
					 newFileName = fileManager.doFileUpload(bytes, attachList.get(i).getOriginalFilename(), path); 
					 
					 fileSize = attachList.get(i).getSize();
					 
					// === >>>> thumbnail 파일 생성을 위한 작업 <<<<    =========  //
					 thumbnailFileName = thumbnailManager.doCreateThumbnail(newFileName, path); 
					// ===================================================  //
					 
					 HashMap<String, String> mapBandimage = new HashMap<String, String>();
					 
					 mapBandimage.put("fk_bcode", bcode);
					 mapBandimage.put("imagefilename", newFileName);
					 mapBandimage.put("imageorgFilename", attachList.get(i).getOriginalFilename());
					 mapBandimage.put("imagefileSize", String.valueOf(fileSize));
					 mapBandimage.put("thumbnailFileName", thumbnailFileName);
					 
					 mapBandimageList.add(mapBandimage);
					 
				} catch (Exception e) {
					
				}
				
			}// end of for-------------------------
		}// end of if------------------------------

	   
	   // 이미지 첨부가 없는 경우!!!!!!! (기본이미지를 등록시킨다)
		else {
		//	System.out.println("======> 이미지 첨부가 없는 경우!!!!!!! (기본이미지 보여주기) <=======");
			// 랜덤으로 1~5까지 뽑는다.
			rnd = (int)(Math.random()*5)+1; 
	
		}
	   
		 // **** 폼에서 입력받은 제품입력정보 값을 
		 //      Service 단으로 넘겨서 테이블에 insert 하기로 한다.
		 // 이미지파일첨부가 없는 경우 또는 이미지파일첨부가 있는 경우로 나누어서
		 // Service 단으로 호출하기
	   int n = 0;
	   int m = 0;
	   int count = 0;
	   
	   
	   if(attachList.get(0).getSize() == 0) { // 파일 첨부된것이 없다면
		   n = service.addBand(mapBand);
		   
		   if(m==1) n=1;
		   

	   }
	   
	   else if(attachList.get(0).getSize() > 0) { // 파일 첨부된것이 있다면
		   n = service.addBand(mapBand);
		   
		   for(int i=0; i<mapBandimageList.size(); i++) {
			   m = service.addBandImage(mapBandimageList.get(i));
			   if(m==1) count++;
		   }
		   
		   if(mapBandimageList.size() == count) {
			   n=1;
			//   System.out.println("count:" + count);
		   }
		   else {
			   n=0;
		   }
	   }
	   			   
	   String msg = "";
	   String loc = "";
	   if(n==1) {
		   msg = "밴드등록 성공!!";
		   loc = "/final/bandList.action";
	   }
	   else {
		   msg = "밴드등록 실패!!";
		   loc = "javascript:history.back();";
	   }
	    		
	   req.setAttribute("msg", msg);
	   req.setAttribute("loc", loc);
	   req.setAttribute("rnd", rnd);
	   
	   return "msg.notiles";
   }
   
   // 나의 메뉴 - 가입한 밴드페이지 상세 불러오는 코드
   @RequestMapping(value="/mybandDetail.action", method= {RequestMethod.GET})
   public String requireLogin_mybandDetail(HttpServletRequest req,HttpServletResponse res) {
	   
	   String bcode = req.getParameter("bcode");
	   req.setAttribute("bcode", bcode);
	   
	   BandVO bandvo = service.getBandInfo(bcode);
	   req.setAttribute("bandvo", bandvo);
	   
	   List<HashMap<String, String>> image = service.getViewBandImage(bcode);
	   req.setAttribute("image", image);
	   
	   HttpSession session = req.getSession();
	   session.setAttribute("bandvo", bandvo);
	   session.setAttribute("image", image);
	   
      return "myband/mybandDetail.tiles2";
   }
   
   // 나의 메뉴 - 마이페이지에서 가입한 밴드목록 리스트 불러오는 코드
   @RequestMapping(value="/mybandList.action", method= {RequestMethod.GET})
   public String requireLogin_mybandList(HttpServletRequest req,HttpServletResponse res) {
	   
	   HttpSession session = req.getSession();
	   MemberVO mvo = (MemberVO)session.getAttribute("loginuser");	   
	   String userid = mvo.getUserid();	 
	   
	   
	   String type = req.getParameter("type");
	   
	   if(type==null) {
		   type="create";
	   }
	   
	   HashMap<String,String> map = new HashMap<String,String>();
	   
	   // ==== 페이징 처리 하기 ====
		String str_currentShowPageNo = req.getParameter("currentShowPageNo");
		
		int totalCount = 0; // 총 게시물건수
		int sizePerPage = 5; // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0; // 현재 페이지번호로서, 초기치로는 1페이지로 설정함
		int totalPage = 0; // 총 페이지수 (웹브라우저상에 보여줄 총 페이지수)
		
		int startRno = 0; // 시작행 번호
		int endRno = 0; // 끝행 번호
		
		int blockSize = 10; // '페이지바'에 보여줄 페이지의 갯수
	   
		
		if(type.equals("join")) {
			totalCount = service.getMyJoinBandCount(userid);
		}else {
			totalCount = service.getMyCreateBandCount(userid);
		}
	    totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
	
		if(str_currentShowPageNo == null) {
			// 게시판 초기화면에 보여지는 것은
			// req.getParameter("currentShowPageNo"); 값이 없으므로
			// str_currentShowPageNo은 null이 된다
			
			currentShowPageNo = 1;
		}else {
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				
				if(currentShowPageNo < 1 || currentShowPageNo > totalPage)
					currentShowPageNo = 1;
				
			}catch (NumberFormatException e) {
				currentShowPageNo = 1;
			}
		}
		
		// **** 가져올 게시글의 범위를 구한다 (공식!!!) ****
		
		/* 	currentShowPageNo	startRno	endRno
		 	----------------------------------------
		 		1page		==> 	1			5
		 		2page		==> 	6			10
		 		3page		==> 	11			15
		 		4page		==> 	16			20
		 		5page		==> 	21			25
		 		6page		==> 	26			30
		 		7page		==> 	31			35
		 */
		startRno = (currentShowPageNo - 1)*sizePerPage + 1;
		endRno = startRno + sizePerPage - 1;
		
		
		// ==== #111. 페이징 처리를 위해 startRno, endRno를 map에 추가하여
		//			파라미터로 넘겨 select 되도록 한다.
		map.put("startRno", String.valueOf(startRno));
		map.put("endRno", String.valueOf(endRno));
	    map.put("userid",userid);

	   
	    List<HashMap<String, String>> mybandList = null;
	    
	    if(type.equals("join")) {
	    	 mybandList = service.getListMyJoinband(map);		
		}else {
			 mybandList = service.getListMyCreateband(map);
		}
	 
	   
	    String pagebar = "<ul>";
		pagebar += MyUtil.getBandPageBar("mybandList.action", currentShowPageNo, sizePerPage, totalPage, blockSize,type);
		pagebar += "</ul>";
	
		req.setAttribute("pageBar", pagebar);
		
		String type2= "";
		
		if(type.equals("join")) {
			type2 = "가입밴드";
		}else {
			type2="생성밴드";
		}
		
	   req.setAttribute("type", type2);
	   
	   req.setAttribute("mybandList", mybandList);
		
      return "band/mybandList.tiles";
      
   }
   
   // 메인메뉴 - badmin 과 userid가 같으면 밴드 정보 수정 가능
   @RequestMapping(value="/bandEdit.action", method= {RequestMethod.GET})
   public String requireLogin_bandEdit(HttpServletRequest req,HttpServletResponse res) {
   
	   	HttpSession session = req.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String bcode = req.getParameter("bcode");
		BandVO bandvo = service.getBandInfo(bcode);
		
		if(!bandvo.getBadmin().equals(loginuser.getUserid())) {
			
			String msg = "밴드 생성자만 수정이 가능합니다.";
			String loc = "javascript:history.back();";
			
		//	System.out.println("userid: "+loginuser.getUserid());
		//	System.out.println("badmin: "+bandvo.getBadmin());
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			  
			return "msg.notiles";
		}
		else {
			  List<HashMap<String, String>> bandThemaList = service.getBandThema();
			  req.setAttribute("bandThemaList", bandThemaList);
			  req.setAttribute("bandvo", bandvo);
			  return "band/bandEdit.tiles";  
		}
   }
   
   @RequestMapping(value="/bandEditEnd.action", method= {RequestMethod.POST})
   public String requireLogin_bandEditEnd(HttpServletRequest request,HttpServletResponse res, HttpSession session,BandVO bandvo,MultipartHttpServletRequest req) {

	   String bcode = req.getParameter("bcode");
	   String bname = req.getParameter("bname");
	   String badmin = req.getParameter("badmin");
	   String bthema = req.getParameter("bthema");
	   String binfo = req.getParameter("binfo").replaceAll("\r\n", "<br>");
	   
	   List<MultipartFile> attachList = req.getFiles("attach");
	   
	  // System.out.println("/////// : "+attachList.size());
	  
	   int rnd = -1;
	   /*List<MultipartFile> attachList = new ArrayList<MultipartFile>();
	   attachList = req.getFiles("attach");*/
	   
	   HashMap<String, String> mapBand = new HashMap<String, String>();
	   mapBand.put("bcode", bcode);
	   mapBand.put("bname", bname);
	   mapBand.put("badmin", badmin);
	   mapBand.put("bthema", bthema);
	   mapBand.put("binfo", binfo);
	   
	   // 첨부파일이 있으면 
	   List<HashMap<String, String>> mapBandimageList = new ArrayList<HashMap<String, String>>();
	   
	   // 이미지 첨부가 있는 경우!!!!!!!
	   /*if(attachList != null ) {*/
	   if(attachList.get(0).getSize() > 0) {
	   /*if(attachList.size() > 0 ) {*/
		   System.out.println("======> 이미지 첨부가 있어요~~~~~~ <=======");
		   System.out.println("attachList.get(0).getSize() ==> "+attachList.get(0).getSize());
		   
			// WAS 의 webapp 의 절대경로를 알아와야 한다. 
			session = req.getSession();
			String root = session.getServletContext().getRealPath("/"); 
			String path = root + "resources"+File.separator+"files";
			// path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
			
			String newFileName = "";
			// WAS(톰캣) 디스크에 저장할 파일명 
			
			byte[] bytes = null;
			// 첨부파일을 WAS(톰캣) 디스크에 저장할때 사용되는 용도 
			
			long fileSize = 0;
			// 파일크기를 읽어오기 위한 용도
			
			String thumbnailFileName = ""; 
			// WAS 디스크에 저장될 thumbnail 파일명 
			
			for(int i=0; i<attachList.size(); i++) {
				try {
					 bytes = attachList.get(i).getBytes();
					 
					 newFileName = fileManager.doFileUpload(bytes, attachList.get(i).getOriginalFilename(), path); 
					 
					 fileSize = attachList.get(i).getSize();
					 
					// === >>>> thumbnail 파일 생성을 위한 작업 <<<<    =========  //
					 thumbnailFileName = thumbnailManager.doCreateThumbnail(newFileName, path); 
					// ===================================================  //
					 
					 HashMap<String, String> mapBandimage = new HashMap<String, String>();
					 
					 mapBandimage.put("fk_bcode", bcode);
					 mapBandimage.put("imagefilename", newFileName);
					 mapBandimage.put("imageorgFilename", attachList.get(i).getOriginalFilename());
					 mapBandimage.put("imagefileSize", String.valueOf(fileSize));
					 mapBandimage.put("thumbnailFileName", thumbnailFileName);
					 
					 mapBandimageList.add(mapBandimage);
					 
				} catch (Exception e) {
					
				}
				
			}// end of for-------------------------
		}// end of if------------------------------

	   
	   // 이미지 첨부가 없는 경우!!!!!!! (기본이미지를 등록시킨다)
		else {
			System.out.println("======> 이미지 첨부가 없는 경우!!!!!!! (기본이미지 보여주기) <=======");
			// 랜덤으로 1~5까지 뽑는다.
			rnd = (int)(Math.random()*5)+1; 
	
		}
	   
		 // **** 폼에서 입력받은 제품입력정보 값을 
		 //      Service 단으로 넘겨서 테이블에 insert 하기로 한다.
		 // 이미지파일첨부가 없는 경우 또는 이미지파일첨부가 있는 경우로 나누어서
		 // Service 단으로 호출하기
	   int n = 0;
	   int m = 0;
	   int w = 0;
	   int cnt = 0;
	   int count = 0;	   
	  
	   
	   if(attachList.get(0).getSize() == 0) { // 파일 첨부된것이 없다면
		   n = service.editBand(mapBand);
		   if(m==1) n=1;
	   }
	   
	  
	   else if(attachList.get(0).getSize() > 0) { // 파일 첨부된것이 있다면
		   
		   n = service.editBand(mapBand);		   
		   cnt = service.countBandImage(bcode);		 
		   // 기존에 final_bandImage 테이블에 저장되어있던 수정 전 이미지 파일 갯수 구하기 

		   if(cnt>0) {
			   w = service.deleteBandImage(bcode); 
			   // 기본 final_bandImage 테이블에 저장된 이미지는 전부 삭제함
		   }
		   
		   for(int i=0; i<mapBandimageList.size(); i++) {
			  
			   System.out.println(mapBandimageList.get(i));
			   m = service.addBandImage(mapBandimageList.get(i));
			   // 수정하면서 첨부한 이미지들을 final_bandImage에 저장함
			   
			   if(m==1 ) count++;
			   // 수정전 이미지 파일 갯수와 삭제한 이미지 파일 갯수가 같고 
			   // 이미지 파일을 count 갯수만큼 add 한다. 
		   }
		   
		   if(mapBandimageList.size() == count) {
			   n=1;
		   }
		   else {
			   n=0;
		   }
	   }

	   			   
	   req.setAttribute("n", n);
	   
	   return "band/bandEditEnd.tiles";  
   }

   // 메인 메뉴 - 밴드 삭제하기
   @RequestMapping(value="/bandDel.action", method= {RequestMethod.GET})
   public String requireLogin_bandDel(HttpServletRequest req,HttpServletResponse res) {
   
	   HttpSession session = req.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String bcode = req.getParameter("bcode");
		BandVO bandvo = service.getBandInfo(bcode);
		int n =0;
		
		if(!bandvo.getBadmin().equals(loginuser.getUserid())) {
			
			String msg = "밴드 생성자만 삭제가 가능합니다.";
			String loc = "javascript:history.back();";
			
		//	System.out.println("userid: "+loginuser.getUserid());
		//	System.out.println("badmin: "+bandvo.getBadmin());
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			  
			return "msg.notiles";
		}
		else {
			 n = service.getBandDel(bcode); // 밴드삭제
			  req.setAttribute("n", n);
			  return "band/bandDel.tiles";  
		}
   }
   

   //bandJoin.action   
   @RequestMapping(value="/bandJoin.action", method= {RequestMethod.GET})
   public String requireLogin_bandJoin(HttpServletRequest req ,HttpServletResponse res) {
   
	   	String userid = req.getParameter("userid");
	   	String bcode  = req.getParameter("bcode");
	  
	   	String msg = "";
	   	String loc = "";
	   	
	   	
	    HashMap<String, String> map = new HashMap<String, String>();
    	map.put("userid", userid);
    	map.put("bcode", bcode);
   
    	String bandjang = service.getbandjang(map);
    	
    	// System.out.println("밴드장 "+bandjang);
    	
    	String alreadybandjohn = service.getalreadybandjohn(map);
    	
    	//System.out.println("alreadybandjohnlist "+alreadybandjohnlist);
    	 
    	if(alreadybandjohn != null && alreadybandjohn != userid ) {
    	
    		msg = "이미 가입신청을 하셨습니다!.";
    		loc = "javascript:history.back();";

			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			  
			return "msg.notiles";
    	}else {

	    	int n = service.getbandJoin(map);
	    	
	    	msg = "밴드 가입 신청 성공!!.";
			loc = "javascript:history.back();";
	
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			  
			return "msg.notiles";
    	}
    
   }
   
   //bandAdmin.action
   //밴드관리
   @RequestMapping(value="/bandAdmin.action",method= {RequestMethod.GET})
   public String requireLogin_bandAdmin(HttpServletRequest req,HttpServletResponse res) {
	   
  		HttpSession session = req.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String bcode = req.getParameter("bcode");
		BandVO bandvo = service.getBandInfo(bcode);
		req.setAttribute("bcode", bcode);
		
		 List<HashMap<String,String>> bandmemberList = null;
		 HashMap<String, String> map = new HashMap<String, String>();
		 
		if(!bandvo.getBadmin().equals(loginuser.getUserid())) {				
			String msg = "밴드 생성자만 관리가 가능합니다.";
			String loc = "javascript:history.back();";				

			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);				  
			return "msg.notiles";
		}
		else {		 
			
			String searchWord = req.getParameter("searchWord");
		    String searchType = req.getParameter("searchType");
		    
		    String str_sizePerPage = req.getParameter("sizePerPage"); 
		    
		    if(searchType==null) {
		    	searchType="name";
		    }
		
		    
		    if(searchWord == null)   // 초기화면
		    {
		    	searchWord = "";
		    }
		    
		    if(str_sizePerPage == null)  // 초기화면
		    	str_sizePerPage = "5";
		    
		    // GET 방식으로 넘어오는 경우이므로 사용자가 장난치는 경우를 대비해야함.
		    if(!"name".equals(searchType) && !"userid".equals(searchType) && !"email".equals(searchType)) { 
		    	searchType = "name";
		    }
		    
		    req.setAttribute("searchType", searchType);	
		    req.setAttribute("searchWord", searchWord);
		    
		 
		    if(str_sizePerPage == null)  // 초기화면
		    		str_sizePerPage = "5";
			
		    int sizePerPage = 0;
		    
		    try{
		    	sizePerPage = Integer.parseInt(str_sizePerPage);
		    	
		    	if(sizePerPage != 3 && sizePerPage != 5 && sizePerPage != 10) {
			    	sizePerPage = 5;
			    }
		    } catch(NumberFormatException e){
		    	sizePerPage = 5;
		    }
		    
		    req.setAttribute("sizePerPage", sizePerPage);
			
			// 전체 페이지 갯수 알아오기
		    // 총 밴드 회원수 알아오기
		    int totalMemberCount = 0;
		    
			map.put("bcode", bcode);						
			map.put("searchType",searchType);
			map.put("searchWord",searchWord);
		    
		    
		    totalMemberCount = service.getBandTotalCount(map);
		    
		    int totalPage = (int)Math.ceil((double)totalMemberCount / sizePerPage); 
		    
		    String str_currentShowPageNo = req.getParameter("currentShowPageNo");
		    int currentShowPageNo = 0; // 사용자가 보고자 하는 페이지번호    
				    
		    if(str_currentShowPageNo == null || str_currentShowPageNo.trim().isEmpty() ) {
		    	currentShowPageNo = 1;
		    	// 초기화면은 현재페이지 번호로 1 페이지로 설정한다.
		    }
		    else {
		    	try{
		    		currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
		        	// 사용자가 보고자하는 페이지번호를 클릭했을 때
		        	// 페이지번호를 현재페이지로 설정함.
		        	
		        	if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
		        		currentShowPageNo = 1;
		        	}
		        	
		    	}catch(NumberFormatException e) {
		    		currentShowPageNo = 1;
		    	}
		    	
		    }
		    
		    int startRno = 0;
		    int endRno = 0;
		    
		    startRno = (currentShowPageNo*sizePerPage)-(sizePerPage-1);
		    endRno = (currentShowPageNo*sizePerPage);
		    
		    map.put("startRno",String.valueOf(startRno));
			map.put("endRno",String.valueOf(endRno));				
	
		    
		    bandmemberList = service.getBandMember(map);
		//	HashMap<String,String> map = new HashMap<String,String>();
			
		/*	map.put("bcode", bcode);
			map.put("startRno", startRno);		
			map.put("endRno", endRno);		
				*/
		//	 List<HashMap<String,String>> bandmemberList = service.getBandMember(map);
			  
		    String pageBar = "<ul>";
		    
		    pageBar += MyUtil.getSearchPageBar("bandAdmin.action", currentShowPageNo, sizePerPage, totalPage, 10,bcode,searchType , searchWord);
			
		    pageBar +="</ul>";
		    
			  req.setAttribute("bandmemberList",bandmemberList);
			  req.setAttribute("pageBar",pageBar);
				
			  return "band/bandAdmin.tiles";
		}	
   }
	   
	//joinallow
   @RequestMapping(value="/joinallow.action", method= {RequestMethod.GET})
   public String requireLogin_joinallow(HttpServletRequest req,HttpServletResponse res) throws Throwable {
   
	   String userid = req.getParameter("userid");
	   String bcode = req.getParameter("bcode");
	   
	   HashMap<String,String> map = new HashMap<String,String>();
	   map.put("userid", userid);
	   map.put("bcode",bcode);
	   
	   int n = service.getjoinallow(map);
	   
	   
      return "joinallow.notiles";
   }
   
	// 밴드 탈퇴
   @RequestMapping(value="/oneDeleteBand.action", method= {RequestMethod.POST,RequestMethod.GET})
   public String oneDeleteBand(HttpServletRequest req ) {
   
	   String bcode = req.getParameter("bandIdxModal");
	   String type = req.getParameter("type");
	   
	 
	   HashMap<String,String> map = new HashMap<String,String>();
	   
	   HttpSession session = req.getSession();
	   MemberVO mvo = (MemberVO) session.getAttribute("loginuser");
	   String userid = mvo.getUserid();
	   
	 
	   map.put("bcode", bcode);
	   map.put("userid",userid);
	   map.put("type",type);	   

	   int n = service.deleteMyBand(map);

	   String msg ="";
	   String loc ="";
	   
	   if(n>0) {
		   msg = "탈퇴 성공!";
		   loc = "mybandList.action";
	   }
	   
	   req.setAttribute("msg", msg);
	   req.setAttribute("loc", loc);
	   
    		
      return "msg.notiles";
   }
	   
   /////////////////////////////////////////////////////////////////////////
   //////////////////// ******* 밴드 공지사항 ******** //////////////////////////
	// 밴드 공지사항 목록 보기
   @RequestMapping(value="/bandNotice.action", method= {RequestMethod.GET})
	public String requireLogin_bandNotice(HttpServletRequest req, HttpServletResponse res) {
	   
		HttpSession session = req.getSession();

	      
		List<BandNoticeVO> bandNoticeList = null;
		  
		String bcode = req.getParameter("bcode");
		session.setAttribute("bcode", bcode);
		req.setAttribute("bcode", bcode);

	    BandVO bandInfo = service.getBandInfo(bcode);
		req.setAttribute("bandInfo", bandInfo);
		
		// ==== 검색어가 포함 ====
		String colname = req.getParameter("colname");
		String search = req.getParameter("search");
		
		String goBackURL = MyUtil.getCurrentURL(req);
		session.setAttribute("goBackURL", goBackURL);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("colname", colname);
		map.put("search", search);
		map.put("bcode",bcode);
		
		// ==== 페이징 처리 하기 ====
		String str_currentShowPageNo = req.getParameter("currentShowPageNo");
		
		int totalCount = 0; // 총 게시물건수
		int sizePerPage = 4; // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0; // 현재 페이지번호로서, 초기치로는 1페이지로 설정함
		int totalPage = 0; // 총 페이지수 (웹브라우저상에 보여줄 총 페이지수)
		
		int startRno = 0; // 시작행 번호
		int endRno = 0; // 끝행 번호
		
		int blockSize = 10; // '페이지바'에 보여줄 페이지의 갯수
		
		// 먼저 총게시물의 건수를 구한다.
/*			if((colname != null && search != null) && (!colname.equals("null") && !search.equals("null")) && 
				(!colname.trim().isEmpty() && !search.trim().isEmpty())) {
			totalCount = service.getBandNoticeTotalCount2(map);
			// 검색어가 있는 총게시물의 건수
		}
		else {
			totalCount = service.getBandNoticeTotalCount();
			// 검색어가 없는 총게시물의 건수
		}
		
		*/
		totalCount = service.getBandNoticeTotalCount(map);
		
		totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
		
		if(str_currentShowPageNo == null) {
			// 게시판 초기화면에 보여지는것은 
			// req.getParameter("currentShowPageNo"); 값이 없으므로
			// str_currentShowPageNo은 null이 된다.
			
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
		
		// **** 가져올 게시글의 범위를 구한다.(공식) ****
		startRno = (currentShowPageNo - 1) * sizePerPage + 1;
		endRno = startRno + sizePerPage - 1;
		
		// ==== #111. 페이지 처리를 위해 startRno와  endRno를 map에 추가하여 파라미터로 넘겨서 select되도록 한다. ====
		map.put("startRno", String.valueOf(startRno));
		map.put("endRno", String.valueOf(endRno));
		
	/*	if( (colname != null && search != null) && (!colname.equals("null") && !search.equals("null")) && 
				(!colname.trim().isEmpty() && !search.trim().isEmpty())) {
			bandNoticeList = service.bandNoticeList2(map);
			// 검색어가 있는 페이징처리
		}
		else {
			bandNoticeList = service.bandNoticeList(map);
			// 검색어가 없는 페이징처리
		}
		*/
		
		// 검색어가 없는 페이징 처리
		bandNoticeList = service.bandNoticeList(map);
		
		// ==== #113. 페이지바 만들기 ====
		// 먼저, 페이지바에 나타낼 총페이지 갯수 구해야하는데 우리는 위에서 구함
				
		String pageBar = "<ul>";
		//pageBar += MyUtil.getSearchPageBar("bandNotice.action", currentShowPageNo, sizePerPage, totalPage, blockSize, colname, search, null);
		
		pageBar += MyUtil.getBandNoticePageBar("bandNotice.action",bcode,currentShowPageNo,sizePerPage,totalPage,blockSize);
		pageBar += "</ul>";
		
		req.setAttribute("pageBar", pageBar);
		req.setAttribute("bcode", bcode);
		req.setAttribute("bandNoticeList", bandNoticeList);

	   
		return "myband/bandNotice.tiles2";
	}
	
		
	// 밴드 공지 등록하는 form 요청
	@RequestMapping(value="/addBandNotice.action", method= {RequestMethod.GET})
	public String requireLogin_addBandNotice(HttpServletRequest req, HttpServletResponse res) {
		
		String userid = req.getParameter("userid");
		String bcode = req.getParameter("bcode");
		
		req.setAttribute("userid", userid);
		req.setAttribute("bcode", bcode);
	
		return "myband/addBandNotice.tiles2";
	} 
		
		
	// 밴드 공지 등록 완료 요청
	@RequestMapping(value="/addBandNoticeEnd.action", method= {RequestMethod.POST})
	public String addBandNoticeEnd(BandNoticeVO bandnoticevo, HttpServletRequest req, HttpSession session) {
		
		String content = bandnoticevo.getContent().replaceAll("\r\n", "<br>");
		bandnoticevo.setContent(content);			
		
		String bcode = req.getParameter("bcode");
		bandnoticevo.setFk_bcode(bcode);
		
		req.setAttribute("bcode", bcode);
		
		int n = 0;
		
		n = service.addBandNotice(bandnoticevo);
		
		req.setAttribute("n", n);
	
		return "myband/addBandNoticeEnd.tiles2";
	} 
	
	// 밴드 공지 1개 보여주기
	@RequestMapping(value="/bandNoticeView.action", method= {RequestMethod.GET})
	public String requireLogin_bandNoticeView(HttpServletRequest req, HttpServletResponse res) {		
		
		
		String seq = req.getParameter("seq"); // 글번호 받아오기
		
		BandNoticeVO bandnoticevo = null;	
		
		// ==== #67. 글조회수증가는 반드시 해당 글제목을 클릭했을 경우에만 증가되고 
		//			  웹브라우저에서 새로고침을 했을 경우에는 증가되지 못하도록 한다. ====		
		HttpSession session = req.getSession();
		
		if("yes".equals(session.getAttribute("readCountPermission"))) {
			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
			String userid = null;
			
			if(loginuser != null) {
				userid = loginuser.getUserid();					
			}
			
			bandnoticevo = service.getBandNoticeView(seq, userid); 
			// 조회수 증가한 후에 글 1개를 가져오는 것
			// 단, 자신이 쓴 글을 자신이 읽을시에는 조회수가 증가되지않고,
			// 다른 사람이 쓴 글이여야 조회수가 증가되도록 해야한다.
			// 로그인 하지 않은 상태에서 글을 읽을때 조회수 증가가 일어나지 않도록 한다.
			
			session.removeAttribute("readCountPermission");
			
		}
		else {
			// 웹브라우저에서 새로고침(F5)을 누른경우, 
			// 조회수 증가없이 그냥 글 1개를 가져오는 것
			bandnoticevo = service.getViewWithNoReadCount(seq);			
		}
		
		req.setAttribute("bandnoticevo", bandnoticevo);
		
		               
		return "myband/bandNoticeView.tiles2";
	}
	
	// ==== #70. 글수정 페이지 요청하기 ====
	@RequestMapping(value="/bandNoticeEdit.action", method= {RequestMethod.GET})
	public String requireLogin_bandNoticeEdit(HttpServletRequest req, HttpServletResponse response) {		
		
		String seq = req.getParameter("seq"); // 수정해야할 글번호 가져오기
		String bcode = req.getParameter("bcode");
		
		// 수정해야할 글 전체 내용 가져오기
		// 조회수 1증가없이 그냥 글 1개를 가져오는것
		BandNoticeVO bandnoticevo = service.getViewWithNoReadCount(seq);
		
		HttpSession session = req.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(!bandnoticevo.getUserid().equals(loginuser.getUserid())) {
			String msg = "다른 사용자의 글은 수정이 불가합니다.";
			String loc = "javascript:history.back()";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
			return "msg.notiles";
		}
		else {
			// 가져온 1개글을 request 영역에 저장시켜서 view단 페이지로 넘긴다.
			req.setAttribute("bandnoticevo", bandnoticevo);
			req.setAttribute("bcode", bcode);
		}
		
		return "myband/bandNoticeEdit.tiles2";
	}
		
		
	// ==== #71. 글수정 페이지 완료하기 ====
	@RequestMapping(value="/bandNoticeEditEnd.action", method= {RequestMethod.POST})
	public String bandNoticeEditEnd(BandNoticeVO bandnoticevo, HttpServletRequest req) {
		
		String content = bandnoticevo.getContent().replaceAll("\r\n", "<br>");
		bandnoticevo.setContent(content);
		
		String bcode = req.getParameter("bcode");
		bandnoticevo.setFk_bcode(bcode);
		
		req.setAttribute("bcode", bcode);
		
		// 글 수정을 하려면 원본글의 암호와 수정시 입력해주는 암호가 일치할 때만
		// 수정이 가능하도록 해야한다.
		
		int n = service.bandNoticeEdit(bandnoticevo);
		// n이 1이면 update 성공, 0이면 실패
		
		req.setAttribute("n", n);
		req.setAttribute("seq", bandnoticevo.getSeq());
		
		return "myband/bandNoticeEditEnd.tiles2";
	}
	
	
	// ==== #77. 글삭제 페이지 요청하기 ====
	@RequestMapping(value="/bandNoticeDel.action", method= {RequestMethod.GET})
	public String requireLogin_bandNoticeDel(HttpServletRequest req, HttpServletResponse response) {		
		
		String seq = req.getParameter("seq"); // 삭제해야할 글번호 가져오기
		
		HttpSession session = req.getSession();
		String bcode = (String)session.getAttribute("bcode");	
	
		
		// 삭제되어질 글은 자신이 작성한 글이어야만 가능하다.
		// 삭제되어질 글내용을 읽어오면 작성자를 알 수 있다.
		BandNoticeVO bandnoticevo = service.getViewWithNoReadCount(seq);
		
	
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(!bandnoticevo.getUserid().equals(loginuser.getUserid())) {
			String msg = "다른 사용자의 글은 삭제가 불가합니다.";
			String loc = "javascript:history.back()";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
			
			return "msg.notiles";
		}
		else {
			// 삭제해야할 글번호를 request 영역에 저장시켜서 view단 페이지로 넘긴다.
			req.setAttribute("seq", seq);
			req.setAttribute("bcode", bcode);
			// 글삭제시 글작성시 암호를 다시 입력받아 암호의 일치여부를 알아보도록 view단 페이지 del.jsp로 넘긴다.
			session.removeAttribute("bcode");
			return "myband/bandNoticeDel.tiles2";
		}
		
	}
	
	
	// ==== #78. 글삭제 페이지 완료하기 ====
	@RequestMapping(value="/bandNoticeDelEnd.action", method= {RequestMethod.POST})
	public String bandNoticeDelEnd(BandNoticeVO bandnoticevo, HttpServletRequest req) throws Throwable {
		
		// 글 삭제를 하려면 원본글의 암호와 삭제시 입력해주는 암호가 일치할 때만
		// 삭제가 가능하도록 해야한다.
		// service단에서 글삭제를 처리한 결과를 int타입으로 받아오겠다.
		
		String seq = req.getParameter("seq");
		String pw = req.getParameter("pw");			
		String bcode = req.getParameter("bcode");
		System.out.println(bcode);
		req.setAttribute("bcode", bcode);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("seq", seq);
		map.put("pw", pw);
		map.put("bcode",bcode);
		
		int n = service.bandNoticeDel(map);
		
		req.setAttribute("n", n);
		
		return "myband/bandNoticeDelEnd.tiles2";
	}
	
	////////////////////******* 밴드 공지사항 끝 ******** //////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////----------BandPlan-----------/////////////////////////////////
	@RequestMapping(value="/bandPlan.action", method= {RequestMethod.GET})
	   public String bandPlan(HttpServletRequest req) {
	   
		   String bcode = req.getParameter("bcode");
		   req.setAttribute("bcode", bcode); 		

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("bcode", bcode);
			
		    // ==== 글조회수증가는 반드시 해당 글제목을 클릭했을 경우에만 증가되고 
			//		웹브라우저에서 새로고침을 했을 경우에는 증가되지 못하도록 한다. ====
			// 이것을 하기 위한 표식을 단다.
			// ===> 시작 <===
			
			HttpSession session = req.getSession();
			session.setAttribute("readCountPermission", "yes");
			// 반드시 웹브라우저 주소창에 /list.action이라고 입력해야만 
			// 세션에 "readCountPermission"값이 저장되어진다.
			
			// ===> 끝 <===		
		   
		   /////////////////////////페이징////////////////////////////
		   // 페이징 처리 ======================================
		   List<HashMap<String, String>> planList = null;

			String colname = req.getParameter("colname");
			String search = req.getParameter("search");
		
			
			if(colname == null) {
				colname = "fk_bcode";
			}
			
			if(search == null) {
				search = "";
			}
			
		//	System.out.println("확인요 ㄹㄹㄹ bcode    " + bcode );
		//	System.out.println("확인용 ㄹㄹㄹ        " + colname);
		//	System.out.println("확인용 ㄹㄹㄹ        " + search);
			
	
			map.put("colname", colname);
			map.put("search", search);
			
			// ===== 페이징 처리하기 =====
			String str_currentShowPageNo = req.getParameter("currentShowPageNo");
			
			int totalCount = 0; // 총게시물건수
			int sizePerPage = 5; // 한 페이지당 보여줄 게시물 건수
			int currentShowPageNo =0; // 현재 보여주는 페이지 번호로서, 
			int totalPage = 0;// 총페이지수 (웹브라우저상에 보여줄 총 페이지 갯수)
			
			int startRno = 0; // 시작행 번호
			int endRno =0; // 끝행 번호
			
			int blockSize = 10; // "페이지바" 에 보여줄 페이지의 갯수
		
			
		 	// ===== 총페이지수 구하기 =====
		 	//검색조건이 없을때의 총페이지 수와
		 	//검색조건이 있을때의 총페이지 수를 구해야한다.
		 	
		 	//검색조건이 없을때의 총 페이지수 ==> colname과 search 값이 null 인 것이고,
		 	//검색조건이 있을때의 총 페이지수 ==> colname과 search 값이 null 이 아닌것이다.
		 
		
			// 먼저 총게시물 건수를 구한다.
			
			totalCount = service.getTotalPlanCount2(map); // 검색어가 있는 플랜 건수
			
		//	System.out.println("33333333333333          "+totalCount);
			
			totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
			
			if(str_currentShowPageNo == null) {
				// 게시판 초기화면에 보여지는 것은
				// req.getParameter("currentShowPageNo"); 값이 없으므로
				// str_currentShowPageNo은 null이 된다
				
				currentShowPageNo = 1;
			}else {
				try {
					currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
					
					if(currentShowPageNo < 1 || currentShowPageNo > totalPage)
						currentShowPageNo = 1;
					
				}catch (NumberFormatException e) {
					currentShowPageNo = 1;
				}
			}
			
			startRno = (currentShowPageNo - 1)*sizePerPage + 1;
			endRno = startRno + sizePerPage - 1;
			
			// ==== #111. 페이징 처리를 위해 startRno, endRno를 map에 추가하여
			//			파라미터로 넘겨 select 되도록 한다.
			map.put("startRno", String.valueOf(startRno));
			map.put("endRno", String.valueOf(endRno));
			
			
			planList = service.getPlanList2(map);// 검색어가 있는 페이징처리
					
			// ====페이지바 만들기
			//		(먼저, 페이지바에 나타낼 총 페이지 갯수 구해야하는데 위에서 구했음.) ====
			
			
			String pagebar = "<ul>";
			pagebar += MyUtil.getSearchPageBarbandplan("bandPlan.action", currentShowPageNo, sizePerPage, totalPage, blockSize, colname, search, bcode);
			pagebar += "</ul>";
			
			req.setAttribute("pagebar", pagebar);
			
			req.setAttribute("planList", planList);
			req.setAttribute("colname", colname);
			req.setAttribute("search", search);
			
			 String currentURL = MyUtil.getCurrentURL(req);
			 
			 session.setAttribute("gobackURL", currentURL);
		   
		   
	      return "myband/plan/bandPlan.tiles2";
	   }
	   
	   @RequestMapping(value="/bandPlanAdd.action", method= {RequestMethod.GET})
	   public String bandPlanAdd(HttpServletRequest req ) {
	   
		   String bcode = req.getParameter("bcode");
		   req.setAttribute("bcode", bcode); 	

	      return "myband/plan/bandPlanAdd.tiles2";
	   }
	   
	   @RequestMapping(value="/bandPlanAddEnd.action", method= {RequestMethod.POST})
	   public String bandPlanAddEnd(HttpServletRequest req ) {
	   
		   BandPlanVO planvo = new BandPlanVO();
		   
		    String fk_bcode = req.getParameter("fk_bcode");
		    String pnum = String.valueOf(service.getPnumseq()); // 먼저 밴드플랜번호 채번해오기
	    	String fk_userid = req.getParameter("fk_userid");
	    	String pdate = req.getParameter("pdate");
	    	String ptitle = req.getParameter("ptitle");
	    	String cultureSearch = req.getParameter("cultureSearch2");
	    	String pstime = req.getParameter("pstime"); 
	    	String petime = req.getParameter("petime");
	    	String pmoney = req.getParameter("pmoney");
	    	String pcontent = req.getParameter("pcontent").replaceAll("\r\n", "<br>");
	    	
	    	planvo.setFk_bcode(fk_bcode);
	    	planvo.setPnum(pnum);
	    	planvo.setFk_userid(fk_userid);
	    	planvo.setPdate(pdate);
	    	planvo.setPtitle(ptitle);
	    	planvo.setCultureSearch(cultureSearch);
	    	planvo.setPstime(pstime);
	    	planvo.setPetime(petime);
	    	planvo.setPmoney(pmoney);
	    	planvo.setPcontent(pcontent);
	    	
	      	// 플랜등록
	    	int n = service.getBandPlanAdd(planvo);
	    	
	    	req.setAttribute("n", n);
	    	req.setAttribute("fk_bcode", fk_bcode);
	    	
	      return "myband/plan/bandPlanAddEnd.tiles2";
	   }
	   
	   @RequestMapping(value="/planView.action", method= {RequestMethod.GET})
	   public String requireLogin_planView(HttpServletRequest req,HttpServletResponse res) throws IOException {
		   String fk_bcode = req.getParameter("bcode");
	         String pnum = req.getParameter("pnum");
	   //   String cultureSearch = req.getParameter("cultureSearch");
	      
	        BandPlanVO planView = null;
	          ////////////////////
	         

	      // ==== #조회수 증가: 글조회수증가는 반드시 해당 글제목을 클릭했을 경우에만 증가되고 
	      //           웹브라우저에서 새로고침을 했을 경우에는 증가되지 못하도록 한다. ====      
	      HttpSession session = req.getSession();
	      
	      int idx = 0;
	      
	      if("yes".equals(session.getAttribute("readCountPermission"))) {
	         MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	         String userid = null;
	   
	         
	         if(loginuser != null) {
	            userid = loginuser.getUserid();               
	         }
	         
	         System.out.println("userid: "+userid);
	         planView = service.getPlanView(pnum, userid); 
	         // 조회수 증가한 후에 글 1개를 가져오는 것
	         // 단, 자신이 쓴 글을 자신이 읽을시에는 조회수가 증가되지않고,
	         // 다른 사람이 쓴 글이여야 조회수가 증가되도록 해야한다.
	         // 로그인 하지 않은 상태에서 글을 읽을때 조회수 증가가 일어나지 않도록 한다.
	         
	         // 문화재 명을 가지고 온다
	         String cultureSearch = planView.getCultureSearch();
	         
	         // 탐방 문화재가 데이터 베이스에 존재하면 idx 값을 가져온다.
	          idx = service.getCultureIdx(cultureSearch);
	          
	         session.removeAttribute("readCountPermission");
	         
	      }
	      else {
	         // 웹브라우저에서 새로고침(F5)을 누른경우, 
	         // 조회수 증가없이 그냥 글 1개를 가져오는 것
	         planView = service.getPlanViewWithNoReadCount(pnum);   
	         
	         // 문화재 명을 가지고 온다
	         String cultureSearch = planView.getCultureSearch();
	         
	         // 탐방 문화재가 데이터 베이스에 존재하면 idx 값을 가져온다.
	          idx = service.getCultureIdx(cultureSearch);
	      }
	      
	      //   int idx = service.getCultureIdx(cultureSearch); // 밴드 플랜 페이지에서 문화재 번호가져오기
	        
	      
	      // ==== 댓글 내용 가져오기 ====
	         List<PCommentVO> commentList = service.listPComment(pnum);
	         req.setAttribute("commentList", commentList);
	            
	         req.setAttribute("fk_bcode", fk_bcode);
	         req.setAttribute("pnum", pnum);
	         req.setAttribute("planView", planView);
	         req.setAttribute("idx", idx);


	         return "myband/plan/planView.tiles2";

	   }

	   @RequestMapping(value="/bandPlanEdit.action", method= {RequestMethod.GET})
	   public String bandPlanEdit(HttpServletRequest req ) throws IOException {
		   
		   String fk_bcode = req.getParameter("fk_bcode");
		   String pnum = req.getParameter("pnum");
		   
		   BandPlanVO planView = service.getPlanViewWithNoReadCount(pnum);			
		   
		   HttpSession session = req.getSession();
		   MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		   
		   if(!planView.getFk_userid().equals(loginuser.getUserid())) {
				String msg = "다른 사용자의 글은 수정이 불가합니다.";
				String loc = "javascript:history.back()";
				
				req.setAttribute("msg", msg);
				req.setAttribute("loc", loc);
				
				return "msg.notiles";
			}
			else {
				// 가져온 1개글을 request 영역에 저장시켜서 view단 페이지로 넘긴다.
				req.setAttribute("planView", planView);
			}
		   
		   
		   System.out.println("fk_bcode"+fk_bcode);
		   System.out.println("pnum: "+pnum);
		   
		   
		   return "myband/plan/bandPlanEdit.tiles2";
	   }
	   
	   @RequestMapping(value="/bandPlanEditEnd.action", method= {RequestMethod.POST})
	   public String bandPlanEditEnd(HttpServletRequest req ) throws IOException {
		   
		    BandPlanVO planvo = new BandPlanVO();
		   
		    String fk_bcode = req.getParameter("fk_bcode");
		    String pnum = req.getParameter("pnum");
		   	String fk_userid = req.getParameter("fk_userid");
		   	String pdate = req.getParameter("pdate");
		   	String ptitle = req.getParameter("ptitle");
		   	String cultureSearch = req.getParameter("cultureSearch2");
		   	String pstime = req.getParameter("pstime"); 
		   	String petime = req.getParameter("petime");
		   	String pmoney = req.getParameter("pmoney");
		   	String pcontent = req.getParameter("pcontent").replaceAll("\r\n", "<br>");
		   	
		   	planvo.setFk_bcode(fk_bcode);
		   	planvo.setPnum(pnum);
		   	planvo.setFk_userid(fk_userid);
		   	planvo.setPdate(pdate);
		   	planvo.setPtitle(ptitle);
		   	planvo.setCultureSearch(cultureSearch);
		   	planvo.setPstime(pstime);
		   	planvo.setPetime(petime);
		   	planvo.setPmoney(pmoney);
		   	planvo.setPcontent(pcontent);
		   	
		   /*	System.out.println("fk_bcode"+fk_bcode);
			System.out.println("pnum"+pnum);
			System.out.println("fk_userid"+fk_userid);
			System.out.println("ptitle"+ptitle);*/
			
		    // 플랜수정
		   	int n = service.getBandPlanEdit(planvo);
		   
		   req.setAttribute("n", n);
		   req.setAttribute("fk_bcode", fk_bcode);
		   req.setAttribute("pnum", pnum);
		   
		   return "myband/plan/bandPlanEditEnd.tiles2";
	   }
	   
	   @RequestMapping(value="/bandPlanDel.action", method= {RequestMethod.GET})
	   public String bandPlanDel(HttpServletRequest req ) throws IOException {
		   
		   String pnum = req.getParameter("pnum");
		   String fk_bcode = req.getParameter("fk_bcode");
		   
		   // 플랜삭제
		   int n = service.getBandPlanDel(pnum);
		   
		   req.setAttribute("fk_bcode", fk_bcode);
		   req.setAttribute("pnum", pnum);
		   req.setAttribute("n", n);
		   
		   return "myband/plan/bandPlanDel.tiles2";
	   }
	   
	// 밴드플랜 댓글추가
	@RequestMapping(value="/addPComment.action", method= {RequestMethod.POST})
	public String addPComment(HttpServletRequest req, HttpServletResponse response, PCommentVO pcommentvo) throws Throwable {
	   
	   // 플랜 댓글쓰기(**** AJAX로 처리 ****)
		int n = service.addPComment(pcommentvo); 
		
		JSONArray jsonarry = new JSONArray();
		String str_jsonarray = null;
		
		if(n != 0) {
			// 댓글쓰기와 원게시물(tblBoard 테이블)에 댓글의 갯수(1씩 증가)
			// 증가가 성공했다라면
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", pcommentvo.getName()); 
			jsonObj.put("content", pcommentvo.getContent());
			jsonObj.put("regDate", MyUtil.getNowTime());
			
			jsonarry.put(jsonObj);
		}
		
		str_jsonarray = jsonarry.toString();
		
		req.setAttribute("str_jsonarray", str_jsonarray);

		return "addPCommentEndJSON.notiles";
	   }
   
   
	///////////////// 사진게시판 /////////////////////////
	@RequestMapping(value="/photoList.action", method= {RequestMethod.GET})
	public String requireLogin_photoList(HttpServletRequest req, HttpServletResponse res) {
	   
		HttpSession session = req.getSession();
		  
		List<PhotoVO> photoList = null;
		  
		String bcode = req.getParameter("bcode");
		session.setAttribute("bcode", bcode);
		req.setAttribute("bcode", bcode);
		
		String goBackURL = MyUtil.getCurrentURL(req);
		session.setAttribute("goBackURL", goBackURL);
		
		photoList = service.photoList(bcode);
		
		int size = photoList.size();
		
		req.setAttribute("bcode", bcode);
		req.setAttribute("photoList", photoList);
		req.setAttribute("size", size);
	   
		return "myband/photoList.tiles2";
	}
   
	@RequestMapping(value="/addPhoto.action", method= {RequestMethod.GET})
	public String requireLogin_addPhoto(HttpServletRequest req, HttpServletResponse res) {
		
		String userid = req.getParameter("userid");
		String bcode = req.getParameter("bcode");
		
		req.setAttribute("userid", userid);
		req.setAttribute("bcode", bcode);
	
		return "myband/addPhoto.tiles2";
	} 
	
	
	@RequestMapping(value="/addPhotoEnd.action", method= {RequestMethod.POST})
	public String addPhotoEnd(PhotoVO photovo, MultipartHttpServletRequest req, HttpSession session) {

		String bcode = req.getParameter("bcode");
		
		photovo.setFk_bcode(bcode);
		photovo.setUserid(req.getParameter("userid"));
		
		if(!photovo.getAttach().isEmpty()) {
			// attach가 비어있지 않다면(즉, 첨부파일이 있는 경우라면)
			
			/*
				1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 경로 폴더에 저장해주어야 한다.
				>>>> 파일이 업로드 되어질 특정 경로(폴더) 지정해주기
				우리는 WAS(톰캣)의 webapp/resources/files라는 폴더로 지정해주겠다.
			*/
			
			// WAS(톰캣)의 webapp의 절대경로를 알아와야 한다.
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "photos";
			
			System.out.println("path: " + path);
			// C:\springworkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\files
			
			/*
				2. 파일첨부를 위한 변수의 설정 및 값을 초기화한 후 파일올리기
			*/
			String newFileName = "";
			// WAS(톰캣) 디스크에 저장할 파일명
			
			byte[] bytes = null;
			// 첨부파일을 WAS(톰캣)디스크에 저장할때 사용되는 용도
			
			long fileSize = 0;
			// 파일크기를 읽어오기 위한 용도
			
			try {
				bytes = photovo.getAttach().getBytes();
				// getBytes()는 첨부된 파일을 바이트단위로 파일을 다 읽어오는 것이다.
				
				newFileName = fileManager.doFileUpload(bytes, photovo.getAttach().getOriginalFilename(), path);
				// 파일을 업로드한 후, 현재시간+나노타입.확장자로 되어진 파일명을 리턴받아 newFileName으로 저장한다.
				// boardvo.getAttach().getOriginalFilename()은 첨부된 파일의 실제 파일명을 얻어오는 것이다.
				
				photovo.setFileName(newFileName);
				photovo.setOrgFilename(photovo.getAttach().getOriginalFilename());
				fileSize = photovo.getAttach().getSize();
				photovo.setFileSize(String.valueOf(fileSize));
				
			} catch (Exception e) {
				
			}
		}
		
		int n = 0;
		if(!photovo.getAttach().isEmpty()) {
			// 파일첨부가 있다라면
			n = service.addPhoto(photovo);
		}
		
		req.setAttribute("n", n);
		
	
		return "myband/addPhotoEnd.tiles2";
	} 
   
   //////////////////////////////////////////////////
	
	  //bandallow.action////////////////////
	   @RequestMapping(value="/bandallow.action", method= {RequestMethod.GET})
	   public String requireAdmin_bandallow(HttpServletRequest req,HttpServletResponse res){
		   
		  HashMap<String,String> map = new HashMap<String,String>();
		  
		  // ==== 페이징 처리 하기 ====
			String str_currentShowPageNo = req.getParameter("currentShowPageNo");
			
			int totalCount = 0; // 총 게시물건수
			int sizePerPage = 5; // 한 페이지당 보여줄 게시물 건수
			int currentShowPageNo = 0; // 현재 페이지번호로서, 초기치로는 1페이지로 설정함
			int totalPage = 0; // 총 페이지수 (웹브라우저상에 보여줄 총 페이지수)
			
			int startRno = 0; // 시작행 번호
			int endRno = 0; // 끝행 번호
			
			int blockSize = 10; // '페이지바'에 보여줄 페이지의 갯수
		   
				totalCount = service.getbandallowCount();
		
			//	System.out.println("확인용 총카운트 " + totalCount);
				
		    totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
		
			if(str_currentShowPageNo == null) {
				// 게시판 초기화면에 보여지는 것은
				// req.getParameter("currentShowPageNo"); 값이 없으므로
				// str_currentShowPageNo은 null이 된다
				
				currentShowPageNo = 1;
			}else {
				try {
					currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
					
					if(currentShowPageNo < 1 || currentShowPageNo > totalPage)
						currentShowPageNo = 1;
					
				}catch (NumberFormatException e) {
					currentShowPageNo = 1;
				}
			}
			
			// **** 가져올 게시글의 범위를 구한다 (공식!!!) ****
			
			/* 	currentShowPageNo	startRno	endRno
			 	----------------------------------------
			 		1page		==> 	1			5
			 		2page		==> 	6			10
			 		3page		==> 	11			15
			 		4page		==> 	16			20
			 		5page		==> 	21			25
			 		6page		==> 	26			30
			 		7page		==> 	31			35
			 */
			startRno = (currentShowPageNo - 1)*sizePerPage + 1;
			endRno = startRno + sizePerPage - 1;
			
			
			// ==== #111. 페이징 처리를 위해 startRno, endRno를 map에 추가하여
			//			파라미터로 넘겨 select 되도록 한다.
			map.put("startRno", String.valueOf(startRno));
			map.put("endRno", String.valueOf(endRno));
		   
			List<HashMap<String,String>> bandlist = service.getbandallow(map);

		    String pagebar = "<ul>";
			pagebar += MyUtil.getBandallowPageBar("bandallow.action", currentShowPageNo, sizePerPage, totalPage, blockSize);
			pagebar += "</ul>";
		
			req.setAttribute("pageBar", pagebar);
		  
		   req.setAttribute("bandlist", bandlist);
					
			return "band/bandallow.tiles";
		}	// end of bandallow ------------------------------------------------------
		   
	   
	   //oneallowband
	   @RequestMapping(value="/oneallowband.action", method= {RequestMethod.POST})
	   public String requireLogin_oneallowband(HttpServletRequest req,HttpServletResponse res){
		   
		   String bcode = req.getParameter("bandIdxModal");
		  
		   int n = service.getoneallowband(bcode);
		   
		   if(n == 1) {
				
				String msg = "밴드 가입 승인!";
				String loc = "bandallow.action";
				
				req.setAttribute("msg", msg);
				req.setAttribute("loc", loc);
				  
				return "msg.notiles";
			}
		
		   return "band/bandallow.tiles";
	   }
	   
	   
	   
	  // checkboxbandallow.action
	   @RequestMapping(value="/checkboxbandallow.action", method= {RequestMethod.POST})
	   public String requireLogin_checkboxbandallow(HttpServletRequest req,HttpServletResponse res){
		   
		   String[] pnumArr = req.getParameterValues("pnum"); // 제품번호
		  
		   HashMap<String,String[]> map = new HashMap<String,String[]>();
		   
		   map.put("pnumArr", pnumArr);
		   
		   
		   int n = service.getcheckboxbandallow(map);
		   
		   if(n > 0) {
				
				String msg = "밴드 "+n+"개 가입 승인!";
				String loc = "bandallow.action";
				
				req.setAttribute("msg", msg);
				req.setAttribute("loc", loc);
				  
				return "msg.notiles";
			}
		   
		   
		
		   return "band/bandallow.tiles";
	   }

	   /////////// 댓글 신고 추가////////////////////
	   // 1. bandPlan 댓글 신고
	   @RequestMapping(value="/policePcomment.action", method= {RequestMethod.POST})
	   public String policePcomment(HttpServletRequest req ) throws Throwable {
		
			String seq = req.getParameter("seq"); 
			String police_userid = req.getParameter("police_userid");
			
	
		
			int n = 0;
			int m = 0;
			if(police_userid == null || police_userid.trim().isEmpty()) {
				
				String msg = "로그인이 필요합니다.";
				String loc = "javascript:history.back();";
				
				req.setAttribute("msg", msg);
				req.setAttribute("loc", loc);
				  
				return "msg.notiles";
				
			}else {
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("seq", seq);
				map.put("police_userid", police_userid);
				
				System.out.println("map seq:" +map.get("seq") +"map police_userid"+map.get("police_userid"));
				
				// 중복신고 막기
				m = service.policePuserid(map); // 밴드플랜 댓글 중복 신고 (policePcomment 테이블에 동일한 fk_seq에 userid 있는지 확인) 막기
				
				
				if(m==0) { // 없으면 신고접수
					// 신고하기
					n = service.policePcomment(map); // 밴드 플랜 댓글 신고
					
					req.setAttribute("n", n);
					return "policePcommentEnd.notiles";
					
				}else {
					String msg = "이미 신고하신 댓글입니다.";
					String loc = "javascript:history.back();";
					
					req.setAttribute("msg", msg);
					req.setAttribute("loc", loc);
					  
					return "msg.notiles";
				}
				
			}
	
		}
	
		// 2. 메인게시판 자유게시판 댓글 신고
		@RequestMapping(value="/policeComment.action", method= {RequestMethod.POST})
		public String policeComment(HttpServletRequest req ) throws Throwable {
			
			String seq = req.getParameter("seq"); 
			String police_userid = req.getParameter("police_userid");
			
			//System.out.println("번호seq : "+ seq +"아이디police_userid: "+police_userid);
		
			int n = 0;
			int m = 0;
			if(police_userid == null || police_userid.trim().isEmpty()) {
				
				String msg = "로그인이 필요합니다.";
				String loc = "javascript:history.back();";
				
				req.setAttribute("msg", msg);
				req.setAttribute("loc", loc);
				  
				return "msg.notiles";
				
			}else {
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("seq", seq);
				map.put("police_userid", police_userid);
				
				// 중복신고 막기
				m = service.policeUserid(map); // 자유게시판 댓글 중복 신고 (policecomment 테이블에 동일한 fk_seq에 userid 있는지 확인) 막기
						
				if(m==0) {
					n = service.policeComment(map); // 밴드 플랜 댓글 신고
					
					req.setAttribute("n", n);
					return "policeCommentEnd.notiles";
					
				}else {
					String msg = "이미 신고하신 댓글입니다.";
					String loc = "javascript:history.back();";
					
					req.setAttribute("msg", msg);
					req.setAttribute("loc", loc);
					  
					return "msg.notiles";
				}
	
			}

	}
	
	
		 // 메인메뉴 - 밴드 정보 불러오는 코드
		   @RequestMapping(value="/bandInfo.action", method= {RequestMethod.GET})
		   public String requireLogin_bandInfo(HttpServletRequest req,HttpServletResponse res) {
		   
		      String bcode = req.getParameter("bcode");
		      System.out.println("bcode" +bcode);
		     
		      BandVO bandInfo = service.getBandInfo(bcode);
		      
		      List<HashMap<String, String>> viewBandImageList = service.getViewBandImage(bcode);

		    
		      int rnd = -1;
		      rnd = (int)(Math.random()*5)+1;
		      req.setAttribute("rnd", rnd);
		      
		      req.setAttribute("bcode", bcode);
		      
		     req.setAttribute("bandInfo", bandInfo);
		     
		     req.setAttribute("bcode", bcode);
		     
		     try {
		    	 HashMap<String, String> viewBandImageMain = viewBandImageList.get(0);
			     req.setAttribute("main",viewBandImageMain);   
			     
		     }catch(Exception e) {
		    	 
		     }
		     
		     List<HashMap<String, String>> viewBandImageSub = new ArrayList<HashMap<String,String>>(); 
		     
		     for(int i=1;i<viewBandImageList.size();i++) {
		        viewBandImageSub.add(viewBandImageList.get(i));
		     }
		     
		     
		     int size = viewBandImageSub.size();
		     

		     req.setAttribute("viewBandImageSub", viewBandImageSub);      
		      
		     req.setAttribute("size", size);      
		      return "band/bandInfo.tiles";
		   }
   
}
