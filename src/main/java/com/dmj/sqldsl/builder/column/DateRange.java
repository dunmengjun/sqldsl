package com.dmj.sqldsl.builder.column;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DateRange {

  private Date start;
  private Date end;
}
