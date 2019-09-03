// SalesOrderHandler.java
// Copyright © 2019 NextStep IT Training. All rights reserved.
//

package com.tc3.tc3service.services;

import com.tc3.tc3service.dao.ISalesOrderRepository;
import com.tc3.tc3service.models.CardInfo;
import com.tc3.tc3service.models.SalesOrder;
import com.tc3.tc3service.models.SalesOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

@Service
public class SalesOrderHandler {

    private AuthorizationProvider authorizationProvider;
    private CreditCardValidator creditCardValidator;
    private ISalesOrderRepository salesOrderRepository;

    @Autowired
    public SalesOrderHandler(ISalesOrderRepository salesOrderRepository) {

        this.salesOrderRepository = salesOrderRepository;
        authorizationProvider = new AuthorizationProvider();
        creditCardValidator = new CreditCardValidator();
    }

    public boolean CompleteSale(SalesOrder salesOrder, CardInfo cardInfo) {

        boolean result = true;

        if (validateSale(salesOrder) == false) {

            result = false;

        } else if (creditCardValidator.validateCardInfo(cardInfo) == false) {

            result = false;

        } else if (authorizationProvider.authorize(totalSalesOrder(salesOrder), cardInfo) == null) {

            return false;
        }

        return result;
    }

    private BigDecimal totalSalesOrder(SalesOrder salesOrder) {

        BigDecimal total = new BigDecimal(0);

        for (SalesOrderItem item : salesOrder.getSalesOrderItems()) {

            BigDecimal price = item.getPrice() != null ? item.getPrice() : new BigDecimal(0);
            BigDecimal quantity = new BigDecimal(item.getQuantity() != null ? item.getQuantity() : 0);

            total = total.add(price.multiply(quantity));
        }

        return total;
    }

    private boolean validateSale(SalesOrder salesOrder) {

        BigDecimal total = totalSalesOrder(salesOrder);

        return total.compareTo(new BigDecimal(0)) > 0 && total.compareTo(new BigDecimal(250.00)) <= 0;
    }

    public void create(SalesOrder salesOrder) {

        salesOrderRepository.saveAndFlush(salesOrder);
    }

    public void delete(SalesOrder salesOrder) {

        salesOrderRepository.delete(salesOrder);
    }

    public Collection<SalesOrder> read() {

        return (Collection<SalesOrder>)salesOrderRepository.findAll();
    }

    public SalesOrder read(long id) {

        return salesOrderRepository.findById(id).orElse(null);
    }

    public void update(SalesOrder salesOrder) {

        salesOrderRepository.saveAndFlush(salesOrder);
    }
}
