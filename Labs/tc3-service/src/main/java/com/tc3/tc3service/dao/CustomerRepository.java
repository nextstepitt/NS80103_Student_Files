package com.tc3.tc3service.dao;

import com.tc3.tc3service.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> { // or it could extend PagingAndSortingRepository<Account, Long>{
}
