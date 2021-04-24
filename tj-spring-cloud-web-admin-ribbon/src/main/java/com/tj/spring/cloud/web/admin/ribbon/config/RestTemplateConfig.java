package com.tj.spring.cloud.web.admin.ribbon.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {


    @LoadBalanced //加入到负载均衡
    @Bean //注入到spring
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
