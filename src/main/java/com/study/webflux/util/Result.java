package com.study.webflux.util;

import lombok.Data;

/**
 * FileName: Result Description:
 *
 * @author caozhongyu
 * @create 2019/12/25
 */
@Data
public class Result {

  private boolean ifSuccess;

  private String code;

  private String msg;

  private Object data;

}