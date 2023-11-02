package com.gdu.movie.service;

import org.springframework.stereotype.Service;

import com.gdu.movie.dao.MovieMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
  
  private final MovieMapper movieMapper;

}
