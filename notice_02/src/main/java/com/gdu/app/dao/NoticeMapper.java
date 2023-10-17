package com.gdu.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app.dto.NoticeDto;

@Mapper  // noticeMapper.xml 과 연결되는 annotation
public interface NoticeMapper {
  int modifyNotice(NoticeDto noticeDto);
  NoticeDto getNotice(int noticeNo);   // #{noticeNo}로 받음
  int addNotice(NoticeDto noticeDto);  // insert의 반환타입은 int로 정해져있음
  List<NoticeDto> getNoticeList();     // Interface 특성상 public 생략 가능
}
