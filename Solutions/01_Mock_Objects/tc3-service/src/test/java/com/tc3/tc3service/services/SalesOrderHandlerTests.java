// SalesOrderHandlerTests.cs
// Copyright © 2019 NextStep IT Training. All rights reserved.
//

package com.tc3.tc3service.services;

import com.tc3.tc3service.__doubles__.AuthorizationProviderStub;
import com.tc3.tc3service.dao.ISalesOrderRepository;
import com.tc3.tc3service.models.CardInfo;
import com.tc3.tc3service.models.SalesOrder;
import com.tc3.tc3service.models.SalesOrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalesOrderHandlerTests {

    private CardInfo cardInfo;
    private SalesOrder salesOrder;
    private SalesOrderHandler salesOrderHandler;

    @Mock
    private ISalesOrderRepository salesOrderRepository;

    private IAuthorizationProvider authorizationProvider;

    @Mock
    private ICreditCardValidator creditCardValidator;

    @BeforeEach
    public void setup() {

        authorizationProvider = new AuthorizationProviderStub();
        salesOrderHandler = new SalesOrderHandler(salesOrderRepository, authorizationProvider, creditCardValidator);

        // The shared cardInfo is initialzed to valid data. The test methods will replace one value at a time
        // with invalid data to test it. Test doubles are not required to stand in for data objects, only for
        // objects that provide business logic.

        cardInfo = new CardInfo("John Q Public", "378282246310005", new java.sql.Date(System.currentTimeMillis()), 168);

        // The shared salesOrder is initialized with valid data per the requirement: 0 < total <= 250.

        salesOrder = new SalesOrder();
        salesOrder.getSalesOrderItems().add(new SalesOrderItem() {{ setPrice(new BigDecimal(50.00)); setQuantity(2); }});
        salesOrder.getSalesOrderItems().add(new SalesOrderItem() {{ setPrice(new BigDecimal(50.00)); setQuantity(2); }});
    }

    @Test
    public void acceptsValidSalesOrderAndCardIsAuthorized() {

        cardInfo.setCardNumber("378282246310005");
        when(creditCardValidator.validateCardInfo(cardInfo)).thenReturn(true);

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
        when(creditCardValidator.validateCardInfo(cardInfo)).thenReturn(false);

        assertFalse(salesOrderHandler.CompleteSale(salesOrder, cardInfo));
    }

    @Test
    public void rejectsValidSalesOrderButValidCardIsNotAuthorized() {

        cardInfo.setCardNumber("2221001223630333");
        when(creditCardValidator.validateCardInfo(cardInfo)).thenReturn(false);

        assertFalse(salesOrderHandler.CompleteSale(salesOrder, cardInfo));
    }
}
