package com.gdu.movie.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gdu.movie.dao.MovieMapper;
import com.gdu.movie.dto.MovieDto;
import com.gdu.movie.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
  
  private final MovieMapper movieMapper;
  private final MySecurityUtils mySecurityUtils;
  
  @Override
  public Map<String, Object> getMovieList() {
    
    int movieCount = movieMapper.getMovieCount();
    List<MovieDto> list = movieMapper.getMovieList();
    
    return Map.of("message", "전체" + movieCount + "개의 목록을 가져왔습니다."
                , "list", list
                , "status", 200);
  }
  
  @Override
  public Map<String, Object> getSearchMovie(HttpServletRequest request) {
    
    String searchText = mySecurityUtils.preventXSS(request.getParameter("searchText"));
    
    Map<String, Object> map = Map.of("column", request.getParameter("column")
                                   , "searchText", searchText);
    
    int searchCount = movieMapper.getSearchCount(map);
    
    if(searchCount != 0) {
      List<MovieDto> list = movieMapper.getSearchMovie(map);
      return Map.of("message", "전체" + searchCount + "개의 목록을 가져왔습니다."
                  , "list", list
                  , "status", 200);
    } else {
      Map<String, Object> map2 = new HashMap<String, Object>();
      map2.put("message", searchText + "검색 결과가 없습니다.");
      map2.put("list", null);
      map2.put("status", 500);
      return map2;
    }
    
  }
  
  @Override
  public void findComedy() {
    
    Map<String, Object> map = Map.of("column", "genre", "searchText", "코미디");
    
    List<MovieDto> searchComedy = movieMapper.getSearchMovie(map);
    if(searchComedy != null) {
      File file = new File("코미디.txt");
      

      
    }
    
    
    
  }

}











