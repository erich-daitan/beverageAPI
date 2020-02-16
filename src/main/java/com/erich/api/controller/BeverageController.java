package com.erich.api.controller;

import com.erich.api.exception.BeverageServiceException;
import com.erich.api.model.Beverage;
import com.erich.api.service.BeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/beverage")
public class BeverageController {

    private BeverageService beverageService;

    @Autowired
    public BeverageController(BeverageService beverageService) {
        this.beverageService = beverageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable> findAllBeverages() {
        Iterable<Beverage> result = beverageService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/section/{section}")
    public ResponseEntity<Object> findAllBeveragesBySection(@PathVariable Integer section) {
        try {
            Iterable<Beverage> result = beverageService.findAllBySection(section);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (BeverageServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/availableStorage/section/{section}")
    public ResponseEntity<Object> getAvailableStorageBySection(@PathVariable Integer section) {
        try {
            Integer result = beverageService.getAvailableStorageInLitters(section);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (BeverageServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> addBeverage(@RequestBody Beverage beverage) {
        try {
            Beverage storedBeverage = beverageService.addBeverage(beverage);
            return new ResponseEntity<>(storedBeverage, HttpStatus.OK);
        } catch (BeverageServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
