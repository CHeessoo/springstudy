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
  private int hit;            // 조회수
  private String ip;
  private String createdAt;
  private String modifiedAt;
  private UserDto userDto;    // private int userNo; // 사용자와 관계를 userNo로 줌 (association으로 추가하는 부분은 위치를 맞춰서 바꿔주는것이 좋다.)

}
