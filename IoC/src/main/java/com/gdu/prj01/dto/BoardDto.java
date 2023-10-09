package com.gdu.prj01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDto {
  private int boardNo;
  private String title;
  private UserDto userDto;
}
