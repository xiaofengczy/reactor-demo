package com.study.webflux.controller;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * FileName: MainTest1
 *
 * Description: 用于测试webflux及web差异
 *
 * @author caozhongyu
 * @create 2019-12-23
 */
@RestController
public class MainTest1 {

  private Logger logger = LoggerFactory.getLogger(MainTest1.class);

  @GetMapping("/test/1")
  public String test1() {
    logger.info("start time:" + System.currentTimeMillis());
    String str = doHello();
    logger.info("end time:" + System.currentTimeMillis());
    return str + " web";
  }

  @GetMapping("/test/2")
  public Mono<String> test2() {
    logger.info("start time:" + System.currentTimeMillis());
    Mono<String> str = Mono.fromSupplier(() -> doHello());
    logger.info("end time:" + System.currentTimeMillis());
    return str.map(s -> s + " webflux");
  }


  @GetMapping("/test/3")
  public Flux<String> test3() {
    return Flux.from(Flux.range(1, 5))
        .map(i -> {
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return "flux data:" + i;
        });
  }


  private String doHello() {
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "hello";
  }
}