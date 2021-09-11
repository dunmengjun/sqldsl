package com.dmj.sqldsl.driver;

import com.dmj.sqldsl.model.DslQuery;
import java.util.List;

public interface Driver {

  <T> List<T> execute(DslQuery query, Class<T> targetClass);
}
