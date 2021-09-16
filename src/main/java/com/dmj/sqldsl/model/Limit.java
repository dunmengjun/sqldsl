package com.dmj.sqldsl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Limit {

  private int offset;
  private int size;
}
