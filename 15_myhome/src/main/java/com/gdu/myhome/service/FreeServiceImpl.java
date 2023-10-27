package com.gdu.myhome.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.myhome.dao.FreeMapper;
import com.gdu.myhome.dto.FreeDto;
import com.gdu.myhome.util.MyPageUtils;
import com.gdu.myhome.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class FreeServiceImpl implements FreeService {
  
  private final FreeMapper freeMapper;
  private final MyPageUtils myPageUtils;
  private final MySecurityUtils mySecurityUtils;
  
  @Override
  public int addFree(HttpServletRequest request) {
    
    String email = request.getParameter("email");
    String contents = mySecurityUtils.preventXSS(request.getParameter("contents"));
    
    FreeDto free = FreeDto.builder()
                    .email(email)
                    .contents(contents)
                    .build();
    
    return freeMapper.insertFree(free);
  }
  
  @Transactional(readOnly=true)
  @Override
  public void loadFreeList(HttpServletRequest request, Model model) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page")); // 페이지번호가 전달되지 않은 경우
    int page = Integer.parseInt(opt.orElse("1"));                             // 1페이지로 본다.
    
    int display = 10;                                                         // 한번에 보여줄 게시글의 개수
    
    int total = freeMapper.getFreeCount();                                    // 전체 게시글 개수
    
    myPageUtils.setPaging(page, total, display);                              // myPageUtils 호출해서 setPaging(page, total, display)전달
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<FreeDto> freeList = freeMapper.getFreeList(map);
    
    model.addAttribute("freeList", freeList);
    model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/free/list.do"));
    model.addAttribute("beginNo", total - (page - 1) * display);
    
  }
  
  
  @Override
  public int addReply(HttpServletRequest request) {
    /* 댓글 달기 */
    
    // 요청 파라미터(댓글 작성 회면에서 받아오는 정보들)
    // 댓글 정보(EDITOR, CONTENTS)
    // 원글 정보(DEPTH, GROUP_NO, GROUP_ORDER)
    String email = request.getParameter("email");
    String contents = request.getParameter("contents");
    int depth = Integer.parseInt(request.getParameter("depth"));
    int groupNo = Integer.parseInt(request.getParameter("groupNo"));
    int groupOrder = Integer.parseInt(request.getParameter("groupOrder"));
    
    
    // 원글 DTO
    // 기존댓글업데이트(원글DTO)
    FreeDto free = FreeDto.builder()
                    .groupNo(groupNo)
                    .groupOrder(groupOrder)
                    .build();
    freeMapper.updateGroupOrder(free);
    
    
    // 댓글DTO
    // 댓글삽입(댓글DTO)
    FreeDto reply = FreeDto.builder()
                     .email(email)
                     .contents(contents)
                     .depth(depth + 1)
                     .groupNo(groupNo)
                     .groupOrder(groupOrder + 1)
                     .build();
    int addReplyResult = freeMapper.insertReply(reply);
     
    return addReplyResult;
  }
  
  
  @Override
  public int removeFree(int freeNo) {
    return freeMapper.deleteFree(freeNo);
  }
  
  @Transactional(readOnly=true)
  @Override
  public void loadSearchList(HttpServletRequest request, Model model) {
    
    String column = request.getParameter("column");
    String query = request.getParameter("query");
    
    Map<String, Object> map = new HashMap<>();
    map.put("column", column);
    map.put("query", query);
    
    int total = freeMapper.getSearchCount(map);

    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    String strPage = opt.orElse("1");
    int page = Integer.parseInt(strPage);
    
    int display = 10;
    
    myPageUtils.setPaging(page, total, display);
    
    map.put("begin", myPageUtils.getBegin());
    map.put("end", myPageUtils.getEnd());
    
    List<FreeDto> freeList = freeMapper.getSearchList(map);
    
    model.addAttribute("freeList", freeList);
    model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/free/search.do", "column=" + column + "&query=" + query));
    model.addAttribute("beginNo", total - (page - 1) * display);
    
  }

    
    

}
