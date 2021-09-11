package com.dmj.sqldsl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private Integer age;
}
