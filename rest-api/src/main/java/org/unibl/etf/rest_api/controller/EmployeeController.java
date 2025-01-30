package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.model.db.Employee;
import org.unibl.etf.rest_api.service.crud.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "limit", defaultValue = "10") int limit) {
        try {
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<Employee> pageResult = employeeService.retrieveAllPaginated(pageRequest);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable(name = "id") int id) {
        try {
            return ResponseEntity.ok(employeeService.retrieve(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeService.update(employee));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            employee.setActive(true);
            return ResponseEntity.ok(employeeService.create(employee));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(name = "id") int id) {
        try {
            return ResponseEntity.ok(employeeService.delete(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
