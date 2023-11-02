package com.gdu.movie.controller;

import org.springframework.web.bind.annotation.RestController;

import com.gdu.movie.service.MovieService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController // ajax 전용 Controller (ajax외 사용 불가)
public class MovieController {
  
  private final MovieService movieService;

}
