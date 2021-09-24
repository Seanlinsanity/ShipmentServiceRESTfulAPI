package com.seanlindev.springframework.shippingserviceapi.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String userId;
    private String email;
    private String name;
    private String encryptedPassword;
}
