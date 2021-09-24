package com.seanlindev.springframework.shippingserviceapi.services;

import com.seanlindev.springframework.shippingserviceapi.dtos.AccessSecretDto;

public interface AccessSecretService {
    AccessSecretDto createAccessSecret(AccessSecretDto accessSecretDto);
    AccessSecretDto getKeySecretByKeyId(String keyId);
}
