package com.gdu.app09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdu.app09.service.ContactService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  // private final ContactService contactService;에 @Autowired를 하기 위한 코드이다.
@Controller
public class ContactController {
  
  private final ContactService contactService;
  
  @RequestMapping(value="/contact/list.do", method=RequestMethod.GET)
  public String list(Model model) {  // forward 전달을 위한 Model 선언
    model.addAttribute("contactList", contactService.getContactList());
    return "contact/list";
  }
  
}
