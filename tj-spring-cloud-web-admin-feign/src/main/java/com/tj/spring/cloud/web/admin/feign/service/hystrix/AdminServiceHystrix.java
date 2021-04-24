package com.tj.spring.cloud.web.admin.feign.service.hystrix;

import com.tj.spring.cloud.web.admin.feign.service.AdminService;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceHystrix implements AdminService {
    @Override
    public String sayHi(String message) {
        return String.format("hi your message %s is  but request bad ",message);
    }
}
