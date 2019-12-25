package com.study.webflux.exception;

import lombok.Data;

/**
 * FileName: WebException Description:
 *
 * @author caozhongyu
 * @create 2019/12/25
 */
@Data
public class WebException extends RuntimeException{
  private String code;

  private String msg;

  public WebException(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public WebException(String message, String code, String msg) {
    super(message);
    this.code = code;
    this.msg = msg;
  }

  public WebException(String message, Throwable cause, String code, String msg) {
    super(message, cause);
    this.code = code;
    this.msg = msg;
  }

  public WebException(Throwable cause, String code, String msg) {
    super(cause);
    this.code = code;
    this.msg = msg;
  }

  public WebException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace, String code, String msg) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.code = code;
    this.msg = msg;
  }
}