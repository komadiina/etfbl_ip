package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.model.db.ElectricBicycle;
import org.unibl.etf.rest_api.service.crud.ElectricBicycleService;

@RestController
@RequestMapping("/api/electric-bicycles")
public class ElectricBicycleController {
    @Autowired
    private ElectricBicycleService electricBicycleService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(electricBicycleService.retrieveAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getElectricBicycle(@PathVariable(name = "id") int id) {
        try {
            ElectricBicycle electricBicycle = electricBicycleService.retrieve(id);
            if (electricBicycle != null)
                return ResponseEntity.ok(electricBicycle);
            else
                return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ElectricBicycle electricBicycle) {
        try {
            electricBicycle.setDeviceType("ElectricBicycle");
            return ResponseEntity.ok(electricBicycleService.create(electricBicycle));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        try {
            return ResponseEntity.ok(electricBicycleService.delete(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
