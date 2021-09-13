package com.dmj.sqldsl.platform;

import com.dmj.sqldsl.driver.SqlDialect;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

@AllArgsConstructor
public class SqlDialectParameterResolver implements ParameterResolver {

  private SqlDialect dialect;

  @Override
  public boolean supportsParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterContext.getParameter().getType() == SqlDialect.class;
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {
    return dialect;
  }
}
