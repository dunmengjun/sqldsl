package com.dmj.sqldsl.service.page;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Page<T> {

  private PageRequest request;
  @Getter
  private long total;
  @Getter
  private List<T> data;

  public int getPageNumber() {
    return request.getPageNumber();
  }

  public int getSize() {
    return request.getSize();
  }
}
