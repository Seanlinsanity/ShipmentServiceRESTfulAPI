package com.seanlindev.springframework.shippingserviceapi.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessSecretDto implements Serializable {
    private String userId;
    private String keyId;
    private String keySecret;
}
