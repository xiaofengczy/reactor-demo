package com.study.webflux.controller;

import com.study.webflux.dao.User;
import com.study.webflux.dao.UserRespository;
import com.study.webflux.util.Result;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * FileName: MainTest2 Description:
 *
 * @author caozhongyu
 * @create 2019-12-24
 */
@RestController
@RequestMapping("/user")
public class MainTest2 {

  @Resource
  private UserRespository userRespository;

  @GetMapping("/")
  public Flux<User> findAll() {
    return userRespository.findAll();
  }

  @PostMapping("/")
  public Mono<User> saveUser(@Valid @RequestBody User user) {
    //新增和更新均为save方法，如果没id则新增，有id则修改
    user.setId(null);
    return userRespository.save(user);
  }

  @DeleteMapping("/{id}")
  public Mono<Result> deleteUser(@PathVariable("id") String id) {
    //先查询是否存在，在删除
    return userRespository.findById(id)
        .flatMap(u -> userRespository.delete(u).then(Mono.just(toResult("删除成功", "200", null))))
        .defaultIfEmpty(toResult("用户不存在", "10004", null));
  }

  @PutMapping("/{id}")
  public Mono<Result> updateUser(@PathVariable("id") String id, @RequestBody User user) {
    //先查询是否存在，在删除
    return userRespository.findById(id)
        .flatMap(u -> {
          u.setAge(user.getAge());
          u.setName(user.getName());
          return userRespository.save(u);
        })
        .map(u -> toResult("更新成功", "200", u))
        .defaultIfEmpty(toResult("用户不存在", "10004", null));
  }

  @GetMapping("/{id}")
  public Mono<Result> findById(@PathVariable("id") String id) {
    //先查询是否存在，在删除
    return userRespository.findById(id)
        .map(u -> toResult("success", "200", u))
        .defaultIfEmpty(toResult("用户不存在", "10004", null));
  }

  private Result toResult(String msg, String code, Object data) {
    Result result = new Result();
    result.setCode(code);
    result.setData(data);
    result.setMsg(msg);
    return result;
  }
}