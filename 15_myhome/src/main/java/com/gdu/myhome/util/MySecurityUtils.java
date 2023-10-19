package com.gdu.myhome.util;

import java.security.MessageDigest;

import org.springframework.stereotype.Component;

@Component
public class MySecurityUtils {

  /*
   * SHA256 암호화
   * (Secure Hash Algorithm)
   * 
   * 1. 입력값을 256비트(32바이트)로 암호화하는 해시 알고리즘이다.
   * 2. 원본을 암화화할 수 있으나, 암호화된 결과를 원본으로 되돌리는 복호화는 불가능하다. (단방향)
   * 3. java.security 패키지를 활용해서 구하거나, 암호화 디펜던시(예시 commons-lang3)를 활용할 수 있다.
   */
  public String getSHA256(String password) {
    StringBuilder sb = new StringBuilder();
    try {
      // MessageDigest는 자바에서 단방향 해시 함수 값을 구할 때 사용
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");  // getInstance(알고리즘명시)
      messageDigest.update(password.getBytes());             // 입력받은 password의 바이트 배열 값을 입력해준다.
      byte[] b = messageDigest.digest();                     // 암호화된 32바이트 배열이 생성됨
      for(int i = 0; i < b.length; i++) {
        sb.append(String.format("%02X", b[i]));               // 2자리 16진수 문자열로 변환하기(0으로 시작하는 값은 0으로 표현하기 10.19-4교시 22분)
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sb.toString();
  }
  
}
