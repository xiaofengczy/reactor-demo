package com.study.webflux.exception;

import com.study.webflux.util.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * FileName: ValidateController Description:
 *
 * @author caozhongyu
 * @create 2019-12-24
 */
@ControllerAdvice
public class ValidateController {

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<Result> validateParam(WebExchangeBindException e){
    return new ResponseEntity<>(toError(e), HttpStatus.BAD_REQUEST);
  }

  private Result toError(WebExchangeBindException ex){
    Result result = new Result();
    result.setCode("400");
    result.setMsg("检验失败");
    List<Map<String, String>> dataList = ex.getFieldErrors().stream()
        .map(e -> {
          Map<String, String> dataMap = new HashMap<>();
          dataMap.put(e.getField(), e.getDefaultMessage());
          return dataMap;
        }).collect(Collectors.toList());
    result.setData(dataList);
    return result;
  }

  @ExceptionHandler(ExtException.class)
  public ResponseEntity<Result> validateParam(ExtException e){
    return new ResponseEntity<>(toError(e), HttpStatus.BAD_REQUEST);
  }

  private Result toError(ExtException e) {
    Result result = new Result();
    result.setCode(e.getCode());
    result.setMsg(e.getMsg());
    result.setData(null);
    return result;
  }


}