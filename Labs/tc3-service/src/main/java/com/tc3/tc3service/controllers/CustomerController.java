// CustomerController.java
// Copyright © 2019 NextStep IT Training. All rights reserved.
//

package com.tc3.tc3service.controllers;

import com.tc3.tc3service.models.Customer;
import com.tc3.tc3service.services.CustomerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost")
public class CustomerController {

    @Autowired
    private CustomerManager service;

    @DeleteMapping("/{id}")
    void deleteCustomer(@PathVariable("id") long id) {

        Customer customer = getCustomer(id);

        try {

            service.manageCustomer(5, customer.getCustomerId(), customer, null);
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    Collection<Customer> getCustomers() {

        try {

            return service.manageCustomer(1, 0, null, null);
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    Customer getCustomer(@PathVariable("id") long id) {

        try {

            List<Customer> customers = service.manageCustomer(2, id, null, null);

            if (customers == null) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return customers.get(0);
        }

        catch (IllegalArgumentException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void post(@RequestBody Customer customer) {

        try {

            service.manageCustomer(3, 0, customer, null);
        }

        catch (DataIntegrityViolationException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void put(@PathVariable("id") long id, @RequestBody Customer customer) {

        try {

            service.manageCustomer(4, customer.getCustomerId(), customer, null);
        }

        catch (DataIntegrityViolationException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

