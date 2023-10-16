package com.gdu.app07.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.app07.dto.AjaxDto;
import com.gdu.app07.service.AjaxService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/ajax3")
@RequiredArgsConstructor
@Controller
public class AjaxController3 {

  private final AjaxService ajaxService;
  
  @GetMapping(value="/list.do", produces="application/json; charset=UTF-8")
  public ResponseEntity<List<AjaxDto>> list() {  // ResponseEntity : Ajax 응답 전용 스프링 클래스, @ResponseBody가 포함되어 있다.
    // ResponseEntity 생성자로 만들어서 전달
    return new ResponseEntity<List<AjaxDto>>(    // ResponseEntity는 @ResponseBody를 내장하고 있기 때문에 작성하지 않는다.
                 ajaxService.getDtoList()        // 실제 응답 데이터
               , HttpStatus.OK);                 // 응답 코드(200)
  }
  
  @PostMapping(value="/detail.do")
  public ResponseEntity<AjaxDto> detail(@RequestBody AjaxDto ajaxDto) {  // post 방식(@RequestBody)으로 전송된 JSON 데이터(AjaxDto ajaxDto)
                                                                         // 요청 본문(@RequestBody)에 있는 객체(AjaxDto)를 전달받음(파라미터가 아님)
    // 응답 헤더 : Content-Type (produces="application/json; charset=UTF-8"을 대체한다.)
    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/json; charset=UTF-8");       // header에 Content-Type 저장
    // 반환
    return new ResponseEntity<AjaxDto>(                 // ResponseEntity는 @ResponseBody를 작성하지 않는다.
                 ajaxService.getDto(ajaxDto.getName())  // 실제 응답 데이터
               , header                                 // 응답 헤더
               , HttpStatus.OK);                        // 응답 코드 (200) (오류 찾기 안 해서 정상 코드만 전송)
  }
  
}
