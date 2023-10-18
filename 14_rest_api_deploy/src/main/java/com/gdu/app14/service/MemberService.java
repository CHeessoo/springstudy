package com.gdu.app14.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.gdu.app14.dto.MemberDto;

public interface MemberService {
  
  // 회원 등록 
  public Map<String, Object> register(MemberDto memberDto, HttpServletResponse response); // HttpServletResponse가 예외처리(catch)-(ajax)error로 넘겨주는 역할
  //ResponseEntity<Map<String, Object>>로도 사용 가능(ResponseEntity는 @ResponseBody를 이미 내장) 
  
  // 회원 목록
  public Map<String, Object> getMembers(int page);
  
  // 회원 조회
  public Map<String, Object> getMember(int memberNo);
  
  // 회원 정보 수정
  public Map<String, Object> modifyMember(MemberDto memberDto);
  
  // 회원 정보 삭제
  public Map<String, Object> removeMember(int memberNo);
  
  // 회원들 정보 삭제(선택 삭제)
  public Map<String, Object> removeMembers(String memberNoList);
  
}
