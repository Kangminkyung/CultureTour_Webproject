package com.spring.project.culture.model;

import java.util.HashMap;
import java.util.List;

import com.spring.project.member.model.MemberVO;

public interface InterCultureDAO {

	int getCultureTotalCount(HashMap<String, String> map);

	List<CultureVO> getCulturePagingList(HashMap<String, String> map);

	CultureVO getCultureDetail(String idx);// 문화재 상세 정보를 가져오는 메소드

	int updateGrade(String userid); //퀴즈 등급 올리기
	
	int getCultureSearchTotalCount(HashMap<String, String> map);

	List<CultureVO> getCultureSearch(HashMap<String, String> map);

	int addWishCulture(HashMap<String, String> map); // 나의 위시 문화재 추가
	
	int getupdatequiz(String userid);
	
	MemberVO getLoginMember(HashMap<String, String> map); // 로그인 여부 알아오기

	int getCultureWishIdxCount(HashMap<String, String> map); // 나의 위시 문화재 중복 검사

	int getCultureWishTotalCount(HashMap<String, String> map); // wishlist count

	List<HashMap<String, String>> culturePagingWishList(HashMap<String, String> map); // wishlist paging list

	int oneDeleteWish(HashMap<String, String> map); // 즐겨찾기에서 하나삭제

	int ArrDeleteWish(HashMap<String, Object> map); // 즐겨찾기에서 여러개삭제
	
	List<String> getcultureSearchJSON(String searchword);

	int getcultureCount(String cultureSearchWord); // 문화재 검색카운트
	
	List<HashMap<String, String>> rankShowJSON(); // 문화재 실시간 검색어

	List<HashMap<String, String>> wordCloud(); // 워드클라우드 차트 

	
	
}
