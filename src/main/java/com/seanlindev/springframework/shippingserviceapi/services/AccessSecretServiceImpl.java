package com.seanlindev.springframework.shippingserviceapi.services;

import com.seanlindev.springframework.shippingserviceapi.dtos.AccessSecretDto;
import com.seanlindev.springframework.shippingserviceapi.model.AccessSecret;
import com.seanlindev.springframework.shippingserviceapi.model.User;
import com.seanlindev.springframework.shippingserviceapi.repositories.AccessSecretRepository;
import com.seanlindev.springframework.shippingserviceapi.repositories.UserRepository;
import com.seanlindev.springframework.shippingserviceapi.utils.PublicIdGenerator;
import com.seanlindev.springframework.shippingserviceapi.utils.SecretKeyBuilder;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

@Service
@CacheConfig(cacheNames={"access-secrets"}, cacheManager = "cacheManager")
public class AccessSecretServiceImpl implements AccessSecretService {
    private UserRepository userRepository;
    private AccessSecretRepository accessSecretRepository;

    public AccessSecretServiceImpl(UserRepository userRepository, AccessSecretRepository accessSecretRepository) {
        this.userRepository = userRepository;
        this.accessSecretRepository = accessSecretRepository;
    }

    @Override
    @CachePut( key="#accessSecretDto.keyId")
    public AccessSecretDto createAccessSecret(AccessSecretDto accessSecretDto) {
        User user = userRepository.findByUserId(accessSecretDto.getUserId());
        if (user == null) {
            throw new RuntimeException("User Not Found, id: " + accessSecretDto.getUserId());
        }
        AccessSecret accessSecret = new AccessSecret();
        accessSecret.setKeyId(PublicIdGenerator.generatePublicId(10));
        accessSecret.setKeySecret(SecretKeyBuilder.randomSecretKey());
        accessSecret.setUserId(user.getId());
        accessSecretRepository.save(accessSecret);
        accessSecretDto.setKeyId(accessSecret.getKeyId());
        accessSecretDto.setKeySecret(accessSecret.getKeySecret());
        return accessSecretDto;
    }

    @Override
    @Cacheable( key="#keyId")
    public AccessSecretDto getKeySecretByKeyId(String keyId) {
        AccessSecret accessSecret = accessSecretRepository.findByKeyId(keyId);
        User user = userRepository.findById(accessSecret.getUserId());
        if (user == null) {
            return null;
        }
        AccessSecretDto accessSecretDto = accessSecret.convertToAccessSecretDto();
        accessSecretDto.setUserId(user.getUserId());
        return accessSecretDto;
    }
}
