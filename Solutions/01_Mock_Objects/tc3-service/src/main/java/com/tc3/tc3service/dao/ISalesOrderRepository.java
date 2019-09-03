package com.tc3.tc3service.dao;

import com.tc3.tc3service.models.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISalesOrderRepository extends JpaRepository<SalesOrder, Long> { // or it could extend PagingAndSortingRepository<Account, Long>{
}
