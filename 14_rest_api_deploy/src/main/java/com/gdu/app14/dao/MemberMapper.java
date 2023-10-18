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
  
  // 전체 개수
  public int getMemberCount();
  
  // 회원 조회(상세 보기)
  public MemberDto getMember(int memberNo);
  
  // 회원 정보 수정
  public int updateMember(MemberDto memberDto);
  
  // 회원 정보 삭제
  public int deleteMember(int memberNo);
  
  // 회원들 정보 삭제(선택 삭제)
  public int deleteMembers(List<String> list);
  
}
