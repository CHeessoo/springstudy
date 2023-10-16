package com.gdu.app14.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.gdu.app14.dao.MemberMapper;
import com.gdu.app14.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
  
  private final MemberMapper memberMapper;

  @Override
  public Map<String, Object> register(MemberDto memberDto, HttpServletResponse httpServletResponse) {
    
    Map<String, Object> map = null;
    
    try {
      
      int addResult = memberMapper.insertMember(memberDto);
      map = Map.of("addResult", addResult);
      
    } catch (Exception e) {
      System.out.println(e.getClass().getName());  // 발생한 예외 클래스의 이름 확인
    }
    
    return map;
  }
  
  @Override
  public List<MemberDto> getMembers(int page) {
    
    return null;
  }

}
