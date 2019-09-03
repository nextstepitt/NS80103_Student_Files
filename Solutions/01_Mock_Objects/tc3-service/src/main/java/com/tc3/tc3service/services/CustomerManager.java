// CustomerManager.java
// Copyright © 2019 NextStep IT Training. All rights reserved.
//

package com.tc3.tc3service.services;

import com.tc3.tc3service.dao.CustomerCardRepository;
import com.tc3.tc3service.dao.CustomerRepository;
import com.tc3.tc3service.models.Customer;
import com.tc3.tc3service.models.CustomerCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerManager {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerCardRepository customerCardRepository;

    // operations:
    // 1 = get customers
    // 2 = get specific customer
    // 3 = add customer
    // 4 = update customer
    // 5 = delete customer
    // 6 = add customer card
    // 7 = update customer card
    // 8 = delete customer card

    public List<Customer> manageCustomer(int operation, long customerId, Customer customer, CustomerCard cardInfo) {

        switch (operation) {
            case 1:
                return customerRepository.findAll();

            case 2:
                var OptionalCustomer = customerRepository.findById((long)customerId);
                if (OptionalCustomer.isPresent()) {
                    List<Customer> customers = new ArrayList<Customer>();
                    customers.add(OptionalCustomer.get());
                    return customers;
                } else {
                    return null;
                }

            case 3:
            case 4:
                customerRepository.save(customer);
                return null;

            case 5:
                customerRepository.delete(customer);
                return null;

            case 6:
            case 7:
                customerCardRepository.save(cardInfo);
                return null;

            case 8:
                customerCardRepository.delete(cardInfo);
                return null;
        }

        return null;
    }
}
