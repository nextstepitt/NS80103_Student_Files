// SalesOrderDto.java
// Copyright © 2019 NextStep IT Training. All rights reserved.
//
// This is the representation of a sales order that leaves and arrives the service as JSON data. Data
// transfer objects allow control of what is actually permitted to be sent to the remote.
//

package com.tc3.tc3service.dto;

import com.tc3.tc3service.models.SalesOrder;

import java.math.BigDecimal;
import java.sql.Date;

public class SalesOrderDto {

    private long salesOrderId;
    private Date orderDate;
    private long customerId;
    private BigDecimal total;
    private long paymentTypeId;
    private String cardNumber;
    private Date cardExpires;
    private Date filled;

    public SalesOrderDto() {
    }

    public SalesOrderDto(SalesOrder salesOrder) {

        setSalesOrderId(salesOrder.getSalesOrderId());
        setOrderDate(salesOrder.getOrderDate());
        setCustomerId(salesOrder.getCustomerId());
        setTotal((salesOrder.getTotal()));
        setPaymentTypeId(salesOrder.getPaymentTypeId());
        setCardNumber(salesOrder.getCardNumber());
        setCardExpires(salesOrder.getCardExpires());
        setFilled(salesOrder.getFilled());
    }

    public long getSalesOrderId() {

        return salesOrderId;
    }

    public void setSalesOrderId(long id) {

        this.salesOrderId = id;
    }

    public Date getOrderDate() {

        return orderDate;
    }

    public void setOrderDate(Date orderDate) {

        this.orderDate = orderDate;
    }

    public long getCustomerId() {

        return customerId;
    }

    public void setCustomerId(long customerId) {

        this.customerId = customerId;
    }

    public BigDecimal getTotal() {

        return total;
    }

    public void setTotal(BigDecimal total) {

        this.total = total;
    }

    public long getPaymentTypeId() {

        return paymentTypeId;
    }

    public void setPaymentTypeId(long paymentTypeId) {

        this.paymentTypeId = paymentTypeId;
    }

    public String getCardNumber() {

        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {

        this.cardNumber = cardNumber;
    }

    public Date getCardExpires() {

        return cardExpires;
    }

    public void setCardExpires(Date cardExpires) {

        this.cardExpires = cardExpires;
    }

    public Date getFilled() {

        return filled;
    }

    public void setFilled(Date filled) {

        this.filled = filled;
    }
}
