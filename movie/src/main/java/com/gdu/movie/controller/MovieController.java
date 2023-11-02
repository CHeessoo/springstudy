package com.gdu.movie.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.movie.service.MovieService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/movie")
@RequiredArgsConstructor
@RestController // ajax 전용 Controller (ajax외 사용 불가)
public class MovieController {
  
  private final MovieService movieService;
  
  @GetMapping(value="/searchAllMovies", produces="application/json")
  public Map<String, Object> searchAllMovies(){
    return movieService.getMovieList();
  }

}
