package com.seanlindev.springframework.shippingserviceapi.repositories;

import com.seanlindev.springframework.shippingserviceapi.model.User;

public interface UserRepository {
    public User findById(Integer id);
    public User findByUserId(String id);
    public User findByEmail(String email);
    public void save(User user);
}
