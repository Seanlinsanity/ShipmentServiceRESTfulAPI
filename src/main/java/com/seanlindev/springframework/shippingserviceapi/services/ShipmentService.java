package com.seanlindev.springframework.shippingserviceapi.services;

import com.seanlindev.springframework.shippingserviceapi.dtos.ShipmentDto;

public interface ShipmentService {
    ShipmentDto createShipment(ShipmentDto shipmentDto);
    ShipmentDto getDetailsByShipmentId(String id);
    ShipmentDto updateStatus(ShipmentDto shipmentDto);
}
