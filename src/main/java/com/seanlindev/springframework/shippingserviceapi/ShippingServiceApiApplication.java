package com.seanlindev.springframework.shippingserviceapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.seanlindev.springframework.shippingserviceapi.repositories")
public class ShippingServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShippingServiceApiApplication.class, args);
    }

}
