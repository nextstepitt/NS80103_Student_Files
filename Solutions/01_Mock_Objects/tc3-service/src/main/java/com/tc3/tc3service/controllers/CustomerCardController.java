// CustomerCardController.java
// Copyright © 2019 NextStep IT Training. All rights reserved.
//

package com.tc3.tc3service.controllers;

import com.tc3.tc3service.models.Customer;
import com.tc3.tc3service.models.CustomerCard;
import com.tc3.tc3service.services.CustomerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/customercards")
@CrossOrigin(origins = "http://localhost")
public class CustomerCardController {

    @Autowired
    private CustomerManager service;

    @DeleteMapping("/{id}")
    void deleteCustomerCard(@PathVariable("id") long id) {

        try {

            List<Customer> customers = service.manageCustomer(1, 0, null, null);

            if (customers != null) {

                for (CustomerCard card : customers.get(0).getCustomerCards()) {

                    if (card.getCustomerCardId() == id) {

                        service.manageCustomer(8, 0, null, card);
                        break;
                    }
                }
            }
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    Collection<CustomerCard> getCustomerCards(@PathVariable("id") long id) {

        try {

            List<Customer> customers = service.manageCustomer(1, id, null, null);

            if (customers != null) {

                return customers.get(0).getCustomerCards();
            }

            return null;
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    CustomerCard getCustomerCard(@PathVariable("id") long id) {

        try {

            List<Customer> customers = service.manageCustomer(1, 0, null, null);

            if (customers != null) {

                for (CustomerCard card : customers.get(0).getCustomerCards()) {

                    if (card.getCustomerCardId() == id) {

                        return card;
                    }
                }
            }

            return null;
        }

        catch (IllegalArgumentException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void postCustomerCard(@RequestBody CustomerCard card) {

        try {

            service.manageCustomer(6, 0, null, card);
        }

        catch (DataIntegrityViolationException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void putCustomerCard(@PathVariable("id") long id, @RequestBody CustomerCard card) {

        try {

            service.manageCustomer(7, 0, null, card);
        }

        catch (DataIntegrityViolationException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

