package com.gdu.staff.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.staff.dao.StaffMapper;
import com.gdu.staff.dto.StaffDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StaffServiceImple implements StaffService {

  private final StaffMapper staffMapper;
  
  @Override
  public ResponseEntity<Map<String, Object>> registerStaff(StaffDto staff) {
    int addResult = 0;
    Map<String, Object> map = new HashMap<>();
    try {
      addResult = staffMapper.insertStaff(staff);
      map.put("addResult", addResult);
      return new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      map.put("addResult", addResult);
      return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    } 
  }
  
  @Override
  public ResponseEntity<Map<String, Object>> getStaffList(StaffDto staff) {
    return null;
  }
  
  @Override
  public ResponseEntity<StaffDto> getStaff(String sno) {
    StaffDto staffDto = staffMapper.getStaff(sno);
    return new ResponseEntity<>(staffDto, staffMapper.getStaff(sno));
  }
  
}
