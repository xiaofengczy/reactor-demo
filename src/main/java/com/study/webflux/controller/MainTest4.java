package com.study.webflux.controller;

import com.study.webflux.exception.WebException;
import com.study.webflux.service.UserService;
import com.study.webflux.util.Result;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/user/webclient")
public class MainTest4 {

  @Resource
  private UserService userService;

  @GetMapping("/{id}")
  public Mono<Result> findUserById(@PathVariable String id) {
    return userService.findUserById(id)
        .map(u->toSuccess(u))
        .onErrorResume(e->Mono.just(toError(e)));
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

}