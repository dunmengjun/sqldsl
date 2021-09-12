package com.dmj.sqldsl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRating {

  private Integer id;
  private String name;
  private String message;
  private Integer rating;
}
