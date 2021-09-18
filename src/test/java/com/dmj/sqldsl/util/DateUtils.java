package com.dmj.sqldsl.util;

import com.dmj.sqldsl.builder.column.DateRange;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

  private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public static DateRange parseRange(String range) {
    String[] split = range.split(",");
    Date start = parseDate(split[0].trim());
    Date end = parseDate(split[1].trim());
    return new DateRange(start, end);
  }

  public static Date parseDate(String date) {
    try {
      return new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(date);
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
