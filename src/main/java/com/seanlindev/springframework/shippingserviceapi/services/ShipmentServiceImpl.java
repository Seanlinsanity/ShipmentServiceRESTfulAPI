package com.seanlindev.springframework.shippingserviceapi.services;

import com.seanlindev.springframework.shippingserviceapi.dtos.ShipmentDto;
import com.seanlindev.springframework.shippingserviceapi.model.Shipment;
import com.seanlindev.springframework.shippingserviceapi.model.ShipmentStatus;
import com.seanlindev.springframework.shippingserviceapi.repositories.ShipmentRepository;
import com.seanlindev.springframework.shippingserviceapi.utils.PublicIdGenerator;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@CacheConfig(cacheNames={"shipments"}, cacheManager = "cacheManager")
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    @CachePut( key="#shipmentDto.shipmentId")
    public ShipmentDto createShipment(ShipmentDto shipmentDto) {
        Shipment shipment = shipmentDto.convertToShipment();
        shipment.setShipmentId(PublicIdGenerator.generatePublicId(30));
        shipment.setCreated(new Date());
        shipment.setStatus(ShipmentStatus.WAITING);
        shipmentRepository.save(shipment);

        shipmentDto.setShipmentId(shipment.getShipmentId());
        shipmentDto.setCreated(shipment.getCreated());
        shipmentDto.setStatus(shipment.getStatus());
        return shipmentDto;
    }

    @Override
    @Cacheable( key="#id")
    public ShipmentDto getDetailsByShipmentId(String id) {
        Shipment shipment = shipmentRepository.findByShipmentId(id);
        return shipment.convertToShipmentDto();
    }

    @Override
    @CachePut( key="#shipmentDto.shipmentId")
    public ShipmentDto updateStatus(ShipmentDto shipmentDto) {
        Shipment shipment = shipmentRepository.findByShipmentIdForUpdate(shipmentDto.getShipmentId());
        if (shipment.getStatus() == shipmentDto.getStatus()) {
            throw new RuntimeException("Invalid shipment status change");
        }
        if (shipment.getStatus() == ShipmentStatus.COMPLETED && shipmentDto.getStatus() == ShipmentStatus.CANCELLED) {
            throw new RuntimeException("Cannot cancel a completed shipment");
        }
        shipmentRepository.updateStatusByShipmentId(shipmentDto.getShipmentId(), shipmentDto.getStatus());
        shipmentDto.setCreated(shipment.getCreated());
        shipmentDto.setAddress(shipment.getAddress());
        shipmentDto.setOrderId(shipment.getOrderId());
        shipmentDto.setRecipient(shipment.getRecipient());
        return shipmentDto;
    }
}
