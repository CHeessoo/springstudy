package com.gdu.myhome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdu.myhome.util.MySecurityUtils;

@Controller
public class MvcController {
  
  @Autowired
  private MySecurityUtils mySecurityUtils;

  @RequestMapping(value= {"/", "/main.do"}, method=RequestMethod.GET)
  public String index() {
    System.out.println(mySecurityUtils.getSHA256("1111"));
    return "index";
  }

  
}
