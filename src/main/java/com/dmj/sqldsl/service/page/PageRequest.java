package com.dmj.sqldsl.service.page;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class PageRequest {

  private int number;
  private int size;

  public static PageRequest of(int number, int size) {
    return new PageRequest(Math.max(1, number), Math.max(0, size));
  }
}
