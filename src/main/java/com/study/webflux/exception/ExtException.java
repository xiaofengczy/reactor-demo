package com.study.webflux.exception;

import lombok.Data;

/**
 * FileName: ExtException Description:
 *
 * @author caozhongyu
 * @create 2019-12-24
 */
@Data
public class ExtException extends RuntimeException {

  private String code;

  private String msg;

  public ExtException(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public ExtException(String message, String code, String msg) {
    super(message);
    this.code = code;
    this.msg = msg;
  }

  public ExtException(String message, Throwable cause, String code, String msg) {
    super(message, cause);
    this.code = code;
    this.msg = msg;
  }

  public ExtException(Throwable cause, String code, String msg) {
    super(cause);
    this.code = code;
    this.msg = msg;
  }

  public ExtException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace, String code, String msg) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.code = code;
    this.msg = msg;
  }
}