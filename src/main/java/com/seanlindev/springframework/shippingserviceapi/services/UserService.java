package com.seanlindev.springframework.shippingserviceapi.services;

import com.seanlindev.springframework.shippingserviceapi.dtos.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto loginUser(UserDto user);
    UserDto findByUserId(String id);
}
