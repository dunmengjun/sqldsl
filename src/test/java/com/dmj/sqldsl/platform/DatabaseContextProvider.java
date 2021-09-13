package com.dmj.sqldsl.platform;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

public class DatabaseContextProvider implements TestTemplateInvocationContextProvider {

  private static final String METHOD_CONTEXT_KEY = "context";

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    Method method = context.getRequiredTestMethod();
    Database annotation = method.getAnnotation(Database.class);
    if (annotation == null) {
      Class<?> testClass = context.getRequiredTestClass();
      annotation = testClass.getAnnotation(Database.class);
    }
    getStore(context).put(METHOD_CONTEXT_KEY, annotation.value());
    return true;
  }

  private ExtensionContext.Store getStore(ExtensionContext context) {
    return context.getStore(
        Namespace.create(DatabaseContextProvider.class, context.getRequiredTestMethod()));
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
      ExtensionContext context) {
    SQLDialect[] sqlDialects = getStore(context).get(METHOD_CONTEXT_KEY, SQLDialect[].class);
    return Arrays.stream(sqlDialects).map(this::sqlDialectContext);
  }

  private TestTemplateInvocationContext sqlDialectContext(SQLDialect dialect) {
    return new TestTemplateInvocationContext() {
      @Override
      public String getDisplayName(int invocationIndex) {
        return dialect.toString();
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return Collections.singletonList(
            (BeforeEachCallback) context1 -> DatabaseManager.url =
                DatabaseManager.baseUrl + dialect.getDialect()
        );
      }
    };
  }
}
