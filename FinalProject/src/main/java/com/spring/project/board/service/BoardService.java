package com.spring.project.board.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.project.board.model.BoardVO;
import com.spring.project.board.model.CommentVO;
import com.spring.project.board.model.InterBoardDAO;
import com.spring.project.board.model.NoticeVO;

@Service
public class BoardService implements InterBoardService {
	             
	// ==== #31. 의존객체 주입하기(DI : Dependency Injection) ==== 
	@Autowired
	private InterBoardDAO dao;
	

	@Override
	public int getNoticeTotalCount() {
		int count = dao.getTotalCount();
		return count;
	}


	@Override
	public int getNoticeTotalCount2(HashMap<String, String> map) {
		int count = dao.getNoticeTotalCount2(map);
		return count;
	}


	@Override
	public List<NoticeVO> noticeList(HashMap<String, String> map) {
		List<NoticeVO> list = dao.noticeList(map);
		return list;
	}


	@Override
	public List<NoticeVO> noticeList2(HashMap<String, String> map) {
		List<NoticeVO> list = dao.noticeList2(map);
		return list;
	}


	@Override
	public int addNotice(NoticeVO noticevo) {
		int n = dao.addNotice(noticevo);
		return n;
	}
	
	// 공지글 삭제하기
	@Override
	public int noticeDel(HashMap<String, String> map) {
		int n = dao.deleteNotice(map);
		return n;
	}
	
	
	// ==== 검색어가 없는 총게시물의 건수 ====
	@Override
	public int getTotalCount() {
		int count = dao.getTotalCount();
		return count;
	}

	
	// ==== 검색어가 있는 총게시물의 건수 ====
	@Override
	public int getTotalCount2(HashMap<String, String> map) {
		int count = dao.getTotalCount2(map);
		return count;
	}

	
	// ==== 글목록 보여주기(검색어가 없는 것) ====
	@Override
	public List<BoardVO> qnaList(HashMap<String, String> map) {
		List<BoardVO> list = dao.qnaList(map);
		return list;
	}

	
	// ==== 글목록 보여주기(검색어가 있는 것) ====
	@Override
	public List<BoardVO> qnaList2(HashMap<String, String> map) {
		List<BoardVO> list = dao.qnaList2(map);
		return list;
	}

	// ==== 파일첨부가 없는 글쓰기 ====
	@Override
	public int addqna(BoardVO boardvo) {
		// ==== 글쓰기가 원글쓰기인지 아니면 답변글쓰기인지를 구분하여 tblBoard테이블에 insert를 해주어야 한다.
		// 원글쓰기이라면 tblBoard테이블의 groupno컬럼의 값은 max값 + 1로 해야하고 답변글쓰기이라면 넘겨받은 값을 그대로 insert해주어야 한다.
		
		// 원글쓰기인지, 답변글쓰기인지 구분하기
		if(boardvo.getFk_seq() == null || boardvo.getFk_seq().trim().isEmpty()) {
			// 원글쓰기 경우
			int groupno = dao.getGroupMaxno() + 1;
			boardvo.setGroupno(String.valueOf(groupno));
		}
		
		int n = dao.addqna(boardvo);
		return n;
	}

	// ==== 파일첨부가 있는 글쓰기 ====
	@Override
	public int addqna_withFile(BoardVO boardvo) {
		// 글쓰기가 원글쓰기인지 아니면 답변글쓰기인지를 구분하여 tblBoard테이블에 insert를 해주어야 한다.
		// 원글쓰기이라면 tblBoard테이블의 groupno컬럼의 값은 max값 + 1로 해야하고 답변글쓰기이라면 넘겨받은 값을 그대로 insert해주어야 한다.
		
		// 원글쓰기인지, 답변글쓰기인지 구분하기
		if(boardvo.getFk_seq() == null || boardvo.getFk_seq().trim().isEmpty()) {
			// 원글쓰기 경우
			int groupno = dao.getGroupMaxno() + 1;
			boardvo.setGroupno(String.valueOf(groupno));
		}
		
		int n = dao.addqna_withFile(boardvo);
		return n;
	}

	
	// ==== 글 1개를 보여주는 페이지 요청하기 ====
	@Override
	public BoardVO getQnaView(String seq, String userid) {
		BoardVO boardvo = dao.getQnaView(seq);
		
		if(userid != null && !boardvo.getUserid().equals(userid)) {
			dao.setAddReadCount(seq);
			boardvo = dao.getQnaView(seq);
		}
			
		return boardvo;
	}

	
	// ==== 조회수 증가없이 그냥 글 1개를 가져오는 것 ====
	@Override
	public BoardVO getQnaViewWithNoReadCount(String seq) {
		BoardVO boardvo = dao.getQnaView(seq);
		return boardvo;
	}

	
	// ==== #86. 댓글쓰기 ====
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public int addComment(CommentVO commentvo) throws Throwable {
		int result = 0;
		
		int n = 0;
		n = dao.addComment(commentvo);
		// tblComment 테이블에 insert
		
		if(n == 1) {
			result = dao.updateCommentCount(commentvo.getParentSeq());
			// tblBoard 테이블에 commentCount컬럼의 값이 1증가(update)
		}		
		
		return result;
	}

	
	// ==== #92. 댓글내용 보여주기 ====
	@Override
	public List<CommentVO> listComment(String seq) {
		List<CommentVO> list = dao.listComment(seq);
		return list;
	}

	
	// ==== #72. 1개 글 수정하기 ====
	@Override
	public int qnaedit(BoardVO boardvo) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("seq", boardvo.getSeq());
		map.put("pw", boardvo.getPw());		
		
		boolean checkPw = dao.checkPw(map);
		// 글번호에 대한 암호가 일치하면 true반환, 그렇지않으면 false반환
		
		int n = 0;
		
		if(checkPw) {
			n = dao.updateContent(boardvo); // 1개 글 수정하기
		}
		
		return n;
	}

	
	// ==== #79. 1개 글 삭제하기 ====
	@Override
	public int qnadel(HashMap<String, String> map) throws Throwable {
		boolean checkPw = dao.checkPw(map);
		// 글번호에 대한 암호가 일치하면 true반환, 그렇지않으면 false반환
		
		int result1 = 0, result2 = 0, n = 0;
		boolean bool = false;
		
		if(checkPw) {
			// ==== #97. 원게시글에 딸린 댓글이 있는지 없는지를 확인하기 ====
			bool = dao.isExistsComment(map);
			
			result1 = dao.deleteContent(map); // 1개 글 삭제하기
			
			if(bool) {
				// ==== #98. 원게시글에 딸린 댓글 삭제하기 ====
				result2 = dao.deleteComment(map);
			}
		}
		
		if((bool == true && result1 == 1 && result2 > 0) || (bool == false && result1 == 1 && result2 == 0)) {
			n = 1;
		} 
		
		return n;
	}


	@Override
	public int getTotalPoliceCommentCount() {
		int count = dao.getTotalPoliceCommentCount();
		return count;
	}


	@Override
	public int getTotalPolicePCommentCount() {
		int count = dao.getTotalPolicePCommentCount();
		return count;
	}


	@Override
	public List<HashMap<String, String>> getListPoliceComment(HashMap<String, String> map) {
		List<HashMap<String, String>> list =dao.getListPoliceComment(map);
		return list;  
	}

	
	@Override
	public List<HashMap<String, String>> getListPolicePComment(HashMap<String, String> map) {
		List<HashMap<String, String>> list =dao.getListPolicePComment(map);
		return list;    
	} 



}
