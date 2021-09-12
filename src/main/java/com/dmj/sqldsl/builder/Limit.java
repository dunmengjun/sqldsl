package com.dmj.sqldsl.builder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Limit {

  private int offset;
  private int size;
}
