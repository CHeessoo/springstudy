package com.gdu.myhome.controller;

import org.springframework.stereotype.Controller;

import com.gdu.myhome.service.UploadService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UploadController {
  
  private final UploadService uploadService;

}
