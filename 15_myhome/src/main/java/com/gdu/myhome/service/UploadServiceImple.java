package com.gdu.myhome.service;

import org.springframework.stereotype.Service;

import com.gdu.myhome.dao.UploadMapper;
import com.gdu.myhome.util.MyFileUtils;
import com.gdu.myhome.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UploadServiceImple implements UploadService {
  
  private final UploadMapper uploadMapper;
  private final MyFileUtils myFileUtils;
  private final MyPageUtils myPageUtils;

}
