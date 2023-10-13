package com.gdu.app13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app13.service.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FileController {
  
  private final FileService fileService;

  @RequestMapping(value="/upload.do", method=RequestMethod.POST)
  public String upload(MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes) {
    int addResult = fileService.upload(multipartRequest);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/main.do";  // insert한 값을 받았기 때문에 redirect로 전달
  }
  
}
