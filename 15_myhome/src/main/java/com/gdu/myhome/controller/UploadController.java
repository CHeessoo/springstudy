package com.gdu.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.myhome.service.UploadService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/upload")
@RequiredArgsConstructor
@Controller
public class UploadController {
  
  private final UploadService uploadService;
  
  @GetMapping("/list.do")
  public String list() {
    return "upload/list";
  }
  
  @GetMapping("/write.form")
  public String write() {
    return "upload/write";
  }
  
  @PostMapping("/add.do")
  public String add() {
    return null;
  }

}
