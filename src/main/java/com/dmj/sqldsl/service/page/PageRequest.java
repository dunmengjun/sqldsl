package com.dmj.sqldsl.service.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PageRequest {

  private int pageNumber;
  private int size;

  public static PageRequest of(int pageNumber, int size) {
    return new PageRequest(pageNumber, size);
  }
}
