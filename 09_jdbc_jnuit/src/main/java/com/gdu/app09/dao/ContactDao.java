package com.gdu.app09.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdu.app09.dto.ContactDto;

@Repository
public class ContactDao {
  
  @Autowired  // Spring Container에 저장된 JdbcConnection 타입의 객체(Bean)을 가져온다.
  private JdbcConnection jdbcConnection;
  
  private Connection con;         // DB 접속
  private PreparedStatement ps;   // 쿼리문 실행
  private ResultSet rs;           // SELECT 처리
  
  
  /**
   * 삽입 메소드<br>
   * @param contactDto 삽입할 연락처 정보(name, tel, email, address)
   * @return insertCount 삽입된 행(Row)의 개수, 1이면 삽입 성공, 0이면 삽입 실패
   */
  public int insert(ContactDto contactDto) {
    
    
    int insertCount = 0;
    
    try {
      
      // 커넥션 호출
      con = jdbcConnection.getConnection();  
      
      // 쿼리문
      String sql = "INSERT INTO CONTACT_T(CONTACT_NO, NAME, TEL, EMAIL, ADDRESS, CREATED_AT) VALUES(CONTACT_SEQ.NEXTVAL, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))";
      
      ps = con.prepareStatement(sql); // sql 전달
      // 변수채우기
      ps.setString(1, contactDto.getName());
      ps.setString(2, contactDto.getTel());
      ps.setString(3, contactDto.getEmail());
      ps.setString(4, contactDto.getAddress());
      // 업데이트
      insertCount = ps.executeUpdate();
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jdbcConnection.close(con, ps, rs);  // 모든 닫아주는 역할은 클로즈 메소드 호출
    }
    
    return insertCount;
  }
  
  /**
   * 수정 메소드<br>
   * @param contactDto 수정할 연락처 정보(contact_no, name, tel, email, address)
   * @return updateCount 수정된 행(Row)의 개수, 1이면 수정 성공, 0이면 수정 실패
   */
  public int update(ContactDto contactDto) {
    

    int updateCount = 0;
    
    try {
      
      con = jdbcConnection.getConnection();
      String sql = "UPDATE CONTACT_T SET NAME = ?, TEL = ?, EMAIL = ?, ADDRESS = ? WHERE CONTACT_NO = ?";
      ps = con.prepareStatement(sql);   
      ps.setString(1, contactDto.getName());
      ps.setString(2, contactDto.getTel());
      ps.setString(3, contactDto.getEmail());
      ps.setString(4, contactDto.getAddress());
      ps.setInt(5, contactDto.getContact_no());
      updateCount = ps.executeUpdate();
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jdbcConnection.close(con, ps, rs);
    }
    
    return updateCount;
  }
  
  /**
   * 삭제 메소드<br>
   * @param contact_no 삭제할 연락처 번호
   * @return deleteCount 삭제된 행(Row)의 개수, 1이면 삭제 성공, 0이면 삭제 실패
   */
  public int delete(int contact_no) {
    
    
    int deleteCount = 0;
    
    try {
      
      con = jdbcConnection.getConnection();
      String sql = "DELETE FROM CONTACT_T WHERE CONTACT_NO = ?";
      ps = con.prepareStatement(sql);
      ps.setInt(1, contact_no);
      deleteCount = ps.executeUpdate();
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jdbcConnection.close(con, ps, rs);
    }
    
    return deleteCount;
  }
  
  /**
   * 전체 조회 메소드<br>
   * @return  조회된 모든 연락처 정보(ContactDto)
   */
  public List<ContactDto> selectList() {
    
    List<ContactDto> list = new ArrayList<ContactDto>();
    
    try {
      
      con = jdbcConnection.getConnection();
      String sql = "SELECT CONTACT_NO, NAME, TEL, EMAIL, ADDRESS, CREATED_AT FROM CONTACT_T ORDER BY CONTACT_NO";
      ps = con.prepareStatement(sql);
      
      rs = ps.executeQuery();
      while(rs.next()) {
        ContactDto contactDto = new ContactDto();
        contactDto.setContact_no(rs.getInt("CONTACT_NO"));  // 행(Row) 하나당 contactDto 하나
        contactDto.setName(rs.getString("NAME"));
        contactDto.setTel(rs.getString("TEL"));
        contactDto.setEmail(rs.getString("EMAIL"));
        contactDto.setAddress(rs.getString("ADDRESS"));
        contactDto.setCreated_at(rs.getString("CREATED_AT"));
        list.add(contactDto); // 리스트에 담기
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jdbcConnection.close(con, ps, rs);
    }
    
    return list;
  }
  
  /**
   * 상세 조회 메소드<br>
   * @param contact_no 조회할 연락처 번호
   * @return contactDto 조회된 연락처 정보, 조회된 연락처가 없으면 null 반환
   */
  public ContactDto selectContactByNo(int contact_no) {   // 번호로 조회하기
    
    
    ContactDto contactDto = null; // 조회가 되면 new를 하겠다는 의미
    
    try {
      
      con = jdbcConnection.getConnection();
      String sql = "SELECT CONTACT_NO, NAME, TEL, EMAIL, ADDRESS, CREATED_AT FROM CONTACT_T WHERE CONTACT_NO = ?";
      ps = con.prepareStatement(sql);
      ps.setInt(1, contact_no);
      rs = ps.executeQuery(); 
      
      if(rs.next()) {                            // 조회결과가 있으면
        contactDto = new ContactDto();           // contactDto 만들기
        contactDto.setContact_no(rs.getInt(1));  // 1 -> SELECT에 적힌 순서 기준
        contactDto.setName(rs.getString(2));
        contactDto.setTel(rs.getString(3));
        contactDto.setEmail(rs.getString(4));
        contactDto.setAddress(rs.getString(5));
        contactDto.setCreated_at(rs.getString(6));
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jdbcConnection.close(con, ps, rs);;
    }
    
    
    
    return contactDto;
    
  }
  
}
