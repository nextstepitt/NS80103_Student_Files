package com.tc3.tc3service.dao;

import com.tc3.tc3service.models.CustomerCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long> { // or it could extend PagingAndSortingRepository<Account, Long>{
}
