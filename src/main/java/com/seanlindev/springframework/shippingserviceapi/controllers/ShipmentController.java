package com.seanlindev.springframework.shippingserviceapi.controllers;

import com.seanlindev.springframework.shippingserviceapi.dtos.ShipmentDto;
import com.seanlindev.springframework.shippingserviceapi.security.SecurityConstants;
import com.seanlindev.springframework.shippingserviceapi.services.ShipmentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @ApiOperation(value = "Request creating a new shipment endpoint",
                  notes = "Create a new shipment with order identity details")
    @ApiImplicitParams({
            @ApiImplicitParam(name = SecurityConstants.API_ACCESS_KEY, value= "Access Key", paramType = "header"),
            @ApiImplicitParam(name = SecurityConstants.API_REQUEST_ID, value= "Random Request ID", paramType = "header"),
            @ApiImplicitParam(name = SecurityConstants.API_ACCESS_SIGNATURE, value= "Access Signature", paramType = "header")
    })
    @PostMapping
    public ShipmentDto createShipment(@RequestBody ShipmentDto shipmentDto) {
        return shipmentService.createShipment(shipmentDto);
    }

    @ApiOperation(value = "Get shipment details endpoint",
                  notes = "Specify shipment public id in URL path to get the details about a shipment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = SecurityConstants.API_ACCESS_KEY, value= "Access Key", paramType = "header"),
            @ApiImplicitParam(name = SecurityConstants.API_REQUEST_ID, value= "Random Request ID", paramType = "header"),
            @ApiImplicitParam(name = SecurityConstants.API_ACCESS_SIGNATURE, value= "Access Signature", paramType = "header")
    })
    @GetMapping("/{id}")
    public ShipmentDto getShipmentDetails(@PathVariable String id) {
        return shipmentService.getDetailsByShipmentId(id);
    }

    @ApiOperation(value = "Update shipment status endpoint",
                  notes = "Specify shipment public id in URL path to update the status of a shipment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = SecurityConstants.API_ACCESS_KEY, value= "Access Key", paramType = "header"),
            @ApiImplicitParam(name = SecurityConstants.API_REQUEST_ID, value= "Random Request ID", paramType = "header"),
            @ApiImplicitParam(name = SecurityConstants.API_ACCESS_SIGNATURE, value= "Access Signature", paramType = "header")
    })
    @PutMapping("/{id}/status")
    public ShipmentDto updateShipmentStatus(@PathVariable String id, @RequestBody ShipmentDto shipmentDto) {
        shipmentDto.setShipmentId(id);
        return shipmentService.updateStatus(shipmentDto);
    }
}
