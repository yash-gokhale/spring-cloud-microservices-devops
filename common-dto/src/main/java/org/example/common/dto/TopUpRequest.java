package org.example.common.dto;

import java.io.Serializable;

public class TopUpRequest implements Serializable {
    private String customerId;
    private double amount;

    public TopUpRequest() {}

    public TopUpRequest(String customerId, double amount) {
        this.customerId = customerId;
        this.amount = amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
