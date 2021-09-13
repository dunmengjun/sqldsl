package com.dmj.sqldsl.util;

import com.dmj.sqldsl.platform.DatabaseManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FileUtils {

  public static void readLines(URI file, Consumer<String> lineConsumer) {
    try {
      try (Stream<String> lines = Files.lines(Paths.get(file))) {
        lines.forEach(lineConsumer);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static URI getUri(String fileName) {
    URL resource = DatabaseManager.class.getClassLoader().getResource(fileName);
    assert resource != null;
    try {
      return resource.toURI();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
