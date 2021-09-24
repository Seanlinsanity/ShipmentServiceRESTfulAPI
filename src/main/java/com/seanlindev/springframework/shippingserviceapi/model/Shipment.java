package com.seanlindev.springframework.shippingserviceapi.model;

import com.seanlindev.springframework.shippingserviceapi.dtos.ShipmentDto;
import lombok.Data;

import java.util.Date;

@Data
public class Shipment {
    Long id;
    String shipmentId;
    String orderId;
    Date created;
    String recipient;
    String address;
    ShipmentStatus status;

    public ShipmentDto convertToShipmentDto() {
        ShipmentDto shipmentDto = new ShipmentDto();
        shipmentDto.setShipmentId(getShipmentId());
        shipmentDto.setCreated(getCreated());
        shipmentDto.setStatus(getStatus());
        shipmentDto.setAddress(getAddress());
        shipmentDto.setOrderId(getOrderId());
        shipmentDto.setRecipient(getRecipient());
        return shipmentDto;
    }
}
