package com.gdu.app14.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.gdu.app14.dto.MemberDto;

public interface MemberService {
  
  // 회원 등록 
  public Map<String, Object> register(MemberDto memberDto, HttpServletResponse httpServletResponse); // HttpServletResponse가 예외처리(catch)-(ajax)error로 넘겨주는 역할
  //ResponseEntity<Map<String, Object>>로도 사용 가능(ResponseEntity는 @ResponseBody를 이미 내장) 
  
  // 회원 목록
  public Map<String, Object> getMembers(int page);
}
