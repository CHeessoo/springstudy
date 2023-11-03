package com.gdu.staff.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.gdu.staff.dto.StaffDto;

public interface StaffService {

  public ResponseEntity<Map<String, Object>> registerStaff(StaffDto staff);
}
