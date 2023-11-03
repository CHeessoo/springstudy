package com.gdu.myhome.service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
  
  
}
