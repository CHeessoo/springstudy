package com.gdu.myhome.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MyFileUtils {

  // 블로그 작성시 사용된 이미지가 저장될 경로 반환하기
  public String getBlogImagePath() {
    
    /*   /blog/yyyy/MM/dd   */
    LocalDate today = LocalDate.now();
    return "/blog/" + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(today);
  }
  
  // 블로그 이미지가 저장된 어제 경로를 반환
  public String getBlogImagePathYesterday() {
    LocalDate date = LocalDate.now();
    date = date.minusDays(1);  // 1일 전
    return "/blog/" + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(date);
  }
  
  // 업로드 게시판 작성시 첨부한 파일이 저장될 경로 반환하기
  public String getUploadPath() {

    LocalDate today = LocalDate.now();
    return "/upload/" + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(today);
  }
  
  // 임시 파일이 저장될 경로 반환하기 (zip 파일)
  public String getTempPath() {
    return "/temporary";
  }
  
  // 파일이 저장될 이름 반환하기
  public String getFilesystemName(String originalFileName) {
    
    /* UUID.확장자 */
    /* UUID (universally unique identifier) : 범용 고유 식별자 */
    
    String extName = null;
    if(originalFileName.endsWith("tar.gz")) {        // 확장자에 마침표가 포함되는 예외 경우를 처리한다.
      extName = "tar.gz";
    } else {
      String[] arr = originalFileName.split("\\.");  // 정규식이기 때문에 [.] 또는 \\. 로 마침표를 구분해준다.
      extName = arr[arr.length - 1];             // 마지막 인덱스 찾는 방법 (배열.길이 -1)
    }
   
    return UUID.randomUUID().toString().replace("-", "") + "." + extName;  // UUID는 생성될 때 (-)하이픈이 계속 들어가기 때문에 replace를 이용해서 빈문자열로 바꿔줌
  }
  
  // 임시 파일 이름 반환하기 (확장자는 제외)
  public String getTempFilename() {
    return System.currentTimeMillis() + "";
  }
  
  
}
