package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.model.db.Manufacturer;
import org.unibl.etf.rest_api.model.db.TransportationDevice;
import org.unibl.etf.rest_api.service.crud.ElectricBicycleService;
import org.unibl.etf.rest_api.service.crud.ElectricScooterService;
import org.unibl.etf.rest_api.service.crud.TransportationDeviceService;
import org.unibl.etf.rest_api.service.crud.VehicleService;


@RestController
@RequestMapping("/api/transportation-devices")
public class TransportationDeviceController {
    @Autowired
    private TransportationDeviceService transportationDeviceService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ElectricBicycleService electricBicycleService;

    @Autowired
    private ElectricScooterService electricScooterService;

    private ResponseEntity<?> getAll() {
        return ResponseEntity.ok(transportationDeviceService.retrieveAll());
    }

    // pageable
    @GetMapping
    public ResponseEntity<?> getAllPaginated(
            @RequestParam(defaultValue = "-1", required = false) int page,
            @RequestParam(defaultValue = "-1", required = false) int limit) {
        try {
            if (page == -1 || limit == -1) {
                return getAll();
            }

            Pageable pageable = PageRequest.of(page, limit);
            Page<TransportationDevice> pageResult = transportationDeviceService.retrieveAllPaginated(pageable);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransportationDevice(@PathVariable(name = "id") int id) {
        try {
            TransportationDevice transportationDevice = transportationDeviceService.retrieve(id);
            if (transportationDevice != null)
                return ResponseEntity.ok(transportationDevice);
            else
                return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/available/all")
    public ResponseEntity<?> getAllAvailable() {
        try {
            return ResponseEntity.ok(transportationDeviceService.retrieveAllAvailableWithPositionInfo());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
