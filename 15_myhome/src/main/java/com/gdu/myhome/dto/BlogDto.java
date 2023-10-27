package com.gdu.myhome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BlogDto {
  
  private int blogNo;
  private String title;
  private String contents;
  private int userNo;           // 사용자와 관계를 userNo로 줌
  private int hit;              // 조회수
  private String ip;
  private String createdAt;
  private String modifiedAt;

}
