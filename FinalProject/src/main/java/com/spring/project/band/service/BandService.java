package com.spring.project.band.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.project.band.model.BandImageVO;
import com.spring.project.band.model.BandNoticeVO;
import com.spring.project.band.model.BandPlanVO;
import com.spring.project.band.model.BandVO;
import com.spring.project.band.model.InterBandDAO;
import com.spring.project.band.model.PCommentVO;
import com.spring.project.band.model.PhotoVO;
import com.spring.project.board.model.BoardVO;
import com.spring.project.board.model.NoticeVO;
import com.spring.project.culture.model.CultureVO;

//===== #30. Service 선언 ======
@Service
public class BandService implements InterBandService{

	// ===== #31. 의존객체 주입하기(DI : Dependency Injection) =====
	@Autowired
	private InterBandDAO dao;

	 // 썸네일 포함 전 밴드 리스트 불러오는 코드
/*	@Override
	public List<BandVO> getBandList() {
		List<BandVO> bandList = dao.getBandList();
		return bandList;
	}*/
	
	//썸네일 포함 후 밴드 리스트 불러오는 코드 (페이징처리 전)
	/*public List<HashMap<String, String>> getBandList(){
		List<HashMap<String, String>> bandList = dao.getBandList();
		return bandList;
	}*/
	
	// 밴드테마 가져오기
	@Override
	public List<HashMap<String, String>> getBandThema() {
		List<HashMap<String, String>> bandThemaList = dao.getBandThema();
		return bandThemaList;
	}

	@Override
	public int getBcodeseq() {
		// 새로이 입력할 제품번호(시퀀스) 가져오기
		int bcode = dao.getBcodeseq();
		return bcode;
	}

	// 밴드 등록하기
	@Override
	public int addBand(HashMap<String, String> mapBand) {
		int n = dao.addBand(mapBand);
		return n;
	}

	// 벤드 등록시  밴드 이미지파일 등록하기
	@Override
	public int addBandImage(HashMap<String, String> mapBandImage) {
		int n = dao.addBandImage(mapBandImage);
		return n;
	}

		
	// 마이페이지에서 가입한 밴드목록 리스트 불러오는 코드
	@Override
	public List<HashMap<String, String>> getListMyband(String userid) {
		List<HashMap<String, String>> mybandList = dao.getListMyband(userid);
		return mybandList;
	}

	// 메인메뉴 - 밴드 정보 페이지 불러오는 코드
	@Override
	public BandVO getBandInfo(String bcode) {
		BandVO bandInfo = dao.getBandInfo(bcode);
		return bandInfo;
	}

	

	/////////////////////페이징처리///////////////////////////////////////
	// 썸네일 포함 검색어가 없는 밴드 리스트 불러오기
	@Override
	public List<HashMap<String, String>> bandList(HashMap<String, String> map) {
		List<HashMap<String, String>> bandList = dao.getbandList(map);
		return bandList;
	}
	// 썸네일 포함 검색어가 있는 밴드 리스트 불러오기
	@Override
	public List<HashMap<String, String>> bandList2(HashMap<String, String> map) {
		List<HashMap<String, String>> bandList = dao.getbandList2(map);
		return bandList;
	}

	// 검색어가 없는 총게시물 건수
	@Override
	public int getTotalBandCount() {
		int totalBandCount = dao.getTotalBandCount();
		return totalBandCount;
	}

	// 검색어가 있는 총게시물 건수
	@Override
	public int getTotalBandCount2(HashMap<String, String> map) {
		int totalBandCount = dao.getTotalBandCount2(map);
		return totalBandCount;
	}

	// 밴드 info 페이지에 밴드 이미지 불러오기
	@Override
	public List<HashMap<String, String>> getViewBandImage(String bcode) {
		List<HashMap<String, String>> viewBandImageList = dao.getViewBandImage(bcode);
		return viewBandImageList;
	}

	
	
	///////////////// 밴드 공지사항 /////////////////
	@Override
	public int getBandNoticeTotalCount(HashMap<String, String> map) {
		int count = dao.getBandNoticeTotalCount(map);
		return count;
	}

	@Override
	public int getBandNoticeTotalCount2(HashMap<String, String> map) {
		int count = dao.getBandNoticeTotalCount2(map);
		return count;
	}

	@Override
	public List<BandNoticeVO> bandNoticeList(HashMap<String, String> map) {
		List<BandNoticeVO> list = dao.bandNoticeList(map);
		return list;
	}

	@Override
	public List<BandNoticeVO> bandNoticeList2(HashMap<String, String> map) {
		List<BandNoticeVO> list = dao.bandNoticeList2(map);
		return list;
	}

	@Override
	public int addBandNotice(BandNoticeVO bandnoticevo) {		
		int n = dao.addBandNotice(bandnoticevo);
		return n;
	}
	
	// ==== 글 1개를 보여주는 페이지 요청하기 ====
	@Override
	public BandNoticeVO getBandNoticeView(String seq, String userid) {
		BandNoticeVO bandnoticevo = dao.getBandNoticeView(seq);
		
		if(userid != null && !bandnoticevo.getUserid().equals(userid)) {
			dao.setAddReadCount(seq);
			bandnoticevo = dao.getBandNoticeView(seq);
		}
			
		return bandnoticevo;
	}

	// ==== 조회수 증가없이 그냥 글 1개를 가져오는 것 ====
	@Override
	public BandNoticeVO getViewWithNoReadCount(String seq) {
		BandNoticeVO bandnoticevo = dao.getBandNoticeView(seq);
		return bandnoticevo;
	}

	@Override
	public int bandNoticeEdit(BandNoticeVO bandnoticevo) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("seq", bandnoticevo.getSeq());
		map.put("pw", bandnoticevo.getPw());		
		
		boolean checkPw = dao.checkPw(map);
		// 글번호에 대한 암호가 일치하면 true반환, 그렇지않으면 false반환
		
		int n = 0;
		
		if(checkPw) {
			n = dao.updateContent(bandnoticevo); // 1개 글 수정하기
		}
		
		return n;
	}

	@Override
	public int bandNoticeDel(HashMap<String, String> map) throws Throwable {
		boolean checkPw = dao.checkPw(map);
		// 글번호에 대한 암호가 일치하면 true반환, 그렇지않으면 false반환
		
		int result = 0, n = 0;
		boolean bool = false;
		
		if(checkPw) {
			result = dao.deleteContent(map); // 1개 글 삭제하기
		}
		
		if((bool == true && result == 1) || (bool == false && result == 1)) {
			n = 1;
		} 
		
		return n;
	}
	
	/////////////////////////////////

	// 밴드 수정하기
	@Override
	public int editBand(HashMap<String, String> mapBand) {
		int n = dao.editBand(mapBand);
		return n;
	}

	// 밴드 수정하기(이미지 첨부) 기본 final_bandImage 테이블에 저장된 이미지는 전부 삭제함
	@Override
	public int deleteBandImage(String bcode) {
		int n = dao.deleteBandImage(bcode);
		return n;
	}

	// 기존에 final_bandImage 테이블에 저장되어있던 수정 전 이미지 파일 갯수 구하기 
	@Override
	public int countBandImage(String bcode) {
		int n = dao.countBandImage(bcode);
		return n;
	}

	// 밴드삭제
	@Override
	public int getBandDel(String bcode) {
		int n = dao.getBandDel(bcode);
		return n;
	}
	
	@Override
	public int getbandJoin(HashMap<String, String> map) {
		
		int n = dao.getbandJoin(map);
		
		return n;
	}

	@Override
	public String getbandjang(HashMap<String, String> map) {
		
		String bandjang = dao.getbandjang(map);
		
		return bandjang;
	}

	@Override
	public String getalreadybandjohn(HashMap<String, String> map) {
		
		String alreadybandjohn = dao.getalreadybandjohn(map);
		
		return alreadybandjohn;
	}

	// 밴드 멤버 구하기
	@Override
	public List<HashMap<String, String>> getBandMember(HashMap<String, String> map) {
		
		List<HashMap<String, String>> BandMemberList = dao.getBandMember(map);
		return BandMemberList;
	}

	// 총 밴드원 수 구하기
	@Override
	public int getBandTotalCount( HashMap<String, String> map) {
		int cnt = dao.getBandTotalCount(map);
		return cnt;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,rollbackFor= {Throwable.class} )
	public int getjoinallow(HashMap<String,String> map)
			throws Throwable {
		
		int n = dao.getjoinallow(map);
		
		int m = dao.getmembercountup(map);
				
		return (n+m);
		
	}
	
	// 내가 만든 밴드의 총 갯수를 구하는 메소드
	@Override
	public int getMyCreateBandCount(String userid) {
		int n = dao.getMyCreateBandCount(userid);
		return n;
	}

	// 내가 가입한 밴드의 수를 구하는 메소드
	@Override
	public int getMyJoinBandCount(String userid) {
		int n = dao.getMyJoinBandCount(userid);
		return n;
	}
	
	// 마이페이지에서 가입한 밴드목록 리스트 불러오는 코드
	@Override
	public List<HashMap<String, String>> getListMyCreateband(HashMap<String, String> map) {
		List<HashMap<String, String>> mybandList = dao.getListMyCreateband(map);
		return mybandList;
	}

	@Override
	public List<HashMap<String, String>> getListMyJoinband(HashMap<String, String> map) {
		List<HashMap<String, String>> mybandList = dao.getListMyJoinband(map);
		return mybandList;
	}

	// 내가 가입한 밴드 탈퇴
	@Override
	public int deleteMyBand(HashMap<String,String> map) {
		int n = dao.deleteMyBand(map);
		return n;
	}


	//////////밴드플랜///////////////
	// 먼저 밴드플랜일련번호 채번해오기
	@Override
	public int getPnumseq() {
		int pnum = dao.getPnumseq();
		return pnum;
	}

	// 플랜등록
	@Override
	public int getBandPlanAdd(BandPlanVO planvo) {
		int n = dao.getBandPlanAdd(planvo);
		return n;
	}

	// 플랜 리스트 가지고오기
	@Override
	public List<HashMap<String, String>> getBandPlanList(String bcode) {
		List<HashMap<String, String>> bandPlanList = dao.getBandPlanList(bcode);
		return bandPlanList;
	}

	// 검색어가 있는 플랜 건수
	@Override
	public int getTotalPlanCount2(HashMap<String, String> map) {
		int totalPlanCount = dao.getTotalPlanCount2(map);
		return totalPlanCount;
	}


	// 검색어가 있는 페이징처리
	@Override
	public List<HashMap<String, String>> getPlanList2(HashMap<String, String> map) {
		List<HashMap<String, String>> planList = dao.getPlanList2(map);
		return planList;
	}

	//플랜보기
	@Override
	public BandPlanVO getPlanView(String pnum, String userid) {
		BandPlanVO planList = dao.getPlanView(pnum);
		
		if(userid != null && !planList.getFk_userid().equals(userid)) {
			dao.setAddReadCount2(pnum);
			planList = dao.getPlanView(pnum);
		}
	
		return planList;
	}

	// 조회수 증가없이 그냥 플랜 1개를 가져오는 것 	
	@Override
	public BandPlanVO getPlanViewWithNoReadCount(String pnum) {
		BandPlanVO planList = dao.getPlanView(pnum);
		return planList;
	}
		
	// 밴드 플랜 페이지에서 문화재 번호가져오기
	@Override
    public int getCultureIdx(String cultureSearch) {
	      int cultureIdx = dao.getCultureIdx(cultureSearch);
	      return cultureIdx;
   }

	// 플랜수정
	@Override
	public int getBandPlanEdit(BandPlanVO planvo) {
		int n = dao.getBandPlanEdit(planvo);
		return n;
	}

	// 플랜삭제
	@Override
	public int getBandPlanDel(String pnum) {
		int n = dao.getBandPlanDel(pnum);
		return n;
	}

	
	// 플랜 댓글쓰기(**** AJAX로 처리 ****)
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public int addPComment(PCommentVO pcommentvo) throws Throwable {
		int result = 0;
		
		int n = 0;
			n = dao.addPComment(pcommentvo);
		// final_pcomment 테이블에 insert
		
		if(n == 1) {
			result = dao.updatePCommentCount(pcommentvo.getParentPnum());
			// final_bandplan 테이블에 commentCount컬럼의 값이 1증가(update)
		}		
		
		return result;
	}

	
	// ==== 플랜 댓글내용 보여주기 ====
	@Override
	public List<PCommentVO> listPComment(String pnum) {
		List<PCommentVO> list = dao.listPComment(pnum);
		return list;
	}

	
	////////////////////////////////////////////////
	// 사진게시판
	@Override
	public List<PhotoVO> photoList(String bcode) {
		List<PhotoVO> photoList = dao.photoList(bcode);
		return photoList;
	}

	@Override
	public int addPhoto(PhotoVO photovo) {
		int n = dao.addPhoto(photovo);
		return n;
	}

		
	@Override
	public List<HashMap<String, String>> getbandallow(HashMap<String, String> map) {
		List<HashMap<String, String>> bandlist = dao.getbandallow(map);
		return bandlist;
	}

	@Override
	public int getbandallowCount() {
		int n = dao.getbandallowCount();
		return n;
	}

	// 밴드 가입 신청 승인 status 0 에서 1(관리자 승인)
	@Override
	public int getoneallowband(String bcode) {
		int n = dao.getoneallowband(bcode);
		return n;
	}

	@Override
	public int getcheckboxbandallow(HashMap<String, String[]> map) {
		int n = dao.getcheckboxbandallow(map);
		return n;
	}
	
	// 밴드 플랜 댓글 신고
	@Override
	public int policePcomment(HashMap<String, String> map) {
		int n = dao.policePcomment(map);
		return n;
	}

		
	// 자유게시판 댓글 신고
	@Override
	public int policeComment(HashMap<String, String> map) {
		int n = dao.policeComment(map);
		return n;
	}

	// 밴드플랜 댓글 중복 신고 (policePcomment 테이블에 동일한 fk_seq에 userid 있는지 확인)
	@Override
	public int policePuserid(HashMap<String, String> map) {
		int n = dao.policePuserid(map);
		return n;
	}
	
	// 밴드플랜 댓글 중복 신고 (policePcomment 테이블에 동일한 fk_seq에 userid 있는지 확인)
	@Override
	public int policeUserid(HashMap<String, String> map) {
		int n = dao.policeUserid(map);
		return n;
	}
			
	
}
