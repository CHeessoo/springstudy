package com.gdu.app14.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.swing.Spring;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.app14.dto.MemberDto;
import com.gdu.app14.service.MemberService;

import lombok.RequiredArgsConstructor;


/*
 * REST(REpresentational State Transfer)
 * 1. 요청 주소를 작성하는 새로운 방식이다.
 * 2. 변수를 파라미터로 추가하는 Query String 방식이 아니다.
 * 3. 변수를 주소에 포함시키는 Path Variable 방식을 사용한다.
 * 4. "요청 주소(URL) + 요청 방식(Method)"을 합쳐서 요청을 구분한다.
 * 5. 예시
 *          URL         Method
 *  1) 목록 /members    GET         /members/page/1
 *  2) 상세 /members/1  GET
 *  3) 삽입 /members    POST
 *  4) 수정 /members    PUT
 *  5) 삭제 /member/1   DELETE      /members/4,3,2 (여러명 삭제)
 */

@RequiredArgsConstructor
@RestController  // 모든 메소드에 @ResponseBody를 추가한다.
public class MemberController {

  private final MemberService memberService;
  
  
  // 회원 등록 요청
  @RequestMapping(value="/members", method=RequestMethod.POST, produces="application/json")
  public Map<String, Object> registerMember(@RequestBody MemberDto memberDto, HttpServletResponse response) {
    return memberService.register(memberDto, response);
  }
  
  // 회원 목록 요청
  // 경로에 포함되어 있는 변수 {page}는 @pathVariable을 이용해서 가져올 수 있다.
  @RequestMapping(value="/members/page/{p}", method=RequestMethod.GET, produces="application/json")
  public Map<String, Object> getMembers(@PathVariable(value="p", required=false) Optional<String> opt) {  // Optional<String>로 Null 처리를 하면서 받아옴(경로에 포함되어 있는 값은 String으로 본다.)
    int page = Integer.parseInt(opt.orElse("1"));
    return memberService.getMembers(page);
  }
  
  // 회원 조회 요청
  @RequestMapping(value="/members/{mNo}", method=RequestMethod.GET, produces="application/json")
  public Map<String, Object> getMember(@PathVariable(value="mNo") int memberNo) {  // int로 받았기 때문에 null 값이 전달될수도 있다.(10.17-2교시38분)
    return memberService.getMember(memberNo);
  }
  
  // 회원 정보 수정 요청
  @RequestMapping(value="/members", method=RequestMethod.PUT, produces="application/json")
  public Map<String, Object> modifyMember(@RequestBody MemberDto memberDto){  // @RequestBody로 요청 본문을 통해 전달되는 데이터를 받아서 사용한다.
    return memberService.modifyMember(memberDto);
  }
  
  // 회원 정보 삭제
  @RequestMapping(value="/member/{memberNo}", method=RequestMethod.DELETE, produces="application/json")
  public Map<String, Object> removeMember(@PathVariable(value="memberNo") int memberNo) {
    return memberService.removeMember(memberNo);
  }
  
  // 회원들 정보 삭제(선택 삭제)
  @RequestMapping(value="/members/{memberNoList}", method=RequestMethod.DELETE, produces="application/json")
  public Map<String, Object> removeMembers(@PathVariable(value="memberNoList") String memberNoList){  // 전달된 값을 문자열로 받음
    System.out.println(memberNoList);
    return memberService.removeMembers(memberNoList);
  }
  
}
