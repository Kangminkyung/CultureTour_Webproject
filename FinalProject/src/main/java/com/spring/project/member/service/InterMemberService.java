package com.spring.project.member.service;

import java.util.HashMap;
import java.util.List;

import com.spring.project.member.model.MemberVO;

public interface InterMemberService {

	MemberVO getLoginMember(HashMap<String, String> map); // 로그인 여부 처리

	int gettotalmember(HashMap<String, String> map);	// 회원 총원 구하기 -- 페이징처리용

	List<MemberVO> getMemberlist(HashMap<String, String> map); // 페이징 처리 - 회원목록 보기

	int registerMember(MemberVO membervo); // 회원가입 여부 처리

	int idDuplicateCheck(String userid); // 회원가입 할때 ID 중복 여부 체크

	String getUserid(HashMap<String, String> map); // 아이디 찾기

	int isUserExists(HashMap<String, String> map); // 비밀번호 찾기를 위해 먼저 userid 와 email 을 가지는 사용자가 존재하는지 검증해주는 폼 생성

	int updatePwdUser(HashMap<String, String> map); // 암호를 새암호로 변경하는 폼 생성

	MemberVO getMemberOneByIdx(String idx); // 회원 정보 보기

	int getNaverDuplicate(HashMap<String, String> map); // 네이버 아이디 중복검사

	int NaverRegisterMember(MemberVO membervo); // 네이버아이디로 가입
	
	int updateMember(MemberVO membervo); // 회원 정보 수정 여부 처리

	MemberVO getMemberOneByIdx(HashMap<String, String> map); // 회원 정보 보기
	
	int deleteMember(String idx); // 회원 삭제 하기

	
}
