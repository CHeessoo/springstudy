package com.gdu.bbs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.bbs.dto.BbsDto;
import com.gdu.bbs.service.BbsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BbsController {
  
  private final BbsService bbsService;
  
  // select는 model에 저장한 뒤 forward한다.
  @GetMapping("/list.do")
  public String list(HttpServletRequest request, Model model) {
    bbsService.loadBbsList(request, model);
    return "bbs/list";
  }
  
  // select는 model에 저장한 뒤 forward한다.
  @GetMapping("/detail.do")
  public String detail(@RequestParam(value="bbsNo", required=false, defaultValue="0") int bbsNo // @RequestParam은 주소창 뒤에 파라미터가 붙어서 전달될 때 사용
                     , Model model) {  
    BbsDto bbs = bbsService.getBbs(bbsNo);
    model.addAttribute("bbs", bbs);
    return "bbs/detail";
  }
  
  // insert는 redirectAttributes에 저장한 뒤 redirect한다.
  @PostMapping("/add.do")
  public String add(BbsDto bbs, RedirectAttributes attr) {
    int addResult = bbsService.addBbs(bbs);
    attr.addFlashAttribute("addResult", addResult);
    return "redirect:list.do";
  }
  
  // update는 redirectAttribute에 저장한 뒤 redirect한다.
  @PostMapping("/modify.do")
  public String modify(BbsDto bbs, RedirectAttributes attr) {
    int modifyResult = bbsService.modifyBbs(bbs);
    attr.addFlashAttribute("modifyResult", modifyResult);
    return "redirect:/detail.do?bbsNo=" + bbs.getBbsNo();
  }
  
  
  // delete는 redirectAttribute에 저장한 뒤 redirect한다.
  @PostMapping("/remove.do")
  public String remove(@RequestParam(value="bbsNo", required=false, defaultValue="0") int bbsNo // @RequestParam은 주소창 뒤에 파라미터가 붙어서 전달될 때 사용
                     , RedirectAttributes attr) {
    int removeResult = bbsService.removeBbs(bbsNo);
    attr.addFlashAttribute("removeResult", removeResult);
    return "redirect:/list.do";
  }
  
}
