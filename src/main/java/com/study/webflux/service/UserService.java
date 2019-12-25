package com.study.webflux.service;

import com.study.webflux.enttiy.UserRes;
import reactor.core.publisher.Mono;

/**
 * FileName: UserService Description:
 *
 * @author caozhongyu
 * @create 2019/12/25
 */
public interface UserService {

  Mono<UserRes> findUserById(String id);

}