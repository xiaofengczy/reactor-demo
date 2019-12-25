package com.study.webflux.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * FileName: UserRespository Description:
 *
 * @author caozhongyu
 * @create 2019-12-24
 */
public interface UserRespository extends ReactiveMongoRepository<User,String> {

}