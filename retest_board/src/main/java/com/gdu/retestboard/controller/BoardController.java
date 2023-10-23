package com.gdu.retestboard.controller;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

  @GetMapping
  public String write(HttpServletRequest request) {  // 그냥 궁금한거 하나 테스트용(정식으로 짠 코드 아님)
    String ipAddress1 = request.getRemoteAddr(); // 1번
    System.out.println(ipAddress1);
    
    String ipAddress2 = request.getRemoteAddr();
    if(ipAddress2.equalsIgnoreCase("0:0:0:0:0:0:0:1")){
        InetAddress inetAddress = InetAddress.getLocalHost(); // 예외처리안함
        ipAddress2 = inetAddress.getHostAddress();
    }
    
    return null;
  }
}
