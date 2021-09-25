package com.dmj.sqldsl.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table
public class Satisfaction {

  @Id
  private int id;

  @Column
  private int userId;

  @Column
  private int commentId;

  @Column
  private int rating;
}
