package org.example.common.dto;

public class PaymentResponse {
    private  long orderId;

    private double amount;

    private String paymentStatus;

    private String transactionId;

    private String message;

    public PaymentResponse(){}

    public PaymentResponse(long orderId, double amount, String paymentStatus, String transactionId, String message) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.message = message;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


