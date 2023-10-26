package com.gdu.bbs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bbs.service.BbsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BbsController {
  
  private final BbsService bbsService;
  
  @GetMapping("/bbs/list.do")
  public String list(HttpServletRequest request, Model model) {
    bbsService.loadBbsList(request, model);
    return "bbs/list";
  }
  
}
