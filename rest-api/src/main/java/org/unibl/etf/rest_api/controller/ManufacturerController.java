package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.model.db.Manufacturer;
import org.unibl.etf.rest_api.service.crud.ManufacturerService;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {
    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<?> getPaginated(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "limit", defaultValue = "10") int limit) {
        try {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Manufacturer> pageResult = manufacturerService.retrieveAllPaginated(pageable);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getManufacturer(@PathVariable(name = "id") int id) {
        try {
            Manufacturer manufacturer = manufacturerService.retrieve(id);

            if (manufacturer != null)
                return ResponseEntity.ok(manufacturer);
            else
                return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateManufacturer(@PathVariable(name = "id") int id, @RequestBody Manufacturer manufacturer) {
        try {
            manufacturer.setManufacturerID(id); // upitno al aj
            return ResponseEntity.ok(manufacturerService.update(manufacturer));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createManufacturer(@RequestBody Manufacturer manufacturer) {
        System.out.println(manufacturer);

        try {
            return ResponseEntity.ok(manufacturerService.create(manufacturer));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManufacturer(@PathVariable(name = "id") int id) {
        try {
            manufacturerService.delete(id);
            return ResponseEntity.ok(manufacturerService.delete(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
