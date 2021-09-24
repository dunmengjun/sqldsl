package com.dmj.sqldsl.builder.column.type;

import static com.dmj.sqldsl.utils.ReflectionUtils.forName;
import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.LambdaColumnBuilder;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface TypedLambda<T, R> extends Serializable, ColumnBuilderFactory<T, R> {

  Pattern regex = Pattern.compile("\\(L(.*?);\\)");

  default ColumnBuilder<T, R> getColumnBuilder() {
    return new LambdaColumnBuilder<>(getLambdaType());
  }

  default LambdaType getLambdaType() {
    SerializedLambda lambda = invokeMethod("writeReplace", this);
    String instantiatedMethodType = lambda.getInstantiatedMethodType();
    Matcher matcher = regex.matcher(instantiatedMethodType);
    if (matcher.find()) {
      String group = matcher.group(1);
      group = group.replace("/", ".");
      return new LambdaType(forName(group), lambda.getImplMethodName());
    }
    throw new RuntimeException("unknown error");
  }
}
