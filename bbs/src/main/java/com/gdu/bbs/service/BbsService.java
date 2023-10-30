package com.gdu.bbs.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.gdu.bbs.dto.BbsDto;

public interface BbsService {
  public void loadBbsList(HttpServletRequest request, Model model);
  public BbsDto getBbs(int bbsNo);
  public int addBbs(BbsDto bbs);
  public int modifyBbs(BbsDto bbs);
  public int removeBbs(int bbsNo);
}
