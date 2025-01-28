package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.model.db.TransportationDevice;
import org.unibl.etf.rest_api.model.db.Vehicle;
import org.unibl.etf.rest_api.model.dto.VehicleDto;
import org.unibl.etf.rest_api.service.crud.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(vehicleService.retrieveAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicle(@PathVariable(name = "id") int id) {
        try {
            Vehicle vehicle = vehicleService.retrieve(id);
            if (vehicle != null)
                return ResponseEntity.ok(vehicle);
            else
                return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody Vehicle vehicle) {
        try {
            vehicle.setDeviceType("Vehicle");
            return ResponseEntity.ok(vehicleService.add(vehicle));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable(name = "id") int id) {
        try {
            return ResponseEntity.ok(vehicleService.delete(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
