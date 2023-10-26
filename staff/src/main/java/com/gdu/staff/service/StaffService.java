package com.gdu.staff.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.gdu.staff.dto.StaffDto;

public interface StaffService {
  public Map<String, Object> staffList(HttpServletRequest request);
  public Map<String, Object> getStaffOne(HttpServletRequest request);
  public ResponseEntity<Map<String, Object>> registerStaff(StaffDto staff);
}
