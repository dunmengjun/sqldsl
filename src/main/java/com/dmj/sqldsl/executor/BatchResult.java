package com.dmj.sqldsl.executor;

import static lombok.AccessLevel.PRIVATE;

import com.dmj.sqldsl.executor.exception.ExecutionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@Getter
@EqualsAndHashCode
public class BatchResult {

  private Map<Integer, Integer> resultMap;
  private int successful;
  private int failed;
  private int affectedRows;

  public static BatchResult empty() {
    return new BatchResult();
  }

  public static BatchResult create(List<Integer> indexList, int[] resultArray) {
    if (indexList.size() != resultArray.length) {
      throw new ExecutionException("batch size doesn't match the result size!");
    }
    Map<Integer, Integer> resultMap = new HashMap<>(indexList.size());
    int successful = 0;
    int failed = 0;
    int affectedRows = 0;
    for (int i = 0; i < indexList.size(); i++) {
      Integer key = indexList.get(i);
      int value = resultArray[i];
      affectedRows += value;
      resultMap.put(key, value);
      if (value > 0) {
        successful++;
      } else {
        failed++;
      }
    }
    return new BatchResult(resultMap, successful, failed, affectedRows);
  }

  public BatchResult add(BatchResult result) {
    this.resultMap.putAll(result.resultMap);
    this.affectedRows += result.affectedRows;
    this.successful += result.successful;
    this.failed += result.failed;
    return this;
  }
}
