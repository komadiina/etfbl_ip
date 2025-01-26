package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.rest_api.service.crud.ElectricBicycleService;

@RestController
@RequestMapping("/api/electric-bicycles")
public class ElectricBicycleController {
    @Autowired
    private ElectricBicycleService electricBicycleService;
}
