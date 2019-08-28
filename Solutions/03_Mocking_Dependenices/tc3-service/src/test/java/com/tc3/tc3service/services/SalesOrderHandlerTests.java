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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class SalesOrderHandlerTests {

    @Spy
    private IAuthorizationProvider authorizationProvider = new AuthorizationProviderStub();

    private CardInfo cardInfo;

    @Mock
    private ICreditCardValidator creditCardValidator;

    private SalesOrder salesOrder;
    private SalesOrderHandler salesOrderHandler;

    @BeforeEach
    public void setup() {

        salesOrderHandler = new SalesOrderHandler(authorizationProvider, creditCardValidator);

        // The shared cardInfo is initialzed to valid data. The test methods will replace one value at a time
        // with invalid data to test it.

        cardInfo = new CardInfo("John Q Public", "378282246310005", new Date(System.currentTimeMillis()), 168);

        // The shared salesOrder is initialized with valid data (0 < total <= 250).

        salesOrder = new SalesOrder();
        salesOrder.getSalesOrderItems().add(new SalesOrderItem() {{ setPrice(new BigDecimal(50.00)); setQuantity(2); }});
        salesOrder.getSalesOrderItems().add(new SalesOrderItem() {{ setPrice(new BigDecimal(50.00)); setQuantity(2); }});
    }

    @Test
    public void acceptsValidSalesOrderAndCard() {

        // CardInfo cardInfo2 = new CardInfo("John Q Public", "378282246310005", cardInfo.getExpires(), 168);
        // CardInfo cardInfo2 = new CardInfo() {{ setName("John Q Public"); setCardNumber("378282246310005"); setExpires(cardInfo.getExpires()); setCcv(168); }};

        // when(creditCardValidator.validateCardInfo(any(CardInfo.class))).thenReturn(true);
        when(creditCardValidator.validateCardInfo(argThat(new CardNumberMatches378282246310005()))).thenReturn(true);

        cardInfo.setCardNumber("378282246310005");
        assertTrue(salesOrderHandler.completeSale(salesOrder, cardInfo));
    }

    @Test
    public void rejectsEmptySalesOrderWithValidCard() {

        salesOrder.getSalesOrderItems().clear();
        assertFalse(salesOrderHandler.completeSale(salesOrder, cardInfo));
    }

    @Test
    public void rejectsGt250SalesOrderWithValidCard() {

        salesOrder.getSalesOrderItems().get(0).setQuantity(4);
        assertFalse(salesOrderHandler.completeSale(salesOrder, cardInfo));
    }

    @Test
    public void rejectsValidSalesOrderWithInvalidCard() {

        when(creditCardValidator.validateCardInfo(argThat(new CardNumberMatches3378282246310006()))).thenReturn(false);

        cardInfo.setCardNumber("378282246310006");
        assertFalse(salesOrderHandler.completeSale(salesOrder, cardInfo));
    }

    @Test
    public void acceptsButDoesNotAuthorizeOtherCard() {

        when(creditCardValidator.validateCardInfo(argThat(new CardNumberMatches2221001223630333()))).thenReturn(true);

        cardInfo.setCardNumber("2221001223630333");
        assertFalse(salesOrderHandler.completeSale(salesOrder, cardInfo));
    }

    @Test
    public void rejectsValidSalesOrderWithNullCard() {

        when(creditCardValidator.validateCardInfo(argThat(new CardNumberMatchesNull()))).thenReturn(false);

        cardInfo.setCardNumber(null);
        assertFalse(salesOrderHandler.completeSale(salesOrder, cardInfo));
    }

    @Test
    public void exceptionOnNullCardInfo() {

        CardInfo cInfo = new CardInfo();

        when(creditCardValidator.validateCardInfo(cInfo)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> salesOrderHandler.completeSale(salesOrder, cInfo));
    }

    @Test
    public void creditCardValidatorValidateWasCalled() {

        when(creditCardValidator.validateCardInfo(argThat(new CardNumberMatches378282246310005()))).thenReturn(true);

        salesOrderHandler.completeSale(salesOrder, cardInfo);
        verify(creditCardValidator, times(1)).validateCardInfo(cardInfo);
    }

    @Test
    public void creditCardValidatorValidateWasCalledForReal() {

        when(creditCardValidator.validateCardInfo(argThat(new CardNumberMatches378282246310005()))).thenReturn(true);

        cardInfo.setCardNumber("378282246310005");
        salesOrderHandler.completeSale(salesOrder, cardInfo);
        verify(authorizationProvider, times(1)).authorize(new BigDecimal(200.0), cardInfo);
    }

    public class CardNumberMatches378282246310005 implements ArgumentMatcher<CardInfo> {

        public boolean matches(CardInfo value) {

            if (value.getCardNumber().equals("378282246310005")) {

                return true;

            } else {

                throw new IllegalArgumentException();
            }
        }
    }

    public class CardNumberMatches3378282246310006 implements ArgumentMatcher<CardInfo> {

        public boolean matches(CardInfo value) {

            if (value.getCardNumber().equals("378282246310006")) {

                return true;

            } else {

                throw new IllegalArgumentException();
            }
        }
    }

    public class CardNumberMatches2221001223630333 implements ArgumentMatcher<CardInfo> {

        public boolean matches(CardInfo value) {

            if (value.getCardNumber().equals("2221001223630333")) {

                return true;

            } else {

                throw new IllegalArgumentException();
            }
        }
    }

    public class CardNumberMatchesNull implements ArgumentMatcher<CardInfo> {

        public boolean matches(CardInfo value) {

            if (value.getCardNumber() == null) {

                return true;

            } else {

                throw new IllegalArgumentException();
            }
        }
    }
}
