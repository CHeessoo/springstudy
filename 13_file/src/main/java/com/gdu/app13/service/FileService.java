package com.gdu.app13.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileService {
  public int upload(MultipartHttpServletRequest multipartRequest);
}
