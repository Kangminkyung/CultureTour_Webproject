package com.spring.project.board.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO implements InterBoardDAO {
	
	// ==== 의존객체 주입하기(DI : Dependency Injection) ==== 
	@Autowired
	private SqlSessionTemplate sqlsession;

	
	@Override
	public int getNoticeTotalCount() {
		int count = sqlsession.selectOne("board.getNoticeTotalCount");
		return count;
	}

	@Override
	public int getNoticeTotalCount2(HashMap<String, String> map) {
		int count = sqlsession.selectOne("board.getNoticeTotalCount2", map);
		return count;
	}

	@Override
	public List<NoticeVO> noticeList(HashMap<String, String> map) {
		List<NoticeVO> list = sqlsession.selectList("board.noticeList", map);
		return list;
	}

	@Override
	public List<NoticeVO> noticeList2(HashMap<String, String> map) {
		List<NoticeVO> list = sqlsession.selectList("board.noticeList2", map);
		return list;
	}
	
	
	@Override
	public int addNotice(NoticeVO noticevo) {
		int n = sqlsession.insert("board.addNotice", noticevo);
		return n;
	}
		
	// 공지글삭제하기
	@Override
	public int deleteNotice(HashMap<String, String> map) {
		int n = sqlsession.update("board.deleteNotice", map);
		return n;
	}
	
	
	// ==== 검색어가 없는 총게시물의 건수 ====
	@Override
	public int getTotalCount() {
		int count = sqlsession.selectOne("board.getTotalCount");
		return count;
	}

	
	// ==== 검색어가 있는 총게시물의 건수 ====
	@Override
	public int getTotalCount2(HashMap<String, String> map) {
		int count = sqlsession.selectOne("board.getTotalCount2", map);
		return count;
	}

	
	// ==== 글목록 보여주기(검색어가 없는 것) ====
	@Override
	public List<BoardVO> qnaList(HashMap<String, String> map) {
		List<BoardVO> list = sqlsession.selectList("board.qnaList", map);
		return list;
	}

	
	// ==== 글목록 보여주기(검색어가 있는 것) ====
	@Override
	public List<BoardVO> qnaList2(HashMap<String, String> map) {
		List<BoardVO> list = sqlsession.selectList("board.qnaList2", map);
		return list;
	}

	
	// ==== final_board테이블의 groupno컬럼의 max값 알아오기 ====
	@Override
	public int getGroupMaxno() {
		int max = sqlsession.selectOne("board.getGroupMaxno");
		return max;
	}

	
	// ==== 파일첨부가 없는 글쓰기 ====
	@Override
	public int addqna(BoardVO boardvo) {
		int n = sqlsession.insert("board.addqna", boardvo);
		return n;
	}

	
	// ==== 파일첨부가 있는 글쓰기 ====
	@Override
	public int addqna_withFile(BoardVO boardvo) {
		int n = sqlsession.insert("board.addqna_withFile", boardvo);
		return n;
	}

	
	// ==== 글 1개를 보여주기 ====
	@Override
	public BoardVO getQnaView(String seq) {
		BoardVO boardvo = sqlsession.selectOne("board.getQnaView", seq);
		return boardvo;
	}

	
	// ==== 글 1개를 볼때 조회수 1증가 시키기 ====
	@Override
	public void setAddReadCount(String seq) {
		sqlsession.update("board.setAddReadCount",seq);			
	}

	
	// ==== 댓글쓰기 ====
	@Override
	public int addComment(CommentVO commentvo) {
		int n = sqlsession.insert("board.addComment", commentvo);
		return n;
	}


	// ==== 댓글쓰기 이후에 댓글의 갯수 1증가 시키기 ====	
	@Override
	public int updateCommentCount(String parentSeq) {
		int n = sqlsession.update("board.updateCommentCount", parentSeq);
		return n;
	}

	
	// ==== 댓글내용 보여주기 ====
	@Override
	public List<CommentVO> listComment(String seq) {
		List<CommentVO> list = sqlsession.selectList("board.listComment", seq);
		return list;
	}

	
	// ==== 글수정 및 글삭제시 암호일치 여부 알아오기 ====
	@Override
	public boolean checkPw(HashMap<String, String> map) {
		int n = sqlsession.selectOne("board.checkPw", map);
		boolean result = false;
		
		if(n == 1) 
			result = true;
		
		return result;
	}

	
	// ==== 1개 글 수정하기 ====
	@Override
	public int updateContent(BoardVO boardvo) {
		int n = sqlsession.update("board.updateContent", boardvo);
		return n;
	}

	
	// ==== 원게시글에 딸린 댓글이 있는지 없는지를 확인하기 ====
	@Override
	public boolean isExistsComment(HashMap<String, String> map) {
		int count = sqlsession.selectOne("board.isExistsComment", map);
		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	

	// ==== 1개 글 삭제하기 ====
	@Override
	public int deleteContent(HashMap<String, String> map) {
		int n = sqlsession.update("board.deleteContent", map);
		return n;
	}

	
	// ==== 원게시글에 딸린 댓글 삭제하기 ====
	@Override
	public int deleteComment(HashMap<String, String> map) {
		int n = sqlsession.update("board.deleteComment", map);
		return n;
	}

	@Override
	public int getTotalPoliceCommentCount() {
		int cnt = sqlsession.selectOne("board.getTotalPoliceCommentCount");
		return cnt;
	}

	@Override 
	public int getTotalPolicePCommentCount() {
		int cnt = sqlsession.selectOne("board.getTotalPolicePCommentCount");
		return cnt; 
	}

	@Override
	public List<HashMap<String, String>> getListPoliceComment(HashMap<String, String> map) {
		List<HashMap<String, String>> list = sqlsession.selectList("board.getListPoliceComment", map);
		return list;
	}

	@Override
	public List<HashMap<String, String>> getListPolicePComment(HashMap<String, String> map) {
		List<HashMap<String, String>> list = sqlsession.selectList("board.getListPolicePComment", map);
		return list;
	}

	
	

}
