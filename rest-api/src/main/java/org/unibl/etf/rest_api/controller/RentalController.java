package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.model.db.Rental;
import org.unibl.etf.rest_api.service.crud.RentalService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    @Autowired
    private RentalService service;

    private ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.retrieveAll());
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "-1", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "-1", required = false) int limit) {
        try {
            if (page == -1 || limit == -1) {
                return getAll();
            }

            Pageable pageable = PageRequest.of(page, limit);
            Page<Rental> pageResult = service.retrieveAllPaginated(pageable);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all/{deviceID}")
    public ResponseEntity<?> getByDeviceID(@PathVariable(name = "deviceID") int deviceID) {
        return ResponseEntity.ok(service.retrieveByDeviceID(deviceID));
    }
}
