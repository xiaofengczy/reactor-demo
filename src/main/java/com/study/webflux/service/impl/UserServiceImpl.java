package com.study.webflux.service.impl;

import com.study.webflux.enttiy.UserRes;
import com.study.webflux.exception.WebException;
import com.study.webflux.service.UserService;
import com.study.webflux.util.Result;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * FileName: UserServiceImpl Description:
 *
 * @author caozhongyu
 * @create 2019/12/25
 */
@Service
public class UserServiceImpl implements UserService {

  @Resource
  private WebClient.Builder webClient;

  @Override
  public Mono<UserRes> findUserById(String id) {
    //校验请求参数
    Mono<Result> resultMono = webClient
        .build()
        .get()
        .uri("http://provider-service/provider/{id}", id)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<Result>() {
        });
    return resultMono.flatMap(result -> {
      if (!result.isIfSuccess()) {
        return Mono.error(new WebException(result.getCode(), result.getMsg()));
      }
      Map data = (Map) result.getData();
      UserRes userRes = new UserRes();
      userRes.setId((String) data.get("id"));
      userRes.setName((String) data.get("name"));
      userRes.setEmail((String) data.get("email"));
      return Mono.just(userRes);
    });
  }
}