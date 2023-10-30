package com.gdu.myhome.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myhome.dto.BlogDto;
import com.gdu.myhome.dto.BlogImageDto;

@Mapper
public interface BlogMapper {
  
  public int insertBlog(BlogDto blog);
  public int insertBlogImage(BlogImageDto blogImageDto);
  public List<BlogImageDto> getBlogImageInYesterday();
  public int getBlogCount();
  public List<BlogDto> getBlogList(Map<String, Object> map);

}
