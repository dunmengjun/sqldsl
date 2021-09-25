package com.dmj.sqldsl.builder.config;

import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;
import static com.dmj.sqldsl.utils.ReflectionUtils.newInstance;

import com.dmj.sqldsl.utils.StringUtils;
import java.lang.annotation.Annotation;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
public class TableConfig {

  @Getter
  private Class<? extends Annotation> annotationClass;
  private String tableNameAttribute;

  @Builder.Default
  @Setter
  private NameTranslator globalTableNameTranslator = new NotingTranslator();

  public Optional<String> getTableName(Class<?> targetClass) {
    if (!targetClass.isAnnotationPresent(annotationClass)) {
      return Optional.empty();
    }
    Annotation annotation = targetClass.getAnnotation(annotationClass);
    String tableName = invokeMethod(tableNameAttribute, annotation);
    if (StringUtils.isBlank(tableName)) {
      Translator translator = targetClass.getAnnotation(Translator.class);
      if (translator != null) {
        tableName = newInstance(translator.value()).translate(targetClass.getSimpleName());
      } else {
        tableName = globalTableNameTranslator.translate(targetClass.getSimpleName());
      }
    }
    return Optional.of(tableName);
  }
}
