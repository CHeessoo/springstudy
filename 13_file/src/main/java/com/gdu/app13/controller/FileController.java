package com.gdu.app13.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app13.service.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FileController {
  
  private final FileService fileService;

  @RequestMapping(value="/upload.do", method=RequestMethod.POST)
  public String upload(MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes) { // MultipartHttpServletRequest : 파일 첨부된 폼을 받는 request
    int addResult = fileService.upload(multipartRequest);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/main.do";  // insert한 값을 받았기 때문에 redirect로 전달
  }
  
  @RequestMapping(value="/ajax/upload.do", method=RequestMethod.POST, produces="application/json") // json을 반환하는 (10.13-3교시20~2분)은 명시해줘야함. Java에서는 json을 appliaction/json 으로 인지함
  @ResponseBody  // ajax 처리하는 controller 메소드는 @ResponseBody가 기본적으로 필요하다.
  public Map<String, Object> ajaxUpload(MultipartHttpServletRequest multipartRequest){
    return fileService.ajaxUpload(multipartRequest);
  }
  
  
  @RequestMapping(value="/ckeditor/upload.do", method=RequestMethod.POST, produces="application/json") // 이미지 업로드는 다 POST방식(주소창에 이미지를 실어서 보낼 수 없음)
  @ResponseBody
  public Map<String, Object> ckeditorUpload(MultipartFile upload, HttpServletRequest request){  // MultipartFile의 이름은 반드시 upload 여야 받아진다.(ckedior에서 지켜져야되는 사항)
                                                                                                // 반환타입이 Map인건 반환하는게 (10.18-1교시 25~26분)이라는 의미(@ResponseBody를 반드시 붙여줘야한다.)
    return fileService.ckeditorUpload(upload, request.getContextPath());  // request에 있는 context path만 빼서 전달
  }
  
  @RequestMapping(value="/add.do", method=RequestMethod.POST)
  public void add(@RequestParam String contents) {
    System.out.println(contents);
  }
  
}
