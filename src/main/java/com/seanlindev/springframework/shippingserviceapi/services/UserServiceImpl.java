package com.seanlindev.springframework.shippingserviceapi.services;

import com.seanlindev.springframework.shippingserviceapi.dtos.UserDto;
import com.seanlindev.springframework.shippingserviceapi.model.User;
import com.seanlindev.springframework.shippingserviceapi.repositories.UserRepository;
import com.seanlindev.springframework.shippingserviceapi.utils.PublicIdGenerator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto user) {
        user.setUserId(PublicIdGenerator.generatePublicId(30));
        User userModel = new User();
        userModel.setUserId(user.getUserId());
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        userModel.setEncryptedPassword(hashedPassword);
        userRepository.save(userModel);
        return user;
    }

    @Override
    public UserDto loginUser(UserDto user) {
        User savedUser = userRepository.findByEmail(user.getEmail());
        Boolean success = BCrypt.checkpw(user.getPassword(), savedUser.getEncryptedPassword());
        if (!success) {
            throw new RuntimeException("invalid user credentials");
        }
        UserDto userDto = new UserDto();
        userDto.setUserId(savedUser.getUserId());
        userDto.setName(savedUser.getName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setName(savedUser.getName());
        return userDto;
    }

    @Override
    public UserDto findByUserId(String id) {
        User user = userRepository.findByUserId(id);
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        return userDto;
    }
}
