package com.gdu.myhome.service;

import org.springframework.stereotype.Service;

import com.gdu.myhome.dao.UserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSerivceImpl implements UserService {

  private final UserMapper userMapper;
}
