package com.study.webflux.dao;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * FileName: User Description:
 *
 * @author caozhongyu
 * @create 2019-12-24
 */
@Data
@Document
public class User {

  @Id
  private String id;

  @NotBlank
  private String name;

  @Range(min = 1,max = 100)
  private int age;
}