package com.dmj.sqldsl.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dept")
public class Dept {

  @Column
  private int id;

  @Column
  private String name;

  @Column
  private Integer parent;
}
