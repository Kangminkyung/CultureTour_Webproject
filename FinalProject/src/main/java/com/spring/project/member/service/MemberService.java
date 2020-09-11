package com.spring.project.member.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.project.member.model.InterMemberDAO;
import com.spring.project.member.model.MemberVO;

@Service
public class MemberService implements InterMemberService{

	@Autowired
	private InterMemberDAO dao;
	
	@Override
	public MemberVO getLoginMember(HashMap<String, String> map) {
		
		MemberVO loginuser = dao.getLoginMember(map);
		return loginuser;
	
	}

	@Override
	public int gettotalmember(HashMap<String, String> map) {
		int n = dao.gettotalmember(map);
		return n;
	}

	@Override
	public List<MemberVO> getMemberlist(HashMap<String, String> map) {
		List<MemberVO> memberlist = dao.getMemberlist(map);
		return memberlist;
	}
	
	
	// 회원가입 여부 처리
			@Override
			public int registerMember(MemberVO membervo) {
				int n = dao.registerMember(membervo);
				return n;
			}
			
			// 아이디 중복체크
			@Override
			public int idDuplicateCheck(String userid) {

				int n = dao.idDuplicateCheck(userid);
				
				return n;
			}
			
			// 아이디 찾기
			@Override
			public String getUserid(HashMap<String, String> map) {
				
				String userid = dao.getUserid(map);
				
				return userid;
			}
			
			// 비밀번호 찾기를 위해 먼저 userid 와 email 을 가지는 사용자가 존재하는지 검증해주는 것
			@Override
			public int isUserExists(HashMap<String, String> map) {
				
				int n = dao.isUserExists(map);
				
				return n;
			}

			// 암호를 새암호로 변경하는 폼 생성
			@Override
			public int updatePwdUser(HashMap<String, String> map) {
				
				int n = dao.updatePwdUser(map);
				
				return n;
			}

		
			// 회원 정보 보기
			@Override
			public MemberVO getMemberOneByIdx(String idx) {
				MemberVO loginuser = dao.getMemberOneByIdx(idx);
				return loginuser;
			}

			// 네이버 아이디 중복검사
			@Override
			public int getNaverDuplicate(HashMap<String, String> map) {
				
				int n = dao.getNaverDuplicate(map);
				return n;
			}

			// 네이버 아이디 만들기
			@Override
			public int NaverRegisterMember(MemberVO membervo) {
				int n = dao.NaverRegisterMember(membervo);
				return n;
			}
	
			// 회원 정보 수정 여부 처리	
			@Override
			public int updateMember(MemberVO membervo) {

				int n = dao.updateMember(membervo);
				
				return n;
			}

			// 회원 정보 보기
			@Override
			public MemberVO getMemberOneByIdx(HashMap<String, String> map) {
				
				MemberVO loginuser = dao.getMemberOneByIdx(map);
				return loginuser;
			}
			
			// 회원 삭제 하기
			@Override
			public int deleteMember(String idx) {
				
				int n = dao.deleteMember(idx);
				
				return n;
			}
			
			
	

}
