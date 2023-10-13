package com.gdu.app13.util;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MyFileUtil {

  // 파일이 저장될 경로 반환하기
  public String getPath() {
    /*   /storage/yyyy/MM/dd   */
    LocalDate today = LocalDate.now();
    return "/storage/" + today.getYear() + "/" + String.format("%02d", today.getMonthValue()) + "/" + String.format("%02d", today.getDayOfMonth());
    // return "/storage/" + DateTimeFormatter.ofPattern("yyyy/MM.dd").format(today);
  }
  
  // 파일이 저장될 이름 반환하기
  public String getFilesystemName(String originalName) {
    
    /* UUID.확장자 */
    /* UUID (universally unique identifier) : 범용 고유 식별자 */
    
    String extName = null;
    if(originalName.endsWith("tar.gz")) {        // 확장자에 마침표가 포함되는 예외 경우를 처리한다.
      extName = "tar.gz";
    } else {
      String[] arr = originalName.split("\\.");  // 정규식이기 때문에 [.] 또는 \\. 로 마침표를 구분해준다.
      extName = arr[arr.length - 1];             // 마지막 인덱스 찾는 방법 (배열.길이 -1)
    }
   
    return UUID.randomUUID().toString().replace("-", "") + "." + extName;  // UUID는 생성될 때 (-)하이픈이 계속 들어가기 때문에 replace를 이용해서 빈문자열로 바꿔줌
    
  }
  
}
