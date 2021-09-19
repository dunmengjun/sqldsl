package com.dmj.sqldsl.builder.column.type;

import static com.dmj.sqldsl.utils.ReflectionUtils.forName;
import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.LambdaColumnBuilder;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface SerializableLambda extends Serializable, ColumnBuilderFactory {

  Pattern regex = Pattern.compile("\\(L(.*?);\\)");

  default ColumnBuilder getColumnBuilder() {
    return new LambdaColumnBuilder(this);
  }

  default String getMethodName() {
    SerializedLambda lambda = invokeMethod("writeReplace", this);
    return lambda.getImplMethodName();
  }

  default Class<?> getTargetClass() {
    SerializedLambda lambda = invokeMethod("writeReplace", this);
    String instantiatedMethodType = lambda.getInstantiatedMethodType();

    Matcher matcher = regex.matcher(instantiatedMethodType);
    if (matcher.find()) {
      String group = matcher.group(1);
      group = group.replace("/", ".");
      return forName(group);
    }
    throw new RuntimeException("unknown error");
  }
}
