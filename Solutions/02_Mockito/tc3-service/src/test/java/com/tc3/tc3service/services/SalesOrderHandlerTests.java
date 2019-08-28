// SalesOrderHandlerTests.cs
// Copyright © 2019 NextStep IT Training. All rights reserved.
//

package com.tc3.tc3service.services;

import com.tc3.tc3service.doubles.AuthorizationProviderStub;
import com.tc3.tc3service.models.CardInfo;
import com.tc3.tc3service.models.SalesOrder;
import com.tc3.tc3service.models.SalesOrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SalesOrderHandlerTests {

    private IAuthorizationProvider authorizationProvider;
    private CardInfo cardInfo;
    private CreditCardValidator creditCardValidator;
    private SalesOrder salesOrder;
    private SalesOrderHandler salesOrderHandler;

    @BeforeEach
    public void setup() {

        authorizationProvider = new AuthorizationProviderStub();
        creditCardValidator = new CreditCardValidator();
        salesOrderHandler = new SalesOrderHandler(authorizationProvider, creditCardValidator);


        // The shared cardInfo is initialzed to valid data. The test methods will replace one value at a time
        // with invalid data to test it.

        cardInfo = new CardInfo("John Q Public", "378282246310005", new java.sql.Date(System.currentTimeMillis()), 168);

        // The shared salesOrder is initialized with valid data (0 < total <= 250).

        salesOrder = new SalesOrder();
        salesOrder.getSalesOrderItems().add(new SalesOrderItem() {{ setPrice(new BigDecimal(50.00)); setQuantity(2); }});
        salesOrder.getSalesOrderItems().add(new SalesOrderItem() {{ setPrice(new BigDecimal(50.00)); setQuantity(2); }});
    }

    @Test
    public void acceptsValidSalesOrderAndCard() {

        assertTrue(salesOrderHandler.CompleteSale(salesOrder, cardInfo));
    }

    @Test
    public void rejectsEmptySalesOrderWithValidCard() {

        salesOrder.getSalesOrderItems().clear();
        assertFalse(salesOrderHandler.CompleteSale(salesOrder, cardInfo));
    }

    @Test
    public void rejectsGt250SalesOrderWithValidCard() {

        salesOrder.getSalesOrderItems().get(0).setQuantity(4);
        assertFalse(salesOrderHandler.CompleteSale(salesOrder, cardInfo));
    }

    @Test
    public void rejectsValidSalesOrderWithInvalidCard() {

        cardInfo.setCardNumber("378282246310006");
        assertFalse(salesOrderHandler.CompleteSale(salesOrder, cardInfo));
    }

    @Test
    public void acceptsButDoesNotAuthorizeOtherCard() {

        cardInfo.setCardNumber("2221001223630333");
        assertFalse(salesOrderHandler.CompleteSale(salesOrder, cardInfo));
    }
}
