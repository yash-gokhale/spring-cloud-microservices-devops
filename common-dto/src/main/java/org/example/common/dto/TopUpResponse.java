package org.example.common.dto;


import java.io.Serializable;

public class TopUpResponse implements Serializable {
    private String customerId;
    private double finalPrice;
    private String transactionId;
    private String TopUpStatus;

    public TopUpResponse(){}

    public TopUpResponse(String customerId, String transactionId, String topUpStatus) {
        this.customerId = customerId;
        this.transactionId = transactionId;
        TopUpStatus = topUpStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTopUpStatus() {
        return TopUpStatus;
    }

    public void setTopUpStatus(String topUpStatus) {
        TopUpStatus = topUpStatus;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
