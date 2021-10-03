package com.dmj.sqldsl.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table
public class DemoEntity {

  @Id
  private int id;

  @Column(name = "name")
  private String name;
}
