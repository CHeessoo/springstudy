package com.gdu.myhome.service;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dao.BlogMapper;
import com.gdu.myhome.dto.BlogDto;
import com.gdu.myhome.dto.BlogImageDto;
import com.gdu.myhome.util.MyFileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlogServiceImple implements BlogService {
  
  private final BlogMapper blogMapper;
  private final MyFileUtils myFileUtils;
  
  @Override
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest) {
    
    // 이미지가 저장될 경로
    String imagePath = myFileUtils.getBlogImagePath();
    File dir = new File(imagePath);
    if(!dir.exists()) {
      dir.mkdirs();
    }
    
    // 이미지 파일 (CKEditor는 이미지를 upload라는 이름으로 보냄 - 메뉴얼로 확인 가능)
    MultipartFile upload = multipartRequest.getFile("upload");
    
    // 이미지가 저장될 이름
    String originalFileName = upload.getOriginalFilename();
    String filesystemName = myFileUtils.getFilesystemName(originalFileName);
    
    // 이미지 File 객체
    File file = new File(dir, filesystemName);
    
    // 저장
    try {
      upload.transferTo(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // CKEditor로 저장된 이미지의 경로를 JSON 형식으로 반환해야 함
    return Map.of("uploaded", true
                , "url", multipartRequest.getContextPath() + imagePath + "/" + filesystemName);
    
    // url: "http://localhost:8080/myhome/blog/2023/10/27/파일명" 
    // servlet-context.xml에 
    // /blog/** 주소 요청을 /blog 디렉터리로 연결하는 <resources> 태그를 추가해야 함
  }
  
  
  @Override
  public int addBlog(HttpServletRequest request) {
    
    // BLOG_T에 추가할 데이터
    String title = request.getParameter("title");
    String contents = request.getParameter("contents");
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    String ip = request.getRemoteAddr();
    
    // BlogDto 생성
    BlogDto blog = BlogDto.builder()
                    .title(title)
                    .contents(contents)
                    .userNo(userNo)
                    .ip(ip)
                    .build();
    
    // BLOG_T에 추가
    // BlogMapper의 insertBlog() 메소드를 실행하면
    // 메소드로 전달한 blog 객체에 blogNo값이 저장된다. (insert 이후에 저장됨을 반드시 기억!)
    int addResult = blogMapper.insertBlog(blog);  
    
    // BLOG 작성시 사용한 이미지 목록 (Jsoup 라이브러리 사용)
    Document document = Jsoup.parse(contents);
    Elements elements = document.getElementsByTag("img");  // 유사 배열
    if(elements != null) {
      for(Element element : elements) {
        String src = element.attr("src");
        String filesystemname = src.substring(src.lastIndexOf("/") + 1);
        BlogImageDto blogImage = BlogImageDto.builder()
                              .blogNo(blog.getBlogNo())
                              .imagePath(myFileUtils.getBlogImagePath())
                              .filesystemName(filesystemname)
                              .build();
        blogMapper.insertBlogImage(blogImage);
      }
    }
    
    return addResult;
  }
  

}
