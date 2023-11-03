package com.gdu.myhome.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dao.UploadMapper;
import com.gdu.myhome.dto.AttachDto;
import com.gdu.myhome.dto.UploadDto;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MyFileUtils;
import com.gdu.myhome.util.MyPageUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Transactional
@RequiredArgsConstructor
@Service
public class UploadServiceImple implements UploadService {
  
  private final UploadMapper uploadMapper;
  private final MyFileUtils myFileUtils;
  private final MyPageUtils myPageUtils;
  
  
  @Override
  public boolean addUpload(MultipartHttpServletRequest multipartRequest) throws Exception {
    
    String title = multipartRequest.getParameter("title");
    String contents = multipartRequest.getParameter("contents");
    int userNo = Integer.parseInt(multipartRequest.getParameter("userNo"));
    
    UploadDto upload = UploadDto.builder()
                        .title(title)
                        .contents(contents)
                        .userDto(UserDto.builder()
                                  .userNo(userNo)
                                  .build())
                        .build();
    
    int addUploadCount = uploadMapper.insertUpload(upload);  // 이후로 UploadDto에 uploadNo가 생성됨
    
    // Spring에서는 첨부된 파일을 MultipartFile이라고 부른다.
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
    // 파일 개수로는 첨부파일의 유무를 알 수 없음
    // 파일 이름으로 구분 : files.get(0).getOriginalFilename().isEmpty()
    // 파일 사이즈로 구분 : files.get(0).getSize() == 0
    int attachCount;
    if(files.get(0).getSize() == 0) {  // 등록된 파일 인덱스0의 사이즈가 0일때(==첨부파일이 없을때를 구분) 
      attachCount = 1;  // 첨부된게 없으면 attachCount가 1 (files.size()와 값을 맞춘다.)
    } else {
      attachCount = 0;  // 첨부된게 있으면 0개부터 시작
    }
    
    for(MultipartFile multipartFile : files) {
      
      if(multipartFile != null && !multipartFile.isEmpty()) {
        
        String path = myFileUtils.getUploadPath();
        File dir = new File(path);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        String orginalFilename = multipartFile.getOriginalFilename();
        String filesystemName = myFileUtils.getFilesystemName(orginalFilename);
        File file = new File(dir, filesystemName);
        
        multipartFile.transferTo(file);  // 실제로 파일이 저장되는 단계
        
        String contentType = Files.probeContentType(file.toPath()); // 이미지의 Content-Type은 image/jpeg, image/png 등 image로 시작한다.
        // null 체크가 필요하면 반드시 null 체크가 최우선적으로 온다.
        int hasThumbnail = (contentType != null && contentType.startsWith("image")) ? 1 : 0;  
        
        if(hasThumbnail == 1) {
          File thumbnail = new File(dir, "s_" + filesystemName); // small 이미지를 의미하는 s_을 덧붙임
          Thumbnails.of(file)           // 원본 파일 이미지를
                    .size(100, 100)     // 가로 100px, 세로 100px 크기의 작은 이미지로 변경해서
                    .toFile(thumbnail); // 썸네일을 제작한다.
        }
        
        AttachDto attach = AttachDto.builder()
                            .path(path)
                            .originalFilename(orginalFilename)
                            .filesystemName(filesystemName)
                            .hasThumbnail(hasThumbnail)
                            .uploadNo(upload.getUploadNo())
                            .build();
        
        attachCount += uploadMapper.insertAttach(attach);
        
      }  // if

    }  // for
    
    return (addUploadCount == 1) && (files.size() == attachCount);  // addUploadCount 값이 1이고, files.size()와 attachCount의 값이 같으면 true
  }

  
  @Transactional(readOnly=true)
  @Override
  public Map<String, Object> getUploadList(HttpServletRequest request) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    int total = uploadMapper.getUploadCount();
    int display = 9;
    
    myPageUtils.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<UploadDto> uploadList = uploadMapper.getUploadList(map);
    
    return Map.of("uploadList", uploadList
                , "totalPage" , myPageUtils.getTotalPage());  // 무한스크롤의 종료를 위해 전체페이지 수도 jsp로 넘겨줘야 한다.
  }
  
  
  @Transactional(readOnly=true)
  @Override
  public void loadUpload(HttpServletRequest request, Model model) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("uploadNo"));
    int uploadNo = Integer.parseInt(opt.orElse("0"));
    
    model.addAttribute("upload", uploadMapper.getUpload(uploadNo));
    model.addAttribute("attachList", uploadMapper.getAttachList(uploadNo));
    
  }
  
  @Override
  public ResponseEntity<Resource> download(HttpServletRequest request) {
    
    // 첨부 파일의 정보 가져오기
    int attachNo = Integer.parseInt(request.getParameter("attachNo"));
    AttachDto attach = uploadMapper.getAttach(attachNo);
    
    // 첨부 파일 File 객체 -> Resource 객체
    File file = new File(attach.getPath(), attach.getFilesystemName());
    Resource resource = new FileSystemResource(file);
    
    // 첨부 파일이 없으면 다운로드 취소
    if(!resource.exists()) {
      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
    }
    // 다운로드 횟수 증가하기
    uploadMapper.updateDownloadCount(attachNo);
    
    // 사용자가 다운로드 받을 파일의 이름 결정(User-Agent값에 따른 인코딩 처리)
    String originalFilename = attach.getOriginalFilename();
    String userAgent = request.getHeader("User-Agent");
    try {
      // IE
      if(userAgent.contains("Trident")) {
        originalFilename = URLEncoder.encode(originalFilename, "UTF-8").replace("+", " "); // 인터넷 익스플로러는 공백이 +로 치환되기 때문에 리플레이스로 다시 변경해줌
      }
      // Edge
      else if(userAgent.contains("Edg")) {
        originalFilename = URLEncoder.encode(originalFilename, "UTF-8"); 
        
      }
      // Other
      else {
        originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // 다운로드 응답 헤더 만들기
    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/octet-stream"); // setContentType() 과 동일
    header.add("Content-Disposition", "attachment; filename=" + originalFilename);
    header.add("Content-Length", file.length() + "");  // 기본적으로 응답헤더는 스트링(빈문자열을 더해서 스트링 값으로 변환)
    
    
    // 응답
    return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    
  }
  
  
  @Override
  public ResponseEntity<Resource> downloadAll(HttpServletRequest request) {
    
    // 다운로드 할 모든 첨부 파일 정보 가져오기
    int uploadNo = Integer.parseInt(request.getParameter("uploadNo"));
    List<AttachDto> attachList = uploadMapper.getAttachList(uploadNo);
    
    // 첨부 파일이 없으면 종료
    if(attachList.isEmpty()) {
      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
    }
    
    // zip 파일을 생성할 경로
    File tempDir = new File(myFileUtils.getTempPath());
    if(!tempDir.exists()) {
      tempDir.mkdirs();
    }
    
    // zip 파일의 이름
    String zipName = myFileUtils.getTempFilename() + ".zip";
    
    // zip 파일의 File 객체 
    File zipFile = new File(tempDir, zipName);
    
    // zip 파일을 생성하는 출력 스트림
    ZipOutputStream zout = null;
    
    // 첨부 파일들을 순회하면서 zip 파일에 등록하기
    try {
      
      zout = new ZipOutputStream(new FileOutputStream(zipFile));
      
      for(AttachDto attach : attachList) {
        
        // 각 첨부 파일들의 원래 이름을 zip 파일에 등록하기 (이름만 등록)
        ZipEntry zipEntry = new ZipEntry(attach.getOriginalFilename());
        zout.putNextEntry(zipEntry);
        
        // 각 첨부 파일들의 내용을 zip 파일에 등록하기 (실제 파일 등록)
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(attach.getPath(), attach.getFilesystemName())));
        zout.write(bin.readAllBytes());
        
        // 자원 반납
        bin.close();
        zout.closeEntry();
        
        // 다운로드 횟수 증가
        uploadMapper.updateDownloadCount(attach.getAttachNo());
        
      }
      
      // zout 자원 반납
      zout.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // 다운로드할 zip 파일의 File 객체 -> Resource 객체
    Resource resource = new FileSystemResource(zipFile);
    
    // 다운로드 응답 헤더 만들기
    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/octet-stream"); // setContentType() 과 동일
    header.add("Content-Disposition", "attachment; filename=" + zipName);
    header.add("Content-Length", zipFile.length() + "");    // 기본적으로 응답헤더는 스트링(빈문자열을 더해서 스트링 값으로 변환)
    
    
    // 응답
    return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
  }
  
  
}
