package com.gdu.app05.service;

import java.util.List;

import com.gdu.app05.dao.BoardDao;
import com.gdu.app05.dto.BoardDto;

public class BoardServiceImpl implements BoardService {
  
  private BoardDao boardDao = new BoardDao();

  @Override
  public List<BoardDto> getBoardList() {
    return boardDao.getBoardList();
  }

}
