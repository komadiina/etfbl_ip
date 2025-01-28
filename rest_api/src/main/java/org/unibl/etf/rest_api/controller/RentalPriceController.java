package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.model.db.RentalPrice;
import org.unibl.etf.rest_api.service.crud.RentalPriceService;

@RestController
@RequestMapping("/api/rental-prices")
public class RentalPriceController {
    @Autowired
    private RentalPriceService rentalPriceService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(rentalPriceService.retrieveAll());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalPrice(@PathVariable(name = "id") int id) {
        try {
            return ResponseEntity.ok(rentalPriceService.retrieve(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/device/{id}")
    public ResponseEntity<?> getByDevice(@PathVariable(name = "id") int id) {
        try {
            return ResponseEntity.ok(rentalPriceService.retrieveDeviceActual(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/device/all/pageable/{id}")
    public ResponseEntity<?> getAllByDevice(@PathVariable(name = "id") int id, @RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit) {
        try {
            Pageable pageable = PageRequest.of(page, limit);
            Page<RentalPrice> pageResult = rentalPriceService.retrieveAllByDevicePaginated(id, pageable);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all/pageable")
    public ResponseEntity<?> getAll(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit) {
        try {
            Pageable pageable = PageRequest.of(page, limit);
            Page<RentalPrice> pageResult = rentalPriceService.retrieveAllPaginated(pageable);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RentalPrice rentalPrice) {
        try {
            rentalPrice.setActive(true);
            return ResponseEntity.ok(rentalPriceService.create(rentalPrice));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
