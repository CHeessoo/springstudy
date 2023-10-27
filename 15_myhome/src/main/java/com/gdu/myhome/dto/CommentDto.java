package com.gdu.myhome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentDto {

  private int commentNo;
  private String contents;
  private int userNo;        // 사용자와 관계를 userNo로 줌
  private int blogNo;
  private String createdAt;
  private int status;
  private int depth;
  private int groupNo;
  
}