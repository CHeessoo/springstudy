package com.gdu.app14.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app14.dto.MemberDto;

@Mapper  // memberMapper.xml과 연결해주는 Annotation (MyBatis 제공)
public interface MemberMapper {
  
  // 삽입
  public int insertMember(MemberDto memberDto);
  
  // 목록
  public List<MemberDto> getMemberList(Map<String, Object> map);
  
}
