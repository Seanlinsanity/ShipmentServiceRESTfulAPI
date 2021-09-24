package com.seanlindev.springframework.shippingserviceapi.controllers;

import com.seanlindev.springframework.shippingserviceapi.dtos.UserDto;
import com.seanlindev.springframework.shippingserviceapi.model.User;
import com.seanlindev.springframework.shippingserviceapi.repositories.UserRepository;
import com.seanlindev.springframework.shippingserviceapi.security.JWTHelper;
import com.seanlindev.springframework.shippingserviceapi.security.SecurityConstants;
import com.seanlindev.springframework.shippingserviceapi.services.UserService;
import com.seanlindev.springframework.shippingserviceapi.utils.PublicIdGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public UserDto createNewUser(@RequestBody UserDto user) {
        userService.createUser(user);
        return user;
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Response Headers",
                    responseHeaders = {
                            @ResponseHeader(name = "authorization",
                                    description = "Bearer <JWT value here>",
                                    response = String.class),
                            @ResponseHeader(name = "userId",
                                    description = "<Public User Id value here>",
                                    response = String.class)
                    })
    })
    public ResponseEntity<String> loginUser(@RequestBody UserDto user) {
        UserDto userDto = userService.loginUser(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        String token = JWTHelper.generateToke(userDto.getEmail());
        responseHeaders.set(SecurityConstants.HEADER_STRING,
                            SecurityConstants.TOKEN_PREFIX + token);
        responseHeaders.set("userId", userDto.getUserId());
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("SUCCESS");
    }

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable String id) {
        return userService.findByUserId(id);
    }
}
