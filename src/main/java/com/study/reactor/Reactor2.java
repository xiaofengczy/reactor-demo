package com.study.reactor;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * FileName: Process Description:
 *
 * @author caozhongyu
 * @create 2019-12-23
 */
public class Reactor2 {

  public static void main(String[] args) throws InterruptedException {
    //创建发布者
    SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

    //创建订阅者
    Subscriber<Integer> subscriber = new Subscriber<>() {

      private Subscription subscription;

      @Override
      public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
      }

      @Override
      public void onNext(Integer item) {
        System.out.println("消费者消费消息===>" + item);
        //控制消费速率
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        subscription.request(1);
      }

      @Override
      public void onError(Throwable throwable) {
        throwable.printStackTrace();
        subscription.cancel();
      }

      @Override
      public void onComplete() {
        System.out.println("完成消息接收");
      }
    };

    //绑定关系
    publisher.subscribe(subscriber);

    //生产数据
    for (int i = 0; i < 10000; i++) {
      System.out.println("生产者生产数据:"+i);
      publisher.submit(i);
    }

    publisher.close();

    Thread.currentThread().join(1000);
  }

}