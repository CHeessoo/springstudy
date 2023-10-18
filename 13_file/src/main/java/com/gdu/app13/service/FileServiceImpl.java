package com.gdu.app13.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app13.util.MyFileUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
  
  private final MyFileUtil myFileUtil;

  @Override
  public int upload(MultipartHttpServletRequest multipartRequest) {
    
    // 첨부된 파일들의 목록 
    List<MultipartFile> files = multipartRequest.getFiles("files");  // 스프링에서는 첨부 파일 타입을 MultipartFile로 잡아줘야함
    
    // 순회
    for(MultipartFile multipartFile : files) {
      
      // 첨부 여부 확인
      if(multipartFile != null && !multipartFile.isEmpty()) {  // 첨부 파일이 null이 아니거나 비어있지 않다면
       
        try {
          
          // 첨부 파일이 저장될 경로 가져오기
          String path = myFileUtil.getPath();
          
          // 저장될 경로의 디렉터리 만들기
          File dir = new File(path);
          if(!dir.exists()) {
            dir.mkdirs();
          }
          
          // 첨부 파일의 원래 이름 알아내기 
          String originalName = multipartFile.getOriginalFilename();
          
          // 첨부 파일이 저장될 이름 가져오기
          String filesystemName = myFileUtil.getFilesystemName(originalName);
          
          // 첨부 파일의 File 객체
          File file = new File(dir, filesystemName);
          
          // 첨부 파일 저장하기
          multipartFile.transferTo(file);
          
        } catch (Exception e) {
          e.printStackTrace();
        }
        
      }
      
    }
    
    return 0; // 실제로는 나중에 구현을 더 하게되면 addResult(insert한) 값을 반환
  }
  
  
  @Override
  public Map<String, Object> ajaxUpload(MultipartHttpServletRequest multipartRequest) {
    
    // 첨부된 파일들의 목록 
    List<MultipartFile> files = multipartRequest.getFiles("files");  // 스프링에서는 첨부 파일 타입을 MultipartFile로 잡아줘야함
    
    // 순회
    for(MultipartFile multipartFile : files) {
      
      // 첨부 여부 확인
      if(multipartFile != null && !multipartFile.isEmpty()) {  // 첨부 파일이 null이 아니거나 비어있지 않다면
       
        try {
          
          // 첨부 파일이 저장될 경로 가져오기
          String path = myFileUtil.getPath();
          
          // 저장될 경로의 디렉터리 만들기
          File dir = new File(path);
          if(!dir.exists()) {
            dir.mkdirs();
          }
          
          // 첨부 파일의 원래 이름 알아내기 
          String originalName = multipartFile.getOriginalFilename();
          
          // 첨부 파일이 저장될 이름 가져오기
          String filesystemName = myFileUtil.getFilesystemName(originalName);
          
          // 첨부 파일의 File 객체
          File file = new File(dir, filesystemName);
          
          // 첨부 파일 저장하기
          multipartFile.transferTo(file);
          
        } catch (Exception e) {
          e.printStackTrace();
        }
        
      }
      
    }
    return Map.of("success", true);  // 성공 반환, 본문 코드는 위 upload()메소드와 동일하나 Map을 반환하는것만 다르다.
  }
  
  
  @Override
  public Map<String, Object> ckeditorUpload(MultipartFile upload, String contextPath) {  // context path만 String으로 빼서 전달 받음
    
    // 이미지 저장할 경로
    String path = myFileUtil.getPath();
    File dir = new File(path);
    if(!dir.exists()) {
      dir.mkdirs();
    }
    
    // 이미지 저장할 이름 (원래 이름 + 저장할 이름)
    String originalFilename = upload.getOriginalFilename();
    String filesystemName = myFileUtil.getFilesystemName(originalFilename);
    
    // 이미지 File 객체
    File file = new File(dir, filesystemName);
    
    // File 객체를 참고하여, MultipartFile upload 첨부 이미지 저장
    try {
      upload.transferTo(file);  // upload를 file객체 정보로 보냄
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    System.out.println(contextPath + path + "/" + filesystemName);
    // CKEditor로 저장된 이미지를 확인할 수 있는 경로를 {"url": "http://localhost:8080/app13/..."} 방식으로 반환해야 한다.(정해진 방식)
    return Map.of("url", contextPath + path + "/" + filesystemName
                , "uploaded", true); // "uploaded", true 를 꼭 붙여줘야함
    /*
     * CKEditor로 반환할 url
     * 
     * servlet-context.xml에
     *   /storage/** 주소로 요청을 하면 /storage/ 디렉터리의 내용을 보여주는 <resources>태그를 추가한다.
     */
    
    /*
     * 직접 경로를 지정하는 방식
     *   return Map.of("url", contextPath + "/display.do?path=" + path + "&filename=" + filesystemName
     *               , "uploaded", true);
     */

  }

}
