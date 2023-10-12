package com.gdu.app11.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.gdu.app11.dto.ContactDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ContactDao {
  
  private final SqlSessionTemplate sqlSessionTemplate;  // Bean으로 만들어 두었기 때문에 @RequiredArgsConstructor에 의해 자동으로 @Autowired가 됨

  /**
   * 삽입 메소드<br>
   * @param contactDto 삽입할 연락처 정보(name, tel, email, address)
   * @return insertCount 삽입된 행(Row)의 개수, 1이면 삽입 성공, 0이면 삽입 실패
   */
  public int insert(ContactDto contactDto) {  
    return sqlSessionTemplate.insert("mybatis.mapper.contact.insert", contactDto); // insert(호출할id전달, 호출할parameter전달) : namespace(mapper경로)와 id를 함께 적어서 mapper구분을 해주어야 한다.
  }
  
  /**
   * 수정 메소드<br>
   * @param contactDto 수정할 연락처 정보(contact_no, name, tel, email, address)
   * @return updateCount 수정된 행(Row)의 개수, 1이면 수정 성공, 0이면 수정 실패
   */
  public int update(ContactDto contactDto) {
    return sqlSessionTemplate.update("mybatis.mapper.contact.update", contactDto);  // update(호출할 쿼리의 id, parameter)
  }
  
  /**
   * 삭제 메소드<br>
   * @param contact_no 삭제할 연락처 번호
   * @return deleteCount 삭제된 행(Row)의 개수, 1이면 삭제 성공, 0이면 삭제 실패
   */
  public int delete(final int contactNo) {
    return sqlSessionTemplate.delete("mybatis.mapper.contact.delete", contactNo);  // delete(id, param)
  }
  
  /**
   * 전체 조회 메소드<br>
   * @return  조회된 모든 연락처 정보(ContactDto)
   */
  public List<ContactDto> selectList() {  
    // select를 전달하는 쿼리는 selectList와 selectOne이 있다. 목록을 전달할것이기 때문에 selectList를 사용.
    return sqlSessionTemplate.selectList("mybatis.mapper.contact.selectList");  // id만 전달
  }
  
  /**
   * 상세 조회 메소드<br>
   * @param contact_no 조회할 연락처 번호
   * @return contactDto 조회된 연락처 정보, 조회된 연락처가 없으면 null 반환
   */
  public ContactDto selectContactByNo(int contactNo) { 
    return sqlSessionTemplate.selectOne("mybatis.mapper.contact.selectContactByNo", contactNo);  // 전달할 것이 1개 이기 때문에 selectOne사용
  }
  
}
