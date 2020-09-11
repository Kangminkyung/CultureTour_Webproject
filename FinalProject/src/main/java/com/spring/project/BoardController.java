package com.spring.project;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.project.board.model.BoardVO;
import com.spring.project.board.model.CommentVO;
import com.spring.project.board.model.NoticeVO;
import com.spring.project.board.model.PhotoVO;
import com.spring.project.board.service.InterBoardService;
import com.spring.project.member.model.MemberVO;
import com.spring.common.FileManager;
import com.spring.common.MyUtil;;

@Controller
@Component
public class BoardController {
	
	// ==== 의존객체 주입하기(DI : Dependency Injection) ====
	@Autowired
	private InterBoardService service;

	
	// ===== 파일업로드 및 다운로드를 해주는 FileManager 클래스 의존객체 주입하기(DI : Dependency Injection) ===== 
	@Autowired
	private FileManager fileManager;
	
	
	// ==== 공지사항 목록 보기 ====
	@RequestMapping(value="/showNotice.action", method= {RequestMethod.GET})
	public String showNotice(HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		//List<NoticeVO> noticeList = service.getNoticeList();
		List<NoticeVO> noticeList = null;
		
		// ==== 검색어가 포함 ====
		String colname = req.getParameter("colname");
		String search = req.getParameter("search");
		
		if(colname == null) {
			colname = "subject";
		}
		if(search == null) {
			search = "";
		}
		
		String goBackURL = MyUtil.getCurrentURL(req);
		session.setAttribute("goBackURL", goBackURL);
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("colname", colname);
		map.put("search", search);
		
		
		// ==== 페이징 처리 하기 ====
		String str_currentShowPageNo = req.getParameter("currentShowPageNo");
		
		int totalCount = 0; // 총 게시물건수
		int sizePerPage = 4; // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0; // 현재 페이지번호로서, 초기치로는 1페이지로 설정함
		int totalPage = 0; // 총 페이지수 (웹브라우저상에 보여줄 총 페이지수)
		
		int startRno = 0; // 시작행 번호
		int endRno = 0; // 끝행 번호
		
		int blockSize = 10; // '페이지바'에 보여줄 페이지의 갯수

			totalCount = service.getNoticeTotalCount2(map);
	
	//	System.out.println(totalCount);
			
		totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
		
		
		//System.out.println("gggggggggggggg123123" + totalPage);
		
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
		
		if( (colname != null && search != null) && (!colname.equals("null") && !search.equals("null")) && 
				(!colname.trim().isEmpty() && !search.trim().isEmpty())) {
			noticeList = service.noticeList2(map);
			// 검색어가 있는 페이징처리
		}
		else {
			noticeList = service.noticeList(map);
			// 검색어가 없는 페이징처리
		}
		
		// ==== #113. 페이지바 만들기 ====
		// 먼저, 페이지바에 나타낼 총페이지 갯수 구해야하는데 우리는 위에서 구함
				
		String pageBar = "<ul>";
		pageBar += MyUtil.getSearchPageBar("showNotice.action", currentShowPageNo, sizePerPage, totalPage, blockSize, colname, search, null);
		pageBar += "</ul>";
		
		//System.out.println("gggggggggggggg" + pageBar);
		
		req.setAttribute("pageBar", pageBar);
		
		req.setAttribute("noticeList", noticeList);
		req.setAttribute("colname", colname);
		req.setAttribute("search", search);
	
		return "board/showNotice.tiles";
	} 

	
	// ==== 공지사항 등록하기 ====
	@RequestMapping(value="/addNotice.action", method= {RequestMethod.GET})
	public String requireLogin_addNotice(HttpServletRequest req, HttpServletResponse res) {
		
		String userid = req.getParameter("userid");
		
		req.setAttribute("userid", userid);
	
		return "board/addNotice.tiles";
	} 
	
	@RequestMapping(value="/addNoticeEnd.action", method= {RequestMethod.POST})
	public String addNoticeEnd(NoticeVO noticevo, MultipartHttpServletRequest req, HttpSession session) {
		
		String content = noticevo.getContent().replaceAll("\r\n", "<br>");
		noticevo.setContent(content);
		
		int n = 0;
		
		n = service.addNotice(noticevo);
		
		req.setAttribute("n", n);
	
		return "board/addNoticeEnd.tiles";
	} 
	
	
	// ==== 공지글삭제 페이지 완료하기 ====
	@RequestMapping(value="/noticeDel.action", method= {RequestMethod.GET})
	public String noticeDel(HttpServletRequest req) throws Throwable {
		
		// 글 삭제를 하려면 원본글의 암호와 삭제시 입력해주는 암호가 일치할 때만
		// 삭제가 가능하도록 해야한다.
		// service단에서 글삭제를 처리한 결과를 int타입으로 받아오겠다.
		
		String seq = req.getParameter("seq");
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("seq", seq);
		
		int n = service.noticeDel(map);
		req.setAttribute("n", n);
		
		return "board/noticeDel.tiles";
	}
	
	// ==== 질문게시판 목록 보기 ====
	@RequestMapping(value="/qnalist.action", method= {RequestMethod.GET})
	public String qnalist(HttpServletRequest req) {
		
		List<BoardVO> boardList = null;
		
		// ==== 글조회수증가는 반드시 해당 글제목을 클릭했을 경우에만 증가되고 
		//		웹브라우저에서 새로고침을 했을 경우에는 증가되지 못하도록 한다. ====
		// 이것을 하기 위한 표식을 단다.
		// ===> 시작 <===
		
		HttpSession session = req.getSession();
		session.setAttribute("readCountPermission", "yes");
		// 반드시 웹브라우저 주소창에 /list.action이라고 입력해야만 
		// 세션에 "readCountPermission"값이 저장되어진다.
		
		// ===> 끝 <===		
		
		
		// ==== 검색어가 포함 ====
		String colname = req.getParameter("colname");
		String search = req.getParameter("search");
		
		String goBackURL = MyUtil.getCurrentURL(req);
		session.setAttribute("goBackURL", goBackURL);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("colname", colname);
		map.put("search", search);
		
		
		// ==== 페이징 처리 하기 ====
		String str_currentShowPageNo = req.getParameter("currentShowPageNo");
		
		int totalCount = 0; // 총 게시물건수
		int sizePerPage = 5; // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0; // 현재 페이지번호로서, 초기치로는 1페이지로 설정함
		int totalPage = 0; // 총 페이지수 (웹브라우저상에 보여줄 총 페이지수)
		
		int startRno = 0; // 시작행 번호
		int endRno = 0; // 끝행 번호
		
		int blockSize = 10; // '페이지바'에 보여줄 페이지의 갯수
		
		// 먼저 총게시물의 건수를 구한다.
		if((colname != null && search != null) && (!colname.equals("null") && !search.equals("null")) && 
				(!colname.trim().isEmpty() && !search.trim().isEmpty())) {
			totalCount = service.getTotalCount2(map);
			// 검색어가 있는 총게시물의 건수
		}
		else {
			totalCount = service.getTotalCount();
			// 검색어가 없는 총게시물의 건수
		}
		
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
		
		if( (colname != null && search != null) && (!colname.equals("null") && !search.equals("null")) && 
				(!colname.trim().isEmpty() && !search.trim().isEmpty())) {
			boardList = service.qnaList2(map);
			// 검색어가 있는 페이징처리
		}
		else {
			boardList = service.qnaList(map);
			// 검색어가 없는 페이징처리
		}
		
		// ==== #113. 페이지바 만들기 ====
		// 먼저, 페이지바에 나타낼 총페이지 갯수 구해야하는데 우리는 위에서 구함
				
		String pageBar = "<ul>";
		pageBar += MyUtil.getSearchPageBar("qnalist.action", currentShowPageNo, sizePerPage, totalPage, blockSize, colname, search, null);
		pageBar += "</ul>";
		
		req.setAttribute("pageBar", pageBar);
		
		req.setAttribute("boardList", boardList);
		req.setAttribute("colname", colname);
		req.setAttribute("search", search);
	
		return "board/qnalist.tiles";
	} 
	
	
	// ==== 질문게시판에서 질문글 form 요청하기 ====
	@RequestMapping(value="/addqna.action", method= {RequestMethod.GET})
	public String requireLogin_addqna(HttpServletRequest req,HttpServletResponse res) {
		
		String fk_seq = req.getParameter("fk_seq");
		String groupno = req.getParameter("groupno");
		String depthno = req.getParameter("depthno");
		
		req.setAttribute("fk_seq", fk_seq);
		req.setAttribute("groupno", groupno);
		req.setAttribute("depthno", depthno);
	
		return "board/addqna.tiles";
	} 
	
	
	// ==== 질문게시판에서 질문글 쓰기 완료 요청하기 ====
	@RequestMapping(value="/addqnaEnd.action", method= {RequestMethod.POST})
	public String addqnaEnd(BoardVO boardvo, MultipartHttpServletRequest req, HttpSession session) {
		
		// 첨부파일이 있으면 파일업로드하기 시작
		if(!boardvo.getAttach().isEmpty()) {
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";
			
			String newFileName = "";
			// WAS(톰캣) 디스크에 저장할 파일명
			
			byte[] bytes = null;
			// 첨부파일을 WAS(톰캣)디스크에 저장할때 사용되는 용도
			
			long fileSize = 0;
			// 파일크기를 읽어오기 위한 용도
			
			try {
				bytes = boardvo.getAttach().getBytes();
				// getBytes()는 첨부된 파일을 바이트단위로 파일을 다 읽어오는 것이다.
				
				newFileName = fileManager.doFileUpload(bytes, boardvo.getAttach().getOriginalFilename(), path);
				// 파일을 업로드한 후, 현재시간+나노타입.확장자로 되어진 파일명을 리턴받아 newFileName으로 저장한다.
				// boardvo.getAttach().getOriginalFilename()은 첨부된 파일의 실제 파일명을 얻어오는 것이다.
				
				boardvo.setFileName(newFileName);
				boardvo.setOrgFilename(boardvo.getAttach().getOriginalFilename());
				fileSize = boardvo.getAttach().getSize();
				boardvo.setFileSize(String.valueOf(fileSize));
				
			} catch (Exception e) {
				
			}
		}
		
		// 첨부파일이 있으면 파일업로드하기 끝
		String content = boardvo.getContent().replaceAll("\r\n", "<br>");
		boardvo.setContent(content);
		
		int n = 0;
		if(boardvo.getAttach().isEmpty()) {
			// 파일첨부가 없다라면
			n = service.addqna(boardvo);
		}
		if(!boardvo.getAttach().isEmpty()) {
			// 파일첨부가 있다라면
			n = service.addqna_withFile(boardvo);
		}
		
		req.setAttribute("n", n);
	
		return "board/addqnaEnd.tiles";
	} 
	
	
	// ==== #61. 글 1개를 보여주는 페이지 요청하기 ====
	@RequestMapping(value="/qnaview.action", method= {RequestMethod.GET})
	public String requireLogin_qnaview(HttpServletRequest req, HttpServletResponse res) {		
		
		String seq = req.getParameter("seq"); // 글번호 받아오기
		
		BoardVO boardvo = null;	
		
		// ==== #67. 글조회수증가는 반드시 해당 글제목을 클릭했을 경우에만 증가되고 
		//			  웹브라우저에서 새로고침을 했을 경우에는 증가되지 못하도록 한다. ====		
		HttpSession session = req.getSession();
		
		if("yes".equals(session.getAttribute("readCountPermission"))) {
			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
			String userid = null;
			
			if(loginuser != null) {
				userid = loginuser.getUserid();					
			}
			
			boardvo = service.getQnaView(seq, userid); 
			// 조회수 증가한 후에 글 1개를 가져오는 것
			// 단, 자신이 쓴 글을 자신이 읽을시에는 조회수가 증가되지않고,
			// 다른 사람이 쓴 글이여야 조회수가 증가되도록 해야한다.
			// 로그인 하지 않은 상태에서 글을 읽을때 조회수 증가가 일어나지 않도록 한다.
			
			session.removeAttribute("readCountPermission");
			
		}
		else {
			// 웹브라우저에서 새로고침(F5)을 누른경우, 
			// 조회수 증가없이 그냥 글 1개를 가져오는 것
			boardvo = service.getQnaViewWithNoReadCount(seq);			
		}
		
		req.setAttribute("boardvo", boardvo);
		
		// ==== #91. 댓글 내용 가져오기 ====
		List<CommentVO> commentList = service.listComment(seq);
		req.setAttribute("commentList", commentList);
		
		return "board/qnaview.tiles";
	}
	
	
	// ==== #70. 글수정 페이지 요청하기 ====
	@RequestMapping(value="/qnaedit.action", method= {RequestMethod.GET})
	public String requireLogin_qnaedit(HttpServletRequest req, HttpServletResponse response) {		
		
		String seq = req.getParameter("seq"); // 수정해야할 글번호 가져오기
		
		// 수정해야할 글 전체 내용 가져오기
		// 조회수 1증가없이 그냥 글 1개를 가져오는것
		BoardVO boardvo = service.getQnaViewWithNoReadCount(seq);
		
		HttpSession session = req.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(!boardvo.getUserid().equals(loginuser.getUserid())) {
			String msg = "다른 사용자의 글은 수정이 불가합니다.";
			String loc = "javascript:history.back()";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
			return "msg.notiles";
		}
		else {
			// 가져온 1개글을 request 영역에 저장시켜서 view단 페이지로 넘긴다.
			req.setAttribute("boardvo", boardvo);
		}
		return "board/qnaedit.tiles";
	}
	
	
	// ==== #71. 글수정 페이지 완료하기 ====
	@RequestMapping(value="/qnaeditEnd.action", method= {RequestMethod.POST})
	public String qnaeditEnd(BoardVO boardvo, HttpServletRequest req) {
		
		String content = boardvo.getContent().replaceAll("\r\n", "<br>");
		System.out.println("열려라 참깨 : "+content);
		boardvo.setContent(content);
		
		// 글 수정을 하려면 원본글의 암호와 수정시 입력해주는 암호가 일치할 때만
		// 수정이 가능하도록 해야한다.
		
		int n = service.qnaedit(boardvo);
		// n이 1이면 update 성공, 0이면 실패
		
		req.setAttribute("n", n);
		req.setAttribute("seq", boardvo.getSeq());
		
		return "board/qnaeditEnd.tiles";
	}
	
	
	// ==== #77. 글삭제 페이지 요청하기 ====
	@RequestMapping(value="/qnadel.action", method= {RequestMethod.GET})
	public String requireLogin_qnadel(HttpServletRequest req, HttpServletResponse response) {		
		
		String seq = req.getParameter("seq"); // 삭제해야할 글번호 가져오기
		
		// 삭제되어질 글은 자신이 작성한 글이어야만 가능하다.
		// 삭제되어질 글내용을 읽어오면 작성자를 알 수 있다.
		BoardVO boardvo = service.getQnaViewWithNoReadCount(seq);
		
		HttpSession session = req.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(!boardvo.getUserid().equals(loginuser.getUserid())) {
			String msg = "다른 사용자의 글은 삭제가 불가합니다.";
			String loc = "javascript:history.back()";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
			return "msg.notiles";
		}
		else {
			// 삭제해야할 글번호를 request 영역에 저장시켜서 view단 페이지로 넘긴다.
			req.setAttribute("seq", seq);
			
			// 글삭제시 글작성시 암호를 다시 입력받아 암호의 일치여부를 알아보도록 view단 페이지 del.jsp로 넘긴다.
			return "board/qnadel.tiles";
		}
		
	}
	
	
	// ==== #78. 글삭제 페이지 완료하기 ====
	@RequestMapping(value="/qnadelEnd.action", method= {RequestMethod.POST})
	public String qnadelEnd(HttpServletRequest req) throws Throwable {
		
		// 글 삭제를 하려면 원본글의 암호와 삭제시 입력해주는 암호가 일치할 때만
		// 삭제가 가능하도록 해야한다.
		// service단에서 글삭제를 처리한 결과를 int타입으로 받아오겠다.
		
		String seq = req.getParameter("seq");
		String pw = req.getParameter("pw");
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("seq", seq);
		map.put("pw", pw);
		
		int n = service.qnadel(map);
		
		req.setAttribute("n", n);
		
		return "board/qnadelEnd.tiles";
	}
		
	
	// ==== #85. 댓글쓰기 ====
	@RequestMapping(value="/addComment.action", method= {RequestMethod.POST})
	public String requireLogin_addComment(HttpServletRequest req, HttpServletResponse response, CommentVO commentvo) throws Throwable {
		
		// 댓글쓰기(**** AJAX로 처리 ****)
		int n = service.addComment(commentvo); 
		
		JSONArray jsonarry = new JSONArray();
		String str_jsonarray = null;
		
		if(n != 0) {
			// 댓글쓰기와 원게시물(tblBoard 테이블)에 댓글의 갯수(1씩 증가)
			// 증가가 성공했다라면
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", commentvo.getName()); 
			jsonObj.put("content", commentvo.getContent());
			jsonObj.put("regDate", MyUtil.getNowTime());
			
			jsonarry.put(jsonObj);
		}
		
		str_jsonarray = jsonarry.toString();
		
		req.setAttribute("str_jsonarray", str_jsonarray);
		
		return "addCommentEndJSON.notiles";
	}
	
	
	// ==== #148. 첨부파일 다운로드 받기 ====
	@RequestMapping(value="/download.action", method= {RequestMethod.GET})
	public void requireLogin_download(HttpServletRequest req, HttpServletResponse res) {
		String seq = req.getParameter("seq");
		// 첨부파일이 있는 글번호
		
		BoardVO vo =service.getQnaViewWithNoReadCount(seq);
		
		String fileName = vo.getFileName();
		String orgFilename = vo.getOrgFilename();
		
		// WAS(톰캣)의 webapp의 절대경로를 알아와야 한다.
		HttpSession session = req.getSession();
		String root = session.getServletContext().getRealPath("/");
		String path = root + "resources" + File.separator + "files";
		
		boolean bool = false;
		
		bool = fileManager.doFileDownload(fileName, orgFilename, path, res);
		// 다운로드가 성공이면 true로 반납해주고, 그렇지않으면 false를 반납해준다.
		
		if(!bool) {
			// 다운로드가 실패할 경우 메시지를 띄운다.
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = null;
			
			try {
				writer = res.getWriter();
				// 웹브라우저상에 메시지를 쓰기 위한 객체 생성
			} catch (Exception e) {
				
			}
			
			writer.println("<script type='text/javascript'>alert('파일다운로드실패!!');</script>");
			
		}
	}
	
	
	// ==== 회원 개인정보 보기 ====
	@RequestMapping(value="/mamberDatail.action", method= {RequestMethod.GET})
	public String mamberDatail(HttpServletRequest req, HttpServletResponse res) {
		
		String idx = req.getParameter("idx");
		
	//	String str_jsonarray = service.getMemberDetail(idx);
		
	//	req.setAttribute("str_jsonarray", str_jsonarray);
		
		return "mamberDatailJSON.notiles";
	}
		
	
	// ==== #스마트에디터1. 단일사진 파일업로드 ====
	@RequestMapping(value="/image/photoUpload.action", method={RequestMethod.POST})
	public String photoUpload(PhotoVO photovo, HttpServletRequest req) {
	    
		String callback = photovo.getCallback();
	    String callback_func = photovo.getCallback_func();
	    String file_result = "";
	    
		if(!photovo.getFiledata().isEmpty()) {
			// 파일이 존재한다라면
			
			/*
			   1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다.
			   >>>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
			        우리는 WAS 의 webapp/resources/photo_upload 라는 폴더로 지정해준다.
			 */
			
			// WAS 의 webapp 의 절대경로를 알아와야 한다. 
			HttpSession session = req.getSession();
			String root = session.getServletContext().getRealPath("/"); 
			String path = root + "resources"+File.separator+"photo_upload";
			// path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
			
		//	System.out.println(">>>> 확인용 path ==> " + path); 
			// >>>> 확인용 path ==> C:\SpringWorkspaceTeach\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\photo_upload
			
			// 2. 파일첨부를 위한 변수의 설정 및 값을 초기화한 후 파일올리기
			String newFilename = "";
			// WAS(톰캣) 디스크에 저장할 파일명 
			
			byte[] bytes = null;
			// 첨부파일을 WAS(톰캣) 디스크에 저장할때 사용되는 용도 
						
			try {
				bytes = photovo.getFiledata().getBytes(); 
				// getBytes()는 첨부된 파일을 바이트단위로 파일을 다 읽어오는 것이다. 
				/* 2-1. 첨부된 파일을 읽어오는 것
					    첨부한 파일이 강아지.png 이라면
					    이파일을 WAS(톰캣) 디스크에 저장시키기 위해
					    byte[] 타입으로 변경해서 받아들인다.
				*/
				// 2-2. 이제 파일올리기를 한다.
				String original_name = photovo.getFiledata().getOriginalFilename();
				//  photovo.getFiledata().getOriginalFilename() 은 첨부된 파일의 실제 파일명(문자열)을 얻어오는 것이다. 
				newFilename = fileManager.doFileUpload(bytes, original_name, path);
				
		//      System.out.println(">>>> 확인용 newFileName ==> " + newFileName); 
				
				int width = fileManager.getImageWidth(path+File.separator+newFilename);
		//		System.out.println("확인용 >>>>>>>> width : " + width);
				
				if(width > 600)
					width = 600;
		//		System.out.println("확인용 >>>>>>>> width : " + width);
				
				String CP = req.getContextPath();  // board
				file_result += "&bNewLine=true&sFileName="+newFilename+"&sWidth="+width+"&sFileURL="+CP+"/resources/photo_upload/"+newFilename; 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			// 파일이 존재하지 않는다라면
			file_result += "&errstr=error";
		}
	    
		return "redirect:" + callback + "?callback_func="+callback_func+file_result;
		
	}// end of String photoUpload(PhotoVO photovo, HttpServletRequest req)
	
	
	// ==== #스마트에디터2. 드래그앤드롭을 사용한 다중사진 파일업로드 ====
	@RequestMapping(value="/image/multiplePhotoUpload.action", method={RequestMethod.POST})
	public void multiplePhotoUpload(HttpServletRequest req, HttpServletResponse res) {
	    
		/*
		   1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다.
		   >>>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
		        우리는 WAS 의 webapp/resources/photo_upload 라는 폴더로 지정해준다.
		 */
		
		// WAS 의 webapp 의 절대경로를 알아와야 한다. 
		HttpSession session = req.getSession();
		String root = session.getServletContext().getRealPath("/"); 
		String path = root + "resources"+File.separator+"photo_upload";
		// path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
		
	//	System.out.println(">>>> 확인용 path ==> " + path); 
		// >>>> 확인용 path ==> C:\SpringWorkspaceTeach\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\photo_upload   
		
		File dir = new File(path);
		if(!dir.exists())
			dir.mkdirs();
		
		String strURL = "";
		
		try {
			if(!"OPTIONS".equals(req.getMethod().toUpperCase())) {
	    		String filename = req.getHeader("file-name"); //파일명을 받는다 - 일반 원본파일명
	    		
	    //		System.out.println(">>>> 확인용 filename ==> " + filename); 
	    		// >>>> 확인용 filename ==> berkelekle%ED%8A%B8%EB%9E%9C%EB%94%9405.jpg
	    		
	    		InputStream is = req.getInputStream();
	    	/*
	          	요청 헤더의 content-type이 application/json 이거나 multipart/form-data 형식일 때,
	          	혹은 이름 없이 값만 전달될 때 이 값은 요청 헤더가 아닌 바디를 통해 전달된다. 
	          	이러한 형태의 값을 'payload body'라고 하는데 요청 바디에 직접 쓰여진다 하여 'request body post data'라고도 한다.

               	서블릿에서 payload body는 Request.getParameter()가 아니라 
            	Request.getInputStream() 혹은 Request.getReader()를 통해 body를 직접 읽는 방식으로 가져온다. 	
	    	 */
	    		String newFilename = fileManager.doFileUpload(is, filename, path);
	    	
				int width = fileManager.getImageWidth(path+File.separator+newFilename);
			
				if(width > 600)
					width = 600;
				
		//		System.out.println(">>>> 확인용 width ==> " + width);
				// >>>> 확인용 width ==> 600
				// >>>> 확인용 width ==> 121
	    	
				String CP = req.getContextPath(); // board
			
				strURL += "&bNewLine=true&sFileName="; 
            	strURL += newFilename;
            	strURL += "&sWidth="+width;
            	strURL += "&sFileURL="+CP+"/resources/photo_upload/"+newFilename;
	    	}
		
	    	/// 웹브라우저상에 사진 이미지를 쓰기 ///
			PrintWriter out = res.getWriter();
			out.print(strURL);
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}// end of void multiplePhotoUpload(HttpServletRequest req, HttpServletResponse res)
	
	
	//댓글 신고리스트
	@RequestMapping(value="/policeList.action", method={RequestMethod.GET})
	public String requireAdmin_policeList(HttpServletRequest req, HttpServletResponse res){
		
 
		
		  HttpSession session = req.getSession();
		   MemberVO mvo = (MemberVO)session.getAttribute("loginuser");	   
		   
		   String userid = mvo.getUserid();	 
		   
		   
		   String type = req.getParameter("type");
		   
		   if(type==null) {
			   type="qna";
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
		   
			
			if(type.equals("qna")) {
				totalCount = service.getTotalPoliceCommentCount();
			}else {
				totalCount = service.getTotalPolicePCommentCount();
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
		    		   
		    List<HashMap<String, String>> policeList = null;
		    
		    if(type.equals("qna")) {
		    	policeList = service.getListPoliceComment(map);		
			}else {
				policeList = service.getListPolicePComment(map);
			 }
		
		    
		    String pagebar = "<ul>";
			pagebar += MyUtil.getBandallowPageBar("policeList.action", currentShowPageNo, sizePerPage, totalPage, blockSize);
			pagebar += "</ul>";
		
			req.setAttribute("pageBar", pagebar);
			
			String type2= "";
			
			if(type.equals("qna")) {
				type2 = "Q&A게시판";
			}else {
				type2="Plan게시판";
			}
			
		   req.setAttribute("type", type);
		   req.setAttribute("pageBar", pagebar);
		   req.setAttribute("policeList", policeList);
			
	      return "board/policeList.tiles";
	      
	   }
		

	
}
