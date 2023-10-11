package com.gdu.app11.service;

import java.util.List;

import com.gdu.app11.dto.ContactDto;

public interface ContactService {
  public int addContact(ContactDto contactDto);
  public int modifyContact(ContactDto contactDto);
  public int deleteContact(int contact_no);
  public List<ContactDto> getContactList();  // 원래는 페이징 값 전달하지만 이번엔 연습하지 않음
  public ContactDto getContactByNo(int contact_no);
  public void txTest();  // 트랜잭션 테스트
}
