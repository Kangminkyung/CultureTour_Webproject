package com.spring.project.culture.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.project.member.model.MemberVO;

@Repository
public class CultureDAO implements InterCultureDAO {
	
		// 의존객체 주입하기(DI : Dependency Injection) =====
		@Autowired
		private SqlSessionTemplate sqlsession;

		@Override
		public int getCultureTotalCount(HashMap<String, String> map) {
			int totalCount = sqlsession.selectOne("culture.getCultureTotalCount", map);
			return totalCount;
		}

		@Override
		public List<CultureVO> getCulturePagingList(HashMap<String, String> map) {
			List<CultureVO> culturePagingList = sqlsession.selectList("culture.getCulturePagingList", map);
			return culturePagingList;
		}

		// 문화재 상세정보를 가져오기
		@Override
		public CultureVO getCultureDetail(String idx) {
			CultureVO culturevo = sqlsession.selectOne("culture.getCultureDetail", idx);
			return culturevo;
		}

		// 문화재 검색 카운트 가져오기
		@Override
		public int getCultureSearchTotalCount(HashMap<String, String> map) {
			int totalCount = sqlsession.selectOne("culture.getCultureSearchTotalCount", map);
			return totalCount;
		}
		// 문화재 검색 리스트
		@Override
		public List<CultureVO> getCultureSearch(HashMap<String, String> map) {
			List<CultureVO> culturePagingList = sqlsession.selectList("culture.getCultureSearch", map);
			return culturePagingList;
		}
		// 퀴즈 등급 올리기
		@Override
		public int updateGrade(String userid) {
			int n = sqlsession.update("culture.updateGrade",userid);
			return n;
		}
		
		// 위시 문화재 추가
		@Override
		public int addWishCulture(HashMap<String, String> map) {
			int n = sqlsession.insert("culture.addWishCulture",map);
			return n;
		}
		// 퀴즈 하루에 한번 제한
		@Override
		public int getupdatequiz(String userid) {
			int n = sqlsession.update("culture.updatequiz",userid);
			return n;
		}
		
		
		@Override
		public MemberVO getLoginMember(HashMap<String, String> map) {

			MemberVO loginuser = sqlsession.selectOne("member.getLoginMember",map);
			return loginuser;
		}
		
		//나의 위시 중복검사
		@Override
		public int getCultureWishIdxCount(HashMap<String, String> map) {
			int n = sqlsession.selectOne("culture.CultureWishIdxCount", map);
			return n;
		}

		@Override
		public int getCultureWishTotalCount(HashMap<String, String> map) {
			int n = sqlsession.selectOne("culture.getCultureWishTotalCount", map);
			return n;
		}

		@Override
		public List<HashMap<String, String>> culturePagingWishList(HashMap<String, String> map) {
			List<HashMap<String, String>> culturePagingWishList = sqlsession.selectList("culture.culturePagingWishList", map);
			return culturePagingWishList;
		}
		
		//즐겨찾기에서 한개 삭제
		@Override
		public int oneDeleteWish(HashMap<String, String> map) {
			int n = sqlsession.delete("culture.oneDeleteWish", map);
			return n;
		}
		
		//즐겨찾기에서 여러개 삭제
		@Override
		public int ArrDeleteWish(HashMap<String, Object> map) {
			int n = sqlsession.delete("culture.ArrDeleteWish", map);
			return n;
		}

		@Override
		public List<String> getcultureSearchJSON(String searchword) {
			 List<String> cultureList =  sqlsession.selectList("culture.getcultureSearchJSON",searchword);
			return cultureList;
		}
				

		@Override
		public int getcultureCount(String cultureSearchWord) {
			int n = sqlsession.update("culture.getcultureCount",cultureSearchWord);
			return n;
		}
		
		@Override
		public List<HashMap<String, String>> rankShowJSON() {
	
			List<HashMap<String, String>> list = sqlsession.selectList("culture.rankShowJSON");
			
			return list;
		}

		@Override
		public List<HashMap<String, String>> wordCloud() {
			
			 List<HashMap<String, String>> list = sqlsession.selectList("culture.wordCloud");
			 
			return list;
		}
		
}
