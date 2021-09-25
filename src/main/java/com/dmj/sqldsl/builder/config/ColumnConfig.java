package com.dmj.sqldsl.builder.config;

import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;
import static com.dmj.sqldsl.utils.ReflectionUtils.newInstance;

import com.dmj.sqldsl.utils.StringUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Builder
public class ColumnConfig {

  @Getter
  private Class<? extends Annotation> columnAnnotationClass;
  private String columnNameAttribute;
  private Class<? extends Annotation> idAnnotationClass;
  @Builder.Default
  private NameTranslator globalFieldNameTranslator = new NotingTranslator();

  public Optional<String> getColumnName(Field field) {
    if (!isColumnField(field) && !isIdField(field)) {
      return Optional.empty();
    }
    if (isIdField(field)) {
      return Optional.of(translatorFieldName(field));
    }
    Annotation annotation = field.getAnnotation(columnAnnotationClass);
    String columnName = invokeMethod(columnNameAttribute, annotation);
    if (StringUtils.isBlank(columnName)) {
      columnName = translatorFieldName(field);
    }
    return Optional.of(columnName);
  }

  private String translatorFieldName(Field field) {
    Translator translator = field.getAnnotation(Translator.class);
    if (translator != null) {
      return newInstance(translator.value()).translate(field.getName());
    } else {
      return globalFieldNameTranslator.translate(field.getName());
    }
  }

  public boolean isIdField(Field field) {
    return field.isAnnotationPresent(idAnnotationClass);
  }

  public boolean isColumnField(Field field) {
    return field.isAnnotationPresent(columnAnnotationClass);
  }
}
