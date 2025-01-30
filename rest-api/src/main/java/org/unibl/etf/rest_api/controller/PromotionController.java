package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.rest_api.service.crud.PromotionService;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(promotionService.retrieveAll());
    }
}
