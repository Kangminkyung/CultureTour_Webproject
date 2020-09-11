package com.spring.project.band.service;

import java.util.HashMap;
import java.util.List;

import com.spring.project.band.model.BandImageVO;
import com.spring.project.band.model.BandNoticeVO;
import com.spring.project.band.model.BandPlanVO;
import com.spring.project.band.model.BandVO;
import com.spring.project.band.model.PCommentVO;
import com.spring.project.band.model.PhotoVO;
import com.spring.project.board.model.BoardVO;
import com.spring.project.board.model.NoticeVO;

public interface InterBandService {

	List<HashMap<String, String>> getBandThema(); // 밴드테마 가져오기
	
//	List<BandVO> getBandList(); // 썸네일 포함 전 밴드 리스트 불러오는 코드
//	List<HashMap<String, String>> getBandList(); //썸네일 포함 후 밴드 리스트 불러오는 코드
	
	int getBcodeseq(); // 먼저 밴드일련번호 구해오기
	
	int addBand(HashMap<String, String> mapBand); // 밴드 등록하기

	int addBandImage(HashMap<String, String> mapBandImage); // 벤드 이미지 테이블에  밴드 이미지파일 등록하기

	
	List<HashMap<String, String>> getListMyband(String userid);// 마이페이지에서 가입한 밴드목록 리스트 불러오는 코드

	BandVO getBandInfo(String bcode); // 메인메뉴 - 밴드 정보 불러오는 코드

	
	
	////////////////페이징 처리/////////////////////////////
	List<HashMap<String, String>> bandList(HashMap<String, String> map); // 썸네일 포함 검색어가 없는 밴드 리스트 불러오기
	List<HashMap<String, String>> bandList2(HashMap<String, String> map); // 썸네일 포함 검색어가 있는 밴드 리스트 불러오기

	int getTotalBandCount(); // 검색어가 없는 총게시물 건수
	int getTotalBandCount2(HashMap<String, String> map); // 검색어가 있는 총게시물 건수

	List<HashMap<String, String>> getViewBandImage(String bcode); // 특정 밴드 info 페이지에 밴드 이미지 불러오기

	
	///////////////// 밴드 공지사항 /////////////////
	int getBandNoticeTotalCount(HashMap<String, String> map); // 검색어가 없는 총게시물의 건수
	int getBandNoticeTotalCount2(HashMap<String, String> map); // 검색어가 있는 총게시물의 건수
	
	List<BandNoticeVO> bandNoticeList(HashMap<String, String> map); // 글목록 보여주기(검색어가 없는 것)
	List<BandNoticeVO> bandNoticeList2(HashMap<String, String> map); // 글목록 보여주기(검색어가 있는 것)

	int addBandNotice(BandNoticeVO bandnoticevo); // 파일첨부가 없는 글쓰기 완료
	
	BandNoticeVO getBandNoticeView(String seq, String userid); // 글 1개를 보여주기

	BandNoticeVO getViewWithNoReadCount(String seq); // 조회수 증가없이 그냥 글 1개를 가져오는 것
	
	int bandNoticeEdit(BandNoticeVO boardvo); // 1개 글 수정하기

	int bandNoticeDel(HashMap<String, String> map) throws Throwable; // 1개 글 삭제하기
	/////////////////////////////////
	
	int editBand(HashMap<String, String> mapBand); // 밴드 수정하기
	int deleteBandImage(String bcode);  // 밴드 수정(이미지 첨부) 기본 final_bandImage 테이블에 저장된 이미지는 전부 삭제함
	int countBandImage(String bcode); // 기존에 final_bandImage 테이블에 저장되어있던 수정 전 이미지 파일 갯수 구하기 

	int getBandDel(String bcode); // 밴드삭제

	int getbandJoin(HashMap<String, String> map); // 밴드 참가신청 insert

	String getbandjang(HashMap<String, String> map); // 밴드장 구해오기 

	String getalreadybandjohn(HashMap<String, String> map); // 이미 가입한 밴드면 userid 구해오기.

	List<HashMap<String, String>> getBandMember(HashMap<String, String> map); // 밴드 멤버 구하기

	int getBandTotalCount( HashMap<String, String> map);// 총 밴드원 수 구하기

	int getjoinallow( HashMap<String,String> map) throws Throwable; // 밴드 신청 승인
	
	int getMyCreateBandCount(String userid); // 내가 만든 밴드의 총수를 가져오는 메소드

	int getMyJoinBandCount(String userid); // 내가 가입한 밴드의 수를 가져오는 메소드
	
	List<HashMap<String, String>> getListMyCreateband(HashMap<String, String> map);// 내가 만든 밴드의 수를 가져옴

	List<HashMap<String, String>> getListMyJoinband(HashMap<String, String> map); // 내가 가입한 밴드의 수를 구하기

	int deleteMyBand(HashMap<String,String> map); // 내가 가입한 밴드 탈퇴


	// 밴드플랜
	int getPnumseq(); // 먼저 밴드플랜일련번호 채번해오기

	int getBandPlanAdd(BandPlanVO planvo); // 플랜등록

	List<HashMap<String, String>> getBandPlanList(String bcode); // 플랜 리스트 가지고오기
	
	int getTotalPlanCount2(HashMap<String, String> map); // 검색어가 있는 플랜 건수
	
	List<HashMap<String, String>> getPlanList2(HashMap<String, String> map); // 검색어가 있는 페이징처리
	
	BandPlanVO getPlanView(String pnum, String userid); //플랜보기

	int getCultureIdx(String cultureSearch); // 밴드 플랜 페이지에서 문화재 번호가져오기
	
	int getBandPlanEdit(BandPlanVO planvo); // 플랜수정
	
	int getBandPlanDel(String pnum); // 플랜삭제

	int addPComment(PCommentVO pcommentvo) throws Throwable; // 플랜 댓글쓰기(**** AJAX로 처리 ****)
	
	List<PCommentVO> listPComment(String pnum); // 플랜 댓글내용 보여주기

	BandPlanVO getPlanViewWithNoReadCount(String pnum); // 조회수 증가없이 그냥 플랜 1개를 가져오는 것 

	List<PhotoVO> photoList(String bcode); // 사진게시판 목록

	int addPhoto(PhotoVO photovo);
	
	int getbandallowCount(); // 가입 신청 밴드 총갯수 가져오기(페이징처리용)
	
	List<HashMap<String, String>> getbandallow(HashMap<String, String> map);// 밴드 status= 0 인 밴드 가져오기

	int getoneallowband(String bandIdxModal); // 밴드 가입 신청 승인 status 0 에서 1(관리자 승인)

	int getcheckboxbandallow(HashMap<String, String[]> map);
	
	// ---------------------- 신고 --------------------
	int policePcomment(HashMap<String, String> map); // 밴드 플랜 댓글 신고
	int policeComment(HashMap<String, String> map); // 자유게시판 댓글 신고

	int policePuserid(HashMap<String, String> map); // 밴드플랜 댓글 중복 신고 (policePcomment 테이블에 동일한 fk_seq에 userid 있는지 확인)
	
	int policeUserid(HashMap<String, String> map); // 밴드플랜 댓글 중복 신고 (policePcomment 테이블에 동일한 fk_seq에 userid 있는지 확인)

	

	

}
