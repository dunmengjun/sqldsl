package com.dmj.sqldsl.entity;

import javax.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class TypeUser extends User {

  public TypeUser(Integer id, String name, Integer age, Integer type) {
    super(id, name, age);
    this.type = type;
  }

  public TypeUser(String name, Integer age, Integer type) {
    super(name, age);
    this.type = type;
  }

  @Column
  private Integer type;
}
