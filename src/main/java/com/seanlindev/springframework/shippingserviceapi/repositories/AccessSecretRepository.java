package com.seanlindev.springframework.shippingserviceapi.repositories;

import com.seanlindev.springframework.shippingserviceapi.model.AccessSecret;

public interface AccessSecretRepository {
    public void save(AccessSecret accessSecret);
    public AccessSecret findByKeyId(String id);
}
