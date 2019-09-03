// SalesOrderController.java
// Copyright © 2019 NextStep IT Training. All rights reserved.
//

package com.tc3.tc3service.controllers;

import com.tc3.tc3service.dto.SalesOrderDto;
import com.tc3.tc3service.models.SalesOrder;
import com.tc3.tc3service.services.SalesOrderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/salesorders")
@CrossOrigin(origins = "http://localhost")
public class SalesOrderController {

    // All of the methods in this controller expect or return instances of Data Transfer Objects.
    // This is usually the best practice. Using DTOs results in more work, because of the conversion
    // from model object to DTO and back again (see the constructors in both classes). But, the
    // benefit is high: decoupling the client from the model in the service, and being able to
    // have multiple DTOs that represent interface to one model class, providing the client with
    // different versions of what they can see. For example, one DTO could be a "shorter" version
    // of a model object where all the information is not necessary to pass (and expose!).

    private SalesOrderHandler service;

    @Autowired
    public SalesOrderController(SalesOrderHandler service) {

        this.service = service;
    }

    @DeleteMapping("/{id}")
    void deleteSalesOrder(@PathVariable("id") long id) {

        SalesOrderDto salesOrderDto = getSalesOrder(id);

        try {

            service.delete(new SalesOrder(salesOrderDto));
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    Collection<SalesOrderDto> getSalesOrders() {

        try {

            Collection<SalesOrder> salesOrders = service.read();
            Collection<SalesOrderDto> salesOrderDtos = new ArrayList<SalesOrderDto>();

            salesOrders.forEach(salesOrder -> salesOrderDtos.add(new SalesOrderDto(salesOrder)));

            return salesOrderDtos;
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    SalesOrderDto getSalesOrder(@PathVariable("id") long id) {

        try {

            SalesOrder salesOrder = service.read(id);

            if (salesOrder == null) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return new SalesOrderDto(salesOrder);
        }

        catch (IllegalArgumentException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void postSalesOrder(@RequestBody SalesOrderDto salesOrderDto) {

        try {

            service.create(new SalesOrder(salesOrderDto));
        }

        catch (DataIntegrityViolationException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void putSalesOrder(@PathVariable("id") long id, @RequestBody SalesOrderDto salesOrderDto) {

        try {

            service.update(new SalesOrder(salesOrderDto));
        }

        catch (DataIntegrityViolationException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

