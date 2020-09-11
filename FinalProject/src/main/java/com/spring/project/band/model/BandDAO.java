package com.spring.project.band.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.project.board.model.BoardVO;
import com.spring.project.board.model.NoticeVO;

import oracle.net.aso.b;

//===== #28. DAO 선언 =====
@Repository
public class BandDAO implements InterBandDAO{

	// ===== #29. 의존객체 주입하기(DI : Dependency Injection) =====
	@Autowired
	private SqlSessionTemplate sqlsession;

	// 썸네일 포함 전 밴드 리스트 불러오기
/*	@Override
	public List<BandVO> getBandList() {
		List<BandVO> bandList = sqlsession.selectList("band.getBandList");
		return bandList;
	}*/

	// 썸네일 포함 후 밴드 리스트 불러오기
/*	@Override
	public List<HashMap<String, String>> getBandList() {
		List<HashMap<String, String>> bandList = sqlsession.selectList("band.bandList");
		return bandList;
	}*/

	
	@Override
	public List<HashMap<String, String>> getBandThema() {
		List<HashMap<String, String>> bandThemaList = sqlsession.selectList("band.getBandThema");
		return bandThemaList;
	}

	// 먼저 밴드일련번호 채번해오기
	@Override
	public int getBcodeseq() {
		int bcode = sqlsession.selectOne("band.getBcodeseq");
		return bcode;
	}

	// 밴드 등록하기
	@Override
	public int addBand(HashMap<String, String> mapBand) {
		int n = sqlsession.insert("band.addBand", mapBand);
		return n;
	}

	// 벤드 등록시  밴드 이미지파일 등록하기
	@Override
	public int addBandImage(HashMap<String, String> mapBandImage) {
		int n = sqlsession.insert("band.addBandImage", mapBandImage);
		return n;
	}

	// 마이페이지에서 가입한 밴드목록 리스트 불러오는 코드
	@Override
	public List<HashMap<String, String>> getListMyband(String userid) {
		List<HashMap<String, String>> mybandList = sqlsession.selectList("band.getListMyband",userid);
		return mybandList;
	}

	// 메인메뉴 - 밴드 정보 페이지 불러오는 코드
	@Override
	public BandVO getBandInfo(String bcode) {
		BandVO bandInfo = sqlsession.selectOne("band.getBandInfo", bcode);
		return bandInfo;
	}


	/////////////////////페이징처리////////////////////////////////////
	// // 썸네일 포함 검색어가 없는 밴드 리스트 불러오기
	@Override
	public List<HashMap<String, String>> getbandList(HashMap<String, String> map) {
		List<HashMap<String, String>> bandList = sqlsession.selectList("band.getbandList", map);
		return bandList;
	}

	// 썸네일 포함 검색어가 있는 밴드 리스트 불러오기
	@Override
	public List<HashMap<String, String>> getbandList2(HashMap<String, String> map) {
		List<HashMap<String, String>> bandList = sqlsession.selectList("band.getbandList2", map);
		return bandList;
	}

	// 검색어가 없는 총게시물 건수
	@Override
	public int getTotalBandCount() {
		int count = sqlsession.selectOne("band.getTotalBandCount");
		return count;
	}

	// 검색어가 있는 총게시물 건수
	@Override
	public int getTotalBandCount2(HashMap<String, String> map) {
		int count = sqlsession.selectOne("band.getTotalBandCount2", map);
		return count;
	}

	// 밴드 info 페이지에 밴드 이미지 불러오기
	@Override
	public List<HashMap<String, String>> getViewBandImage(String bcode) {
		List<HashMap<String, String>> viewBandImageList = sqlsession.selectList("band.getViewBandImage",bcode);
		return viewBandImageList;
	}

	
	//////////////// 밴드 공지사항 ///////////////////
	@Override
	public int getBandNoticeTotalCount(HashMap<String, String> map) {
		int count = sqlsession.selectOne("band.getBandNoticeTotalCount",map);
		return count;
	}

	@Override
	public int getBandNoticeTotalCount2(HashMap<String, String> map) {
		int count = sqlsession.selectOne("band.getBandNoticeTotalCount2", map);
		return count;
	}

	@Override
	public List<BandNoticeVO> bandNoticeList(HashMap<String, String> map) {
		List<BandNoticeVO> list = sqlsession.selectList("band.bandNoticeList", map);
		return list;
	}

	@Override
	public List<BandNoticeVO> bandNoticeList2(HashMap<String, String> map) {
		List<BandNoticeVO> list = sqlsession.selectList("band.bandNoticeList2", map);
		return list;
	}

	// 글쓰기
	@Override
	public int addBandNotice(BandNoticeVO bandnoticevo) {
		int n = sqlsession.insert("band.addBandNotice", bandnoticevo);
		return n;
	}
	
	// 글 1개를 보여주기
	@Override
	public BandNoticeVO getBandNoticeView(String seq) {
		BandNoticeVO bandnoticevo = sqlsession.selectOne("band.getBandNoticeView", seq);
		return bandnoticevo;
	}
	
	// 글 1개를 볼때 조회수(readCount) 1증가 시키기
	@Override
	public void setAddReadCount(String seq) {
		sqlsession.update("band.setAddReadCount",seq);			
		
	}

	// 글수정 및 글삭제시 암호일치 여부 알아오기
	@Override
	public boolean checkPw(HashMap<String, String> map) {
		int n = sqlsession.selectOne("band.checkPw", map);
		boolean result = false;
		
		if(n == 1) 
			result = true;
		
		return result;
	}

	// 1개 글 수정하기
	@Override
	public int updateContent(BandNoticeVO bandnoticevo) {
		int n = sqlsession.update("band.updateContent", bandnoticevo);
		return n;
	}

	// 1개 글 삭제하기
	@Override
	public int deleteContent(HashMap<String, String> map) {
		int n = sqlsession.update("band.deleteContent", map);
		return n;
	}

	
	///////////////////////////////////////////
	

	// 밴드 수정하기
	@Override
	public int editBand(HashMap<String, String> mapBand) {
		int n = sqlsession.update("band.editBand", mapBand);
		return n;
	}

	// 밴드 수정하기(이미지 첨부) 기본 final_bandImage 테이블에 저장된 이미지는 전부 삭제함
	@Override
	public int deleteBandImage(String bcode) {
		int n = sqlsession.delete("band.deleteBandImage", bcode);
		return n;
	}

	// 기존에 final_bandImage 테이블에 저장되어있던 수정 전 이미지 파일 갯수 구하기 
	@Override
	public int countBandImage(String bcode) {
		int n = sqlsession.selectOne("band.countBandImage", bcode);
		return n;
	}

	// 밴드삭제
	@Override
	public int getBandDel(String bcode) {
		int n = sqlsession.update("band.getBandDel", bcode);
		return n;
	}
	
	// 밴드 가입신청
	@Override
	public int getbandJoin(HashMap<String, String> map) {
		int n = sqlsession.insert("band.getbandJoin", map);
		return n;
	}

	// 밴드장 구하기
	@Override
	public String getbandjang(HashMap<String, String> map) {
		String bandjang = sqlsession.selectOne("band.getbandjang", map);
		return bandjang;
	}

	// 이미가입한 밴드면 userid 구해오기 없으면 null
	@Override
	public String getalreadybandjohn(HashMap<String, String> map) {
		String alreadybandjohn = sqlsession.selectOne("band.getalreadybandjohn",map);
		return alreadybandjohn;
	}

	// 밴드 멤버 구하기
	@Override
	public List<HashMap<String, String>> getBandMember(HashMap<String, String> map) {
		List<HashMap<String, String>> BandMemberList = sqlsession.selectList("band.getBandMember",map);
		return BandMemberList;
	}

	// 총 밴드원 수 구하기
	@Override
	public int getBandTotalCount( HashMap<String, String> map) {
		int cnt = sqlsession.selectOne("band.getBandTotalCount",map);
		return cnt;
	}

	@Override
	public int getjoinallow( HashMap<String,String> map) {
		int n = sqlsession.update("band.getjoinallow", map);
	return n;
	}
	
	// 내가 만든 밴드의 갯수를 출력하는 메소드
	@Override
	public int getMyCreateBandCount(String userid) {
			int n = sqlsession.selectOne("band.myCreateBandCount",userid);
		return n;
	}
	
	// 내가 가입한 밴드의 총 수를 구하는 메소드
	@Override
	public int getMyJoinBandCount(String userid) {
		int n = sqlsession.selectOne("band.myJoinBandCount",userid);
		return n;
	}
	
	@Override
	public List<HashMap<String, String>> getListMyCreateband(HashMap<String, String> map) {
		List<HashMap<String, String>> mybandList = sqlsession.selectList("band.getListMyCreateband",map);
		return mybandList;
	}
	@Override
	public List<HashMap<String, String>> getListMyJoinband(HashMap<String, String> map) {
		List<HashMap<String, String>> mybandList = sqlsession.selectList("band.getListMyJoinband",map);
		return mybandList;
	}

	@Override
	public int getmembercountup( HashMap<String,String> map) {
		int m = sqlsession.update("band.getmembercountup",map);
		return m;
	}

	// 내가 가입한 밴드 탈퇴
	@Override
	public int deleteMyBand(HashMap<String,String> map) {
		int n = sqlsession.update("band.deleteMyBand",map);
		return n;
	}
	
	
	/////////////////////---bandPlan-/////////////////////
	// 먼저 밴드플랜일련번호 채번해오기
	@Override
	public int getPnumseq() {
		int pnum = sqlsession.selectOne("band.getPnumseq");
		return pnum;
	}

	// 플랜등록
	@Override
	public int getBandPlanAdd(BandPlanVO planvo) {
		int n = sqlsession.insert("band.getBandPlanAdd", planvo);
		return n;
	}

	// 플랜 리스트 가지고오기
	@Override
	public List<HashMap<String, String>> getBandPlanList(String bcode) {
		List<HashMap<String, String>> bandPlanList = sqlsession.selectList("band.getBandPlanList", bcode);
		return bandPlanList;
	}

	// 검색어가 있는 플랜 건수
	@Override
	public int getTotalPlanCount2(HashMap<String, String> map) {
		int count = sqlsession.selectOne("band.getTotalPlanCount2", map);
		return count;
	}

	// 검색어가 있는 페이징처리
	@Override
	public List<HashMap<String, String>> getPlanList2(HashMap<String, String> map) {
		List<HashMap<String, String>> planList = sqlsession.selectList("band.getPlanList2", map);
		return planList;
	}

	//플랜보기 
	@Override
	public BandPlanVO getPlanView(String pnum) {
		BandPlanVO planView = sqlsession.selectOne("band.getPlanView",pnum);
		return planView; 
	}

	// 밴드 플랜 페이지에서 문화재 번호가져오기
	  @Override
	   public int getCultureIdx(String cultureSearch) {
	      
	      int n =0;
	      try {
	         n = sqlsession.selectOne("band.getCultureIdx",cultureSearch);
	         
	      } catch (NullPointerException e) {
	         return 0;
	      }
	      
	      if(n !=0) return n;
	      
	      else return 0;
	   }
	// 플랜수정
	@Override
	public int getBandPlanEdit(BandPlanVO planvo) {
		int n = sqlsession.update("band.getBandPlanEdit",planvo);
		return n;
	}

	// 플랜삭제
	@Override
	public int getBandPlanDel(String pnum) {
		int n = sqlsession.update("band.getBandPlanDel",pnum);
		return n;
	}

	// 플랜댓글등록
	@Override
	public int addPComment(PCommentVO pcommentvo) {
		int n = sqlsession.insert("band.addPComment", pcommentvo);
		return n;
	}

	// 플랜 댓글 카운트 등록
	@Override
	public int updatePCommentCount(String parentPnum) {
		int n = sqlsession.update("band.updatePCommentCount", parentPnum);
		return n;
	}

	// ==== 댓글내용 보여주기 ====
	@Override
	public List<PCommentVO> listPComment(String pnum) {
		List<PCommentVO> list = sqlsession.selectList("band.listPComment", pnum);
		return list;
	}
	
	// 플랜 1개를 볼때 조회수(readCount) 1증가 시키기
	@Override
	public void setAddReadCount2(String pnum) {
		sqlsession.update("band.setAddReadCount2",pnum);			
		
	}

	// 사진게시판 목록
	@Override
	public List<PhotoVO> photoList(String bcode) {
		List<PhotoVO> photoList = sqlsession.selectList("band.photoList", bcode);
		return photoList;
	}

	@Override
	public int addPhoto(PhotoVO photovo) {
		int n = sqlsession.insert("band.addPhoto", photovo);
		return n;
	}


	// 밴드가입신청 숫자
	@Override
	public int getbandallowCount() {
		int n = sqlsession.selectOne("band.getbandallowCount");
		return n;
	}

	// 밴드가입신청 리스트
	@Override
	public List<HashMap<String, String>> getbandallow(HashMap<String, String> map) {
		List<HashMap<String, String>> bandlist =sqlsession.selectList("band.getbandallow", map);
		return bandlist;
	}

	// 밴드 가입 신청 승인 status 0 에서 1(관리자 승인)
	@Override
	public int getoneallowband(String bcode) {
		int n = sqlsession.update("band.getoneallowband", bcode);
		return n;
	}

	@Override
	public int getcheckboxbandallow(HashMap<String, String[]> map) {
		int n = sqlsession.update("band.getcheckboxbandallow", map);
		return n;
	}
		
	// 밴드 플랜 댓글 신고
		@Override
		public int policePcomment(HashMap<String, String> map) {
		
			int n = sqlsession.insert("band.policePcomment", map);
			return n;
		}
		// 자유게시판 댓글신고
		@Override
		public int policeComment(HashMap<String, String> map) {
		
			int n = sqlsession.insert("band.policeComment", map);
			return n;
		}

		// 밴드플랜 댓글 중복 신고 (policePcomment 테이블에 동일한 fk_seq에 userid 있는지 확인)
		@Override
		public int policePuserid(HashMap<String, String> map) {
			int n = 0;
			n = sqlsession.selectOne("band.policePuserid", map);
			
			System.out.println("policePuserid : n " +n);
			
			if(n>0) 
				return 1; // 카운트 수가 0보다 크면 이미 신고된 댓글이므로 리턴1
			else 
				return 0; // 카운트 수가 0이면 신고되지 않은 댓글이므로 리턴0
			

		}
		
		// 자유게시판 댓글 중복 신고 (policecomment 테이블에 동일한 fk_seq에 userid 있는지 확인)
		@Override
		public int policeUserid(HashMap<String, String> map) {
			int n = 0;
			n = sqlsession.selectOne("band.policeUserid", map);
			
			if(n>0) 
				return 1; // 카운트 수가 0보다 크면 이미 신고된 댓글이므로 리턴0
			else 
				return 0; // 카운트 수가 0이면 신고되지 않은 댓글이므로 리턴1
			
		
		}
		
}
