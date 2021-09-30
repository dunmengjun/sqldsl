package com.dmj.sqldsl.builder.column.type;

import static com.dmj.sqldsl.utils.ReflectionUtils.forName;
import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.LambdaColumnBuilder;
import com.dmj.sqldsl.builder.exception.UnknownException;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FunctionalInterface
public interface ColumnLambda<T, R> extends Serializable {

  R apply(T t);

  default ColumnBuilder<T, R> getColumnBuilder() {
    return new LambdaColumnBuilder<>(getLambdaType());
  }

  Pattern regex = Pattern.compile("\\(L(.*?);\\)");

  default LambdaType getLambdaType() {
    SerializedLambda lambda = invokeMethod("writeReplace", this);
    String instantiatedMethodType = lambda.getInstantiatedMethodType();
    Matcher matcher = regex.matcher(instantiatedMethodType);
    if (matcher.find()) {
      String group = matcher.group(1);
      group = group.replace("/", ".");
      return new LambdaType(forName(group), lambda.getImplMethodName());
    }
    throw new UnknownException();
  }
}
