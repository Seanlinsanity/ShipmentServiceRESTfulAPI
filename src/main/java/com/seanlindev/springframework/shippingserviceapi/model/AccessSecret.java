package com.seanlindev.springframework.shippingserviceapi.model;

import com.seanlindev.springframework.shippingserviceapi.dtos.AccessSecretDto;
import lombok.Data;

@Data
public class AccessSecret {
    private Integer id;
    private Integer userId;
    private String keyId;
    private String keySecret;

    public AccessSecretDto convertToAccessSecretDto() {
        AccessSecretDto accessSecretDto = new AccessSecretDto();
        accessSecretDto.setKeyId(getKeyId());
        accessSecretDto.setKeySecret(getKeySecret());
        return accessSecretDto;
    }
}
