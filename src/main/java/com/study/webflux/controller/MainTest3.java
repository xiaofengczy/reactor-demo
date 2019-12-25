package com.study.webflux.controller;

import com.study.webflux.dao.User;
import com.study.webflux.dao.UserRespository;
import com.study.webflux.exception.ExtException;
import com.study.webflux.exception.WebException;
import com.study.webflux.util.Result;
import java.util.Objects;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * FileName: MainTest3 Description:
 *
 * @author caozhongyu
 * @create 2019-12-24
 */
@RestController
@RequestMapping("/user/validate")
public class MainTest3 {

  @Resource

  private UserRespository userRespository;

  @PostMapping("/")
  public Mono<User> saveUserValidate(@Valid @RequestBody User user) {
    validateParam(user);
    //新增和更新均为save方法，如果没id则新增，有id则修改
    user.setId(null);
    return userRespository.save(user);
  }

  @PostMapping("/ext")
  public Mono<User> saveUserExt(@RequestBody User user) {
    validateParam(user);
    //新增和更新均为save方法，如果没id则新增，有id则修改
    user.setId(null);
    return userRespository.save(user);
  }

  @PostMapping("/onError")
  public Mono<Result> saveUserOnError(@RequestBody User user) {
    return validateParamOnError(user)
        .flatMap(u -> {
          u.setId(null);
          return userRespository.save(user);
        })
        .map(u -> toSuccess(u))
        .onErrorResume(e -> Mono.just(toError(e)));
  }

  private Result toSuccess(Object obj) {
    Result result = new Result();
    result.setData(obj);
    result.setMsg("success");
    result.setCode("200");
    return result;
  }

  private Result toError(Throwable e) {
    Result result = new Result();
    if (e instanceof WebException) {
      WebException ex = (WebException) e;
      result.setCode(ex.getCode());
      result.setMsg(ex.getMsg());
      result.setData(null);
      return result;
    }
    return result;
  }

  private Mono<User> validateParamOnError(User user) {
    String name = user.getName();
    if (!Objects.equals("admin", name)) {
      return Mono.just(user);
    }
    return Mono.error(new WebException("web error,user name invalidate", "10001"));
  }


  private void validateParam(User user) {
    String name = user.getName();
    if ("admin".equals(name)) {
      throw new ExtException("用户名不能为系统字段", "10001");
    }
  }

}