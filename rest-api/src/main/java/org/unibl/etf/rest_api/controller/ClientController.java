package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.model.db.Client;
import org.unibl.etf.rest_api.service.crud.ClientService;

import java.util.HashMap;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    private ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(clientService.retrieveAll());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "-1", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "-1", required = false) int limit) {
        try {
            if (page == -1 || limit == -1) {
                return getAll();
            }

            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<Client> pageResult = clientService.retrieveAllPaginated(pageRequest);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClient(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(clientService.retrieve(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable(name = "id") int id) {
        try {
            return ResponseEntity.ok(clientService.delete(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/toggle-active/{clientID}")
    public ResponseEntity<?> toggleActive(@PathVariable(name = "clientID") int clientID) {
        try {
            return ResponseEntity.ok(clientService.toggleActive(clientID));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
