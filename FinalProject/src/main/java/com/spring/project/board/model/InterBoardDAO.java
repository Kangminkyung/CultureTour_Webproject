package com.spring.project.board.model;

import java.util.HashMap;
import java.util.List;

public interface InterBoardDAO {
	
	int getNoticeTotalCount(); // 검색어가 없는 총게시물의 건수
	int getNoticeTotalCount2(HashMap<String, String> map); // 검색어가 있는 총게시물의 건수
	
	List<NoticeVO> noticeList(HashMap<String, String> map); // 글목록 보여주기(검색어가 없는 것)
	List<NoticeVO> noticeList2(HashMap<String, String> map); // 글목록 보여주기(검색어가 있는 것)
	
	int addNotice(NoticeVO noticevo);
	
	int deleteNotice(HashMap<String, String> map); // 공지글삭제하기

	int getTotalCount(); // 검색어가 없는 총게시물의 건수
	int getTotalCount2(HashMap<String, String> map); // 검색어가 있는 총게시물의 건수
	
	List<BoardVO> qnaList(HashMap<String, String> map); // 글목록 보여주기(검색어가 없는 것)
	List<BoardVO> qnaList2(HashMap<String, String> map); // 글목록 보여주기(검색어가 있는 것)
	
	int getGroupMaxno(); // final_board테이블의 groupno컬럼의 max값 알아오기
	
	int addqna(BoardVO boardvo); // 파일첨부가 없는 글쓰기
	int addqna_withFile(BoardVO boardvo); // 파일첨부가 있는 글쓰기
	
	BoardVO getQnaView(String seq); // 글 1개를 보여주기
	
	void setAddReadCount(String seq); // 글 1개를 볼때 조회수(readCount) 1증가 시키기
	
	int addComment(CommentVO commentvo); // 댓글쓰기	
	int updateCommentCount(String parentSeq); // 댓글쓰기 이후에 댓글의 갯수 1증가 시키기
	
	List<CommentVO> listComment(String seq); // 댓글내용 보여주기
	
	boolean checkPw(HashMap<String, String> map); // 글수정 및 글삭제시 암호일치 여부 알아오기
	
	int updateContent(BoardVO boardvo); // 1개 글 수정하기
	
	boolean isExistsComment(HashMap<String, String> map); // 원게시글에 딸린 댓글이 있는지 없는지를 확인하기	
	int deleteContent(HashMap<String, String> map); // 1개 글 삭제하기
	int deleteComment(HashMap<String, String> map); // 원게시글에 딸린 댓글 삭제하기	
	
	int getTotalPoliceCommentCount();
	int getTotalPolicePCommentCount();
	     
	List<HashMap<String, String>> getListPoliceComment(HashMap<String, String> map);    
	List<HashMap<String, String>> getListPolicePComment(HashMap<String, String> map);
	  
}
