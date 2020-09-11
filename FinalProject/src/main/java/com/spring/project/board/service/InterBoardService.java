package com.spring.project.board.service;

import java.util.HashMap;
import java.util.List;

import com.spring.project.board.model.BoardVO;
import com.spring.project.board.model.CommentVO;
import com.spring.project.board.model.NoticeVO;

public interface InterBoardService {
	
	int getNoticeTotalCount(); // 검색어가 없는 총게시물의 건수
	int getNoticeTotalCount2(HashMap<String, String> map); // 검색어가 있는 총게시물의 건수
	
	List<NoticeVO> noticeList(HashMap<String, String> map); // 글목록 보여주기(검색어가 없는 것)
	List<NoticeVO> noticeList2(HashMap<String, String> map); // 글목록 보여주기(검색어가 있는 것)
	
	int addNotice(NoticeVO noticevo);
	
	int noticeDel(HashMap<String, String> map); // 공지글 삭제하기
	
	int getTotalCount(); // 검색어가 없는 총게시물의 건수
	int getTotalCount2(HashMap<String, String> map); // 검색어가 있는 총게시물의 건수
	
	List<BoardVO> qnaList(HashMap<String, String> map); // 글목록 보여주기(검색어가 없는 것)
	List<BoardVO> qnaList2(HashMap<String, String> map); // 글목록 보여주기(검색어가 있는 것)

	int addqna(BoardVO boardvo); // 파일첨부가 없는 질문글쓰기 완료
	int addqna_withFile(BoardVO boardvo); // 파일첨부가 있는 질문글쓰기 완료
	
	BoardVO getQnaView(String seq, String userid); // 글 1개를 보여주기

	BoardVO getQnaViewWithNoReadCount(String seq); // 조회수 증가없이 그냥 글 1개를 가져오는 것
	
	int addComment(CommentVO commentvo) throws Throwable; // 댓글쓰기
	
	List<CommentVO> listComment(String seq); // 댓글내용 보여주기
	
	int qnaedit(BoardVO boardvo); // 1개 글 수정하기

	int qnadel(HashMap<String, String> map) throws Throwable; // 1개 글 삭제하기
	
	int  getTotalPoliceCommentCount();
	
	int  getTotalPolicePCommentCount();
	
	List<HashMap<String, String>> getListPoliceComment(HashMap<String, String> map);
	List<HashMap<String, String>> getListPolicePComment(HashMap<String, String> map);
 
	
}
