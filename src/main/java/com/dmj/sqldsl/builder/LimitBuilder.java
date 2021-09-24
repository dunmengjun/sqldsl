package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.model.Limit;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LimitBuilder {

  private final int offset;
  private final int size;

  public Limit build() {
    return new Limit(offset, size);
  }

}
