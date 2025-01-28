package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.model.db.Breakdown;
import org.unibl.etf.rest_api.service.crud.BreakdownService;

@RestController
@RequestMapping("/api/breakdowns")
public class BreakdownController {
    @Autowired
    private BreakdownService breakdownService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(breakdownService.retrieveAll());
    }

    @GetMapping("/all/{deviceID}")
    public ResponseEntity<?> getByDeviceID(@PathVariable(name = "deviceID") int deviceID) {
        return ResponseEntity.ok(breakdownService.retrieveByDeviceID(deviceID));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBreakdown(@PathVariable(name = "id") int id) {
        try {
            return ResponseEntity.ok(breakdownService.retrieve(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createBreakdown(@RequestBody Breakdown breakdown) {
        try {
            return ResponseEntity.ok(breakdownService.create(breakdown));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{deviceID}")
    public ResponseEntity<?> disableBreakdown(@PathVariable(name = "deviceID") int deviceID) {
        try {
            return ResponseEntity.ok(breakdownService.disableBreakdown(deviceID));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{breakdownID}")
    public ResponseEntity<?> deleteBreakdown( @PathVariable(name = "breakdownID") int breakdownID) {
        try {
            return ResponseEntity.ok(breakdownService.removeBreakdown(breakdownID));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
