package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.model.db.ElectricScooter;
import org.unibl.etf.rest_api.service.crud.ElectricScooterService;

@RestController
@RequestMapping("/api/electric-scooters")
public class ElectricScooterController {
    @Autowired
    private ElectricScooterService electricScooterService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(electricScooterService.retrieveAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getElectricScooter(@PathVariable(name = "id") int id) {
        try {
            ElectricScooter electricScooter = electricScooterService.retrieve(id);
            if (electricScooter != null)
                return ResponseEntity.ok(electricScooter);
            else
                return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ElectricScooter electricScooter) {
        try {
            electricScooter.setDeviceType("ElectricScooter");
            return ResponseEntity.ok(electricScooterService.add(electricScooter));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        try {
            return ResponseEntity.ok(electricScooterService.delete(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
