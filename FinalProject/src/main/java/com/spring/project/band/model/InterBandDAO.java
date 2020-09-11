package com.spring.project.band.model;

import java.util.HashMap;
import java.util.List;

import com.spring.project.board.model.BoardVO;
import com.spring.project.board.model.NoticeVO;

public interface InterBandDAO {

//	List<BandVO> getBandList(); // 썸네일 포함 전 밴드 리스트 불러오는 코드
//	List<HashMap<String, String>> getBandList(); //썸네일 포함 후 밴드 리스트 불러오는 코드
	
	List<HashMap<String, String>> getBandThema(); // 밴드테마 가져오기

	int getBcodeseq(); // 먼저 밴드일련번호 채번해오기

	int addBand(HashMap<String, String> mapBand); // 밴드 등록하기

	int addBandImage(HashMap<String, String> mapBandImage); // 벤드 등록시  밴드 이미지파일 등록하기

	List<HashMap<String, String>> getListMyband(String userid); // 마이페이지에서 가입한 밴드목록 리스트 불러오는 코드

	BandVO getBandInfo(String bcode); // 메인메뉴 - 밴드 정보 페이지 불러오는 코드

	
	////////////////페이징처리///////////////////////
	List<HashMap<String, String>> getbandList(HashMap<String, String> map); // 썸네일 포함 검색어가 없는 밴드 리스트 불러오기
	List<HashMap<String, String>> getbandList2(HashMap<String, String> map); // 썸네일 포함 검색어가 있는 밴드 리스트 불러오기

	int getTotalBandCount(); // 검색어가 없는 총게시물 건수
	int getTotalBandCount2(HashMap<String, String> map); // 검색어가 있는 총게시물 건수

	List<HashMap<String, String>> getViewBandImage(String bcode); // 특정밴드 info 페이지에 밴드 이미지 불러오기


	
	///////////////// 밴드 공지사항 //////////////////
	int getBandNoticeTotalCount(HashMap<String, String> map); // 검색어가 없는 총게시물의 건수
	int getBandNoticeTotalCount2(HashMap<String, String> map); // 검색어가 있는 총게시물의 건수
	
	List<BandNoticeVO> bandNoticeList(HashMap<String, String> map); // 글목록 보여주기(검색어가 없는 것)
	List<BandNoticeVO> bandNoticeList2(HashMap<String, String> map); // 글목록 보여주기(검색어가 있는 것)	

	int addBandNotice(BandNoticeVO bandnoticevo); // 글쓰기
	
	BandNoticeVO getBandNoticeView(String seq); // 글 1개를 보여주기
	
	void setAddReadCount(String seq); // 글 1개를 볼때 조회수(readCount) 1증가 시키기
	
	boolean checkPw(HashMap<String, String> map); // 글수정 및 글삭제시 암호일치 여부 알아오기
	
	int updateContent(BandNoticeVO bandnoticevo); // 1개 글 수정하기
	
	int deleteContent(HashMap<String, String> map); // 1개 글 삭제하기	
	///////////////////////////////////////////////////
	
	int editBand(HashMap<String, String> mapBand); // 밴드 수정하기
	int deleteBandImage(String bcode); // 밴드 수정하기(이미지 첨부) 기본 final_bandImage 테이블에 저장된 이미지는 전부 삭제함

	int countBandImage(String bcode); // 기존에 final_bandImage 테이블에 저장되어있던 수정 전 이미지 파일 갯수 구하기 

	int getBandDel(String bcode); // 밴드삭제

	int getbandJoin(HashMap<String, String> map); // 밴드 참가신청 insert

	String getbandjang(HashMap<String, String> map); // 밴드장 구하기

	String getalreadybandjohn(HashMap<String, String> map); //  이미 가입한 밴드면 userid 구해오기.

	List<HashMap<String, String>> getBandMember(HashMap<String, String> map); // 밴드멤버 가져오기

	int getBandTotalCount( HashMap<String, String> map); // 총 밴드원 수 구하기

	int getjoinallow( HashMap<String,String> map);
	
	int getMyCreateBandCount(String userid); // 내가 만든 밴드의 총 수를 가져오는 메소드

	int getMyJoinBandCount(String userid); // 내가 가입한 밴드이 총 수를 ㅏㄱ져ㅗ는 메소드

	List<HashMap<String, String>> getListMyJoinband(HashMap<String, String> map);
	
	List<HashMap<String, String>> getListMyCreateband(HashMap<String, String> map); // 마이페이지에서 가입한 밴드목록 리스트 불러오는 코드

	int getmembercountup( HashMap<String,String> map); // 밴드 멤버 카운트 1 올리기

	int deleteMyBand(HashMap<String,String> map); // 내가 가입한 밴드 탈퇴

	// 밴드플랜 	
	int getPnumseq(); // 먼저 밴드플랜일련번호 채번해오기

	int getBandPlanAdd(BandPlanVO planvo); // 플랜등록

	List<HashMap<String, String>> getBandPlanList(String bcode); // 플랜 리스트 가지고오기

	int getTotalPlanCount2(HashMap<String, String> map); // 검색어가 있는 플랜 건수

	List<HashMap<String, String>> getPlanList2(HashMap<String, String> map); // 검색어가 있는 페이징처리

	BandPlanVO getPlanView(String pnum); //플랜보기

	int getCultureIdx(String cultureSearch); // 밴드 플랜 페이지에서 문화재 번호가져오기

	int getBandPlanEdit(BandPlanVO planvo); // 플랜수정

	int getBandPlanDel(String pnum); // 플랜삭제

	int addPComment(PCommentVO pcommentvo); // 플랜 댓글 등록
	int updatePCommentCount(String parentPnum); // 댓글쓰기 이후에 댓글의 갯수 1증가 시키기

	List<PCommentVO> listPComment(String pnum); // 플랜 댓글내용 보여주기
	
	void setAddReadCount2(String pnum); // 플랜 1개를 볼때 조회수(readCount) 1증가 시키기

	List<PhotoVO> photoList(String bcode); // 사진게시판 목록

	int addPhoto(PhotoVO photovo);

	int getbandallowCount(); // 밴드가입신청 총갯수 가져오기

	List<HashMap<String, String>> getbandallow(HashMap<String, String> map); // 밴드 status= 0 인밴드 리스트가져오기

	int getoneallowband(String bcode); 	// 밴드 가입 신청 승인 status 0 에서 1(관리자 승인)

	int getcheckboxbandallow(HashMap<String, String[]> map);
	
	
	int policePcomment(HashMap<String, String> map); // 밴드 플랜 댓글 신고

	int policeComment(HashMap<String, String> map); // 자유게시판 댓글 신고

	int policePuserid(HashMap<String, String> map); // 밴드플랜 댓글 중복 신고 (policePcomment 테이블에 동일한 fk_seq에 userid 있는지 확인)
	
	int policeUserid(HashMap<String, String> map); // 자유게시판 댓글 중복 신고 (policecomment 테이블에 동일한 fk_seq에 userid 있는지 확인)



}
