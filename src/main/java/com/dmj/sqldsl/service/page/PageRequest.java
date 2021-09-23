package com.dmj.sqldsl.service.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PageRequest {

  private int number;
  private int size;

  public static PageRequest of(int number, int size) {
    return new PageRequest(number, size);
  }
}
