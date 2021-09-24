package com.seanlindev.springframework.shippingserviceapi.dtos;

import com.seanlindev.springframework.shippingserviceapi.model.Shipment;
import com.seanlindev.springframework.shippingserviceapi.model.ShipmentStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ShipmentDto implements Serializable {
    String shipmentId;
    String orderId;
    Date created;
    String recipient;
    String address;
    ShipmentStatus status;

    public Shipment convertToShipment() {
        Shipment shipment = new Shipment();
        shipment.setOrderId(getOrderId());
        shipment.setAddress(getAddress());
        shipment.setRecipient(getRecipient());
        return shipment;
    }
}
