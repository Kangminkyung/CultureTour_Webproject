package com.spring.project.culture.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.project.culture.model.CultureVO;
import com.spring.project.culture.model.InterCultureDAO;
import com.spring.project.member.model.MemberVO;

@Service
public class CultureService implements InterCultureService {
		// ===== #31. 의존객체 주입하기(DI : Dependency Injection) =====
		@Autowired
		private InterCultureDAO dao;

		@Override
		public int getCultureTotalCount(HashMap<String, String> map) {
			int totalCount = dao.getCultureTotalCount(map);
			return totalCount;
		}

		@Override
		public List<CultureVO> culturePagingList(HashMap<String, String> map) {
			List<CultureVO> culturePageList = dao.getCulturePagingList(map);
			return culturePageList;
		}

		
		// 문화재 상세 정보를 가져오는 메소드
		@Override
		public CultureVO getCultureDetail(String idx) {
			CultureVO culturevo = dao.getCultureDetail(idx);
			return culturevo;
		}

		// 퀴즈 등급 올리기
		@Override
		public int updateGrade(String userid) {
			int n = dao.updateGrade(userid);
			return n;
		}
		

		@Override
		public int getCultureSearchTotalCount(HashMap<String, String> map) {
			int totalCount = dao.getCultureSearchTotalCount(map);
			return totalCount;
		}

		@Override
		public List<CultureVO> getCultureSearch(HashMap<String, String> map) {
			List<CultureVO> culturePageList = dao.getCultureSearch(map);
			return culturePageList;
		}

		@Override
		public int addWishCulture(HashMap<String, String> map) {
			int n = dao.addWishCulture(map);
			return n;
		}
		@Override
		public int quizupdate(String userid) {
			
			int n = dao.getupdatequiz(userid);
			
			return n;
		}
		
		@Override
		public MemberVO getLoginMember(HashMap<String, String> map) {
			
			MemberVO loginuser = dao.getLoginMember(map);
			return loginuser;
		
		}
		
		//나의 위시 중복 문화재 검사
		@Override
		public int countWishIdxCulture(HashMap<String, String> map) {

			int n = dao.getCultureWishIdxCount(map);
			return n;
		}

		@Override
		public int getCultureWishTotalCount(HashMap<String, String> map) {
			int n = dao.getCultureWishTotalCount(map);
			return n;
		}

		@Override
		public List<HashMap<String, String>> culturePagingWishList(HashMap<String, String> map) {
			List<HashMap<String, String>> culturePagingWishList = dao.culturePagingWishList(map);
			return culturePagingWishList;
		}

		@Override
		public int oneDeleteWish(HashMap<String, String> map) {
			int n = dao.oneDeleteWish(map);
			return n;
		}

		@Override
		public int ArrDeleteWish(HashMap<String, Object> map) {
			int n = dao.ArrDeleteWish(map);
			return n;
		}
		
		@Override
		public List<String> getcultureSearchJSON(String searchword) {
			 List<String> cultureList =	dao.getcultureSearchJSON(searchword);
			return cultureList;
		}

		@Override
		public int getcultureCount(String cultureSearchWord) {
			int n = dao.getcultureCount(cultureSearchWord);
			return n;
		}
		
		@Override
		public String rankShowJSON() {
			
			List<HashMap<String,String>> list = dao.rankShowJSON();
			
			JSONArray jsonarray = new JSONArray();
			String str_jsonarray = "";
			
			if(list != null && list.size() > 0) {
				for(HashMap<String,String> map : list) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("RNO", map.get("RNO"));
					jsonObj.put("CCMANAME", map.get("CCMANAME"));
					jsonObj.put("TOTALCOUNT", map.get("TOTALCOUNT"));
					jsonObj.put("IDX",map.get("IDX"));
					jsonarray.put(jsonObj);
				}
			}
			
			str_jsonarray = jsonarray.toString();
			
			return str_jsonarray;
		}

		@Override
		public List<HashMap<String, String>> wordCloud() {
		
			List<HashMap<String, String>> list = dao.wordCloud();
			
			return list;
		}
		
		
}
