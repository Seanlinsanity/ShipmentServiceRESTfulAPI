package com.seanlindev.springframework.shippingserviceapi.controllers;

import com.seanlindev.springframework.shippingserviceapi.dtos.AccessSecretDto;
import com.seanlindev.springframework.shippingserviceapi.services.AccessSecretService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class AccessSecretController {
    private AccessSecretService accessSecretService;

    public AccessSecretController(AccessSecretService accessSecretService) {
        this.accessSecretService = accessSecretService;
    }

    @ApiOperation(value = "Request access secret for API usage",
                  notes = "After login, you can generate a access key & secret pair to retrieve the API authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value= "Bearer JWT Token", paramType = "header")
    })
    @PostMapping("/access-secret")
    public AccessSecretDto createApiAccessSecret(@RequestBody AccessSecretDto accessSecretDto) {
        return accessSecretService.createAccessSecret(accessSecretDto);
    }
}
