package com.dmj.sqldsl.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

  @Column
  private Integer id;

  @Column
  private String name;

  @Column
  private Integer age;
}
