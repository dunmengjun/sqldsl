package com.dmj.sqldsl.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "comment")
public class Comment {

  @Column
  private int id;

  @Column
  private int userId;

  @Column
  private String message;

  @Column
  private int status;
}
