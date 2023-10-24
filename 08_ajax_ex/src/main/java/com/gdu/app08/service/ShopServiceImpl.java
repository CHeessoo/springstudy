package com.gdu.app08.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {
  
  @Override
  public ResponseEntity<String> getShoppingList(HttpServletRequest request) {
    
    try {
      
      // 요청 파라미터
      String query = request.getParameter("query");
      String display = request.getParameter("display");
      String sort = request.getParameter("sort");
      
      // 검색어 인코딩 UTF-8
      query = URLEncoder.encode(query, "UTF-8");
      
      // 클라이언트 아이디, 시크릿(네이버개발자센터에서 발급 받은 본인 정보 사용합니다.)
      String clientId = "i8c_oqXanvYAot3MyrU7";
      String clientSecret = "2JiF7i7Eaq";
      
      // API 주소
      String apiURL = "https://openapi.naver.com/v1/search/shop.json?query=" + query + "&display=" + display + "&sort=" + sort;
      
      // URL
      URL url = new URL(apiURL);
      
      // HttpURLConnection
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      
      // 요청 메소드
      con.setRequestMethod("GET");
      
      // 요청 헤더에 포함하는 내용
      con.setRequestProperty("X-Naver-Client-Id", clientId);
      con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
      
      // 네이버 검색 API로부터 번역 결과를 받아 올 입력 스트림 생성
      
      
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }

}
