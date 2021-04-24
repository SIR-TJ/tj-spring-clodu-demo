package com.tj.spring.cloud.web.admin.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AdminService {

    @Autowired
    private RestTemplate template;

    //返回熔断方法
    @HystrixCommand(fallbackMethod = "helloError")
    public String sayHi(String message){
        return template.getForObject("http://tj-spring-cloud-service-admin/hi?message="+message,String.class);
    }

    public String helloError(String message){
        return String.format("hi your message %sis  but request bad ",message);
    }
}
