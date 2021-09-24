package com.seanlindev.springframework.shippingserviceapi.repositories;

import com.seanlindev.springframework.shippingserviceapi.model.Shipment;
import com.seanlindev.springframework.shippingserviceapi.model.ShipmentStatus;

public interface ShipmentRepository {
    public void save(Shipment shipment);
    public Shipment findByShipmentId(String id);
    public Shipment findByShipmentIdForUpdate(String id);
    public Shipment findByOrderId(String id);
    public void updateStatusByShipmentId(String id, ShipmentStatus status);
}
