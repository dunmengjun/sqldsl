package com.dmj.sqldsl.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {

  @Column
  private int id;

  @Column
  private String name;

  @Column
  private Date createTime;
}
