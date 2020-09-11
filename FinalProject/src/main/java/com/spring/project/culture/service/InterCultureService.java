package com.spring.project.culture.service;

import java.util.HashMap;
import java.util.List;

import com.spring.project.culture.model.CultureVO;
import com.spring.project.member.model.MemberVO;

// === Service단 인터페이스 선언 ===
public interface InterCultureService {

	int getCultureTotalCount(HashMap<String, String> map); // 총 문화재 수를 가져오는 메소드
	
	List<CultureVO> culturePagingList(HashMap<String, String> map); // 문화재리스트를 가져오는거 페이징처리

	CultureVO getCultureDetail(String idx);  // 문화재 상세 정보를 가져오는 메소드   

	int updateGrade(String userid); // 퀴즈 등급을 올려주는 메소드
	

	int getCultureSearchTotalCount(HashMap<String, String> map); // 총 문화재 검색 수

	List<CultureVO> getCultureSearch(HashMap<String, String> map); // 문화재 검색 페이징처리

	int addWishCulture(HashMap<String, String> map); // 나의 위시 문화재 추가
	
	int quizupdate(String userid);	// 퀴즈 0에서 1로 업데이트
	
	MemberVO getLoginMember(HashMap<String, String> map); // 로그인 여부 처리
	
	int countWishIdxCulture(HashMap<String, String> map); // 나의 위시 문화재 중복 검사
	
	int getCultureWishTotalCount(HashMap<String, String> map); // wish 총 개수

	List<HashMap<String, String>> culturePagingWishList(HashMap<String, String> map); // wish paging list

	int oneDeleteWish(HashMap<String, String> map); // 즐겨찾기에서 하나 삭제

	int ArrDeleteWish(HashMap<String, Object> map); // 즐겨찾기에서 여러개 삭제
	
	List<String> getcultureSearchJSON(String searchword); // 자동완성기능
	
	int getcultureCount(String cultureSearchWord);// 문화재 검색카운트
	
	String rankShowJSON(); // 문화재 실시간 검색

	List<HashMap<String, String>> wordCloud(); // 워드클라우드차트.

}
