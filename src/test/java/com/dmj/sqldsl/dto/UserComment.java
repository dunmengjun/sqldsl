package com.dmj.sqldsl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserComment {

  private Integer id;

  private String name;

  private String message;
}
